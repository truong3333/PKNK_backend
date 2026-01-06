package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityRequest;
import com.example.pknk.domain.dto.response.clinic.DentalServicesEntityResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.DentalServicesEntityService;
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
@RequestMapping("/api/v1/dentalService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "DentalService", description = "Quản lý dịch vụ")
public class DentalServicesEntityController {
    DentalServicesEntityService dentalServicesEntityService;

    @Operation(summary = "Thêm dịch vụ", description = "Thêm dịch vụ mới cho danh mục dịch vụ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm dịch vụ thành công")
    @PostMapping("/{categoryDentalServiceId}")
    ApiResponses<DentalServicesEntityResponse> createService(@PathVariable String categoryDentalServiceId, @RequestBody DentalServicesEntityRequest request){
        return ApiResponses.<DentalServicesEntityResponse>builder()
                .code(1000)
                .result(dentalServicesEntityService.createService(categoryDentalServiceId, request))
                .build();
    }

    @Operation(summary = "Cập nhật dịch vụ", description = "Cập nhật dịch vụ theo id",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật dịch vụ thành công")
    @PutMapping("/{serviceId}")
    ApiResponses<DentalServicesEntityResponse> updateService(@PathVariable String serviceId, @RequestBody DentalServicesEntityRequest request){
        return ApiResponses.<DentalServicesEntityResponse>builder()
                .code(1000)
                .result(dentalServicesEntityService.updateService(serviceId, request))
                .build();
    }

    @Operation(summary = "Xem danh sách dịch vụ", description = "Xem danh sách dịch vụ")
    @ApiResponse(responseCode = "200", description = "Danh sách dịch vụ")
    @GetMapping
    ApiResponses<List<DentalServicesEntityResponse>> getAllService(){
        return ApiResponses.<List<DentalServicesEntityResponse>>builder()
                .code(1000)
                .result(dentalServicesEntityService.getAllService())
                .build();
    }

    @Operation(summary = "Xoá dịch vụ", description = "Xoá dịch vụ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Xoá dịch vụ thành công")
    @DeleteMapping("/{serviceId}")
    ApiResponses<String> deleteService(@PathVariable String serviceId){
        dentalServicesEntityService.deleteService(serviceId);
        return ApiResponses.<String>builder()
                .code(1000)
                .result("Xóa dịch vụ thành công")
                .build();
    }
}
