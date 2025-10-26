package com.example.pknk.domain.entity.clinic;

import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.Patient;
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
    String duration;
    String notes;
    double totalCost;
    String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;

    @OneToMany(mappedBy = "treatmentPlans", cascade = CascadeType.ALL)
    List<TreatmentPhases> listTreatmentPhases = new ArrayList<>();
}
