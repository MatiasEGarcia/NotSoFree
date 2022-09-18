CREATE DATABASE  IF NOT EXISTS `notsofree` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `notsofree`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: notsofree
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `prodcate`
--

DROP TABLE IF EXISTS `prodcate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodcate` (
  `id_prodcate` int NOT NULL AUTO_INCREMENT,
  `product` int NOT NULL,
  `category` int NOT NULL,
  PRIMARY KEY (`id_prodcate`),
  KEY `product` (`product`),
  KEY `category` (`category`),
  CONSTRAINT `prodcate_ibfk_1` FOREIGN KEY (`product`) REFERENCES `products` (`id_product`),
  CONSTRAINT `prodcate_ibfk_2` FOREIGN KEY (`category`) REFERENCES `categories` (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodcate`
--

LOCK TABLES `prodcate` WRITE;
/*!40000 ALTER TABLE `prodcate` DISABLE KEYS */;
INSERT INTO `prodcate` VALUES (1,1,3),(2,2,2),(3,3,9),(4,4,7),(5,5,1),(6,6,5),(7,7,5),(8,8,6),(9,9,1),(10,10,5),(11,11,7),(12,12,6),(13,13,2),(14,14,8),(15,15,6),(16,16,4),(17,17,6),(18,18,9),(19,19,5),(20,20,6),(21,21,6),(22,22,7),(23,23,3),(24,24,2),(25,25,1),(26,26,6),(27,27,9),(28,28,1),(29,29,9),(30,30,1),(31,31,7),(32,32,6),(33,33,6),(34,34,2),(35,35,7),(36,36,8),(37,37,9),(38,38,2),(39,39,10),(40,40,9),(41,41,4),(42,42,2),(43,43,6),(44,44,3),(45,45,3),(46,46,2),(47,47,2),(48,48,5),(49,49,3),(50,50,4),(51,51,10),(52,52,8),(53,53,1),(54,54,2),(55,55,10),(56,56,1),(57,57,2),(58,58,8),(59,59,10),(60,60,5),(61,61,3),(62,62,7),(63,63,4),(64,64,6),(65,65,6),(66,66,4),(67,67,1),(68,68,1),(69,69,7),(70,70,2),(71,71,7),(72,72,8),(73,73,3),(74,74,2),(75,75,8),(76,76,2),(77,77,1),(78,78,4),(79,79,8),(80,80,2),(81,81,4),(82,82,7),(83,83,1),(84,84,4),(85,85,5),(86,86,4),(87,87,1),(88,88,8),(89,89,3),(90,90,3),(91,91,7),(92,92,4),(93,93,6),(94,94,2),(95,95,5),(96,96,7),(97,97,7),(98,98,5),(99,99,4),(100,100,7);
/*!40000 ALTER TABLE `prodcate` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-18 11:26:28
