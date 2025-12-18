package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, String> {
    @Query("SELECT e FROM Examination e WHERE e.appointment.id = :appointmentId")
    Optional<Examination> findByAppointmentId(@Param("appointmentId") String appointmentId);
    
    @Query("SELECT e FROM Examination e LEFT JOIN FETCH e.appointment a LEFT JOIN FETCH a.doctor d LEFT JOIN FETCH a.patient p WHERE e.id = :examinationId")
    Optional<Examination> findByIdWithAppointment(@Param("examinationId") String examinationId);
    
    @Query("SELECT e FROM Examination e LEFT JOIN FETCH e.appointment a LEFT JOIN FETCH a.doctor d LEFT JOIN FETCH a.patient p WHERE a.doctor.id = :doctorId")
    List<Examination> findAllByDoctorId(@Param("doctorId") String doctorId);
}
