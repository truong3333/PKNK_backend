package com.example.pknk.domain.entity.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionOrderRequest;
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
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String symptoms;        //triệu chứng
    String diagnosis;       //chẩn đoán
    String notes;
    String treatment;
    String examined_at;
    double totalCost;
    List<String> listComment = new ArrayList<>();

    @ElementCollection
    List<DentalServicesEntityOrderRequest> listDentalServicesEntityOrder = new ArrayList<>();

    @ElementCollection
    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    @Builder.Default
    List<Image> listImage = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;
}
