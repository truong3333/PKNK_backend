package com.example.pknk.controller.doctor;

import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.doctor.DoctorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DoctorController {
    DoctorService doctorService;

    @GetMapping("/appointment/scheduled/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAppointmentScheduledOfDoctor(@PathVariable String doctorId){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAppointmentScheduledOfDoctor(doctorId))
                .build();
    }

    @GetMapping("/appointment/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAllAppointmentOfDoctor(@PathVariable String doctorId){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAllAppointmentOfDoctor(doctorId))
                .build();
    }

    @GetMapping("/myAppointment/scheduled")
    ApiResponses<List<AppointmentResponse>> getAppointmentScheduledOfMyDoctor(){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAppointmentScheduledOfMyDoctor())
                .build();
    }

    @GetMapping("/myAppointment")
    ApiResponses<List<AppointmentResponse>> getAllAppointmentScheduledOfMyDoctor(){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAllAppointmentScheduledOfMyDoctor())
                .build();
    }
}
