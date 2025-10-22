package com.example.pknk.repository.patient;

import com.example.pknk.domain.entity.user.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {


}
