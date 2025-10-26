package com.example.pknk.domain.dto.request.doctor;

import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
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
    double cost;

    List<MultipartFile> listImageFile;

    List<String> listDeleteImageByPublicId;

    List<DentalServicesEntity> listDentalServicesEntity = new ArrayList<>();

}
