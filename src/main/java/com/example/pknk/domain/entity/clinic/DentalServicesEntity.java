package com.example.pknk.domain.entity.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DentalServicesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;
    String description;
    double price;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;
}
