package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.DicomInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DicomInstanceRepository extends JpaRepository<DicomInstance, String> {
    
    Optional<DicomInstance> findByOrthancInstanceId(String orthancInstanceId);
    
    Optional<DicomInstance> findBySopInstanceUID(String sopInstanceUID);
    
    List<DicomInstance> findBySeriesId(String seriesId);
    
    List<DicomInstance> findBySeriesIdOrderByInstanceNumberAsc(String seriesId);
    
    @Query("SELECT di FROM DicomInstance di WHERE di.series.id = :seriesId ORDER BY di.instanceNumber ASC")
    List<DicomInstance> findAllBySeriesIdOrdered(@Param("seriesId") String seriesId);
}


