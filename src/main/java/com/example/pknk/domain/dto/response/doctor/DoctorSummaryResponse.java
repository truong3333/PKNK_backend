package com.example.pknk.domain.dto.response.doctor;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorSummaryResponse {

    String id;
    String fullName;
    String specialization;
}
