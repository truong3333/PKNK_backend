package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findAllByDoctorIdAndStatus(String doctorId, String status);
    List<Appointment> findAllByDoctorIdAndStatusNot(String doctorId,String status);
}
