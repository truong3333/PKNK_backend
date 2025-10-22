package com.example.pknk.domain.entity.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPlans {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;
    String description;
    double totalCost;
    String status;

    @OneToMany(mappedBy = "treatmentPlans", cascade = CascadeType.ALL)
    List<TreatmentPhases> listTreatmentPhases = new ArrayList<>();
}
