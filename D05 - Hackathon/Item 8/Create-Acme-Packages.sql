-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Packages
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banned` bit(1) NOT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i7xei45auwq1f6vu25985riuh` (`user_account`),
  CONSTRAINT `FK_i7xei45auwq1f6vu25985riuh` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_message_boxes`
--

DROP TABLE IF EXISTS `actor_message_boxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_message_boxes` (
  `actor` int(11) NOT NULL,
  `message_boxes` int(11) NOT NULL,
  UNIQUE KEY `UK_gsokk7rk6i6vd87e6dvdxfapu` (`message_boxes`),
  CONSTRAINT `FK_gsokk7rk6i6vd87e6dvdxfapu` FOREIGN KEY (`message_boxes`) REFERENCES `mess_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_message_boxes`
--

LOCK TABLES `actor_message_boxes` WRITE;
/*!40000 ALTER TABLE `actor_message_boxes` DISABLE KEYS */;
INSERT INTO `actor_message_boxes` VALUES (27,28),(27,29),(27,30),(27,31),(27,32);
/*!40000 ALTER TABLE `actor_message_boxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_social_profiles`
--

DROP TABLE IF EXISTS `actor_social_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_social_profiles` (
  `actor` int(11) NOT NULL,
  `social_profiles` int(11) NOT NULL,
  UNIQUE KEY `UK_4suhrykpl9af1ubs85ycbyt6q` (`social_profiles`),
  CONSTRAINT `FK_4suhrykpl9af1ubs85ycbyt6q` FOREIGN KEY (`social_profiles`) REFERENCES `social_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_social_profiles`
--

LOCK TABLES `actor_social_profiles` WRITE;
/*!40000 ALTER TABLE `actor_social_profiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_social_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banned` bit(1) NOT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (27,0,'Avd Cosa Nº Casa Blq Piso 22ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','admin@email.com','PV','Pepe','+034656234123','http://i.imgur.com/uGe5LfM.png','\0','Viyuela',26);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banned` bit(1) NOT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor_issues`
--

DROP TABLE IF EXISTS `auditor_issues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor_issues` (
  `auditor` int(11) NOT NULL,
  `issues` int(11) NOT NULL,
  UNIQUE KEY `UK_557126wbu6thsn75u42286rtx` (`issues`),
  KEY `FK_dxfgcvedfb2xgt2e72udb8b9k` (`auditor`),
  CONSTRAINT `FK_dxfgcvedfb2xgt2e72udb8b9k` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`),
  CONSTRAINT `FK_557126wbu6thsn75u42286rtx` FOREIGN KEY (`issues`) REFERENCES `issue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor_issues`
--

LOCK TABLES `auditor_issues` WRITE;
/*!40000 ALTER TABLE `auditor_issues` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditor_issues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor_solicitations`
--

DROP TABLE IF EXISTS `auditor_solicitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor_solicitations` (
  `auditor` int(11) NOT NULL,
  `solicitations` int(11) NOT NULL,
  UNIQUE KEY `UK_6bcj1o1pvl7dm8mss8gycx8ot` (`solicitations`),
  KEY `FK_hcck7bcudj157bu954rmasxg4` (`auditor`),
  CONSTRAINT `FK_hcck7bcudj157bu954rmasxg4` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`),
  CONSTRAINT `FK_6bcj1o1pvl7dm8mss8gycx8ot` FOREIGN KEY (`solicitations`) REFERENCES `solicitation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor_solicitations`
--

LOCK TABLES `auditor_solicitations` WRITE;
/*!40000 ALTER TABLE `auditor_solicitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditor_solicitations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrier`
--

DROP TABLE IF EXISTS `carrier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrier` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banned` bit(1) NOT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `score` double NOT NULL,
  `vat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i0prpa4bfimdx4yjic83kmg49` (`user_account`),
  CONSTRAINT `FK_i0prpa4bfimdx4yjic83kmg49` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrier`
--

LOCK TABLES `carrier` WRITE;
/*!40000 ALTER TABLE `carrier` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrier_curricula`
--

DROP TABLE IF EXISTS `carrier_curricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrier_curricula` (
  `carrier` int(11) NOT NULL,
  `curricula` int(11) NOT NULL,
  UNIQUE KEY `UK_rgoxqeug8hn8k5r35dci22iwx` (`curricula`),
  KEY `FK_6cigs5f2pfo2ek47t17y0oxij` (`carrier`),
  CONSTRAINT `FK_6cigs5f2pfo2ek47t17y0oxij` FOREIGN KEY (`carrier`) REFERENCES `carrier` (`id`),
  CONSTRAINT `FK_rgoxqeug8hn8k5r35dci22iwx` FOREIGN KEY (`curricula`) REFERENCES `curriculum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrier_curricula`
--

LOCK TABLES `carrier_curricula` WRITE;
/*!40000 ALTER TABLE `carrier_curricula` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrier_curricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrier_fares`
--

DROP TABLE IF EXISTS `carrier_fares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrier_fares` (
  `carrier` int(11) NOT NULL,
  `fares` int(11) NOT NULL,
  UNIQUE KEY `UK_9336k3vuga6m4ujwkyf1n4ofq` (`fares`),
  KEY `FK_khoh2f52j2rxr6xxplilseyi5` (`carrier`),
  CONSTRAINT `FK_khoh2f52j2rxr6xxplilseyi5` FOREIGN KEY (`carrier`) REFERENCES `carrier` (`id`),
  CONSTRAINT `FK_9336k3vuga6m4ujwkyf1n4ofq` FOREIGN KEY (`fares`) REFERENCES `fare` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrier_fares`
--

LOCK TABLES `carrier_fares` WRITE;
/*!40000 ALTER TABLE `carrier_fares` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrier_fares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrier_offers`
--

DROP TABLE IF EXISTS `carrier_offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrier_offers` (
  `carrier` int(11) NOT NULL,
  `offers` int(11) NOT NULL,
  UNIQUE KEY `UK_e3qhw017ofqpbln7sfd7v4278` (`offers`),
  KEY `FK_jr9ev2fl7lspycp0vofkm6dml` (`carrier`),
  CONSTRAINT `FK_jr9ev2fl7lspycp0vofkm6dml` FOREIGN KEY (`carrier`) REFERENCES `carrier` (`id`),
  CONSTRAINT `FK_e3qhw017ofqpbln7sfd7v4278` FOREIGN KEY (`offers`) REFERENCES `offer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrier_offers`
--

LOCK TABLES `carrier_offers` WRITE;
/*!40000 ALTER TABLE `carrier_offers` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrier_offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrier_vehicles`
--

DROP TABLE IF EXISTS `carrier_vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrier_vehicles` (
  `carrier` int(11) NOT NULL,
  `vehicles` int(11) NOT NULL,
  UNIQUE KEY `UK_la1vdoiqpew55udbvymy0nxob` (`vehicles`),
  KEY `FK_pkbr83to2f0spfqdq4bf6y7us` (`carrier`),
  CONSTRAINT `FK_pkbr83to2f0spfqdq4bf6y7us` FOREIGN KEY (`carrier`) REFERENCES `carrier` (`id`),
  CONSTRAINT `FK_la1vdoiqpew55udbvymy0nxob` FOREIGN KEY (`vehicles`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrier_vehicles`
--

LOCK TABLES `carrier_vehicles` WRITE;
/*!40000 ALTER TABLE `carrier_vehicles` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrier_vehicles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `english_description` varchar(255) DEFAULT NULL,
  `english_name` varchar(255) DEFAULT NULL,
  `spanish_description` varchar(255) DEFAULT NULL,
  `spanish_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_7gg72o5ybc7rrtrpxw76jue3q` (`english_name`,`spanish_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (33,0,'More than 25KG','Heavy','Mas de 25kg','Pesado'),(34,0,'Package with fragile content','Fragile','Paquete con contenido fragil','Fragil'),(35,0,'Urgent package','Urgent','Paquete urgente','Urgente'),(36,0,'For highly flammable packages','Flammable','Para paquetes altamente inflamables','Inflamable'),(37,0,'Packages with biohazard content','Biohazard','Paquetes con contenido de riesgo biológico','Riesgo biológico'),(38,0,'Transport of live animals','Living animals','Transporte de animales vivos','Animales vivos'),(39,0,'For transporting perishable goods','Perishable','Para transportar productos perezederos','Perecederos');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `user_comment` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `english_message` varchar(255) DEFAULT NULL,
  `fare` double NOT NULL,
  `finder_results` int(11) NOT NULL,
  `finder_time` int(11) NOT NULL,
  `spanish_message` varchar(255) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `vat` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (40,0,'https://i.pinimg.com/originals/f2/5f/31/f25f31af942174c11ca74cc55cfa4b1e.png','+34','Welcome to Acme Packages! We\'re the fastest and best value for money shipping service of the world!',5,10,1,'¡Bienvenidos a Acme Packages! ¡Somos el servicio de envio mas rapido y con mejor relación calidad-precio del mundo!','Acme Packages',21);
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_makes`
--

DROP TABLE IF EXISTS `configuration_makes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_makes` (
  `configuration` int(11) NOT NULL,
  `makes` varchar(255) DEFAULT NULL,
  KEY `FK_eanjwa8s4wiomnti3yklql7j` (`configuration`),
  CONSTRAINT `FK_eanjwa8s4wiomnti3yklql7j` FOREIGN KEY (`configuration`) REFERENCES `configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_makes`
--

LOCK TABLES `configuration_makes` WRITE;
/*!40000 ALTER TABLE `configuration_makes` DISABLE KEYS */;
INSERT INTO `configuration_makes` VALUES (40,'VISA'),(40,'MASTER'),(40,'AMEX'),(40,'DINNERS');
/*!40000 ALTER TABLE `configuration_makes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_mess_priorities`
--

DROP TABLE IF EXISTS `configuration_mess_priorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_mess_priorities` (
  `configuration` int(11) NOT NULL,
  `mess_priorities` varchar(255) DEFAULT NULL,
  KEY `FK_rd3p68v5qnfak798prr0rlxy6` (`configuration`),
  CONSTRAINT `FK_rd3p68v5qnfak798prr0rlxy6` FOREIGN KEY (`configuration`) REFERENCES `configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_mess_priorities`
--

LOCK TABLES `configuration_mess_priorities` WRITE;
/*!40000 ALTER TABLE `configuration_mess_priorities` DISABLE KEYS */;
INSERT INTO `configuration_mess_priorities` VALUES (40,'HIGH'),(40,'NEUTRAL'),(40,'LOW');
/*!40000 ALTER TABLE `configuration_mess_priorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration_spam_words`
--

DROP TABLE IF EXISTS `configuration_spam_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration_spam_words` (
  `configuration` int(11) NOT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  KEY `FK_qk3m1jkx4rq87ofjee19f3b33` (`configuration`),
  CONSTRAINT `FK_qk3m1jkx4rq87ofjee19f3b33` FOREIGN KEY (`configuration`) REFERENCES `configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration_spam_words`
--

LOCK TABLES `configuration_spam_words` WRITE;
/*!40000 ALTER TABLE `configuration_spam_words` DISABLE KEYS */;
INSERT INTO `configuration_spam_words` VALUES (40,'sex'),(40,'viagra'),(40,'cialis'),(40,'one million'),(40,'you\'ve been selected'),(40,'Nigeria'),(40,'sexo'),(40,'un millón'),(40,'ha sido seleccionado');
/*!40000 ALTER TABLE `configuration_spam_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneous_records`
--

DROP TABLE IF EXISTS `curriculum_miscellaneous_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneous_records` (
  `curriculum` int(11) NOT NULL,
  `miscellaneous_records` int(11) NOT NULL,
  UNIQUE KEY `UK_hbex6yqhywe93w3clw8y1od2q` (`miscellaneous_records`),
  KEY `FK_fxsf5ohw20jbm0wuny6j1nnc9` (`curriculum`),
  CONSTRAINT `FK_fxsf5ohw20jbm0wuny6j1nnc9` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_hbex6yqhywe93w3clw8y1od2q` FOREIGN KEY (`miscellaneous_records`) REFERENCES `miscellaneous_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneous_records`
--

LOCK TABLES `curriculum_miscellaneous_records` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneous_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_miscellaneous_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_professional_records`
--

DROP TABLE IF EXISTS `curriculum_professional_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_professional_records` (
  `curriculum` int(11) NOT NULL,
  `professional_records` int(11) NOT NULL,
  UNIQUE KEY `UK_7354sjhp7ih49qku2slvgb166` (`professional_records`),
  KEY `FK_amicw5c3dbgqi4vafcy7wi02i` (`curriculum`),
  CONSTRAINT `FK_amicw5c3dbgqi4vafcy7wi02i` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_7354sjhp7ih49qku2slvgb166` FOREIGN KEY (`professional_records`) REFERENCES `professional_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_professional_records`
--

LOCK TABLES `curriculum_professional_records` WRITE;
/*!40000 ALTER TABLE `curriculum_professional_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_professional_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banned` bit(1) NOT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dlj2jxtd2fmnpihb13abpd1x` (`finder`),
  KEY `FK_mbvdes9ypo1yu76so76owiyqx` (`user_account`),
  CONSTRAINT `FK_mbvdes9ypo1yu76so76owiyqx` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_6dlj2jxtd2fmnpihb13abpd1x` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_requests`
--

DROP TABLE IF EXISTS `customer_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_requests` (
  `customer` int(11) NOT NULL,
  `requests` int(11) NOT NULL,
  UNIQUE KEY `UK_tj7sruu6lp43jvrs49224yyln` (`requests`),
  KEY `FK_jbxlnsbxghjg8yx8a0x6neqk2` (`customer`),
  CONSTRAINT `FK_jbxlnsbxghjg8yx8a0x6neqk2` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_tj7sruu6lp43jvrs49224yyln` FOREIGN KEY (`requests`) REFERENCES `request` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_requests`
--

LOCK TABLES `customer_requests` WRITE;
/*!40000 ALTER TABLE `customer_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `mark` int(11) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `customer` int(11) NOT NULL,
  `offer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6a2fxplraa4nnjg22do6jergv` (`customer`),
  KEY `FK_bgu2lhcviki9gygj10jpvwymw` (`offer`),
  CONSTRAINT `FK_bgu2lhcviki9gygj10jpvwymw` FOREIGN KEY (`offer`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_6a2fxplraa4nnjg22do6jergv` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fare`
--

DROP TABLE IF EXISTS `fare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fare` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `max_volume` double NOT NULL,
  `max_weight` double NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_erjs9w8rfnmsjnusg1wdsk1bg` (`max_weight`,`max_volume`,`price`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fare`
--

LOCK TABLES `fare` WRITE;
/*!40000 ALTER TABLE `fare` DISABLE KEYS */;
/*!40000 ALTER TABLE `fare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `max_date` date DEFAULT NULL,
  `max_price` double DEFAULT NULL,
  `min_date` date DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `volume` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_offers`
--

DROP TABLE IF EXISTS `finder_offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_offers` (
  `finder` int(11) NOT NULL,
  `offers` int(11) NOT NULL,
  KEY `FK_is9auslaaf7ntaeaf16xloksu` (`offers`),
  KEY `FK_2ma73123ijd71113fynucsru9` (`finder`),
  CONSTRAINT `FK_2ma73123ijd71113fynucsru9` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_is9auslaaf7ntaeaf16xloksu` FOREIGN KEY (`offers`) REFERENCES `offer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_offers`
--

LOCK TABLES `finder_offers` WRITE;
/*!40000 ALTER TABLE `finder_offers` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issue` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `closed` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `offer` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5ivcw3j3h5xd0etnjr2nq3lae` (`offer`),
  CONSTRAINT `FK_5ivcw3j3h5xd0etnjr2nq3lae` FOREIGN KEY (`offer`) REFERENCES `offer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue_comments`
--

DROP TABLE IF EXISTS `issue_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `issue_comments` (
  `issue` int(11) NOT NULL,
  `comments` int(11) NOT NULL,
  UNIQUE KEY `UK_okbpbi44qxgmemol4e280ep9q` (`comments`),
  KEY `FK_rlw5lctuobv3o0297no3yu374` (`issue`),
  CONSTRAINT `FK_rlw5lctuobv3o0297no3yu374` FOREIGN KEY (`issue`) REFERENCES `issue` (`id`),
  CONSTRAINT `FK_okbpbi44qxgmemol4e280ep9q` FOREIGN KEY (`comments`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue_comments`
--

LOCK TABLES `issue_comments` WRITE;
/*!40000 ALTER TABLE `issue_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `issue_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mess`
--

DROP TABLE IF EXISTS `mess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mess` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `send_date` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mess`
--

LOCK TABLES `mess` WRITE;
/*!40000 ALTER TABLE `mess` DISABLE KEYS */;
/*!40000 ALTER TABLE `mess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mess_box`
--

DROP TABLE IF EXISTS `mess_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mess_box` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_system` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_sb6yxnfwtrfdem1o05j24hy2b` (`name`,`is_system`),
  KEY `FK_2lep2d9oxhkip2yxx6gtpxy4b` (`parent`),
  CONSTRAINT `FK_2lep2d9oxhkip2yxx6gtpxy4b` FOREIGN KEY (`parent`) REFERENCES `mess_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mess_box`
--

LOCK TABLES `mess_box` WRITE;
/*!40000 ALTER TABLE `mess_box` DISABLE KEYS */;
INSERT INTO `mess_box` VALUES (28,0,'','In box',NULL),(29,0,'','Out box',NULL),(30,0,'','Trash box',NULL),(31,0,'','Spam box',NULL),(32,0,'','Notification box',NULL);
/*!40000 ALTER TABLE `mess_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mess_box_messages`
--

DROP TABLE IF EXISTS `mess_box_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mess_box_messages` (
  `mess_box` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  KEY `FK_pjlsfvgg1fk2t57cg4ot3vb4n` (`messages`),
  KEY `FK_ams40f7wiuiwagnk16u1jecy7` (`mess_box`),
  CONSTRAINT `FK_ams40f7wiuiwagnk16u1jecy7` FOREIGN KEY (`mess_box`) REFERENCES `mess_box` (`id`),
  CONSTRAINT `FK_pjlsfvgg1fk2t57cg4ot3vb4n` FOREIGN KEY (`messages`) REFERENCES `mess` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mess_box_messages`
--

LOCK TABLES `mess_box_messages` WRITE;
/*!40000 ALTER TABLE `mess_box_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `mess_box_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mess_recipients`
--

DROP TABLE IF EXISTS `mess_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mess_recipients` (
  `mess` int(11) NOT NULL,
  `recipients` int(11) NOT NULL,
  KEY `FK_qmq5ue844mlo3no0vdy2knh50` (`mess`),
  CONSTRAINT `FK_qmq5ue844mlo3no0vdy2knh50` FOREIGN KEY (`mess`) REFERENCES `mess` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mess_recipients`
--

LOCK TABLES `mess_recipients` WRITE;
/*!40000 ALTER TABLE `mess_recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `mess_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mess_tags`
--

DROP TABLE IF EXISTS `mess_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mess_tags` (
  `mess` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FK_o1xp3p9k5a7672xokt7uo4xd9` (`mess`),
  CONSTRAINT `FK_o1xp3p9k5a7672xokt7uo4xd9` FOREIGN KEY (`mess`) REFERENCES `mess` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mess_tags`
--

LOCK TABLES `mess_tags` WRITE;
/*!40000 ALTER TABLE `mess_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `mess_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_record`
--

DROP TABLE IF EXISTS `miscellaneous_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_record`
--

LOCK TABLES `miscellaneous_record` WRITE;
/*!40000 ALTER TABLE `miscellaneous_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_record_comments`
--

DROP TABLE IF EXISTS `miscellaneous_record_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_record_comments` (
  `miscellaneous_record` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_ld8qxycuk2b97nvdk1dp8u99c` (`miscellaneous_record`),
  CONSTRAINT `FK_ld8qxycuk2b97nvdk1dp8u99c` FOREIGN KEY (`miscellaneous_record`) REFERENCES `miscellaneous_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_record_comments`
--

LOCK TABLES `miscellaneous_record_comments` WRITE;
/*!40000 ALTER TABLE `miscellaneous_record_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_record_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `canceled` bit(1) NOT NULL,
  `final_mode` bit(1) NOT NULL,
  `max_date_to_request` datetime DEFAULT NULL,
  `score` double NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  `vehicle` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_iex7e8fs0fh89yxpcnm1orjkm` (`ticker`),
  KEY `UK_lmop7kcp9xypntsaw4pmj1p7n` (`final_mode`,`max_date_to_request`,`canceled`),
  KEY `FK_8bqamvdakh5ao7j1q8fsnly4v` (`vehicle`),
  CONSTRAINT `FK_8bqamvdakh5ao7j1q8fsnly4v` FOREIGN KEY (`vehicle`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer_fares`
--

DROP TABLE IF EXISTS `offer_fares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer_fares` (
  `offer` int(11) NOT NULL,
  `fares` int(11) NOT NULL,
  KEY `FK_6qumitoqh3qvr8m2xw2h7bffd` (`fares`),
  KEY `FK_nqp23dmj1w4lw9dtkc5nj3nc` (`offer`),
  CONSTRAINT `FK_nqp23dmj1w4lw9dtkc5nj3nc` FOREIGN KEY (`offer`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_6qumitoqh3qvr8m2xw2h7bffd` FOREIGN KEY (`fares`) REFERENCES `fare` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer_fares`
--

LOCK TABLES `offer_fares` WRITE;
/*!40000 ALTER TABLE `offer_fares` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer_fares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer_traverse_towns`
--

DROP TABLE IF EXISTS `offer_traverse_towns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer_traverse_towns` (
  `offer` int(11) NOT NULL,
  `traverse_towns` int(11) NOT NULL,
  UNIQUE KEY `UK_j1n3xyrpnqs2fggnab22raj88` (`traverse_towns`),
  KEY `FK_jjt4g382f3m715e6tni05nw4v` (`offer`),
  CONSTRAINT `FK_jjt4g382f3m715e6tni05nw4v` FOREIGN KEY (`offer`) REFERENCES `offer` (`id`),
  CONSTRAINT `FK_j1n3xyrpnqs2fggnab22raj88` FOREIGN KEY (`traverse_towns`) REFERENCES `traverse_town` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer_traverse_towns`
--

LOCK TABLES `offer_traverse_towns` WRITE;
/*!40000 ALTER TABLE `offer_traverse_towns` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer_traverse_towns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `details` varchar(255) DEFAULT NULL,
  `height` double NOT NULL,
  `length` double NOT NULL,
  `price` double DEFAULT NULL,
  `weight` double NOT NULL,
  `width` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_categories`
--

DROP TABLE IF EXISTS `package_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_categories` (
  `package` int(11) NOT NULL,
  `categories` int(11) NOT NULL,
  KEY `FK_1mb90o0o12euhyabog7bnu8hq` (`categories`),
  KEY `FK_m431to4c1yw88quin6dn3ollt` (`package`),
  CONSTRAINT `FK_m431to4c1yw88quin6dn3ollt` FOREIGN KEY (`package`) REFERENCES `package` (`id`),
  CONSTRAINT `FK_1mb90o0o12euhyabog7bnu8hq` FOREIGN KEY (`categories`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_categories`
--

LOCK TABLES `package_categories` WRITE;
/*!40000 ALTER TABLE `package_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `package_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professional_record`
--

DROP TABLE IF EXISTS `professional_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professional_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professional_record`
--

LOCK TABLES `professional_record` WRITE;
/*!40000 ALTER TABLE `professional_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `professional_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professional_record_comments`
--

DROP TABLE IF EXISTS `professional_record_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professional_record_comments` (
  `professional_record` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_7r5b094ef74saryrrhv7xo8e5` (`professional_record`),
  CONSTRAINT `FK_7r5b094ef74saryrrhv7xo8e5` FOREIGN KEY (`professional_record`) REFERENCES `professional_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professional_record_comments`
--

LOCK TABLES `professional_record_comments` WRITE;
/*!40000 ALTER TABLE `professional_record_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `professional_record_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_mode` bit(1) NOT NULL,
  `max_price` double NOT NULL,
  `moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `volume` double NOT NULL,
  `weight` double NOT NULL,
  `issue` int(11) DEFAULT NULL,
  `offer` int(11) DEFAULT NULL,
  `town` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9mxq3powq8tqctclj0fbi2nih` (`ticker`),
  KEY `UK_jhma82ai3tr6a1m4uwg3d7kdq` (`deadline`,`final_mode`,`volume`,`weight`,`status`),
  KEY `FK_69py0q4haai8h5r2hi47ns2uy` (`issue`),
  KEY `FK_r0mnhgjvbymdbk6x9jwtnpsk0` (`offer`),
  KEY `FK_nenph7xb4hcm0fsbmyspgsjfk` (`town`),
  CONSTRAINT `FK_nenph7xb4hcm0fsbmyspgsjfk` FOREIGN KEY (`town`) REFERENCES `town` (`id`),
  CONSTRAINT `FK_69py0q4haai8h5r2hi47ns2uy` FOREIGN KEY (`issue`) REFERENCES `issue` (`id`),
  CONSTRAINT `FK_r0mnhgjvbymdbk6x9jwtnpsk0` FOREIGN KEY (`offer`) REFERENCES `offer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_packages`
--

DROP TABLE IF EXISTS `request_packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request_packages` (
  `request` int(11) NOT NULL,
  `packages` int(11) NOT NULL,
  UNIQUE KEY `UK_3jowviy3h9pv20uggi7hckskd` (`packages`),
  KEY `FK_b5eej8qbn2k6kj9v3i9tkmrjq` (`request`),
  CONSTRAINT `FK_b5eej8qbn2k6kj9v3i9tkmrjq` FOREIGN KEY (`request`) REFERENCES `request` (`id`),
  CONSTRAINT `FK_3jowviy3h9pv20uggi7hckskd` FOREIGN KEY (`packages`) REFERENCES `package` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_packages`
--

LOCK TABLES `request_packages` WRITE;
/*!40000 ALTER TABLE `request_packages` DISABLE KEYS */;
/*!40000 ALTER TABLE `request_packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `profile_link` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitation`
--

DROP TABLE IF EXISTS `solicitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4mv6e9ho6egd2kee20xm6i5ra` (`category`),
  CONSTRAINT `FK_4mv6e9ho6egd2kee20xm6i5ra` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitation`
--

LOCK TABLES `solicitation` WRITE;
/*!40000 ALTER TABLE `solicitation` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitation_comments`
--

DROP TABLE IF EXISTS `solicitation_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitation_comments` (
  `solicitation` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_clwl6ynrcvo0yo4e49annec6v` (`solicitation`),
  CONSTRAINT `FK_clwl6ynrcvo0yo4e49annec6v` FOREIGN KEY (`solicitation`) REFERENCES `solicitation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitation_comments`
--

LOCK TABLES `solicitation_comments` WRITE;
/*!40000 ALTER TABLE `solicitation_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitation_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banned` bit(1) NOT NULL,
  `cvv` int(11) DEFAULT NULL,
  `expiration_month` int(11) DEFAULT NULL,
  `expiration_year` int(11) DEFAULT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `spammer` bit(1) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `nif` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_du2w5ldt8rvlvxvtr7vyxk7g3` (`user_account`),
  CONSTRAINT `FK_du2w5ldt8rvlvxvtr7vyxk7g3` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor_sponsorships`
--

DROP TABLE IF EXISTS `sponsor_sponsorships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor_sponsorships` (
  `sponsor` int(11) NOT NULL,
  `sponsorships` int(11) NOT NULL,
  UNIQUE KEY `UK_mkn1illtvx1wgme9mwxxh03ki` (`sponsorships`),
  KEY `FK_4h7hvvjylf3tk4ibb6c09s8pj` (`sponsor`),
  CONSTRAINT `FK_4h7hvvjylf3tk4ibb6c09s8pj` FOREIGN KEY (`sponsor`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_mkn1illtvx1wgme9mwxxh03ki` FOREIGN KEY (`sponsorships`) REFERENCES `sponsorship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor_sponsorships`
--

LOCK TABLES `sponsor_sponsorships` WRITE;
/*!40000 ALTER TABLE `sponsor_sponsorships` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor_sponsorships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `count` int(11) NOT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `total_count` int(11) NOT NULL,
  `valid` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_slb5q580hiifm1yj9hg3jvsq2` (`valid`,`expiration_date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `town`
--

DROP TABLE IF EXISTS `town`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `town` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `county` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o42o239nesjwxl64dbtfpwd6b` (`zip_code`),
  KEY `UK_mog9r67bqs8gcf7lpc74vyvff` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `town`
--

LOCK TABLES `town` WRITE;
/*!40000 ALTER TABLE `town` DISABLE KEYS */;
INSERT INTO `town` VALUES (41,0,'Huesca','Huesca','22001'),(42,0,'Zaragoza','Zaragoza','45678'),(43,0,'Madrid','Madrid','44001'),(44,0,'Sevilla','Sevilla','45612'),(45,0,'Cádiz','Cádiz','98745'),(46,0,'Córdoba','Córdoba','62589'),(47,0,'Huelva','Huelva','456954'),(48,0,'Almería','Almería','04001'),(49,0,'Málaga','Málaga','04081'),(50,0,'Murcia','Murcia','04093');
/*!40000 ALTER TABLE `town` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traverse_town`
--

DROP TABLE IF EXISTS `traverse_town`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traverse_town` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `arrival_date` datetime DEFAULT NULL,
  `current_town` bit(1) NOT NULL,
  `estimated_date` datetime DEFAULT NULL,
  `number` int(11) NOT NULL,
  `town` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b3svs0d6pyjaqfu5yj6gyc3py` (`town`),
  CONSTRAINT `FK_b3svs0d6pyjaqfu5yj6gyc3py` FOREIGN KEY (`town`) REFERENCES `town` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traverse_town`
--

LOCK TABLES `traverse_town` WRITE;
/*!40000 ALTER TABLE `traverse_town` DISABLE KEYS */;
/*!40000 ALTER TABLE `traverse_town` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ban` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (26,0,'\0','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (26,'ADMIN');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `max_volume` double DEFAULT NULL,
  `max_weight` double DEFAULT NULL,
  `plate` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dptrc1lh70j3qrg5v38fmmw7g` (`plate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_pictures`
--

DROP TABLE IF EXISTS `vehicle_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle_pictures` (
  `vehicle` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_ce81ts9xem5nls2fv9hh1ngh` (`vehicle`),
  CONSTRAINT `FK_ce81ts9xem5nls2fv9hh1ngh` FOREIGN KEY (`vehicle`) REFERENCES `vehicle` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_pictures`
--

LOCK TABLES `vehicle_pictures` WRITE;
/*!40000 ALTER TABLE `vehicle_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicle_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle_solicitations`
--

DROP TABLE IF EXISTS `vehicle_solicitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle_solicitations` (
  `vehicle` int(11) NOT NULL,
  `solicitations` int(11) NOT NULL,
  UNIQUE KEY `UK_4kqlboalgycmfg8ubxucym6fk` (`solicitations`),
  KEY `FK_r1xqgywd61l2igks01l8maoo2` (`vehicle`),
  CONSTRAINT `FK_r1xqgywd61l2igks01l8maoo2` FOREIGN KEY (`vehicle`) REFERENCES `vehicle` (`id`),
  CONSTRAINT `FK_4kqlboalgycmfg8ubxucym6fk` FOREIGN KEY (`solicitations`) REFERENCES `solicitation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle_solicitations`
--

LOCK TABLES `vehicle_solicitations` WRITE;
/*!40000 ALTER TABLE `vehicle_solicitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicle_solicitations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-05  9:51:04
