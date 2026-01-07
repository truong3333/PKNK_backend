package com.example.pknk.service.clinic;

import com.example.pknk.configuration.AiServiceConfig;
import com.example.pknk.domain.dto.response.clinic.AiAnalysisResponse;
import com.example.pknk.domain.dto.response.clinic.AiDetectionResponse;
import com.example.pknk.domain.entity.clinic.AiAnalysis;
import com.example.pknk.domain.entity.clinic.AiDetection;
import com.example.pknk.domain.entity.clinic.DicomInstance;
import com.example.pknk.domain.entity.clinic.Image;
import com.example.pknk.exception.AppException;
import com.example.pknk.exception.ErrorCode;
import com.example.pknk.repository.clinic.AiAnalysisRepository;
import com.example.pknk.repository.clinic.AiDetectionRepository;
import com.example.pknk.repository.clinic.DicomInstanceRepository;
import com.example.pknk.repository.clinic.ImageRepository;
import com.example.pknk.service.clinic.AiServiceClient.AiAnalysisResult;
import com.example.pknk.service.clinic.AiServiceClient.AiDetectionData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AiService {
    
    AiServiceClient aiServiceClient;
    AiServiceConfig aiServiceConfig;
    AiAnalysisRepository aiAnalysisRepository;
    AiDetectionRepository aiDetectionRepository;
    DicomInstanceRepository dicomInstanceRepository;
    ImageRepository imageRepository;
    
    /**
     * Analyze DICOM instance using AI Service
     */
    @Transactional
    public AiAnalysisResponse analyzeDicomInstance(String dicomInstanceId, Double confidenceThreshold) {
        DicomInstance dicomInstance = dicomInstanceRepository.findById(dicomInstanceId)
            .orElseThrow(() -> new AppException(ErrorCode.DICOM_INSTANCE_NOT_EXISTED));
        
        // Check if analysis already exists
        AiAnalysis existingAnalysis = aiAnalysisRepository.findByDicomInstanceId(dicomInstanceId)
            .orElse(null);
        
        if (existingAnalysis != null && "COMPLETED".equals(existingAnalysis.getAnalysisStatus())) {
            log.info("Analysis already exists for DICOM instance: {}", dicomInstanceId);
            return mapToResponse(existingAnalysis);
        }
        
        // Get DICOM file from Orthanc
        // Note: This requires integration with OrthancClientService to get the file
        // For now, we'll create a placeholder analysis
        AiAnalysis analysis = AiAnalysis.builder()
            .dicomInstance(dicomInstance)
            .confidenceThreshold(confidenceThreshold != null ? confidenceThreshold : aiServiceConfig.getDefaultConfidenceThreshold())
            .analysisStatus("PENDING")
            .build();
        
        analysis = aiAnalysisRepository.save(analysis);
        
        // Perform async analysis
        performAnalysisAsync(analysis, dicomInstance.getOrthancInstanceId(), confidenceThreshold);
        
        return mapToResponse(analysis);
    }
    
    /**
     * Analyze image using AI Service
     */
    @Transactional
    public AiAnalysisResponse analyzeImage(String imageId, Double confidenceThreshold) {
        Image image = imageRepository.findById(imageId)
            .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_EXISTED));
        
        // Check if analysis already exists
        AiAnalysis existingAnalysis = aiAnalysisRepository.findByImageId(imageId)
            .orElse(null);
        
        if (existingAnalysis != null && "COMPLETED".equals(existingAnalysis.getAnalysisStatus())) {
            log.info("Analysis already exists for image: {}", imageId);
            return mapToResponse(existingAnalysis);
        }
        
        AiAnalysis analysis = AiAnalysis.builder()
            .image(image)
            .confidenceThreshold(confidenceThreshold != null ? confidenceThreshold : aiServiceConfig.getDefaultConfidenceThreshold())
            .analysisStatus("PENDING")
            .build();
        
        analysis = aiAnalysisRepository.save(analysis);
        
        // Perform async analysis
        performAnalysisAsync(analysis, image.getUrl(), confidenceThreshold);
        
        return mapToResponse(analysis);
    }
    
    /**
     * Analyze image file directly
     */
    @Transactional
    public AiAnalysisResponse analyzeImageFile(MultipartFile file, String dicomInstanceId, String imageId, Double confidenceThreshold) {
        if (!aiServiceConfig.isEnabled()) {
            throw new AppException(ErrorCode.AI_SERVICE_DISABLED);
        }
        
        // Check service health
        if (!aiServiceClient.isServiceHealthy()) {
            throw new AppException(ErrorCode.AI_SERVICE_UNAVAILABLE);
        }
        
        // Call AI Service
        AiAnalysisResult result = aiServiceClient.analyzeImage(file, confidenceThreshold);
        
        if (result == null) {
            throw new AppException(ErrorCode.AI_ANALYSIS_FAILED);
        }
        
        // Save analysis to database
        AiAnalysis analysis = AiAnalysis.builder()
            .confidenceThreshold(confidenceThreshold != null ? confidenceThreshold : aiServiceConfig.getDefaultConfidenceThreshold())
            .totalDetections(result.getTotalDetections())
            .imageWidth(result.getImageWidth())
            .imageHeight(result.getImageHeight())
            .analysisStatus("COMPLETED")
            .build();
        
        if (dicomInstanceId != null) {
            DicomInstance dicomInstance = dicomInstanceRepository.findById(dicomInstanceId)
                .orElse(null);
            if (dicomInstance != null) {
                analysis.setDicomInstance(dicomInstance);
            }
        }
        
        if (imageId != null) {
            Image image = imageRepository.findById(imageId)
                .orElse(null);
            if (image != null) {
                analysis.setImage(image);
            }
        }
        
        analysis = aiAnalysisRepository.save(analysis);
        
        // Save detections
        if (result.getDetections() != null) {
            for (AiDetectionData detectionData : result.getDetections()) {
                AiDetection detection = AiDetection.builder()
                    .aiAnalysis(analysis)
                    .classId(detectionData.getClassId())
                    .className(detectionData.getClassName())
                    .confidence(detectionData.getConfidence())
                    .xMin(detectionData.getXMin())
                    .yMin(detectionData.getYMin())
                    .xMax(detectionData.getXMax())
                    .yMax(detectionData.getYMax())
                    .build();
                
                aiDetectionRepository.save(detection);
            }
        }
        
        // Refresh to get detections
        analysis = aiAnalysisRepository.findById(analysis.getId())
            .orElseThrow(() -> new AppException(ErrorCode.AI_ANALYSIS_NOT_EXISTED));
        
        return mapToResponse(analysis);
    }
    
    /**
     * Get analysis by ID
     */
    public AiAnalysisResponse getAnalysisById(String analysisId) {
        AiAnalysis analysis = aiAnalysisRepository.findById(analysisId)
            .orElseThrow(() -> new AppException(ErrorCode.AI_ANALYSIS_NOT_EXISTED));
        
        return mapToResponse(analysis);
    }
    
    /**
     * Get analyses for DICOM instance
     */
    public List<AiAnalysisResponse> getAnalysesByDicomInstanceId(String dicomInstanceId) {
        List<AiAnalysis> analyses = aiAnalysisRepository.findByDicomInstanceIdOrderByCreatedAtDesc(dicomInstanceId);
        return analyses.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Get analyses for image
     */
    public List<AiAnalysisResponse> getAnalysesByImageId(String imageId) {
        List<AiAnalysis> analyses = aiAnalysisRepository.findByImageIdOrderByCreatedAtDesc(imageId);
        return analyses.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Perform analysis asynchronously
     */
    @Async
    public void performAnalysisAsync(AiAnalysis analysis, String fileIdentifier, Double confidenceThreshold) {
        try {
            log.info("Starting async analysis for analysis ID: {}", analysis.getId());
            
            // This is a placeholder - in real implementation, you would:
            // 1. Get the file from Orthanc or Cloudinary
            // 2. Convert to MultipartFile
            // 3. Call analyzeImageFile
            
            // For now, mark as failed
            analysis.setAnalysisStatus("FAILED");
            analysis.setErrorMessage("Async analysis not fully implemented yet");
            aiAnalysisRepository.save(analysis);
            
        } catch (Exception e) {
            log.error("Error in async analysis: {}", e.getMessage(), e);
            analysis.setAnalysisStatus("FAILED");
            analysis.setErrorMessage(e.getMessage());
            aiAnalysisRepository.save(analysis);
        }
    }
    
    private AiAnalysisResponse mapToResponse(AiAnalysis analysis) {
        List<AiDetection> detections = aiDetectionRepository.findByAiAnalysisId(analysis.getId());
        
        List<AiDetectionResponse> detectionResponses = detections.stream()
            .map(detection -> AiDetectionResponse.builder()
                .id(detection.getId())
                .classId(detection.getClassId())
                .className(detection.getClassName())
                .confidence(detection.getConfidence())
                .xMin(detection.getXMin())
                .yMin(detection.getYMin())
                .xMax(detection.getXMax())
                .yMax(detection.getYMax())
                .build())
            .collect(Collectors.toList());
        
        return AiAnalysisResponse.builder()
            .id(analysis.getId())
            .dicomInstanceId(analysis.getDicomInstance() != null ? analysis.getDicomInstance().getId() : null)
            .imageId(analysis.getImage() != null ? analysis.getImage().getId() : null)
            .confidenceThreshold(analysis.getConfidenceThreshold())
            .totalDetections(analysis.getTotalDetections())
            .imageWidth(analysis.getImageWidth())
            .imageHeight(analysis.getImageHeight())
            .analysisStatus(analysis.getAnalysisStatus())
            .errorMessage(analysis.getErrorMessage())
            .createdAt(analysis.getCreatedAt())
            .updatedAt(analysis.getUpdatedAt())
            .detections(detectionResponses)
            .build();
    }
}


