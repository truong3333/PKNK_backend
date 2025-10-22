package com.example.pknk.domain.entity.user;

import com.example.pknk.domain.entity.clinic.Appointment;
import com.example.pknk.domain.entity.clinic.BookingDateTime;
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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    List<Appointment> listAppointment = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    List<BookingDateTime> listBookingDateTime = new ArrayList<>();
}
