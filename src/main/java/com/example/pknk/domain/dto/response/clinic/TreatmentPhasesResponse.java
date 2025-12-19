package com.example.pknk.domain.dto.response.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionOrderRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPhasesResponse {

    String id;
    String phaseNumber;
    String description;
    double cost;
    String status;
    String paymentStatus;
    List<String> listComment = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate endDate;

    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    LocalDateTime nextAppointment;

    List<DentalServicesEntityOrderRequest> listDentalServicesEntityOrder = new ArrayList<>();

    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();

    List<ImageResponse> listImage = new ArrayList<>();
}
