package com.example.pknk.domain.dto.response.patient;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToothResponse {

    String id;
    String toothNumber;
    String status;

}
