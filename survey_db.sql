-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.24 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for survey_db
DROP DATABASE IF EXISTS `survey_db`;
CREATE DATABASE IF NOT EXISTS `survey_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `survey_db`;

-- Dumping structure for table survey_db.answers
DROP TABLE IF EXISTS `answers`;
CREATE TABLE IF NOT EXISTS `answers` (
  `entry_id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_name` varchar(20) DEFAULT NULL,
  `question` varchar(60) DEFAULT NULL,
  `answer` varchar(20) DEFAULT NULL,
  `user_id` varchar(60) DEFAULT NULL,
  `code` varchar(8) DEFAULT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`entry_id`)
) ENGINE=MyISAM AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;

-- Dumping data for table survey_db.answers: 24 rows
DELETE FROM `answers`;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` (`entry_id`, `survey_name`, `question`, `answer`, `user_id`, `code`, `time`) VALUES
	(67, 'French', 'Question 3', '2', '0:0:0:0:0:0:0:1', '62F896D3', '2020-09-04 12:20:18'),
	(66, 'French', 'Question 3', '4', '0:0:0:0:0:0:0:1', '27870B9', '2020-08-15 23:36:18'),
	(13, 'English', '0', '1', '0:0:0:0:0:0:0:1', 'NULL', '2020-08-13 09:50:03'),
	(65, 'French', 'Question 3', '1', '0:0:0:0:0:0:0:1', '6A43091F', '2020-08-15 23:33:20'),
	(16, 'English', '0', '3', '0:0:0:0:0:0:0:1', 'NULL', '2020-08-13 09:52:15'),
	(17, 'English', '0', '1', '0:0:0:0:0:0:0:1', 'NULL', '2020-08-13 11:00:24'),
	(64, 'French', 'Question 3', '2', '0:0:0:0:0:0:0:1', '77D0175A', '2020-08-15 23:32:09'),
	(63, 'French', 'Question 3', '2', '0:0:0:0:0:0:0:1', 'B69AB62', '2020-08-15 23:32:07'),
	(62, 'French', 'Question 3', '2', '0:0:0:0:0:0:0:1', '3A4ADC44', '2020-08-15 23:32:06'),
	(25, 'English', '0', '2', '0:0:0:0:0:0:0:1', 'NULL', '2020-08-14 17:58:14'),
	(61, 'French', 'Question 3', '3', '0:0:0:0:0:0:0:1', '2B4EFFD9', '2020-08-15 23:31:55'),
	(60, 'French', 'Question 3', '3', '0:0:0:0:0:0:0:1', '48601A27', '2020-08-15 23:31:54'),
	(59, 'French', 'Question 3', '3', '0:0:0:0:0:0:0:1', '78E16081', '2020-08-15 23:31:53'),
	(58, 'French', 'Question 3', '3', '0:0:0:0:0:0:0:1', '3D62315F', '2020-08-15 23:31:52'),
	(57, 'French', 'Question 3', '3', '0:0:0:0:0:0:0:1', '622E9EBA', '2020-08-15 23:27:44'),
	(56, 'French', 'Question 3', '4', '0:0:0:0:0:0:0:1', '421CE219', '2020-08-15 23:27:38'),
	(55, 'French', 'Question 3', '1', '0:0:0:0:0:0:0:1', '788CCF9C', '2020-08-15 23:27:32'),
	(54, 'French', 'Question 3', '1', '0:0:0:0:0:0:0:1', '2CCF9803', '2020-08-15 23:27:26'),
	(53, 'French', 'Question 3', '2', '0:0:0:0:0:0:0:1', '49FFDD18', '2020-08-15 23:27:15'),
	(52, 'French', 'Question 3', '3', '0:0:0:0:0:0:0:1', '2D0C6078', '2020-08-15 22:41:51'),
	(50, 'French', 'Question 3', '4', '0:0:0:0:0:0:0:1', '3AB9B9CA', '2020-08-15 22:41:49'),
	(51, 'French', 'Question 3', '1', '0:0:0:0:0:0:0:1', 'F3121A2', '2020-08-15 22:41:50'),
	(49, 'French', 'Question 3', '1', '0:0:0:0:0:0:0:1', '4D4DEE9D', '2020-08-15 22:41:46'),
	(68, 'French', 'How many people live in your house', '3', '0:0:0:0:0:0:0:1', '62F896D3', '2020-09-04 12:20:19');
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;

-- Dumping structure for table survey_db.codes
DROP TABLE IF EXISTS `codes`;
CREATE TABLE IF NOT EXISTS `codes` (
  `counter` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(8) DEFAULT NULL,
  `survey_name` varchar(60) DEFAULT NULL,
  `ip_address` varchar(16) DEFAULT NULL,
  `complete` tinyint(1) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`counter`)
) ENGINE=MyISAM AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;

-- Dumping data for table survey_db.codes: 71 rows
DELETE FROM `codes`;
/*!40000 ALTER TABLE `codes` DISABLE KEYS */;
INSERT INTO `codes` (`counter`, `code`, `survey_name`, `ip_address`, `complete`, `date`) VALUES
	(6, '789F22F5', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 09:50:18'),
	(7, 'NULL', 'English', '0:0:0:0:0:0:0:1', 1, '2020-08-13 09:52:09'),
	(8, 'NULL', 'English', '0:0:0:0:0:0:0:1', 1, '2020-08-13 11:00:23'),
	(19, '79DBB207', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 14:49:28'),
	(18, '6F44761', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 14:48:58'),
	(12, 'D71FF07', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 14:24:34'),
	(13, '2CA427A5', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 14:37:28'),
	(17, '236268D9', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 14:45:50'),
	(16, '25D872EE', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-13 14:39:08'),
	(20, '1AF52306', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:50:07'),
	(21, 'D40CB15', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:51:44'),
	(22, '64F24B91', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:52:41'),
	(23, 'C651E83', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:54:04'),
	(24, '75CD77B4', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:55:18'),
	(25, 'D46FA82', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:55:58'),
	(26, '7E715D5A', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:56:48'),
	(27, 'D71FF07', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:57:32'),
	(28, '79863265', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-13 14:58:18'),
	(29, '16083D7C', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-14 17:58:07'),
	(30, 'NULL', 'English', '0:0:0:0:0:0:0:1', 1, '2020-08-14 17:58:12'),
	(31, '5BC5E301', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-14 18:33:01'),
	(32, '5ABC96CB', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-14 18:34:29'),
	(33, '5D5BB21C', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-14 18:38:07'),
	(34, '66DDE987', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-14 18:39:18'),
	(35, '44A635B1', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-14 19:52:28'),
	(36, '44A635B1', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-14 21:11:17'),
	(37, '5A64B3EF', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-14 21:12:50'),
	(38, '79863265', 'French', '127.0.0.1', 0, '2020-08-14 21:14:03'),
	(39, '4380840F', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 18:11:53'),
	(40, '2DF1B6C8', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 18:20:46'),
	(41, '5D244272', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 18:27:18'),
	(42, '18A162E2', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 18:41:25'),
	(43, '2F6CFDDC', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 18:47:30'),
	(44, '278DEF0', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 18:48:43'),
	(45, '59419BC3', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 19:17:03'),
	(46, '21458C65', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 19:19:54'),
	(47, '5EDFAD74', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 19:23:22'),
	(48, '1487053A', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 19:25:47'),
	(49, '7846EF89', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 21:15:57'),
	(50, 'B349DAC', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 21:19:02'),
	(51, '21458C65', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 21:20:33'),
	(52, '7E715D5A', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 21:21:38'),
	(53, '34F1DF7E', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 21:25:28'),
	(54, '6B2FD612', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 21:26:49'),
	(55, '5EDFAD74', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 21:27:31'),
	(56, '7D2F676F', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 21:33:15'),
	(57, 'A893499', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 21:38:32'),
	(58, '7846EF89', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 21:48:31'),
	(59, '4D4DEE9D', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 22:41:44'),
	(60, '3AB9B9CA', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 22:41:47'),
	(61, 'F3121A2', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 22:41:49'),
	(62, '2D0C6078', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 22:41:51'),
	(63, '49FFDD18', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:27:09'),
	(64, '2CCF9803', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:27:25'),
	(65, '788CCF9C', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:27:31'),
	(66, '421CE219', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:27:37'),
	(67, '622E9EBA', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:27:42'),
	(68, '3D62315F', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:31:51'),
	(69, '78E16081', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:31:53'),
	(70, '48601A27', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:31:54'),
	(71, '2B4EFFD9', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:31:55'),
	(72, '6BA939F3', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 23:32:04'),
	(73, '3A4ADC44', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:32:05'),
	(74, 'B69AB62', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:32:06'),
	(75, '7B10A339', 'French', '0:0:0:0:0:0:0:1', 0, '2020-08-15 23:32:07'),
	(76, '77D0175A', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:32:08'),
	(77, '6A43091F', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:33:19'),
	(78, '27870B9', 'French', '0:0:0:0:0:0:0:1', 1, '2020-08-15 23:36:17'),
	(79, '3D2987E4', 'French', '127.0.0.1', 0, '2020-08-16 00:12:44'),
	(80, '62F896D3', 'French', '0:0:0:0:0:0:0:1', 1, '2020-09-04 12:20:17'),
	(81, '17B8F2F6', 'French', '0:0:0:0:0:0:0:1', 0, '2020-09-04 15:52:46');
/*!40000 ALTER TABLE `codes` ENABLE KEYS */;

-- Dumping structure for table survey_db.questions
DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_name` varchar(20) DEFAULT NULL,
  `question_type` varchar(8) DEFAULT NULL,
  `question` varchar(60) DEFAULT NULL,
  `answers` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- Dumping data for table survey_db.questions: 3 rows
DELETE FROM `questions`;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` (`id`, `survey_name`, `question_type`, `question`, `answers`) VALUES
	(3, 'English', 'MC', 'How many people live in your house', '1|2|3|4|5|6+'),
	(7, 'French', 'MC', 'Question 3', '1|2|3|4||'),
	(8, 'French', 'MC', 'How many people live in your house', '1|2|3|||');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;

-- Dumping structure for table survey_db.surveys
DROP TABLE IF EXISTS `surveys`;
CREATE TABLE IF NOT EXISTS `surveys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `survey_name` varchar(60) DEFAULT NULL,
  `survey_message` varchar(60) DEFAULT NULL,
  `survey_end_message` varchar(60) DEFAULT NULL,
  `code` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;

-- Dumping data for table survey_db.surveys: 2 rows
DELETE FROM `surveys`;
/*!40000 ALTER TABLE `surveys` DISABLE KEYS */;
INSERT INTO `surveys` (`id`, `survey_name`, `survey_message`, `survey_end_message`, `code`) VALUES
	(65, 'French', 'Continuez en Francais', 'Fini!', 1),
	(58, 'English', 'Continue in English', 'End', 0);
/*!40000 ALTER TABLE `surveys` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
