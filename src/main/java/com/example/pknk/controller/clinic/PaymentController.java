package com.example.pknk.controller.clinic;

import com.example.pknk.domain.dto.request.clinic.PaymentRequest;
import com.example.pknk.domain.dto.response.clinic.VNPayResponse;
import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.service.clinic.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/vnPay")
    ApiResponses<VNPayResponse> pay(HttpServletRequest request){
        return ApiResponses.<VNPayResponse>builder()
                .code(1000)
                .result(paymentService.createVnPayPayment( request))
                .build();
    }

}
