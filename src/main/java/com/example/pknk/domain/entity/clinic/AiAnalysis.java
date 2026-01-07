package com.example.pknk.domain.entity.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ai_analysis")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AiAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dicom_instance_id", nullable = true)
    DicomInstance dicomInstance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = true)
    Image image;

    @Column(name = "confidence_threshold")
    Double confidenceThreshold;

    @Column(name = "total_detections")
    Integer totalDetections;

    @Column(name = "image_width")
    Integer imageWidth;

    @Column(name = "image_height")
    Integer imageHeight;

    @Column(name = "analysis_status")
    String analysisStatus; // PENDING, COMPLETED, FAILED

    @Column(name = "error_message", length = 1000)
    String errorMessage;

    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "aiAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AiDetection> detections;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


