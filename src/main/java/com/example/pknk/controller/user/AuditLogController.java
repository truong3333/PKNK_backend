package com.example.pknk.controller.user;

import com.example.pknk.domain.dto.response.user.ApiResponses;
import com.example.pknk.domain.dto.response.user.AuditLogResponse;
import com.example.pknk.service.user.AuditLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auditLog")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuditLogController {
    AuditLogService auditLogService;

    @GetMapping
    ApiResponses<List<AuditLogResponse>> getAll(){
        return ApiResponses.<List<AuditLogResponse>>builder()
                .code(1000)
                .result(auditLogService.getAllAuditLog())
                .build();
    }

}
