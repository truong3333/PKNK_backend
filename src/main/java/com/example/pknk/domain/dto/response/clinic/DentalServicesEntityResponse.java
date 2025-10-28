package com.example.pknk.domain.dto.response.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DentalServicesEntityResponse {

    String id;
    String name;
    String unit;
    double unitPrice;

}
