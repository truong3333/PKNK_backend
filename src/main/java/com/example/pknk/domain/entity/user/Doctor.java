package com.example.pknk.domain.entity.user;

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
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String specialization;
    String licenseNumber;
    int yearsExperience;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
