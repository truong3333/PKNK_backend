package com.example.pknk.controller.nurse;

import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.clinic.TreatmentPlansResponse;
import com.example.pknk.domain.dto.response.doctor.DoctorSummaryResponse;
import com.example.pknk.domain.dto.response.nurse.NurseInfoResponse;
import com.example.pknk.domain.dto.response.nurse.NursePickResponse;
import com.example.pknk.domain.dto.response.patient.PatientResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.doctor.DoctorService;
import com.example.pknk.service.nurse.NurseService;
import com.example.pknk.service.patient.PatientService;
import com.example.pknk.service.patient.TreatmentPlansService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nurse")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class NurseController {
    NurseService nurseService;
    DoctorService doctorService;
    PatientService patientService;
    TreatmentPlansService treatmentPlansService;

    @GetMapping("/getInfo/{nurseId}")
    ApiResponses<NurseInfoResponse> getInfoNurseById(@PathVariable String nurseId){
        return ApiResponses.<NurseInfoResponse>builder()
                .code(1000)
                .result(nurseService.getInfoNurse(nurseId))
                .build();
    }

    @GetMapping("/pick")
    ApiResponses<List<NursePickResponse>> getAllNurseForPick(){
        return ApiResponses.<List<NursePickResponse>>builder()
                .code(1000)
                .result(nurseService.getAllNurseForPick())
                .build();
    }

    @GetMapping("/appointment/scheduled/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAppointmentScheduledOfDoctor(@PathVariable String doctorId){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAppointmentScheduledOfDoctor(doctorId))
                .build();
    }

    @GetMapping("/appointment/all/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAllAppointmentsOfDoctor(@PathVariable String doctorId){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAllAppointmentsIncludingCancelledOfDoctor(doctorId))
                .build();
    }

    @GetMapping("/myTreatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getMyTreatmentPlansOfNurse(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getMyTreatmentPlansOfNurse())
                .build();
    }

    //patient
    @GetMapping("/{patientId}")
    ApiResponses<PatientResponse> getBasicInfo(@PathVariable String patientId){
        return ApiResponses.<PatientResponse>builder()
                .code(1000)
                .result(patientService.getBasicInfo(patientId))
                .build();
    }

    //doctor
    @GetMapping("/doctors")
    ApiResponses<List<DoctorSummaryResponse>> getAllDoctors(){
        return ApiResponses.<List<DoctorSummaryResponse>>builder()
                .code(1000)
                .result(doctorService.getAllDoctors())
                .build();
    }

    @GetMapping("/doctors/{doctorId}")
    ApiResponses<DoctorSummaryResponse> getInfoDoctorById(@PathVariable String doctorId){
        return ApiResponses.<DoctorSummaryResponse>builder()
                .code(1000)
                .result(doctorService.getInfoDoctorById(doctorId))
                .build();
    }
}
