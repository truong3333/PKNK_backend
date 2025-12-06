package com.example.pknk.domain.entity.clinic;

import com.example.pknk.domain.entity.user.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;
}
