package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.PaymentRequest;
import com.example.pknk.domain.dto.response.clinic.VNPayResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "VNPAY", description = "Dịch vụ thanh toán VNPay")
public class PaymentController {
    PaymentService paymentService;

    @Operation(summary = "Thanh toán VNPay", description = "Gọi cổng thanh toán VNPay",security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Thông tin thanh toán")
    @GetMapping("/vnPay")
    ApiResponses<VNPayResponse> pay(HttpServletRequest request){
        return ApiResponses.<VNPayResponse>builder()
                .code(1000)
                .result(paymentService.createVnPayPayment( request))
                .build();
    }

}
