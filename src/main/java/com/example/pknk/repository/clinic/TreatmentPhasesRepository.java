package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.TreatmentPhases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentPhasesRepository extends JpaRepository<TreatmentPhases, String> {
    List<TreatmentPhases> findAllByTreatmentPlansId(String treatmentPlansId);
}
