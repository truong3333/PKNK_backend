package com.example.pknk.domain.dto.request.doctor;

import com.example.pknk.domain.entity.clinic.TreatmentPhases;
import com.example.pknk.domain.entity.user.Patient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPlansRequest {

    String title;
    String description;
    String duration;
    String notes;
    String examinationId;
    String nurseId;
    String doctorId; // Optional - only for DOCTORLV2 with PICK_DOCTOR permission

}
