package com.example.pknk.domain.entity.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ai_detection")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AiDetection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ai_analysis_id", nullable = false)
    AiAnalysis aiAnalysis;

    @Column(name = "class_id")
    Integer classId;

    @Column(name = "class_name")
    String className;

    @Column(name = "confidence")
    Double confidence;

    @Column(name = "x_min")
    Double xMin;

    @Column(name = "y_min")
    Double yMin;

    @Column(name = "x_max")
    Double xMax;

    @Column(name = "y_max")
    Double yMax;
}


