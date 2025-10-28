package com.example.pknk.domain.dto.request.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescriptionRequest {

    String name;
    String dosage;      // liều lượng
    String frequency;   // số lần dùng/ngày
    String duration;    // khoảng thời gian dùng(sau ăn, 15p trước ăn)
    String notes;
    double unitPrice;

}
