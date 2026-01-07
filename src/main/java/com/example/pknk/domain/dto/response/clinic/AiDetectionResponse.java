package com.example.pknk.domain.dto.response.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AiDetectionResponse {
    
    String id;
    Integer classId;
    String className;
    Double confidence;
    Double xMin;
    Double yMin;
    Double xMax;
    Double yMax;
}


