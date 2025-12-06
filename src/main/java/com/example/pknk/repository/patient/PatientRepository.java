package com.example.pknk.repository.patient;

import com.example.pknk.domain.entity.user.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByUserUsername(String username);
}
