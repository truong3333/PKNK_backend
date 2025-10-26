package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.TreatmentPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlansRepository extends JpaRepository<TreatmentPlans, String> {
}
