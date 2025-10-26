package com.example.pknk.domain.entity.user;

import com.example.pknk.domain.entity.clinic.Appointment;
import com.example.pknk.domain.entity.clinic.TreatmentPlans;
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
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String emergencyContactName;
    String emergencyPhoneNumber;

    String bloodGroup;
    String allergy;
    String medicalHistory;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<Appointment> listAppointment = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<TreatmentPlans> listTreatmentPlans = new ArrayList<>();

//    @OneToMany(mappedBy = "patient_id")
//    List<MedicalHistory> medicalHistory = new ArrayList();
}
