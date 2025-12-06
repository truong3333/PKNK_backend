package com.example.pknk.domain.dto.response.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionOrderRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ExaminationResponse {

    String id;
    String symptoms;        //triệu chứng
    String diagnosis;       //chẩn đoán
    String notes;
    String treatment;
    String examined_at;
    double totalCost;
    List<String> listComment = new ArrayList<>();

    List<ImageResponse> listImage = new ArrayList<>();

    List<DentalServicesEntityOrderRequest> listDentalServicesEntityOrder = new ArrayList<>();

    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate createAt;

}
