package com.example.pknk.domain.entity.clinic;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "dicom_study")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DicomStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    com.example.pknk.domain.entity.user.Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id", nullable = true)
    Examination examination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treatment_phase_id", nullable = true)
    TreatmentPhases treatmentPhase;

    @Column(name = "orthanc_study_id", nullable = false, unique = true)
    String orthancStudyId;

    @Column(name = "study_instance_uid", nullable = false, unique = true)
    String studyInstanceUID;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "study_date")
    LocalDate studyDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "study_time")
    LocalTime studyTime;

    @Column(name = "study_description", length = 500)
    String studyDescription;

    @Column(name = "accession_number")
    String accessionNumber;

    @Column(name = "modality", length = 50)
    String modality;

    @Column(name = "referring_physician_name")
    String referringPhysicianName;

    @Column(name = "patient_name")
    String patientName;

    @Column(name = "patient_id_dicom")
    String patientIdDicom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "patient_birth_date")
    LocalDate patientBirthDate;

    @Column(name = "patient_sex", length = 10)
    String patientSex;

    @Column(name = "number_of_study_related_series")
    Integer numberOfStudyRelatedSeries;

    @Column(name = "number_of_study_related_instances")
    Integer numberOfStudyRelatedInstances;

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


