package com.example.pknk.domain.dto.response.patient;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientResponse {

    String fullName;
    String email;
    String phone;
    String address;
    String gender;
    LocalDate dob;

    String emergencyContactName;
    String emergencyPhoneNumber;

    String bloodGroup;
    String allergy;
    String medicalHistory;
}
