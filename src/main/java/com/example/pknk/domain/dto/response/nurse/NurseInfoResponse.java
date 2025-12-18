package com.example.pknk.domain.dto.response.nurse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NurseInfoResponse {
    String id;
    String fullName;
    String phone;
    String email;
    String address;
    String gender;
    String dob;
}
