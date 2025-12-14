package com.example.pknk.domain.dto.response.doctor;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    String phone;
    String email;
    String address;
    String gender;
    LocalDate dob;
    String licenseNumber;
    Integer yearsExperience;
}
