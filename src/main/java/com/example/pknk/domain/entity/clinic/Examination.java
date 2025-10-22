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
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String symptoms;
    String diagnosis;
    String notes;
    String examined_at;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;
}
