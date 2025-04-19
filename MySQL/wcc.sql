-- --------------------------------------------------------
-- Host:                         219.93.129.18
-- Server version:               8.0.26 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for wcc
CREATE DATABASE IF NOT EXISTS `wcc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wcc`;

-- Dumping structure for table wcc.postcodelatlng
CREATE TABLE IF NOT EXISTS `postcodelatlng` (
  `id` int NOT NULL AUTO_INCREMENT,
  `postcode` varchar(8) NOT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- Dumping data for table wcc.postcodelatlng: 19 rows
DELETE FROM `postcodelatlng`;
/*!40000 ALTER TABLE `postcodelatlng` DISABLE KEYS */;
INSERT INTO `postcodelatlng` (`id`, `postcode`, `latitude`, `longitude`) VALUES
	(1, 'AB10 1XG', 57.1441650, -2.1148480),
	(2, 'AB10 6RN', 57.1378800, -2.1214870),
	(3, 'AB10 7JB', 57.1242740, -2.1271900),
	(4, 'AB11 5QN', 57.1427010, -2.0932950),
	(5, 'AB11 6UL', 57.1375470, -2.1122330),
	(6, 'AB11 8RQ', 57.1359780, -2.0721150),
	(7, 'AB12 3FJ', 57.0980030, -2.0774380),
	(8, 'AB12 4NA', 57.0642730, -2.1300180),
	(9, 'AB12 5GL', 57.0819380, -2.2465670),
	(10, 'AB12 9SP', 57.1487070, -2.0978060),
	(11, 'AB14 0TQ', 57.1015570, -2.2684860),
	(12, 'AB15 5HB', 57.1474280, -2.1472660),
	(13, 'AB15 6NA', 57.1517970, -2.1853980),
	(14, 'AB15 8UF', 57.1540060, -2.2244020),
	(15, 'AB15 9SE', 57.1186480, -2.1742510),
	(16, 'AB16 5ST', 57.1634660, -2.1593330),
	(17, 'AB16 6SZ', 57.1587510, -2.1652150),
	(18, '50088', 3.1408530, 101.6932070),
	(19, '70000', 2.7261000, 101.9447000);
/*!40000 ALTER TABLE `postcodelatlng` ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
