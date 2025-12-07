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
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('0379fa32-e1dc-4b98-8325-762c0a17cdd5','2025-10-21 17:00:00.000000','Bị sâu răng','Done','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('0fe117a3-bd10-4643-a519-e8bd662cbcf1','2025-10-21 15:00:00.000000','Bị sâu răng','Done','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('877078de-f4f5-480a-a501-d47ffd498758','2025-10-21 14:00:00.000000','Bị sâu răng','Cancel','Niềng răng','36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac'),('b7385b5d-a7a8-4583-9d9b-e3b1245baf10','2025-11-03 08:00:00.000000','Khám theo tiến trình điều trị','Scheduled','TreatmentPhases','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('b73fc62d-e69f-4cbd-ae45-22eb46d1c944','2024-01-22 17:00:00.000000','nếu mưa e sẽ đến muộn chút\n','Done','Trụ Neoden – Thụy Sĩ','36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac'),('c97baeb5-0c7f-41c2-ae53-c6d7f3766d3a','2025-10-21 16:00:00.000000','Bị sâu răng','Cancel','Niềng răng','36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac'),('f03190cb-9deb-4cd2-8a67-1771c80a9e31','2025-12-07 13:00:00.000000','','Scheduled','Răng sứ Titan','36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac'),('f0d4b58e-a1bd-4a67-9578-977ededbed95','2025-10-22 18:00:00.000000','Bị sâu răng','Done','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac'),('fc349d3a-701b-4d0f-acea-2b2b36558405','2025-10-21 15:00:00.000000','Mới nhổ răng','Cancel','Niềng răng','8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `appointment_list_dental_services_entity`
--

LOCK TABLES `appointment_list_dental_services_entity` WRITE;
/*!40000 ALTER TABLE `appointment_list_dental_services_entity` DISABLE KEYS */;
INSERT INTO `appointment_list_dental_services_entity` VALUES ('b73fc62d-e69f-4cbd-ae45-22eb46d1c944','62dcf052-52af-4803-a71c-77e56bae6d90'),('f03190cb-9deb-4cd2-8a67-1771c80a9e31','2075642f-de85-458b-9e18-442f690c3a91');
/*!40000 ALTER TABLE `appointment_list_dental_services_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `booking_date_time`
--

LOCK TABLES `booking_date_time` WRITE;
/*!40000 ALTER TABLE `booking_date_time` DISABLE KEYS */;
INSERT INTO `booking_date_time` VALUES ('31705dfc-e0f0-4ff2-9f63-831c07ed1858','2024-01-22 17:00:00.000000','36c333c3-9564-4a4b-8a1b-34af719528c1'),('6d1d112f-f5ec-4982-b7eb-7720eef9de10','2025-11-03 08:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b'),('8f98c52e-8dbc-4d58-8a71-ca97ddd474ae','2025-12-07 13:00:00.000000','36c333c3-9564-4a4b-8a1b-34af719528c1'),('97436cd3-4b11-45ef-83d6-7de6a1ccfff7','2025-10-21 15:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b'),('97ced313-b227-483f-b851-850f9763584d','2025-10-22 18:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b'),('e6d9715a-f8c8-4cf3-b15c-6e6803440ad5','2025-10-21 17:00:00.000000','8688ef2a-a4e6-4e63-b081-1b32f154ee5b');
/*!40000 ALTER TABLE `booking_date_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category_dental`
--

LOCK TABLES `category_dental` WRITE;
/*!40000 ALTER TABLE `category_dental` DISABLE KEYS */;
INSERT INTO `category_dental` VALUES ('0d6f425e-577b-44c3-806e-d616c02a9b5d','Trồng răng sứ'),('52e8d218-3c5c-45ce-9464-438c573653ca','Lấy cao răng'),('5c70a5c4-9125-4684-aa7d-32a6c440f4d1','Nhổ răng'),('75fe2d18-110c-43d6-b1d0-a5331e4cc6ea','Niềng răng'),('e7b17c2c-8aac-4d04-96b6-a1265d8592da','Trồng răng Implant');
/*!40000 ALTER TABLE `category_dental` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cost`
--

LOCK TABLES `cost` WRITE;
/*!40000 ALTER TABLE `cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cost_list_dental_service_entity_order`
--

LOCK TABLES `cost_list_dental_service_entity_order` WRITE;
/*!40000 ALTER TABLE `cost_list_dental_service_entity_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `cost_list_dental_service_entity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cost_list_prescription_order`
--

LOCK TABLES `cost_list_prescription_order` WRITE;
/*!40000 ALTER TABLE `cost_list_prescription_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `cost_list_prescription_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `dental_services_entity`
--

LOCK TABLES `dental_services_entity` WRITE;
/*!40000 ALTER TABLE `dental_services_entity` DISABLE KEYS */;
INSERT INTO `dental_services_entity` VALUES ('12d50d4c-e075-4be6-b07a-259395db2cd8','Trụ Implant Mỹ','Răng',23000000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('1a9ec7ea-193d-4ba7-817a-dc341c2c332d','Nhổ răng khôn hàm trên mọc thẳng','Răng',1300000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('1da9e760-6181-4d2a-8cbd-19fcd9289aaa','Nhổ răng khôn hàm dưới mọc lệch 45 độ','Răng',2500000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('2075642f-de85-458b-9e18-442f690c3a91','Răng sứ Titan','Răng',2500000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('208bf601-827f-4fbf-84f3-72a1741e441f','Mắc cài tự động','1 Hàm',5000000,'75fe2d18-110c-43d6-b1d0-a5331e4cc6ea',0),('26686096-cc6b-450b-a584-3a8dfc74a9bf','Trụ Implant Hàn Quốc thế hệ 1','Răng',15000000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('3302b02a-5d67-460e-ad67-354af0b70f8a','Nhổ răng khôn hàm dưới mọc thẳng','Răng',2000000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('3ae42d2c-4007-4271-ab8d-88c449bc2ca0','Rạch lợi trùm','Răng',1100000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('3ecb6672-1ff1-409f-9ca3-a54a4c4a9024','Phẫu thuật lấy trụ Implant cũ','Răng',5000000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('46259e94-7c5e-4ce7-a48f-eb9df9515398','Chỉnh nha trẻ em (1 – 2 răng)','Trọn gói',5000000,'75fe2d18-110c-43d6-b1d0-a5331e4cc6ea',0),('485e30f6-bcfd-4c57-a7a4-5f02cac6be08','Đánh bóng răng','1 Ca',100000,'52e8d218-3c5c-45ce-9464-438c573653ca',0),('48bf8b1d-10e1-4d14-9e1e-60e310f57f89','Lấy cao răng độ 3','1 Ca',400000,'52e8d218-3c5c-45ce-9464-438c573653ca',0),('48cca822-d243-4800-9617-4c2d1f5265f6','Gắn lại mão răng','Răng',500000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('4d82c2ce-bd78-40d0-8398-b8a7ecdc6390','Răng sứ Ziconia','Răng',4000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('5557288f-d84d-4981-9dfe-30fa037ef37f','Răng sứ Nacera','Răng',6000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('594b8779-92ea-4712-8d57-1a84665aa755','Phẫu thuật tạo hình nướu quanh Implant','Răng',2000000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('59f09e80-5f7c-43d6-a07f-3abb1d700bce','Niềng răng mắc cài kim loại','2 Hàm',35000000,'75fe2d18-110c-43d6-b1d0-a5331e4cc6ea',0),('5e016c1e-3e3b-48b3-8143-8ad921ac9efa','Răng sứ Venus','Răng',3500000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('608e5edb-958b-48ba-b68d-4ea5e2fb1326','Nhổ răng khôn hàm dưới mọc lệch ngầm','Răng',4000000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('61601930-e811-4662-b9eb-317a194387b8','Nhổ răng hàm lớn (6,7)','Răng',2000000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('61ab9da1-492f-4ced-ab6c-339c434bac76','Đúc cùi giả toàn sứ','Răng',3000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('62a5d1bb-e30e-46a4-af53-1f04087431f2','Nhổ răng khôn hàm trên mọc lệch','Răng',2000000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('62dcf052-52af-4803-a71c-77e56bae6d90','Trụ Neoden – Thụy Sĩ','Răng',30000000,NULL,0),('64c0d3fe-0464-4e72-baa6-0790857e9a1c','Nhổ răng khôn hàm trên mọc ngầm','Răng',3000000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('7c5f7b44-6a11-42e8-8f28-bf66718385a7','Nhổ răng hàm nhỏ (4,5)','Răng',900000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('80831500-a21a-439e-9116-2dcfd26e8c02','Nhổ răng khôn hàm dưới mọc lệch 90 độ','Răng',3000000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('88261112-6186-442a-9267-133ce25d5c52','Răng sứ Veneer Lisi','Răng',11000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('9826c34c-f8b0-4ade-97b1-d314ef4789ff','Trụ Implant Hàn Quốc thế hệ 2','Răng',13500000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('98bc7ff7-aa36-42e5-8f52-169a297ca0f8','Niềng răng mắc cài sứ','2 Hàm',45000000,'75fe2d18-110c-43d6-b1d0-a5331e4cc6ea',0),('99b216db-241b-4619-aad3-971834b53380','Trụ Neoden – Thụy Sĩ','Răng',30000000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('a64a5e8d-96a4-4194-84cf-2242d58048f8','Răng sứ Cercon','Răng',5000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('ab7da778-e310-4e81-97d3-9949651ba9ce','Răng sứ Katana','Răng',4000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('af4b6ad0-5b86-4c5e-815a-321a1fe01525','Chốt sợi','Răng',1000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('b3776e8f-3b72-4195-91e6-21e6c9941054','Chỉnh nha người lớn (1 – 2 răng)','Trọn gói',10000000,'75fe2d18-110c-43d6-b1d0-a5331e4cc6ea',0),('b3acfb62-94c7-4843-9d3a-f391874f1a59','Trụ Nobel','Răng',40000000,'e7b17c2c-8aac-4d04-96b6-a1265d8592da',0),('bd33f5cf-09ef-424f-8555-def23f3b65db','Lấy cao răng độ 2','1 Ca',300000,'52e8d218-3c5c-45ce-9464-438c573653ca',0),('bf715c40-d084-4a26-87ba-3f38d8d13a05','Nhổ chân răng','Răng',800000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('c31c95d8-460b-4729-9d89-4ea800bd7326','Răng sứ Orodent Gold','Răng',12000000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('c4516a65-a81a-4cfa-a76c-179cedec1d61','Răng sứ Ceramill','Răng',5500000,'0d6f425e-577b-44c3-806e-d616c02a9b5d',0),('d4b055cd-e4c8-42ba-aa86-6cdd36baef4f','Lấy cao răng độ 1','1 Ca',200000,'52e8d218-3c5c-45ce-9464-438c573653ca',0),('e3812d42-243f-4f7a-bc93-477feba7319b','Nhổ răng sữa','Răng',100000,'5c70a5c4-9125-4684-aa7d-32a6c440f4d1',0),('f0a497f2-b98d-45fe-a593-cebcb3571d60','Lấy cao răng bằng công nghệ thổi cát','1 Ca',600000,'52e8d218-3c5c-45ce-9464-438c573653ca',0),('f81f0fb6-682f-4c9a-aea3-3bdf3a1139f4','Niềng răng tháo lắp','1 Hàm',5000000,'75fe2d18-110c-43d6-b1d0-a5331e4cc6ea',0);
/*!40000 ALTER TABLE `dental_services_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES ('36c333c3-9564-4a4b-8a1b-34af719528c1','doctor128432','Niềng răng',4,'d07944e7-9aec-4f69-ba97-4aa3b45314bc'),('8688ef2a-a4e6-4e63-b081-1b32f154ee5b','doctor293772','Răng hàm',6,'969fa480-2e32-4929-bee8-b0f7e095f631');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `examination`
--

LOCK TABLES `examination` WRITE;
/*!40000 ALTER TABLE `examination` DISABLE KEYS */;
INSERT INTO `examination` VALUES ('753f6400-58c0-4e4c-8dbe-e7bdd86f2a0b','trồng răng mới ','Lê Quang Hùng','Cần theo dõi và tái khám sau 1 tuần','Hỏng răng hàm dưới bên trái','b73fc62d-e69f-4cbd-ae45-22eb46d1c944','Trồng răng neoden thuỵ sĩ',0,NULL),('83def3c8-81c8-4326-a716-4dcb2545008a','Viêm nướu cấp tính','Hoàng Công Tráng','Bệnh nhân cần vệ sinh răng miệng tốt hơn','Đau răng hàm trên bên phải, sưng đỏ nướu','f0d4b58e-a1bd-4a67-9578-977ededbed95','Làm sạch răng miệng, kê đơn thuốc kháng viêm',0,NULL),('9079884c-8d10-49ca-bc57-94762aca2593','Sâu răng giai đoạn 2','Hoàng Công Tráng','Cần theo dõi và tái khám sau 1 tuần','Đau răng hàm dưới bên trái','0379fa32-e1dc-4b98-8325-762c0a17cdd5','Trám răng composite',0,NULL);
/*!40000 ALTER TABLE `examination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `examination_list_dental_services_entity_order`
--

LOCK TABLES `examination_list_dental_services_entity_order` WRITE;
/*!40000 ALTER TABLE `examination_list_dental_services_entity_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination_list_dental_services_entity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `examination_list_prescription_order`
--

LOCK TABLES `examination_list_prescription_order` WRITE;
/*!40000 ALTER TABLE `examination_list_prescription_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination_list_prescription_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES ('1bf26e05-9d1d-43b7-a180-3954dbc8b973','m4oujxagvtb13lcw07yq','examination','https://res.cloudinary.com/dn2plfafj/image/upload/v1761972911/m4oujxagvtb13lcw07yq.jpg','83def3c8-81c8-4326-a716-4dcb2545008a',NULL),('4b7f87a2-36c6-49fb-a5be-dde407e92c0f','i9sqk1go2o7klpdn4stu','examinationFace','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316074/i9sqk1go2o7klpdn4stu.jpg','753f6400-58c0-4e4c-8dbe-e7bdd86f2a0b',NULL),('4d6bfc34-036e-4fae-96ff-d6731c59c5ca','gwunetqhs46t1oo51bao','examinationTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316076/gwunetqhs46t1oo51bao.jpg','753f6400-58c0-4e4c-8dbe-e7bdd86f2a0b',NULL),('686e8e66-cb4b-4ad5-9512-15be4ab80434','yqkgnf0zk9wkqpkrrn7k','treatmentPhasesTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316481/yqkgnf0zk9wkqpkrrn7k.jpg',NULL,'7a1614ab-654e-4eb1-97e4-7511511389e4'),('68f07aa1-6042-4045-b2ba-f0283bbb253a','hybmqpcufsfjgzuanxam','examination','https://res.cloudinary.com/dn2plfafj/image/upload/v1761973105/hybmqpcufsfjgzuanxam.jpg','83def3c8-81c8-4326-a716-4dcb2545008a',NULL),('6e6cab62-7069-4710-bf36-eac5bc3e7043','nng9tnvrzf88mxsqjym2','examinationXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316073/nng9tnvrzf88mxsqjym2.jpg','753f6400-58c0-4e4c-8dbe-e7bdd86f2a0b',NULL),('805d8789-f2ca-4039-8841-535d63503333','eyydk82bnecoqpgmc5so','examination','https://res.cloudinary.com/dn2plfafj/image/upload/v1761973104/eyydk82bnecoqpgmc5so.jpg','83def3c8-81c8-4326-a716-4dcb2545008a',NULL),('83f90144-1841-4c28-ab92-b933eebebaa7','yt02vipvxbcgwr5nqr0z','examination','https://res.cloudinary.com/dn2plfafj/image/upload/v1761973129/yt02vipvxbcgwr5nqr0z.jpg','83def3c8-81c8-4326-a716-4dcb2545008a',NULL),('86609efa-a514-4352-88f1-4f41374451c3','h57noktncvoqvsflopfd','examination','https://res.cloudinary.com/dn2plfafj/image/upload/v1761972909/h57noktncvoqvsflopfd.jpg','83def3c8-81c8-4326-a716-4dcb2545008a',NULL),('9c86bea7-5630-46e4-9060-f292d306b3ec','bhezikoqqeofi1fdruru','treatmentPhases','https://res.cloudinary.com/dn2plfafj/image/upload/v1761457713/bhezikoqqeofi1fdruru.jpg',NULL,'bbae2cb0-d9a9-4f9a-a85d-20aeeac70511'),('b1e53ca9-2e58-49eb-a3d9-90b3b9b3a6b5','r3msakstlzuowcx3qlvr','treatmentPhasesFace','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316479/r3msakstlzuowcx3qlvr.jpg',NULL,'7a1614ab-654e-4eb1-97e4-7511511389e4'),('b1fe5c4e-db88-42a1-a039-0bde764574ad','zbhsf7llwqzabhpqdon7','treatmentPhasesTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316482/zbhsf7llwqzabhpqdon7.jpg',NULL,'7a1614ab-654e-4eb1-97e4-7511511389e4'),('cc6b7ddc-8d96-4caf-8c5b-fdeddbb6d24c','b82czgw15vwwybriifkv','examinationTeeth','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316075/b82czgw15vwwybriifkv.jpg','753f6400-58c0-4e4c-8dbe-e7bdd86f2a0b',NULL),('efa41c00-80f4-46f7-a1ba-7d6b31b043f8','ft4u8qyuhetzl03pppqd','treatmentPhasesXray','https://res.cloudinary.com/dn2plfafj/image/upload/v1762316478/ft4u8qyuhetzl03pppqd.jpg',NULL,'7a1614ab-654e-4eb1-97e4-7511511389e4');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `invalidated_token`
--

LOCK TABLES `invalidated_token` WRITE;
/*!40000 ALTER TABLE `invalidated_token` DISABLE KEYS */;
INSERT INTO `invalidated_token` VALUES ('14faf07b-ecd0-4cd3-bfac-d48eb47f11a4','2025-12-06 15:33:47.000000'),('15507bde-3e2a-4cc2-b5c3-de9a946e2cf2','2025-12-06 15:31:59.000000'),('28f37f1e-d22d-4871-abcd-b89d3fb48e2a','2025-12-06 15:23:15.000000'),('30002706-46bd-4e16-8e88-ade1f0f9d25f','2025-12-06 15:37:08.000000'),('38d86a82-0e9a-421b-b4ea-a58286d37439','2025-12-06 15:22:46.000000'),('39ce0349-72cb-4e5b-8cdd-d2ab12292b3e','2025-12-06 15:50:51.000000'),('5a29999f-fba4-4019-882c-d7d26a79e70e','2025-10-19 11:06:38.000000'),('62168fb5-fb2d-4f72-8350-11df4f382523','2025-12-06 15:28:32.000000'),('63f95448-9e3f-4880-998d-1495cdb6ea29','2025-10-12 21:44:58.000000'),('65e89296-a0a0-4966-842a-b24b5026ef18','2025-11-01 14:56:48.000000'),('6cb86520-6558-4a1c-86ff-4037ea7b6e23','2025-12-06 15:31:15.000000'),('81f18cad-3741-4442-87a9-0ef8468a6b4a','2025-12-06 15:31:33.000000'),('836839b6-3991-48c0-afa5-b9cd26e9fdea','2025-12-06 15:20:09.000000'),('ad3c4d13-8ecf-47ed-8c66-2edbb83523c9','2025-12-06 15:38:36.000000'),('ba10693d-50a1-4677-a3bf-c51fc3bcd6ff','2025-12-06 15:32:48.000000'),('c68025f7-3dcf-4f17-ad3b-978f7558aa65','2025-11-01 15:11:44.000000'),('ccb2b2d5-5ee3-43cb-884f-490b7db24ec0','2025-10-12 22:57:09.000000'),('fcfd5614-b4c3-4ca8-9111-144288edb6c2','2025-10-19 10:26:31.000000');
/*!40000 ALTER TABLE `invalidated_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `nurse`
--

LOCK TABLES `nurse` WRITE;
/*!40000 ALTER TABLE `nurse` DISABLE KEYS */;
INSERT INTO `nurse` VALUES ('576494db-dde1-40d4-9422-761dc2aa0cc7',NULL,'7e555886-5944-4f41-b925-04a63bbc0370');
/*!40000 ALTER TABLE `nurse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('200bb0a6-d5c1-47f4-b48f-9de6ed3d9027','6ca100f1-d66b-450b-8430-464a008fcbf3',NULL,NULL,NULL,NULL,NULL),('3d5d3042-7584-4ac5-8351-4bd609092a44','1db0e19d-0ee1-4889-b22e-062f3f8661c2',NULL,NULL,NULL,NULL,NULL),('49bf1013-5204-49f9-ba9f-959df9d909ac','302b1a05-ccaa-4c1a-90da-7c9a6a73b9da','cua','B','huy','0933211534','không'),('843f26be-b753-4968-a7da-2768c8b44a73','93062bd7-c9b6-426d-9357-2ab2c1829c37','Cá','Nhóm A','Lưu Thị Bắc','0927394328','Bị Covid');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES ('READ APPOINTMENT','read appointment');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES ('Amoxicillin 500mg','500mg','sau ăn 30 phút','3 lần/ngày','Kháng sinh sau nhổ răng, dùng trong 5 ngày',35000),('Chlorhexidine 0.12%','10ml','sáng và tối sau đánh răng','2 lần/ngày','Súc miệng 30 giây, không nuốt',15000),('Ibuprofen 400mg','400mg','sau ăn 30 phút','3 lần/ngày','Giảm đau và chống viêm, không dùng khi đau dạ dày',40000),('Paracetamol 500mg','500mg','sau ăn 30 phút','3 lần/ngày','Dùng khi sốt hoặc đau đầu nhẹ',20000),('Prednisolone 5mg','5mg','uống buổi sáng sau ăn','1 lần/ngày','Chỉ dùng tối đa 3 ngày',20000),('Vitamin C 500mg','1 viên','sau bữa sáng 10 phút','1 lần/ngày','Giúp mau lành vết thương, tăng sức đề kháng',25000);
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('ADMIN','role admin'),('DOCTOR','role doctor'),('DOCTORLV2','role doctor lv2'),('NURSE','role nurse'),('PATIENT','role patient');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tooth`
--

LOCK TABLES `tooth` WRITE;
/*!40000 ALTER TABLE `tooth` DISABLE KEYS */;
INSERT INTO `tooth` VALUES ('9d5053e8-1d13-4f71-a0fe-8dc075bbff88','cavity','25','49bf1013-5204-49f9-ba9f-959df9d909ac');
/*!40000 ALTER TABLE `tooth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `treatment_phases`
--

LOCK TABLES `treatment_phases` WRITE;
/*!40000 ALTER TABLE `treatment_phases` DISABLE KEYS */;
INSERT INTO `treatment_phases` VALUES ('7a1614ab-654e-4eb1-97e4-7511511389e4',0,'Bắt đầu hồi phục sau trồng răng','2025-10-22','Phases 1','2025-10-15','Inprogress','926c3e34-5ba9-42ab-ad24-07f3c1798b09',NULL,NULL),('bbae2cb0-d9a9-4f9a-a85d-20aeeac70511',0,'Bắt đầu điều trị viêm nướu, làm sạch răng chuyên nghiệp\r\n\r\nGhi chú: Bắt đầu điều trị viêm nướu, làm sạch răng chuyên nghiệp','2024-01-21','Phases 1','2024-01-14','Done','219e9eea-dda8-48be-aee3-270f3e60cf0d',NULL,NULL),('cdd4293f-ecf6-4aaa-82e4-90046b5b0c3d',1315000,'Test','2025-12-07','Phase 2','2025-12-06','Inprogress','219e9eea-dda8-48be-aee3-270f3e60cf0d',NULL,NULL);
/*!40000 ALTER TABLE `treatment_phases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `treatment_phases_list_dental_service_entity_order`
--

LOCK TABLES `treatment_phases_list_dental_service_entity_order` WRITE;
/*!40000 ALTER TABLE `treatment_phases_list_dental_service_entity_order` DISABLE KEYS */;
INSERT INTO `treatment_phases_list_dental_service_entity_order` VALUES ('bbae2cb0-d9a9-4f9a-a85d-20aeeac70511',1300000,'Nhổ răng khôn hàm trên mọc thẳng',1,'Răng',1300000),('cdd4293f-ecf6-4aaa-82e4-90046b5b0c3d',1300000,'Nhổ răng khôn hàm trên mọc thẳng',1,'Răng',1300000);
/*!40000 ALTER TABLE `treatment_phases_list_dental_service_entity_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `treatment_phases_list_prescription_order`
--

LOCK TABLES `treatment_phases_list_prescription_order` WRITE;
/*!40000 ALTER TABLE `treatment_phases_list_prescription_order` DISABLE KEYS */;
INSERT INTO `treatment_phases_list_prescription_order` VALUES ('bbae2cb0-d9a9-4f9a-a85d-20aeeac70511',40000,'400mg','sau ăn 30 phút','3 lần/ngày','Ibuprofen 400mg','Giảm đau và chống viêm, không dùng khi đau dạ dày',1,40000),('cdd4293f-ecf6-4aaa-82e4-90046b5b0c3d',15000,'10ml','sáng và tối sau đánh răng','2 lần/ngày','Chlorhexidine 0.12%','Súc miệng 30 giây, không nuốt',1,15000);
/*!40000 ALTER TABLE `treatment_phases_list_prescription_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `treatment_plans`
--

LOCK TABLES `treatment_plans` WRITE;
/*!40000 ALTER TABLE `treatment_plans` DISABLE KEYS */;
INSERT INTO `treatment_plans` VALUES ('219e9eea-dda8-48be-aee3-270f3e60cf0d','Kế hoạch điều trị viêm nướu cấp tính update','Inprogress','Điều trị viêm nướu',2655000,'8688ef2a-a4e6-4e63-b081-1b32f154ee5b','49bf1013-5204-49f9-ba9f-959df9d909ac','3 tuần','Cần tái khám sau 1 tuần để đánh giá tiến triển mới',NULL,NULL,NULL),('926c3e34-5ba9-42ab-ad24-07f3c1798b09','Kế hoạch hồi phục sau trồng răng','Inprogress','Hồi phục sau trồng răng',0,'36c333c3-9564-4a4b-8a1b-34af719528c1','49bf1013-5204-49f9-ba9f-959df9d909ac','2 tuần','Cần tái khám sau 1 tuần để đánh giá tiến triển','2025-11-05',NULL,NULL);
/*!40000 ALTER TABLE `treatment_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1db0e19d-0ee1-4889-b22e-062f3f8661c2',_binary '\0','$2a$10$sMdeqGAjg8ZgV3KRB1jZUetf43bw59H2oSz/ooplC5ceRZKXIBGo.','nurse1'),('302b1a05-ccaa-4c1a-90da-7c9a6a73b9da',_binary '\0','$2a$10$UTJJvKorEt0AxbqbR.gISujGlxux0lm1oPerhQY5xwxHwzpydL7xm','test1'),('6ca100f1-d66b-450b-8430-464a008fcbf3',_binary '\0','$2a$10$3wgGPPEkpNjDSsVwHKmnXeL6zIFlX0iW5mKb6Q5xeu2WVbk1PnttK','test3'),('7e555886-5944-4f41-b925-04a63bbc0370',_binary '\0','$2a$10$tL6/UemHx7pmvLhion2eCeZgv8cEgqn0dezfbC/exUmyA8L7n3GTa','nurse2'),('93062bd7-c9b6-426d-9357-2ab2c1829c37',_binary '\0','$2a$10$VpU7sZOjXPToxhMdhw.G1OsjmBypMf8XngwPZt1hYjaEDI.kMumI6','test4'),('969fa480-2e32-4929-bee8-b0f7e095f631',_binary '\0','$2a$10$k3nyxwhV6IHTAa5a5VI5Kejg./tFOnOHHvSduOJgqhu0KwZZAPfMC','doctor2'),('9c8d0593-2f97-42bd-8946-3e1e660bc921',_binary '\0','$2a$10$ItvLHKOzvrx8aKh2uHxoJubhDNUCPmiEi663VZnlQnidpZQmB0usC','admin'),('d07944e7-9aec-4f69-ba97-4aa3b45314bc',_binary '\0','$2a$10$ufHnPKvmdm4jlY4FJyoNJ.29DRnB8IqoZNUgErpccgj2ncdD/zpX.','doctor1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_detail`
--

LOCK TABLES `user_detail` WRITE;
/*!40000 ALTER TABLE `user_detail` DISABLE KEYS */;
INSERT INTO `user_detail` VALUES ('138fc429-15fa-43e0-a937-205ae4bb4990','Bắc Ninh',NULL,'2003-02-02',NULL,'admin','Nam','0929282933','9c8d0593-2f97-42bd-8946-3e1e660bc921'),('3837de7f-e122-40fa-9595-bbd2d22c3507','Bắc Ninh','2025-10-11','2000-10-17','truong935943@gmail.com','Lê Gia Quang','male',NULL,'302b1a05-ccaa-4c1a-90da-7c9a6a73b9da'),('968cb08f-ba57-4b18-a5c5-4274d5bfb300','Hà Nội','2025-10-11','1988-02-02','truong935943@gmail.com','Lê Quang Hùng','Nam','0927348284','d07944e7-9aec-4f69-ba97-4aa3b45314bc'),('a4e94f37-7b40-4a26-9708-e209fa6b7f34',NULL,'2025-10-19',NULL,'truong935943@gmail.com',NULL,NULL,NULL,'6ca100f1-d66b-450b-8430-464a008fcbf3'),('a78df5cf-a8eb-40af-88d0-0006cce423eb','Hà Nội','2025-11-01','2010-10-22','truong935943@gmail.com','truong','male','0925463224','1db0e19d-0ee1-4889-b22e-062f3f8661c2'),('b1bc439b-ba75-4d5f-a217-24c9529fcbb3',NULL,'2025-12-07',NULL,'truong935943@gmail.com',NULL,NULL,NULL,'7e555886-5944-4f41-b925-04a63bbc0370'),('c5b340f8-bf8c-4569-b790-c99d95a724a5','Hà Nội','2025-10-22','1983-03-08','truong935943@gmail.com','Hoàng Công Tráng','Nam','0982937832','969fa480-2e32-4929-bee8-b0f7e095f631'),('d699cc69-42e5-4fb9-9821-0e0a4749f73b',NULL,'2025-10-19',NULL,'truong935943@gmail.com','Long',NULL,NULL,'93062bd7-c9b6-426d-9357-2ab2c1829c37');
/*!40000 ALTER TABLE `user_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('9c8d0593-2f97-42bd-8946-3e1e660bc921','ADMIN'),('d07944e7-9aec-4f69-ba97-4aa3b45314bc','DOCTOR'),('969fa480-2e32-4929-bee8-b0f7e095f631','DOCTORLV2'),('1db0e19d-0ee1-4889-b22e-062f3f8661c2','NURSE'),('7e555886-5944-4f41-b925-04a63bbc0370','NURSE'),('302b1a05-ccaa-4c1a-90da-7c9a6a73b9da','PATIENT'),('6ca100f1-d66b-450b-8430-464a008fcbf3','PATIENT'),('93062bd7-c9b6-426d-9357-2ab2c1829c37','PATIENT');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `verification_code`
--

LOCK TABLES `verification_code` WRITE;
/*!40000 ALTER TABLE `verification_code` DISABLE KEYS */;
INSERT INTO `verification_code` VALUES ('3980b651-3cc6-4939-badc-76152309ad77','106542','truong935943@gmail.com','2025-12-07 18:24:34.829805');
/*!40000 ALTER TABLE `verification_code` ENABLE KEYS */;
UNLOCK TABLES;

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

-- Dump completed on 2025-12-07 20:25:56
