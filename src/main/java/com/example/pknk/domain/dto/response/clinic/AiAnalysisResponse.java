package com.example.pknk.domain.dto.response.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AiAnalysisResponse {
    
    String id;
    String dicomInstanceId;
    String imageId;
    Double confidenceThreshold;
    Integer totalDetections;
    Integer imageWidth;
    Integer imageHeight;
    String analysisStatus;
    String errorMessage;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<AiDetectionResponse> detections;
}


