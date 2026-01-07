package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.CategoryDentalServiceRequest;
import com.example.pknk.domain.dto.response.clinic.CategoryDentalServiceResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.CategoryDentalService;
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
@RequestMapping("/api/v1/categoryDentalService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Tag(name = "CategoryDentalService", description = "Quản lý danh mục dịch vụ")
public class CategoryDentalServiceController {
    CategoryDentalService categoryDentalService;

    @Operation(summary = "Thêm danh mục dịch vụ", description = "Thêm danh mục dịch vụ mới",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thêm danh mục dịch vụ thành công")
    @PostMapping
    ApiResponses<CategoryDentalServiceResponse> createCategoryDentalService(@RequestBody CategoryDentalServiceRequest request){
        return ApiResponses.<CategoryDentalServiceResponse>builder()
                .code(1000)
                .result(categoryDentalService.createCategoryDentalService(request))
                .build();
    }

    @Operation(summary = "Cập nhật danh mục dịch vụ", description = "Cập nhật danh mục dịch vụ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Cập nhật danh mục dịch vụ thành công")
    @PutMapping("/{categoryDentalServiceId}")
    ApiResponses<CategoryDentalServiceResponse> updateCategoryDentalService(@PathVariable String categoryDentalServiceId, @RequestBody CategoryDentalServiceRequest request){
        return ApiResponses.<CategoryDentalServiceResponse>builder()
                .code(1000)
                .result(categoryDentalService.updateCategoryDentalService(categoryDentalServiceId, request))
                .build();
    }

    @Operation(summary = "Xem danh mục dịch vụ", description = "Xem danh sách danh mục dịch vụ")
    @ApiResponse(responseCode = "200", description = "Danh sách danh mục dịch vụ")
    @GetMapping
    ApiResponses<List<CategoryDentalServiceResponse>> getAllCategoryDentalService(){
        return ApiResponses.<List<CategoryDentalServiceResponse>>builder()
                .code(1000)
                .result(categoryDentalService.getAllCategoryDentalService())
                .build();
    }

    @Operation(summary = "Xoá danh mục dịch vụ", description = "Xoá danh mục dịch vụ",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Xoá danh mục dịch vụ thành công")
    @DeleteMapping("/{categoryDentalServiceId}")
    ApiResponses<String> deleteCategoryDentalService(@PathVariable String categoryDentalServiceId){
        categoryDentalService.deleteCategoryDentalService(categoryDentalServiceId);
        return ApiResponses.<String>builder()
                .code(1000)
                .result("Xóa danh mục thành công")
                .build();
    }
}
