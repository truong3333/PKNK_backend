package com.example.pknk.controller.patient;

import com.example.pknk.domain.dto.request.patient.*;
import com.example.pknk.domain.dto.response.clinic.AppointmentResponse;
import com.example.pknk.domain.dto.response.clinic.ExaminationResponse;
import com.example.pknk.domain.dto.response.clinic.TreatmentPlansResponse;
import com.example.pknk.domain.dto.response.patient.*;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.patient.PatientService;
import com.example.pknk.service.patient.ToothService;
import com.example.pknk.service.patient.TreatmentPlansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Patient", description = "Quản lý chức năng bệnh nhân")
public class PatientController {
    PatientService patientService;
    TreatmentPlansService treatmentPlansService;
    ToothService toothService;

    @Operation(summary = "Xem thông tin y tế bệnh nhân", description = "Xem thông tin y tế bệnh nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin bệnh nhân")
    @GetMapping("/{patientId}")
    ApiResponses<PatientResponse> getBasicInfo(@PathVariable String patientId){
        return ApiResponses.<PatientResponse>builder()
                .code(1000)
                .result(patientService.getBasicInfo(patientId))
                .build();
    }

    @Operation(summary = "Xem thông tin cá nhân", description = "Xem thông tin cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin cá nhân")
    @GetMapping("/myInfo")
    public ApiResponses<PatientResponse> getMyInfo(){
        return ApiResponses.<PatientResponse>builder()
                .code(1000)
                .result(patientService.getMyPatientInfo())
                .build();
    }

    @Operation(summary = "Liên hệ khẩn cấp", description = "Xem thông tin liên hệ khẩn cấp",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin liên hệ khẩn cấp")
    @PutMapping("/emergencyContact/{patientId}")
    ApiResponses<EmergencyContactResponse> updateEmergencyContact(@PathVariable String patientId, @RequestBody EmergencyContactRequest request){
        return ApiResponses.<EmergencyContactResponse>builder()
                .code(1000)
                .result(patientService.updateEmergencyContact(patientId, request))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin y tế", description = "Xem thông tin liên hệ khẩn cấp",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin liên hệ khẩn cấp")
    @PutMapping("/medicalInformation/{patientId}")
    ApiResponses<MedicalInformationResponse> updateMedicalInformation(@PathVariable String patientId, @RequestBody MedicalInformationRequest request){
        return  ApiResponses.<MedicalInformationResponse>builder()
                .code(1000)
                .result(patientService.updateMedicalInformation(patientId, request))
                .build();
    }

    @Operation(summary = "Đặt lịch hẹn", description = "Đặt lịch hẹn",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Đặt lịch hẹn thành công")
    @PostMapping("/appointment/booking")
    ApiResponses<AppointmentResponse> bookAppointment(@RequestBody AppointmentRequest request){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(patientService.bookingAppointment(request))
                .build();
    }

    @Operation(summary = "Xem khung giờ đặt bác sĩ", description = "Xem khung giờ đặt bác sĩ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Khung giờ đặt bác sĩ")
    @GetMapping("/appointment/bookingDateTime/{doctorId}")
    ApiResponses<List<BookingDateTimeResponse>> getAllBookingForPatient(@PathVariable String doctorId){
        return ApiResponses.<List<BookingDateTimeResponse>>builder()
                .code(1000)
                .result(patientService.getAllBookingOfDoctor(doctorId))
                .build();
    }

    @Operation(summary = "Huỷ lịch hẹn", description = "Huỷ lịch hẹn theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Huỷ lịch hẹn thành công")
    @PutMapping("/appointment/booking/cancel/{appointmentId}")
    ApiResponses<AppointmentResponse> cancelBookingAppointment(@PathVariable String appointmentId){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(patientService.cancelBookingAppointment(appointmentId))
                .build();
    }

    @Operation(summary = "Cập nhật lịch hẹn", description = "Cập nhật lịch hẹn theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật lịch hẹn thành công")
    @PutMapping("/appointment/booking/update/{appointmentId}")
    ApiResponses<AppointmentResponse> updateBookingAppointment(@PathVariable String appointmentId, @RequestBody AppointmentRequest request){
        return ApiResponses.<AppointmentResponse>builder()
                .code(1000)
                .result(patientService.updateBookingAppointment(appointmentId, request))
                .build();
    }

    @Operation(summary = "Xem lịch hẹn", description = "Xem lịch hẹn cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách lịch hẹn")
    @GetMapping("/myAppointment")
    ApiResponses<List<AppointmentResponse>> getMyAppointment(){
        return ApiResponses.<List<AppointmentResponse>>builder()
                .code(1000)
                .result(patientService.getMyAppointment())
                .build();
    }

    @Operation(summary = "Xem hồ sơ khám", description = "Xem hồ sơ khám ban đầu cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách hồ sơ khám ban đầu")
    @GetMapping("/myExamination")
    ApiResponses<List<ExaminationResponse>> getMyExamination(){
        return ApiResponses.<List<ExaminationResponse>>builder()
                .code(1000)
                .result(patientService.getMyExamination())
                .build();
    }

    @Operation(summary = "Xem chi tiết hồ sơ khám", description = "Xem chi tiết hồ sơ khám bằng id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Hồ sơ khám ban đầu")
    @GetMapping("/examination/{examinationId}")
    ApiResponses<ExaminationResponse> getExaminationDetailById(@PathVariable String examinationId){
        return ApiResponses.<ExaminationResponse>builder()
                .code(1000)
                .result(patientService.getExaminationDetailById(examinationId))
                .build();
    }

    @Operation(summary = "Xem danh sách phác đồ điều trị", description = "Xem danh sách phác đồ điều trị theo id bệnh nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách phác đồ điều trị")
    @GetMapping("/treatmentPlans/{patientId}")
    ApiResponses<List<TreatmentPlansResponse>> getAllTreatmentPlansByPatientId(@PathVariable String patientId){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getAllTreatmentPlansByPatientId(patientId))
                .build();
    }

    @Operation(summary = "Xem danh sách phác đồ điều trị", description = "Xem danh sách phác đồ điều trị cá nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách phác đồ điều trị")
    @GetMapping("/myTreatmentPlans")
    ApiResponses<List<TreatmentPlansResponse>> getMyTreatmentPlansOfPatient(){
        return ApiResponses.<List<TreatmentPlansResponse>>builder()
                .code(1000)
                .result(treatmentPlansService.getMyTreatmentPlansOfPatient())
                .build();
    }


    //Tooth
    @Operation(summary = "Thêm trạng thái răng", description = "Thêm trạng thái răng cho bệnh nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm trạng thái răng thành công")
    @PostMapping("/{patientId}/tooth")
    ApiResponses<ToothResponse> createToothStatus(@PathVariable String patientId, @RequestBody ToothRequest request){
        return ApiResponses.<ToothResponse>builder()
                .code(1000)
                .result(toothService.createToothStatus(patientId, request))
                .build();
    }

    @Operation(summary = "Cập nhật trạng thái răng", description = "Câ nhật trạng thái răng cho bệnh nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật trạng thái răng thành công")
    @PutMapping("/tooth/{toothId}")
    ApiResponses<ToothResponse> updateToothStatus(@PathVariable String toothId, @RequestBody ToothUpdateRequest request){
        return ApiResponses.<ToothResponse>builder()
                .code(1000)
                .result(toothService.updateToothStatus(toothId, request))
                .build();
    }

    @Operation(summary = "Xem trạng thái răng", description = "Xem trạng thái răng của bệnh nhân",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách trạng thái răng")
    @GetMapping("/{patientId}/tooth")
    ApiResponses<List<ToothResponse>> getAllToothStatusOfPatient(@PathVariable String patientId){
        return ApiResponses.<List<ToothResponse>>builder()
                .code(1000)
                .result(toothService.getAllToothStatusOfPatient(patientId))
                .build();
    }


}
