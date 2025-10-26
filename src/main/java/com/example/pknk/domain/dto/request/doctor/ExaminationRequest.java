package com.example.pknk.domain.dto.request.doctor;

import com.example.pknk.domain.entity.clinic.Appointment;
import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
public class ExaminationRequest {

    String symptoms;        //triệu chứng
    String diagnosis;       //chẩn đoán
    String notes;
    String treatment;

    List<MultipartFile> listImageFile;

    List<DentalServicesEntity> listDentalServicesEntity = new ArrayList<>();
}
