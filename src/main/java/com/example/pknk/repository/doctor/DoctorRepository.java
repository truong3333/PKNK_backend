package com.example.pknk.repository.doctor;

import com.example.pknk.domain.entity.user.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    java.util.Optional<Doctor> findByUserId(String userId);
}
