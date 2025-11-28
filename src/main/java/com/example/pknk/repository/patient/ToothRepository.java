package com.example.pknk.repository.patient;

import com.example.pknk.domain.entity.clinic.Tooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToothRepository extends JpaRepository<Tooth,String> {
    List<Tooth> findAllByPatientId(String patientId);
    boolean existsByPatientIdAndToothNumber(String patientId, String toothNumber);
}
