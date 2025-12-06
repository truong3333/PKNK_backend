package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostRepository extends JpaRepository<Cost, String> {
    List<Cost> findAllByPatientId(String patientId);
}
