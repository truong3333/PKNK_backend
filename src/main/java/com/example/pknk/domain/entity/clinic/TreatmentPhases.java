package com.example.pknk.domain.entity.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPhases {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String phaseNumber;
    String description;
    double cost;
    String status;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate nextAppointment;

    @ManyToOne
    @JoinColumn(name = "treatmentPlans_id")
    TreatmentPlans treatmentPlans;
}
