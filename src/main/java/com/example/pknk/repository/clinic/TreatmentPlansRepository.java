package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.TreatmentPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentPlansRepository extends JpaRepository<TreatmentPlans, String> {
    List<TreatmentPlans> findAllByPatientId(String patientId);
    List<TreatmentPlans> findAllByDoctorId(String doctorId);
}
