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
  `hashcodehw` int DEFAULT NULL,
  PRIMARY KEY (`matricola`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hardware`
--

LOCK TABLES `hardware` WRITE;
/*!40000 ALTER TABLE `hardware` DISABLE KEYS */;
INSERT INTO `hardware` VALUES ('aa','','','',NULL,NULL,0,979974945),('cc','','','',NULL,NULL,0,981881569),('ff','','','',NULL,NULL,0,984741505),('FG-0001','PC','DELL','OPTIPLEX 200',NULL,'2022-02-13 00:00:00',2000,NULL),('FG-0002','PC','HP','aaa',NULL,'2022-01-31 00:00:00',100.01,NULL),('fgfgfg','','','',NULL,NULL,0,-586254626),('ghgh','','','',NULL,NULL,0,886754943),('ghghgh','','','',NULL,NULL,0,266187390),('hh','','','',NULL,NULL,0,986648129),('hhh','','','',NULL,NULL,0,-330887463),('hhhhhh','hhhh','','',NULL,NULL,0,-649638335),('yfyfy','','','',NULL,NULL,0,912182786);
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
INSERT INTO `hwsw` VALUES ('FG-0001','OFFICE'),('FG-0001','WIN11'),('FG-0002','ACAD19');
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
INSERT INTO `software` VALUES ('aaa','aaaa','aaaaaa','aaaa'),('ACAD19','CAD','AUTOCAD 2019','2019'),('cod1','office','office11','2019'),('kjgkjgkjg','','',''),('OFFICE','OFFICE','OFFICE 2019','2019'),('WIN11','S.OPERATIVO','WINDOWS 11 PRO','2022');
/*!40000 ALTER TABLE `software` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-22 16:42:29
