package com.example.pknk.domain.dto.response.nurse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NursePickResponse {
    String id;
    String fullName;
}
