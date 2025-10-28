package com.example.pknk.domain.dto.request.clinic;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DentalServicesEntityOrderRequest {

    String name;
    String unit;
    double unitPrice;
    int quantity;
    double cost;

}
