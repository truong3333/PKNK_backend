package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.response.clinic.CostResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.CostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cost")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CostController {
    CostService costService;

    @GetMapping
    ApiResponses<List<CostResponse>> getAllMyCost(){
        return ApiResponses.<List<CostResponse>>builder()
                .code(1000)
                .result(costService.getAllMyCost())
                .build();
    }
}
