package com.example.pknk.domain.dto.request.doctor;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityOrderRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionOrderRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExaminationUpdateRequest {

    String symptoms;        //triệu chứng
    String diagnosis;       //chẩn đoán
    String notes;
    String treatment;
    double totalCost;

    List<MultipartFile> listImageFile;

    List<String> listDeleteImageByPublicId;

    List<DentalServicesEntityOrderRequest> listDentalServicesEntityOrder = new ArrayList<>();

    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();
}
