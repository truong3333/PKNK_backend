package com.example.pknk.controller.doctor;

import com.example.pknk.domain.dto.request.doctor.*;
import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.clinic.ExaminationResponse;
import com.example.pknk.domain.dto.response.clinic.TreatmentPhasesResponse;
import com.example.pknk.domain.dto.response.clinic.TreatmentPlansResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.doctor.DoctorSummaryResponse;
import com.example.pknk.service.doctor.DoctorService;
import com.example.pknk.service.patient.TreatmentPhasesService;
import com.example.pknk.service.patient.TreatmentPlansService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DoctorController {
    DoctorService doctorService;
    TreatmentPlansService treatmentPlansService;
    TreatmentPhasesService treatmentPhasesService;

    // Public doctor list for selection
    @GetMapping("/doctors")
    ApiResponses<List<DoctorSummaryResponse>> getAllDoctors(){
        return ApiResponses.<List<DoctorSummaryResponse>>builder()
                .code(1000)
                .result(doctorService.getAllDoctors())
                .build();
    }

    @GetMapping("/{doctorId}")
    ApiResponses<DoctorSummaryResponse> getInfoDoctorById(@PathVariable String doctorId){
        return ApiResponses.<DoctorSummaryResponse>builder()
                .code(1000)
                .result(doctorService.getInfoDoctorById(doctorId))
                .build();
    }

    // Appointment
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
                .result(doctorService.getAllAppointmentOfMyDoctor())
                .build();
    }


    // Examination
    @PostMapping("/{appointmentId}/examination")
    ApiResponses<ExaminationResponse> createExamination(@PathVariable String appointmentId, @ModelAttribute ExaminationRequest request) throws IOException {
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.createExamination(appointmentId, request))
                .build();
    }

    @PutMapping("/examination/{examinationId}")
    ApiResponses<ExaminationResponse> updateExamination(@PathVariable String examinationId, @ModelAttribute ExaminationUpdateRequest request) throws IOException {
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.updateExamination(examinationId, request))
                .build();
    }

    @GetMapping("/{appointmentId}/examination")
    ApiResponses<ExaminationResponse> getExaminationByAppointmentId(@PathVariable String appointmentId){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.getExaminationByAppointmentId(appointmentId))
                .build();
    }

    @GetMapping("/myExamination")
    ApiResponses<List<ExaminationResponse>> getMyExamination(){
        return ApiResponses.<List<ExaminationResponse>>builder()
                .code(1000)
                .result(doctorService.getMyExamination())
                .build();
    }

    @GetMapping("/examination/{examinationId}")
    ApiResponses<ExaminationResponse> getExaminationDetailById(@PathVariable String examinationId){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.getExaminationDetailById(examinationId))
                .build();
    }


    // Treatment Plans
    @PostMapping("/treatmentPlans")
    ApiResponses<TreatmentPlansResponse> createTreatmentPlans(@RequestBody TreatmentPlansRequest request){
        return ApiResponses.<TreatmentPlansResponse>builder()
                .code(1000)
                .result(treatmentPlansService.createTreatmentPlans(request))
                .build();
    }

    @GetMapping("/treatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getAllTreatmentPlans(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getAllTreatmentPlans())
                .build();
    }

    @GetMapping("/myTreatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getMyTreatmentPlansOfDoctor(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getMyTreatmentPlansOfDoctor())
                .build();
    }

    @PutMapping("/treatmentPlans/{treatmentPlansId}")
    ApiResponses<TreatmentPlansResponse> updateTreatmentPlans(@PathVariable String treatmentPlansId, @RequestBody TreatmentPlansUpdateRequest request){
        return ApiResponses.<TreatmentPlansResponse>builder()
                .code(1000)
                .result(treatmentPlansService.updateTreatmentPlans(treatmentPlansId, request))
                .build();
    }

    // treatmentPhases
    @PostMapping("/{treatmentPlansId}/treatmentPhases")
    ApiResponses<TreatmentPhasesResponse> createTreatmentPhases(@PathVariable String treatmentPlansId, @ModelAttribute TreatmentPhasesRequest request) throws IOException {
        return ApiResponses.<TreatmentPhasesResponse>builder()
                .code(1000)
                .result(treatmentPhasesService.createTreatmentPhases(treatmentPlansId, request))
                .build();
    }

    @PutMapping("/treatmentPhases/{treatmentPhasesId}")
    ApiResponses<TreatmentPhasesResponse> updateTreatmentPhases(@PathVariable String treatmentPhasesId, @ModelAttribute TreatmentPhasesUpdateRequest request) throws IOException {
        return ApiResponses.<TreatmentPhasesResponse>builder()
                .code(1000)
                .result(treatmentPhasesService.updateTreatmentPhases(treatmentPhasesId, request))
                .build();
    }

    @GetMapping("/treatmentPhases/{treatmentPlansId}")
    ApiResponses<List<TreatmentPhasesResponse>> getAllTreatmentPhasesOfTreatmentPlansId(@PathVariable String treatmentPlansId){
        return ApiResponses.<List<TreatmentPhasesResponse>>builder()
                .code(1000)
                .result(treatmentPhasesService.getAllTreatmentPhasesOfTreatmentPlansId(treatmentPlansId))
                .build();
    }


    //DOCTOR LV2
    @PostMapping("/commentExamination/{examinationId}")
    ApiResponses<ExaminationResponse> addCommentExaminationByDoctorLV2(@PathVariable String examinationId, @RequestBody CommentRequest request){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.addCommentExaminationByDoctorLV2(examinationId, request))
                .build();
    }

    @PostMapping("/commentTreatmentPhases/{examinationId}")
    ApiResponses<TreatmentPhasesResponse> addCommentTreatmentPhasesByDoctorLV2(@PathVariable String treatmentPhasesId, @RequestBody CommentRequest request){
        return ApiResponses.<TreatmentPhasesResponse>builder()
                .code(1000)
                .result(doctorService.addCommentTreatmentPhasesByDoctorLV2(treatmentPhasesId, request))
                .build();
    }

}
