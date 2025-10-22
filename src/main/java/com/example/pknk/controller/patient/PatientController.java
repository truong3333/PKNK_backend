package com.example.pknk.controller.patient;

import com.example.pknk.domain.dto.request.patient.AppointmentRequest;
import com.example.pknk.domain.dto.request.patient.EmergencyContactRequest;
import com.example.pknk.domain.dto.request.patient.MedicalInformationRequest;
import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.patient.BookingDateTimeResponse;
import com.example.pknk.domain.dto.response.patient.EmergencyContactResponse;
import com.example.pknk.domain.dto.response.patient.MedicalInformationResponse;
import com.example.pknk.domain.dto.response.patient.PatientResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.patient.PatientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientController {
    PatientService patientService;

    @GetMapping("/{patientId}")
    ApiResponses<PatientResponse> getBasicInfo(@PathVariable String patientId){
        return ApiResponses.<PatientResponse>builder()
                .code(1000)
                .result(patientService.getBasicInfo(patientId))
                .build();
    }

    @PutMapping("/emergencyContact/{patientId}")
    ApiResponses<EmergencyContactResponse> updateEmergencyContact(@PathVariable String patientId, @RequestBody EmergencyContactRequest request){
        return ApiResponses.<EmergencyContactResponse>builder()
                .code(1000)
                .result(patientService.updateEmergencyContact(patientId, request))
                .build();
    }

    @PutMapping("/medicalInformation/{patientId}")
    ApiResponses<MedicalInformationResponse> updateMedicalInformation(@PathVariable String patientId, @RequestBody MedicalInformationRequest request){
        return  ApiResponses.<MedicalInformationResponse>builder()
                .code(1000)
                .result(patientService.updateMedicalInformation(patientId, request))
                .build();
    }

    @PostMapping("/appointment/booking")
    ApiResponses<AppointmentResponse> bookAppointment(@RequestBody AppointmentRequest request){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(patientService.bookingAppointment(request))
                .build();
    }

    @GetMapping("/appointment/bookingDateTime/{doctorId}")
    ApiResponses<List<BookingDateTimeResponse>> getAllBookingForPatient(@PathVariable String doctorId){
        return ApiResponses.<List<BookingDateTimeResponse>>builder()
                .code(1000)
                .result(patientService.getAllBookingOfDoctor(doctorId))
                .build();
    }

    @PutMapping("/appointment/booking/cancel/{appointmentId}")
    ApiResponses<AppointmentResponse> cancelBookingAppointment(@PathVariable String appointmentId){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(patientService.cancelBookingAppointment(appointmentId))
                .build();
    }

    @PutMapping("/appointment/booking/update/{appointmentId}")
    ApiResponses<AppointmentResponse> updateBookingAppointment(@PathVariable String appointmentId, @RequestBody AppointmentRequest request){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(patientService.updateBookingAppointment(appointmentId, request))
                .build();
    }

}
