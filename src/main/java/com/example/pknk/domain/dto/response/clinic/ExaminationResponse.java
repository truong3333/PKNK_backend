package com.example.pknk.domain.dto.response.clinic;

import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
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

    List<ImageResponse> listImage = new ArrayList<>();

    List<DentalServicesEntity> listDentalServicesEntity = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate createAt;

}
