package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, String> {
}
