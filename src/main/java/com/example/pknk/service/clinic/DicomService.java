package com.example.pknk.service.clinic;

import com.example.pknk.domain.dto.request.clinic.DicomUploadRequest;
import com.example.pknk.domain.dto.response.clinic.DicomInstanceResponse;
import com.example.pknk.domain.dto.response.clinic.DicomSeriesResponse;
import com.example.pknk.domain.dto.response.clinic.DicomStudyResponse;
import com.example.pknk.domain.entity.clinic.DicomInstance;
import com.example.pknk.domain.entity.clinic.DicomSeries;
import com.example.pknk.domain.entity.clinic.DicomStudy;
import com.example.pknk.domain.entity.clinic.Examination;
import com.example.pknk.domain.entity.clinic.TreatmentPhases;
import com.example.pknk.domain.entity.user.Patient;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.DicomInstanceRepository;
import com.example.pknk.repository.clinic.DicomSeriesRepository;
import com.example.pknk.repository.clinic.DicomStudyRepository;
import com.example.pknk.repository.clinic.ExaminationRepository;
import com.example.pknk.repository.clinic.TreatmentPhasesRepository;
import com.example.pknk.repository.patient.PatientRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DicomService {
    
    OrthancClientService orthancClientService;
    DicomStudyRepository dicomStudyRepository;
    DicomSeriesRepository dicomSeriesRepository;
    DicomInstanceRepository dicomInstanceRepository;
    PatientRepository patientRepository;
    ExaminationRepository examinationRepository;
    TreatmentPhasesRepository treatmentPhasesRepository;
    
    /**
     * Upload DICOM file to Orthanc and save metadata to database
     */
    @Transactional
    @PreAuthorize("hasAuthority('UPLOAD_DICOM')")
    public DicomStudyResponse uploadDicom(DicomUploadRequest request) throws IOException {
        MultipartFile file = request.getFile();
        
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.DICOM_FILE_INVALID);
        }
        
        // Validate patient
        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_EXISTED));
        
        // Upload to Orthanc
        String orthancInstanceId = orthancClientService.uploadDicomFile(file);
        log.info("DICOM file uploaded to Orthanc with instance ID: {}", orthancInstanceId);
        
        if (orthancInstanceId == null || orthancInstanceId.isEmpty()) {
            log.error("Orthanc instance ID is empty after upload. Cannot proceed.");
            throw new AppException(ErrorCode.DICOM_UPLOAD_FAILED);
        }
        
        // Get instance info from Orthanc
        log.info("Getting instance info from Orthanc for instance ID: {}", orthancInstanceId);
        JsonNode instanceInfo = orthancClientService.getInstanceInfo(orthancInstanceId);
        log.debug("Instance info: {}", instanceInfo);
        
        if (instanceInfo == null || !instanceInfo.has("ParentSeries")) {
            log.error("Invalid instance info from Orthanc: {}", instanceInfo);
            throw new AppException(ErrorCode.DICOM_UPLOAD_FAILED);
        }
        
        String orthancSeriesId = instanceInfo.get("ParentSeries").asText();
        log.info("Extracted Series ID: {}", orthancSeriesId);
        
        // Get series info from Orthanc to get ParentStudy
        log.info("Getting series info from Orthanc");
        JsonNode seriesInfo = orthancClientService.getSeriesInfo(orthancSeriesId);
        
        if (seriesInfo == null || !seriesInfo.has("ParentStudy")) {
            log.error("Invalid series info from Orthanc or missing ParentStudy: {}", seriesInfo);
            throw new AppException(ErrorCode.DICOM_UPLOAD_FAILED);
        }
        
        String orthancStudyId = seriesInfo.get("ParentStudy").asText();
        log.info("Extracted Study ID: {}", orthancStudyId);
        
        // Get instance tags (DICOM metadata)
        log.info("Getting instance tags from Orthanc");
        JsonNode instanceTags = orthancClientService.getInstanceTags(orthancInstanceId);
        
        // Get study info from Orthanc
        log.info("Getting study info from Orthanc");
        JsonNode studyInfo = orthancClientService.getStudyInfo(orthancStudyId);
        
        // Parse DICOM tags from file for additional metadata
        log.info("Parsing DICOM file for metadata");
        Attributes dicomAttributes = parseDicomFile(file);
        if (dicomAttributes == null) {
            log.warn("Failed to parse DICOM file, continuing with metadata from Orthanc only");
        }
        
        // Check if study already exists
        log.info("Checking if study already exists in database. Orthanc Study ID: {}", orthancStudyId);
        DicomStudy study = dicomStudyRepository.findByOrthancStudyId(orthancStudyId)
            .orElse(null);
        
        if (study == null) {
            log.info("Study not found in database, creating new study");
            // Create new study
            study = createDicomStudy(orthancStudyId, studyInfo, instanceTags, dicomAttributes, patient, 
                request.getExaminationId(), request.getTreatmentPhaseId());
            study = dicomStudyRepository.save(study);
            log.info("New study created with ID: {}", study.getId());
        } else {
            log.info("Study already exists in database with ID: {}", study.getId());
        }
        
        // Check if series already exists
        log.info("Checking if series already exists in database. Orthanc Series ID: {}", orthancSeriesId);
        DicomSeries series = dicomSeriesRepository.findByOrthancSeriesId(orthancSeriesId)
            .orElse(null);
        
        if (series == null) {
            log.info("Series not found in database, creating new series");
            // Create new series
            series = createDicomSeries(orthancSeriesId, seriesInfo, instanceTags, dicomAttributes, study);
            series = dicomSeriesRepository.save(series);
            log.info("New series created with ID: {}", series.getId());
        } else {
            log.info("Series already exists in database with ID: {}", series.getId());
        }
        
        // Check if instance already exists
        log.info("Checking if instance already exists in database. Orthanc Instance ID: {}", orthancInstanceId);
        DicomInstance instance = dicomInstanceRepository.findByOrthancInstanceId(orthancInstanceId)
            .orElse(null);
        
        if (instance == null) {
            log.info("Instance not found in database, creating new instance");
            // Create new instance
            instance = createDicomInstance(orthancInstanceId, instanceInfo, instanceTags, dicomAttributes, series, file);
            instance = dicomInstanceRepository.save(instance);
            log.info("New instance created with ID: {}", instance.getId());
        } else {
            log.info("Instance already exists in database with ID: {}", instance.getId());
        }
        
        // Update study and series counts
        log.info("Updating study and series counts");
        updateStudyAndSeriesCounts(study, series);
        
        // Link with examination or treatment phase if provided
        log.info("Linking study with examination or treatment phase");
        linkWithExaminationOrTreatmentPhase(study, request.getExaminationId(), request.getTreatmentPhaseId());
        
        // Refresh study to get latest data
        study = dicomStudyRepository.findById(study.getId())
            .orElseThrow(() -> new AppException(ErrorCode.DICOM_STUDY_NOT_EXISTED));
        
        // Convert to response
        log.info("Converting study to response. Study ID: {}", study.getId());
        DicomStudyResponse response = mapToStudyResponse(study);
        log.info("DICOM upload completed successfully. Returning response");
        return response;
    }
    
    /**
     * Get DICOM study by ID
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    public DicomStudyResponse getStudyById(String studyId) {
        DicomStudy study = dicomStudyRepository.findById(studyId)
            .orElseThrow(() -> new AppException(ErrorCode.DICOM_STUDY_NOT_EXISTED));
        
        return mapToStudyResponse(study);
    }
    
    /**
     * Get all DICOM studies for a patient
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    public List<DicomStudyResponse> getStudiesByPatientId(String patientId) {
        List<DicomStudy> studies = dicomStudyRepository.findByPatientIdOrderByStudyDateDesc(patientId);
        return studies.stream()
            .map(this::mapToStudyResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Get DICOM studies for an examination
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    public List<DicomStudyResponse> getStudiesByExaminationId(String examinationId) {
        List<DicomStudy> studies = dicomStudyRepository.findByExaminationId(examinationId);
        return studies.stream()
            .map(this::mapToStudyResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Get DICOM studies for a treatment phase
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    public List<DicomStudyResponse> getStudiesByTreatmentPhaseId(String treatmentPhaseId) {
        List<DicomStudy> studies = dicomStudyRepository.findByTreatmentPhaseId(treatmentPhaseId);
        return studies.stream()
            .map(this::mapToStudyResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Delete DICOM study (and cascade to series and instances)
     */
    @Transactional
    /**
     * Get DICOM file from Orthanc by instance ID
     * @param instanceId Orthanc instance ID
     * @return DICOM file as byte array
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    public byte[] getInstanceFile(String instanceId) {
        return orthancClientService.getDicomFile(instanceId);
    }
    
    @PreAuthorize("hasAuthority('DELETE_DICOM')")
    public void deleteStudy(String studyId) {
        DicomStudy study = dicomStudyRepository.findById(studyId)
            .orElseThrow(() -> new AppException(ErrorCode.DICOM_STUDY_NOT_EXISTED));
        
        // Note: Orthanc deletion should be handled separately if needed
        // For now, we only delete from our database
        dicomStudyRepository.delete(study);
    }
    
    // ========== Private Helper Methods ==========
    
    private DicomStudy createDicomStudy(String orthancStudyId, JsonNode studyInfo, JsonNode instanceTags, 
                                       Attributes dicomAttributes, Patient patient, 
                                       String examinationId, String treatmentPhaseId) {
        DicomStudy.DicomStudyBuilder builder = DicomStudy.builder()
            .orthancStudyId(orthancStudyId)
            .patient(patient);
        
        // Extract from DICOM tags
        if (dicomAttributes != null) {
            builder.studyInstanceUID(getDicomTag(dicomAttributes, Tag.StudyInstanceUID))
                .studyDate(parseDicomDate(getDicomTag(dicomAttributes, Tag.StudyDate)))
                .studyTime(parseDicomTime(getDicomTag(dicomAttributes, Tag.StudyTime)))
                .studyDescription(getDicomTag(dicomAttributes, Tag.StudyDescription))
                .accessionNumber(getDicomTag(dicomAttributes, Tag.AccessionNumber))
                .modality(getDicomTag(dicomAttributes, Tag.ModalitiesInStudy))
                .referringPhysicianName(getDicomTag(dicomAttributes, Tag.ReferringPhysicianName))
                .patientName(getDicomTag(dicomAttributes, Tag.PatientName))
                .patientIdDicom(getDicomTag(dicomAttributes, Tag.PatientID))
                .patientBirthDate(parseDicomDate(getDicomTag(dicomAttributes, Tag.PatientBirthDate)))
                .patientSex(getDicomTag(dicomAttributes, Tag.PatientSex));
        }
        
        // Extract from Orthanc study info
        DicomStudy tempStudy = builder.build();
        if (studyInfo != null) {
            if (tempStudy.getStudyInstanceUID() == null && studyInfo.has("MainDicomTags")) {
                JsonNode mainTags = studyInfo.get("MainDicomTags");
                if (mainTags.has("StudyInstanceUID")) {
                    builder.studyInstanceUID(mainTags.get("StudyInstanceUID").asText());
                }
            }
            if (studyInfo.has("NumberOfStudyRelatedSeries")) {
                builder.numberOfStudyRelatedSeries(studyInfo.get("NumberOfStudyRelatedSeries").asInt());
            }
            if (studyInfo.has("NumberOfStudyRelatedInstances")) {
                builder.numberOfStudyRelatedInstances(studyInfo.get("NumberOfStudyRelatedInstances").asInt());
            }
        }
        
        DicomStudy study = builder.build();
        
        // Link with examination or treatment phase
        if (examinationId != null && !examinationId.isEmpty()) {
            Examination examination = examinationRepository.findById(examinationId)
                .orElse(null);
            if (examination != null) {
                study.setExamination(examination);
            }
        }
        
        if (treatmentPhaseId != null && !treatmentPhaseId.isEmpty()) {
            TreatmentPhases treatmentPhase = treatmentPhasesRepository.findById(treatmentPhaseId)
                .orElse(null);
            if (treatmentPhase != null) {
                study.setTreatmentPhase(treatmentPhase);
            }
        }
        
        return study;
    }
    
    private DicomSeries createDicomSeries(String orthancSeriesId, JsonNode seriesInfo, JsonNode instanceTags,
                                         Attributes dicomAttributes, DicomStudy study) {
        DicomSeries.DicomSeriesBuilder builder = DicomSeries.builder()
            .orthancSeriesId(orthancSeriesId)
            .study(study);
        
        // Extract from DICOM tags
        if (dicomAttributes != null) {
            builder.seriesInstanceUID(getDicomTag(dicomAttributes, Tag.SeriesInstanceUID))
                .seriesNumber(getDicomInt(dicomAttributes, Tag.SeriesNumber))
                .seriesDescription(getDicomTag(dicomAttributes, Tag.SeriesDescription))
                .seriesDate(parseDicomDate(getDicomTag(dicomAttributes, Tag.SeriesDate)))
                .seriesTime(parseDicomTime(getDicomTag(dicomAttributes, Tag.SeriesTime)))
                .modality(getDicomTag(dicomAttributes, Tag.Modality))
                .bodyPartExamined(getDicomTag(dicomAttributes, Tag.BodyPartExamined))
                .protocolName(getDicomTag(dicomAttributes, Tag.ProtocolName));
        }
        
        // Extract from Orthanc series info
        DicomSeries tempSeries = builder.build();
        if (seriesInfo != null) {
            if (tempSeries.getSeriesInstanceUID() == null && seriesInfo.has("MainDicomTags")) {
                JsonNode mainTags = seriesInfo.get("MainDicomTags");
                if (mainTags.has("SeriesInstanceUID")) {
                    builder.seriesInstanceUID(mainTags.get("SeriesInstanceUID").asText());
                }
            }
            if (seriesInfo.has("NumberOfSeriesRelatedInstances")) {
                builder.numberOfSeriesRelatedInstances(seriesInfo.get("NumberOfSeriesRelatedInstances").asInt());
            }
        }
        
        return builder.build();
    }
    
    private DicomInstance createDicomInstance(String orthancInstanceId, JsonNode instanceInfo, JsonNode instanceTags,
                                             Attributes dicomAttributes, DicomSeries series, MultipartFile file) {
        DicomInstance.DicomInstanceBuilder builder = DicomInstance.builder()
            .orthancInstanceId(orthancInstanceId)
            .series(series);
        
        // Extract from DICOM tags
        if (dicomAttributes != null) {
            builder.sopInstanceUID(getDicomTag(dicomAttributes, Tag.SOPInstanceUID))
                .sopClassUID(getDicomTag(dicomAttributes, Tag.SOPClassUID))
                .instanceNumber(getDicomInt(dicomAttributes, Tag.InstanceNumber))
                .contentDate(parseDicomDate(getDicomTag(dicomAttributes, Tag.ContentDate)))
                .contentTime(parseDicomTime(getDicomTag(dicomAttributes, Tag.ContentTime)))
                .imageType(getDicomTag(dicomAttributes, Tag.ImageType))
                .rows(getDicomInt(dicomAttributes, Tag.Rows))
                .columns(getDicomInt(dicomAttributes, Tag.Columns))
                .bitsAllocated(getDicomInt(dicomAttributes, Tag.BitsAllocated))
                .bitsStored(getDicomInt(dicomAttributes, Tag.BitsStored))
                .samplesPerPixel(getDicomInt(dicomAttributes, Tag.SamplesPerPixel))
                .photometricInterpretation(getDicomTag(dicomAttributes, Tag.PhotometricInterpretation));
        }
        
        // File size
        if (file != null) {
            builder.fileSize(file.getSize());
        }
        
        return builder.build();
    }
    
    private void updateStudyAndSeriesCounts(DicomStudy study, DicomSeries series) {
        // Update series count in study
        long seriesCount = dicomSeriesRepository.findByStudyId(study.getId()).size();
        study.setNumberOfStudyRelatedSeries((int) seriesCount);
        
        // Update instance count in series
        long instanceCount = dicomInstanceRepository.findBySeriesId(series.getId()).size();
        series.setNumberOfSeriesRelatedInstances((int) instanceCount);
        
        // Update total instance count in study
        long totalInstances = dicomInstanceRepository.findAll().stream()
            .filter(instance -> instance.getSeries().getStudy().getId().equals(study.getId()))
            .count();
        study.setNumberOfStudyRelatedInstances((int) totalInstances);
        
        dicomStudyRepository.save(study);
        dicomSeriesRepository.save(series);
    }
    
    private void linkWithExaminationOrTreatmentPhase(DicomStudy study, String examinationId, String treatmentPhaseId) {
        if (examinationId != null && !examinationId.isEmpty()) {
            Examination examination = examinationRepository.findById(examinationId).orElse(null);
            if (examination != null) {
                examination.setDicomStudy(study);
                examinationRepository.save(examination);
            }
        }
        
        if (treatmentPhaseId != null && !treatmentPhaseId.isEmpty()) {
            TreatmentPhases treatmentPhase = treatmentPhasesRepository.findById(treatmentPhaseId).orElse(null);
            if (treatmentPhase != null) {
                treatmentPhase.setDicomStudy(study);
                treatmentPhasesRepository.save(treatmentPhase);
            }
        }
    }
    
    private DicomStudyResponse mapToStudyResponse(DicomStudy study) {
        List<DicomSeries> seriesList = dicomSeriesRepository.findByStudyIdOrderBySeriesNumberAsc(study.getId());
        
        List<DicomSeriesResponse> seriesResponses = seriesList.stream()
            .map(series -> {
                // Get instances from database
                List<DicomInstance> dbInstances = dicomInstanceRepository.findBySeriesIdOrderByInstanceNumberAsc(series.getId());
                
                // Fetch all instances from Orthanc for this series
                List<DicomInstanceResponse> instanceResponses = new ArrayList<>();
                try {
                    log.info("Fetching instances from Orthanc for series: {} (DB has {} instances)", 
                        series.getOrthancSeriesId(), dbInstances.size());
                    List<String> orthancInstanceIds = orthancClientService.getSeriesInstances(series.getOrthancSeriesId());
                    log.info("Found {} instances in Orthanc for series {} (DB has {} instances)", 
                        orthancInstanceIds.size(), series.getOrthancSeriesId(), dbInstances.size());
                    
                    // Create instance responses from Orthanc instance IDs
                    // Use database instances if available for metadata, otherwise create minimal responses
                    for (String orthancInstanceId : orthancInstanceIds) {
                        DicomInstance dbInstance = dbInstances.stream()
                            .filter(inst -> inst.getOrthancInstanceId().equals(orthancInstanceId))
                            .findFirst()
                            .orElse(null);
                        
                        // Check if this is a multi-frame DICOM
                        int numberOfFrames = 1;
                        try {
                            JsonNode instanceInfo = orthancClientService.getInstanceInfo(orthancInstanceId);
                            if (instanceInfo.has("MainDicomTags")) {
                                JsonNode mainTags = instanceInfo.get("MainDicomTags");
                                if (mainTags.has("NumberOfFrames")) {
                                    numberOfFrames = mainTags.get("NumberOfFrames").asInt();
                                    log.info("Instance {} is multi-frame with {} frames", orthancInstanceId, numberOfFrames);
                                }
                            }
                        } catch (Exception e) {
                            log.warn("Failed to get instance info for multi-frame check: {}", e.getMessage());
                        }
                        
                        if (numberOfFrames > 1) {
                            // Multi-frame DICOM: create a response for each frame
                            // Frontend will use frame index in imageId: wadouri:url#frame=0, #frame=1, etc.
                            DicomInstanceResponse baseResponse = dbInstance != null ? 
                                mapToInstanceResponse(dbInstance) :
                                DicomInstanceResponse.builder()
                                    .orthancInstanceId(orthancInstanceId)
                                    .seriesId(series.getId())
                                    .build();
                            
                            for (int frameIndex = 0; frameIndex < numberOfFrames; frameIndex++) {
                                // Create a copy of base response with frame index in orthancInstanceId
                                DicomInstanceResponse frameResponse = DicomInstanceResponse.builder()
                                    .id(baseResponse.getId())
                                    .seriesId(baseResponse.getSeriesId())
                                    .orthancInstanceId(orthancInstanceId + "#frame=" + frameIndex)
                                    .sopInstanceUID(baseResponse.getSopInstanceUID())
                                    .sopClassUID(baseResponse.getSopClassUID())
                                    .instanceNumber(baseResponse.getInstanceNumber())
                                    .contentDate(baseResponse.getContentDate())
                                    .contentTime(baseResponse.getContentTime())
                                    .imageType(baseResponse.getImageType())
                                    .rows(baseResponse.getRows())
                                    .columns(baseResponse.getColumns())
                                    .bitsAllocated(baseResponse.getBitsAllocated())
                                    .bitsStored(baseResponse.getBitsStored())
                                    .samplesPerPixel(baseResponse.getSamplesPerPixel())
                                    .photometricInterpretation(baseResponse.getPhotometricInterpretation())
                                    .fileSize(baseResponse.getFileSize())
                                    .filePath(baseResponse.getFilePath())
                                    .build();
                                
                                instanceResponses.add(frameResponse);
                            }
                            log.info("Created {} frame responses for multi-frame instance {}", numberOfFrames, orthancInstanceId);
                        } else {
                            // Single-frame DICOM
                            if (dbInstance != null) {
                                // Use database instance with full metadata
                                instanceResponses.add(mapToInstanceResponse(dbInstance));
                            } else {
                                // Create minimal instance response from Orthanc ID only
                                instanceResponses.add(DicomInstanceResponse.builder()
                                    .orthancInstanceId(orthancInstanceId)
                                    .seriesId(series.getId())
                                    .build());
                            }
                        }
                    }
                    log.info("Created {} instance responses for series {} ({} from DB, {} from Orthanc only)", 
                        instanceResponses.size(), series.getOrthancSeriesId(), 
                        dbInstances.size(), instanceResponses.size() - dbInstances.size());
                } catch (Exception e) {
                    log.error("Failed to fetch instances from Orthanc for series {}, using database instances only. Error: {}", 
                        series.getOrthancSeriesId(), e.getMessage(), e);
                    // Fallback to database instances only
                    instanceResponses = dbInstances.stream()
                        .map(this::mapToInstanceResponse)
                        .collect(Collectors.toList());
                    log.warn("Using {} database instances as fallback", instanceResponses.size());
                }
                
                log.info("Returning {} instances for series {}", instanceResponses.size(), series.getOrthancSeriesId());
                return mapToSeriesResponse(series, instanceResponses);
            })
            .collect(Collectors.toList());
        
        return DicomStudyResponse.builder()
            .id(study.getId())
            .patientId(study.getPatient().getId())
            .patientName(study.getPatientName())
            .examinationId(study.getExamination() != null ? study.getExamination().getId() : null)
            .treatmentPhaseId(study.getTreatmentPhase() != null ? study.getTreatmentPhase().getId() : null)
            .orthancStudyId(study.getOrthancStudyId())
            .studyInstanceUID(study.getStudyInstanceUID())
            .studyDate(study.getStudyDate())
            .studyTime(study.getStudyTime())
            .studyDescription(study.getStudyDescription())
            .accessionNumber(study.getAccessionNumber())
            .modality(study.getModality())
            .referringPhysicianName(study.getReferringPhysicianName())
            .patientIdDicom(study.getPatientIdDicom())
            .patientBirthDate(study.getPatientBirthDate())
            .patientSex(study.getPatientSex())
            .numberOfStudyRelatedSeries(study.getNumberOfStudyRelatedSeries())
            .numberOfStudyRelatedInstances(study.getNumberOfStudyRelatedInstances())
            .series(seriesResponses)
            .build();
    }
    
    private DicomSeriesResponse mapToSeriesResponse(DicomSeries series, List<DicomInstanceResponse> instances) {
        return DicomSeriesResponse.builder()
            .id(series.getId())
            .studyId(series.getStudy().getId())
            .orthancSeriesId(series.getOrthancSeriesId())
            .seriesInstanceUID(series.getSeriesInstanceUID())
            .seriesNumber(series.getSeriesNumber())
            .seriesDescription(series.getSeriesDescription())
            .seriesDate(series.getSeriesDate())
            .seriesTime(series.getSeriesTime())
            .modality(series.getModality())
            .bodyPartExamined(series.getBodyPartExamined())
            .protocolName(series.getProtocolName())
            .numberOfSeriesRelatedInstances(series.getNumberOfSeriesRelatedInstances())
            .instances(instances)
            .build();
    }
    
    private DicomInstanceResponse mapToInstanceResponse(DicomInstance instance) {
        return DicomInstanceResponse.builder()
            .id(instance.getId())
            .seriesId(instance.getSeries().getId())
            .orthancInstanceId(instance.getOrthancInstanceId())
            .sopInstanceUID(instance.getSopInstanceUID())
            .sopClassUID(instance.getSopClassUID())
            .instanceNumber(instance.getInstanceNumber())
            .contentDate(instance.getContentDate())
            .contentTime(instance.getContentTime())
            .imageType(instance.getImageType())
            .rows(instance.getRows())
            .columns(instance.getColumns())
            .bitsAllocated(instance.getBitsAllocated())
            .bitsStored(instance.getBitsStored())
            .samplesPerPixel(instance.getSamplesPerPixel())
            .photometricInterpretation(instance.getPhotometricInterpretation())
            .fileSize(instance.getFileSize())
            .filePath(instance.getFilePath())
            .build();
    }
    
    // ========== DICOM Parsing Helpers ==========
    
    private Attributes parseDicomFile(MultipartFile file) {
        try {
            DicomInputStream dis = new DicomInputStream(file.getInputStream());
            // Use readDataset() without parameters to avoid deprecation warning
            Attributes attrs = dis.readDataset();
            dis.close();
            return attrs;
        } catch (Exception e) {
            log.warn("Failed to parse DICOM file: {}", e.getMessage());
            return null;
        }
    }
    
    private String getDicomTag(Attributes attrs, int tag) {
        if (attrs == null) return null;
        org.dcm4che3.data.ElementDictionary dict = org.dcm4che3.data.ElementDictionary.getStandardElementDictionary();
        return attrs.getString(tag);
    }
    
    private Integer getDicomInt(Attributes attrs, int tag) {
        if (attrs == null) return null;
        return attrs.getInt(tag, 0);
    }
    
    private LocalDate parseDicomDate(String dicomDate) {
        if (dicomDate == null || dicomDate.isEmpty()) return null;
        try {
            // DICOM date format: YYYYMMDD
            if (dicomDate.length() == 8) {
                return LocalDate.parse(dicomDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
        } catch (Exception e) {
            log.warn("Failed to parse DICOM date: {}", dicomDate);
        }
        return null;
    }
    
    private LocalTime parseDicomTime(String dicomTime) {
        if (dicomTime == null || dicomTime.isEmpty()) return null;
        try {
            // DICOM time format: HHmmss.ffffff or HHmmss
            String timeStr = dicomTime.length() >= 6 ? dicomTime.substring(0, 6) : dicomTime;
            if (timeStr.length() == 6) {
                return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HHmmss"));
            }
        } catch (Exception e) {
            log.warn("Failed to parse DICOM time: {}", dicomTime);
        }
        return null;
    }
}

