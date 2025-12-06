package com.example.pknk.domain.dto.response.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionOrderRequest;
import com.example.pknk.domain.entity.user.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CostResponse {

    String id;
    String title;
    String paymentMethod;
    String status;
    double totalCost;
    String vnpTxnRef;

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate paymentDate;

    List<DentalServicesEntityOrderRequest> listDentalServiceEntityOrder = new ArrayList<>();

    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();
}
