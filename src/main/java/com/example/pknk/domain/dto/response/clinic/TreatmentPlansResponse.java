package com.example.pknk.domain.dto.response.clinic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPlansResponse {

    String id;
    String title;
    String description;
    String duration;
    String notes;
    String status;
    double totalCost;
    String doctorId;
    String doctorFullname; // Tên bác sĩ
    String nurseId;
    String nurseFullname; // Tên y tá
    String patientId;
    String patientName; // Tên bệnh nhân

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate createAt;

}
