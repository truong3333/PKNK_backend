package com.example.pknk.domain.dto.request.patient;

import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
import com.example.pknk.domain.entity.clinic.Examination;
import com.example.pknk.domain.entity.user.Doctor;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequest {

    String dateTime;
    String type;
    String notes;

    List<DentalServicesEntity> listDentalServicesEntity = new ArrayList<>();

    String doctorId;

}
