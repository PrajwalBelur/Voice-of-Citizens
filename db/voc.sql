/*
SQLyog Enterprise - MySQL GUI v8.12 
MySQL - 5.7.27-log : Database - voc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`voc` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `voc`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `a_rid` int(11) NOT NULL AUTO_INCREMENT,
  `a_email` varchar(75) COLLATE utf8_unicode_ci NOT NULL,
  `a_password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`a_rid`,`a_email`),
  UNIQUE KEY `a_rid` (`a_rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `admin` */

insert  into `admin`(`a_rid`,`a_email`,`a_password`) values (1,'admin@gmail.com','admin');

/*Table structure for table `citizen` */

DROP TABLE IF EXISTS `citizen`;

CREATE TABLE `citizen` (
  `c_rid` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c_contact` char(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c_email` varchar(75) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c_password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `c_gender` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'M = male, F = female',
  `c_address` text COLLATE utf8_unicode_ci,
  `c_grama` int(11) DEFAULT NULL,
  `c_profile` text COLLATE utf8_unicode_ci,
  `c_is_active` tinyint(1) DEFAULT '1' COMMENT '1 = enabled, 0 = disabled',
  PRIMARY KEY (`c_rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `complaint_images` */

DROP TABLE IF EXISTS `complaint_images`;

CREATE TABLE `complaint_images` (
  `ci_rid` int(11) NOT NULL AUTO_INCREMENT,
  `ci_comp_rid` int(11) DEFAULT NULL,
  `ci_img_url` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`ci_rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `comp_rid` int(11) NOT NULL AUTO_INCREMENT,
  `comp_from` int(11) DEFAULT NULL,
  `comp_contact` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `comp_panchayath` int(11) DEFAULT NULL,
  `comp_type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'WATER, SEWAGE, STREET_LIGHT, ROAD',
  `comp_land_mark` text COLLATE utf8_unicode_ci,
  `comp_description` text COLLATE utf8_unicode_ci,
  `comp_loc` text COLLATE utf8_unicode_ci,
  `comp_date_time` datetime DEFAULT NULL,
  `comp_status` int(11) DEFAULT '0' COMMENT '0 =  pending, -1 = reject, 1 = accepted, 2 = completed',
  `comp_scheduled_date` date DEFAULT NULL COMMENT 'solve the issue before specified date',
  `comp_solved_date` date DEFAULT NULL,
  `comp_photo_url` text COLLATE utf8_unicode_ci,
  `comp_reject_reason` text COLLATE utf8_unicode_ci,
  `comp_is_confirmed_by_citizen` int(11) DEFAULT '0' COMMENT '0 = not confirmed, 1 = confirmed',
  PRIMARY KEY (`comp_rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `completed_images` */

DROP TABLE IF EXISTS `completed_images`;

CREATE TABLE `completed_images` (
  `ci_rid` int(11) NOT NULL AUTO_INCREMENT,
  `ci_comp_rid` int(11) DEFAULT NULL,
  `ci_img_url` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`ci_rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `districts` */

DROP TABLE IF EXISTS `districts`;

CREATE TABLE `districts` (
  `d_rid` int(11) NOT NULL AUTO_INCREMENT,
  `d_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`d_rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `districts` */

insert  into `districts`(`d_rid`,`d_name`) values (1,'DAKSHINA KANNADA');

/*Table structure for table `grama` */

DROP TABLE IF EXISTS `grama`;

CREATE TABLE `grama` (
  `g_rid` int(11) NOT NULL AUTO_INCREMENT,
  `g_code` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `g_name` varchar(75) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `g_taluk_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`g_rid`)
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `grama` */

insert  into `grama`(`g_rid`,`g_code`,`g_name`,`g_taluk_id`) values (1,'1511001001','KANIYOORU',2),(2,'1511001002','KALMANJA',2),(3,'1511001003','KALIYA',2),(4,'1511001004','KUKKEDI',2),(5,'1511001005','KUVETTU',2),(6,'1511001006','KASHIPATNA',2),(7,'1511001007','KOYYURU',2),(8,'1511001008','KOKKADA',2),(9,'1511001009','ARAMBODI',2),(10,'1511001010','CHARMADI',2),(11,'1511001011','PATRAME',2),(12,'1511001012','PADANGADI',2),(13,'1511001013','PUDUVETTU',2),(14,'1511001014','DHARMASTHALA',2),(15,'1511001015','UJIRE',2),(16,'1511001016','INDABETTU',2),(17,'1511001017','ILANTHILA',2),(18,'1511001018','BANDARU',2),(19,'1511001019','BALANJA',2),(20,'1511001020','BARYA',2),(21,'1511001021','BELALU',2),(22,'1511001022','NADA',2),(23,'1511001023','NARAVI',2),(24,'1511001024','NERIYA',2),(25,'1511001025','THANNIRUPANTHA',2),(26,'1511001026','MALADI',2),(27,'1511001027','MARODI',2),(28,'1511001028','MADANTHYARU',2),(29,'1511001029','MALAVANTHIGE',2),(30,'1511001030','MUNDAJE',2),(31,'1511001031','MACHINA',2),(32,'1511001032','LAILA',2),(33,'1511001033','MITHABAGILU',2),(34,'1511001034','VENOORU',2),(35,'1511001035','MELANTHABETTU',2),(36,'1511001036','SHIBAJE',2),(37,'1511001037','SHIRLALU',2),(38,'1511001038','SHISHILA',2),(39,'1511001039','ANDINJE',2),(40,'1511001040','ARASINAMAKKI',2),(41,'1511001041','ALADANGADI',2),(42,'1511001042','HOSANGADI',2),(43,'1511001043','NIDLE',2),(44,'1511001044','KADIRUDYAVARA',2),(45,'1511001045','KALANJA',2),(46,'1511001046','NAVOORU',2),(47,'1511001047','SULKERI',2),(48,'1511001048','THEKKARU',2),(64,'1511002001','ALIKE',1),(65,'1511002002','AMTADY',1),(66,'1511002003','ANANTHADY',1),(67,'1511002004','BADAGABELLUR',1),(68,'1511002005','BADAGAKAJEKAR',1),(69,'1511002006','BALTHILA',1),(70,'1511002007','BALEPUNI',1),(71,'1511002008','GOLTHAMAJAL',1),(72,'1511002009','CHENNAITHODI',1),(73,'1511002010','IDKIDU',1),(74,'1511002011','IRA',1),(75,'1511002012','KADESHWALYA',1),(76,'1511002013','KAROPADI',1),(77,'1511002014','KANYANA',1),(78,'1511002015','KEPU',1),(79,'1511002016','KARIANGALA',1),(80,'1511002017','KURNAD',1),(81,'1511002018','KEDILA',1),(82,'1511002019','KUKKIPADI',1),(83,'1511002020','MANCHI',1),(84,'1511002021','MANI',1),(85,'1511002022','MERAMAJALU',1),(86,'1511002023','NARIKOMBU',1),(87,'1511002024','NARINGANA',1),(88,'1511001046','NAVOORU',1),(89,'1511002026','KAVALAMUDURU',1),(90,'1511002027','KAVALAPADURU',1),(91,'1511002028','PANJIKALLU',1),(92,'1511002029','PAJIRU',1),(93,'1511002030','PUDU',1),(94,'1511002031','PUNACHA',1),(95,'1511002032','KOLNADU',1),(96,'1511002033','PILATHABETTU',1),(97,'1511002034','PERNE',1),(98,'1511002035','RAYEE',1),(99,'1511002036','SAJIPANADU',1),(100,'1511002037','SAJIPAMOODA',1),(101,'1511002038','SAJIPAMUNNURU',1),(102,'1511002039','SARAPADI',1),(103,'1511002040','SANGABETTU',1),(104,'1511002041','PERUVAI',1),(105,'1511002042','ULI',1),(106,'1511002043','THUMBE',1),(107,'1511002044','VEERAKAMBA',1),(108,'1511002046','VITLAPADNUR',1),(109,'1511002047','VITLAMUDNUR',1),(110,'1511002048','AMMUNJE',1),(111,'1511002049','ARALA',1),(112,'1511002050','BARIMARU',1),(113,'1511002051','BOLANTHURU',1),(114,'1511002052','IRVATHUR',1),(115,'1511002053','KALLIGE',1),(116,'1511002054','MANILA',1),(117,'1511002055','MANINALKUR',1),(118,'1511002056','NETLAMUDNUR',1),(119,'1511002057','PERAJE',1),(120,'1511002058','SAJIPAPADU',1),(121,'1511002059','SALETHOORU',1),(127,'1511003001','KANDAVARA',3),(128,'1511003002','KALLAMUNDKUR',3),(129,'1511003003','KUPPEPADAV',3),(130,'1511003004','KEMRAL',3),(131,'1511003005','KONAJE',3),(132,'1511003007','KINYA',3),(133,'1511003008','KILPADI',3),(134,'1511003009','KINNIGOLI',3),(135,'1511003010','CHELAIRU',3),(136,'1511003011','PADUPANAMBUR',3),(137,'1511003012','PADUPERAR',3),(138,'1511003013','PADUMARNAD',3),(139,'1511003014','PUTTIGE',3),(140,'1511003015','PALADKA',3),(141,'1511003016','PAVOOR',3),(142,'1511003017','PERMUDE',3),(143,'1511003018','JOKATTE',3),(144,'1511003019','DAREGUDDE',3),(145,'1511003020','ULAIBETTU',3),(146,'1511003021','BAJPE',3),(147,'1511003022','BALKUNJE',3),(148,'1511003023','BALA',3),(149,'1511003024','BELMA',3),(150,'1511003025','BELUVAI',3),(151,'1511003026','BOLIYAR',3),(152,'1511003027','NELLIKARU',3),(153,'1511003028','TALAPADY',3),(154,'1511003029','TENKAMIJARU',3),(155,'1511003030','MANJANADY',3),(156,'1511003031','MALAVURU',3),(157,'1511003032','MUCHURU',3),(158,'1511003033','MOODUSHEDDE',3),(159,'1511003034','MUNNURU',3),(160,'1511003035','MENNABETTU',3),(161,'1511003036','SHIRTHADY',3),(162,'1511003037','GANJIMATA',3),(163,'1511003038','GURUPURA',3),(164,'1511003039','SURINJE',3),(165,'1511003040','SOMESHWARA',3),(166,'1511003041','ADYAR',3),(167,'1511003042','AMBLAMOGRU',3),(168,'1511003043','HAREKALA',3),(169,'1511003044','HALEYANGADY',3),(170,'1511003045','HOSABETTU',3),(171,'1511003046','AIKALA',3),(172,'1511003047','NEERMARGA',3),(173,'1511003048','YEKKARU',3),(174,'1511003049','YEDAPADAVU',3),(175,'1511003050','ATHIKARI BETTU',3),(176,'1511003051','BADAGAYEDAPADAVU',3),(177,'1511003052','IRUVAIL',3),(178,'1511003053','KATEELU',3),(179,'1511003054','MALLURU',3),(180,'1511003055','MUTTURU',3),(181,'1511003056','VALPADI',3),(190,'1511004001','BILINELE',4),(191,'1511004002','KADABA',4),(192,'1511004003','KABAKA',4),(193,'1511004004','KUTRUPADY',4),(194,'1511004005','KANIYURU',4),(195,'1511004006','KAUKRADY',4),(196,'1511004007','KEDAMBADI',4),(197,'1511004008','KODIMBADI',4),(198,'1511004009','KOMBARU',4),(199,'1511004010','KOILA',4),(200,'1511004011','KOLTHIGE',4),(201,'1511004012','ARYAPU',4),(202,'1511004013','ALANKARU',4),(203,'1511004014','PANAJE',4),(204,'1511004015','PERABE',4),(205,'1511004016','RAMAKUNJA',4),(206,'1511004017','UPPINANGADY',4),(207,'1511004018','BAJATHURU',4),(208,'1511004019','BADAGANNURU',4),(209,'1511004020','BANNURU',4),(210,'1511004021','BALNADU',4),(211,'1511004022','BETTAMPADY',4),(212,'1511004023','BELANDURU',4),(213,'1511004024','NARIMOGRU',4),(214,'1511004025','NOOJIBALTHILA',4),(215,'1511004026','NETTANIGE MUDNURU',4),(216,'1511004027','NELYADY',4),(217,'1511004028','34 NEKKILADY',4),(218,'1511004029','MARDHALA',4),(219,'1511004030','MUNDURU',4),(220,'1511004031','SHIRADY',4),(221,'1511004032','SAVANURU',4),(222,'1511004033','ARIYADKA',4),(223,'1511004034','HIREBANDADI',4),(224,'1511004035','OLAMOGRU',4),(225,'1511004036','AITHOOR',4),(226,'1511004037','GOLITHOTTU',4),(227,'1511004038','KEYYUR',4),(228,'1511004039','KUDIPPADI',4),(229,'1511004040','KADYA KONAJE',4),(230,'1511004041','NIDPALLI',4),(253,'1511005001','KANAKAMAJALU',5),(254,'1511005002','KALMADKA',5),(255,'1511001045','KALANJA',5),(256,'1511005004','KODIYALA',5),(257,'1511005005','KOLLAMOGRU',5),(258,'1511005006','ALETTI',5),(259,'1511005007','PANJA',5),(260,'1511005008','JALSOOR',5),(261,'1511005009','DEVACHALLA',5),(262,'1511005010','UBARADKA MITHURU',5),(263,'1511005011','BALPA',5),(264,'1511005012','BALILA',5),(265,'1511005013','BELLARE',5),(266,'1511005014','NELLURU KEMRAJE',5),(267,'1511005015','MARKANJA',5),(268,'1511005016','MADAPPADY',5),(269,'1511005017','MANDEKOLU',5),(270,'1511005018','GUTHIGARU',5),(271,'1511005019','SAMPAJE',5),(272,'1511005020','SUBRAHMANYA',5),(273,'1511005021','AJJAVARA',5),(274,'1511005022','ARANTHODU',5),(275,'1511005023','AMARA MUDNURU',5),(276,'1511005024','HARIHARA PALLATHADKA',5),(277,'1511005025','AIVARNADU',5),(278,'1511005026','YENMURU',5),(279,'1511005027','YEDAMANGALA',5),(280,'1511005028','PERUVAJE',5);

/*Table structure for table `panchayath` */

DROP TABLE IF EXISTS `panchayath`;

CREATE TABLE `panchayath` (
  `pan_rid` int(11) NOT NULL AUTO_INCREMENT,
  `pan_grama_rid` int(11) DEFAULT NULL,
  `pan_code` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pan_email` varchar(75) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pan_password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pan_president` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pan_conatct` char(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pan_tel_no` char(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pan_description` text COLLATE utf8_unicode_ci,
  `pan_status` tinyint(1) DEFAULT '1' COMMENT '1 = active, 0 = inactive/deleted',
  PRIMARY KEY (`pan_rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `panchayath_dataset` */

DROP TABLE IF EXISTS `panchayath_dataset`;

CREATE TABLE `panchayath_dataset` (
  `district_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `taluk` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pan_code` int(11) DEFAULT NULL,
  `pan_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `panchayath_dataset` */

insert  into `panchayath_dataset`(`district_name`,`taluk`,`pan_code`,`pan_name`) values ('DAKSHINA KANNADA','BELTANGADI',1511001001,'KANIYOORU'),('DAKSHINA KANNADA','BELTANGADI',1511001002,'KALMANJA'),('DAKSHINA KANNADA','BELTANGADI',1511001003,'KALIYA'),('DAKSHINA KANNADA','BELTANGADI',1511001004,'KUKKEDI'),('DAKSHINA KANNADA','BELTANGADI',1511001005,'KUVETTU'),('DAKSHINA KANNADA','BELTANGADI',1511001006,'KASHIPATNA'),('DAKSHINA KANNADA','BELTANGADI',1511001007,'KOYYURU'),('DAKSHINA KANNADA','BELTANGADI',1511001008,'KOKKADA'),('DAKSHINA KANNADA','BELTANGADI',1511001009,'ARAMBODI'),('DAKSHINA KANNADA','BELTANGADI',1511001010,'CHARMADI'),('DAKSHINA KANNADA','BELTANGADI',1511001011,'PATRAME'),('DAKSHINA KANNADA','BELTANGADI',1511001012,'PADANGADI'),('DAKSHINA KANNADA','BELTANGADI',1511001013,'PUDUVETTU'),('DAKSHINA KANNADA','BELTANGADI',1511001014,'DHARMASTHALA'),('DAKSHINA KANNADA','BELTANGADI',1511001015,'UJIRE'),('DAKSHINA KANNADA','BELTANGADI',1511001016,'INDABETTU'),('DAKSHINA KANNADA','BELTANGADI',1511001017,'ILANTHILA'),('DAKSHINA KANNADA','BELTANGADI',1511001018,'BANDARU'),('DAKSHINA KANNADA','BELTANGADI',1511001019,'BALANJA'),('DAKSHINA KANNADA','BELTANGADI',1511001020,'BARYA'),('DAKSHINA KANNADA','BELTANGADI',1511001021,'BELALU'),('DAKSHINA KANNADA','BELTANGADI',1511001022,'NADA'),('DAKSHINA KANNADA','BELTANGADI',1511001023,'NARAVI'),('DAKSHINA KANNADA','BELTANGADI',1511001024,'NERIYA'),('DAKSHINA KANNADA','BELTANGADI',1511001025,'THANNIRUPANTHA'),('DAKSHINA KANNADA','BELTANGADI',1511001026,'MALADI'),('DAKSHINA KANNADA','BELTANGADI',1511001027,'MARODI'),('DAKSHINA KANNADA','BELTANGADI',1511001028,'MADANTHYARU'),('DAKSHINA KANNADA','BELTANGADI',1511001029,'MALAVANTHIGE'),('DAKSHINA KANNADA','BELTANGADI',1511001030,'MUNDAJE'),('DAKSHINA KANNADA','BELTANGADI',1511001031,'MACHINA'),('DAKSHINA KANNADA','BELTANGADI',1511001032,'LAILA'),('DAKSHINA KANNADA','BELTANGADI',1511001033,'MITHABAGILU'),('DAKSHINA KANNADA','BELTANGADI',1511001034,'VENOORU'),('DAKSHINA KANNADA','BELTANGADI',1511001035,'MELANTHABETTU'),('DAKSHINA KANNADA','BELTANGADI',1511001036,'SHIBAJE'),('DAKSHINA KANNADA','BELTANGADI',1511001037,'SHIRLALU'),('DAKSHINA KANNADA','BELTANGADI',1511001038,'SHISHILA'),('DAKSHINA KANNADA','BELTANGADI',1511001039,'ANDINJE'),('DAKSHINA KANNADA','BELTANGADI',1511001040,'ARASINAMAKKI'),('DAKSHINA KANNADA','BELTANGADI',1511001041,'ALADANGADI'),('DAKSHINA KANNADA','BELTANGADI',1511001042,'HOSANGADI'),('DAKSHINA KANNADA','BELTANGADI',1511001043,'NIDLE'),('DAKSHINA KANNADA','BELTANGADI',1511001044,'KADIRUDYAVARA'),('DAKSHINA KANNADA','BELTANGADI',1511001045,'KALANJA'),('DAKSHINA KANNADA','BELTANGADI',1511001046,'NAVOORU'),('DAKSHINA KANNADA','BELTANGADI',1511001047,'SULKERI'),('DAKSHINA KANNADA','BELTANGADI',1511001048,'THEKKARU'),('DAKSHINA KANNADA','BANTVAL',1511002001,'ALIKE'),('DAKSHINA KANNADA','BANTVAL',1511002002,'AMTADY'),('DAKSHINA KANNADA','BANTVAL',1511002003,'ANANTHADY'),('DAKSHINA KANNADA','BANTVAL',1511002004,'BADAGABELLUR'),('DAKSHINA KANNADA','BANTVAL',1511002005,'BADAGAKAJEKAR'),('DAKSHINA KANNADA','BANTVAL',1511002006,'BALTHILA'),('DAKSHINA KANNADA','BANTVAL',1511002007,'BALEPUNI'),('DAKSHINA KANNADA','BANTVAL',1511002008,'GOLTHAMAJAL'),('DAKSHINA KANNADA','BANTVAL',1511002009,'CHENNAITHODI'),('DAKSHINA KANNADA','BANTVAL',1511002010,'IDKIDU'),('DAKSHINA KANNADA','BANTVAL',1511002011,'IRA'),('DAKSHINA KANNADA','BANTVAL',1511002012,'KADESHWALYA'),('DAKSHINA KANNADA','BANTVAL',1511002013,'KAROPADI'),('DAKSHINA KANNADA','BANTVAL',1511002014,'KANYANA'),('DAKSHINA KANNADA','BANTVAL',1511002015,'KEPU'),('DAKSHINA KANNADA','BANTVAL',1511002016,'KARIANGALA'),('DAKSHINA KANNADA','BANTVAL',1511002017,'KURNAD'),('DAKSHINA KANNADA','BANTVAL',1511002018,'KEDILA'),('DAKSHINA KANNADA','BANTVAL',1511002019,'KUKKIPADI'),('DAKSHINA KANNADA','BANTVAL',1511002020,'MANCHI'),('DAKSHINA KANNADA','BANTVAL',1511002021,'MANI'),('DAKSHINA KANNADA','BANTVAL',1511002022,'MERAMAJALU'),('DAKSHINA KANNADA','BANTVAL',1511002023,'NARIKOMBU'),('DAKSHINA KANNADA','BANTVAL',1511002024,'NARINGANA'),('DAKSHINA KANNADA','BANTVAL',1511002025,'NAVOORU'),('DAKSHINA KANNADA','BANTVAL',1511002026,'KAVALAMUDURU'),('DAKSHINA KANNADA','BANTVAL',1511002027,'KAVALAPADURU'),('DAKSHINA KANNADA','BANTVAL',1511002028,'PANJIKALLU'),('DAKSHINA KANNADA','BANTVAL',1511002029,'PAJIRU'),('DAKSHINA KANNADA','BANTVAL',1511002030,'PUDU'),('DAKSHINA KANNADA','BANTVAL',1511002031,'PUNACHA'),('DAKSHINA KANNADA','BANTVAL',1511002032,'KOLNADU'),('DAKSHINA KANNADA','BANTVAL',1511002033,'PILATHABETTU'),('DAKSHINA KANNADA','BANTVAL',1511002034,'PERNE'),('DAKSHINA KANNADA','BANTVAL',1511002035,'RAYEE'),('DAKSHINA KANNADA','BANTVAL',1511002036,'SAJIPANADU'),('DAKSHINA KANNADA','BANTVAL',1511002037,'SAJIPAMOODA'),('DAKSHINA KANNADA','BANTVAL',1511002038,'SAJIPAMUNNURU'),('DAKSHINA KANNADA','BANTVAL',1511002039,'SARAPADI'),('DAKSHINA KANNADA','BANTVAL',1511002040,'SANGABETTU'),('DAKSHINA KANNADA','BANTVAL',1511002041,'PERUVAI'),('DAKSHINA KANNADA','BANTVAL',1511002042,'ULI'),('DAKSHINA KANNADA','BANTVAL',1511002043,'THUMBE'),('DAKSHINA KANNADA','BANTVAL',1511002044,'VEERAKAMBA'),('DAKSHINA KANNADA','BANTVAL',1511002046,'VITLAPADNUR'),('DAKSHINA KANNADA','BANTVAL',1511002047,'VITLAMUDNUR'),('DAKSHINA KANNADA','BANTVAL',1511002048,'AMMUNJE'),('DAKSHINA KANNADA','BANTVAL',1511002049,'ARALA'),('DAKSHINA KANNADA','BANTVAL',1511002050,'BARIMARU'),('DAKSHINA KANNADA','BANTVAL',1511002051,'BOLANTHURU'),('DAKSHINA KANNADA','BANTVAL',1511002052,'IRVATHUR'),('DAKSHINA KANNADA','BANTVAL',1511002053,'KALLIGE'),('DAKSHINA KANNADA','BANTVAL',1511002054,'MANILA'),('DAKSHINA KANNADA','BANTVAL',1511002055,'MANINALKUR'),('DAKSHINA KANNADA','BANTVAL',1511002056,'NETLAMUDNUR'),('DAKSHINA KANNADA','BANTVAL',1511002057,'PERAJE'),('DAKSHINA KANNADA','BANTVAL',1511002058,'SAJIPAPADU'),('DAKSHINA KANNADA','BANTVAL',1511002059,'SALETHOORU'),('DAKSHINA KANNADA','MANGALURU',1511003001,'KANDAVARA'),('DAKSHINA KANNADA','MANGALURU',1511003002,'KALLAMUNDKUR'),('DAKSHINA KANNADA','MANGALURU',1511003003,'KUPPEPADAV'),('DAKSHINA KANNADA','MANGALURU',1511003004,'KEMRAL'),('DAKSHINA KANNADA','MANGALURU',1511003005,'KONAJE'),('DAKSHINA KANNADA','MANGALURU',1511003007,'KINYA'),('DAKSHINA KANNADA','MANGALURU',1511003008,'KILPADI'),('DAKSHINA KANNADA','MANGALURU',1511003009,'KINNIGOLI'),('DAKSHINA KANNADA','MANGALURU',1511003010,'CHELAIRU'),('DAKSHINA KANNADA','MANGALURU',1511003011,'PADUPANAMBUR'),('DAKSHINA KANNADA','MANGALURU',1511003012,'PADUPERAR'),('DAKSHINA KANNADA','MANGALURU',1511003013,'PADUMARNAD'),('DAKSHINA KANNADA','MANGALURU',1511003014,'PUTTIGE'),('DAKSHINA KANNADA','MANGALURU',1511003015,'PALADKA'),('DAKSHINA KANNADA','MANGALURU',1511003016,'PAVOOR'),('DAKSHINA KANNADA','MANGALURU',1511003017,'PERMUDE'),('DAKSHINA KANNADA','MANGALURU',1511003018,'JOKATTE'),('DAKSHINA KANNADA','MANGALURU',1511003019,'DAREGUDDE'),('DAKSHINA KANNADA','MANGALURU',1511003020,'ULAIBETTU'),('DAKSHINA KANNADA','MANGALURU',1511003021,'BAJPE'),('DAKSHINA KANNADA','MANGALURU',1511003022,'BALKUNJE'),('DAKSHINA KANNADA','MANGALURU',1511003023,'BALA'),('DAKSHINA KANNADA','MANGALURU',1511003024,'BELMA'),('DAKSHINA KANNADA','MANGALURU',1511003025,'BELUVAI'),('DAKSHINA KANNADA','MANGALURU',1511003026,'BOLIYAR'),('DAKSHINA KANNADA','MANGALURU',1511003027,'NELLIKARU'),('DAKSHINA KANNADA','MANGALURU',1511003028,'TALAPADY'),('DAKSHINA KANNADA','MANGALURU',1511003029,'TENKAMIJARU'),('DAKSHINA KANNADA','MANGALURU',1511003030,'MANJANADY'),('DAKSHINA KANNADA','MANGALURU',1511003031,'MALAVURU'),('DAKSHINA KANNADA','MANGALURU',1511003032,'MUCHURU'),('DAKSHINA KANNADA','MANGALURU',1511003033,'MOODUSHEDDE'),('DAKSHINA KANNADA','MANGALURU',1511003034,'MUNNURU'),('DAKSHINA KANNADA','MANGALURU',1511003035,'MENNABETTU'),('DAKSHINA KANNADA','MANGALURU',1511003036,'SHIRTHADY'),('DAKSHINA KANNADA','MANGALURU',1511003037,'GANJIMATA'),('DAKSHINA KANNADA','MANGALURU',1511003038,'GURUPURA'),('DAKSHINA KANNADA','MANGALURU',1511003039,'SURINJE'),('DAKSHINA KANNADA','MANGALURU',1511003040,'SOMESHWARA'),('DAKSHINA KANNADA','MANGALURU',1511003041,'ADYAR'),('DAKSHINA KANNADA','MANGALURU',1511003042,'AMBLAMOGRU'),('DAKSHINA KANNADA','MANGALURU',1511003043,'HAREKALA'),('DAKSHINA KANNADA','MANGALURU',1511003044,'HALEYANGADY'),('DAKSHINA KANNADA','MANGALURU',1511003045,'HOSABETTU'),('DAKSHINA KANNADA','MANGALURU',1511003046,'AIKALA'),('DAKSHINA KANNADA','MANGALURU',1511003047,'NEERMARGA'),('DAKSHINA KANNADA','MANGALURU',1511003048,'YEKKARU'),('DAKSHINA KANNADA','MANGALURU',1511003049,'YEDAPADAVU'),('DAKSHINA KANNADA','MANGALURU',1511003050,'ATHIKARI BETTU'),('DAKSHINA KANNADA','MANGALURU',1511003051,'BADAGAYEDAPADAVU'),('DAKSHINA KANNADA','MANGALURU',1511003052,'IRUVAIL'),('DAKSHINA KANNADA','MANGALURU',1511003053,'KATEELU'),('DAKSHINA KANNADA','MANGALURU',1511003054,'MALLURU'),('DAKSHINA KANNADA','MANGALURU',1511003055,'MUTTURU'),('DAKSHINA KANNADA','MANGALURU',1511003056,'VALPADI'),('DAKSHINA KANNADA','PUTTUR',1511004001,'BILINELE'),('DAKSHINA KANNADA','PUTTUR',1511004002,'KADABA'),('DAKSHINA KANNADA','PUTTUR',1511004003,'KABAKA'),('DAKSHINA KANNADA','PUTTUR',1511004004,'KUTRUPADY'),('DAKSHINA KANNADA','PUTTUR',1511004005,'KANIYURU'),('DAKSHINA KANNADA','PUTTUR',1511004006,'KAUKRADY'),('DAKSHINA KANNADA','PUTTUR',1511004007,'KEDAMBADI'),('DAKSHINA KANNADA','PUTTUR',1511004008,'KODIMBADI'),('DAKSHINA KANNADA','PUTTUR',1511004009,'KOMBARU'),('DAKSHINA KANNADA','PUTTUR',1511004010,'KOILA'),('DAKSHINA KANNADA','PUTTUR',1511004011,'KOLTHIGE'),('DAKSHINA KANNADA','PUTTUR',1511004012,'ARYAPU'),('DAKSHINA KANNADA','PUTTUR',1511004013,'ALANKARU'),('DAKSHINA KANNADA','PUTTUR',1511004014,'PANAJE'),('DAKSHINA KANNADA','PUTTUR',1511004015,'PERABE'),('DAKSHINA KANNADA','PUTTUR',1511004016,'RAMAKUNJA'),('DAKSHINA KANNADA','PUTTUR',1511004017,'UPPINANGADY'),('DAKSHINA KANNADA','PUTTUR',1511004018,'BAJATHURU'),('DAKSHINA KANNADA','PUTTUR',1511004019,'BADAGANNURU'),('DAKSHINA KANNADA','PUTTUR',1511004020,'BANNURU'),('DAKSHINA KANNADA','PUTTUR',1511004021,'BALNADU'),('DAKSHINA KANNADA','PUTTUR',1511004022,'BETTAMPADY'),('DAKSHINA KANNADA','PUTTUR',1511004023,'BELANDURU'),('DAKSHINA KANNADA','PUTTUR',1511004024,'NARIMOGRU'),('DAKSHINA KANNADA','PUTTUR',1511004025,'NOOJIBALTHILA'),('DAKSHINA KANNADA','PUTTUR',1511004026,'NETTANIGE MUDNURU'),('DAKSHINA KANNADA','PUTTUR',1511004027,'NELYADY'),('DAKSHINA KANNADA','PUTTUR',1511004028,'34 NEKKILADY'),('DAKSHINA KANNADA','PUTTUR',1511004029,'MARDHALA'),('DAKSHINA KANNADA','PUTTUR',1511004030,'MUNDURU'),('DAKSHINA KANNADA','PUTTUR',1511004031,'SHIRADY'),('DAKSHINA KANNADA','PUTTUR',1511004032,'SAVANURU'),('DAKSHINA KANNADA','PUTTUR',1511004033,'ARIYADKA'),('DAKSHINA KANNADA','PUTTUR',1511004034,'HIREBANDADI'),('DAKSHINA KANNADA','PUTTUR',1511004035,'OLAMOGRU'),('DAKSHINA KANNADA','PUTTUR',1511004036,'AITHOOR'),('DAKSHINA KANNADA','PUTTUR',1511004037,'GOLITHOTTU'),('DAKSHINA KANNADA','PUTTUR',1511004038,'KEYYUR'),('DAKSHINA KANNADA','PUTTUR',1511004039,'KUDIPPADI'),('DAKSHINA KANNADA','PUTTUR',1511004040,'KADYA KONAJE'),('DAKSHINA KANNADA','PUTTUR',1511004041,'NIDPALLI'),('DAKSHINA KANNADA','SULYA',1511005001,'KANAKAMAJALU'),('DAKSHINA KANNADA','SULYA',1511005002,'KALMADKA'),('DAKSHINA KANNADA','SULYA',1511005003,'KALANJA'),('DAKSHINA KANNADA','SULYA',1511005004,'KODIYALA'),('DAKSHINA KANNADA','SULYA',1511005005,'KOLLAMOGRU'),('DAKSHINA KANNADA','SULYA',1511005006,'ALETTI'),('DAKSHINA KANNADA','SULYA',1511005007,'PANJA'),('DAKSHINA KANNADA','SULYA',1511005008,'JALSOOR'),('DAKSHINA KANNADA','SULYA',1511005009,'DEVACHALLA'),('DAKSHINA KANNADA','SULYA',1511005010,'UBARADKA MITHURU'),('DAKSHINA KANNADA','SULYA',1511005011,'BALPA'),('DAKSHINA KANNADA','SULYA',1511005012,'BALILA'),('DAKSHINA KANNADA','SULYA',1511005013,'BELLARE'),('DAKSHINA KANNADA','SULYA',1511005014,'NELLURU KEMRAJE'),('DAKSHINA KANNADA','SULYA',1511005015,'MARKANJA'),('DAKSHINA KANNADA','SULYA',1511005016,'MADAPPADY'),('DAKSHINA KANNADA','SULYA',1511005017,'MANDEKOLU'),('DAKSHINA KANNADA','SULYA',1511005018,'GUTHIGARU'),('DAKSHINA KANNADA','SULYA',1511005019,'SAMPAJE'),('DAKSHINA KANNADA','SULYA',1511005020,'SUBRAHMANYA'),('DAKSHINA KANNADA','SULYA',1511005021,'AJJAVARA'),('DAKSHINA KANNADA','SULYA',1511005022,'ARANTHODU'),('DAKSHINA KANNADA','SULYA',1511005023,'AMARA MUDNURU'),('DAKSHINA KANNADA','SULYA',1511005024,'HARIHARA PALLATHADKA'),('DAKSHINA KANNADA','SULYA',1511005025,'AIVARNADU'),('DAKSHINA KANNADA','SULYA',1511005026,'YENMURU'),('DAKSHINA KANNADA','SULYA',1511005027,'YEDAMANGALA'),('DAKSHINA KANNADA','SULYA',1511005028,'PERUVAJE');

/*Table structure for table `taluk` */

DROP TABLE IF EXISTS `taluk`;

CREATE TABLE `taluk` (
  `t_rid` int(11) NOT NULL AUTO_INCREMENT,
  `t_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `t_district_id` int(11) NOT NULL,
  PRIMARY KEY (`t_rid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `taluk` */

insert  into `taluk`(`t_rid`,`t_name`,`t_district_id`) values (1,'BANTVAL',1),(2,'BELTANGADI',1),(3,'MANGALURU',1),(4,'PUTTUR',1),(5,'SULYA',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
