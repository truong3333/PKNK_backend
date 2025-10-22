package com.example.pknk.domain.dto.response.clinic;

import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
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
public class AppointmentResponse {

    String dateTime;
    String type;
    String notes;
    String status;

    List<DentalServicesEntity> listDentalServicesEntity = new ArrayList<>();

    String doctorFullName;
    String doctorSpecialization;

}
