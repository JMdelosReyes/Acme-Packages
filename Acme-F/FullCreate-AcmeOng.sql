start transaction;
drop database if exists `AcmeOng`;
create database `AcmeOng`;

use `AcmeOng`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete on `AcmeOng`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `AcmeOng`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: AcmeOng
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
-- Table structure for table `actor_mensajes`
--

DROP TABLE IF EXISTS `actor_mensajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_mensajes` (
  `actor` int(11) NOT NULL,
  `mensajes` int(11) NOT NULL,
  UNIQUE KEY `UK_fn18kttlmnuaqintwhq29ox2g` (`mensajes`),
  CONSTRAINT `FK_fn18kttlmnuaqintwhq29ox2g` FOREIGN KEY (`mensajes`) REFERENCES `mensaje` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_mensajes`
--

LOCK TABLES `actor_mensajes` WRITE;
/*!40000 ALTER TABLE `actor_mensajes` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_mensajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_config`
--

DROP TABLE IF EXISTS `admin_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_config` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `default_country_code` varchar(255) DEFAULT NULL,
  `notification_message` varchar(255) DEFAULT NULL,
  `sent` bit(1) DEFAULT NULL,
  `welcome_message_english` varchar(255) DEFAULT NULL,
  `welcome_message_spanish` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_config`
--

LOCK TABLES `admin_config` WRITE;
/*!40000 ALTER TABLE `admin_config` DISABLE KEYS */;
INSERT INTO `admin_config` VALUES (71,0,'http://www.clasesdeperiodismo.com/wp-content/uploads/2015/10/twitter1.jpg','+34',NULL,NULL,'Welcome to Acme Ong!','¡Bienvenidos a Acme Ong!');
/*!40000 ALTER TABLE `admin_config` ENABLE KEYS */;
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
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `is_sent_broadcast` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (87,0,'Address AD','admin@mail.com','Admin','910290322','Admin',72,NULL);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `breache`
--

DROP TABLE IF EXISTS `breache`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `breache` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breache`
--

LOCK TABLES `breache` WRITE;
/*!40000 ALTER TABLE `breache` DISABLE KEYS */;
INSERT INTO `breache` VALUES (127,0,'descr 1','brec1'),(128,0,'descr 2','brec2'),(129,0,'descr 3','brec3'),(130,0,'descr 4','brec4');
/*!40000 ALTER TABLE `breache` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_bancaria`
--

DROP TABLE IF EXISTS `cuenta_bancaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuenta_bancaria` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `fondos` double NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `ultima_extraccion` date DEFAULT NULL,
  `ultimo_ingreso` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_bancaria`
--

LOCK TABLES `cuenta_bancaria` WRITE;
/*!40000 ALTER TABLE `cuenta_bancaria` DISABLE KEYS */;
INSERT INTO `cuenta_bancaria` VALUES (105,0,12300,'Mario Martin','11110000222299993333','2019-12-05','2019-10-05'),(106,0,13400,'Domingo Moron','11113333222266663333','2020-03-05','2019-04-05');
/*!40000 ALTER TABLE `cuenta_bancaria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donacion`
--

DROP TABLE IF EXISTS `donacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donacion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cantidad` double NOT NULL,
  `deap` bit(1) DEFAULT NULL,
  `administrator` int(11) DEFAULT NULL,
  `miembro` int(11) DEFAULT NULL,
  `oficinista` int(11) DEFAULT NULL,
  `ong` int(11) DEFAULT NULL,
  `presidente` int(11) DEFAULT NULL,
  `tesorero` int(11) DEFAULT NULL,
  `vice_presidente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n2cx3kuepu4htwo9jydlx5t4q` (`administrator`),
  KEY `FK_hw1nsi6xnkja6vvdabgwifx8g` (`miembro`),
  KEY `FK_ougc91ryocfj6f9w607h05e37` (`oficinista`),
  KEY `FK_oy4ys1y5pq872aox7u2gcutxs` (`ong`),
  KEY `FK_3kepk3p1n3ex1c2dk74p0a88y` (`presidente`),
  KEY `FK_98s86e38xej80pgv7849oigqy` (`tesorero`),
  KEY `FK_rre7wud18w70ecc4imfh0i5xr` (`vice_presidente`),
  CONSTRAINT `FK_rre7wud18w70ecc4imfh0i5xr` FOREIGN KEY (`vice_presidente`) REFERENCES `vice_presidente` (`id`),
  CONSTRAINT `FK_3kepk3p1n3ex1c2dk74p0a88y` FOREIGN KEY (`presidente`) REFERENCES `presidente` (`id`),
  CONSTRAINT `FK_98s86e38xej80pgv7849oigqy` FOREIGN KEY (`tesorero`) REFERENCES `tesorero` (`id`),
  CONSTRAINT `FK_hw1nsi6xnkja6vvdabgwifx8g` FOREIGN KEY (`miembro`) REFERENCES `miembro` (`id`),
  CONSTRAINT `FK_n2cx3kuepu4htwo9jydlx5t4q` FOREIGN KEY (`administrator`) REFERENCES `administrator` (`id`),
  CONSTRAINT `FK_ougc91ryocfj6f9w607h05e37` FOREIGN KEY (`oficinista`) REFERENCES `oficinista` (`id`),
  CONSTRAINT `FK_oy4ys1y5pq872aox7u2gcutxs` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donacion`
--

LOCK TABLES `donacion` WRITE;
/*!40000 ALTER TABLE `donacion` DISABLE KEYS */;
INSERT INTO `donacion` VALUES (131,0,300,'\0',NULL,98,NULL,89,NULL,NULL,NULL),(132,0,3000,'',NULL,NULL,NULL,89,NULL,NULL,NULL),(133,0,200,'\0',NULL,99,NULL,89,NULL,NULL,NULL),(134,0,3300,'\0',NULL,100,NULL,89,NULL,NULL,NULL),(135,0,500,'\0',NULL,101,NULL,91,NULL,NULL,NULL),(136,0,600,'\0',NULL,NULL,NULL,91,90,NULL,NULL),(137,0,1500,'\0',NULL,103,NULL,91,NULL,NULL,NULL),(138,0,50,'\0',NULL,NULL,NULL,89,NULL,NULL,NULL),(139,0,30000,'',NULL,NULL,NULL,91,NULL,NULL,NULL),(140,0,200,'\0',NULL,NULL,NULL,91,NULL,NULL,NULL);
/*!40000 ALTER TABLE `donacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expedicion`
--

DROP TABLE IF EXISTS `expedicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expedicion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `coste_estimado` double NOT NULL,
  `coste_final` double NOT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `n_vacunas` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expedicion`
--

LOCK TABLES `expedicion` WRITE;
/*!40000 ALTER TABLE `expedicion` DISABLE KEYS */;
INSERT INTO `expedicion` VALUES (107,0,17357,1324,'Sahara','2020-10-05',120),(108,0,20456,1334,'Somalia','2019-10-12',170),(109,0,33456,1322,'Mauritania','2018-10-12',110),(110,0,11456,1338,'Congo','2020-06-12',190),(111,0,22474,1334,'Zimbaue','2019-08-02',170),(112,0,14456,1334,'Mrrakech','2019-10-07',170),(113,0,30458,1334,'Egipto','2019-11-12',170),(114,0,70456,534,'Madagascar','2020-08-11',170),(115,0,29956,534,'Argelia','2020-10-05',170);
/*!40000 ALTER TABLE `expedicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expedicion_miembros`
--

DROP TABLE IF EXISTS `expedicion_miembros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expedicion_miembros` (
  `expedicion` int(11) NOT NULL,
  `miembros` int(11) NOT NULL,
  KEY `FK_ndpe38buu0hlv15m6ebx9dptt` (`miembros`),
  KEY `FK_b02hrtej27iha0sg9f37ihlpr` (`expedicion`),
  CONSTRAINT `FK_b02hrtej27iha0sg9f37ihlpr` FOREIGN KEY (`expedicion`) REFERENCES `expedicion` (`id`),
  CONSTRAINT `FK_ndpe38buu0hlv15m6ebx9dptt` FOREIGN KEY (`miembros`) REFERENCES `miembro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expedicion_miembros`
--

LOCK TABLES `expedicion_miembros` WRITE;
/*!40000 ALTER TABLE `expedicion_miembros` DISABLE KEYS */;
INSERT INTO `expedicion_miembros` VALUES (107,98),(107,99),(108,102),(108,101),(109,103),(109,101),(110,100),(110,99),(111,98),(111,100),(112,101),(112,102),(113,102),(113,103);
/*!40000 ALTER TABLE `expedicion_miembros` ENABLE KEYS */;
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
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `asunto` varchar(255) DEFAULT NULL,
  `cuerpo` varchar(255) DEFAULT NULL,
  `fecha_envio` date DEFAULT NULL,
  `actor_emisor` int(11) DEFAULT NULL,
  `actor_receptor` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miembro`
--

DROP TABLE IF EXISTS `miembro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miembro` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `ong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n4jpfn4p0crktye9gbfg9cdph` (`user_account`),
  KEY `FK_3o36fv0ivh115oi8om3lvh05b` (`ong`),
  CONSTRAINT `FK_n4jpfn4p0crktye9gbfg9cdph` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_3o36fv0ivh115oi8om3lvh05b` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembro`
--

LOCK TABLES `miembro` WRITE;
/*!40000 ALTER TABLE `miembro` DISABLE KEYS */;
INSERT INTO `miembro` VALUES (98,0,'Address calle 1','miembro@mail.com','Tania','910290321','Salguero Álvarez',78,89),(99,0,'Address calle 2','miembro2@mail.com','Gonzalo','910222321','Lopez Heredia',79,89),(100,0,'Address calle 3','miembro3@mail.com','Nazaret','910330321','Ramirez Moreno',80,89),(101,0,'Address calle 4','miembro4@mail.com','David','910444321','Perez Merello',81,91),(102,0,'Address calle 5','miembro5@mail.com','Daniel','910290222','Benitez Álvarez',82,91),(103,0,'Address calle 6','miembro6@mail.com','Marina','910690321','Salguero',83,91);
/*!40000 ALTER TABLE `miembro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miembro_peticiones`
--

DROP TABLE IF EXISTS `miembro_peticiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miembro_peticiones` (
  `miembro` int(11) NOT NULL,
  `peticiones` int(11) NOT NULL,
  UNIQUE KEY `UK_bp51at1khigangiusrxmwyoqc` (`peticiones`),
  KEY `FK_a2bb8vjufi7jap7sx3i9pu0gr` (`miembro`),
  CONSTRAINT `FK_a2bb8vjufi7jap7sx3i9pu0gr` FOREIGN KEY (`miembro`) REFERENCES `miembro` (`id`),
  CONSTRAINT `FK_bp51at1khigangiusrxmwyoqc` FOREIGN KEY (`peticiones`) REFERENCES `peticion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miembro_peticiones`
--

LOCK TABLES `miembro_peticiones` WRITE;
/*!40000 ALTER TABLE `miembro_peticiones` DISABLE KEYS */;
INSERT INTO `miembro_peticiones` VALUES (98,125),(99,126);
/*!40000 ALTER TABLE `miembro_peticiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oficinista`
--

DROP TABLE IF EXISTS `oficinista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oficinista` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `ong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ppshqqqj26rrwyviui8g5ymbs` (`user_account`),
  KEY `FK_l5v3yrrka8y6wv1mqsdsekse0` (`ong`),
  CONSTRAINT `FK_ppshqqqj26rrwyviui8g5ymbs` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_l5v3yrrka8y6wv1mqsdsekse0` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oficinista`
--

LOCK TABLES `oficinista` WRITE;
/*!40000 ALTER TABLE `oficinista` DISABLE KEYS */;
INSERT INTO `oficinista` VALUES (96,0,'Address C 1','cc1@mail.com','Oficinista 1','910290321','Surname oficinista 1',85,89),(97,0,'Address C 1','cc1@mail.com','Oficinista 2','910290321','Surname oficinista 2',86,91);
/*!40000 ALTER TABLE `oficinista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oficinista_peticiones`
--

DROP TABLE IF EXISTS `oficinista_peticiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oficinista_peticiones` (
  `oficinista` int(11) NOT NULL,
  `peticiones` int(11) NOT NULL,
  UNIQUE KEY `UK_t71gkdvx1s56qk264111h6pwi` (`peticiones`),
  KEY `FK_7u34edo638yw205he9pcj476j` (`oficinista`),
  CONSTRAINT `FK_7u34edo638yw205he9pcj476j` FOREIGN KEY (`oficinista`) REFERENCES `oficinista` (`id`),
  CONSTRAINT `FK_t71gkdvx1s56qk264111h6pwi` FOREIGN KEY (`peticiones`) REFERENCES `peticion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oficinista_peticiones`
--

LOCK TABLES `oficinista_peticiones` WRITE;
/*!40000 ALTER TABLE `oficinista_peticiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `oficinista_peticiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ong`
--

DROP TABLE IF EXISTS `ong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ong` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cif` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `cuenta_bancaria` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_17gwhskea7s0qkl0vo2dy3ulp` (`cuenta_bancaria`),
  CONSTRAINT `FK_17gwhskea7s0qkl0vo2dy3ulp` FOREIGN KEY (`cuenta_bancaria`) REFERENCES `cuenta_bancaria` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ong`
--

LOCK TABLES `ong` WRITE;
/*!40000 ALTER TABLE `ong` DISABLE KEYS */;
INSERT INTO `ong` VALUES (89,1,'A01000000','direccion1','2019-12-01','ong1','999000999',105),(91,1,'A02000000','direccion2','2018-12-01','ong2','999000399',106),(93,0,'A02003000','direccion2','2019-12-01','ong3','996700399',NULL);
/*!40000 ALTER TABLE `ong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ong_donaciones`
--

DROP TABLE IF EXISTS `ong_donaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ong_donaciones` (
  `ong` int(11) NOT NULL,
  `donaciones` int(11) NOT NULL,
  UNIQUE KEY `UK_qsove34glbgp00bitvdr7js0r` (`donaciones`),
  KEY `FK_c1qk6yevnt49cau86wedrvf9a` (`ong`),
  CONSTRAINT `FK_c1qk6yevnt49cau86wedrvf9a` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`),
  CONSTRAINT `FK_qsove34glbgp00bitvdr7js0r` FOREIGN KEY (`donaciones`) REFERENCES `donacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ong_donaciones`
--

LOCK TABLES `ong_donaciones` WRITE;
/*!40000 ALTER TABLE `ong_donaciones` DISABLE KEYS */;
INSERT INTO `ong_donaciones` VALUES (89,131),(89,132),(89,133),(89,134),(89,138),(91,135),(91,136),(91,137),(91,139),(91,140);
/*!40000 ALTER TABLE `ong_donaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ong_expediciones`
--

DROP TABLE IF EXISTS `ong_expediciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ong_expediciones` (
  `ong` int(11) NOT NULL,
  `expediciones` int(11) NOT NULL,
  UNIQUE KEY `UK_6yu1dlfull75p2wgvjvbon9s7` (`expediciones`),
  KEY `FK_lvs4wx89lw49r2yex1bj3fle4` (`ong`),
  CONSTRAINT `FK_lvs4wx89lw49r2yex1bj3fle4` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`),
  CONSTRAINT `FK_6yu1dlfull75p2wgvjvbon9s7` FOREIGN KEY (`expediciones`) REFERENCES `expedicion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ong_expediciones`
--

LOCK TABLES `ong_expediciones` WRITE;
/*!40000 ALTER TABLE `ong_expediciones` DISABLE KEYS */;
INSERT INTO `ong_expediciones` VALUES (89,107),(89,110),(89,111),(89,114),(91,108),(91,109),(91,112),(91,113),(91,115);
/*!40000 ALTER TABLE `ong_expediciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ong_reuniones`
--

DROP TABLE IF EXISTS `ong_reuniones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ong_reuniones` (
  `ong` int(11) NOT NULL,
  `reuniones` int(11) NOT NULL,
  UNIQUE KEY `UK_dpwc1qu595i5qgom299q24gci` (`reuniones`),
  KEY `FK_n8bxplrw1l4uw06cdybr32ikj` (`ong`),
  CONSTRAINT `FK_n8bxplrw1l4uw06cdybr32ikj` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`),
  CONSTRAINT `FK_dpwc1qu595i5qgom299q24gci` FOREIGN KEY (`reuniones`) REFERENCES `reunion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ong_reuniones`
--

LOCK TABLES `ong_reuniones` WRITE;
/*!40000 ALTER TABLE `ong_reuniones` DISABLE KEYS */;
INSERT INTO `ong_reuniones` VALUES (89,116),(89,117),(89,118),(89,119),(89,120),(89,121),(91,122),(91,123),(91,124);
/*!40000 ALTER TABLE `ong_reuniones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peticion`
--

DROP TABLE IF EXISTS `peticion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `peticion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `moment_request` date DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `miembro` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_31yekhf2neq6et299aa9cyimy` (`miembro`),
  CONSTRAINT `FK_31yekhf2neq6et299aa9cyimy` FOREIGN KEY (`miembro`) REFERENCES `miembro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peticion`
--

LOCK TABLES `peticion` WRITE;
/*!40000 ALTER TABLE `peticion` DISABLE KEYS */;
INSERT INTO `peticion` VALUES (125,0,'Sahara','2019-10-05','','PENDING',98),(126,0,'Sahara','2019-12-05','','PENDING',99);
/*!40000 ALTER TABLE `peticion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presidente`
--

DROP TABLE IF EXISTS `presidente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presidente` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `ong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_i16j2dfqaw3sged905p0u4elo` (`user_account`),
  KEY `FK_nhw25l5pkg56vbnt82dotn9u` (`ong`),
  CONSTRAINT `FK_i16j2dfqaw3sged905p0u4elo` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_nhw25l5pkg56vbnt82dotn9u` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presidente`
--

LOCK TABLES `presidente` WRITE;
/*!40000 ALTER TABLE `presidente` DISABLE KEYS */;
INSERT INTO `presidente` VALUES (88,0,'Address AD','presi1@mail.com','Presidente','910290322','Presidente',73,89),(90,0,'Address AD2','presi2@mail.com','Presidente2','910290322','Presidente2',74,91),(92,0,'Address AD3','presi3@mail.com','Presidente3','910290322','Presidente3',75,93),(94,0,'Address AD4','presi4@mail.com','Presidente4','910290322','Presidente4',76,NULL);
/*!40000 ALTER TABLE `presidente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presidente_expediciones`
--

DROP TABLE IF EXISTS `presidente_expediciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presidente_expediciones` (
  `presidente` int(11) NOT NULL,
  `expediciones` int(11) NOT NULL,
  UNIQUE KEY `UK_1at52idovcm4koaehmq4q4aaa` (`expediciones`),
  KEY `FK_thrjo0meav6qu46o35mxuyjr2` (`presidente`),
  CONSTRAINT `FK_thrjo0meav6qu46o35mxuyjr2` FOREIGN KEY (`presidente`) REFERENCES `presidente` (`id`),
  CONSTRAINT `FK_1at52idovcm4koaehmq4q4aaa` FOREIGN KEY (`expediciones`) REFERENCES `expedicion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presidente_expediciones`
--

LOCK TABLES `presidente_expediciones` WRITE;
/*!40000 ALTER TABLE `presidente_expediciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `presidente_expediciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presidente_reuniones`
--

DROP TABLE IF EXISTS `presidente_reuniones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presidente_reuniones` (
  `presidente` int(11) NOT NULL,
  `reuniones` int(11) NOT NULL,
  UNIQUE KEY `UK_shrkhhxxi4me4sda4oheb1uil` (`reuniones`),
  KEY `FK_1bw5576la2ldkma6t29mnnvrq` (`presidente`),
  CONSTRAINT `FK_1bw5576la2ldkma6t29mnnvrq` FOREIGN KEY (`presidente`) REFERENCES `presidente` (`id`),
  CONSTRAINT `FK_shrkhhxxi4me4sda4oheb1uil` FOREIGN KEY (`reuniones`) REFERENCES `reunion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presidente_reuniones`
--

LOCK TABLES `presidente_reuniones` WRITE;
/*!40000 ALTER TABLE `presidente_reuniones` DISABLE KEYS */;
/*!40000 ALTER TABLE `presidente_reuniones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reunion`
--

DROP TABLE IF EXISTS `reunion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reunion` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `asunto` varchar(255) DEFAULT NULL,
  `cuestiones` varchar(255) DEFAULT NULL,
  `fecha_comunicado` date DEFAULT NULL,
  `fecha_quedada` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reunion`
--

LOCK TABLES `reunion` WRITE;
/*!40000 ALTER TABLE `reunion` DISABLE KEYS */;
INSERT INTO `reunion` VALUES (116,0,'Subject1','Issues1','2019-10-08','2020-01-08'),(117,0,'Subject2','Issues2','2021-06-07','2019-03-08'),(118,0,'Subject3','Issues3','2019-09-11','2020-02-11'),(119,0,'Subject4','Issues4','2020-02-08','2020-08-08'),(120,0,'Subject5','Issues5','2019-10-10','2020-01-10'),(121,0,'Subject6','Issues6','2019-10-09','2020-01-09'),(122,0,'Subject7','Issues7','2020-05-08','2020-06-08'),(123,0,'Subject8','Issues8','2020-08-07','2021-06-07'),(124,0,'Subject9','Issues9','2020-03-08','2020-07-08');
/*!40000 ALTER TABLE `reunion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tesorero`
--

DROP TABLE IF EXISTS `tesorero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tesorero` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `ong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_et8rdmhcuy8hrquyfpdcxx1we` (`user_account`),
  KEY `FK_d4fk5k7jsjtc4krys6yw8vc5y` (`ong`),
  CONSTRAINT `FK_et8rdmhcuy8hrquyfpdcxx1we` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_d4fk5k7jsjtc4krys6yw8vc5y` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tesorero`
--

LOCK TABLES `tesorero` WRITE;
/*!40000 ALTER TABLE `tesorero` DISABLE KEYS */;
INSERT INTO `tesorero` VALUES (95,0,'Address C 1','cc1@mail.com','Tesorero 1','910290321','Surname tesorero 1',77,89);
/*!40000 ALTER TABLE `tesorero` ENABLE KEYS */;
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
  `active` bit(1) DEFAULT NULL,
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
INSERT INTO `user_account` VALUES (72,0,NULL,'21232f297a57a5a743894a0e4a801fc3','admin'),(73,0,NULL,'0b3b17a225cd94c2aff8d3e484dcd01f','presidente1'),(74,0,NULL,'1efb6b9f09b1b2eec3b5fb2bd0357b34','presidente2'),(75,0,NULL,'d8e93b5d4aad8e95880838c4f52bec1c','presidente3'),(76,0,NULL,'6a86a1893dca6b3909cc850e0de43fd1','presidente4'),(77,0,NULL,'b262761e74381ae94593996dea8bba14','tesorero1'),(78,0,NULL,'7d39c6c4712e0f165271db7e99076184','miembro1'),(79,0,NULL,'187731e39cb3a976442ed0e7c5f868c4','miembro2'),(80,0,NULL,'64d6b1fc59d76486ccdb3529ec46f2c9','miembro3'),(81,0,NULL,'09545b603756d739378df7dec568405b','miembro4'),(82,0,NULL,'174d24c1e63dc268401633c02467b3f8','miembro5'),(83,0,NULL,'bfb4d9e2434e896fec8efe161df34e58','miembro6'),(84,0,NULL,'bb68c856477e988d0b466f3dd7b65c86','vicePresidente1'),(85,0,NULL,'e19b4e625cd25aaf864b57420039fb94','oficinista1'),(86,0,NULL,'35bec4aea548b26e0aa2fe3db40ce4b8','oficinista2');
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
INSERT INTO `user_account_authorities` VALUES (72,'ADMIN'),(73,'PRESIDENTE'),(74,'PRESIDENTE'),(75,'PRESIDENTE'),(76,'PRESIDENTE'),(77,'TESORERO'),(78,'MIEMBRO'),(79,'MIEMBRO'),(80,'MIEMBRO'),(81,'MIEMBRO'),(82,'MIEMBRO'),(83,'MIEMBRO'),(84,'VICEPRESIDENTE'),(85,'OFICINISTA'),(86,'OFICINISTA');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vice_presidente`
--

DROP TABLE IF EXISTS `vice_presidente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vice_presidente` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `ong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3btjyng2n4q5y6cu73wi41rov` (`user_account`),
  KEY `FK_o73cb290fp3n6tdj8b6acta5f` (`ong`),
  CONSTRAINT `FK_3btjyng2n4q5y6cu73wi41rov` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_o73cb290fp3n6tdj8b6acta5f` FOREIGN KEY (`ong`) REFERENCES `ong` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vice_presidente`
--

LOCK TABLES `vice_presidente` WRITE;
/*!40000 ALTER TABLE `vice_presidente` DISABLE KEYS */;
INSERT INTO `vice_presidente` VALUES (104,0,'Address calle 1','vicePresidente1@mail.com','Maria','910234321','Salguero Álvarez',84,89);
/*!40000 ALTER TABLE `vice_presidente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vice_presidente_expediciones`
--

DROP TABLE IF EXISTS `vice_presidente_expediciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vice_presidente_expediciones` (
  `vice_presidente` int(11) NOT NULL,
  `expediciones` int(11) NOT NULL,
  UNIQUE KEY `UK_76uce7t2x6ulun41i4gghoyye` (`expediciones`),
  KEY `FK_3331wwwuvg8ka7c8vk7odh4jc` (`vice_presidente`),
  CONSTRAINT `FK_3331wwwuvg8ka7c8vk7odh4jc` FOREIGN KEY (`vice_presidente`) REFERENCES `vice_presidente` (`id`),
  CONSTRAINT `FK_76uce7t2x6ulun41i4gghoyye` FOREIGN KEY (`expediciones`) REFERENCES `expedicion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vice_presidente_expediciones`
--

LOCK TABLES `vice_presidente_expediciones` WRITE;
/*!40000 ALTER TABLE `vice_presidente_expediciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `vice_presidente_expediciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vice_presidente_reuniones`
--

DROP TABLE IF EXISTS `vice_presidente_reuniones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vice_presidente_reuniones` (
  `vice_presidente` int(11) NOT NULL,
  `reuniones` int(11) NOT NULL,
  UNIQUE KEY `UK_sox72pid41fe8w2ii3l8e81cd` (`reuniones`),
  KEY `FK_7dvdj21ko8dvwc5305we1sdg9` (`vice_presidente`),
  CONSTRAINT `FK_7dvdj21ko8dvwc5305we1sdg9` FOREIGN KEY (`vice_presidente`) REFERENCES `vice_presidente` (`id`),
  CONSTRAINT `FK_sox72pid41fe8w2ii3l8e81cd` FOREIGN KEY (`reuniones`) REFERENCES `reunion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vice_presidente_reuniones`
--

LOCK TABLES `vice_presidente_reuniones` WRITE;
/*!40000 ALTER TABLE `vice_presidente_reuniones` DISABLE KEYS */;
/*!40000 ALTER TABLE `vice_presidente_reuniones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-27 15:14:33
