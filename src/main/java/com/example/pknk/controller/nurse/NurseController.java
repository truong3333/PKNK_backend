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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nurse")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "Nurse", description = "Quản lý chức năng y tá")
public class NurseController {
    NurseService nurseService;
    DoctorService doctorService;
    PatientService patientService;
    TreatmentPlansService treatmentPlansService;

    @Operation(summary = "Xem thông tin y tá", description = "Xem thông tin y tá bằng id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin y tá")
    @GetMapping("/getInfo/{nurseId}")
    ApiResponses<NurseInfoResponse> getInfoNurseById(@PathVariable String nurseId){
        return ApiResponses.<NurseInfoResponse>builder()
                .code(1000)
                .result(nurseService.getInfoNurse(nurseId))
                .build();
    }

    @Operation(summary = "Xem thông tin y tá", description = "Xem thông tin y tá bằng id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin y tá")
    @GetMapping("/user/{userId}")
    ApiResponses<NurseInfoResponse> getInfoNurseByUserId(@PathVariable String userId){
        return ApiResponses.<NurseInfoResponse>builder()
                .code(1000)
                .result(nurseService.getInfoNurseByUserId(userId))
                .build();
    }

    @Operation(summary = "Danh sách y tá", description = "Xem danh sách y tá",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách y tá")
    @GetMapping("/pick")
    ApiResponses<List<NursePickResponse>> getAllNurseForPick(){
        return ApiResponses.<List<NursePickResponse>>builder()
                .code(1000)
                .result(nurseService.getAllNurseForPick())
                .build();
    }

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
    @GetMapping("/appointment/all/{doctorId}")
    ApiResponses<List<AppointmentResponse>> getAllAppointmentsByDoctor(@PathVariable String doctorId){
        // Lấy tất cả lịch hẹn của doctor (trừ các lịch đã bị patient hủy)
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(doctorService.getAllAppointmentOfDoctor(doctorId))
                .build();
    }

    @Operation(summary = "Thông báo lịch hẹn cho bác sĩ và bệnh nhân", description = "Thông báo lịch hẹn cho bác sĩ và bệnh nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông báo thành công")
    @PutMapping("/appointment/{appointmentId}")
    ApiResponses<AppointmentResponse> notificationUpdateAppointment(@PathVariable String appointmentId){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(nurseService.notificationUpdateAppointment(appointmentId))
                .build();
    }

    @Operation(summary = "Xem danh sách phác đồ được giao", description = "Xem danh sách phác đồ được giao cho y tá",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách phác đồ được giao")
    @GetMapping("/myTreatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getMyTreatmentPlansOfNurse(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getMyTreatmentPlansOfNurse())
                .build();
    }

    //patient
    @Operation(summary = "Xem thông tin bệnh nhân", description = "Xem thông tin bệnh nhân theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bệnh nhân")
    @GetMapping("/{patientId}")
    ApiResponses<PatientResponse> getBasicInfo(@PathVariable String patientId){
        return ApiResponses.<PatientResponse>builder()
                .code(1000)
                .result(patientService.getBasicInfo(patientId))
                .build();
    }

    @Operation(summary = "Xem thông tin bệnh nhân", description = "Xem thông tin bệnh nhân theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bệnh nhân")
    @GetMapping("/patient/user/{userId}")
    ApiResponses<PatientResponse> getBasicInfoByUserId(@PathVariable String userId){
        return ApiResponses.<PatientResponse>builder()
                .code(1000)
                .result(patientService.getBasicInfoByUserId(userId))
                .build();
    }

    //doctor
    @Operation(summary = "Xem thông tin bác sĩ", description = "Xem thông tin bác sĩ theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bác sĩ")
    @GetMapping("/doctors")
    ApiResponses<List<DoctorSummaryResponse>> getAllDoctors(){
        return ApiResponses.<List<DoctorSummaryResponse>>builder()
                .code(1000)
                .result(doctorService.getAllDoctors())
                .build();
    }

    @Operation(summary = "Xem thông tin bác sĩ", description = "Xem thông tin bác sĩ theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bác sĩ")
    @GetMapping("/doctors/{doctorId}")
    ApiResponses<DoctorSummaryResponse> getInfoDoctorById(@PathVariable String doctorId){
        return ApiResponses.<DoctorSummaryResponse>builder()
                .code(1000)
                .result(doctorService.getInfoDoctorById(doctorId))
                .build();
    }
}
