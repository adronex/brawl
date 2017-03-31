CREATE DATABASE  IF NOT EXISTS `brawl` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `brawl`;
-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: localhost    Database: brawl
-- ------------------------------------------------------
-- Server version	5.5.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('1','adronex303@gmail.com','1234'),('2','adronex_@mail.ru','1234');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_spell`
--

DROP TABLE IF EXISTS `account_spell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_spell` (
  `account_id` varchar(45) NOT NULL,
  `spell_id` varchar(45) NOT NULL,
  PRIMARY KEY (`account_id`,`spell_id`),
  KEY `FK_AS_SPELL_idx` (`spell_id`),
  CONSTRAINT `FK_AS_ACCOUNT` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AS_SPELL` FOREIGN KEY (`spell_id`) REFERENCES `spell` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_spell`
--

LOCK TABLES `account_spell` WRITE;
/*!40000 ALTER TABLE `account_spell` DISABLE KEYS */;
INSERT INTO `account_spell` VALUES ('1','1'),('2','1'),('1','2'),('1','3'),('2','3'),('1','4'),('1','5'),('2','5'),('2','6');
/*!40000 ALTER TABLE `account_spell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hero`
--

DROP TABLE IF EXISTS `hero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hero` (
  `id` varchar(45) NOT NULL,
  `owner_id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CHARACTER_ACCOUNT_idx` (`owner_id`),
  CONSTRAINT `FK_CHARACTER_ACCOUNT` FOREIGN KEY (`owner_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hero`
--

LOCK TABLES `hero` WRITE;
/*!40000 ALTER TABLE `hero` DISABLE KEYS */;
INSERT INTO `hero` VALUES ('1','1','Batman'),('2','1','NeBatman'),('3','2','Superman'),('4','2','Affleck');
/*!40000 ALTER TABLE `hero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hero_spell`
--

DROP TABLE IF EXISTS `hero_spell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hero_spell` (
  `hero_id` varchar(45) NOT NULL,
  `spell_id` varchar(45) NOT NULL,
  PRIMARY KEY (`hero_id`,`spell_id`),
  KEY `FK_CS_SPELL_idx` (`spell_id`),
  CONSTRAINT `FK_HS_HERO` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_HS_SPELL` FOREIGN KEY (`spell_id`) REFERENCES `spell` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hero_spell`
--

LOCK TABLES `hero_spell` WRITE;
/*!40000 ALTER TABLE `hero_spell` DISABLE KEYS */;
INSERT INTO `hero_spell` VALUES ('1','1'),('2','1'),('3','1'),('4','1'),('1','2'),('2','2'),('1','3'),('2','3'),('3','3'),('4','3'),('1','4'),('2','5'),('3','5'),('4','5'),('3','6'),('4','6');
/*!40000 ALTER TABLE `hero_spell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hero_squad`
--

DROP TABLE IF EXISTS `hero_squad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hero_squad` (
  `hero_id` varchar(45) NOT NULL,
  `squad_id` varchar(45) NOT NULL,
  PRIMARY KEY (`hero_id`,`squad_id`),
  KEY `FK_CS_SQUAD_idx` (`squad_id`),
  CONSTRAINT `FK_HSQ_HERO` FOREIGN KEY (`hero_id`) REFERENCES `hero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_HSQ_SQUAD` FOREIGN KEY (`squad_id`) REFERENCES `squad` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hero_squad`
--

LOCK TABLES `hero_squad` WRITE;
/*!40000 ALTER TABLE `hero_squad` DISABLE KEYS */;
INSERT INTO `hero_squad` VALUES ('1','1'),('2','1'),('3','2'),('4','2');
/*!40000 ALTER TABLE `hero_squad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'root','2017-03-25 19:03:46',0,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spell`
--

DROP TABLE IF EXISTS `spell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spell` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spell`
--

LOCK TABLES `spell` WRITE;
/*!40000 ALTER TABLE `spell` DISABLE KEYS */;
INSERT INTO `spell` VALUES ('1','Sucker Punch'),('2','Dead Shot'),('3','Be The Batman!'),('4','HEAL!!!'),('5','Immortality'),('6','Mortality'),('7','Guiness'),('8','Buff');
/*!40000 ALTER TABLE `spell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `squad`
--

DROP TABLE IF EXISTS `squad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `squad` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `owner_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `SQUAD_ACCOUNT_idx` (`owner_id`),
  CONSTRAINT `SQUAD_ACCOUNT` FOREIGN KEY (`owner_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `squad`
--

LOCK TABLES `squad` WRITE;
/*!40000 ALTER TABLE `squad` DISABLE KEYS */;
INSERT INTO `squad` VALUES ('1','Hui','1'),('2','Bui','2');
/*!40000 ALTER TABLE `squad` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-31 10:19:43
