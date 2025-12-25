package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.DicomStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DicomStudyRepository extends JpaRepository<DicomStudy, String> {
    
    Optional<DicomStudy> findByOrthancStudyId(String orthancStudyId);
    
    Optional<DicomStudy> findByStudyInstanceUID(String studyInstanceUID);
    
    List<DicomStudy> findByPatientId(String patientId);
    
    List<DicomStudy> findByPatientIdOrderByStudyDateDesc(String patientId);
    
    List<DicomStudy> findByExaminationId(String examinationId);
    
    List<DicomStudy> findByTreatmentPhaseId(String treatmentPhaseId);
    
    @Query("SELECT ds FROM DicomStudy ds WHERE ds.patient.id = :patientId AND ds.studyDate >= :startDate ORDER BY ds.studyDate DESC")
    List<DicomStudy> findByPatientIdAndStudyDateAfter(@Param("patientId") String patientId, @Param("startDate") LocalDate startDate);
    
    @Query("SELECT ds FROM DicomStudy ds WHERE ds.patient.id = :patientId AND ds.modality = :modality ORDER BY ds.studyDate DESC")
    List<DicomStudy> findByPatientIdAndModality(@Param("patientId") String patientId, @Param("modality") String modality);
}


