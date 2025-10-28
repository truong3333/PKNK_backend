package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.PrescriptionRequest;
import com.example.pknk.domain.dto.request.clinic.PrescriptionUpdateRequest;
import com.example.pknk.domain.dto.response.clinic.PrescriptionResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.PrescriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/prescription")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PrescriptionController {
    PrescriptionService prescriptionService;

    @PostMapping
    ApiResponses<PrescriptionResponse> createPrescription(@RequestBody PrescriptionRequest request){
        return ApiResponses.<PrescriptionResponse>builder()
                .code(1000)
                .result(prescriptionService.createPrescription(request))
                .build();
    }

    @PutMapping("/{prescriptionName}")
    ApiResponses<PrescriptionResponse> updatePrescription(@PathVariable String prescriptionName, @RequestBody PrescriptionUpdateRequest request){
        return ApiResponses.<PrescriptionResponse>builder()
                .code(1000)
                .result(prescriptionService.updatePrescription(prescriptionName, request))
                .build();
    }

    @GetMapping
    ApiResponses<List<PrescriptionResponse>> getAllPrescription(){
        return ApiResponses.<List<PrescriptionResponse>>builder()
                .code(1000)
                .result(prescriptionService.getAllPrescription())
                .build();
    }

}
