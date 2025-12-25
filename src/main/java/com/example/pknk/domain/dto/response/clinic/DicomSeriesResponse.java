package com.example.pknk.domain.dto.response.clinic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DicomSeriesResponse {
    
    String id;
    String studyId;
    String orthancSeriesId;
    String seriesInstanceUID;
    Integer seriesNumber;
    String seriesDescription;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate seriesDate;
    
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime seriesTime;
    
    String modality;
    String bodyPartExamined;
    String protocolName;
    Integer numberOfSeriesRelatedInstances;
    
    List<DicomInstanceResponse> instances; // List of instances in this series
}


