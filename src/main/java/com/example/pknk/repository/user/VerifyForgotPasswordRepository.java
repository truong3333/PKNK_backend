package com.example.pknk.repository.user;

import com.example.pknk.domain.entity.user.VerifyForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyForgotPasswordRepository extends JpaRepository<VerifyForgotPassword, String> {
    boolean existsByUsername(String username);
    void deleteAllByUsername(String username);
}
