package com.example.pknk.repository.doctor;

import com.example.pknk.domain.entity.clinic.BookingDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingDateTimeRepository extends JpaRepository<BookingDateTime, String> {
    boolean existsByDoctorIdAndDateTime(String doctorId, LocalDateTime dateTime);
    void deleteByDoctorIdAndDateTime(String doctorId, LocalDateTime dateTime);
    List<BookingDateTime> findAllByDoctorId(String doctorId);
}
