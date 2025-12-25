package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.DicomSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DicomSeriesRepository extends JpaRepository<DicomSeries, String> {
    
    Optional<DicomSeries> findByOrthancSeriesId(String orthancSeriesId);
    
    Optional<DicomSeries> findBySeriesInstanceUID(String seriesInstanceUID);
    
    List<DicomSeries> findByStudyId(String studyId);
    
    List<DicomSeries> findByStudyIdOrderBySeriesNumberAsc(String studyId);
    
    @Query("SELECT ds FROM DicomSeries ds WHERE ds.study.id = :studyId AND ds.modality = :modality")
    List<DicomSeries> findByStudyIdAndModality(@Param("studyId") String studyId, @Param("modality") String modality);
}


