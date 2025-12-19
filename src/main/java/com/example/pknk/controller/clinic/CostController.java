package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.CostPaymentUpdateRequest;
import com.example.pknk.domain.dto.request.clinic.VNPayCallbackRequest;
import com.example.pknk.domain.dto.response.clinic.CostResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.CostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{costId}")
    ApiResponses<CostResponse> updatePaymentCost(@PathVariable String costId, @RequestBody CostPaymentUpdateRequest request){
        return ApiResponses.<CostResponse>builder()
                .code(1000)
                .result(costService.updatePaymentCost(costId, request))
                .build();
    }

    /**
     * Get cost detail by id (for payment status checking)
     * Roles: ADMIN or users with UPDATE_PAYMENT_COST
     */
    @PreAuthorize("hasAnyAuthority('UPDATE_PAYMENT_COST','ADMIN')")
    @GetMapping("/{costId}")
    ApiResponses<CostResponse> getCostById(@PathVariable String costId){
        return ApiResponses.<CostResponse>builder()
                .code(1000)
                .result(costService.getCostById(costId))
                .build();
    }

    /**
     * VNPay callback endpoint - public, validates using secure hash
     * This endpoint doesn't require authentication because VNPay callback doesn't include auth token
     */
    @PostMapping("/vnpay-callback")
    ApiResponses<CostResponse> updatePaymentCostFromVNPay(@RequestBody VNPayCallbackRequest request){
        return ApiResponses.<CostResponse>builder()
                .code(1000)
                .result(costService.updatePaymentCostFromVNPay(request))
                .build();
    }

}
