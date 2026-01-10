-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: dental_clinic
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ai_analysis`
--

DROP TABLE IF EXISTS `ai_analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_analysis` (
  `id` varchar(255) NOT NULL,
  `analysis_status` varchar(255) DEFAULT NULL,
  `confidence_threshold` double DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `error_message` varchar(1000) DEFAULT NULL,
  `image_height` int DEFAULT NULL,
  `image_width` int DEFAULT NULL,
  `total_detections` int DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `dicom_instance_id` varchar(255) DEFAULT NULL,
  `image_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKockq4m2j1wap6f2vec9g5g43g` (`dicom_instance_id`),
  KEY `FK2tlwqtrjystr59gutcu001ssj` (`image_id`),
  CONSTRAINT `FK2tlwqtrjystr59gutcu001ssj` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `FKockq4m2j1wap6f2vec9g5g43g` FOREIGN KEY (`dicom_instance_id`) REFERENCES `dicom_instance` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_analysis`
--

LOCK TABLES `ai_analysis` WRITE;
/*!40000 ALTER TABLE `ai_analysis` DISABLE KEYS */;
INSERT INTO `ai_analysis` VALUES ('03d5e070-efcc-4780-b2c8-d6d804eef354','COMPLETED',0.25,'2025-12-31 07:36:44.766580',NULL,401,602,0,'2025-12-31 07:36:44.767580',NULL,NULL),('2ad3d24e-32bb-4e6b-8a5e-09c9e2ae98b6','FAILED',0.25,'2025-12-31 06:13:38.234712','Async analysis not fully implemented yet',NULL,NULL,NULL,'2025-12-31 06:13:38.262717','082ffb00-abe5-4bed-a53a-5afdfaa5daf5',NULL),('4fb11a3d-1dd3-4072-baf1-7e59a26b71e2','COMPLETED',1,'2025-12-31 07:44:11.412004',NULL,640,640,0,'2025-12-31 07:44:11.413004',NULL,NULL),('5be6aa1c-1c5b-466e-ba9a-9a05ca02bfe5','COMPLETED',1,'2025-12-31 07:39:08.700551',NULL,640,640,0,'2025-12-31 07:39:08.700551',NULL,NULL),('8c7c9cce-ba5b-4242-b6f9-880c9e84ca0a','COMPLETED',0.5,'2025-12-31 07:38:53.027976',NULL,640,640,0,'2025-12-31 07:38:53.028978',NULL,NULL),('8d8f1471-f2c3-40ad-989f-757a2ce9a9bd','COMPLETED',0.5,'2025-12-31 07:43:28.145082',NULL,401,602,0,'2025-12-31 07:43:28.145082',NULL,NULL);
/*!40000 ALTER TABLE `ai_analysis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ai_detection`
--

DROP TABLE IF EXISTS `ai_detection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ai_detection` (
  `id` varchar(255) NOT NULL,
  `class_id` int DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `confidence` double DEFAULT NULL,
  `x_max` double DEFAULT NULL,
  `x_min` double DEFAULT NULL,
  `y_max` double DEFAULT NULL,
  `y_min` double DEFAULT NULL,
  `ai_analysis_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK11f6bspw8ghfiuf89hjjva9yr` (`ai_analysis_id`),
  CONSTRAINT `FK11f6bspw8ghfiuf89hjjva9yr` FOREIGN KEY (`ai_analysis_id`) REFERENCES `ai_analysis` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ai_detection`
--

LOCK TABLES `ai_detection` WRITE;
/*!40000 ALTER TABLE `ai_detection` DISABLE KEYS */;
/*!40000 ALTER TABLE `ai_detection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` varchar(255) NOT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `notification` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `doctor_id` varchar(255) DEFAULT NULL,
  `patient_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoeb98n82eph1dx43v3y2bcmsl` (`doctor_id`),
  KEY `FK4apif2ewfyf14077ichee8g06` (`patient_id`),
  CONSTRAINT `FK4apif2ewfyf14077ichee8g06` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FKoeb98n82eph1dx43v3y2bcmsl` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('0767466a-b73f-427f-ba9f-1db6d2c81efc','2026-01-10 14:00:00.000000','',NULL,'Scheduled','Nhiều dịch vụ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('0dfd9eed-0472-47e8-afc2-7fce0ade5f86','2025-12-11 16:00:00.000000','Tesssst',NULL,'Cancel','Rạch lợi trùm','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('19c541ec-3647-4af7-a66f-9213be474b28','2025-12-19 09:00:00.000000','Test 9h 19 12',NULL,'Cancel','Nhổ răng sữa bôi tê','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('20b7a9ff-53f1-42d7-8f29-349fb20cbcbc','2026-04-16 09:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('2bba223d-3a8b-4aa3-afc6-8b1ecd56f2f1','2025-12-13 09:00:00.000000','Khám theo tiến trình điều trị',NULL,'Scheduled','TreatmentPhases','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('3a260be1-c5c4-4f8c-8d51-a41aafef7f2c','2026-04-16 11:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('3e882101-2833-48fc-af62-9be001e8db7c','2026-01-10 08:00:00.000000','',NULL,'Scheduled','Rạch lợi trùm','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('466f3dc6-3f28-425f-9d24-0ec18f4c7b9e','2026-01-01 09:00:00.000000','Em bị đau răng',NULL,'Scheduled','Rạch lợi trùm','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('5b64f956-1961-4dde-8db2-43809b6721c0','2025-12-04 09:00:00.000000','em bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('7a213f72-4d41-479a-86ae-677c952d455e','2026-04-16 15:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('81a5acfc-6fd8-4038-a665-3740138c9966','2026-01-10 09:00:00.000000','',NULL,'Scheduled','Nhiều dịch vụ','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('8f2c1fab-8391-4026-b935-7499b6db6d1d','2026-04-16 14:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('9a088238-fdcb-43d3-9709-578b49e1ff72','2026-04-16 10:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('a51bb25b-9486-4e0b-aa1b-ba2a93e8b188','2025-12-11 16:00:00.000000','Tesstttt',NULL,'Scheduled','Niềng răng mắc cài kim loại','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('c06c1970-2dd4-40f6-a048-e1cac62170b4','2026-04-16 13:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('c805e8b8-d190-412c-b6f5-78061f7430ca','2025-12-11 15:00:00.000000','Test','Done','Scheduled','Trụ Neoden – Thụy Sĩ','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('d4f3b21b-082f-46db-9a2a-267fe243ba58','2025-12-21 16:00:00.000000','Testtt',NULL,'Scheduled','Nhiều dịch vụ','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('d88d7c8d-1456-4291-9405-2f9f6f77ec01','2025-12-11 14:00:00.000000','','Done','Done','Nhổ răng khôn hàm dưới','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('ee64c519-25b7-4bf8-86f2-50b699579e97','2025-12-19 08:00:00.000000','Test 19/12',NULL,'Cancel','Niềng răng mắc cài kim loại','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de'),('ef998bb2-c0c8-4120-9fc6-9f49cfd0eac4','2026-04-16 08:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('f6d21d2f-5ec2-47dc-95e5-dc65a6bf970b','2026-04-16 12:00:00.000000','đang bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_list_dental_services_entity`
--

DROP TABLE IF EXISTS `appointment_list_dental_services_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment_list_dental_services_entity` (
  `appointment_id` varchar(255) NOT NULL,
  `list_dental_services_entity_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  KEY `FK7s2kby1gmu4ful2seyino258c` (`list_dental_services_entity_id`),
  KEY `FKcgb9y4mv3q50lk6liieyp4tut` (`appointment_id`),
  CONSTRAINT `FK7s2kby1gmu4ful2seyino258c` FOREIGN KEY (`list_dental_services_entity_id`) REFERENCES `dental_services_entity` (`id`),
  CONSTRAINT `FKcgb9y4mv3q50lk6liieyp4tut` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_list_dental_services_entity`
--

LOCK TABLES `appointment_list_dental_services_entity` WRITE;
/*!40000 ALTER TABLE `appointment_list_dental_services_entity` DISABLE KEYS */;
INSERT INTO `appointment_list_dental_services_entity` VALUES ('5b64f956-1961-4dde-8db2-43809b6721c0','92c324f2-1ff1-434a-a8ac-3000902e813a',NULL,NULL,NULL,NULL,NULL),('d88d7c8d-1456-4291-9405-2f9f6f77ec01','10a72aac-deb8-4db8-be92-d2d1d6ed58cd',NULL,NULL,NULL,NULL,NULL),('c805e8b8-d190-412c-b6f5-78061f7430ca','9b104d38-d289-4032-936f-2f94a3491b73',NULL,NULL,NULL,NULL,NULL),('0dfd9eed-0472-47e8-afc2-7fce0ade5f86','0c67d5b2-fcdc-44ce-961c-a0e324572cd8',NULL,NULL,NULL,NULL,NULL),('d4f3b21b-082f-46db-9a2a-267fe243ba58','37b37edd-6635-4152-9528-773c5e912453',35000000,'Niềng răng mắc cài kim loại',1,'2 Hàm',35000000),('d4f3b21b-082f-46db-9a2a-267fe243ba58','10a72aac-deb8-4db8-be92-d2d1d6ed58cd',3000000,'Nhổ răng khôn hàm dưới',1,'Răng',3000000),('d4f3b21b-082f-46db-9a2a-267fe243ba58','6a8efba5-4022-40b8-b48a-c0d7f161bcd5',3500000,'Răng sứ Venus',1,'Răng',3500000),('3a260be1-c5c4-4f8c-8d51-a41aafef7f2c','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('ef998bb2-c0c8-4120-9fc6-9f49cfd0eac4','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('20b7a9ff-53f1-42d7-8f29-349fb20cbcbc','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('9a088238-fdcb-43d3-9709-578b49e1ff72','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('f6d21d2f-5ec2-47dc-95e5-dc65a6bf970b','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('c06c1970-2dd4-40f6-a048-e1cac62170b4','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('8f2c1fab-8391-4026-b935-7499b6db6d1d','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('7a213f72-4d41-479a-86ae-677c952d455e','92c324f2-1ff1-434a-a8ac-3000902e813a',45000000,'Niềng răng mắc cài sứ',1,'2 Hàm',45000000),('466f3dc6-3f28-425f-9d24-0ec18f4c7b9e','0c67d5b2-fcdc-44ce-961c-a0e324572cd8',1100000,'Rạch lợi trùm',1,'Răng',1100000),('0767466a-b73f-427f-ba9f-1db6d2c81efc','37b37edd-6635-4152-9528-773c5e912453',35000000,'Niềng răng mắc cài kim loại',1,'2 Hàm',35000000),('0767466a-b73f-427f-ba9f-1db6d2c81efc','9892d757-0af9-4b55-a0ff-26b2ea3eb47a',2500000,'Răng sứ Titan',1,'Răng',2500000),('3e882101-2833-48fc-af62-9be001e8db7c','0c67d5b2-fcdc-44ce-961c-a0e324572cd8',1100000,'Rạch lợi trùm',1,'Răng',1100000),('81a5acfc-6fd8-4038-a665-3740138c9966','0c67d5b2-fcdc-44ce-961c-a0e324572cd8',1100000,'Rạch lợi trùm',1,'Răng',1100000),('81a5acfc-6fd8-4038-a665-3740138c9966','10a72aac-deb8-4db8-be92-d2d1d6ed58cd',3000000,'Nhổ răng khôn hàm dưới',1,'Răng',3000000);
/*!40000 ALTER TABLE `appointment_list_dental_services_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_date_time`
--

DROP TABLE IF EXISTS `booking_date_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_date_time` (
  `id` varchar(255) NOT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `doctor_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKorpat3h7ctlc6ubt2c0jdvty4` (`doctor_id`),
  CONSTRAINT `FKorpat3h7ctlc6ubt2c0jdvty4` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_date_time`
--

LOCK TABLES `booking_date_time` WRITE;
/*!40000 ALTER TABLE `booking_date_time` DISABLE KEYS */;
INSERT INTO `booking_date_time` VALUES ('023f39a6-ff11-45e9-859e-3f2f7fc50e9d','2026-04-16 11:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('10a515eb-55e7-43ab-9d68-37b1a92c1d00','2025-12-21 16:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('18506f91-e7e1-424c-bf6f-a6adcde5c4de','2025-12-11 15:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('2185493e-666e-4fa2-9119-712f5614c950','2025-12-11 16:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('26f9b84d-b531-47bc-86d6-f6f916a528c6','2026-01-01 09:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('26fa6749-a8ac-45ff-8c09-2f8660bbc15a','2026-01-10 08:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('2e73fb08-7d32-4bb0-8373-1bcafc76902f','2026-04-16 10:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('4b1e7d79-d6dd-4a1e-adb3-994a4c7616bc','2026-04-16 12:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('4cbfd9a2-d66b-44d2-b29f-ce3f5480cd68','2026-04-16 09:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('4e0b22e0-0792-4438-a156-5182d0eb9529','2026-04-16 08:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('50225060-80b1-4741-9402-68756b05ed58','2025-12-11 14:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('693e6977-9ede-4c78-a16b-f3c7a3711f4e','2026-01-10 09:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('7b05f058-2906-47c9-9f0f-f2839dfde03f','2026-01-10 14:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('96672183-47ea-48c2-8190-f99c5d033168','2026-04-16 14:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('a539831e-97d1-42bf-9c5a-e8393c868f24','2026-04-16 15:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('ac399854-0908-422f-804c-8d04bccf1c25','2026-04-16 13:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a'),('d4ea2441-2b24-4def-911a-33e79336f95e','2025-12-13 09:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('da9f29a3-02dc-4138-ad85-9b420b78ea5a','2025-12-04 09:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a');
/*!40000 ALTER TABLE `booking_date_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_dental`
--

DROP TABLE IF EXISTS `category_dental`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_dental` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_dental`
--

LOCK TABLES `category_dental` WRITE;
/*!40000 ALTER TABLE `category_dental` DISABLE KEYS */;
INSERT INTO `category_dental` VALUES ('0254aa4d-9558-4c9d-9537-ebc9c228cae7','Niềng Răng'),('6ddc7ec5-9868-4dd6-9420-c4935583b67d','Nhổ răng'),('71f1f9fb-7f05-4d1a-9498-2b40a4496fc1','Trồng răng sứ'),('7adba041-bf88-4e8e-820b-f95af4ba6d37','Trồng răng Implant');
/*!40000 ALTER TABLE `category_dental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost`
--

DROP TABLE IF EXISTS `cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cost` (
  `id` varchar(255) NOT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_cost` double NOT NULL,
  `vnp_txn_ref` varchar(255) DEFAULT NULL,
  `patient_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `treatment_plan_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKexauo4htrd4c6j98vv31lp372` (`patient_id`),
  KEY `FK_cost_treatment_plans` (`treatment_plan_id`),
  CONSTRAINT `FK_cost_treatment_plans` FOREIGN KEY (`treatment_plan_id`) REFERENCES `treatment_plans` (`id`),
  CONSTRAINT `FKexauo4htrd4c6j98vv31lp372` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FKoonc1etmdkipahoaglmp3ntvi` FOREIGN KEY (`treatment_plan_id`) REFERENCES `treatment_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost`
--

LOCK TABLES `cost` WRITE;
/*!40000 ALTER TABLE `cost` DISABLE KEYS */;
INSERT INTO `cost` VALUES ('5847aef3-8076-4eff-af43-a03276839d9d','2025-12-19','VNPay-NCB','paid','Tiến trình điều trị: Phase 1 - Sửa soạn xoang trám, trám bít Composite, đánh bóng.\r\n\r\nGhi chú: Đã loại bỏ hoàn toàn mô sâu. Bệnh nhân không có biểu hiện đau. Xoang trám được cách ly tốt và trám bằng Composite khối A2. Hướng dẫn bệnh nhân chăm sóc tại nhà.',4155000,'62203959','a68e2737-ae82-4e1a-abd1-c985d14912de','phase_payment','7e2663c4-21ea-483e-a80c-e0bfecd487e1'),('716aa04d-afbc-420f-bd62-73b03d4824c0','2026-01-10','VNPay-NCB','paid','Tiến trình điều trị: Phase 2 - Testtttt\r\n\r\nGhi chú: Testttt',2625000,'88776950','a68e2737-ae82-4e1a-abd1-c985d14912de','phase_payment','7e2663c4-21ea-483e-a80c-e0bfecd487e1'),('741932d7-500f-49b9-8a77-2cce87e94a31',NULL,NULL,'wait','Tiến trình điều trị: Test - Test\r\n\r\nGhi chú: Test',5035000,NULL,'a68e2737-ae82-4e1a-abd1-c985d14912de','phase_payment','7e2663c4-21ea-483e-a80c-e0bfecd487e1'),('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',NULL,NULL,'wait','Khám theo lịch hẹn: 2025-12-11T14:00',20000000,NULL,'a68e2737-ae82-4e1a-abd1-c985d14912de','phase_payment',NULL);
/*!40000 ALTER TABLE `cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost_list_dental_service_entity_order`
--

DROP TABLE IF EXISTS `cost_list_dental_service_entity_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cost_list_dental_service_entity_order` (
  `cost_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `list_dental_services_entity_id` varchar(255) DEFAULT NULL,
  KEY `FK2v0feum1rn94phqttsg5bhy8r` (`cost_id`),
  CONSTRAINT `FK2v0feum1rn94phqttsg5bhy8r` FOREIGN KEY (`cost_id`) REFERENCES `cost` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost_list_dental_service_entity_order`
--

LOCK TABLES `cost_list_dental_service_entity_order` WRITE;
/*!40000 ALTER TABLE `cost_list_dental_service_entity_order` DISABLE KEYS */;
INSERT INTO `cost_list_dental_service_entity_order` VALUES ('716aa04d-afbc-420f-bd62-73b03d4824c0',50000,'Nhổ răng sữa bôi tê',1,'Răng',50000,NULL,NULL),('716aa04d-afbc-420f-bd62-73b03d4824c0',2500000,'Răng sứ Titan',1,'Răng',2500000,NULL,NULL),('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',16500000,'Răng sứ Ceramill',3,'Răng',5500000,NULL,NULL),('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',2000000,'Nhổ răng khôn hàm trên',1,'Răng',2000000,NULL,NULL),('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',1500000,'Nhổ răng hàm lớn (6,7)',1,'Răng',1500000,NULL,NULL),('741932d7-500f-49b9-8a77-2cce87e94a31',5000000,'Niềng răng tháo lắp',1,'1 Hàm',5000000,NULL,NULL);
/*!40000 ALTER TABLE `cost_list_dental_service_entity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost_list_prescription_order`
--

DROP TABLE IF EXISTS `cost_list_prescription_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cost_list_prescription_order` (
  `cost_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `dosage` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `frequency` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  KEY `FKhrrgi74x4n21930f17mj0no58` (`cost_id`),
  CONSTRAINT `FKhrrgi74x4n21930f17mj0no58` FOREIGN KEY (`cost_id`) REFERENCES `cost` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost_list_prescription_order`
--

LOCK TABLES `cost_list_prescription_order` WRITE;
/*!40000 ALTER TABLE `cost_list_prescription_order` DISABLE KEYS */;
INSERT INTO `cost_list_prescription_order` VALUES ('716aa04d-afbc-420f-bd62-73b03d4824c0',35000,'500mg','sau ăn 30 phút','3 lần/ngày','Amoxicillin 500mg','Kháng sinh sau nhổ răng, dùng trong 5 ngày',1,35000),('716aa04d-afbc-420f-bd62-73b03d4824c0',40000,'400mg','sau ăn 30 phút','3 lần/ngày','Ibuprofen 400mg\',\'400mg\',\'sau ăn 30 phút','Giảm đau và chống viêm, không dùng khi đau dạ dày',1,40000),('741932d7-500f-49b9-8a77-2cce87e94a31',35000,'500mg','sau ăn 30 phút','3 lần/ngày','Amoxicillin 500mg','Kháng sinh sau nhổ răng, dùng trong 5 ngày',1,35000);
/*!40000 ALTER TABLE `cost_list_prescription_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dental_services_entity`
--

DROP TABLE IF EXISTS `dental_services_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dental_services_entity` (
  `id` varchar(255) NOT NULL,
  `discount` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double NOT NULL,
  `category_dental_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1uot0jm4np2m8hiqpfl0sfsui` (`category_dental_id`),
  CONSTRAINT `FK1uot0jm4np2m8hiqpfl0sfsui` FOREIGN KEY (`category_dental_id`) REFERENCES `category_dental` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dental_services_entity`
--

LOCK TABLES `dental_services_entity` WRITE;
/*!40000 ALTER TABLE `dental_services_entity` DISABLE KEYS */;
INSERT INTO `dental_services_entity` VALUES ('0c67d5b2-fcdc-44ce-961c-a0e324572cd8',0,'Rạch lợi trùm','Răng',1100000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('10a72aac-deb8-4db8-be92-d2d1d6ed58cd',0,'Nhổ răng khôn hàm dưới','Răng',3000000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('1fb2a1e6-6a99-45e7-b35f-bbd6f3f7e190',0,'Trụ Implant Mỹ','Răng',23000000,'7adba041-bf88-4e8e-820b-f95af4ba6d37'),('37b37edd-6635-4152-9528-773c5e912453',0,'Niềng răng mắc cài kim loại','2 Hàm',35000000,'0254aa4d-9558-4c9d-9537-ebc9c228cae7'),('41b78306-2bfb-455c-a443-fe6ea4aac064',0,'Răng sứ Veneer Lisi','Răng',11000000,'71f1f9fb-7f05-4d1a-9498-2b40a4496fc1'),('4919bd1c-be71-472d-9ca3-9d7404dbfe4c',0,'Trụ Implant Hàn Quốc','Răng',15000000,'7adba041-bf88-4e8e-820b-f95af4ba6d37'),('52a404a7-a784-43c6-b9ff-d6da41ac887c',0,'Răng sứ Ceramill','Răng',5500000,'71f1f9fb-7f05-4d1a-9498-2b40a4496fc1'),('579cb2a6-74d2-4aba-ac95-36f1fb1a7fc6',0,'Niềng răng tháo lắp','1 Hàm',5000000,'0254aa4d-9558-4c9d-9537-ebc9c228cae7'),('6a8efba5-4022-40b8-b48a-c0d7f161bcd5',0,'Răng sứ Venus','Răng',3500000,'71f1f9fb-7f05-4d1a-9498-2b40a4496fc1'),('7edc02d2-c834-4dfd-af74-c3cc45d8a6a6',0,'Nhổ răng sữa bôi tê','Răng',50000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('84d03d18-238e-4de3-97ef-cb90886d8147',0,'Nhổ răng sữa tiêm tê','Răng',100000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('92c324f2-1ff1-434a-a8ac-3000902e813a',0,'Niềng răng mắc cài sứ','2 Hàm',45000000,'0254aa4d-9558-4c9d-9537-ebc9c228cae7'),('9892d757-0af9-4b55-a0ff-26b2ea3eb47a',0,'Răng sứ Titan','Răng',2500000,'71f1f9fb-7f05-4d1a-9498-2b40a4496fc1'),('9b104d38-d289-4032-936f-2f94a3491b73',0,'Trụ Neoden – Thụy Sĩ','Răng',40000000,'7adba041-bf88-4e8e-820b-f95af4ba6d37'),('b92ca6f0-fb36-4973-ac06-bf92abf129f1',0,'Răng sứ Orodent Gold','Răng',12000000,'71f1f9fb-7f05-4d1a-9498-2b40a4496fc1'),('b9b52255-015d-4e34-9396-4523ebc3f102',0,'NobelActive TiUltra/NobelParallel TiUltra','Răng',50000000,'0254aa4d-9558-4c9d-9537-ebc9c228cae7'),('c6f3b00a-d154-4b27-bf82-a033e1ec48b8',0,'Nhổ răng hàm nhỏ (4,5)','Răng',1000000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('c95db755-e786-4425-89cd-79e8731e912f',0,'Nhổ răng khôn hàm trên','Răng',2000000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('d8f87fb3-3e56-4bb5-bd4e-1bfe1b0d9648',0,'Nhổ răng cửa (1,2,3)','Răng',800000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('d9ce7657-c527-429a-ac32-f0cf2e46ecd7',0,'Nhổ răng hàm lớn (6,7)','Răng',1500000,'6ddc7ec5-9868-4dd6-9420-c4935583b67d'),('ea67fc0e-a81a-4bf7-b904-b65348289273',0,'Mắc cài tự động','1 Hàm',5000000,'0254aa4d-9558-4c9d-9537-ebc9c228cae7'),('ebe2afcf-91f3-4710-8fbf-141ba4e733ee',0,'Trụ Neoden – Thụy Sĩ','Răng',30000000,'7adba041-bf88-4e8e-820b-f95af4ba6d37');
/*!40000 ALTER TABLE `dental_services_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dicom_instance`
--

DROP TABLE IF EXISTS `dicom_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dicom_instance` (
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
  KEY `IDX_dicom_instance_series_number` (`series_id`,`instance_number`),
  CONSTRAINT `FK_dicom_instance_series` FOREIGN KEY (`series_id`) REFERENCES `dicom_series` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dicom_instance`
--

LOCK TABLES `dicom_instance` WRITE;
/*!40000 ALTER TABLE `dicom_instance` DISABLE KEYS */;
INSERT INTO `dicom_instance` VALUES ('082ffb00-abe5-4bed-a53a-5afdfaa5daf5','ab17d291-8be8-4bb8-b6fb-8bd16798d29f','563a1a6f-b5e51523-1cdd8ee3-f976af90-e3928e3a','2.16.840.1.113669.632.10.20230920.110420558.1830070.1040','1.2.840.10008.5.1.4.1.1.2.1',1385,'2023-09-20','11:04:10','ORIGINAL',534,534,16,12,1,'MONOCHROME2',303860346,NULL,'2025-12-25 19:23:09.306606','2025-12-25 19:23:09.306606'),('15267369-6c93-490e-88f0-72e7578ff6bb','04c6c9ad-15ab-47e0-8657-0e696879784b','95c4d831-a1cba480-dd53ef5d-44326669-a57e7c5c','1.2.276.0.7230010.3.1.4.3835155082.9388.1766656810.1072','1.2.840.10008.5.1.4.1.1.2',446,'2025-12-25','16:59:20','ORIGINAL/PRIMARY/AXIAL',800,800,16,16,1,'MONOCHROME2',512418,NULL,'2025-12-26 20:53:57.263891','2025-12-26 20:53:57.263891'),('afbc1088-415c-41df-824f-836c6e3f343c','04c6c9ad-15ab-47e0-8657-0e696879784b','09c0df6b-c2a1e250-22146b3e-5344137c-7ef64265','1.2.276.0.7230010.3.1.4.3835155082.9388.1766656810.1067','1.2.840.10008.5.1.4.1.1.2',441,'2025-12-25','16:59:20','ORIGINAL/PRIMARY/AXIAL',800,800,16,16,1,'MONOCHROME2',527024,NULL,'2025-12-26 20:54:35.496951','2025-12-26 20:54:35.496951'),('fd4cb53c-157e-4f70-beaf-9151ad29de5f','04c6c9ad-15ab-47e0-8657-0e696879784b','6ba8cd44-7b21c4a1-79c4f41f-de148536-7d096537','1.2.276.0.7230010.3.1.4.3835155082.9388.1766656810.1076','1.2.840.10008.5.1.4.1.1.2',450,'2025-12-25','16:59:20','ORIGINAL/PRIMARY/AXIAL',800,800,16,16,1,'MONOCHROME2',408052,NULL,'2025-12-26 20:49:10.906593','2025-12-26 20:49:10.906593');
/*!40000 ALTER TABLE `dicom_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dicom_series`
--

DROP TABLE IF EXISTS `dicom_series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dicom_series` (
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
  `number_of_series_related_instances` int DEFAULT '0' COMMENT 'Number of instances in this series',
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_orthanc_series_id` (`orthanc_series_id`),
  UNIQUE KEY `UK_series_instance_uid` (`series_instance_uid`),
  KEY `IDX_study_id` (`study_id`),
  KEY `IDX_series_number` (`series_number`),
  KEY `IDX_dicom_series_study_modality` (`study_id`,`modality`),
  CONSTRAINT `FK_dicom_series_study` FOREIGN KEY (`study_id`) REFERENCES `dicom_study` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dicom_series`
--

LOCK TABLES `dicom_series` WRITE;
/*!40000 ALTER TABLE `dicom_series` DISABLE KEYS */;
INSERT INTO `dicom_series` VALUES ('04c6c9ad-15ab-47e0-8657-0e696879784b','69ec4c09-77d7-40da-99fb-9966acc6e9e3','616d9854-d1fed746-5149be6e-751de221-081d5f23','1.2.276.0.7230010.3.1.3.3835155082.9388.1766656760.625',1,NULL,'2025-12-25','16:59:20','CT','HEAD',NULL,3,'2025-12-26 20:49:10.885592','2025-12-26 20:54:35.538952'),('ab17d291-8be8-4bb8-b6fb-8bd16798d29f','18b4d209-ac1b-4f3a-af32-75b422c851b5','c4c84c87-3f65af69-4aa3a240-fb02b2ab-a3ebea1c','2.16.840.1.113669.632.10.20230920.110410540.1385',1385,'AXIAL','2023-09-20','11:04:10','CT','HEAD',NULL,1,'2025-12-25 19:23:09.296605','2025-12-25 19:23:09.482609');
/*!40000 ALTER TABLE `dicom_series` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dicom_study`
--

DROP TABLE IF EXISTS `dicom_study`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dicom_study` (
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
  `number_of_study_related_series` int DEFAULT '0' COMMENT 'Number of series in this study',
  `number_of_study_related_instances` int DEFAULT '0' COMMENT 'Total number of instances in this study',
  `created_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `updated_at` datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_orthanc_study_id` (`orthanc_study_id`),
  UNIQUE KEY `UK_study_instance_uid` (`study_instance_uid`),
  KEY `IDX_patient_id` (`patient_id`),
  KEY `IDX_examination_id` (`examination_id`),
  KEY `IDX_treatment_phase_id` (`treatment_phase_id`),
  KEY `IDX_study_date` (`study_date`),
  KEY `IDX_dicom_study_patient_date` (`patient_id`,`study_date` DESC),
  CONSTRAINT `FK_dicom_study_examination` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_dicom_study_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_dicom_study_treatment_phase` FOREIGN KEY (`treatment_phase_id`) REFERENCES `treatment_phases` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dicom_study`
--

LOCK TABLES `dicom_study` WRITE;
/*!40000 ALTER TABLE `dicom_study` DISABLE KEYS */;
INSERT INTO `dicom_study` VALUES ('18b4d209-ac1b-4f3a-af32-75b422c851b5','a68e2737-ae82-4e1a-abd1-c985d14912de',NULL,'5847aef3-8076-4eff-af43-a03276839d9d','f091e809-46db6c16-4d110bec-3f910b6c-74b65f9d','2.16.840.1.113669.632.10.20230920.110420558.1385.105','2023-09-20','11:04:10',NULL,NULL,NULL,NULL,'Cream^Cookies^^^','121212',NULL,NULL,1,1,'2025-12-25 19:23:09.276605','2025-12-25 19:23:09.482609'),('69ec4c09-77d7-40da-99fb-9966acc6e9e3','a68e2737-ae82-4e1a-abd1-c985d14912de',NULL,'5847aef3-8076-4eff-af43-a03276839d9d','a03e9803-b1b1350f-b0da8402-f0c40ce9-0e468dc5','1.2.276.0.7230010.3.1.2.3835155082.9388.1766656760.626','2025-12-25','16:59:20',NULL,'0250102105650447',NULL,NULL,'pham thi mai','00043','1960-11-20','Female',1,3,'2025-12-26 20:49:10.867592','2025-12-26 20:54:35.538952');
/*!40000 ALTER TABLE `dicom_study` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` varchar(255) NOT NULL,
  `license_number` varchar(255) DEFAULT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `years_experience` int NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3q0j5r6i4e9k3afhypo6uljph` (`user_id`),
  CONSTRAINT `FK9roto9ydtnjfkixvexq5vxyl5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES ('3499aba6-dece-4097-bd76-1987135adb4a','BS29503','Niềng Răng',4,'ec466f69-d577-4ed4-a3db-d51e8a66de23'),('39c38f25-8118-4393-907a-1586d80670f8','BS2114','Răng hàm mặt',7,'9842924e-673d-4fe8-984d-17419b147fe7'),('466f26bb-6127-41f2-8f11-fdfa06e27013','BS04295','Trồng răng Implant',10,'2cef34e9-757a-4bea-98c1-0c1180bf47f9'),('49cf0645-20f4-4f61-9404-db65ece6fd92','BS21500','Trồng răng ',6,'b3a4c6f0-4f54-4ef8-911d-1049c1afd532');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination`
--

DROP TABLE IF EXISTS `examination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination` (
  `id` varchar(255) NOT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `examined_at` varchar(255) DEFAULT NULL,
  `list_comment` varbinary(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `symptoms` varchar(255) DEFAULT NULL,
  `total_cost` double NOT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `appointment_id` varchar(255) DEFAULT NULL,
  `dicom_study_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkri7gwdsgw6hp6cf0jexwhl7w` (`appointment_id`),
  KEY `IDX_examination_dicom_study` (`dicom_study_id`),
  CONSTRAINT `FK7dqgrq2dnuomi11x7x3vhl5dl` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `FK_examination_dicom_study` FOREIGN KEY (`dicom_study_id`) REFERENCES `dicom_study` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination`
--

LOCK TABLES `examination` WRITE;
/*!40000 ALTER TABLE `examination` DISABLE KEYS */;
INSERT INTO `examination` VALUES ('9b4fe49b-71ee-4497-890d-ff42a5fc57fc','Bị sâu răng','Hoàng Công Tráng',NULL,'Test','Đau nhói vùng răng hàm dưới',20000000,'Thay răng và trồng răng mới','d88d7c8d-1456-4291-9405-2f9f6f77ec01',NULL);
/*!40000 ALTER TABLE `examination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination_list_comment`
--

DROP TABLE IF EXISTS `examination_list_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination_list_comment` (
  `examination_id` varchar(255) NOT NULL,
  `list_comment` varchar(255) DEFAULT NULL,
  KEY `FK2wp7lnu8evvod9c7rrl7tsb4e` (`examination_id`),
  CONSTRAINT `FK2wp7lnu8evvod9c7rrl7tsb4e` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_list_comment`
--

LOCK TABLES `examination_list_comment` WRITE;
/*!40000 ALTER TABLE `examination_list_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination_list_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination_list_dental_services_entity_order`
--

DROP TABLE IF EXISTS `examination_list_dental_services_entity_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination_list_dental_services_entity_order` (
  `examination_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `list_dental_services_entity_id` varchar(255) DEFAULT NULL,
  KEY `FK6qqc1j0cch72gl4nc67bnd0bx` (`examination_id`),
  CONSTRAINT `FK6qqc1j0cch72gl4nc67bnd0bx` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_list_dental_services_entity_order`
--

LOCK TABLES `examination_list_dental_services_entity_order` WRITE;
/*!40000 ALTER TABLE `examination_list_dental_services_entity_order` DISABLE KEYS */;
INSERT INTO `examination_list_dental_services_entity_order` VALUES ('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',16500000,'Răng sứ Ceramill',3,'Răng',5500000,NULL,NULL),('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',2000000,'Nhổ răng khôn hàm trên',1,'Răng',2000000,NULL,NULL),('9b4fe49b-71ee-4497-890d-ff42a5fc57fc',1500000,'Nhổ răng hàm lớn (6,7)',1,'Răng',1500000,NULL,NULL);
/*!40000 ALTER TABLE `examination_list_dental_services_entity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination_list_prescription_order`
--

DROP TABLE IF EXISTS `examination_list_prescription_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination_list_prescription_order` (
  `examination_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `dosage` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `frequency` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  KEY `FK4erp3jb1q2uvts5f5l51uyy71` (`examination_id`),
  CONSTRAINT `FK4erp3jb1q2uvts5f5l51uyy71` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_list_prescription_order`
--

LOCK TABLES `examination_list_prescription_order` WRITE;
/*!40000 ALTER TABLE `examination_list_prescription_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination_list_prescription_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` varchar(255) NOT NULL,
  `public_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `examination_id` varchar(255) DEFAULT NULL,
  `treatment_phases_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5oy76tckipw5fi7n6fu7p0u0d` (`examination_id`),
  KEY `FKqedmjmkl3cft5etp5nvm9mr6f` (`treatment_phases_id`),
  CONSTRAINT `FK5oy76tckipw5fi7n6fu7p0u0d` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`),
  CONSTRAINT `FKqedmjmkl3cft5etp5nvm9mr6f` FOREIGN KEY (`treatment_phases_id`) REFERENCES `treatment_phases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES ('037b9586-f0d3-485a-a5f5-d9da735f14d7','vcgfdhlia4jplenyg8he','treatmentPhasesXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1767141508/vcgfdhlia4jplenyg8he.jpg',NULL,'741932d7-500f-49b9-8a77-2cce87e94a31'),('069142ec-c69b-4ee2-9617-97479de1aa94','uh5k1hlsb3e8o2qkyxh8','treatmentPhasesTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1766153805/uh5k1hlsb3e8o2qkyxh8.png',NULL,'716aa04d-afbc-420f-bd62-73b03d4824c0'),('2a8641d9-3973-4f82-833f-c4e4a457f673','zg9c3fzv5kjep10j6nej','treatmentPhasesFace','https://res.cloudinary.com/dn2plfafj/image/upload/v1766153804/zg9c3fzv5kjep10j6nej.png',NULL,'716aa04d-afbc-420f-bd62-73b03d4824c0'),('3e53f233-c57d-400b-b53c-b75db773b2c6','eypgosqehqaradzlvdjc','examinationTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1765640215/eypgosqehqaradzlvdjc.jpg','9b4fe49b-71ee-4497-890d-ff42a5fc57fc',NULL),('418159a7-fb35-4e52-bd39-a70256ba1d52','wpjmqponwvjdmae5wlma','examinationFace','https://res.cloudinary.com/dn2plfafj/image/upload/v1765640213/wpjmqponwvjdmae5wlma.jpg','9b4fe49b-71ee-4497-890d-ff42a5fc57fc',NULL),('42141722-2cad-4e93-bb89-ca525817dd16','sjnqjqgwgjm4cautrbsl','examinationXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1767140784/sjnqjqgwgjm4cautrbsl.jpg','9b4fe49b-71ee-4497-890d-ff42a5fc57fc',NULL),('42dee22c-73b6-4ede-a3cf-e801b48ea6e0','qpmozsya2whxxpgme5yp','treatmentPhasesTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1767141510/qpmozsya2whxxpgme5yp.jpg',NULL,'741932d7-500f-49b9-8a77-2cce87e94a31'),('5ad07bcc-4f01-4b97-af00-d0dc1770286a','xv09dzzd0qw0xbue4amf','treatmentPhasesFace','https://res.cloudinary.com/dn2plfafj/image/upload/v1765659377/xv09dzzd0qw0xbue4amf.jpg',NULL,'5847aef3-8076-4eff-af43-a03276839d9d'),('7c26e819-0a62-45e3-b425-1958799bde15','oowf6naghq4i5fvlr9c0','treatmentPhasesFace','https://res.cloudinary.com/dn2plfafj/image/upload/v1767141509/oowf6naghq4i5fvlr9c0.jpg',NULL,'741932d7-500f-49b9-8a77-2cce87e94a31'),('8f46aca7-e992-4377-91f5-b2d1612ce1ad','xwlkpb4bm0p9aqip5wpz','treatmentPhasesTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1765659379/xwlkpb4bm0p9aqip5wpz.jpg',NULL,'5847aef3-8076-4eff-af43-a03276839d9d'),('94f2c075-76c1-4b56-a031-9f52abd6a605','coe6zvge6miy1gqiaw5q','examinationXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1767136918/coe6zvge6miy1gqiaw5q.jpg','9b4fe49b-71ee-4497-890d-ff42a5fc57fc',NULL),('bfce0727-f585-4973-adfe-7a5e3db1f6c2','agll92fijsxfzzsx9rtk','treatmentPhasesXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1765659374/agll92fijsxfzzsx9rtk.jpg',NULL,'5847aef3-8076-4eff-af43-a03276839d9d'),('d40e110c-9ada-4388-b337-2b064c0b1cab','c8xe6zx5lgj68obemdwt','examinationXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1765640209/c8xe6zx5lgj68obemdwt.jpg','9b4fe49b-71ee-4497-890d-ff42a5fc57fc',NULL),('d44a93f4-2aec-435c-8fab-4e7d94fa7243','jgbgf3xvnvy4mk93voah','treatmentPhasesXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1766153802/jgbgf3xvnvy4mk93voah.png',NULL,'716aa04d-afbc-420f-bd62-73b03d4824c0');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invalidated_token`
--

DROP TABLE IF EXISTS `invalidated_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invalidated_token` (
  `id` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invalidated_token`
--

LOCK TABLES `invalidated_token` WRITE;
/*!40000 ALTER TABLE `invalidated_token` DISABLE KEYS */;
INSERT INTO `invalidated_token` VALUES ('019a4bd9-16a7-444b-bdbf-8e640726d6e5','2026-01-10 04:36:58.000000'),('033669d6-0bd6-4f98-8854-4604ecd0256c','2026-01-06 19:02:23.000000'),('048b4dd4-a850-43df-807c-2ac644ca1a63','2025-12-14 02:23:02.000000'),('04917da5-8c69-4629-b438-cb308f12d962','2025-12-19 00:57:28.000000'),('072bd73f-b1d0-4e10-883d-04771392e3d1','2025-12-14 03:57:51.000000'),('0a4de343-330c-4320-8e14-af2ebb6a68b2','2026-01-10 03:13:59.000000'),('0f375c68-2fa0-4b79-9b91-7cfba8058306','2025-12-20 03:37:18.000000'),('104d579a-b369-4860-8cd9-1f970bfd572a','2026-01-10 04:43:49.000000'),('108750cf-4717-491e-a446-77ba33a25fc0','2025-12-14 03:55:33.000000'),('10960613-9248-4f0c-9263-6dd362e1df8a','2025-12-14 03:08:37.000000'),('12f1acb7-b02a-4d38-9153-0421661456b6','2025-12-14 17:54:54.000000'),('15e6238c-efaa-4473-a884-88705231a051','2025-12-14 09:24:12.000000'),('182dde45-b0df-496a-958a-b41330a9d953','2026-01-06 19:12:30.000000'),('19877703-70ef-4002-a800-d3d23fa269df','2025-12-13 12:36:13.000000'),('1acb267d-e929-4ff3-9629-b6429d49fa07','2026-01-06 19:11:30.000000'),('1c512414-20e4-49d4-a842-fe699ef32248','2025-12-13 21:26:17.000000'),('22791bd9-111b-4305-823c-26744ed2e170','2025-12-13 21:38:24.000000'),('22f79318-de5f-4647-a0a2-f0b227dbb411','2025-12-14 04:12:57.000000'),('266fe464-f550-41b4-8c05-2f3de0f07ccd','2025-12-26 21:36:21.000000'),('28e8a0a2-ac2e-44bc-ac4a-7fdd511a940f','2026-01-06 19:00:03.000000'),('29f8feef-93da-4d87-b438-f1157c97bdcc','2026-01-06 19:00:59.000000'),('29fa9f48-ede5-4d21-b193-ecece82cfaff','2025-12-14 17:04:38.000000'),('2be3c101-cfc5-423b-8b82-f2bcd9a09d2d','2025-12-14 04:57:42.000000'),('2c59cc28-20df-46e8-88bd-5892fbee67bc','2026-01-10 04:43:23.000000'),('2cca89c7-ae0e-40ba-b7b0-9f65f37f7410','2025-12-14 03:31:23.000000'),('2d47fe31-2d5e-4216-8235-ad510129a0f8','2026-01-06 19:35:43.000000'),('36d69d92-b94b-4e40-9bdc-0392398ee28c','2025-12-14 06:23:56.000000'),('390cf8c4-1294-4b7c-b564-7142a8085ec8','2025-12-14 17:23:29.000000'),('39da863c-34c2-4b47-a353-16791b0453e7','2026-01-10 01:46:23.000000'),('3c10d47e-a1da-4819-93a5-86cd22e5a582','2025-12-14 08:51:47.000000'),('430066dc-3851-4c5e-b787-6cf05226e6b7','2025-12-21 12:48:17.000000'),('435354e9-0e97-4dad-9762-c52323690d32','2025-12-14 03:10:00.000000'),('43aa8561-9a16-4bba-b264-daab797b0347','2025-12-31 13:03:50.000000'),('44d5e662-e56b-4be6-9110-4e80d4a8c3f8','2025-12-14 10:29:16.000000'),('458e46e5-b478-4e74-ab15-6ffb63bed70d','2025-12-13 22:22:38.000000'),('48a72824-9c4e-477c-a44f-3f15f8a6285d','2026-01-06 19:41:23.000000'),('491064e8-e440-4bae-b378-7350bdc4a5f1','2026-01-06 23:08:03.000000'),('49a840fd-e51c-43c0-ba4f-defaa8325431','2026-01-06 18:58:54.000000'),('4ef3449b-d4f0-48f0-a1db-e3ed991e4cf4','2025-12-13 21:19:08.000000'),('52df233c-1437-4a13-adaa-c5236a7606b8','2025-12-20 14:17:24.000000'),('5423cd86-0eb9-4643-9a25-be0035cf30a1','2025-12-14 04:46:59.000000'),('558d7bdd-3a04-4e94-80ed-e980e51e01b9','2025-12-14 17:39:02.000000'),('573065ac-144b-41b1-8731-d57500f66049','2025-12-13 11:42:59.000000'),('59bf6227-982c-4efd-a6cd-999892761695','2025-12-14 09:32:58.000000'),('59daf20f-3548-4128-b2e1-59011a4023e0','2025-12-14 04:09:50.000000'),('5b22b70f-c2cf-4aa2-9baa-d52fc483a754','2025-12-20 03:56:27.000000'),('5c777ac2-54ea-4d6a-b56f-73df98666713','2025-12-31 22:56:53.000000'),('5f50ecd2-d2c0-4e98-8257-3c777750bf25','2025-12-20 13:20:06.000000'),('5f6c9857-a260-47d7-ad4c-5a6f6e363513','2025-12-14 06:19:39.000000'),('61c81b13-c58b-4897-9854-6f8a47d53243','2025-12-20 14:04:03.000000'),('61ff57b7-6dda-4cc4-b9b6-fa7ae1d0ab44','2025-12-19 01:29:48.000000'),('631c7cb4-c9d6-4713-bc3c-2c81c33aa42d','2025-12-14 04:17:53.000000'),('69c06bd3-0595-4c38-be90-1fb8bdb99aec','2026-01-06 19:10:32.000000'),('6e075d5c-46bc-41e4-aa6a-b027d7142e41','2025-12-19 13:28:57.000000'),('6fa546b5-0f43-4ff7-b1d8-97a052e3fb4c','2025-12-26 21:38:22.000000'),('6fbeae0a-f4f4-45ff-ac71-8156bb3b51bd','2025-12-14 04:33:18.000000'),('70c86694-a393-4dc4-879a-688ebd437dc7','2026-01-10 01:37:48.000000'),('71328ed1-f082-4a4f-8be1-aa19a27a4748','2025-12-31 14:45:50.000000'),('750f38e7-90ba-4ae1-aa83-944c1e97c20b','2025-12-14 03:29:28.000000'),('76a61008-8a22-415a-8ef5-147d69bf80de','2025-12-19 14:23:57.000000'),('79f03186-7baf-4211-9d53-f64fa548a34d','2025-12-14 03:25:37.000000'),('7c9b9e3e-b50d-4eb1-a019-9466748aacf9','2025-12-31 14:24:23.000000'),('7e64fb0a-3e65-4f2b-8d70-3c01b29eab06','2025-12-31 14:08:48.000000'),('7f4e6829-b950-4fc7-bfda-92f9e5a238e5','2026-01-06 19:44:07.000000'),('7fd26134-f62d-40e8-9bef-eb7035c1da83','2025-12-21 11:45:20.000000'),('835b1496-c29b-4b18-8650-7dacc8906eff','2025-12-14 17:36:51.000000'),('844e81ed-4d93-406a-aa4f-d619c469c9bf','2026-01-06 19:36:51.000000'),('85c6845d-3076-4c24-973c-52167fe2d92e','2026-01-10 05:55:01.000000'),('8677bd2c-3eae-4d1e-8332-32c97ecd92c2','2026-01-06 19:35:54.000000'),('87fb709f-8918-4bc3-9503-16b703aec844','2025-12-13 21:15:07.000000'),('883fd53d-99ff-49ec-b155-51243fb7db63','2025-12-13 19:55:20.000000'),('89d1a348-35d0-4caf-b459-ff46bdf48f73','2026-01-06 19:44:23.000000'),('8e811de4-5dd9-4ace-aafc-ed09901294cd','2025-12-14 17:09:39.000000'),('92757d7f-9e45-4079-9ade-9a5828349b2b','2025-12-14 04:39:46.000000'),('96adc35d-c6a5-45f4-a885-5ae6e1c20ae5','2026-01-10 02:41:53.000000'),('9c261ca6-3eae-4efa-90c4-4395b82b0459','2025-12-31 05:09:48.000000'),('9c37b1e0-a2b9-4d11-8b05-f53905817427','2025-12-25 15:13:42.000000'),('9c397f98-ff30-4057-9292-250478b4ffa9','2026-01-06 19:34:12.000000'),('9d6a54aa-2865-40d4-a673-4b31d88133ca','2025-12-20 14:11:30.000000'),('9eba9261-950a-4707-bd5e-73cc955b1a57','2025-12-31 05:06:25.000000'),('9f304e41-2a57-4ace-9125-036279229bcb','2026-01-10 04:43:09.000000'),('a195b0c0-fbd0-480c-95ea-f80e3a04b7c6','2025-12-31 14:52:58.000000'),('a1e9732b-d2ee-4f67-9722-959df2be47c2','2025-12-19 14:38:45.000000'),('a92b8b23-066d-480e-9457-a9a550fa381b','2025-12-14 03:29:42.000000'),('aab388bf-4a66-40a8-bbc6-808b55126d3c','2025-12-19 15:33:21.000000'),('ae4c1614-6ec0-4945-995b-916ca982bbd7','2025-12-14 04:27:29.000000'),('ae67be61-cb1e-4f10-bb3e-c156eb43cae0','2025-12-31 14:53:19.000000'),('b0ca3dc6-d695-4a36-95d1-f3f858eca7ba','2025-12-20 04:42:06.000000'),('b4495b28-aea1-4325-8155-f0b9abfc16af','2025-12-13 22:33:50.000000'),('b5625d44-1191-4e48-a9d3-b248600f7874','2025-12-14 02:37:13.000000'),('b6984dfa-ca15-4d88-9db6-a7e6775cb933','2025-12-25 15:18:51.000000'),('b799ac56-ee69-473d-acfd-c3e5e1ee05a9','2025-12-14 17:04:54.000000'),('b7b7d7ea-4823-4916-bda2-a0ac9170ae3d','2025-12-14 16:51:00.000000'),('b8769e83-3e0c-4927-879a-024e3e8ec5d2','2025-12-18 21:55:57.000000'),('b8a06d2e-01f0-40f5-b59a-162986c36d09','2026-01-06 19:10:56.000000'),('baa5ecc0-8fe7-4e92-a9bf-b40714fe1565','2026-01-06 19:41:33.000000'),('bb17bd63-8c31-4d19-a193-e6a60f2de906','2025-12-19 14:03:37.000000'),('bf70ea28-e878-4272-a815-52b62b81d5f9','2025-12-19 14:02:11.000000'),('c0a09d3e-733e-4ad7-94fd-978f6c2589f5','2025-12-14 04:01:40.000000'),('c29ac796-5f8b-4018-9a1e-96b5c2b7555e','2025-12-19 14:37:51.000000'),('c3e991f2-7f15-4f41-abb5-a62b4bfd3b77','2026-01-10 04:37:24.000000'),('c4933a50-27ad-4cb7-b0f8-3df8c7591532','2025-12-19 14:01:20.000000'),('c540901b-17c0-4129-a5a6-3fc797fc15f1','2025-12-14 04:09:31.000000'),('c658ac95-3f6f-433e-ac5d-f4ec9dcc6429','2025-12-14 17:47:23.000000'),('c7d83d72-2260-4191-8a30-78e82b1cae61','2025-12-31 13:08:37.000000'),('cb7781d6-603e-4640-87c7-041a51832f4d','2025-12-14 17:36:28.000000'),('cba27a13-da9c-4e4d-8900-dca4e9bf9e7a','2026-01-06 19:01:55.000000'),('ccd697d3-d4dd-4804-ba08-26eca9eea9b5','2025-12-14 04:23:35.000000'),('cf9489d9-1914-48f9-981b-19e3b77323f1','2025-12-19 14:00:33.000000'),('d016ccd0-8656-4212-b71a-677af2d67e9f','2025-12-31 14:23:07.000000'),('d1161213-0646-4cd5-a246-ed8a5a36c1ba','2025-12-13 21:56:15.000000'),('d2b1fc37-79b8-40d0-a1d4-6e141787f245','2026-01-10 06:16:19.000000'),('d2be6f82-48d6-40fc-9622-d13af77f912d','2026-01-06 23:02:27.000000'),('d30309d5-21b2-45c6-ba9f-9847bcd013ea','2025-12-14 03:31:52.000000'),('d4f4432c-0455-4662-b533-0ccec0714305','2025-12-31 14:23:45.000000'),('d699c5d9-ac73-49f7-bc53-480826335688','2026-01-06 19:36:43.000000'),('da38744c-e7e8-4752-9088-3bf51d87e769','2025-12-13 21:45:21.000000'),('da5d68df-9ac2-476a-b3bf-7ab54d1ed95a','2025-12-21 11:43:23.000000'),('dadc095d-d4dd-4208-9da6-85bde6e8bed6','2025-12-18 23:59:12.000000'),('dbb5067b-782b-404a-a01f-a6082eff3c96','2026-01-06 18:59:33.000000'),('dd9c4674-0ce7-46a8-bafc-a50f6d38a55c','2026-01-07 14:08:33.000000'),('de39be70-0301-4332-86b3-d09497d6599e','2026-01-06 19:36:35.000000'),('e0b810f5-ebba-429c-8062-eb81ff4073ca','2026-01-10 04:48:38.000000'),('e4a5b027-06e8-4df9-ad48-2aa18afaf9f4','2026-01-10 04:23:21.000000'),('e7cf32de-c57e-48a5-a913-a0e04ac04d1c','2025-12-14 03:25:22.000000'),('e99f43c0-50b9-4e06-ae91-49f645eee7bc','2025-12-14 04:29:28.000000'),('ea74c939-bad1-4408-a04f-33f2ddc60711','2025-12-20 14:11:51.000000'),('eedfe449-0bfe-4749-8421-edd53946dcd2','2025-12-14 17:38:49.000000'),('f808b0f1-a269-418b-88b4-f2ce874d78c4','2025-12-14 02:30:28.000000'),('f9288c00-a227-40c1-9c4b-1cee82893369','2025-12-20 14:12:23.000000'),('fcd43139-483a-4cdc-bb13-5bc831ce08f3','2026-01-06 19:36:21.000000');
/*!40000 ALTER TABLE `invalidated_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nurse`
--

DROP TABLE IF EXISTS `nurse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nurse` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbl2q6g60tor4whxe60hlrbdx8` (`user_id`),
  CONSTRAINT `FKr5s9i2lqsfmloldv6tqbh3eg4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nurse`
--

LOCK TABLES `nurse` WRITE;
/*!40000 ALTER TABLE `nurse` DISABLE KEYS */;
INSERT INTO `nurse` VALUES ('6cf5bce7-f1c7-4c13-aa72-e9209c19caf0','b991c5ca-0967-419e-b155-491abc512252'),('b5d8bd4c-8fef-4c7f-a295-735cba03f6d8','bc450a3e-092e-4e2d-8f18-71b5222f1d68');
/*!40000 ALTER TABLE `nurse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` varchar(255) NOT NULL,
  `allergy` varchar(255) DEFAULT NULL,
  `blood_group` varchar(255) DEFAULT NULL,
  `emergency_contact_name` varchar(255) DEFAULT NULL,
  `emergency_phone_number` varchar(255) DEFAULT NULL,
  `medical_history` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6i3fp8wcdxk473941mbcvdao4` (`user_id`),
  CONSTRAINT `FKp6ttmfrxo2ejiunew4ov805uc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('7dc08f18-49a5-43fb-a708-90f8c2798986',NULL,NULL,NULL,NULL,NULL,'eb95baaa-cc84-482f-b162-1d6ee5f3a8e3'),('a68e2737-ae82-4e1a-abd1-c985d14912de','Cá, Mèo','A','Hà Ninh Quang','0888705203','Tiểu đường','77623a83-43ac-4566-a19d-d3fb9c634a95');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES ('ANALYZE_IMAGE','Phân tích ảnh X-Quang và CT Scan bằng AI'),('CREATE_EXAMINATION','thêm hồ sơ khám ban đầu'),('CREATE_TOOTH_STATUS','thêm trạng thái răng của bệnh nhân'),('CREATE_TREATMENT_PHASES','thêm tiến trình điều trị'),('CREATE_TREATMENT_PLANS','thêm phác đồ điều trị'),('DELETE_DICOM','xóa DICOM studies'),('GET_ALL_TREATMENT_PHASES','lấy danh sách tiến trình điều trị của phác đồ'),('GET_BASIC_INFO','lấy thông tin cơ bản của bệnh nhân'),('GET_EXAMINATION_DETAIL','xem chi tiết hồ sơ khám ban đầu'),('GET_INFO_DOCTOR','xem thông tin bác sĩ'),('GET_INFO_NURSE','lấy thông tin y tá'),('GET_TOOTH_STATUS','lấy thông tin trạng thái răng của bệnh nhân'),('NOTIFICATION_APPOINMENT','cập nhật trạng thái thông báo lịch hẹn'),('PICK_DOCTOR','chọn bác sĩ có trong danh sách'),('PICK_NURSE','chọn y tá theo dõi phác đồ'),('UPDATE_EXAMINATION','cập nhật hồ sơ khám ban đầu'),('UPDATE_PAYMENT_COST','cập nhật thanh toán vnpay'),('UPDATE_TOOTH_STATUS','cập nhật trạng thái răng của bệnh nhân'),('UPDATE_TREATMENT_PHASES','cập nhật tiến trình điều trị'),('UPDATE_TREATMENT_PLANS','cập nhật tiến trình điều trị'),('UPLOAD_DICOM','tải lên file dicom'),('VIEW_AI_ANALYSIS','Xem kết quả phân tích AI'),('VIEW_DICOM','xem DICOM studies');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription` (
  `name` varchar(255) NOT NULL,
  `dosage` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `frequency` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `unit_price` double NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES ('Amoxicillin 500mg','500mg','sau ăn 30 phút','3 lần/ngày','Kháng sinh sau nhổ răng, dùng trong 5 ngày',35000),('Chlorhexidine 0.12%','10ml','sáng và tối sau đánh răng','2 lần/ngày','Súc miệng 30 giây, không nuốt',15000),('Ibuprofen 400mg\',\'400mg\',\'sau ăn 30 phút','400mg','sau ăn 30 phút','3 lần/ngày','Giảm đau và chống viêm, không dùng khi đau dạ dày',40000),('Paracetamol 500mg','500mg','sau ăn 30 phút','3 lần/ngày','Dùng khi sốt hoặc đau đầu nhẹ',20000),('Vitamin C','500mg','sau bữa sáng 10 phút','1 lần/ngày','Giúp mau lành vết thương, tăng sức đề kháng',25000);
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('ADMIN','role admin'),('DOCTOR','role doctor'),('DOCTORLV2','role doctor lv2'),('NURSE','role nurse'),('PATIENT','role patient');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `roles_name` varchar(255) NOT NULL,
  `permissions_name` varchar(255) NOT NULL,
  PRIMARY KEY (`roles_name`,`permissions_name`),
  KEY `FKf5aljih4mxtdgalvr7xvngfn1` (`permissions_name`),
  CONSTRAINT `FK7g6tvof18psqvvex6i3s5ljk9` FOREIGN KEY (`roles_name`) REFERENCES `role` (`name`),
  CONSTRAINT `FKf5aljih4mxtdgalvr7xvngfn1` FOREIGN KEY (`permissions_name`) REFERENCES `permission` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES ('DOCTOR','ANALYZE_IMAGE'),('DOCTORLV2','ANALYZE_IMAGE'),('DOCTOR','CREATE_EXAMINATION'),('DOCTORLV2','CREATE_EXAMINATION'),('DOCTOR','CREATE_TOOTH_STATUS'),('DOCTORLV2','CREATE_TOOTH_STATUS'),('DOCTOR','CREATE_TREATMENT_PHASES'),('DOCTORLV2','CREATE_TREATMENT_PHASES'),('DOCTOR','CREATE_TREATMENT_PLANS'),('DOCTORLV2','CREATE_TREATMENT_PLANS'),('DOCTOR','DELETE_DICOM'),('DOCTORLV2','DELETE_DICOM'),('DOCTOR','GET_ALL_TREATMENT_PHASES'),('DOCTORLV2','GET_ALL_TREATMENT_PHASES'),('NURSE','GET_ALL_TREATMENT_PHASES'),('PATIENT','GET_ALL_TREATMENT_PHASES'),('DOCTOR','GET_BASIC_INFO'),('DOCTORLV2','GET_BASIC_INFO'),('NURSE','GET_BASIC_INFO'),('PATIENT','GET_BASIC_INFO'),('DOCTOR','GET_EXAMINATION_DETAIL'),('DOCTORLV2','GET_EXAMINATION_DETAIL'),('DOCTORLV2','GET_INFO_DOCTOR'),('NURSE','GET_INFO_DOCTOR'),('PATIENT','GET_INFO_DOCTOR'),('DOCTOR','GET_INFO_NURSE'),('DOCTORLV2','GET_INFO_NURSE'),('PATIENT','GET_INFO_NURSE'),('DOCTOR','GET_TOOTH_STATUS'),('DOCTORLV2','GET_TOOTH_STATUS'),('NURSE','GET_TOOTH_STATUS'),('PATIENT','GET_TOOTH_STATUS'),('NURSE','NOTIFICATION_APPOINMENT'),('DOCTORLV2','PICK_DOCTOR'),('NURSE','PICK_DOCTOR'),('PATIENT','PICK_DOCTOR'),('DOCTOR','PICK_NURSE'),('DOCTORLV2','PICK_NURSE'),('DOCTOR','UPDATE_EXAMINATION'),('DOCTORLV2','UPDATE_EXAMINATION'),('DOCTOR','UPDATE_PAYMENT_COST'),('DOCTORLV2','UPDATE_PAYMENT_COST'),('NURSE','UPDATE_PAYMENT_COST'),('PATIENT','UPDATE_PAYMENT_COST'),('DOCTOR','UPDATE_TOOTH_STATUS'),('DOCTORLV2','UPDATE_TOOTH_STATUS'),('DOCTOR','UPDATE_TREATMENT_PHASES'),('DOCTORLV2','UPDATE_TREATMENT_PHASES'),('DOCTOR','UPDATE_TREATMENT_PLANS'),('DOCTORLV2','UPDATE_TREATMENT_PLANS'),('DOCTOR','UPLOAD_DICOM'),('DOCTORLV2','UPLOAD_DICOM'),('DOCTOR','VIEW_AI_ANALYSIS'),('DOCTORLV2','VIEW_AI_ANALYSIS'),('NURSE','VIEW_AI_ANALYSIS'),('PATIENT','VIEW_AI_ANALYSIS'),('DOCTOR','VIEW_DICOM'),('DOCTORLV2','VIEW_DICOM');
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tooth`
--

DROP TABLE IF EXISTS `tooth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tooth` (
  `id` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tooth_number` varchar(255) DEFAULT NULL,
  `patient_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5184sq3ybhyw3cbpu86pxebdh` (`patient_id`),
  CONSTRAINT `FK5184sq3ybhyw3cbpu86pxebdh` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tooth`
--

LOCK TABLES `tooth` WRITE;
/*!40000 ALTER TABLE `tooth` DISABLE KEYS */;
INSERT INTO `tooth` VALUES ('45976d01-13f6-491e-b294-a343a95871d5','extracted','45','a68e2737-ae82-4e1a-abd1-c985d14912de'),('c27c7919-1b0e-492f-ab2f-4de6e6671fbe','crown','28','a68e2737-ae82-4e1a-abd1-c985d14912de'),('f309dd69-16d4-44f1-a1d5-018cd7a96064','cavity','26','a68e2737-ae82-4e1a-abd1-c985d14912de');
/*!40000 ALTER TABLE `tooth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_phases`
--

DROP TABLE IF EXISTS `treatment_phases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_phases` (
  `id` varchar(255) NOT NULL,
  `cost` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `list_comment` varbinary(255) DEFAULT NULL,
  `next_appointment` datetime(6) DEFAULT NULL,
  `phase_number` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `treatment_plans_id` varchar(255) DEFAULT NULL,
  `dicom_study_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsihldx8464uc02ipwj5tgr0kx` (`treatment_plans_id`),
  KEY `IDX_treatment_phases_dicom_study` (`dicom_study_id`),
  CONSTRAINT `FK_treatment_phases_dicom_study` FOREIGN KEY (`dicom_study_id`) REFERENCES `dicom_study` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FKsihldx8464uc02ipwj5tgr0kx` FOREIGN KEY (`treatment_plans_id`) REFERENCES `treatment_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_phases`
--

LOCK TABLES `treatment_phases` WRITE;
/*!40000 ALTER TABLE `treatment_phases` DISABLE KEYS */;
INSERT INTO `treatment_phases` VALUES ('5847aef3-8076-4eff-af43-a03276839d9d',3015000,'Sửa soạn xoang trám, trám bít Composite, đánh bóng.\r\n\r\nGhi chú: Đã loại bỏ hoàn toàn mô sâu. Bệnh nhân không có biểu hiện đau. Xoang trám được cách ly tốt và trám bằng Composite khối A2. Hướng dẫn bệnh nhân chăm sóc tại nhà.','2025-12-11',NULL,'2025-12-13 09:00:00.000000','Phase 1','2025-12-11','Inprogress','7e2663c4-21ea-483e-a80c-e0bfecd487e1','69ec4c09-77d7-40da-99fb-9966acc6e9e3'),('716aa04d-afbc-420f-bd62-73b03d4824c0',2625000,'Testtttt\r\n\r\nGhi chú: Testttt','2025-12-20',NULL,NULL,'Phase 2','2025-12-19','Inprogress','7e2663c4-21ea-483e-a80c-e0bfecd487e1','69ec4c09-77d7-40da-99fb-9966acc6e9e3'),('741932d7-500f-49b9-8a77-2cce87e94a31',5035000,'Test\r\n\r\nGhi chú: Test','2026-01-02',NULL,NULL,'Test','2026-01-01','Inprogress','7e2663c4-21ea-483e-a80c-e0bfecd487e1',NULL);
/*!40000 ALTER TABLE `treatment_phases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_phases_list_comment`
--

DROP TABLE IF EXISTS `treatment_phases_list_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_phases_list_comment` (
  `treatment_phases_id` varchar(255) NOT NULL,
  `list_comment` varchar(255) DEFAULT NULL,
  KEY `FKd6hkqgqvrlmtxgqg3atmhtc7b` (`treatment_phases_id`),
  CONSTRAINT `FKd6hkqgqvrlmtxgqg3atmhtc7b` FOREIGN KEY (`treatment_phases_id`) REFERENCES `treatment_phases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_phases_list_comment`
--

LOCK TABLES `treatment_phases_list_comment` WRITE;
/*!40000 ALTER TABLE `treatment_phases_list_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `treatment_phases_list_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_phases_list_dental_service_entity_order`
--

DROP TABLE IF EXISTS `treatment_phases_list_dental_service_entity_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_phases_list_dental_service_entity_order` (
  `treatment_phases_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `id` varchar(255) DEFAULT NULL,
  `list_dental_services_entity_id` varchar(255) DEFAULT NULL,
  KEY `FK6f2ldsdoucgmvgtxhws7m0s31` (`treatment_phases_id`),
  CONSTRAINT `FK6f2ldsdoucgmvgtxhws7m0s31` FOREIGN KEY (`treatment_phases_id`) REFERENCES `treatment_phases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_phases_list_dental_service_entity_order`
--

LOCK TABLES `treatment_phases_list_dental_service_entity_order` WRITE;
/*!40000 ALTER TABLE `treatment_phases_list_dental_service_entity_order` DISABLE KEYS */;
INSERT INTO `treatment_phases_list_dental_service_entity_order` VALUES ('5847aef3-8076-4eff-af43-a03276839d9d',3000000,'Nhổ răng khôn hàm dưới',1,'Răng',3000000,NULL,NULL),('5847aef3-8076-4eff-af43-a03276839d9d',1100000,'Rạch lợi trùm',1,'Răng',1100000,NULL,NULL),('716aa04d-afbc-420f-bd62-73b03d4824c0',50000,'Nhổ răng sữa bôi tê',1,'Răng',50000,NULL,NULL),('716aa04d-afbc-420f-bd62-73b03d4824c0',2500000,'Răng sứ Titan',1,'Răng',2500000,NULL,NULL),('741932d7-500f-49b9-8a77-2cce87e94a31',5000000,'Niềng răng tháo lắp',1,'1 Hàm',5000000,NULL,NULL);
/*!40000 ALTER TABLE `treatment_phases_list_dental_service_entity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_phases_list_prescription_order`
--

DROP TABLE IF EXISTS `treatment_phases_list_prescription_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_phases_list_prescription_order` (
  `treatment_phases_id` varchar(255) NOT NULL,
  `cost` double DEFAULT NULL,
  `dosage` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `frequency` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  KEY `FK3ge0wauk87xylv1w9wm7v2w04` (`treatment_phases_id`),
  CONSTRAINT `FK3ge0wauk87xylv1w9wm7v2w04` FOREIGN KEY (`treatment_phases_id`) REFERENCES `treatment_phases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_phases_list_prescription_order`
--

LOCK TABLES `treatment_phases_list_prescription_order` WRITE;
/*!40000 ALTER TABLE `treatment_phases_list_prescription_order` DISABLE KEYS */;
INSERT INTO `treatment_phases_list_prescription_order` VALUES ('5847aef3-8076-4eff-af43-a03276839d9d',15000,'10ml','sáng và tối sau đánh răng','2 lần/ngày','Chlorhexidine 0.12%','Súc miệng 30 giây, không nuốt',1,15000),('5847aef3-8076-4eff-af43-a03276839d9d',40000,'400mg','sau ăn 30 phút','3 lần/ngày','Ibuprofen 400mg\',\'400mg\',\'sau ăn 30 phút','Giảm đau và chống viêm, không dùng khi đau dạ dày',1,40000),('716aa04d-afbc-420f-bd62-73b03d4824c0',35000,'500mg','sau ăn 30 phút','3 lần/ngày','Amoxicillin 500mg','Kháng sinh sau nhổ răng, dùng trong 5 ngày',1,35000),('716aa04d-afbc-420f-bd62-73b03d4824c0',40000,'400mg','sau ăn 30 phút','3 lần/ngày','Ibuprofen 400mg\',\'400mg\',\'sau ăn 30 phút','Giảm đau và chống viêm, không dùng khi đau dạ dày',1,40000),('741932d7-500f-49b9-8a77-2cce87e94a31',35000,'500mg','sau ăn 30 phút','3 lần/ngày','Amoxicillin 500mg','Kháng sinh sau nhổ răng, dùng trong 5 ngày',1,35000);
/*!40000 ALTER TABLE `treatment_phases_list_prescription_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_plans`
--

DROP TABLE IF EXISTS `treatment_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_plans` (
  `id` varchar(255) NOT NULL,
  `create_at` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `list_comment` varbinary(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_cost` double NOT NULL,
  `doctor_id` varchar(255) DEFAULT NULL,
  `nurse_id` varchar(255) DEFAULT NULL,
  `patient_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrdpa7gpa9f50mwf0hwclw4b2n` (`doctor_id`),
  KEY `FK3hb03exhqc8wos5wfawmhoacs` (`nurse_id`),
  KEY `FKj6p4hrkpk5s8331e913l5c3xv` (`patient_id`),
  CONSTRAINT `FK3hb03exhqc8wos5wfawmhoacs` FOREIGN KEY (`nurse_id`) REFERENCES `nurse` (`id`),
  CONSTRAINT `FKj6p4hrkpk5s8331e913l5c3xv` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FKrdpa7gpa9f50mwf0hwclw4b2n` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_plans`
--

LOCK TABLES `treatment_plans` WRITE;
/*!40000 ALTER TABLE `treatment_plans` DISABLE KEYS */;
INSERT INTO `treatment_plans` VALUES ('7e2663c4-21ea-483e-a80c-e0bfecd487e1','2025-12-14','Loại bỏ mô răng sâu tại mặt nhai răng hàm lớn thứ nhất hàm dưới bên trái (răng 36). Sát khuẩn và tạo hình xoang trám. Thực hiện hàn răng bằng vật liệu Composite thẩm mỹ. Yêu cầu tái khám sau 6 tháng để kiểm tra định kỳ.','1 buổi, khoảng 45 phút',NULL,'Tư vấn bệnh nhân sử dụng kem đánh răng có Fluoride và chỉ nha khoa hàng ngày. Tránh ăn đồ quá cứng trong vòng 24 giờ đầu sau khi trám.','Inprogress','Hàn (Trám) răng sâu mặt nhai răng 36 bằng Composite',10675000,'49cf0645-20f4-4f61-9404-db65ece6fd92','6cf5bce7-f1c7-4c13-aa72-e9209c19caf0','a68e2737-ae82-4e1a-abd1-c985d14912de'),('b2a0f903-e8db-4e64-8ddf-de810909afda','2025-12-14','Test','Test',NULL,'Test','Inprogress','Test',0,'49cf0645-20f4-4f61-9404-db65ece6fd92',NULL,'a68e2737-ae82-4e1a-abd1-c985d14912de');
/*!40000 ALTER TABLE `treatment_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `disable` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('2cef34e9-757a-4bea-98c1-0c1180bf47f9',_binary '\0','$2a$10$WtwdiJrz4ONgooP7p1EgjeFk39kGw/5PeKQByAJ4bsF8bUzuAnV4y','doctorlv2'),('70dc2719-1f18-4351-a78a-eb693749da8c',_binary '\0','$2a$10$8sI853/hibsncznC.xzAduEOoleMf4MhtV/W2IDBPqP./d8TeV95O','admin'),('77623a83-43ac-4566-a19d-d3fb9c634a95',_binary '\0','$2a$10$I0ALwq2WJyQdxFeJjh1BXueZc/nGRMkMMlb947iUTjNBbPXCCcQEu','benhnhan1'),('9842924e-673d-4fe8-984d-17419b147fe7',_binary '\0','$2a$10$w9a.2FUw7mFi3twBvIW49OFzecSGwhguhTUpakqqbOJbZw7HLn1cm','haquang1'),('b3a4c6f0-4f54-4ef8-911d-1049c1afd532',_binary '\0','$2a$10$Mmchr/qCbKfMqyymg7/UFOB0qZqwltzqhp35qaRBJqyZL1D6ki2RW','doctor1'),('b991c5ca-0967-419e-b155-491abc512252',_binary '\0','$2a$10$0ejVAK6VJld4REqgzElCOOvy5oR.48Tp2vPaC2gviwjwhxzMmQ2EW','nurse2'),('bc450a3e-092e-4e2d-8f18-71b5222f1d68',_binary '\0','$2a$10$JzmlrAOl2KNrTP2Dk/zzGOBGHGRcgwJUDRQDg.SI/0X.EnL8pS8cS','nurse1'),('eb95baaa-cc84-482f-b162-1d6ee5f3a8e3',_binary '\0','$2a$10$I0ALwq2WJyQdxFeJjh1BXueZc/nGRMkMMlb947iUTjNBbPXCCcQEu','benhnhan2'),('ec466f69-d577-4ed4-a3db-d51e8a66de23',_binary '\0','$2a$10$aNPmkv6Gw4zjVNs0IJGDyuA/xgMfEWdg.JJpuJS.tGU.OqyJ2ARDK','doctor2');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_detail`
--

DROP TABLE IF EXISTS `user_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_detail` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `create_at` date DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdm7hrxg9mvrb92v1p3o6wg97u` (`user_id`),
  CONSTRAINT `FKc2fr118twu8aratnm1qop1mn9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_detail`
--

LOCK TABLES `user_detail` WRITE;
/*!40000 ALTER TABLE `user_detail` DISABLE KEYS */;
INSERT INTO `user_detail` VALUES ('1ef5663c-d8b6-4b4b-8b71-c381167b9577','Lục Ngạn - Bắc Giang','2025-12-13','1972-10-17','haninhquang2003@gmail.com','Hoàng Đình Cường','male','0926338423','2cef34e9-757a-4bea-98c1-0c1180bf47f9'),('2c122a66-b7da-4445-adf5-11c721c260c3','Quốc Oai - Hà Nội','2025-12-13','1984-06-14','haninhquang2003@gmail.com','Lê Quang Hùng','male','0984763444','ec466f69-d577-4ed4-a3db-d51e8a66de23'),('335f7fe6-d6da-4ecf-92dc-0567b4341365',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'70dc2719-1f18-4351-a78a-eb693749da8c'),('50f1f6d7-f3ab-4462-96a9-372f071f0b60','Ninh Bình','2025-12-13','2003-01-13','haninhquang2003@gmail.com','Nguyễn Xuân Thu','FEMALE','0933257332','b991c5ca-0967-419e-b155-491abc512252'),('81c89e86-65d9-4398-a91b-8d33e2ce0e68','Phan Đình Giót','2025-12-20','2025-12-21','haninhquang2003@gmail.com','Hà Quang','male','0123456789','eb95baaa-cc84-482f-b162-1d6ee5f3a8e3'),('8b6f664e-ef17-475d-bba1-72f3dc5a687a','Hà Đông - Hà Nội','2025-12-13','2004-11-24','haninhquang2003@gmail.com','Mạc Xuân Trí','male','09237892231','77623a83-43ac-4566-a19d-d3fb9c634a95'),('9e965368-b11a-4783-a0d4-4f025412a4ff','Phan Đình Giót','2025-12-20','2003-05-17','haninhquang2003@gmail.com','Hà Ninh Quang','male','0123456789','9842924e-673d-4fe8-984d-17419b147fe7'),('a9fed5cd-1e08-45be-93e7-0fa7a0f2b4fe','Cao Bằng','2025-12-13','1994-06-17','haninhquang2003@gmail.com','Lý Thanh Huyền','female','0927728145','bc450a3e-092e-4e2d-8f18-71b5222f1d68'),('bb74c6dc-bea3-4c29-91e0-34a2b8e81d0b','Thanh Xuân - Hà Nội','2025-12-13','1981-02-13','haninhquang2003@gmail.com','Hoàng Công Tráng','male','0936228135','b3a4c6f0-4f54-4ef8-911d-1049c1afd532');
/*!40000 ALTER TABLE `user_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `users_id` varchar(255) NOT NULL,
  `roles_name` varchar(255) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_name`),
  KEY `FK6pmbiap985ue1c0qjic44pxlc` (`roles_name`),
  CONSTRAINT `FK6pmbiap985ue1c0qjic44pxlc` FOREIGN KEY (`roles_name`) REFERENCES `role` (`name`),
  CONSTRAINT `FK7ecyobaa59vxkxckg6t355l86` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('70dc2719-1f18-4351-a78a-eb693749da8c','ADMIN'),('b3a4c6f0-4f54-4ef8-911d-1049c1afd532','DOCTOR'),('ec466f69-d577-4ed4-a3db-d51e8a66de23','DOCTOR'),('2cef34e9-757a-4bea-98c1-0c1180bf47f9','DOCTORLV2'),('9842924e-673d-4fe8-984d-17419b147fe7','DOCTORLV2'),('b991c5ca-0967-419e-b155-491abc512252','NURSE'),('bc450a3e-092e-4e2d-8f18-71b5222f1d68','NURSE'),('77623a83-43ac-4566-a19d-d3fb9c634a95','PATIENT'),('eb95baaa-cc84-482f-b162-1d6ee5f3a8e3','PATIENT');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_dicom_study_summary`
--

DROP TABLE IF EXISTS `v_dicom_study_summary`;
/*!50001 DROP VIEW IF EXISTS `v_dicom_study_summary`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_dicom_study_summary` AS SELECT 
 1 AS `id`,
 1 AS `orthanc_study_id`,
 1 AS `study_instance_uid`,
 1 AS `study_date`,
 1 AS `study_description`,
 1 AS `modality`,
 1 AS `number_of_study_related_series`,
 1 AS `number_of_study_related_instances`,
 1 AS `patient_id`,
 1 AS `patient_name`,
 1 AS `examination_id`,
 1 AS `treatment_phase_id`,
 1 AS `created_at`,
 1 AS `updated_at`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `verification_code`
--

DROP TABLE IF EXISTS `verification_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_code` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expired_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_code`
--

LOCK TABLES `verification_code` WRITE;
/*!40000 ALTER TABLE `verification_code` DISABLE KEYS */;
INSERT INTO `verification_code` VALUES ('749a9b18-f8da-4cae-95bd-f082af4ec6fe','189357','haninhquang2003@gmail.com','2025-12-30 00:21:35.076938'),('b43e732a-5af6-4c0e-9b5d-fd9c4f3c1c47','246537','truong935943@gmail.com','2025-12-13 11:47:29.532873');
/*!40000 ALTER TABLE `verification_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verify_forgot_password`
--

DROP TABLE IF EXISTS `verify_forgot_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verify_forgot_password` (
  `id` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verify_forgot_password`
--

LOCK TABLES `verify_forgot_password` WRITE;
/*!40000 ALTER TABLE `verify_forgot_password` DISABLE KEYS */;
/*!40000 ALTER TABLE `verify_forgot_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'dental_clinic'
--

--
-- Final view structure for view `v_dicom_study_summary`
--

/*!50001 DROP VIEW IF EXISTS `v_dicom_study_summary`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_dicom_study_summary` AS select `ds`.`id` AS `id`,`ds`.`orthanc_study_id` AS `orthanc_study_id`,`ds`.`study_instance_uid` AS `study_instance_uid`,`ds`.`study_date` AS `study_date`,`ds`.`study_description` AS `study_description`,`ds`.`modality` AS `modality`,`ds`.`number_of_study_related_series` AS `number_of_study_related_series`,`ds`.`number_of_study_related_instances` AS `number_of_study_related_instances`,`p`.`id` AS `patient_id`,`ud`.`full_name` AS `patient_name`,`e`.`id` AS `examination_id`,`tp`.`id` AS `treatment_phase_id`,`ds`.`created_at` AS `created_at`,`ds`.`updated_at` AS `updated_at` from ((((`dicom_study` `ds` join `patient` `p` on((`ds`.`patient_id` = `p`.`id`))) left join `user_detail` `ud` on((`p`.`user_id` = `ud`.`user_id`))) left join `examination` `e` on((`ds`.`examination_id` = `e`.`id`))) left join `treatment_phases` `tp` on((`ds`.`treatment_phase_id` = `tp`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-10  6:53:05
