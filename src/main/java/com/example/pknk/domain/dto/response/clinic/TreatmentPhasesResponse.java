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
public class TreatmentPhasesResponse {

    String id;
    String phaseNumber;
    String description;
    double cost;
    String status;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate endDate;
    String nextAppointment;

    List<DentalServicesEntity> listDentalServiceEntity = new ArrayList<>();

    List<ImageResponse> listImage = new ArrayList<>();
}
