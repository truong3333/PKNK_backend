package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.CategoryDentalServiceRequest;
import com.example.pknk.domain.dto.response.clinic.CategoryDentalServiceResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.CategoryDentalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoryDentalService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryDentalServiceController {
    CategoryDentalService categoryDentalService;

    @PostMapping
    ApiResponses<CategoryDentalServiceResponse> createCategoryDentalService(@RequestBody CategoryDentalServiceRequest request){
        return ApiResponses.<CategoryDentalServiceResponse>builder()
                .code(1000)
                .result(categoryDentalService.createCategoryDentalService(request))
                .build();
    }

    @PutMapping("/{categoryDentalServiceId}")
    ApiResponses<CategoryDentalServiceResponse> updateCategoryDentalService(@PathVariable String categoryDentalServiceId, @RequestBody CategoryDentalServiceRequest request){
        return ApiResponses.<CategoryDentalServiceResponse>builder()
                .code(1000)
                .result(categoryDentalService.updateCategoryDentalService(categoryDentalServiceId, request))
                .build();
    }

    @GetMapping
    ApiResponses<List<CategoryDentalServiceResponse>> getAllCategoryDentalService(){
        return ApiResponses.<List<CategoryDentalServiceResponse>>builder()
                .code(1000)
                .result(categoryDentalService.getAllCategoryDentalService())
                .build();
    }
}
