package com.example.pknk.domain.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @Size(min = 8,message = "USERNAME_INVALID")
    String username;
    @Size(min = 8,message = "PASSWORD_INVALID")
    String password;
    String fullName;
    String email;
    String phone;
    String address;
    String gender;
    LocalDate dob;
    LocalDate createAt;

    String verifiedCode;
}
