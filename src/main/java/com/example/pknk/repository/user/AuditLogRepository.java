package com.example.pknk.repository.user;

import com.example.pknk.domain.entity.user.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog,String> {
}
