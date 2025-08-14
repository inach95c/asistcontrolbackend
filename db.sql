-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: sistema_examenes_spring_boot
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
-- Table structure for table `asistencia`
--

DROP TABLE IF EXISTS `asistencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asistencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `fecha` date NOT NULL,
  `hora_entrada` time NOT NULL,
  `hora_salida` time NOT NULL,
  `estado` enum('Presente','Ausente','Tardío') COLLATE utf8mb4_general_ci DEFAULT 'Presente',
  `horas_extras` int DEFAULT '0',
  `fecha_hora_entrada` time DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencia`
--

LOCK TABLES `asistencia` WRITE;
/*!40000 ALTER TABLE `asistencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asistencias`
--

DROP TABLE IF EXISTS `asistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asistencias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora` datetime DEFAULT NULL,
  `tipo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `origen` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `estado` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `asistencias_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=324 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencias`
--

LOCK TABLES `asistencias` WRITE;
/*!40000 ALTER TABLE `asistencias` DISABLE KEYS */;
INSERT INTO `asistencias` VALUES (251,'2025-07-19 06:00:00','ENTRADA',48,NULL,NULL),(252,'2025-07-19 09:18:16','ENTRADA',46,NULL,NULL),(253,'2025-07-19 09:28:48','ENTRADA',48,NULL,NULL),(264,'2025-07-19 12:29:33','ALMUERZO',52,NULL,NULL),(265,'2025-07-19 12:30:19','ENTRADA',52,NULL,NULL),(267,'2025-07-19 13:01:59','ENTRADA',51,NULL,NULL),(268,'2025-07-19 17:51:19','SALIDA',46,NULL,'NORMAL'),(269,'2025-07-19 17:54:00','SALIDA',48,NULL,'NORMAL'),(270,'2025-07-19 18:50:39','ALMUERZO',46,NULL,'NORMAL'),(271,'2025-07-19 18:51:53','SALIDA',46,NULL,'NORMAL'),(272,'2025-07-20 07:57:40','ENTRADA',48,NULL,'TARDÍA'),(273,'2025-07-20 08:24:17','SALIDA',48,NULL,'NORMAL'),(274,'2025-07-20 13:51:11','ENTRADA',46,NULL,'TARDÍA'),(275,'2025-07-20 13:52:49','ENTRADA',52,NULL,'TARDÍA'),(276,'2025-07-20 15:38:38','SALIDA',52,NULL,NULL),(277,'2025-07-20 15:38:39','SALIDA',52,NULL,NULL),(278,'2025-07-20 17:19:39','ENTRADA',50,NULL,'NORMAL'),(279,'2025-07-20 21:54:41','ENTRADA',47,NULL,'TARDÍA'),(280,'2025-07-20 21:54:41','ENTRADA',47,NULL,'TARDÍA'),(281,'2025-07-20 22:45:12','ENTRADA',52,NULL,'TARDÍA'),(282,'2025-07-20 22:46:13','SALIDA',52,NULL,'NORMAL'),(283,'2025-07-20 23:04:01','ENTRADA',53,NULL,'NORMAL'),(284,'2025-07-20 23:48:48','SALIDA',53,NULL,'NORMAL'),(285,'2025-07-21 20:54:18','ENTRADA',46,NULL,'TARDÍA'),(286,'2025-07-21 20:56:18','SALIDA',46,NULL,'NORMAL'),(287,'2025-07-21 21:02:03','ENTRADA',48,NULL,'TARDÍA'),(288,'2025-07-21 22:02:38','SALIDA',48,NULL,'NORMAL'),(289,'2025-07-21 22:40:47','ENTRADA',47,NULL,'NORMAL'),(290,'2025-07-21 22:41:19','SALIDA',47,NULL,'NORMAL'),(291,'2025-07-21 22:48:07','ENTRADA',49,NULL,'TARDÍA'),(292,'2025-07-21 22:48:32','SALIDA',49,NULL,'NORMAL'),(293,'2025-07-21 22:51:36','ENTRADA',49,NULL,'TARDÍA'),(294,'2025-07-21 22:51:54','ALMUERZO',49,NULL,'NORMAL'),(295,'2025-07-21 22:52:08','SALIDA',49,NULL,'NORMAL'),(296,'2025-07-21 23:12:36','ENTRADA',48,NULL,'TARDÍA'),(297,'2025-07-21 23:15:50','SALIDA',48,NULL,'NORMAL'),(298,'2025-07-22 17:42:23','ENTRADA',52,NULL,'TARDÍA'),(299,'2025-07-22 17:43:44','SALIDA',52,NULL,'NORMAL'),(300,'2025-07-22 18:56:51','ENTRADA',48,NULL,'TARDÍA'),(301,'2025-07-22 20:13:02','SALIDA',48,NULL,'NORMAL'),(302,'2025-07-22 20:14:42','ENTRADA',48,NULL,'TARDÍA'),(303,'2025-07-22 23:12:39','SALIDA',48,NULL,'NORMAL'),(304,'2025-07-22 23:29:44','ENTRADA',48,NULL,'TARDÍA'),(305,'2025-07-22 23:43:57','SALIDA',48,NULL,'NORMAL'),(306,'2025-07-22 23:52:17','ENTRADA',49,NULL,'TARDÍA'),(307,'2025-07-22 23:53:43','SALIDA',49,NULL,'NORMAL'),(308,'2025-07-23 00:02:28','ENTRADA',49,NULL,'NORMAL'),(309,'2025-07-23 00:04:28','SALIDA',49,NULL,'NORMAL'),(310,'2025-07-23 00:22:21','ENTRADA',47,NULL,'NORMAL'),(311,'2025-07-23 01:14:32','SALIDA',47,NULL,'NORMAL'),(312,'2025-07-23 01:39:20','ENTRADA',46,NULL,'NORMAL'),(313,'2025-07-24 12:10:11','ENTRADA',48,NULL,'TARDÍA'),(314,'2025-07-24 13:00:55','ENTRADA',47,NULL,'NORMAL'),(315,'2025-07-24 13:01:23','SALIDA',48,NULL,'NORMAL'),(316,'2025-07-24 13:32:27','ENTRADA',51,NULL,'NORMAL'),(317,'2025-07-24 16:05:48','ENTRADA',49,NULL,'TARDÍA'),(318,'2025-07-24 16:07:02','SALIDA',47,NULL,'NORMAL'),(319,'2025-07-25 19:41:18','ENTRADA',48,NULL,'TARDÍA'),(320,'2025-07-25 19:42:12','ENTRADA',51,NULL,'TARDÍA'),(321,'2025-07-25 19:43:37','SALIDA',48,NULL,'NORMAL'),(322,'2025-07-26 01:35:16','ENTRADA',47,NULL,'NORMAL'),(323,'2025-07-26 01:37:12','SALIDA',47,NULL,'NORMAL');
/*!40000 ALTER TABLE `asistencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `categoria_id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`categoria_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (71,'CT Baracoa','Baracoa'),(72,'CA Maisi','Maisí'),(73,'CA Imias','Imías'),(74,'CT Yateras','Yateras'),(75,'CT San Antonio del Sur','San Antonio'),(76,'CT Caimanera','Caimanera'),(77,'GTMO','Guantánamo'),(78,'NP','Niceto Peres'),(79,'MT','Manuel Tames'),(80,'CA El Salvador','El Salvador');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenes`
--

DROP TABLE IF EXISTS `examenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examenes` (
  `examen_id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `numero_de_preguntas` varchar(255) DEFAULT NULL,
  `puntos_maximos` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `categoria_categoria_id` bigint DEFAULT NULL,
  `prioridad` varchar(255) DEFAULT NULL,
  `categoria_del_cliente` varchar(255) DEFAULT NULL,
  `organismo` varchar(255) DEFAULT NULL,
  `consejo_popular` varchar(255) DEFAULT NULL,
  `costo_de_instalacion` varchar(255) DEFAULT NULL,
  `cuota` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `enlace` varchar(255) DEFAULT NULL,
  `municipio` varchar(255) DEFAULT NULL,
  `no_adsl` varchar(255) DEFAULT NULL,
  `numero_de_solicitud` varchar(255) DEFAULT NULL,
  `seguimiento` varchar(255) DEFAULT NULL,
  `soliciud` varchar(255) DEFAULT NULL,
  `telefono_de_contacto` varchar(255) DEFAULT NULL,
  `estado_del_servicio` varchar(255) DEFAULT NULL,
  `velocidad` varchar(255) DEFAULT NULL,
  `solicitud` varchar(255) DEFAULT NULL,
  `fecha_de_solicitud` date DEFAULT NULL,
  `estado_de_calificacion_de_los_centros` varchar(255) DEFAULT NULL,
  `evaluacion` varchar(255) DEFAULT NULL,
  `fecha_respuesta_calificacion_operaciones` date DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `observaciones_especialista_de_operaciones` varchar(255) DEFAULT NULL,
  `fecha_de_ejecucion_estimadaaproponer` date DEFAULT NULL,
  `propuesta_de_soluion_tecnica` varchar(255) DEFAULT NULL,
  `tipo_de_recursosademandar` varchar(255) DEFAULT NULL,
  `observacion_esp_inversiones` varchar(255) DEFAULT NULL,
  `tipo_de_servicio` varchar(255) DEFAULT NULL,
  `instalada` varchar(255) DEFAULT NULL,
  `programa_proyecto` varchar(255) DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `codigoqr` varchar(255) NOT NULL DEFAULT '',
  `campo_modificado` varchar(255) DEFAULT NULL,
  `fecha_ultima_modificacion` datetime DEFAULT NULL,
  `notificar_administrador` bit(1) NOT NULL,
  `usuario_que_modifico` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`examen_id`),
  KEY `FK9e3vkr595xf5ntcw0ih72lifw` (`categoria_categoria_id`),
  KEY `FKa027tdoo6kxwi3vhgobxn8j89` (`usuario_id`),
  CONSTRAINT `FK9e3vkr595xf5ntcw0ih72lifw` FOREIGN KEY (`categoria_categoria_id`) REFERENCES `categorias` (`categoria_id`),
  CONSTRAINT `FKa027tdoo6kxwi3vhgobxn8j89` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examenes`
--

LOCK TABLES `examenes` WRITE;
/*!40000 ALTER TABLE `examenes` DISABLE KEYS */;
INSERT INTO `examenes` VALUES (61,_binary '','Direccion Municipal de Deporte y Recreación','','','AZCUBA',71,'Alta','Proyectos_Priorizados','CANEC_SA','','we','t','Direccion municipal de deportes y recreacion','6767','','ewwe','4','DT',NULL,'34','Solicitud_Nueva','8Mbps','Alta','2024-10-23','Pdte_Puerta','Operaciones','2025-03-20','santo Se necesita de equipo tplink para su implementación','santo1',NULL,'d','iiii','pendiente a recursos',NULL,NULL,NULL,NULL,'',NULL,'2025-06-26 23:28:04',_binary '\0','www'),(62,_binary '','Consulado Habana','','','CONJUSOL',72,'Alta','Informatización_CTC','EMPA','gfg','as','f','ghhghg','dfdfdf','','f','1','VPCM',NULL,'sfsdfsdff','Con_OS_Siprec','4Mbps','Alta_Mig','2025-04-24','DI','Operaciones','2024-10-10','Parámetros del Cable incorrecto','Se requiere de cable UTP','2029-12-04','FO','100km','sa',NULL,NULL,NULL,NULL,'',NULL,'2025-06-27 00:18:20',_binary '\0','www'),(63,_binary '','Educación Salud','','','EPCONS',71,'Baja','Sin_Clasificación','Fondo_Cubano','baitiquiri','trt','fd','ewewewewe','4433','','445df','22','DT',NULL,'2323','Solicitud_Nueva','155Mbps','CL','2025-04-02','No_Apto','Operaciones','2025-04-15','No disponibilidad','Revisar',NULL,'','','',NULL,NULL,NULL,NULL,'',NULL,'2025-06-26 23:27:51',_binary '\0','www'),(64,_binary '','dffdffddd','','','MES',73,'Alta','MIPYMES','MES','43443','rttrtr','trtrtr','56565','fdf54','','454','23','VPCM',NULL,'fgf','Solicitud_Nueva','2Mbps','CL','2025-04-03','DI','Operaciones','2024-11-11','','',NULL,'','','',NULL,NULL,NULL,NULL,'',NULL,NULL,_binary '\0',NULL),(65,_binary '','tyyty','','','GAF',75,'Alta','Informatización_PCC','GAF','ddd','tyy','yty','fgtf','tytyt','','ttyyy','66','VPCM',NULL,'tytyyt','Con_OS_Siprec','4Mbps','Baja','2025-04-03','','Operaciones',NULL,'','',NULL,'','','',NULL,'Pendiente_de_Inlalacion',NULL,NULL,'',NULL,NULL,_binary '\0',NULL),(66,_binary '','san','','','Otros',76,'Alta','MINED','Otro','r3','g','g','ereetr','dffdd','','f454','4','VPCM',NULL,'4gfgffg','Con_OS_Siprec','10Mbps','Alta','2025-04-03','DI','Operaciones','2025-04-11','Pendiente a FO','Se requieren 62m','2086-12-04','','','',NULL,NULL,NULL,NULL,'',NULL,NULL,_binary '\0',NULL),(67,_binary '','RADIOCUBA','','','PCC',74,'Baja','Informatización_PCC','PCC','err','55','erer','erre','rer','','ere','34','DT',NULL,'43454','OK_Red_Móvil','20Mbps','Baja','2025-04-12','Pdte_Puerta','Operaciones','2025-04-30','INA','ina1','2025-04-12','','','',NULL,NULL,NULL,NULL,'','estadoDeCalificacionDeLosCentros','2025-06-27 00:31:56',_binary '','www'),(68,_binary '','citma','','','CITMA',80,'Baja','MIPYMES','CITMA','tr','fdfdf','4ghh','yuyu','454545','','4555','54','VPCM',NULL,'445','En_Proceso','10Mbps','CVIV','2025-02-10','','',NULL,'','','2025-04-13','','','',NULL,NULL,NULL,NULL,'',NULL,NULL,_binary '\0',NULL),(69,_binary '','artex','','','Artex',73,'Baja','Proyectos_Priorizados','Artex','erer','cv','hjj','reere','tt','','trt','3','VPCM',NULL,'545','Solicitud_Nueva','80Mbps','CL','2025-02-25','No_Apto','Inversiones','2025-04-13','','','2025-04-13','','','',NULL,NULL,NULL,NULL,'',NULL,NULL,_binary '\0',NULL),(71,_binary '','Centro Comercial El Salvador Suministro Agropecuario','','','EMPA',80,'Alta','MINED','MINAG','Bayate','910','140','Bayate','','','212811112','100','VPCM',NULL,'','DI','2Mbps','Alta','2025-04-16','','',NULL,'','','2025-04-16','','','',NULL,NULL,NULL,NULL,'',NULL,NULL,_binary '\0',NULL),(72,_binary '','Solvisión Internet','','','ICRT',77,'Baja','Proyectos_Priorizados','ICRT','Caribe','144','123','Calle 13 Norte #1151 Esq. 5 Oeste. ','GTED3435','','','1','DT',NULL,'3453535','OK','20Mbps','Alta','2024-02-06','','',NULL,'','','2025-04-17','','','','Dedicado_Internacional','Instalada','Otros_Programas',NULL,'',NULL,NULL,_binary '\0',NULL),(73,_binary '','qq','','','Artex',75,'','','Artex','Mártires-de-la-Frontera','','','qq','','','','4','',NULL,'','Solicitud_Nueva','2Mbps','Alta','2025-06-22','DI','Operaciones','2025-06-26','se necesita de equipo VDSL','Transferir del almacen 8085','2025-06-22','','','','','','',NULL,'','estadoDeCalificacionDeLosCentros','2025-06-27 00:39:24',_binary '','s'),(74,_binary '','admin','','','ADMIN',71,'','','ADMIN','Cabacú','','','','','','','4','',NULL,'','Solicitud_Nueva','Paquete_Móvil','Alta','2025-06-22','','Operaciones',NULL,'','','2025-06-22','','','','','','',NULL,'',NULL,'2025-06-27 00:31:39',_binary '\0','www'),(75,_binary '','WW','','','CIMEX',75,'','','CITMA','Mandinga','','','WW','','','','2','',NULL,'','Con_OS_Siprec','2Mbps','','2025-06-22','','Inversiones',NULL,'','','2025-06-22','fq','','','','','',NULL,'',NULL,'2025-06-26 22:55:08',_binary '\0','www');
/*!40000 ALTER TABLE `examenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_semana` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hora_entrada` time NOT NULL,
  `hora_salida` time NOT NULL,
  `usuario_id` bigint NOT NULL,
  `turno` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `horarios_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=859 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios`
--

LOCK TABLES `horarios` WRITE;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
INSERT INTO `horarios` VALUES (844,NULL,'07:00:00','23:00:00',46,NULL,'2025-07-30','2025-07-18'),(846,NULL,'09:00:00','23:00:00',48,NULL,'2025-07-30','2025-07-18'),(847,NULL,'07:00:00','23:59:00',49,NULL,'2025-07-31','2025-07-11'),(849,NULL,'14:00:00','15:00:00',51,NULL,'2025-07-30','2025-07-18'),(850,NULL,'11:50:00','23:00:00',52,NULL,'2025-07-31','2025-07-01'),(852,NULL,'07:14:00','00:14:00',49,NULL,'2025-07-23','2025-07-20'),(854,NULL,'23:09:00','12:09:00',50,NULL,'2025-07-20','2025-07-20'),(855,NULL,'23:13:00','23:58:00',53,NULL,'2025-07-23','2025-06-23'),(858,NULL,'22:39:00','00:39:00',47,NULL,'2025-07-31','2025-07-01');
/*!40000 ALTER TABLE `horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preguntas`
--

DROP TABLE IF EXISTS `preguntas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preguntas` (
  `pregunta_id` bigint NOT NULL AUTO_INCREMENT,
  `contenido` varchar(5000) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `opcion2` varchar(255) DEFAULT NULL,
  `opcion3` varchar(255) DEFAULT NULL,
  `opcion4` varchar(255) DEFAULT NULL,
  `respuesta` varchar(255) DEFAULT NULL,
  `examen_examen_id` bigint DEFAULT NULL,
  `opcion1` varchar(255) DEFAULT NULL,
  `respuesta_dada` varchar(255) DEFAULT NULL,
  `estado_de_calificacion_de_los_centros` varchar(255) DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `evaluacion` varchar(255) DEFAULT NULL,
  `observaciones_especialista_de_operaciones` varchar(255) DEFAULT NULL,
  `fecha_respuesta_calificacion_operciones` datetime(6) DEFAULT NULL,
  `fecha_respuesta_calificacion_operaciones` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`pregunta_id`),
  KEY `FK9g0sx7pv0vsvc4uksis4egp4j` (`examen_examen_id`),
  CONSTRAINT `FK9g0sx7pv0vsvc4uksis4egp4j` FOREIGN KEY (`examen_examen_id`) REFERENCES `examenes` (`examen_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preguntas`
--

LOCK TABLES `preguntas` WRITE;
/*!40000 ALTER TABLE `preguntas` DISABLE KEYS */;
/*!40000 ALTER TABLE `preguntas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qr_token`
--

DROP TABLE IF EXISTS `qr_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qr_token` (
  `token` varchar(255) NOT NULL,
  `tipo_evento` varchar(255) DEFAULT NULL,
  `expiracion` datetime DEFAULT NULL,
  `usado` tinyint(1) NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qr_token`
--

LOCK TABLES `qr_token` WRITE;
/*!40000 ALTER TABLE `qr_token` DISABLE KEYS */;
INSERT INTO `qr_token` VALUES ('98fb8f7d-c33c-4063-9b31-9cee34b7d650','ENTRADA','2025-07-26 09:53:04',0);
/*!40000 ALTER TABLE `qr_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_asistencia_qr`
--

DROP TABLE IF EXISTS `registro_asistencia_qr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_asistencia_qr` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `fecha_escaneo` datetime NOT NULL,
  `estado` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_asistencia_qr`
--

LOCK TABLES `registro_asistencia_qr` WRITE;
/*!40000 ALTER TABLE `registro_asistencia_qr` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_asistencia_qr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_asistenciaqr`
--

DROP TABLE IF EXISTS `registro_asistenciaqr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_asistenciaqr` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fecha_escaneo` datetime DEFAULT NULL,
  `estado` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_asistenciaqr`
--

LOCK TABLES `registro_asistenciaqr` WRITE;
/*!40000 ALTER TABLE `registro_asistenciaqr` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_asistenciaqr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `rol_id` bigint NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'NORMAL');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_rol` (
  `usuario_rol_id` bigint NOT NULL AUTO_INCREMENT,
  `rol_rol_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`usuario_rol_id`),
  KEY `FK7j1tyvjj1tv8gbq7n6f7efccc` (`rol_rol_id`),
  KEY `FKktsemf1f6awjww4da0ocv4n32` (`usuario_id`),
  CONSTRAINT `FK7j1tyvjj1tv8gbq7n6f7efccc` FOREIGN KEY (`rol_rol_id`) REFERENCES `roles` (`rol_id`),
  CONSTRAINT `FKktsemf1f6awjww4da0ocv4n32` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_rol`
--

LOCK TABLES `usuario_rol` WRITE;
/*!40000 ALTER TABLE `usuario_rol` DISABLE KEYS */;
INSERT INTO `usuario_rol` VALUES (1,1,1),(2,1,2),(46,2,46),(47,2,47),(48,2,48),(49,2,49),(50,2,50),(51,2,51),(52,2,52),(53,2,53);
/*!40000 ALTER TABLE `usuario_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `perfil` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `ctlc` varchar(255) DEFAULT NULL,
  `hora_salida` datetime DEFAULT NULL,
  `hora_entrada` datetime DEFAULT NULL,
  `dni` varchar(20) DEFAULT NULL,
  `dia_semana` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `puesto` varchar(255) DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `fecha_de_contrato` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Girandy','santos.columbie@etecsa.cu',_binary '','santod','$2a$10$voB65MxwxHk3ydfhvf.lXOLG0b0qt0FnL9fJ3dgzrnBRQZ1TaFuwe','foto.pnp','59888006','santod',NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL),(2,'Girandy','santos.columbie@etecsa.cu',_binary '','santodgc','$2a$10$9T.mNHVq.1mD/s20zL0exuZP5.So2YL5bNdeugJAsCk2/hO0FrqSC','foto.pnp','59888006','santodgc',NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL),(46,'Woll','w@app.cu',_binary '','Walter','$2a$10$VzeMNh/88/pwCNiDK9XLJewkMptCXqRseh72/EztOKhCgBSGOtgJa','defaul.png','32134567','w','Informatica',NULL,NULL,'12345678901',NULL,'Wajay Habana','Ingeniero en Sistemas',NULL,'2025-07-19'),(47,'Altunez','a@hu.vi',_binary '','Alvaro','$2a$10$5Ml3TjoMB1T.lmO.4zC9LeL0.D8BEWX8QFIho6CJvSOgk5xLKm3yO','defaul.png','12456789','a','Economia',NULL,NULL,'12345631234',NULL,'Artemisa. Habana','Especialista en Gestión Económica',NULL,'2025-07-15'),(48,'Quiala','q@qs.m',_binary '','Quevedo','$2a$10$qUTbmFKSNfPnEpf6fUXvPOvKa.zlMD.AQVTmCJ6VDAU16rr809hhi','defaul.png','65478987','q','Comercial',NULL,NULL,'45632123456',NULL,'Quivijan','Especialista Comercial',NULL,'2025-07-10'),(49,'Samón','s@gh.ku',_binary '','Silvia','$2a$10$K0N9LhF5xQYi0rjE7QWuXOIriNXfjM1kICbyi0cYQmdV/I6oZQsYK','defaul.png','23479832','s','Marketing',NULL,NULL,'23456712098',NULL,'Santa María','Jefe de Departamento',NULL,'2025-07-10'),(50,'Zill','z@nj.cu',_binary '','Zoila','$2a$10$BVyqOiSaWK4oxRsNA/39lOrBN1LdPvW5pgSI1WavLaq.1aeUBh04y','defaul.png','67890345','z','Serguridad y Protección',NULL,NULL,'54678923234',NULL,'Zanja, No. 23. Habana. Cuba','Jefe de Departamento',NULL,'2025-07-01'),(51,'Xelchon','x@hty.vj.vc',_binary '','Xiomara','$2a$10$sUUwvvxgDvHUT1WjpOUWU.9wTuurmQETZeYQB8pmel7HwvRujncwq','defaul.png','445789034','x','Investigación y Desarrollo',NULL,NULL,'5555666777',NULL,'Xanon, Habana del Este. Cuba','Especialista Proyectos I+D',NULL,'2025-07-31'),(52,'Elso','e@rm.cf',_binary '','Eulide','$2a$10$bX1iM6l781ScVaboDAsrtecmIZ.Ltf2iorSQCUrq797LsXtEyG1l6','defaul.png','334445676','e','Operaciones',NULL,NULL,'34567890321',NULL,'Elsa, Cuba','Jefe de Operaciones de La Red',NULL,'2025-07-19'),(53,'Díaz','dai@fg.cu',_binary '','daniel','$2a$10$hcpkeVo2C98HOFY4xcz99uEtriGlgj3psSrvgXULuDv7MZSjrFbTu','defaul.png','334343432','d','Recursos Humanos',NULL,NULL,'43344334343343',NULL,'daitron.Stgo','EspecialistaPrincipal',NULL,'2025-07-20');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-26  9:58:17
