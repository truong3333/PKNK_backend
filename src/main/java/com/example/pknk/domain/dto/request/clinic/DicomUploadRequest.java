package com.example.pknk.domain.dto.request.clinic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DicomUploadRequest {
    
    @NotNull(message = "File không được để trống")
    MultipartFile file;
    
    @NotBlank(message = "Patient ID không được để trống")
    String patientId;
    
    String examinationId; // Optional - link với examination nếu có
    
    String treatmentPhaseId; // Optional - link với treatment phase nếu có
}


