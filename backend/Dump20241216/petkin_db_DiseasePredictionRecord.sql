-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: petkin_db
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `DiseasePredictionRecord`
--

DROP TABLE IF EXISTS `DiseasePredictionRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DiseasePredictionRecord` (
  `record_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint NOT NULL COMMENT '(Foreign Key from Pet)',
  `disease_id` int NOT NULL COMMENT '(Foreign Key from DiseaseType)',
  `analysis_id` bigint NOT NULL COMMENT '(Foreign Key from AIResult)',
  `image_url` text,
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DiseasePredictionRecord`
--

LOCK TABLES `DiseasePredictionRecord` WRITE;
/*!40000 ALTER TABLE `DiseasePredictionRecord` DISABLE KEYS */;
INSERT INTO `DiseasePredictionRecord` VALUES (1,5,1,9,'/static/uploads/5_20241210184515.jpg','2024-12-10 18:45:16'),(2,5,6,10,'/static/uploads/5_20241210185726.jpg','2024-12-11 03:57:26'),(3,5,2,11,'/static/uploads/5_20241210190044.jpg','2024-12-11 04:00:45'),(4,5,5,12,'/static/uploads/5_20241211023815.jpg','2024-12-11 11:38:16'),(5,5,5,13,'/static/uploads/5_20241211024028.jpg','2024-12-11 11:40:28'),(6,5,5,14,'/static/uploads/5_20241211024152.jpg','2024-12-11 11:41:52'),(7,5,5,15,'/static/uploads/5_20241211024353.jpg','2024-12-11 11:43:53'),(8,5,5,16,'/static/uploads/5_20241211024448.jpg','2024-12-11 11:44:49'),(9,5,5,17,'/static/uploads/5_20241211043738.jpg','2024-12-11 13:37:39'),(10,5,3,18,'/static/uploads/5_20241211064938.jpg','2024-12-11 15:49:38'),(11,5,3,19,'/static/uploads/5_20241211064947.jpg','2024-12-11 15:49:47'),(12,5,3,20,'/static/uploads/5_20241211065044.jpg','2024-12-11 15:50:44'),(13,6,3,21,'/static/uploads/6_20241211065812.jpg','2024-12-11 15:58:12'),(14,6,3,22,'/static/uploads/6_20241211152452.jpg','2024-12-12 00:24:52'),(15,6,3,23,'/static/uploads/6_20241212052309.webp','2024-12-12 14:23:10');
/*!40000 ALTER TABLE `DiseasePredictionRecord` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-16  9:30:39
