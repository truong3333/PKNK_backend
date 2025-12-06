package com.example.pknk.domain.dto.response.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDateTimeResponse{

    @JsonFormat(pattern = "HH:mm dd/MM/yyyy")
    LocalDateTime dateTime;
}
