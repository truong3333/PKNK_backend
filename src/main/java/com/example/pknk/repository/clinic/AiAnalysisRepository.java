package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.AiAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiAnalysisRepository extends JpaRepository<AiAnalysis, String> {
    
    Optional<AiAnalysis> findByDicomInstanceId(String dicomInstanceId);
    
    Optional<AiAnalysis> findByImageId(String imageId);
    
    List<AiAnalysis> findByDicomInstanceIdOrderByCreatedAtDesc(String dicomInstanceId);
    
    List<AiAnalysis> findByImageIdOrderByCreatedAtDesc(String imageId);
}


