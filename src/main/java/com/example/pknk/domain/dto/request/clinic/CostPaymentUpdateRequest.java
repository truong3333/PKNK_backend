package com.example.pknk.domain.dto.request.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CostPaymentUpdateRequest {

    String paymentMethod;
    String status;
    String vnpTxnRef;
}
