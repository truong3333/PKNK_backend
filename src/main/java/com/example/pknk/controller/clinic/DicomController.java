package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.DicomUploadRequest;
import com.example.pknk.domain.dto.response.clinic.DicomStudyResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.DicomService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dicom")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DicomController {
    
    DicomService dicomService;
    
    /**
     * Upload DICOM file to Orthanc and save metadata to database
     * POST /api/v1/dicom/upload
     * 
     * @param file DICOM file (.dcm)
     * @param patientId Patient ID (required)
     * @param examinationId Examination ID (optional)
     * @param treatmentPhaseId Treatment Phase ID (optional)
     * @return DicomStudyResponse
     */
    @PreAuthorize("hasAuthority('UPLOAD_DICOM')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponses<DicomStudyResponse> uploadDicom(
            @RequestPart("file") MultipartFile file,
            @RequestPart("patientId") String patientId,
            @RequestPart(value = "examinationId", required = false) String examinationId,
            @RequestPart(value = "treatmentPhaseId", required = false) String treatmentPhaseId
    ) throws IOException {
        try {
            DicomUploadRequest request = DicomUploadRequest.builder()
                .file(file)
                .patientId(patientId)
                .examinationId(examinationId)
                .treatmentPhaseId(treatmentPhaseId)
                .build();
            
            DicomStudyResponse response = dicomService.uploadDicom(request);
            return ApiResponses.<DicomStudyResponse>builder()
                .code(1000)
                .result(response)
                .build();
        } catch (Exception e) {
            // Log full stack trace for debugging
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get DICOM study by ID
     * GET /api/v1/dicom/studies/{studyId}
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    @GetMapping("/studies/{studyId}")
    public ApiResponses<DicomStudyResponse> getStudyById(@PathVariable String studyId) {
        DicomStudyResponse response = dicomService.getStudyById(studyId);
        return ApiResponses.<DicomStudyResponse>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Get all DICOM studies for a patient
     * GET /api/v1/dicom/patients/{patientId}/studies
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    @GetMapping("/patients/{patientId}/studies")
    public ApiResponses<List<DicomStudyResponse>> getStudiesByPatientId(@PathVariable String patientId) {
        List<DicomStudyResponse> response = dicomService.getStudiesByPatientId(patientId);
        return ApiResponses.<List<DicomStudyResponse>>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Get DICOM studies for an examination
     * GET /api/v1/dicom/examinations/{examinationId}/studies
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    @GetMapping("/examinations/{examinationId}/studies")
    public ApiResponses<List<DicomStudyResponse>> getStudiesByExaminationId(@PathVariable String examinationId) {
        List<DicomStudyResponse> response = dicomService.getStudiesByExaminationId(examinationId);
        return ApiResponses.<List<DicomStudyResponse>>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Get DICOM studies for a treatment phase
     * GET /api/v1/dicom/treatment-phases/{treatmentPhaseId}/studies
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    @GetMapping("/treatment-phases/{treatmentPhaseId}/studies")
    public ApiResponses<List<DicomStudyResponse>> getStudiesByTreatmentPhaseId(@PathVariable String treatmentPhaseId) {
        List<DicomStudyResponse> response = dicomService.getStudiesByTreatmentPhaseId(treatmentPhaseId);
        return ApiResponses.<List<DicomStudyResponse>>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Delete DICOM study
     * DELETE /api/v1/dicom/studies/{studyId}
     */
    @PreAuthorize("hasAuthority('DELETE_DICOM')")
    @DeleteMapping("/studies/{studyId}")
    public ApiResponses<String> deleteStudy(@PathVariable String studyId) {
        dicomService.deleteStudy(studyId);
        return ApiResponses.<String>builder()
            .code(1000)
            .result("DICOM study đã được xóa thành công")
            .build();
    }
    
    /**
     * Get DICOM file from Orthanc (proxy endpoint to avoid CORS)
     * GET /api/v1/dicom/instances/{instanceId}/file?frame={frameIndex}
     * 
     * Supports multi-frame DICOM: frame index can be passed as query parameter
     * Example: "/instances/563a1a6f.../file?frame=0" -> extracts base instanceId and frame index
     * 
     * @param instanceId Orthanc instance ID (base ID, without frame index)
     * @param frameIndex Optional frame index for multi-frame DICOM (0-based)
     * @return DICOM file as byte array
     */
    @PreAuthorize("hasAuthority('VIEW_DICOM')")
    @GetMapping(value = "/instances/{instanceId}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getInstanceFile(
            @PathVariable String instanceId,
            @RequestParam(value = "frame", required = false) Integer frameIndex) {
        // instanceId from path variable is already the base ID (Spring doesn't parse # in path)
        // Frame index is passed as query parameter
        byte[] fileData = dicomService.getInstanceFile(instanceId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/dicom"));
        headers.setContentLength(fileData.length);
        headers.set("Content-Disposition", "inline; filename=\"" + instanceId + ".dcm\"");
        // CORS headers are handled by global SecurityConfig, no need to set here
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(fileData);
    }
}

