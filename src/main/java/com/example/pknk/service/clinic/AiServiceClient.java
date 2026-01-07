package com.example.pknk.service.clinic;

import com.example.pknk.configuration.AiServiceConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AiServiceClient {
    
    AiServiceConfig aiServiceConfig;
    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    
    public AiServiceClient(AiServiceConfig aiServiceConfig, 
                          @Qualifier("aiServiceRestTemplate") RestTemplate restTemplate) {
        this.aiServiceConfig = aiServiceConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }
    
    public AiAnalysisResult analyzeImage(MultipartFile file, Double confidenceThreshold) {
        if (!aiServiceConfig.isEnabled()) {
            log.warn("AI Service is disabled");
            return null;
        }
        
        try {
            String url = aiServiceConfig.getBaseUrl() + "/analyze";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            try {
                ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };
                body.add("file", resource);
            } catch (Exception e) {
                log.error("Error reading file bytes: {}", e.getMessage());
                throw new RuntimeException("Failed to read file", e);
            }
            if (confidenceThreshold != null) {
                body.add("confidence", confidenceThreshold.toString());
            }
            
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            log.info("Calling AI Service at: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                return parseAnalysisResult(jsonResponse);
            } else {
                log.error("AI Service returned error: {}", response.getStatusCode());
                return null;
            }
            
        } catch (Exception e) {
            log.error("Error calling AI Service: {}", e.getMessage(), e);
            return null;
        }
    }
    
    public boolean isServiceHealthy() {
        if (!aiServiceConfig.isEnabled()) {
            return false;
        }
        
        try {
            String url = aiServiceConfig.getBaseUrl() + "/health";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                return jsonResponse.has("model_loaded") && jsonResponse.get("model_loaded").asBoolean();
            }
            
            return false;
        } catch (Exception e) {
            log.warn("AI Service health check failed: {}", e.getMessage());
            return false;
        }
    }
    
    private AiAnalysisResult parseAnalysisResult(JsonNode jsonNode) {
        AiAnalysisResult result = new AiAnalysisResult();
        
        if (jsonNode.has("total_detections")) {
            result.setTotalDetections(jsonNode.get("total_detections").asInt());
        }
        
        if (jsonNode.has("image_width")) {
            result.setImageWidth(jsonNode.get("image_width").asInt());
        }
        
        if (jsonNode.has("image_height")) {
            result.setImageHeight(jsonNode.get("image_height").asInt());
        }
        
        List<AiDetectionData> detections = new ArrayList<>();
        if (jsonNode.has("detections") && jsonNode.get("detections").isArray()) {
            for (JsonNode detectionNode : jsonNode.get("detections")) {
                AiDetectionData detection = new AiDetectionData();
                
                if (detectionNode.has("class_id")) {
                    detection.setClassId(detectionNode.get("class_id").asInt());
                }
                
                if (detectionNode.has("class_name")) {
                    detection.setClassName(detectionNode.get("class_name").asText());
                }
                
                if (detectionNode.has("confidence")) {
                    detection.setConfidence(detectionNode.get("confidence").asDouble());
                }
                
                if (detectionNode.has("bounding_box")) {
                    JsonNode bbox = detectionNode.get("bounding_box");
                    if (bbox.has("x_min")) detection.setXMin(bbox.get("x_min").asDouble());
                    if (bbox.has("y_min")) detection.setYMin(bbox.get("y_min").asDouble());
                    if (bbox.has("x_max")) detection.setXMax(bbox.get("x_max").asDouble());
                    if (bbox.has("y_max")) detection.setYMax(bbox.get("y_max").asDouble());
                }
                
                detections.add(detection);
            }
        }
        
        result.setDetections(detections);
        return result;
    }
    
    // Inner classes for parsing
    @lombok.Data
    public static class AiAnalysisResult {
        private Integer totalDetections;
        private Integer imageWidth;
        private Integer imageHeight;
        private List<AiDetectionData> detections = new ArrayList<>();
    }
    
    @lombok.Data
    public static class AiDetectionData {
        private Integer classId;
        private String className;
        private Double confidence;
        private Double xMin;
        private Double yMin;
        private Double xMax;
        private Double yMax;
    }
}

