-- =====================================================
-- DICOM Integration Database Schema
-- =====================================================
-- This script adds tables and columns for DICOM integration
-- with Orthanc PACS server
-- =====================================================

USE dental_clinic;

-- =====================================================
-- 1. Create dicom_study table
-- =====================================================
-- Stores DICOM Study information linked to patients and examinations
CREATE TABLE IF NOT EXISTS `dicom_study` (
  `id` varchar(255) NOT NULL,
  `patient_id` varchar(255) NOT NULL,
  `examination_id` varchar(255) DEFAULT NULL,
  `treatment_phase_id` varchar(255) DEFAULT NULL,
  `orthanc_study_id` varchar(255) NOT NULL COMMENT 'Study ID from Orthanc PACS',
  `study_instance_uid` varchar(255) NOT NULL COMMENT 'DICOM StudyInstanceUID tag',
  `study_date` date DEFAULT NULL COMMENT 'DICOM StudyDate tag',
  `study_time` time DEFAULT NULL COMMENT 'DICOM StudyTime tag',
  `study_description` varchar(500) DEFAULT NULL COMMENT 'DICOM StudyDescription tag',
  `accession_number` varchar(255) DEFAULT NULL COMMENT 'DICOM AccessionNumber tag',
  `modality` varchar(50) DEFAULT NULL COMMENT 'DICOM Modality tag (CT, XR, etc.)',
  `referring_physician_name` varchar(255) DEFAULT NULL COMMENT 'DICOM ReferringPhysicianName tag',
  `patient_name` varchar(255) DEFAULT NULL COMMENT 'DICOM PatientName tag',
  `patient_id_dicom` varchar(255) DEFAULT NULL COMMENT 'DICOM PatientID tag',
  `patient_birth_date` date DEFAULT NULL COMMENT 'DICOM PatientBirthDate tag',
  `patient_sex` varchar(10) DEFAULT NULL COMMENT 'DICOM PatientSex tag',
  `number_of_study_related_series` int DEFAULT 0 COMMENT 'Number of series in this study',
  `number_of_study_related_instances` int DEFAULT 0 COMMENT 'Total number of instances in this study',
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_orthanc_study_id` (`orthanc_study_id`),
  UNIQUE KEY `UK_study_instance_uid` (`study_instance_uid`),
  KEY `IDX_patient_id` (`patient_id`),
  KEY `IDX_examination_id` (`examination_id`),
  KEY `IDX_treatment_phase_id` (`treatment_phase_id`),
  KEY `IDX_study_date` (`study_date`),
  CONSTRAINT `FK_dicom_study_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_dicom_study_examination` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_dicom_study_treatment_phase` FOREIGN KEY (`treatment_phase_id`) REFERENCES `treatment_phases` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- 2. Create dicom_series table
-- =====================================================
-- Stores DICOM Series information within a Study
CREATE TABLE IF NOT EXISTS `dicom_series` (
  `id` varchar(255) NOT NULL,
  `study_id` varchar(255) NOT NULL,
  `orthanc_series_id` varchar(255) NOT NULL COMMENT 'Series ID from Orthanc PACS',
  `series_instance_uid` varchar(255) NOT NULL COMMENT 'DICOM SeriesInstanceUID tag',
  `series_number` int DEFAULT NULL COMMENT 'DICOM SeriesNumber tag',
  `series_description` varchar(500) DEFAULT NULL COMMENT 'DICOM SeriesDescription tag',
  `series_date` date DEFAULT NULL COMMENT 'DICOM SeriesDate tag',
  `series_time` time DEFAULT NULL COMMENT 'DICOM SeriesTime tag',
  `modality` varchar(50) DEFAULT NULL COMMENT 'DICOM Modality tag',
  `body_part_examined` varchar(255) DEFAULT NULL COMMENT 'DICOM BodyPartExamined tag',
  `protocol_name` varchar(255) DEFAULT NULL COMMENT 'DICOM ProtocolName tag',
  `number_of_series_related_instances` int DEFAULT 0 COMMENT 'Number of instances in this series',
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_orthanc_series_id` (`orthanc_series_id`),
  UNIQUE KEY `UK_series_instance_uid` (`series_instance_uid`),
  KEY `IDX_study_id` (`study_id`),
  KEY `IDX_series_number` (`series_number`),
  CONSTRAINT `FK_dicom_series_study` FOREIGN KEY (`study_id`) REFERENCES `dicom_study` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- 3. Create dicom_instance table
-- =====================================================
-- Stores individual DICOM Instance (image) information
CREATE TABLE IF NOT EXISTS `dicom_instance` (
  `id` varchar(255) NOT NULL,
  `series_id` varchar(255) NOT NULL,
  `orthanc_instance_id` varchar(255) NOT NULL COMMENT 'Instance ID from Orthanc PACS',
  `sop_instance_uid` varchar(255) NOT NULL COMMENT 'DICOM SOPInstanceUID tag',
  `sop_class_uid` varchar(255) DEFAULT NULL COMMENT 'DICOM SOPClassUID tag',
  `instance_number` int DEFAULT NULL COMMENT 'DICOM InstanceNumber tag',
  `content_date` date DEFAULT NULL COMMENT 'DICOM ContentDate tag',
  `content_time` time DEFAULT NULL COMMENT 'DICOM ContentTime tag',
  `image_type` varchar(255) DEFAULT NULL COMMENT 'DICOM ImageType tag',
  `rows` int DEFAULT NULL COMMENT 'DICOM Rows tag - image height in pixels',
  `columns` int DEFAULT NULL COMMENT 'DICOM Columns tag - image width in pixels',
  `bits_allocated` int DEFAULT NULL COMMENT 'DICOM BitsAllocated tag',
  `bits_stored` int DEFAULT NULL COMMENT 'DICOM BitsStored tag',
  `samples_per_pixel` int DEFAULT NULL COMMENT 'DICOM SamplesPerPixel tag',
  `photometric_interpretation` varchar(50) DEFAULT NULL COMMENT 'DICOM PhotometricInterpretation tag',
  `file_size` bigint DEFAULT NULL COMMENT 'File size in bytes',
  `file_path` varchar(1000) DEFAULT NULL COMMENT 'Optional local file path if stored separately',
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_orthanc_instance_id` (`orthanc_instance_id`),
  UNIQUE KEY `UK_sop_instance_uid` (`sop_instance_uid`),
  KEY `IDX_series_id` (`series_id`),
  KEY `IDX_instance_number` (`instance_number`),
  CONSTRAINT `FK_dicom_instance_series` FOREIGN KEY (`series_id`) REFERENCES `dicom_series` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- 4. Add dicom_study_id column to examination table
-- =====================================================
-- Link examination to DICOM study (nullable - not all examinations have DICOM)
ALTER TABLE `examination`
ADD COLUMN `dicom_study_id` varchar(255) DEFAULT NULL AFTER `appointment_id`,
ADD KEY `IDX_examination_dicom_study` (`dicom_study_id`),
ADD CONSTRAINT `FK_examination_dicom_study` FOREIGN KEY (`dicom_study_id`) REFERENCES `dicom_study` (`id`) ON DELETE SET NULL;

-- =====================================================
-- 5. Add dicom_study_id column to treatment_phases table
-- =====================================================
-- Link treatment phase to DICOM study (nullable - not all phases have DICOM)
ALTER TABLE `treatment_phases`
ADD COLUMN `dicom_study_id` varchar(255) DEFAULT NULL AFTER `treatment_plans_id`,
ADD KEY `IDX_treatment_phases_dicom_study` (`dicom_study_id`),
ADD CONSTRAINT `FK_treatment_phases_dicom_study` FOREIGN KEY (`dicom_study_id`) REFERENCES `dicom_study` (`id`) ON DELETE SET NULL;

-- =====================================================
-- 6. Create indexes for better query performance
-- =====================================================
-- Additional indexes for common query patterns
CREATE INDEX `IDX_dicom_study_patient_date` ON `dicom_study` (`patient_id`, `study_date` DESC);
CREATE INDEX `IDX_dicom_series_study_modality` ON `dicom_series` (`study_id`, `modality`);
CREATE INDEX `IDX_dicom_instance_series_number` ON `dicom_instance` (`series_id`, `instance_number`);

-- =====================================================
-- 7. Optional: Create view for DICOM study summary
-- =====================================================
-- View to easily query DICOM studies with patient and related entity info
-- Note: Adjust patient_name field based on your user_detail table structure
CREATE OR REPLACE VIEW `v_dicom_study_summary` AS
SELECT 
  ds.id,
  ds.orthanc_study_id,
  ds.study_instance_uid,
  ds.study_date,
  ds.study_description,
  ds.modality,
  ds.number_of_study_related_series,
  ds.number_of_study_related_instances,
  p.id AS patient_id,
  ud.full_name AS patient_name,
  e.id AS examination_id,
  tp.id AS treatment_phase_id,
  ds.created_at,
  ds.updated_at
FROM dicom_study ds
INNER JOIN patient p ON ds.patient_id = p.id
LEFT JOIN user_detail ud ON p.user_id = ud.user_id
LEFT JOIN examination e ON ds.examination_id = e.id
LEFT JOIN treatment_phases tp ON ds.treatment_phase_id = tp.id;

-- =====================================================
-- End of DICOM Integration Schema
-- =====================================================

