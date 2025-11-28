package com.example.pknk.domain.entity.clinic;

import com.example.pknk.domain.entity.user.Patient;
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
public class Tooth {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String toothNumber;
    String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;
}
