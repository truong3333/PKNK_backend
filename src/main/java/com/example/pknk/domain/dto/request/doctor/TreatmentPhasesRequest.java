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
public class TreatmentPhasesRequest {

    String phaseNumber;
    String description;
    String startDate;
    String endDate;
    String nextAppointment;
    double cost;

    List<DentalServicesEntityOrderRequest> listDentalServicesEntityOrder = new ArrayList<>();

    List<PrescriptionOrderRequest> listPrescriptionOrder = new ArrayList<>();

    List<MultipartFile> listImageFile;}
