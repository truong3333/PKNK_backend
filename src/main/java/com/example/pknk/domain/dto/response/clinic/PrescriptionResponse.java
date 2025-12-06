package com.example.pknk.domain.dto.response.clinic;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescriptionResponse {

    String name;
    String dosage;      // liều lượng
    String frequency;   // số lần dùng/ngày
    String duration;    // khoảng thời gian dùng(sau ăn, 15p trước ăn)
    String notes;
    double unitPrice;

}
