package com.example.pknk.domain.dto.request.patient;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmergencyContactRequest {

    String emergencyContactName;
    String emergencyPhoneNumber;
}
