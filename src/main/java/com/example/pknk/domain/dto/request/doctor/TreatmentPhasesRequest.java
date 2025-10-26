package com.example.pknk.domain.dto.request.doctor;

import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreatmentPhasesRequest {

    String phaseNumber;
    String description;
    String startDate;
    String endDate;
    String nextAppointment;

    List<DentalServicesEntity> listDentalServiceEntity = new ArrayList<>();

    List<MultipartFile> listImageFile;}
