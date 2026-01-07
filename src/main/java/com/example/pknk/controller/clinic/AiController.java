package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.response.clinic.AiAnalysisResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.AiService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AiController {
    
    AiService aiService;
    
    /**
     * Analyze image file
     * POST /api/v1/ai/analyze
     */
    @PreAuthorize("hasAuthority('ANALYZE_IMAGE')")
    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponses<AiAnalysisResponse> analyzeImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "dicomInstanceId", required = false) String dicomInstanceId,
            @RequestParam(value = "imageId", required = false) String imageId,
            @RequestParam(value = "confidence", required = false) 
            @Min(0) @Max(1) Double confidence
    ) {
        AiAnalysisResponse response = aiService.analyzeImageFile(file, dicomInstanceId, imageId, confidence);
        return ApiResponses.<AiAnalysisResponse>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Analyze DICOM instance
     * POST /api/v1/ai/dicom-instances/{dicomInstanceId}/analyze
     */
    @PreAuthorize("hasAuthority('ANALYZE_IMAGE')")
    @PostMapping("/dicom-instances/{dicomInstanceId}/analyze")
    public ApiResponses<AiAnalysisResponse> analyzeDicomInstance(
            @PathVariable String dicomInstanceId,
            @RequestParam(value = "confidence", required = false) 
            @Min(0) @Max(1) Double confidence
    ) {
        AiAnalysisResponse response = aiService.analyzeDicomInstance(dicomInstanceId, confidence);
        return ApiResponses.<AiAnalysisResponse>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Analyze image
     * POST /api/v1/ai/images/{imageId}/analyze
     */
    @PreAuthorize("hasAuthority('ANALYZE_IMAGE')")
    @PostMapping("/images/{imageId}/analyze")
    public ApiResponses<AiAnalysisResponse> analyzeImageById(
            @PathVariable String imageId,
            @RequestParam(value = "confidence", required = false) 
            @Min(0) @Max(1) Double confidence
    ) {
        AiAnalysisResponse response = aiService.analyzeImage(imageId, confidence);
        return ApiResponses.<AiAnalysisResponse>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Get analysis by ID
     * GET /api/v1/ai/analyses/{analysisId}
     */
    @PreAuthorize("hasAuthority('VIEW_AI_ANALYSIS')")
    @GetMapping("/analyses/{analysisId}")
    public ApiResponses<AiAnalysisResponse> getAnalysisById(@PathVariable String analysisId) {
        AiAnalysisResponse response = aiService.getAnalysisById(analysisId);
        return ApiResponses.<AiAnalysisResponse>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Get analyses for DICOM instance
     * GET /api/v1/ai/dicom-instances/{dicomInstanceId}/analyses
     */
    @PreAuthorize("hasAuthority('VIEW_AI_ANALYSIS')")
    @GetMapping("/dicom-instances/{dicomInstanceId}/analyses")
    public ApiResponses<List<AiAnalysisResponse>> getAnalysesByDicomInstanceId(@PathVariable String dicomInstanceId) {
        List<AiAnalysisResponse> response = aiService.getAnalysesByDicomInstanceId(dicomInstanceId);
        return ApiResponses.<List<AiAnalysisResponse>>builder()
            .code(1000)
            .result(response)
            .build();
    }
    
    /**
     * Get analyses for image
     * GET /api/v1/ai/images/{imageId}/analyses
     */
    @PreAuthorize("hasAuthority('VIEW_AI_ANALYSIS')")
    @GetMapping("/images/{imageId}/analyses")
    public ApiResponses<List<AiAnalysisResponse>> getAnalysesByImageId(@PathVariable String imageId) {
        List<AiAnalysisResponse> response = aiService.getAnalysesByImageId(imageId);
        return ApiResponses.<List<AiAnalysisResponse>>builder()
            .code(1000)
            .result(response)
            .build();
    }
}

