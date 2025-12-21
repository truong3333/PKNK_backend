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

    String id; // ID của DentalServicesEntity để map vào list_dental_services_entity_id (mapped via @AttributeOverride)
    String name;
    String unit;
    Double unitPrice; // Changed from double to Double to allow null values
    Integer quantity; // Changed from int to Integer to allow null values
    Double cost; // Changed from double to Double to allow null values

}
