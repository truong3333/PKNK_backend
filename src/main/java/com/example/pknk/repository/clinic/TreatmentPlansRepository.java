package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.TreatmentPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentPlansRepository extends JpaRepository<TreatmentPlans, String> {
    List<TreatmentPlans> findAllByPatientId(String patientId);
    
    @Query("SELECT tp FROM TreatmentPlans tp " +
           "LEFT JOIN FETCH tp.nurse n " +
           "LEFT JOIN FETCH n.user nu " +
           "LEFT JOIN FETCH nu.userDetail " +
           "LEFT JOIN FETCH tp.doctor d " +
           "LEFT JOIN FETCH d.user du " +
           "LEFT JOIN FETCH du.userDetail " +
           "LEFT JOIN FETCH tp.patient p " +
           "LEFT JOIN FETCH p.user pu " +
           "LEFT JOIN FETCH pu.userDetail " +
           "WHERE tp.doctor.id = :doctorId")
    List<TreatmentPlans> findAllByDoctorId(@Param("doctorId") String doctorId);
    
    List<TreatmentPlans> findAllByNurseId(String NurseId);
}
