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
) ENGINE=InnoDB AUTO_INCREMENT=379 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencias`
--

LOCK TABLES `asistencias` WRITE;
/*!40000 ALTER TABLE `asistencias` DISABLE KEYS */;
INSERT INTO `asistencias` VALUES (347,'2025-08-06 08:09:13','ENTRADA',53,NULL,'NORMAL'),(348,'2025-08-06 08:11:34','ENTRADA',55,NULL,'NORMAL'),(349,'2025-08-06 09:08:20','SALIDA',53,NULL,'NORMAL'),(350,'2025-08-06 10:01:53','SALIDA',55,NULL,'NORMAL'),(351,'2025-08-06 14:40:39','ENTRADA',56,NULL,'NORMAL'),(352,'2025-08-06 14:44:54','SALIDA',56,NULL,'NORMAL'),(353,'2025-08-06 14:46:53','ENTRADA',58,NULL,'TARDÍA'),(354,'2025-08-06 14:50:07','SALIDA',58,NULL,'NORMAL'),(365,'2025-08-06 18:44:38','ENTRADA',59,NULL,'NORMAL'),(367,'2025-08-06 18:48:54','ENTRADA',59,NULL,'NORMAL'),(368,'2025-08-06 19:09:21','SALIDA',59,NULL,'NORMAL'),(369,'2025-08-06 19:10:54','ENTRADA',59,NULL,'TARDÍA'),(370,'2025-08-06 19:26:30','SALIDA',59,NULL,'NORMAL'),(371,'2025-08-06 20:17:25','ENTRADA',59,NULL,'TARDÍA'),(372,'2025-08-06 20:32:17','SALIDA',59,NULL,'NORMAL'),(373,'2025-08-07 00:00:00','ENTRADA',60,NULL,'TARDÍA'),(374,'2025-08-07 01:54:00','SALIDA',60,NULL,'NORMAL'),(375,'2025-08-07 02:13:07','ENTRADA',60,NULL,'NORMAL'),(376,'2025-08-07 02:16:28','SALIDA',60,NULL,'NORMAL'),(377,'2025-08-07 16:08:51','ENTRADA',61,NULL,'NORMAL'),(378,'2025-08-07 17:07:36','SALIDA',61,NULL,'NORMAL');
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
  `tolerancia_entrada` int NOT NULL,
  `tolerancia_salida` int NOT NULL,
  `es_configuracion` tinyint(1) NOT NULL,
  `horas_normales_por_dia` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `horarios_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios`
--

LOCK TABLES `horarios` WRITE;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
INSERT INTO `horarios` VALUES (1000,NULL,'01:50:00','06:51:00',56,NULL,'2025-08-01','2025-08-01',0,0,0,0),(1017,NULL,'08:11:00','08:15:00',53,NULL,'2025-08-15','2025-08-01',0,0,0,0),(1019,NULL,'08:05:00','08:15:00',55,NULL,'2025-08-07','2025-08-04',0,0,0,0),(1020,NULL,'14:38:00','14:45:00',56,NULL,'2025-08-15','2025-08-01',0,0,0,0),(1021,NULL,'08:00:00','17:00:00',1,NULL,'2025-08-06','2025-08-06',30,10,1,1),(1022,NULL,'14:00:00','14:50:00',58,NULL,'2025-08-14','2025-08-05',0,0,0,0),(1026,NULL,'18:43:00','18:45:00',58,NULL,'2025-08-15','2025-08-06',0,0,0,0),(1027,NULL,'18:10:00','19:10:00',59,NULL,'2025-08-06','2025-08-06',0,0,0,0),(1028,NULL,'19:10:00','20:00:00',59,NULL,'2025-08-06','2025-08-06',0,0,0,0),(1030,NULL,'20:05:00','20:18:00',59,NULL,'2025-08-06','2025-08-06',0,0,0,0),(1031,NULL,'23:42:00','02:06:00',60,NULL,'2025-08-07','2025-08-07',0,0,0,0),(1032,NULL,'02:10:00','02:15:00',60,NULL,'2025-08-07','2025-08-07',0,0,0,0),(1033,NULL,'16:00:00','16:10:00',61,NULL,'2025-08-15','2025-08-07',0,0,0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_rol`
--

LOCK TABLES `usuario_rol` WRITE;
/*!40000 ALTER TABLE `usuario_rol` DISABLE KEYS */;
INSERT INTO `usuario_rol` VALUES (1,1,1),(2,1,2),(53,2,53),(54,2,55),(55,2,56),(57,2,58),(58,2,59),(59,2,60),(60,2,61);
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
  `tarifa_por_hora` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Girandy','santos.columbie@etecsa.cu',_binary '','santod','$2a$10$voB65MxwxHk3ydfhvf.lXOLG0b0qt0FnL9fJ3dgzrnBRQZ1TaFuwe','foto.pnp','59888006','santod',NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,NULL),(2,'Girandy','santos.columbie@etecsa.cu',_binary '','santodgc','$2a$10$9T.mNHVq.1mD/s20zL0exuZP5.So2YL5bNdeugJAsCk2/hO0FrqSC','foto.pnp','59888006','santodgc',NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,NULL),(53,'Díaz','dai@fg.cu',_binary '','daniel','$2a$10$hcpkeVo2C98HOFY4xcz99uEtriGlgj3psSrvgXULuDv7MZSjrFbTu','defaul.png','334343432','d','Recursos Humanos',NULL,NULL,'43344334343343',NULL,'daitron.Stgo','EspecialistaPrincipal',NULL,'2025-07-20',NULL),(55,'cill','cc@ff.gh',_binary '','cecilia','$2a$10$GrzWSdlfMpHjd3RgqlicvOZ7VQVlmekkdeG6E2yXdADHtaynts6Bu','defaul.png','2344344','c','Iversiones',NULL,NULL,'3343454555',NULL,'la celva. Perú','Inversionista ',NULL,'2025-07-27',NULL),(56,'reye','rio@rt.kl',_binary '','riooo','$2a$10$GKiw73JOABle9zjjqwAQE.cO2XdVjz/Ol9K3cVel00qFlxYraLsE6','defaul.png','45454454','r','Circuito',NULL,NULL,'344334433443',NULL,'dfdfddf','j rh',NULL,'2025-07-30',NULL),(58,'Gamez','e@rm.cf',_binary '','Eulide','$2a$10$D3Rb10pZ0NBlVhhgXduN/etmdsEa8lGOFZfHHfDLhcenE3cObQzPK','defaul.png','345565656','e','Comercial',NULL,NULL,'23343434',NULL,'La pajarera','Jefe Comercial',NULL,'2025-08-06',NULL),(59,'wood','www@cu.cu',_binary '','walter','$2a$10$Ay30GuS1W2DIrZR08ZGnl.Nctx8D3ESM7BZV498Ti5FW0Sk8rDFdi','defaul.png','344545','w','Trafico',NULL,NULL,'3233443',NULL,'weeer','Especialista Principal',NULL,'2025-08-06',NULL),(60,'filo','f@ju.ci.v',_binary '','fidel','$2a$10$xF/qkeReFYXvGYW24iRsU.mmbsZVpwxNp3nw6/3T6/PQoLt3d7sYy','defaul.png','4554','f','Operaciones',NULL,NULL,'344344',NULL,'weeer','Especialista',NULL,'2025-08-07','200'),(61,'vidal','vuyki@lk.l',_binary '','vida','$2a$10$1RWGrukWEoaKec51tRIjV.vB5JtxsH6KW8mDvfzX.Ervu.nAl3pMK','defaul.png','343344','v','Servicio',NULL,NULL,'334454545',NULL,'via Blanca','Administrador',NULL,'2025-08-07','10');
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

-- Dump completed on 2025-08-08  0:38:17
