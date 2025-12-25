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
public class DicomStudyResponse {
    
    String id;
    String patientId;
    String patientName;
    String examinationId;
    String treatmentPhaseId;
    String orthancStudyId;
    String studyInstanceUID;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate studyDate;
    
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime studyTime;
    
    String studyDescription;
    String accessionNumber;
    String modality;
    String referringPhysicianName;
    String patientIdDicom;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate patientBirthDate;
    
    String patientSex;
    Integer numberOfStudyRelatedSeries;
    Integer numberOfStudyRelatedInstances;
    
    List<DicomSeriesResponse> series; // List of series in this study
}


