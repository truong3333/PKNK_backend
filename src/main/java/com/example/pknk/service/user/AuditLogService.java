package com.example.pknk.service.user;

import com.example.pknk.domain.dto.response.user.AuditLogResponse;
import com.example.pknk.domain.entity.user.AuditLog;
import com.example.pknk.repository.user.AuditLogRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class AuditLogService {
    AuditLogRepository auditLogRepository;

    public void log(String action){
        AuditLog auditLog = AuditLog.builder()
                .username(SecurityContextHolder.getContext().getAuthentication().getName())
                .action(action)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }

    public void logFree(String action, String username){
        AuditLog auditLog = AuditLog.builder()
                .username(username)
                .action(action)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditLogResponse> getAllAuditLog(){
        List<AuditLog> listAuditLog = new ArrayList<>(auditLogRepository.findAll());

        log.info("Lấy danh sách hoạt động người dùng thành công.");

        return listAuditLog.stream().map(auditLog -> AuditLogResponse.builder()
                .username(auditLog.getUsername())
                .action(auditLog.getAction())
                .timestamp(auditLog.getTimestamp())
                .build()
        ).toList();
    }
}
