package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.PrescriptionRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.PrescriptionResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.PrescriptionService;
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
@RequestMapping("api/v1/prescription")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "Prescription", description = "Quản lý đơn thuốc")
public class PrescriptionController {
    PrescriptionService prescriptionService;

    @Operation(summary = "Thêm đơn thuốc", description = "Thêm đơn thuốc mới",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm đơn thuốc thành công")
    @PostMapping
    ApiResponses<PrescriptionResponse> createPrescription(@RequestBody PrescriptionRequest request){
        return ApiResponses.<PrescriptionResponse>builder()
                .code(1000)
                .result(prescriptionService.createPrescription(request))
                .build();
    }

    @Operation(summary = "Cập nhật đơn thuốc", description = "Cập nhật đơn thuốc mới",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật đơn thuốc thành công")
    @PutMapping("/{prescriptionName}")
    ApiResponses<PrescriptionResponse> updatePrescription(@PathVariable String prescriptionName, @RequestBody PrescriptionUpdateRequest request){
        return ApiResponses.<PrescriptionResponse>builder()
                .code(1000)
                .result(prescriptionService.updatePrescription(prescriptionName, request))
                .build();
    }

    @Operation(summary = "Xem danh sách đơn thuốc", description = "Xem danh sách đơn thuốc",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Danh sách đơn thuốc")
    @GetMapping
    ApiResponses<List<PrescriptionResponse>> getAllPrescription(){
        return ApiResponses.<List<PrescriptionResponse>>builder()
                .code(1000)
                .result(prescriptionService.getAllPrescription())
                .build();
    }

}
