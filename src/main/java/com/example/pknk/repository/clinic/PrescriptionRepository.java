package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
}
