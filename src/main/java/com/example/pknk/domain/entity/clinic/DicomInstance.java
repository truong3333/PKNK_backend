package com.example.pknk.domain.entity.clinic;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "dicom_instance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DicomInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false)
    DicomSeries series;

    @Column(name = "orthanc_instance_id", nullable = false, unique = true)
    String orthancInstanceId;

    @Column(name = "sop_instance_uid", nullable = false, unique = true)
    String sopInstanceUID;

    @Column(name = "sop_class_uid")
    String sopClassUID;

    @Column(name = "instance_number")
    Integer instanceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "content_date")
    LocalDate contentDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "content_time")
    LocalTime contentTime;

    @Column(name = "image_type")
    String imageType;

    @Column(name = "rows")
    Integer rows;

    @Column(name = "columns")
    Integer columns;

    @Column(name = "bits_allocated")
    Integer bitsAllocated;

    @Column(name = "bits_stored")
    Integer bitsStored;

    @Column(name = "samples_per_pixel")
    Integer samplesPerPixel;

    @Column(name = "photometric_interpretation", length = 50)
    String photometricInterpretation;

    @Column(name = "file_size")
    Long fileSize;

    @Column(name = "file_path", length = 1000)
    String filePath;

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

