-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: giordano
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `hardware`
--

DROP TABLE IF EXISTS `hardware`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hardware` (
  `matricola` varchar(10) NOT NULL,
  `tipohw` varchar(4) DEFAULT NULL,
  `marca` varchar(45) DEFAULT NULL,
  `modello` varchar(45) DEFAULT NULL,
  `immagine` longblob,
  `dataacquisto` datetime DEFAULT NULL,
  `prezzoacquisto` float DEFAULT NULL,
  `savedhashcode` int DEFAULT '0',
  PRIMARY KEY (`matricola`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hardware`
--

LOCK TABLES `hardware` WRITE;
/*!40000 ALTER TABLE `hardware` DISABLE KEYS */;
INSERT INTO `hardware` VALUES ('ff','PC','SAMSUNG','Retro1',NULL,'2022-05-02 00:00:00',1500,-1999232001),('FG-0001','PC','DELL','OPTIPLEX 200',NULL,'2022-02-13 00:00:00',2000,0),('FG-0002','PC','HP','aaa',NULL,'2022-01-31 00:00:00',100.01,0),('FG-0003','PC','LENOVO','LNV02000',NULL,'2022-04-20 00:00:00',700,0),('fgfgfg','PC','RORORO','',NULL,NULL,0,119949896),('ghgh','','','',NULL,NULL,0,0),('ghghgh','','','',NULL,NULL,0,0),('hh','','','',NULL,NULL,0,0),('hhh','','','',NULL,NULL,0,0),('hhhhhh','hhhh','','',NULL,NULL,0,0),('yfyfy','','','',NULL,NULL,0,0);
/*!40000 ALTER TABLE `hardware` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hwsw`
--

DROP TABLE IF EXISTS `hwsw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hwsw` (
  `matricola` varchar(10) NOT NULL,
  `codice` varchar(10) NOT NULL,
  PRIMARY KEY (`matricola`,`codice`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hwsw`
--

LOCK TABLES `hwsw` WRITE;
/*!40000 ALTER TABLE `hwsw` DISABLE KEYS */;
INSERT INTO `hwsw` VALUES ('ff','ACAD19'),('ff','OFFICE'),('ff','WIN11'),('FG-0001','aaa'),('FG-0001','ACAD19'),('FG-0001','OFFICE'),('FG-0001','WIN11'),('FG-0002','ACAD19'),('FG-0002','WIN11'),('FG-0003','OFFICE'),('fgfgfg','ACAD19'),('fgfgfg','OFFICE'),('fgfgfg','WIN11'),('ghgh','ACAD19'),('ghgh','OFFICE'),('ghgh','WIN11');
/*!40000 ALTER TABLE `hwsw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `software`
--

DROP TABLE IF EXISTS `software`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `software` (
  `codice` varchar(10) NOT NULL,
  `tiposw` varchar(45) DEFAULT NULL,
  `nomesw` varchar(45) DEFAULT NULL,
  `versione` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`codice`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `software`
--

LOCK TABLES `software` WRITE;
/*!40000 ALTER TABLE `software` DISABLE KEYS */;
INSERT INTO `software` VALUES ('aaa','aaaa','aaaaaa','aaaa'),('ACAD19','CAD','AUTOCAD 2019','2019'),('cod1','office','office11','2019'),('OFFICE','OFFICE','OFFICE 2019','2019'),('WIN11','S.OPERATIVO','WINDOWS 11 PRO','2022');
/*!40000 ALTER TABLE `software` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `codiceuser` int NOT NULL DEFAULT '0',
  `username` varchar(16) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `hash` int DEFAULT '0',
  PRIMARY KEY (`codiceuser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'22','222','22','2022-05-07 12:42:30',0),(1,'Giordano',NULL,NULL,'2022-05-05 20:48:10',0),(3,'333','333','333','2022-05-07 13:02:36',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-07 15:46:14
