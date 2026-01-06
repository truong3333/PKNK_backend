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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@Tag(name = "Doctor", description = "Quản lý chức năng bác sĩ")
public class DoctorController {
    DoctorService doctorService;
    TreatmentPlansService treatmentPlansService;
    TreatmentPhasesService treatmentPhasesService;

    // Public doctor list for selection
    @Operation(summary = "Xem danh sách bác sĩ", description = "Xem danh sách thông tin bác sĩ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách thông tin bác sĩ")
    @GetMapping("/doctors")
    ApiResponses<List<DoctorSummaryResponse>> getAllDoctors(){
        return ApiResponses.<List<DoctorSummaryResponse>>builder()
                .code(1000)
                .result(doctorService.getAllDoctors())
                .build();
    }

    @Operation(summary = "Xem thông tin bác sĩ", description = "Xem thông tin bác sĩ theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bác sĩ")
    @GetMapping("/{doctorId}")
    ApiResponses<DoctorSummaryResponse> getInfoDoctorById(@PathVariable String doctorId){
        return ApiResponses.<DoctorSummaryResponse>builder()
                .code(1000)
                .result(doctorService.getInfoDoctorById(doctorId))
                .build();
    }

    @Operation(summary = "Xem thông tin bác sĩ", description = "Xem thông tin bác sĩ theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bác sĩ")
    @GetMapping("/user/{userId}")
    ApiResponses<DoctorSummaryResponse> getInfoDoctorByUserId(@PathVariable String userId){
        return ApiResponses.<DoctorSummaryResponse>builder()
                .code(1000)
                .result(doctorService.getInfoDoctorByUserId(userId))
                .build();
    }

    // Appointment
    @Operation(summary = "Xem lịch hẹn bác sĩ", description = "Xem lịch hẹn bác sĩ sắp tới theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sach lịch hẹn bác sĩ sắp tới")
    @GetMapping("/appointment/scheduled/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAppointmentScheduledOfDoctor(@PathVariable String doctorId){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAppointmentScheduledOfDoctor(doctorId))
                .build();
    }

    @Operation(summary = "Xem lịch hẹn bác sĩ", description = "Xem lịch hẹn bác sĩ theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách lịch hẹn bác sĩ")
    @GetMapping("/appointment/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAllAppointmentOfDoctor(@PathVariable String doctorId){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAllAppointmentOfDoctor(doctorId))
                .build();
    }

    @Operation(summary = "Xem lịch hẹn cá nhân sắp tới", description = "Xem lịch hẹn cá nhân sắp tới",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách lịch hẹn cá nhân sắp tới")
    @GetMapping("/myAppointment/scheduled")
    ApiResponses<List<AppointmentResponse>> getAppointmentScheduledOfMyDoctor(){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAppointmentScheduledOfMyDoctor())
                .build();
    }

    @Operation(summary = "Xem lịch hẹn cá nhân", description = "Xem lịch hẹn cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách lịch hẹn cá nhân")
    @GetMapping("/myAppointment")
    ApiResponses<List<AppointmentResponse>> getAllAppointmentScheduledOfMyDoctor(){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAllAppointmentOfMyDoctor())
                .build();
    }


    // Examination
    @Operation(summary = "Thêm hồ sơ khám ban đầu", description = "Thêm hồ sơ khám ban đầu",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm hồ sơ khám ban đầu thành công")
    @PostMapping("/{appointmentId}/examination")
    ApiResponses<ExaminationResponse> createExamination(@PathVariable String appointmentId, @ModelAttribute ExaminationRequest request) throws IOException {
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.createExamination(appointmentId, request))
                .build();
    }

    @Operation(summary = "Cập nhật hồ sơ khám ban đầu", description = "Cập nhật hồ sơ khám ban đầu",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật hồ sơ khám ban đầu thành công")
    @PutMapping("/examination/{examinationId}")
    ApiResponses<ExaminationResponse> updateExamination(@PathVariable String examinationId, @ModelAttribute ExaminationUpdateRequest request) throws IOException {
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.updateExamination(examinationId, request))
                .build();
    }

    @Operation(summary = "Xem hồ sơ khám ban đầu", description = "Xem hồ sơ khám ban đầu theo id lịch hẹn",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Hồ sơ khám ban đầu")
    @GetMapping("/{appointmentId}/examination")
    ApiResponses<ExaminationResponse> getExaminationByAppointmentId(@PathVariable String appointmentId){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.getExaminationByAppointmentId(appointmentId))
                .build();
    }

    @Operation(summary = "Xem danh sách hồ sơ khám cá nhân", description = "Xem danh sách hồ sơ khám cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách hồ sơ khám ban đầu")
    @GetMapping("/myExamination")
    ApiResponses<List<ExaminationResponse>> getMyExamination(){
        return ApiResponses.<List<ExaminationResponse>>builder()
                .code(1000)
                .result(doctorService.getMyExamination())
                .build();
    }

    @Operation(summary = "Xem hồ sơ khám ban đầu", description = "Xem hồ sơ khám ban đầu theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Hồ sơ khám ban đầu")
    @GetMapping("/examination/{examinationId}")
    ApiResponses<ExaminationResponse> getExaminationDetailById(@PathVariable String examinationId){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.getExaminationDetailById(examinationId))
                .build();
    }


    // Treatment Plans
    @Operation(summary = "Thêm phác đồ điều trị", description = "Thêm phác đồ điều trị",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm phác đồ điều trị thành công")
    @PostMapping("/treatmentPlans")
    ApiResponses<TreatmentPlansResponse> createTreatmentPlans(@RequestBody TreatmentPlansRequest request){
        log.info("Received createTreatmentPlans request: title={}, examinationId={}, nurseId={}", 
                request.getTitle(), request.getExaminationId(), request.getNurseId());
        return ApiResponses.<TreatmentPlansResponse>builder()
                .code(1000)
                .result(treatmentPlansService.createTreatmentPlans(request))
                .build();
    }

    @Operation(summary = "Xem phác đồ điều trị", description = "Xem phác đồ điều trị",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách phác đồ điều trị")
    @GetMapping("/treatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getAllTreatmentPlans(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getAllTreatmentPlans())
                .build();
    }

    @Operation(summary = "Xem phác đồ điều trị cá nhân", description = "Xem phác đồ điều trị cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách phác đồ điều trị cá nhân")
    @GetMapping("/myTreatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getMyTreatmentPlansOfDoctor(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getMyTreatmentPlansOfDoctor())
                .build();
    }

    @Operation(summary = "Cập nhật đồ điều trị", description = "Cập nhật phác đồ điều trị",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật phác đồ điều trị thành công")
    @PutMapping("/treatmentPlans/{treatmentPlansId}")
    ApiResponses<TreatmentPlansResponse> updateTreatmentPlans(@PathVariable String treatmentPlansId, @RequestBody TreatmentPlansUpdateRequest request){
        return ApiResponses.<TreatmentPlansResponse>builder()
                .code(1000)
                .result(treatmentPlansService.updateTreatmentPlans(treatmentPlansId, request))
                .build();
    }

    // treatmentPhases
    @Operation(summary = "Thêm tiến trình điều trị", description = "Thêm tiến trình điều trị",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm tiến trình điều trị thành công")
    @PostMapping("/{treatmentPlansId}/treatmentPhases")
    ApiResponses<TreatmentPhasesResponse> createTreatmentPhases(@PathVariable String treatmentPlansId, @ModelAttribute TreatmentPhasesRequest request) throws IOException {
        return ApiResponses.<TreatmentPhasesResponse>builder()
                .code(1000)
                .result(treatmentPhasesService.createTreatmentPhases(treatmentPlansId, request))
                .build();
    }

    @Operation(summary = "Cập nhật tiến trình điều trị", description = "Cập nhật tiến trình điều trị",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật tiến trình điều trị thành công")
    @PutMapping("/treatmentPhases/{treatmentPhasesId}")
    ApiResponses<TreatmentPhasesResponse> updateTreatmentPhases(@PathVariable String treatmentPhasesId, @ModelAttribute TreatmentPhasesUpdateRequest request) throws IOException {
        return ApiResponses.<TreatmentPhasesResponse>builder()
                .code(1000)
                .result(treatmentPhasesService.updateTreatmentPhases(treatmentPhasesId, request))
                .build();
    }

    @Operation(summary = "Xem danh sách tiến trình điều trị của phác đồ", description = "Xem danh sách tiến trình điều trị của phác đồ theo id phác đồ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách tiến trình điều trị của phác đồ")
    @GetMapping("/treatmentPhases/{treatmentPlansId}")
    ApiResponses<List<TreatmentPhasesResponse>> getAllTreatmentPhasesOfTreatmentPlansId(@PathVariable String treatmentPlansId){
        return ApiResponses.<List<TreatmentPhasesResponse>>builder()
                .code(1000)
                .result(treatmentPhasesService.getAllTreatmentPhasesOfTreatmentPlansId(treatmentPlansId))
                .build();
    }


    //DOCTOR LV2
    @Operation(summary = "Bác sĩ cấp cao xem hồ sơ khám", description = "Bác sĩ cấp cao xem hồ sơ khám của bác sĩ khác theo id bác sĩ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách hồ sơ khám")
    @GetMapping("/{doctorId}/examinations")
    @PreAuthorize("hasAnyAuthority('PICK_DOCTOR','ADMIN')")
    ApiResponses<List<ExaminationResponse>> getExaminationsByDoctorId(@PathVariable String doctorId){
        return ApiResponses.<List<ExaminationResponse>>builder()
                .code(1000)
                .result(doctorService.getExaminationsByDoctorId(doctorId))
                .build();
    }

    @Operation(summary = "Bác sĩ cấp cao xem phác đồ điều trị", description = "Bác sĩ cấp cao xem phác đồ điều trị của bác sĩ khác theo id bác sĩ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách phác đồ điều trị")
    @GetMapping("/{doctorId}/treatmentPlans")
    @PreAuthorize("hasAnyAuthority('PICK_DOCTOR','ADMIN')")
    ApiResponses<List<TreatmentPlansResponse>> getTreatmentPlansByDoctorId(@PathVariable String doctorId){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getTreatmentPlansByDoctorId(doctorId))
                .build();
    }

    @Operation(summary = "Bác sĩ cấp cao thêm nhận xét hồ sơ khám", description = "Bác sĩ cấp cao thêm nhận xét hồ sơ khám của bác sĩ khác theo id hồ sơ khám",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm nhận xét thành công")
    @PostMapping("/commentExamination/{examinationId}")
    ApiResponses<ExaminationResponse> addCommentExaminationByDoctorLV2(@PathVariable String examinationId, @RequestBody CommentRequest request){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(doctorService.addCommentExaminationByDoctorLV2(examinationId, request))
                .build();
    }

    @Operation(summary = "Bác sĩ cấp cao thêm nhận xét tiến trình điều trị", description = "Bác sĩ cấp cao thêm nhận xét tiến trình điều trị của bác sĩ khác theo id hồ sơ khám",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm nhận xét thành công")
    @PostMapping("/commentTreatmentPhases/{treatmentPhasesId}")
    ApiResponses<TreatmentPhasesResponse> addCommentTreatmentPhasesByDoctorLV2(@PathVariable String treatmentPhasesId, @RequestBody CommentRequest request){
        return ApiResponses.<TreatmentPhasesResponse>builder()
                .code(1000)
                .result(doctorService.addCommentTreatmentPhasesByDoctorLV2(treatmentPhasesId, request))
                .build();
    }

}
