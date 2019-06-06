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
INSERT INTO `actor_message_boxes` VALUES (2568,2569),(2568,2570),(2568,2571),(2568,2572),(2568,2573),(2575,2576),(2575,2577),(2575,2578),(2575,2579),(2575,2580),(2582,2583),(2582,2584),(2582,2585),(2582,2586),(2582,2587),(2589,2590),(2589,2591),(2589,2592),(2589,2593),(2589,2594),(2595,2596),(2595,2597),(2595,2598),(2595,2599),(2595,2600),(2601,2602),(2601,2603),(2601,2604),(2601,2605),(2601,2606),(2622,2623),(2622,2624),(2622,2625),(2622,2626),(2622,2627),(2637,2638),(2637,2639),(2637,2640),(2637,2641),(2637,2642),(2653,2654),(2653,2655),(2653,2656),(2653,2657),(2653,2658),(2790,2689),(2790,2690),(2790,2691),(2790,2692),(2790,2693),(2794,2694),(2794,2695),(2794,2696),(2794,2697),(2794,2698),(2797,2699),(2797,2700),(2797,2701),(2797,2702),(2797,2703),(2800,2704),(2800,2705),(2800,2706),(2800,2707),(2800,2708),(2740,2709),(2740,2710),(2740,2711),(2740,2712),(2740,2713),(2747,2714),(2747,2715),(2747,2716),(2747,2717),(2747,2718),(2750,2719),(2750,2720),(2750,2721),(2750,2722),(2750,2723);
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
INSERT INTO `actor_social_profiles` VALUES (2568,2574),(2575,2581),(2582,2588),(2790,2733),(2790,2734),(2790,2735),(2790,2736),(2794,2737),(2794,2738),(2794,2739);
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
INSERT INTO `administrator` VALUES (2568,0,'Avd Cosa Nº Casa Blq Piso 22ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','admin@email.com','PV','Pepe','+034656234123','http://i.imgur.com/uGe5LfM.png','\0','Viyuela',2552);
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
INSERT INTO `auditor` VALUES (2575,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','manolita@gafotos.com','','Manolita','+034656234165','http://1.bp.blogspot.com/-reFIlIJEIsI/XGTUp4FXvlI/AAAAAAAADxA/tmYImyJKCnEipNZG2aVjizm7jsU1izy1QCK4BGAYYCw/s1600/08.png','\0','Gafotos',2557),(2582,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','perez@ggmail.com','','Susan','+034656234165','http://1.bp.blogspot.com/-reFIlIJEIsI/XGTUp4FXvlI/AAAAAAAADxA/tmYImyJKCnEipNZG2aVjizm7jsU1izy1QCK4BGAYYCw/s1600/08.png','\0','Prez',2558),(2589,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','paco@grid.com','','Paco','+034656234165','http://1.bp.blogspot.com/-reFIlIJEIsI/XGTUp4FXvlI/AAAAAAAADxA/tmYImyJKCnEipNZG2aVjizm7jsU1izy1QCK4BGAYYCw/s1600/08.png','\0','Grid',2559),(2595,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','juanito@gafotos.com','','Juanito','+034656234165','http://1.bp.blogspot.com/-reFIlIJEIsI/XGTUp4FXvlI/AAAAAAAADxA/tmYImyJKCnEipNZG2aVjizm7jsU1izy1QCK4BGAYYCw/s1600/08.png','\0','Loren',2560);
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
INSERT INTO `auditor_issues` VALUES (2575,2675),(2582,2676),(2582,2684);
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
INSERT INTO `auditor_solicitations` VALUES (2575,2785),(2575,2786),(2575,2789),(2582,2788);
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
INSERT INTO `carrier` VALUES (2601,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','paco@jemez.com','Calvo','Paco','+034656234565','https://media1.tenor.com/images/f74f4ab7ec209c423eaa4dbcd74081af/tenor.gif?itemid=11007657','\0','Jémez',2561,2,'CZ12345678'),(2622,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','mike@jack.com','Ayuwoki','Michael','+034656534565','https://thumbs.gfycat.com/SphericalVictoriousBlackrhino-size_restricted.gif','\0','Jackson',2562,5,'CZ12345678'),(2637,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','jul@mal.com','Maldini','Julio','+034656534565','https://media.giphy.com/media/iJDMSw4A1MBxJ5H9YD/giphy.gif','\0','Maldonado',2563,10,'CZ12345678'),(2653,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','nino@mal.com','Mal','Niño','+034656534565','https://media.tenor.com/images/03d14473d64a6a3b486e53f9d2600b4a/tenor.gif','\0','Bebe',2564,10,'CZ12345678');
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
INSERT INTO `carrier_curricula` VALUES (2601,2607),(2622,2628),(2637,2643);
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
INSERT INTO `carrier_fares` VALUES (2601,2611),(2601,2612),(2601,2613),(2601,2614),(2601,2615),(2622,2631),(2622,2632),(2637,2645),(2637,2646),(2637,2647),(2637,2648),(2653,2659),(2653,2660);
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
INSERT INTO `carrier_offers` VALUES (2601,2616),(2601,2617),(2601,2618),(2601,2619),(2622,2633),(2622,2634),(2637,2649),(2637,2650);
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
INSERT INTO `carrier_vehicles` VALUES (2601,2620),(2601,2621),(2622,2635),(2622,2636),(2637,2651),(2637,2652),(2653,2661),(2653,2662);
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
INSERT INTO `category` VALUES (2663,0,'More than 25KG','Heavy','Mas de 25kg','Pesado'),(2664,0,'Package with fragile content','Fragile','Paquete con contenido fragil','Fragil'),(2665,0,'Urgent package','Urgent','Paquete urgente','Urgente'),(2666,0,'For highly flammable packages','Flammable','Para paquetes altamente inflamables','Inflamable'),(2667,0,'Packages with biohazard content','Biohazard','Paquetes con contenido de riesgo biológico','Riesgo biológico'),(2668,0,'Transport of live animals','Living animals','Transporte de animales vivos','Animales vivos'),(2669,0,'For transporting perishable goods','Perishable','Para transportar productos perezederos','Perecederos');
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
INSERT INTO `comment` VALUES (2677,0,'2019-05-25 10:00:00','Eso no es cierto','carrier1'),(2678,0,'2019-05-25 10:30:00','Si que lo es','customer1'),(2679,0,'2019-05-25 10:35:00','Es usted un mentiroso','carrier1'),(2680,0,'2019-05-25 10:50:00','Calmense amigos','auditor2'),(2681,0,'2019-05-25 10:52:00','Como me voy a calmar con semejante calumnia','carrier1'),(2682,0,'2019-05-25 10:55:00','Semejante babuino me ha ido a tocar','customer1'),(2683,0,'2019-05-25 10:58:00','No te voy a responder mas porque me has faltado al respeto','carrier1');
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
INSERT INTO `configuration` VALUES (2670,0,'https://i.pinimg.com/originals/f2/5f/31/f25f31af942174c11ca74cc55cfa4b1e.png','+34','Welcome to Acme Packages! We\'re the fastest and best value for money shipping service of the world!',5,10,1,'¡Bienvenidos a Acme Packages! ¡Somos el servicio de envio mas rapido y con mejor relación calidad-precio del mundo!','Acme Packages',21);
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
INSERT INTO `configuration_makes` VALUES (2670,'VISA'),(2670,'MASTER'),(2670,'AMEX'),(2670,'DINNERS');
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
INSERT INTO `configuration_mess_priorities` VALUES (2670,'HIGH'),(2670,'NEUTRAL'),(2670,'LOW');
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
INSERT INTO `configuration_spam_words` VALUES (2670,'sex'),(2670,'viagra'),(2670,'cialis'),(2670,'one million'),(2670,'you\'ve been selected'),(2670,'Nigeria'),(2670,'sexo'),(2670,'un millón'),(2670,'ha sido seleccionado');
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
INSERT INTO `curriculum` VALUES (2607,0,'vinicius@madrid.es','Vinicius Junior','654123987','http://e00-co-marca.uecdn.es/claro/assets/multimedia/imagenes/2019/03/05/15518185642905.jpg'),(2628,0,'novak@atp.com','Novak Djokovic','656128687','http://www.laprensa.com.uy/images/stories/articulos/2011/09/20/deportes__Djokovic.jpg'),(2643,0,'cholo@atm.com','Diego Simeone','600128687','https://www.elegimaldia.es/wp-content/uploads/2017/04/20111228dasdasftb_56-4.png');
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
INSERT INTO `curriculum_miscellaneous_records` VALUES (2607,2608),(2628,2629),(2643,2644);
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
INSERT INTO `curriculum_professional_records` VALUES (2607,2609),(2607,2610),(2628,2630);
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
INSERT INTO `customer` VALUES (2790,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','manolito@gafotas.com','','Manolito','+034656234165','http://1.bp.blogspot.com/-reFIlIJEIsI/XGTUp4FXvlI/AAAAAAAADxA/tmYImyJKCnEipNZG2aVjizm7jsU1izy1QCK4BGAYYCw/s1600/08.png','\0','Gafotas',2553,2671),(2794,0,'','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','manolito@gafotas.com','Paquirrin','Manolito','+034666666666','https://i.pinimg.com/originals/20/1a/5e/201a5ecf693e818d523f12d6a210716e.jpg','\0','Gafotas',2554,2672),(2797,0,'Avd Cosa Nº Casa Blq Piso 25ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','bran@stark.com','El tullido','Bran','+034656234165','https://media.metrolatam.com/2019/05/16/brangameofthrone-1a41173dca71168881d658461726a0a2-600x400.jpg','\0','Stark',2555,2673),(2800,0,'','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','arya@stark.com','MVP','Arya','+034666666666','https://vignette.wikia.nocookie.net/gameofthrones/images/5/54/AryaLastoftheStarks.PNG/revision/latest?cb=20190515193848','\0','Stark',2556,2674);
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
INSERT INTO `customer_requests` VALUES (2790,2792),(2790,2793),(2794,2795),(2794,2796),(2797,2798),(2797,2799),(2800,2801),(2800,2802),(2800,2803);
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
INSERT INTO `evaluation` VALUES (2791,0,'Bad',4,'2019-05-24 00:00:00',2790,2616);
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
INSERT INTO `fare` VALUES (2611,0,250000,3,5.95),(2612,0,500000,5,7.95),(2613,0,750000,10,8.95),(2614,0,1000000,10,9.95),(2615,0,3,15,8.95),(2631,0,200000,5,3.95),(2632,0,800000,10,6.95),(2645,0,2,10,6.95),(2646,0,2,20,9.95),(2647,0,5,10,10.95),(2648,0,5,20,14.95),(2659,0,1,5,5.95),(2660,0,3,15,8.95);
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
INSERT INTO `finder` VALUES (2671,0,'','2019-05-01 11:00:00',NULL,NULL,NULL,'',NULL,NULL),(2672,0,'','2019-05-01 10:00:00',NULL,NULL,NULL,'',NULL,NULL),(2673,0,'','2019-05-01 11:00:00',NULL,NULL,NULL,'',NULL,NULL),(2674,0,'','2019-05-01 10:00:00',NULL,NULL,NULL,'',NULL,NULL);
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
INSERT INTO `issue` VALUES (2675,0,'\0','Ha llegado roto al destinatario','2019-05-24 00:00:00','190523-ASD123-AA',2616),(2676,0,'','Ha llegado doblado al destinatario','2019-05-24 00:00:00','190523-ASD123-AB',2616),(2684,0,'','Ha llegado roto al destinatario','2019-05-24 00:00:00','1190523-ASD125-AA',2617),(2685,0,'\0','Ha llegado con caldo en la caja al destinatario','2019-05-24 00:00:00','190523-ASD125-AB',2633);
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
INSERT INTO `issue_comments` VALUES (2676,2677),(2676,2678),(2676,2679),(2676,2680),(2676,2681),(2676,2682),(2676,2683);
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
INSERT INTO `mess` VALUES (2686,0,'Message Ad1-->CUS1/CAR1','HIGH','2018-10-20 09:00:00','Message Ad1-->CUS1/CAR1',2568),(2687,0,'Message Ad1-->CUS1/CAR1','HIGH','2018-10-20 09:00:00','Message Ad1-->CUS1/CAR1',2568),(2688,0,'Message Ad1-->CUS1/CAR1','HIGH','2018-10-20 09:00:00','Message Ad1-->CUS1/CAR1',2568);
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
INSERT INTO `mess_box` VALUES (2569,0,'','In box',NULL),(2570,0,'','Out box',NULL),(2571,0,'','Trash box',NULL),(2572,0,'','Spam box',NULL),(2573,0,'','Notification box',NULL),(2576,0,'','In box',NULL),(2577,0,'','Out box',NULL),(2578,0,'','Spam box',NULL),(2579,0,'','Trash box',NULL),(2580,0,'','Notification box',NULL),(2583,0,'','In box',NULL),(2584,0,'','Out box',NULL),(2585,0,'','Spam box',NULL),(2586,0,'','Trash box',NULL),(2587,0,'','Notification box',NULL),(2590,0,'','In box',NULL),(2591,0,'','Out box',NULL),(2592,0,'','Spam box',NULL),(2593,0,'','Trash box',NULL),(2594,0,'','Notification box',NULL),(2596,0,'','In box',NULL),(2597,0,'','Out box',NULL),(2598,0,'','Spam box',NULL),(2599,0,'','Trash box',NULL),(2600,0,'','Notification box',NULL),(2602,0,'','In box',NULL),(2603,0,'','Out box',NULL),(2604,0,'','Spam box',NULL),(2605,0,'','Trash box',NULL),(2606,0,'','Notification box',NULL),(2623,0,'','In box',NULL),(2624,0,'','Out box',NULL),(2625,0,'','Spam box',NULL),(2626,0,'','Trash box',NULL),(2627,0,'','Notification box',NULL),(2638,0,'','In box',NULL),(2639,0,'','Out box',NULL),(2640,0,'','Spam box',NULL),(2641,0,'','Trash box',NULL),(2642,0,'','Notification box',NULL),(2654,0,'','In box',NULL),(2655,0,'','Out box',NULL),(2656,0,'','Spam box',NULL),(2657,0,'','Trash box',NULL),(2658,0,'','Notification box',NULL),(2689,0,'','In box',NULL),(2690,0,'','Out box',NULL),(2691,0,'','Spam box',NULL),(2692,0,'','Trash box',NULL),(2693,0,'','Notification box',NULL),(2694,0,'','In box',NULL),(2695,0,'','Out box',NULL),(2696,0,'','Spam box',NULL),(2697,0,'','Trash box',NULL),(2698,0,'','Notification box',NULL),(2699,0,'','In box',NULL),(2700,0,'','Out box',NULL),(2701,0,'','Spam box',NULL),(2702,0,'','Trash box',NULL),(2703,0,'','Notification box',NULL),(2704,0,'','In box',NULL),(2705,0,'','Out box',NULL),(2706,0,'','Spam box',NULL),(2707,0,'','Trash box',NULL),(2708,0,'','Notification box',NULL),(2709,0,'','In box',NULL),(2710,0,'','Out box',NULL),(2711,0,'','Spam box',NULL),(2712,0,'','Trash box',NULL),(2713,0,'','Notification box',NULL),(2714,0,'','In box',NULL),(2715,0,'','Out box',NULL),(2716,0,'','Spam box',NULL),(2717,0,'','Trash box',NULL),(2718,0,'','Notification box',NULL),(2719,0,'','In box',NULL),(2720,0,'','Out box',NULL),(2721,0,'','Spam box',NULL),(2722,0,'','Trash box',NULL),(2723,0,'','Notification box',NULL);
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
INSERT INTO `mess_box_messages` VALUES (2570,2686),(2602,2688),(2689,2687);
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
INSERT INTO `mess_recipients` VALUES (2686,2790),(2686,2601),(2687,2790),(2687,2601),(2688,2790),(2688,2601);
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
INSERT INTO `miscellaneous_record` VALUES (2608,0,'https://www.link.com','Se hablar 8 lenguas'),(2629,0,'https://www.link.com','Tengo un 12,5 en selectividad'),(2644,0,'https://www.link.com','Se tocar la flauta');
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
INSERT INTO `offer` VALUES (2616,0,'\0','','2019-10-07 00:00:00',5,'190523-ASD123',13.9,2620),(2617,0,'\0','','2019-10-07 00:00:00',5,'190523-ASD125',8.95,2620),(2618,0,'','','2020-05-05 00:00:00',0,'190523-ASD456',0,2620),(2619,0,'\0','\0','2020-05-05 00:00:00',0,'190523-ASD987',0,2620),(2633,0,'\0','','2019-05-12 00:00:00',0,'190523-ASD159',6.95,2635),(2634,0,'\0','','2020-05-05 00:00:00',0,'190523-ASD682',0,2635),(2649,0,'\0','','2019-05-05 00:00:00',0,'190523-LKJ123',0,2651),(2650,0,'\0','','2020-05-05 00:00:00',0,'190523-POI123',0,2651);
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
INSERT INTO `offer_fares` VALUES (2616,2611),(2616,2612),(2616,2613),(2616,2614),(2617,2611),(2617,2612),(2617,2613),(2617,2614),(2618,2611),(2618,2612),(2618,2613),(2618,2614),(2633,2631),(2633,2632),(2634,2631),(2634,2632),(2649,2645),(2649,2646),(2649,2647),(2649,2648),(2650,2645),(2650,2646),(2650,2647),(2650,2648);
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
INSERT INTO `offer_traverse_towns` VALUES (2616,2761),(2616,2762),(2616,2763),(2616,2764),(2616,2765),(2617,2766),(2617,2767),(2617,2768),(2618,2769),(2618,2770),(2618,2771),(2618,2772),(2618,2773),(2633,2774),(2633,2775),(2634,2776),(2634,2777),(2634,2778),(2649,2779),(2649,2780),(2649,2781),(2650,2782),(2650,2783),(2650,2784);
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
INSERT INTO `package` VALUES (2724,0,'Mi play 1',15,100,5.95,2,100),(2725,0,'Mi play 2',40,100,7.95,4,100),(2726,0,'Mi play 3',70,100,8.95,5,100),(2727,0,'Mi play 4',20,100,NULL,7,100),(2728,0,'Mi play 5',70,100,8.95,2,100),(2729,0,'Mi play 6',40,100,7.95,4,100),(2730,0,'Mi play 7',70,100,8.95,2,100),(2731,0,'Mi play 8',70,100,8.95,5,100),(2732,0,'Mi play 9',70,100,8.95,5,100);
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
INSERT INTO `package_categories` VALUES (2724,2664),(2725,2664),(2726,2664),(2727,2664),(2728,2664),(2729,2664),(2730,2664),(2731,2664),(2732,2665);
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
INSERT INTO `professional_record` VALUES (2609,0,'https://www.google.com','Google','2018-10-10 00:00:00','2017-10-10 00:00:00'),(2610,0,'https://www.zara.com','Zara','2018-10-10 00:00:00','2016-10-10 00:00:00'),(2630,0,'https://www.mrpizza.com.es/','Mr. Pizza','2017-10-10 00:00:00','2015-10-10 00:00:00');
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
INSERT INTO `request` VALUES (2792,0,'Cuidadito con el envio, rufianes','2019-12-10 00:00:00','Tened mucho cuidaito','',100,'2019-01-01 10:00:00','ACCEPTED','Calle pinta','010119-XNNMDD',150000,2,2676,2616,2751),(2793,0,'Cuidadito con el envio, rufianes','2019-12-12 00:00:00','Tened aun mas cuidadito','',50,'2019-02-02 10:00:00','SUBMITTED','Calle la niña de las flores','020219-AMPPL',400000,4,NULL,2617,2757),(2795,0,'Cuidadito con el envio, rufianes','2019-12-07 00:00:00','Tened aun mas cuidadito','',50,'2019-02-02 10:00:00','ACCEPTED','Calle la niña de las flores','020219-CMCPL',400000,4,2675,2616,2752),(2796,0,'Cuidadito con el envio, rufianes','2019-12-12 00:00:00','Tened aun mas cuidadito','',50,'2019-02-02 10:00:00','SUBMITTED','Calle la niña de las flores','020219-BBCPZ',700000,2,NULL,2617,2758),(2798,0,'Cuidadito con el envio, rufianes','2019-12-10 00:00:00','Tened aun mas cuidadito','',50,'2019-02-02 10:00:00','SUBMITTED','Calle la niña de las flores','020219-CMOKZ',700000,5,NULL,2616,2753),(2799,0,'Cuidadito con el envio, rufianes','2021-10-07 00:00:00','Tened aun mas cuidadito','',50,'2019-02-02 10:00:00','REJECTED','Calle la niña de las flores','020219-CBBAA',700000,5,NULL,2618,2759),(2801,0,'Cuidadito con el envio, rufianes','2019-10-07 00:00:00','Tened aun mas cuidadito','\0',50,'2019-03-03 10:00:00',NULL,'Calle la niña de las flores','030319-CMLOZ',200000,7,NULL,NULL,2754),(2802,0,'Cuidadito con el envio, rufianes','2019-12-12 00:00:00','Tened mucho cuidaito','',100,'2019-01-01 10:00:00','ACCEPTED','Calle pinta','010119-XNNMFF',700000,2,2684,2617,2756),(2803,0,'Cuidadito con el envio, rufianes','2020-02-02 00:00:00','Tened aun mas cuidadito','',50,'2019-02-02 10:00:00','ACCEPTED','Calle la niña de las flores','020219-KKKKKK',700000,5,2685,2633,2756);
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
INSERT INTO `request_packages` VALUES (2792,2724),(2793,2729),(2795,2725),(2796,2730),(2798,2726),(2799,2731),(2801,2727),(2802,2728),(2803,2732);
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
INSERT INTO `social_profile` VALUES (2574,0,'Paco','https://www.facebook.com','Facebook'),(2581,0,'Miguel','https://www.facebook.com','Facebook'),(2588,0,'Antonio','https://www.twitter.com','Twitter'),(2733,0,'Welinton','https://www.twitter.com','Twitter'),(2734,0,'Los del Betis','https://www.twitter.com','Twitter'),(2735,0,'Los de las penas','https://www.twitter.com','Twitter'),(2736,0,'Chapter1996','https://www.twitter.com','Twitter'),(2737,0,'Chap221','https://www.twitter.com','Twitter'),(2738,0,'sp21','https://www.twitter.com','Twitter'),(2739,0,'sp22','https://www.twitter.com','Twitter');
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
INSERT INTO `solicitation` VALUES (2785,0,'2021-05-11 00:00:00','2019-05-05 00:00:00','2019-05-05 00:00:00','ACCEPTED',2664),(2786,0,'2021-05-11 00:00:00','2019-05-05 00:00:00','2019-05-05 00:00:00','ACCEPTED',2663),(2787,0,NULL,'2019-05-05 00:00:00',NULL,'PENDING',2665),(2788,0,'2021-05-11 00:00:00','2019-05-05 00:00:00','2019-05-05 00:00:00','ACCEPTED',2663),(2789,0,'2021-05-11 00:00:00','2019-05-05 00:00:00','2019-05-05 00:00:00','ACCEPTED',2665);
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
INSERT INTO `solicitation_comments` VALUES (2785,'Lo necesito para darle de comer a mi gato'),(2786,'Lo necesito para darle de comer a mi gato'),(2787,'Tengo los permisos necesarios para transportar esa mercancía'),(2788,'Lo necesito para mi negocio'),(2789,'Lo necesito para darle de comer a mi gato');
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
INSERT INTO `sponsor` VALUES (2740,0,'Avd Cosa Nº Casa Blq Piso 44ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','amancio@slave.com','','Amancio','+034656289265','https://ep00.epimg.net/elpais/imagenes/2017/06/29/album/1498746433_850270_1498746596_album_normal.jpg','\0','Ortega',2565,'46018584T'),(2747,0,'Avd Cosa Nº Casa Blq Piso 45ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','mark@facebook.com','','Mark','+034656289275','https://www.elsiglodetorreon.com.mx/m/i/2018/04/1049342.jpeg','\0','Zuckerberg',2566,'46038584T'),(2750,0,'Avd Cosa Nº Casa Blq Piso 47ºA','\0',102,1,30,'José Mel Pérez','VISA','4495840606545807','marcelo@madrid.com','','Marcelo','+034656289275','https://www.diariogol.com/uploads/s1/59/69/14/4/marcelo.jpeg','\0','Da silva',2567,'46018584X');
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
INSERT INTO `sponsor_sponsorships` VALUES (2740,2741),(2740,2742),(2740,2743),(2740,2744),(2740,2745),(2740,2746),(2747,2748),(2747,2749);
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
INSERT INTO `sponsorship` VALUES (2741,0,'https://www.sivasdescalzo.com/skin/frontend/sivasdescalzo/opentrends/svd-siglas.svg',10,'2020-05-05 00:00:00','https://www.sivasdescalzo.com/es/lifestyle',10,''),(2742,0,'https://st.forocoches.com/image/top_c1_hd.png',0,NULL,'https://www.forocoches.com/',0,'\0'),(2743,0,'https://www.24-kts.com/themes/default-bootstrap/img/brand/brand.svg',0,'2020-05-05 00:00:00','https://www.24-kts.com/',0,''),(2744,0,'https://www.footlocker.es/INTERSHOP/static/WFS/Footlocker-Footlocker_ES-Site/-/-/es_ES/images/brand/logo-1440.png',0,'2020-05-05 00:00:00','https://www.footlocker.es/es/inicio',0,''),(2745,0,'https://www.comuniate.com//images/logo_nuevo.png',0,'2020-05-05 00:00:00','https://www.comuniate.com/',0,''),(2746,0,'https://cdn0.tnwcdn.com/wp-content/blogs.dir/1/files/2016/11/github-image-796x418.png',0,'2020-05-05 00:00:00','https://github.com/',0,''),(2748,0,'https://footdistrict.com/en/media/extendware/ewimageopt/media/skin/ae/9/logo-foot-district.png',0,'2019-10-10 00:00:00','https://footdistrict.com/en/',0,''),(2749,0,'https://clockify.me/assets/logo.svg',0,'2020-05-05 00:00:00','https://clockify.me/tracker',0,'');
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
INSERT INTO `town` VALUES (2751,0,'Huesca','Huesca','22001'),(2752,0,'Zaragoza','Zaragoza','45678'),(2753,0,'Madrid','Madrid','44001'),(2754,0,'Sevilla','Sevilla','45612'),(2755,0,'Cádiz','Cádiz','98745'),(2756,0,'Córdoba','Córdoba','62589'),(2757,0,'Huelva','Huelva','456954'),(2758,0,'Almería','Almería','04001'),(2759,0,'Málaga','Málaga','04081'),(2760,0,'Murcia','Murcia','04093');
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
INSERT INTO `traverse_town` VALUES (2761,0,NULL,'\0','2019-11-09 00:00:00',1,2751),(2762,0,NULL,'\0','2019-11-10 00:00:00',2,2752),(2763,0,NULL,'\0','2019-11-11 00:00:00',3,2753),(2764,0,NULL,'\0','2020-05-09 00:00:00',4,2754),(2765,0,NULL,'\0','2020-05-10 00:00:00',5,2755),(2766,0,NULL,'\0','2019-10-08 00:00:00',1,2753),(2767,0,NULL,'\0','2019-10-09 00:00:00',2,2757),(2768,0,NULL,'\0','2019-10-10 00:00:00',3,2758),(2769,0,NULL,'\0','2020-05-06 00:00:00',1,2759),(2770,0,NULL,'\0','2020-05-07 00:00:00',2,2752),(2771,0,NULL,'\0','2020-05-08 00:00:00',3,2753),(2772,0,NULL,'\0','2020-05-09 00:00:00',4,2754),(2773,0,NULL,'\0','2020-05-10 00:00:00',5,2755),(2774,0,NULL,'\0','2020-01-01 00:00:00',1,2756),(2775,0,NULL,'\0','2020-05-10 00:00:00',2,2757),(2776,0,NULL,'\0','2020-05-09 00:00:00',1,2756),(2777,0,NULL,'\0','2020-05-10 00:00:00',2,2757),(2778,0,NULL,'\0','2020-05-11 00:00:00',3,2758),(2779,0,NULL,'\0','2019-05-09 00:00:00',1,2756),(2780,0,NULL,'\0','2019-05-10 00:00:00',2,2757),(2781,0,NULL,'\0','2019-05-11 00:00:00',3,2758),(2782,0,NULL,'\0','2020-05-09 00:00:00',1,2756),(2783,0,NULL,'\0','2020-05-10 00:00:00',2,2757),(2784,0,NULL,'\0','2020-05-11 00:00:00',3,2758);
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
INSERT INTO `user_account` VALUES (2552,0,'\0','21232f297a57a5a743894a0e4a801fc3','admin'),(2553,0,'\0','ffbc4675f864e0e9aab8bdf7a0437010','customer1'),(2554,0,'\0','5ce4d191fd14ac85a1469fb8c29b7a7b','customer2'),(2555,0,'\0','033f7f6121501ae98285ad77f216d5e7','customer3'),(2556,0,'\0','55feb130be438e686ad6a80d12dd8f44','customer4'),(2557,0,'\0','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(2558,0,'\0','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(2559,0,'\0','fc2073dbe4f65dfd71e46602f8e6a5f3','auditor3'),(2560,0,'\0','6cc8affcba51a854192a33af68c08f49','auditor4'),(2561,0,'\0','8d2f089b195fc8ca1415db70968a461f','carrier1'),(2562,0,'\0','e9c83d20abca1fe451b73b6f74d9f411','carrier2'),(2563,0,'\0','8dee391cf2bfda3504283d0dc1af7a2e','carrier3'),(2564,0,'\0','cde726b4067344e0cf4228f72f213c19','carrier4'),(2565,0,'\0','42c63ad66d4dc07ed17753772bef96d6','sponsor1'),(2566,0,'\0','3dc67f80a03324e01b1640f45d107485','sponsor2'),(2567,0,'\0','857a54956061fdc1b88d7722cafe6519','sponsor3');
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
INSERT INTO `user_account_authorities` VALUES (2552,'ADMIN'),(2553,'CUSTOMER'),(2554,'CUSTOMER'),(2555,'CUSTOMER'),(2556,'CUSTOMER'),(2557,'AUDITOR'),(2558,'AUDITOR'),(2559,'AUDITOR'),(2560,'AUDITOR'),(2561,'CARRIER'),(2562,'CARRIER'),(2563,'CARRIER'),(2564,'CARRIER'),(2565,'SPONSOR'),(2566,'SPONSOR'),(2567,'SPONSOR');
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
INSERT INTO `vehicle` VALUES (2620,0,'Multipla',4000000,4000,'4561ASD','CAR'),(2621,0,'',3500000,2500,'4561AFG','CAR'),(2635,0,'',10000000,8000,'7894AFG','TRUCK'),(2636,0,'',100000,80,'9856AFG','MOTORCYCLE'),(2651,0,'',6000000,6000,'1235AFG','VAN'),(2652,0,'',4000000,5000,'6123AFG','VAN'),(2661,0,'',9000000,7200,'7415AFG','TRUCK'),(2662,0,'Who you gonna call?',55000,2000,'9635AFG','CAR');
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
INSERT INTO `vehicle_pictures` VALUES (2620,'https://images.honestjohn.co.uk/imagecache/file/width/640/media/12797287/Fiat%20Multipla%20(8).jpg'),(2620,'http://hablemosdecoches.com/wp-content/uploads/2017/11/Multipla-main.jpg'),(2621,'https://secretscotland.files.wordpress.com/2018/02/fiatmultipla.jpg'),(2635,'https://i.kym-cdn.com/photos/images/original/001/418/154/5a1.gif'),(2636,'https://ae01.alicdn.com/kf/HTB1gHNBIpXXXXXPXVXXq6xXFXXXY/Tourfella-Aluminum-motorcycle-panniers-system-for-R1200GS-F800GS-F650.jpg_640x640.jpg'),(2651,'https://awesomestuff365.com/wp-content/uploads/2018/04/Scooby-Doo-Mystery-Machine-Van-9.jpg'),(2652,'https://www.chevroletbuickgmcofmurfreesboro.com/assets/stock/colormatched/white/640/cc_2019chv33_03_640/cc_2019chv330001_03_640_gaz.jpg'),(2661,'https://media.wired.com/photos/5b9c3d5e7d9d332cf364ad66/master/pass/AV-Trucks-187479297.jpg'),(2662,'https://i.ytimg.com/vi/gAwqvynkMeY/maxresdefault.jpg');
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
INSERT INTO `vehicle_solicitations` VALUES (2620,2785),(2621,2786),(2621,2787),(2635,2789),(2651,2788);
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

-- Dump completed on 2019-06-05  9:50:45
