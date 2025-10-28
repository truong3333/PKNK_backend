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
INSERT INTO `appointment` VALUES ('0379fa32-e1dc-4b98-8325-762c0a17cdd5','2025-10-21 17:00:00.000000','Bị sâu răng','Done','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('0fe117a3-bd10-4643-a519-e8bd662cbcf1','2025-10-21 15:00:00.000000','Bị sâu răng','Done','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('877078de-f4f5-480a-a501-d47ffd498758','2025-10-21 14:00:00.000000','Bị sâu răng','Cancel','Niềng răng','36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac'),('b7385b5d-a7a8-4583-9d9b-e3b1245baf10','2024-01-22 17:00:00.000000','Khám theo tiến trình điều trị','Scheduled','TreatmentPhases','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('c97baeb5-0c7f-41c2-ae53-c6d7f3766d3a','2025-10-21 16:00:00.000000','Bị sâu răng','Cancel','Niềng răng','36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac'),('f0d4b58e-a1bd-4a67-9578-977ededbed95','2025-10-22 18:00:00.000000','Bị sâu răng','Done','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('fc349d3a-701b-4d0f-acea-2b2b36558405','2025-10-21 15:00:00.000000','Mới nhổ răng','Cancel','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac');
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
/*!40000 ALTER TABLE `appointment_list_dental_services_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_log` (
  `id` varchar(255) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
INSERT INTO `audit_log` VALUES ('1b4e902c-fcdc-424e-b2c2-a2f316daa2bf','Đăng nhập','2025-10-26 11:27:38.293620','doctor2'),('25f0c45b-ff99-41c1-be68-b4e35410b23a','Đăng nhập','2025-10-23 19:35:45.723309','doctor2'),('2faef296-d5ee-4a81-b443-e432e92376e0','Đăng nhập','2025-10-26 20:09:49.860541','doctor2'),('31dfcf2c-b400-4a3d-9457-c77764c95059','Đăng nhập','2025-10-28 16:53:20.875290','test1'),('337958d3-e239-47d8-a23b-bd4c0bd28ebb','Đăng nhập','2025-10-22 10:54:40.536339','admin'),('3586900d-1a34-4520-b300-44b61160da70','Đăng nhập','2025-10-20 11:29:52.013018','admin'),('41210af8-3f26-44d1-8c6d-39621eca1c41','Đăng nhập','2025-10-24 15:57:56.135744','test1'),('4d2de234-d419-46cd-8b3a-330956a399ac','Đăng nhập','2025-10-22 14:40:29.389477','test1'),('55c94a99-2132-4729-9d55-d407baac0410','Đăng nhập','2025-10-22 11:07:39.989784','test1'),('5dfe4edc-dbcd-424a-8dad-cf051a9ed6e7','Đăng xuất','2025-10-19 10:19:42.124030','admin'),('64b782e4-3c24-450f-a0ba-288160bbd6cb','Đăng nhập','2025-10-23 11:14:14.360197','doctor1'),('6896f388-480d-4c69-89e3-15c09ed2c206','Đăng nhập','2025-10-23 15:48:54.877919','test1'),('6b6d10cc-352a-4050-b308-4b10b8e47aac','Đăng nhập','2025-10-23 19:36:00.511648','test1'),('70b1f632-e27e-4eae-a57a-10e7f9f85fd6','Đăng nhập','2025-10-24 17:54:29.875271','doctor2'),('790128fe-fcbc-47d9-8c20-17604896d1c3','Đăng nhập','2025-10-26 11:42:00.431987','doctor2'),('84d162d6-156b-47eb-b532-4f612a003f53','Đăng nhập','2025-10-23 16:10:53.505576','test1'),('8ecedfb3-47b4-4804-a83b-1d9172707d2f','Đăng nhập','2025-10-19 14:34:25.095182','admin'),('8f4aa0e1-1172-451f-9a12-d3dc13b87b7a','Đăng nhập','2025-10-28 21:22:23.500555','test1'),('9bb6bcbb-911c-4576-aaf2-ae52441ffd4b','Đăng nhập','2025-10-22 20:33:18.817475','test1'),('9c869f65-b902-437a-8f3f-dc8d21bc9150','Đăng nhập','2025-10-22 15:02:46.912274','doctor1'),('9e9984a6-1e1d-4aa9-a639-07943b628f62','Đăng nhập','2025-10-26 12:39:55.266675','doctor2'),('9fee47cd-ca31-4e4a-a03b-27bca12e8caf','Đăng nhập','2025-10-19 14:52:21.502046','test3'),('a278573e-1d97-480f-b7a6-3527b09ec059','cập nhật thông tin liên hệ khẩn cấp','2025-10-22 10:57:36.660291','admin'),('a4bc3037-45ec-4f6b-87c0-e412bd5d352d','cập nhật thông tin y tế','2025-10-22 11:00:28.359640','admin'),('ae2dbe81-0a94-4e8a-b5d3-df9da83acd2c','Đăng nhập','2025-10-19 15:07:56.562431','test3'),('bab3fbcd-e079-4625-956e-86334b34cb18','Đăng nhập','2025-10-19 11:45:23.385402','admin'),('c713210f-ac0e-4fb5-afc5-38effd288746','Cập nhật thông tin cá nhân','2025-10-20 11:34:06.318255','admin'),('cf5d6310-54fe-4185-a3d2-9b927bcb9a88','Đăng nhập','2025-10-22 17:08:54.646075','doctor1'),('d4f81a15-5fb5-496b-a339-f74fb7e569a6','Đăng nhập','2025-10-19 10:21:06.753757','admin'),('d6d9f8c9-f89d-4866-b5c3-d38d437bbb15','Đăng nhập','2025-10-22 17:45:41.967141','admin'),('db485675-ec12-46d6-91f1-9b5265cf8688','Đăng nhập','2025-10-26 20:52:57.611011','test1'),('e3fc7925-c9bf-4714-8fcf-3fc48693d05e','Đăng nhập','2025-10-24 15:55:01.975656','doctor2'),('e6c576d0-7eba-4f95-8d27-0ddd781909e9','Đăng nhập','2025-10-24 17:36:16.660900','doctor2'),('ebe6e847-4706-4611-9f88-9f520703a318','Đăng nhập','2025-10-19 21:54:20.564861','admin'),('f09cb03c-5a4b-48d7-b411-a6a800aa9697','Đăng nhập','2025-10-23 15:48:35.113772','doctor1'),('f0e1c06e-56d0-4179-bf76-6a3b80329ea9','Đăng nhập','2025-10-23 16:41:22.991734','doctor2');
/*!40000 ALTER TABLE `audit_log` ENABLE KEYS */;
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
INSERT INTO `booking_date_time` VALUES ('97436cd3-4b11-45ef-83d6-7de6a1ccfff7','2025-10-21 15:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b'),('97ced313-b227-483f-b851-850f9763584d','2025-10-22 18:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b'),('d5d2b6e0-afaf-4dbb-bbb6-d34bdd4ec386','2024-01-22 17:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b'),('e6d9715a-f8c8-4cf3-b15c-6e6803440ad5','2025-10-21 17:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b');
/*!40000 ALTER TABLE `booking_date_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dental_services_entity`
--

DROP TABLE IF EXISTS `dental_services_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dental_services_entity` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dental_services_entity`
--

LOCK TABLES `dental_services_entity` WRITE;
/*!40000 ALTER TABLE `dental_services_entity` DISABLE KEYS */;
INSERT INTO `dental_services_entity` VALUES ('394a50c3-41ac-4381-9715-5bdaef98f499','Trụ Implant Mỹ','Răng',23000000),('5012015b-8e24-434e-8cab-739bad90653e','Trụ Implant Hàn Quốc thế hệ 2','Răng',13500000),('62dcf052-52af-4803-a71c-77e56bae6d90','Trụ Neoden – Thụy Sĩ','Răng',30000000),('6af078be-f627-4900-bddd-f82932f44339','Phẫu thuật tạo hình nướu quanh Implant','Răng',2000000),('74ae5c8d-a8fa-4981-abbb-d67d37d6abb6','Phẫu thuật lấy trụ Implant cũ','Răng',5000000),('a37442eb-16bb-438b-b74d-79d44fe27260','Trụ Implant Hàn Quốc thế hệ 1','Răng',15000000),('ad47c0bb-7482-499e-b18e-9933e616953b','Phẫu thuật nâng xoang hở','Xoang',15000000),('b9902db5-598b-4aa4-8ecc-3dd1c824a307','Niềng răng mắc cài sứ','2 Hàm',38000000),('ca7009f9-f81b-4090-bebc-79c5edcc1785','Ghép xương và màng xương','Răng',4000000),('cd2a6ec8-a732-435d-bb6e-bc3ca03f36a6','Trụ Nobel','Răng',40000000),('d8002c82-0cf2-4178-8c1f-8810a9b4af26','Phẫu thuật ghép mô liên kết','Răng',5000000),('d9280d5b-3cac-477d-8eb9-8f6ea45a3ae2','Phẫu thuật nâng xoang kín','Răng',8000000),('dba82f50-c8d9-42f4-b34d-62443bc856d2','Niềng răng mắc cài kim loại','2 Hàm',35000000),('fc1c879d-d140-45c5-95c9-6e16e6cb55a6','Niềng răng tháo lắp','Hàm',5000000);
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
INSERT INTO `doctor` VALUES ('36c333c3-9564-4a4b-8a1b-34af719528c1','doctor128432','Niềng răng',4,'d07944e7-9aec-4f69-ba97-4aa3b45314bc'),('8688ef2a-a4e6-4e63-b081-1b32f154ee5b','doctor293772','Răng hàm',6,'969fa480-2e32-4929-bee8-b0f7e095f631');
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
  `notes` varchar(255) DEFAULT NULL,
  `symptoms` varchar(255) DEFAULT NULL,
  `appointment_id` varchar(255) DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `total_cost` double NOT NULL,
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
INSERT INTO `examination` VALUES ('83def3c8-81c8-4326-a716-4dcb2545008a','Viêm nướu cấp tính','Hoàng Công Tráng','Bệnh nhân cần vệ sinh răng miệng tốt hơn','Đau răng hàm trên bên phải, sưng nướu','f0d4b58e-a1bd-4a67-9578-977ededbed95','Làm sạch răng miệng, kê đơn thuốc kháng viêm',0),('9079884c-8d10-49ca-bc57-94762aca2593','Sâu răng giai đoạn 2','Hoàng Công Tráng','Cần theo dõi và tái khám sau 1 tuần','Đau răng hàm dưới bên trái','0379fa32-e1dc-4b98-8325-762c0a17cdd5','Trám răng composite',0);
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
INSERT INTO `image` VALUES ('9c86bea7-5630-46e4-9060-f292d306b3ec','bhezikoqqeofi1fdruru','treatmentPhases','https://res.cloudinary.com/dn2plfafj/image/upload/v1761457713/bhezikoqqeofi1fdruru.jpg',NULL,'bbae2cb0-d9a9-4f9a-a85d-20aeeac70511');
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
INSERT INTO `invalidated_token` VALUES ('5a29999f-fba4-4019-882c-d7d26a79e70e','2025-10-19 11:06:38.000000'),('63f95448-9e3f-4880-998d-1495cdb6ea29','2025-10-12 21:44:58.000000'),('ccb2b2d5-5ee3-43cb-884f-490b7db24ec0','2025-10-12 22:57:09.000000'),('fcfd5614-b4c3-4ca8-9111-144288edb6c2','2025-10-19 10:26:31.000000');
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
  `department` varchar(255) DEFAULT NULL,
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
  `user_id` varchar(255) DEFAULT NULL,
  `allergy` varchar(255) DEFAULT NULL,
  `blood_group` varchar(255) DEFAULT NULL,
  `emergency_contact_name` varchar(255) DEFAULT NULL,
  `emergency_phone_number` varchar(255) DEFAULT NULL,
  `medical_history` varchar(255) DEFAULT NULL,
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
INSERT INTO `patient` VALUES ('200bb0a6-d5c1-47f4-b48f-9de6ed3d9027','6ca100f1-d66b-450b-8430-464a008fcbf3',NULL,NULL,NULL,NULL,NULL),('49bf1013-5204-49f9-ba9f-959df9d909ac','302b1a05-ccaa-4c1a-90da-7c9a6a73b9da',NULL,NULL,NULL,NULL,NULL),('843f26be-b753-4968-a7da-2768c8b44a73','93062bd7-c9b6-426d-9357-2ab2c1829c37','Cá','Nhóm A','Lưu Thị Bắc','0927394328','Bị Covid');
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
INSERT INTO `permission` VALUES ('READ APPOINTMENT','read appointment');
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
INSERT INTO `prescription` VALUES ('Amoxicillin 500mg','500mg','sau ăn 30 phút','3 lần/ngày','Kháng sinh sau nhổ răng, dùng trong 5 ngày',35000),('Chlorhexidine 0.12%','10ml','sáng và tối sau đánh răng','2 lần/ngày','Súc miệng 30 giây, không nuốt',15000),('Ibuprofen 400mg','400mg','sau ăn 30 phút','3 lần/ngày','Giảm đau và chống viêm, không dùng khi đau dạ dày',40000),('Paracetamol 500mg','500mg','sau ăn 30 phút','3 lần/ngày','Dùng khi sốt hoặc đau đầu nhẹ',20000),('Prednisolone 5mg','5mg','uống buổi sáng sau ăn','1 lần/ngày','Chỉ dùng tối đa 3 ngày',20000),('Vitamin C 500mg','1 viên','sau bữa sáng 10 phút','1 lần/ngày','Giúp mau lành vết thương, tăng sức đề kháng',25000);
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
INSERT INTO `role` VALUES ('ADMIN','role admin'),('DOCTOR','role doctor'),('NURSE','role nurse'),('PATIENT','role patient');
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
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
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
  `phase_number` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `treatment_plans_id` varchar(255) DEFAULT NULL,
  `next_appointment` datetime(6) DEFAULT NULL,
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
INSERT INTO `treatment_phases` VALUES ('bbae2cb0-d9a9-4f9a-a85d-20aeeac70511',0,'Bắt đầu điều trị viêm nướu, làm sạch răng chuyên nghiệp','2024-01-22','Phases 1','2024-01-15','Done','219e9eea-dda8-48be-aee3-270f3e60cf0d','2024-01-22 17:00:00.000000');
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
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `total_cost` double NOT NULL,
  `doctor_id` varchar(255) DEFAULT NULL,
  `patient_id` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `create_at` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrdpa7gpa9f50mwf0hwclw4b2n` (`doctor_id`),
  KEY `FKj6p4hrkpk5s8331e913l5c3xv` (`patient_id`),
  CONSTRAINT `FKj6p4hrkpk5s8331e913l5c3xv` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `FKrdpa7gpa9f50mwf0hwclw4b2n` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_plans`
--

LOCK TABLES `treatment_plans` WRITE;
/*!40000 ALTER TABLE `treatment_plans` DISABLE KEYS */;
INSERT INTO `treatment_plans` VALUES ('219e9eea-dda8-48be-aee3-270f3e60cf0d','Kế hoạch điều trị viêm nướu cấp tính update','Inprogress','Điều trị viêm nướu',0,'8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac','3 tuần','Cần tái khám sau 1 tuần để đánh giá tiến triển mới',NULL);
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
INSERT INTO `user` VALUES ('302b1a05-ccaa-4c1a-90da-7c9a6a73b9da',_binary '\0','$2a$10$UTJJvKorEt0AxbqbR.gISujGlxux0lm1oPerhQY5xwxHwzpydL7xm','test1'),('6ca100f1-d66b-450b-8430-464a008fcbf3',_binary '\0','$2a$10$3wgGPPEkpNjDSsVwHKmnXeL6zIFlX0iW5mKb6Q5xeu2WVbk1PnttK','test3'),('93062bd7-c9b6-426d-9357-2ab2c1829c37',_binary '\0','$2a$10$VpU7sZOjXPToxhMdhw.G1OsjmBypMf8XngwPZt1hYjaEDI.kMumI6','test4'),('969fa480-2e32-4929-bee8-b0f7e095f631',_binary '\0','$2a$10$k3nyxwhV6IHTAa5a5VI5Kejg./tFOnOHHvSduOJgqhu0KwZZAPfMC','doctor2'),('9c8d0593-2f97-42bd-8946-3e1e660bc921',_binary '\0','$2a$10$ItvLHKOzvrx8aKh2uHxoJubhDNUCPmiEi663VZnlQnidpZQmB0usC','admin'),('d07944e7-9aec-4f69-ba97-4aa3b45314bc',_binary '\0','$2a$10$ufHnPKvmdm4jlY4FJyoNJ.29DRnB8IqoZNUgErpccgj2ncdD/zpX.','doctor1');
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
INSERT INTO `user_detail` VALUES ('138fc429-15fa-43e0-a937-205ae4bb4990','Bắc Ninh',NULL,'2003-02-02',NULL,'admin','Nam','0929282933','9c8d0593-2f97-42bd-8946-3e1e660bc921'),('3837de7f-e122-40fa-9595-bbd2d22c3507',NULL,'2025-10-11',NULL,'truong935943@gmail.com',NULL,NULL,NULL,'302b1a05-ccaa-4c1a-90da-7c9a6a73b9da'),('968cb08f-ba57-4b18-a5c5-4274d5bfb300','Hà Nội','2025-10-11','1988-02-02','truong935943@gmail.com','Lê Quang Hùng','Nam','0927348284','d07944e7-9aec-4f69-ba97-4aa3b45314bc'),('a4e94f37-7b40-4a26-9708-e209fa6b7f34',NULL,'2025-10-19',NULL,'truong935943@gmail.com',NULL,NULL,NULL,'6ca100f1-d66b-450b-8430-464a008fcbf3'),('c5b340f8-bf8c-4569-b790-c99d95a724a5','Hà Nội','2025-10-22','1983-03-08','truong935943@gmail.com','Hoàng Công Tráng','Nam','0982937832','969fa480-2e32-4929-bee8-b0f7e095f631'),('d699cc69-42e5-4fb9-9821-0e0a4749f73b',NULL,'2025-10-19',NULL,'truong935943@gmail.com','Long',NULL,NULL,'93062bd7-c9b6-426d-9357-2ab2c1829c37');
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
INSERT INTO `user_roles` VALUES ('9c8d0593-2f97-42bd-8946-3e1e660bc921','ADMIN'),('969fa480-2e32-4929-bee8-b0f7e095f631','DOCTOR'),('d07944e7-9aec-4f69-ba97-4aa3b45314bc','DOCTOR'),('302b1a05-ccaa-4c1a-90da-7c9a6a73b9da','PATIENT'),('6ca100f1-d66b-450b-8430-464a008fcbf3','PATIENT'),('93062bd7-c9b6-426d-9357-2ab2c1829c37','PATIENT');
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
INSERT INTO `verification_code` VALUES ('16a33d79-a886-49d0-a058-41307d8ab864','958138','truong935943@gmail.com','2025-10-22 11:42:06.057213');
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
INSERT INTO `verify_forgot_password` VALUES ('04f71cb1-e055-44e3-997b-99d2a30f2281','2025-10-19 09:02:25.836288','test1'),('865fc15c-d303-4c66-9629-0a2af82550d0','2025-10-15 14:56:49.731320','test1');
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

-- Dump completed on 2025-10-28 21:40:25
