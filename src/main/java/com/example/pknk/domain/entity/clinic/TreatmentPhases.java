package com.example.pknk.domain.entity.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionOrderRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
public class TreatmentPhases {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String phaseNumber;
    String description;
    double cost;
    String status;

    @ElementCollection
    List<String> listComment = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate endDate;

    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
     LocalDateTime nextAppointment;

    @ElementCollection
    List<DentalServicesEntityOrderRequest> listDentalServiceEntityOrder = new ArrayList<>();

    @ElementCollection
    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();

    @OneToMany(mappedBy = "treatmentPhases", cascade = CascadeType.ALL)
    @Builder.Default
    List<Image> listImage = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "treatmentPlans_id")
    TreatmentPlans treatmentPlans;
}
