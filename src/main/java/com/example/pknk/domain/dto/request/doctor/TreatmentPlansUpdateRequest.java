package com.example.pknk.domain.dto.request.doctor;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPlansUpdateRequest {

    String title;
    String description;
    String duration;
    String notes;
    String status;
    String nurseId;

}
