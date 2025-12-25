CREATE DATABASE  IF NOT EXISTS `dental_clinic` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dental_clinic`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dental_clinic
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

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
INSERT INTO `appointment` VALUES ('5b64f956-1961-4dde-8db2-43809b6721c0','2025-12-04 09:00:00.000000','em bị sâu răng',NULL,'Scheduled','Niềng răng mắc cài sứ','3499aba6-dece-4097-bd76-1987135adb4a','a68e2737-ae82-4e1a-abd1-c985d14912de'),('d88d7c8d-1456-4291-9405-2f9f6f77ec01','2025-12-11 14:00:00.000000','',NULL,'Scheduled','Nhổ răng khôn hàm dưới','49cf0645-20f4-4f61-9404-db65ece6fd92','a68e2737-ae82-4e1a-abd1-c985d14912de');
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
INSERT INTO `appointment_list_dental_services_entity` VALUES ('5b64f956-1961-4dde-8db2-43809b6721c0','92c324f2-1ff1-434a-a8ac-3000902e813a'),('d88d7c8d-1456-4291-9405-2f9f6f77ec01','10a72aac-deb8-4db8-be92-d2d1d6ed58cd');
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
INSERT INTO `booking_date_time` VALUES ('50225060-80b1-4741-9402-68756b05ed58','2025-12-11 14:00:00.000000','49cf0645-20f4-4f61-9404-db65ece6fd92'),('da9f29a3-02dc-4138-ad85-9b420b78ea5a','2025-12-04 09:00:00.000000','3499aba6-dece-4097-bd76-1987135adb4a');
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
  PRIMARY KEY (`id`),
  KEY `FKexauo4htrd4c6j98vv31lp372` (`patient_id`),
  CONSTRAINT `FKexauo4htrd4c6j98vv31lp372` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost`
--

LOCK TABLES `cost` WRITE;
/*!40000 ALTER TABLE `cost` DISABLE KEYS */;
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
  KEY `FK2v0feum1rn94phqttsg5bhy8r` (`cost_id`),
  CONSTRAINT `FK2v0feum1rn94phqttsg5bhy8r` FOREIGN KEY (`cost_id`) REFERENCES `cost` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost_list_dental_service_entity_order`
--

LOCK TABLES `cost_list_dental_service_entity_order` WRITE;
/*!40000 ALTER TABLE `cost_list_dental_service_entity_order` DISABLE KEYS */;
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
INSERT INTO `doctor` VALUES ('3499aba6-dece-4097-bd76-1987135adb4a','BS29503','Niềng Răng',4,'ec466f69-d577-4ed4-a3db-d51e8a66de23'),('466f26bb-6127-41f2-8f11-fdfa06e27013','BS04295','Trồng răng Implant',10,'2cef34e9-757a-4bea-98c1-0c1180bf47f9'),('49cf0645-20f4-4f61-9404-db65ece6fd92','BS21500','Trồng răng ',6,'b3a4c6f0-4f54-4ef8-911d-1049c1afd532');
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkri7gwdsgw6hp6cf0jexwhl7w` (`appointment_id`),
  CONSTRAINT `FK7dqgrq2dnuomi11x7x3vhl5dl` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination`
--

LOCK TABLES `examination` WRITE;
/*!40000 ALTER TABLE `examination` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination` ENABLE KEYS */;
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
  KEY `FK6qqc1j0cch72gl4nc67bnd0bx` (`examination_id`),
  CONSTRAINT `FK6qqc1j0cch72gl4nc67bnd0bx` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination_list_dental_services_entity_order`
--

LOCK TABLES `examination_list_dental_services_entity_order` WRITE;
/*!40000 ALTER TABLE `examination_list_dental_services_entity_order` DISABLE KEYS */;
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
INSERT INTO `invalidated_token` VALUES ('19877703-70ef-4002-a800-d3d23fa269df','2025-12-13 12:36:13.000000'),('573065ac-144b-41b1-8731-d57500f66049','2025-12-13 11:42:59.000000');
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
INSERT INTO `patient` VALUES ('a68e2737-ae82-4e1a-abd1-c985d14912de',NULL,NULL,NULL,NULL,NULL,'77623a83-43ac-4566-a19d-d3fb9c634a95');
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
INSERT INTO `permission` VALUES ('CREATE_EXAMINATION','thêm hồ sơ khám ban đầu'),('CREATE_TOOTH_STATUS','thêm trạng thái răng của bệnh nhân'),('CREATE_TREATMENT_PHASES','thêm tiến trình điều trị'),('CREATE_TREATMENT_PLANS','thêm phác đồ điều trị'),('GET_ALL_TREATMENT_PHASES','lấy danh sách tiến trình điều trị của phác đồ'),('GET_BASIC_INFO','lấy thông tin cơ bản của bệnh nhân'),('GET_EXAMINATION_DETAIL','xem chi tiết hồ sơ khám ban đầu'),('GET_INFO_DOCTOR','xem thông tin bác sĩ'),('GET_INFO_NURSE','lấy thông tin y tá'),('GET_TOOTH_STATUS','lấy thông tin trạng thái răng của bệnh nhân'),('NOTIFICATION_APPOINMENT','cập nhật trạng thái thông báo lịch hẹn'),('PICK_DOCTOR','chọn bác sĩ có trong danh sách'),('PICK_NURSE','chọn y tá theo dõi phác đồ'),('UPDATE_EXAMINATION','cập nhật hồ sơ khám ban đầu'),('UPDATE_PAYMENT_COST','cập nhật thanh toán vnpay'),('UPDATE_TOOTH_STATUS','cập nhật trạng thái răng của bệnh nhân'),('UPDATE_TREATMENT_PHASES','cập nhật tiến trình điều trị'),('UPDATE_TREATMENT_PLANS','cập nhật tiến trình điều trị');
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
INSERT INTO `role_permissions` VALUES ('DOCTOR','CREATE_EXAMINATION'),('DOCTORLV2','CREATE_EXAMINATION'),('DOCTOR','CREATE_TOOTH_STATUS'),('DOCTORLV2','CREATE_TOOTH_STATUS'),('DOCTOR','CREATE_TREATMENT_PHASES'),('DOCTORLV2','CREATE_TREATMENT_PHASES'),('DOCTOR','CREATE_TREATMENT_PLANS'),('DOCTORLV2','CREATE_TREATMENT_PLANS'),('DOCTOR','GET_ALL_TREATMENT_PHASES'),('DOCTORLV2','GET_ALL_TREATMENT_PHASES'),('NURSE','GET_ALL_TREATMENT_PHASES'),('PATIENT','GET_ALL_TREATMENT_PHASES'),('DOCTOR','GET_BASIC_INFO'),('DOCTORLV2','GET_BASIC_INFO'),('NURSE','GET_BASIC_INFO'),('PATIENT','GET_BASIC_INFO'),('DOCTOR','GET_EXAMINATION_DETAIL'),('DOCTORLV2','GET_EXAMINATION_DETAIL'),('DOCTORLV2','GET_INFO_DOCTOR'),('NURSE','GET_INFO_DOCTOR'),('PATIENT','GET_INFO_DOCTOR'),('DOCTOR','GET_INFO_NURSE'),('DOCTORLV2','GET_INFO_NURSE'),('PATIENT','GET_INFO_NURSE'),('DOCTOR','GET_TOOTH_STATUS'),('DOCTORLV2','GET_TOOTH_STATUS'),('NURSE','GET_TOOTH_STATUS'),('PATIENT','GET_TOOTH_STATUS'),('NURSE','NOTIFICATION_APPOINMENT'),('DOCTORLV2','PICK_DOCTOR'),('NURSE','PICK_DOCTOR'),('PATIENT','PICK_DOCTOR'),('DOCTOR','PICK_NURSE'),('DOCTORLV2','PICK_NURSE'),('DOCTOR','UPDATE_EXAMINATION'),('DOCTORLV2','UPDATE_EXAMINATION'),('DOCTOR','UPDATE_PAYMENT_COST'),('DOCTORLV2','UPDATE_PAYMENT_COST'),('NURSE','UPDATE_PAYMENT_COST'),('PATIENT','UPDATE_PAYMENT_COST'),('DOCTOR','UPDATE_TOOTH_STATUS'),('DOCTORLV2','UPDATE_TOOTH_STATUS'),('DOCTOR','UPDATE_TREATMENT_PHASES'),('DOCTORLV2','UPDATE_TREATMENT_PHASES'),('DOCTOR','UPDATE_TREATMENT_PLANS'),('DOCTORLV2','UPDATE_TREATMENT_PLANS');
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
  PRIMARY KEY (`id`),
  KEY `FKsihldx8464uc02ipwj5tgr0kx` (`treatment_plans_id`),
  CONSTRAINT `FKsihldx8464uc02ipwj5tgr0kx` FOREIGN KEY (`treatment_plans_id`) REFERENCES `treatment_plans` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_phases`
--

LOCK TABLES `treatment_phases` WRITE;
/*!40000 ALTER TABLE `treatment_phases` DISABLE KEYS */;
/*!40000 ALTER TABLE `treatment_phases` ENABLE KEYS */;
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
  KEY `FK6f2ldsdoucgmvgtxhws7m0s31` (`treatment_phases_id`),
  CONSTRAINT `FK6f2ldsdoucgmvgtxhws7m0s31` FOREIGN KEY (`treatment_phases_id`) REFERENCES `treatment_phases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_phases_list_dental_service_entity_order`
--

LOCK TABLES `treatment_phases_list_dental_service_entity_order` WRITE;
/*!40000 ALTER TABLE `treatment_phases_list_dental_service_entity_order` DISABLE KEYS */;
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
INSERT INTO `user` VALUES ('2cef34e9-757a-4bea-98c1-0c1180bf47f9',_binary '\0','$2a$10$WtwdiJrz4ONgooP7p1EgjeFk39kGw/5PeKQByAJ4bsF8bUzuAnV4y','doctorlv2'),('70dc2719-1f18-4351-a78a-eb693749da8c',_binary '\0','$2a$10$8sI853/hibsncznC.xzAduEOoleMf4MhtV/W2IDBPqP./d8TeV95O','admin'),('77623a83-43ac-4566-a19d-d3fb9c634a95',_binary '\0','$2a$10$Xa7GHsTDgmYCn17UrTs/teUt.ybfwN.yGrt72kMvGcLh1XAZeQNSK','benhnhan1'),('b3a4c6f0-4f54-4ef8-911d-1049c1afd532',_binary '\0','$2a$10$Mmchr/qCbKfMqyymg7/UFOB0qZqwltzqhp35qaRBJqyZL1D6ki2RW','doctor1'),('b991c5ca-0967-419e-b155-491abc512252',_binary '\0','$2a$10$0ejVAK6VJld4REqgzElCOOvy5oR.48Tp2vPaC2gviwjwhxzMmQ2EW','nurse2'),('bc450a3e-092e-4e2d-8f18-71b5222f1d68',_binary '\0','$2a$10$JzmlrAOl2KNrTP2Dk/zzGOBGHGRcgwJUDRQDg.SI/0X.EnL8pS8cS','nurse1'),('ec466f69-d577-4ed4-a3db-d51e8a66de23',_binary '\0','$2a$10$aNPmkv6Gw4zjVNs0IJGDyuA/xgMfEWdg.JJpuJS.tGU.OqyJ2ARDK','doctor2');
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
INSERT INTO `user_detail` VALUES ('1ef5663c-d8b6-4b4b-8b71-c381167b9577','Lục Ngạn - Bắc Giang','2025-12-13','1972-10-17','truong935943@gmail.com','Hoàng Đình Cường','male','0926338423','2cef34e9-757a-4bea-98c1-0c1180bf47f9'),('2c122a66-b7da-4445-adf5-11c721c260c3','Quốc Oai - Hà Nội','2025-12-13','1984-06-14','truong935943@gmail.com','Lê Quang Hùng','male','0984763444','ec466f69-d577-4ed4-a3db-d51e8a66de23'),('335f7fe6-d6da-4ecf-92dc-0567b4341365',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'70dc2719-1f18-4351-a78a-eb693749da8c'),('50f1f6d7-f3ab-4462-96a9-372f071f0b60','Ninh Bình','2025-12-13',NULL,'truong935943@gmail.com','Nguyễn Xuân Thu','male','0933257332','b991c5ca-0967-419e-b155-491abc512252'),('8b6f664e-ef17-475d-bba1-72f3dc5a687a','Hà Đông - Hà Nội','2025-12-13','2004-11-24','truong935943@gmail.com','Mạc Xuân Trí','male','0923789223','77623a83-43ac-4566-a19d-d3fb9c634a95'),('a9fed5cd-1e08-45be-93e7-0fa7a0f2b4fe','Cao Bằng','2025-12-13','1994-06-17','truong935943@gmail.com','Lý Thanh Huyền','female','0927728145','bc450a3e-092e-4e2d-8f18-71b5222f1d68'),('bb74c6dc-bea3-4c29-91e0-34a2b8e81d0b','Thanh Xuân - Hà Nội','2025-12-13','1981-02-13','truong935943@gmail.com','Hoàng Công Tráng','male','0936228135','b3a4c6f0-4f54-4ef8-911d-1049c1afd532');
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
INSERT INTO `user_roles` VALUES ('70dc2719-1f18-4351-a78a-eb693749da8c','ADMIN'),('b3a4c6f0-4f54-4ef8-911d-1049c1afd532','DOCTOR'),('ec466f69-d577-4ed4-a3db-d51e8a66de23','DOCTOR'),('2cef34e9-757a-4bea-98c1-0c1180bf47f9','DOCTORLV2'),('b991c5ca-0967-419e-b155-491abc512252','NURSE'),('bc450a3e-092e-4e2d-8f18-71b5222f1d68','NURSE'),('77623a83-43ac-4566-a19d-d3fb9c634a95','PATIENT');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `verification_code` VALUES ('b43e732a-5af6-4c0e-9b5d-fd9c4f3c1c47','246537','truong935943@gmail.com','2025-12-13 11:47:29.532873');
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-13 15:08:02
