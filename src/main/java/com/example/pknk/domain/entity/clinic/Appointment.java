package com.example.pknk.domain.entity.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.entity.user.Doctor;
import com.example.pknk.domain.entity.user.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    LocalDateTime dateTime;
    String status;
    String type;
    String notes;
    String notification;

    @ElementCollection
    @CollectionTable(
        name = "appointment_list_dental_services_entity",
        joinColumns = @JoinColumn(name = "appointment_id")
    )
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "list_dental_services_entity_id"))
    })
    List<DentalServicesEntityOrderRequest> listDentalServicesEntity = new ArrayList<>();

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    Examination examination;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;
}
