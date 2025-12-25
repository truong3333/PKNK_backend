package com.example.pknk.service.clinic;

import com.example.pknk.configuration.OrthancConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service to interact with Orthanc PACS server via REST API
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrthancClientService {
    
    OrthancConfig orthancConfig;
    RestTemplate restTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Upload DICOM file to Orthanc
     * @param file DICOM file to upload
     * @return Orthanc instance ID
     */
    public String uploadDicomFile(MultipartFile file) throws IOException {
        if (!orthancConfig.isEnabled()) {
            throw new IllegalStateException("Orthanc integration is disabled");
        }
        
        String url = orthancConfig.getBaseUrl() + "/instances";
        
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.parseMediaType("application/dicom"));
        headers.setContentLength(file.getSize());
        
        // Use InputStreamResource for streaming large files instead of loading entire file into memory
        InputStreamResource resource = new InputStreamResource(file.getInputStream()) {
            @Override
            public long contentLength() {
                return file.getSize();
            }
        };
        
        HttpEntity<InputStreamResource> request = new HttpEntity<>(resource, headers);
        
        try {
            log.info("Uploading DICOM file to Orthanc. File size: {} bytes", file.getSize());
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String responseBody = response.getBody().trim();
                log.info("Orthanc upload response body (length: {}): {}", responseBody.length(), responseBody);
                
                if (responseBody.isEmpty()) {
                    log.error("Orthanc returned empty response body");
                    throw new RuntimeException("Orthanc returned empty response - upload may have failed");
                }
                
                JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(responseBody);
                } catch (Exception e) {
                    // If response is not JSON, treat it as plain string (instance ID)
                    log.info("Orthanc returned plain string instance ID: {}", responseBody);
                    if (responseBody.isEmpty()) {
                        throw new RuntimeException("Orthanc returned empty instance ID");
                    }
                    return responseBody;
                }
                
                // Handle JSON response - Orthanc may return {"ID": "instance-id"} or just the ID as string
                String instanceId = null;
                if (jsonNode.isTextual()) {
                    // Response is a JSON string value (e.g., "abc123")
                    instanceId = jsonNode.asText();
                } else if (jsonNode.has("ID")) {
                    // Response is a JSON object with "ID" field
                    instanceId = jsonNode.get("ID").asText();
                } else if (jsonNode.isObject() && jsonNode.size() > 0) {
                    // Response is a JSON object - try to get first text value
                    var fields = jsonNode.fields();
                    if (fields.hasNext()) {
                        var entry = fields.next();
                        JsonNode value = entry.getValue();
                        if (value.isTextual()) {
                            instanceId = value.asText();
                        }
                    }
                } else if (jsonNode.isArray() && jsonNode.size() > 0) {
                    // Response is a JSON array - get first element
                    JsonNode firstElement = jsonNode.get(0);
                    if (firstElement.isTextual()) {
                        instanceId = firstElement.asText();
                    } else if (firstElement.has("ID")) {
                        instanceId = firstElement.get("ID").asText();
                    }
                }
                
                if (instanceId == null || instanceId.isEmpty()) {
                    log.error("Failed to extract instance ID from Orthanc response. Response body: {}", responseBody);
                    log.error("JSON node type: {}, isTextual: {}, isObject: {}, isArray: {}", 
                        jsonNode.getNodeType(), jsonNode.isTextual(), jsonNode.isObject(), jsonNode.isArray());
                    throw new RuntimeException("Failed to extract instance ID from Orthanc response: " + responseBody);
                }
                
                log.info("DICOM file uploaded successfully to Orthanc. Instance ID: {}", instanceId);
                return instanceId;
            } else {
                String errorBody = response.getBody() != null ? response.getBody() : "null";
                log.error("Failed to upload DICOM file to Orthanc. Status: {}, Body: {}", response.getStatusCode(), errorBody);
                throw new RuntimeException("Failed to upload DICOM file to Orthanc. Status: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            log.error("Error uploading DICOM file to Orthanc: Status={}, Message={}", e.getStatusCode(), e.getMessage());
            throw new RuntimeException("Failed to upload DICOM file to Orthanc: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error uploading DICOM file to Orthanc: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to upload DICOM file to Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get instance information from Orthanc
     */
    public JsonNode getInstanceInfo(String instanceId) {
        String url = orthancConfig.getBaseUrl() + "/instances/" + instanceId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            } else {
                throw new RuntimeException("Failed to get instance info from Orthanc");
            }
        } catch (Exception e) {
            log.error("Error getting instance info from Orthanc: {}", e.getMessage());
            throw new RuntimeException("Failed to get instance info from Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get instance tags (DICOM metadata)
     */
    public JsonNode getInstanceTags(String instanceId) {
        String url = orthancConfig.getBaseUrl() + "/instances/" + instanceId + "/tags";
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            } else {
                throw new RuntimeException("Failed to get instance tags from Orthanc");
            }
        } catch (Exception e) {
            log.error("Error getting instance tags from Orthanc: {}", e.getMessage());
            throw new RuntimeException("Failed to get instance tags from Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get study information
     */
    public JsonNode getStudyInfo(String studyId) {
        String url = orthancConfig.getBaseUrl() + "/studies/" + studyId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            } else {
                throw new RuntimeException("Failed to get study info from Orthanc");
            }
        } catch (Exception e) {
            log.error("Error getting study info from Orthanc: {}", e.getMessage());
            throw new RuntimeException("Failed to get study info from Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get series information
     */
    public JsonNode getSeriesInfo(String seriesId) {
        String url = orthancConfig.getBaseUrl() + "/series/" + seriesId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            } else {
                throw new RuntimeException("Failed to get series info from Orthanc");
            }
        } catch (Exception e) {
            log.error("Error getting series info from Orthanc: {}", e.getMessage());
            throw new RuntimeException("Failed to get series info from Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get all instance IDs from a series in Orthanc
     * Note: Orthanc may return instances in "Instances" array or we may need to query from study level
     * @param seriesId Orthanc series ID
     * @return List of Orthanc instance IDs
     */
    public List<String> getSeriesInstances(String seriesId) {
        // First, get series info to find the study ID
        String seriesUrl = orthancConfig.getBaseUrl() + "/series/" + seriesId;
        log.info("Fetching instances from Orthanc for series: {}", seriesId);
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        try {
            // Get series info first
            ResponseEntity<String> seriesResponse = restTemplate.exchange(
                seriesUrl,
                HttpMethod.GET,
                request,
                String.class
            );
            
            if (!seriesResponse.getStatusCode().is2xxSuccessful() || seriesResponse.getBody() == null) {
                throw new RuntimeException("Failed to get series info from Orthanc");
            }
            
            JsonNode seriesInfo = objectMapper.readTree(seriesResponse.getBody());
            
            // Log full response for debugging (first 1000 chars)
            String responseBody = seriesResponse.getBody();
            log.info("Full Orthanc series response (length: {}): {}", 
                responseBody.length(), 
                responseBody.length() > 1000 ? responseBody.substring(0, 1000) + "..." : responseBody);
            
            // Log all available keys
            List<String> availableKeys = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(seriesInfo.fieldNames(), 0), false)
                .collect(Collectors.toList());
            log.info("Available keys in series response: {}", availableKeys);
            
            // Get study ID from series
            String studyId = null;
            if (seriesInfo.has("ParentStudy")) {
                studyId = seriesInfo.get("ParentStudy").asText();
                log.info("Found ParentStudy: {}", studyId);
            }
            
            // Try to get instances from series
            List<String> instanceIds = new ArrayList<>();
            if (seriesInfo.has("Instances") && seriesInfo.get("Instances").isArray()) {
                JsonNode instancesArray = seriesInfo.get("Instances");
                log.info("Found {} instances in series 'Instances' array", instancesArray.size());
                
                for (JsonNode instanceId : instancesArray) {
                    instanceIds.add(instanceId.asText());
                }
                
                // Log first few instance IDs
                if (instanceIds.size() > 0) {
                    int logCount = Math.min(3, instanceIds.size());
                    log.info("First {} instance IDs: {}", logCount, instanceIds.subList(0, logCount));
                    
                    // Check if this is a multi-frame DICOM (1 instance with multiple frames)
                    // Query the first instance to check for NumberOfFrames
                    String firstInstanceId = instanceIds.get(0);
                    try {
                        JsonNode instanceInfo = getInstanceInfo(firstInstanceId);
                        if (instanceInfo.has("MainDicomTags")) {
                            JsonNode mainTags = instanceInfo.get("MainDicomTags");
                            if (mainTags.has("NumberOfFrames")) {
                                int numberOfFrames = mainTags.get("NumberOfFrames").asInt();
                                log.info("Instance {} is a multi-frame DICOM with {} frames", firstInstanceId, numberOfFrames);
                                
                                // For multi-frame DICOM, we need to create virtual instances for each frame
                                // Cornerstone.js can handle multi-frame by using frame index in imageId
                                // Format: wadouri:url#frame=0, wadouri:url#frame=1, etc.
                                // But for now, we'll return the single instance and let the viewer handle frames
                                log.info("This is a multi-frame DICOM. Frontend should handle frames using frame index in imageId.");
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Failed to check instance info for multi-frame: {}", e.getMessage());
                    }
                }
            } else {
                log.warn("Series response does not have 'Instances' array");
            }
            
            if (instanceIds.isEmpty()) {
                log.error("No instances found for series {}", seriesId);
                throw new RuntimeException("No instances found in Orthanc series");
            }
            
            log.info("Returning {} instance IDs for series {}", instanceIds.size(), seriesId);
            return instanceIds;
        } catch (Exception e) {
            log.error("Error getting series instances from Orthanc for series {}: {}", seriesId, e.getMessage(), e);
            throw new RuntimeException("Failed to get series instances from Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get DICOM file from Orthanc (for download)
     */
    public byte[] getDicomFile(String instanceId) {
        String url = orthancConfig.getBaseUrl() + "/instances/" + instanceId + "/file";
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                byte[].class
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to get DICOM file from Orthanc");
            }
        } catch (Exception e) {
            log.error("Error getting DICOM file from Orthanc: {}", e.getMessage());
            throw new RuntimeException("Failed to get DICOM file from Orthanc: " + e.getMessage(), e);
        }
    }
    
    /**
     * Create HTTP headers with authentication if configured
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        
        if (orthancConfig.getUsername() != null && !orthancConfig.getUsername().isEmpty() &&
            orthancConfig.getPassword() != null && !orthancConfig.getPassword().isEmpty()) {
            String auth = orthancConfig.getUsername() + ":" + orthancConfig.getPassword();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            headers.set("Authorization", authHeader);
        }
        
        return headers;
    }
}

