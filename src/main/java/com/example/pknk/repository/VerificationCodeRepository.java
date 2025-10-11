package com.example.pknk.repository;

import com.example.pknk.domain.entity.user.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode,String> {
    boolean existsByEmail(String email);
    void deleteAllByEmail(String email);
    Optional<VerificationCode> findByEmail(String email);
}
