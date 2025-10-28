package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.DentalServicesEntityRequest;
import com.example.pknk.domain.dto.response.clinic.DentalServicesEntityResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.DentalServicesEntityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dentalService")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DentalServicesEntityController {
    DentalServicesEntityService dentalServicesEntityService;

    @PostMapping
    ApiResponses<DentalServicesEntityResponse> createService(@RequestBody DentalServicesEntityRequest request){
        return ApiResponses.<DentalServicesEntityResponse>builder()
                .code(1000)
                .result(dentalServicesEntityService.createService(request))
                .build();
    }

    @PutMapping("/{serviceId}")
    ApiResponses<DentalServicesEntityResponse> updateService(@PathVariable String serviceId, @RequestBody DentalServicesEntityRequest request){
        return ApiResponses.<DentalServicesEntityResponse>builder()
                .code(1000)
                .result(dentalServicesEntityService.updateService(serviceId, request))
                .build();
    }

    @GetMapping
    ApiResponses<List<DentalServicesEntityResponse>> getAllService(){
        return ApiResponses.<List<DentalServicesEntityResponse>>builder()
                .code(1000)
                .result(dentalServicesEntityService.getAllService())
                .build();
    }
}
