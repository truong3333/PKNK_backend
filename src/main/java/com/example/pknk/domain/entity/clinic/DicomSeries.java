package com.example.pknk.domain.entity.clinic;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "dicom_series")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DicomSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    DicomStudy study;

    @Column(name = "orthanc_series_id", nullable = false, unique = true)
    String orthancSeriesId;

    @Column(name = "series_instance_uid", nullable = false, unique = true)
    String seriesInstanceUID;

    @Column(name = "series_number")
    Integer seriesNumber;

    @Column(name = "series_description", length = 500)
    String seriesDescription;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "series_date")
    LocalDate seriesDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "series_time")
    LocalTime seriesTime;

    @Column(name = "modality", length = 50)
    String modality;

    @Column(name = "body_part_examined")
    String bodyPartExamined;

    @Column(name = "protocol_name")
    String protocolName;

    @Column(name = "number_of_series_related_instances")
    Integer numberOfSeriesRelatedInstances;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt;

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


