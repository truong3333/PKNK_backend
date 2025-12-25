package com.example.pknk.domain.dto.response.clinic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DicomInstanceResponse {
    
    String id;
    String seriesId;
    String orthancInstanceId;
    String sopInstanceUID;
    String sopClassUID;
    Integer instanceNumber;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate contentDate;
    
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime contentTime;
    
    String imageType;
    Integer rows;
    Integer columns;
    Integer bitsAllocated;
    Integer bitsStored;
    Integer samplesPerPixel;
    String photometricInterpretation;
    Long fileSize;
    String filePath;
}


