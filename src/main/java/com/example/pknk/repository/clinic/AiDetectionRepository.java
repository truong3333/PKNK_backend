package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.AiDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiDetectionRepository extends JpaRepository<AiDetection, String> {
    
    List<AiDetection> findByAiAnalysisId(String aiAnalysisId);
    
    void deleteByAiAnalysisId(String aiAnalysisId);
}


