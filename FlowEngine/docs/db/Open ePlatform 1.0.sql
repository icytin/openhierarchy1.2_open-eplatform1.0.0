-- MySQL dump 10.13  Distrib 5.1.73, for Win64 (unknown)
--
-- Host: localhost    Database: e-tjanster.sundsvall.se
-- ------------------------------------------------------
-- Server version	5.1.73-community

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
-- Table structure for table `checkbox_queries`
--

DROP TABLE IF EXISTS `checkbox_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `minChecked` int(10) unsigned DEFAULT NULL,
  `maxChecked` int(10) unsigned DEFAULT NULL,
  `freeTextAlternative` varchar(255) DEFAULT NULL,
  `helpText` text,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_queries`
--

LOCK TABLES `checkbox_queries` WRITE;
/*!40000 ALTER TABLE `checkbox_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkbox_query_alternatives`
--

DROP TABLE IF EXISTS `checkbox_query_alternatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_query_alternatives` (
  `alternativeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`alternativeID`),
  KEY `FK_checkbox_query_alternatives_1` (`queryID`),
  CONSTRAINT `FK_checkbox_query_alternatives_1` FOREIGN KEY (`queryID`) REFERENCES `checkbox_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=799 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_query_alternatives`
--

LOCK TABLES `checkbox_query_alternatives` WRITE;
/*!40000 ALTER TABLE `checkbox_query_alternatives` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_query_alternatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkbox_query_instance_alternatives`
--

DROP TABLE IF EXISTS `checkbox_query_instance_alternatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_query_instance_alternatives` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `alternativeID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`queryInstanceID`,`alternativeID`),
  KEY `FK_checkbox_query_instance_alternatives_2` (`alternativeID`),
  CONSTRAINT `FK_checkbox_query_instance_alternatives_1` FOREIGN KEY (`queryInstanceID`) REFERENCES `checkbox_query_instances` (`queryInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_checkbox_query_instance_alternatives_2` FOREIGN KEY (`alternativeID`) REFERENCES `checkbox_query_alternatives` (`alternativeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_query_instance_alternatives`
--

LOCK TABLES `checkbox_query_instance_alternatives` WRITE;
/*!40000 ALTER TABLE `checkbox_query_instance_alternatives` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_query_instance_alternatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkbox_query_instances`
--

DROP TABLE IF EXISTS `checkbox_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  `freeTextAlternativeValue` varchar(255) DEFAULT NULL,
  `minChecked` int(10) unsigned DEFAULT NULL,
  `maxChecked` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_checkbox_query_instances_1` (`queryID`),
  CONSTRAINT `FK_checkbox_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `checkbox_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_query_instances`
--

LOCK TABLES `checkbox_query_instances` WRITE;
/*!40000 ALTER TABLE `checkbox_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_detail_queries`
--

DROP TABLE IF EXISTS `contact_detail_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_detail_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `helpText` text,
  `allowLetter` tinyint(1) unsigned NOT NULL,
  `allowSMS` tinyint(1) unsigned NOT NULL,
  `allowEmail` tinyint(1) unsigned NOT NULL,
  `allowPhone` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_detail_queries`
--

LOCK TABLES `contact_detail_queries` WRITE;
/*!40000 ALTER TABLE `contact_detail_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_detail_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_detail_query_instances`
--

DROP TABLE IF EXISTS `contact_detail_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_detail_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryID` int(10) unsigned NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `contactByLetter` tinyint(1) unsigned DEFAULT NULL,
  `contactBySMS` tinyint(1) unsigned DEFAULT NULL,
  `contactByEmail` tinyint(1) unsigned DEFAULT NULL,
  `contactByPhone` tinyint(1) unsigned DEFAULT NULL,
  `persistUserProfile` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_contact_detail_query_instances_1` (`queryID`),
  CONSTRAINT `FK_contact_detail_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `contact_detail_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4566 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_detail_query_instances`
--

LOCK TABLES `contact_detail_query_instances` WRITE;
/*!40000 ALTER TABLE `contact_detail_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_detail_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drop_down_queries`
--

DROP TABLE IF EXISTS `drop_down_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drop_down_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `shortDescription` varchar(255) DEFAULT NULL,
  `freeTextAlternative` varchar(255) DEFAULT NULL,
  `helpText` text,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drop_down_queries`
--

LOCK TABLES `drop_down_queries` WRITE;
/*!40000 ALTER TABLE `drop_down_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `drop_down_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drop_down_query_alternatives`
--

DROP TABLE IF EXISTS `drop_down_query_alternatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drop_down_query_alternatives` (
  `alternativeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`alternativeID`),
  KEY `FK_drop_down_query_alternatives_1` (`queryID`),
  CONSTRAINT `FK_drop_down_query_alternatives_1` FOREIGN KEY (`queryID`) REFERENCES `drop_down_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drop_down_query_alternatives`
--

LOCK TABLES `drop_down_query_alternatives` WRITE;
/*!40000 ALTER TABLE `drop_down_query_alternatives` DISABLE KEYS */;
/*!40000 ALTER TABLE `drop_down_query_alternatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drop_down_query_instances`
--

DROP TABLE IF EXISTS `drop_down_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drop_down_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  `alternativeID` int(10) unsigned DEFAULT NULL,
  `freeTextAlternativeValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_drop_down_query_instances_1` (`queryID`),
  KEY `FK_drop_down_query_instances_2` (`alternativeID`),
  CONSTRAINT `FK_drop_down_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `drop_down_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_drop_down_query_instances_2` FOREIGN KEY (`alternativeID`) REFERENCES `drop_down_query_alternatives` (`alternativeID`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drop_down_query_instances`
--

LOCK TABLES `drop_down_query_instances` WRITE;
/*!40000 ALTER TABLE `drop_down_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `drop_down_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_attachments`
--

DROP TABLE IF EXISTS `email_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_attachments` (
  `attachmenID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `data` longblob NOT NULL,
  PRIMARY KEY (`attachmenID`),
  KEY `FK_attachments_1` (`emailID`),
  CONSTRAINT `FK_attachments_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_attachments`
--

LOCK TABLES `email_attachments` WRITE;
/*!40000 ALTER TABLE `email_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_bcc_recipients`
--

DROP TABLE IF EXISTS `email_bcc_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_bcc_recipients` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`) USING BTREE,
  KEY `FK_bccrecipient_1` (`emailID`),
  CONSTRAINT `FK_bccrecipient_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_bcc_recipients`
--

LOCK TABLES `email_bcc_recipients` WRITE;
/*!40000 ALTER TABLE `email_bcc_recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_bcc_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_cc_recipients`
--

DROP TABLE IF EXISTS `email_cc_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_cc_recipients` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`) USING BTREE,
  KEY `FK_ccrecipient_1` (`emailID`),
  CONSTRAINT `FK_ccrecipient_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_cc_recipients`
--

LOCK TABLES `email_cc_recipients` WRITE;
/*!40000 ALTER TABLE `email_cc_recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_cc_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_recipients`
--

DROP TABLE IF EXISTS `email_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_recipients` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`) USING BTREE,
  KEY `FK_recipient_1` (`emailID`),
  CONSTRAINT `FK_recipient_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_recipients`
--

LOCK TABLES `email_recipients` WRITE;
/*!40000 ALTER TABLE `email_recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_replyto`
--

DROP TABLE IF EXISTS `email_replyto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_replyto` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`) USING BTREE,
  KEY `FK_replyto_1` (`emailID`),
  CONSTRAINT `FK_replyto_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_replyto`
--

LOCK TABLES `email_replyto` WRITE;
/*!40000 ALTER TABLE `email_replyto` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_replyto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emails`
--

DROP TABLE IF EXISTS `emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emails` (
  `emailID` char(36) NOT NULL,
  `resendCount` int(10) unsigned DEFAULT NULL,
  `senderName` varchar(255) DEFAULT NULL,
  `senderAddress` varchar(255) NOT NULL,
  `charset` varchar(255) DEFAULT NULL,
  `messageContentType` varchar(255) DEFAULT NULL,
  `subject` mediumtext,
  `message` longtext,
  `lastSent` timestamp NULL DEFAULT NULL,
  `owner` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`emailID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emails`
--

LOCK TABLES `emails` WRITE;
/*!40000 ALTER TABLE `emails` DISABLE KEYS */;
/*!40000 ALTER TABLE `emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_upload_files`
--

DROP TABLE IF EXISTS `file_upload_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_upload_files` (
  `fileID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `size` int(10) unsigned NOT NULL,
  `queryInstanceID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`fileID`),
  KEY `FK_file_upload_files_1` (`queryInstanceID`),
  CONSTRAINT `FK_file_upload_files_1` FOREIGN KEY (`queryInstanceID`) REFERENCES `file_upload_query_instances` (`queryInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_upload_files`
--

LOCK TABLES `file_upload_files` WRITE;
/*!40000 ALTER TABLE `file_upload_files` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_upload_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_upload_queries`
--

DROP TABLE IF EXISTS `file_upload_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_upload_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `helpText` text,
  `maxFileCount` int(10) unsigned DEFAULT NULL,
  `maxFileSize` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_upload_queries`
--

LOCK TABLES `file_upload_queries` WRITE;
/*!40000 ALTER TABLE `file_upload_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_upload_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_upload_query_extensions`
--

DROP TABLE IF EXISTS `file_upload_query_extensions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_upload_query_extensions` (
  `queryID` int(10) unsigned NOT NULL,
  `extension` varchar(12) NOT NULL,
  PRIMARY KEY (`queryID`,`extension`),
  CONSTRAINT `FK_file_upload_query_extensions_1` FOREIGN KEY (`queryID`) REFERENCES `file_upload_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_upload_query_extensions`
--

LOCK TABLES `file_upload_query_extensions` WRITE;
/*!40000 ALTER TABLE `file_upload_query_extensions` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_upload_query_extensions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_upload_query_instances`
--

DROP TABLE IF EXISTS `file_upload_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_upload_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`queryInstanceID`) USING BTREE,
  KEY `FK_file_upload_query_instances_1` (`queryID`),
  CONSTRAINT `FK_file_upload_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `file_upload_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_upload_query_instances`
--

LOCK TABLES `file_upload_query_instances` WRITE;
/*!40000 ALTER TABLE `file_upload_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_upload_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_default_flow_statuses`
--

DROP TABLE IF EXISTS `flowengine_default_flow_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_default_flow_statuses` (
  `actionID` varchar(255) NOT NULL,
  `flowID` int(10) unsigned NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`actionID`,`flowID`),
  KEY `FK_flowengine_default_flow_states_2` (`flowID`),
  KEY `FK_flowengine_default_flow_states_3` (`statusID`) USING BTREE,
  CONSTRAINT `FK_flowengine_default_flow_states_1` FOREIGN KEY (`actionID`) REFERENCES `flowengine_flow_actions` (`actionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_flowengine_default_flow_states_2` FOREIGN KEY (`flowID`) REFERENCES `flowengine_flows` (`flowID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_flowengine_default_flow_statuses_3` FOREIGN KEY (`statusID`) REFERENCES `flowengine_flow_statuses` (`statusID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_default_flow_statuses`
--

LOCK TABLES `flowengine_default_flow_statuses` WRITE;
/*!40000 ALTER TABLE `flowengine_default_flow_statuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_default_flow_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_default_standard_statuses`
--

DROP TABLE IF EXISTS `flowengine_default_standard_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_default_standard_statuses` (
  `actionID` varchar(255) NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`actionID`,`statusID`),
  KEY `FK_flowengine_default_standard_statuses_2` (`statusID`),
  CONSTRAINT `FK_flowengine_default_standard_statuses_1` FOREIGN KEY (`actionID`) REFERENCES `flowengine_flow_actions` (`actionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_flowengine_default_standard_statuses_2` FOREIGN KEY (`statusID`) REFERENCES `flowengine_standard_statuses` (`statusID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_default_standard_statuses`
--

LOCK TABLES `flowengine_default_standard_statuses` WRITE;
/*!40000 ALTER TABLE `flowengine_default_standard_statuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_default_standard_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_evaluator_descriptors`
--

DROP TABLE IF EXISTS `flowengine_evaluator_descriptors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_evaluator_descriptors` (
  `evaluatorID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  `evaluatorTypeID` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`evaluatorID`),
  KEY `FK_flowengine_evaluator_descriptors_1` (`queryID`),
  CONSTRAINT `FK_flowengine_evaluator_descriptors_1` FOREIGN KEY (`queryID`) REFERENCES `flowengine_query_descriptors` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=833 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_evaluator_descriptors`
--

LOCK TABLES `flowengine_evaluator_descriptors` WRITE;
/*!40000 ALTER TABLE `flowengine_evaluator_descriptors` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_evaluator_descriptors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_evaluators_target_queries`
--

DROP TABLE IF EXISTS `flowengine_evaluators_target_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_evaluators_target_queries` (
  `evaluatorID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`evaluatorID`,`queryID`),
  CONSTRAINT `FK_flowengine_evaluators_target_queries_1` FOREIGN KEY (`evaluatorID`) REFERENCES `flowengine_evaluator_descriptors` (`evaluatorID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_evaluators_target_queries`
--

LOCK TABLES `flowengine_evaluators_target_queries` WRITE;
/*!40000 ALTER TABLE `flowengine_evaluators_target_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_evaluators_target_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_external_message_attachments`
--

DROP TABLE IF EXISTS `flowengine_external_message_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_external_message_attachments` (
  `attachmentID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL,
  `size` int(10) unsigned NOT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `data` longblob NOT NULL,
  `messageID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`attachmentID`),
  KEY `FK_flowengine_external_message_attachments_1` (`messageID`),
  CONSTRAINT `FK_flowengine_external_message_attachments_1` FOREIGN KEY (`messageID`) REFERENCES `flowengine_external_messages` (`messageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_external_message_attachments`
--

LOCK TABLES `flowengine_external_message_attachments` WRITE;
/*!40000 ALTER TABLE `flowengine_external_message_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_external_message_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_external_message_read_receipts`
--

DROP TABLE IF EXISTS `flowengine_external_message_read_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_external_message_read_receipts` (
  `userID` int(10) unsigned NOT NULL,
  `messageID` int(10) unsigned NOT NULL,
  `read` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userID`,`messageID`),
  KEY `FK_flowengine_external_message_read_receipts_1` (`messageID`),
  CONSTRAINT `FK_flowengine_external_message_read_receipts_1` FOREIGN KEY (`messageID`) REFERENCES `flowengine_external_messages` (`messageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_external_message_read_receipts`
--

LOCK TABLES `flowengine_external_message_read_receipts` WRITE;
/*!40000 ALTER TABLE `flowengine_external_message_read_receipts` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_external_message_read_receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_external_messages`
--

DROP TABLE IF EXISTS `flowengine_external_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_external_messages` (
  `messageID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `poster` int(10) unsigned NOT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editor` int(10) unsigned DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `message` mediumtext NOT NULL,
  `flowInstanceID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`messageID`),
  KEY `FK_flowengine_external_messages_1` (`flowInstanceID`),
  CONSTRAINT `FK_flowengine_external_messages_1` FOREIGN KEY (`flowInstanceID`) REFERENCES `flowengine_flow_instances` (`flowInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_external_messages`
--

LOCK TABLES `flowengine_external_messages` WRITE;
/*!40000 ALTER TABLE `flowengine_external_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_external_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_actions`
--

DROP TABLE IF EXISTS `flowengine_flow_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_actions` (
  `actionID` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `required` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`actionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_actions`
--

LOCK TABLES `flowengine_flow_actions` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_actions` DISABLE KEYS */;
INSERT INTO `flowengine_flow_actions` VALUES ('com.nordicpeak.flowengine.FlowBrowserModule.save','Användare sparar en ansökan utan att skicka den',1),('com.nordicpeak.flowengine.FlowBrowserModule.submit','Användare skickar in ansökan',1),('com.nordicpeak.flowengine.UserFlowInstanceModule.submitcompletion','Användare skickar in komplettering',0);
/*!40000 ALTER TABLE `flowengine_flow_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_categories`
--

DROP TABLE IF EXISTS `flowengine_flow_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_categories` (
  `categoryID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `flowTypeID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`categoryID`) USING BTREE,
  KEY `FK_flowengine_flow_categories_1` (`flowTypeID`),
  CONSTRAINT `FK_flowengine_flow_categories_1` FOREIGN KEY (`flowTypeID`) REFERENCES `flowengine_flow_types` (`flowTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_categories`
--

LOCK TABLES `flowengine_flow_categories` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_checks`
--

DROP TABLE IF EXISTS `flowengine_flow_checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_checks` (
  `flowID` int(10) unsigned NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`flowID`,`value`) USING BTREE,
  CONSTRAINT `FK_flowengine_flow_checks_1` FOREIGN KEY (`flowID`) REFERENCES `flowengine_flows` (`flowID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_checks`
--

LOCK TABLES `flowengine_flow_checks` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_checks` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_checks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_families`
--

DROP TABLE IF EXISTS `flowengine_flow_families`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_families` (
  `flowFamilyID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `versionCount` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowFamilyID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_families`
--

LOCK TABLES `flowengine_flow_families` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_families` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_families` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_family_favourites`
--

DROP TABLE IF EXISTS `flowengine_flow_family_favourites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_family_favourites` (
  `flowFamilyID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowFamilyID`,`userID`),
  CONSTRAINT `FK_flowengine_flow_family_favourites_1` FOREIGN KEY (`flowFamilyID`) REFERENCES `flowengine_flow_families` (`flowFamilyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_family_favourites`
--

LOCK TABLES `flowengine_flow_family_favourites` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_family_favourites` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_family_favourites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_family_manager_groups`
--

DROP TABLE IF EXISTS `flowengine_flow_family_manager_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_family_manager_groups` (
  `flowFamilyID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowFamilyID`,`groupID`),
  CONSTRAINT `FK_flowengine_flow_family_manager_groups_1` FOREIGN KEY (`flowFamilyID`) REFERENCES `flowengine_flow_families` (`flowFamilyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_family_manager_groups`
--

LOCK TABLES `flowengine_flow_family_manager_groups` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_family_manager_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_family_manager_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_family_manager_users`
--

DROP TABLE IF EXISTS `flowengine_flow_family_manager_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_family_manager_users` (
  `flowFamilyID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowFamilyID`,`userID`),
  CONSTRAINT `FK_flowengine_flow_family_manager_users_1` FOREIGN KEY (`flowFamilyID`) REFERENCES `flowengine_flow_families` (`flowFamilyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_family_manager_users`
--

LOCK TABLES `flowengine_flow_family_manager_users` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_family_manager_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_family_manager_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_instance_bookmarks`
--

DROP TABLE IF EXISTS `flowengine_flow_instance_bookmarks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_instance_bookmarks` (
  `userID` int(10) unsigned NOT NULL,
  `flowInstanceID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userID`,`flowInstanceID`),
  KEY `FK_flowengine_flow_instance_bookmarks_1` (`flowInstanceID`),
  CONSTRAINT `FK_flowengine_flow_instance_bookmarks_1` FOREIGN KEY (`flowInstanceID`) REFERENCES `flowengine_flow_instances` (`flowInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_instance_bookmarks`
--

LOCK TABLES `flowengine_flow_instance_bookmarks` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_instance_bookmarks` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_instance_bookmarks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_instance_event_attributes`
--

DROP TABLE IF EXISTS `flowengine_flow_instance_event_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_instance_event_attributes` (
  `eventID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`eventID`,`name`),
  CONSTRAINT `FK_flowengine_flow_instance_event_attributes_1` FOREIGN KEY (`eventID`) REFERENCES `flowengine_flow_instance_events` (`eventID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_instance_event_attributes`
--

LOCK TABLES `flowengine_flow_instance_event_attributes` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_instance_event_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_instance_event_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_instance_events`
--

DROP TABLE IF EXISTS `flowengine_flow_instance_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_instance_events` (
  `eventID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `flowInstanceID` int(10) unsigned NOT NULL,
  `eventType` varchar(45) NOT NULL,
  `status` varchar(255) NOT NULL,
  `statusDescription` text,
  `details` varchar(255) DEFAULT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `poster` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`eventID`),
  KEY `FK_flowengine_flow_instance_events_1` (`flowInstanceID`),
  CONSTRAINT `FK_flowengine_flow_instance_events_1` FOREIGN KEY (`flowInstanceID`) REFERENCES `flowengine_flow_instances` (`flowInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_instance_events`
--

LOCK TABLES `flowengine_flow_instance_events` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_instance_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_instance_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_instance_managers`
--

DROP TABLE IF EXISTS `flowengine_flow_instance_managers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_instance_managers` (
  `flowInstanceID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowInstanceID`,`userID`),
  CONSTRAINT `FK_flowengine_flow_instance_managers_1` FOREIGN KEY (`flowInstanceID`) REFERENCES `flowengine_flow_instances` (`flowInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_instance_managers`
--

LOCK TABLES `flowengine_flow_instance_managers` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_instance_managers` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_instance_managers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_instances`
--

DROP TABLE IF EXISTS `flowengine_flow_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_instances` (
  `flowInstanceID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `poster` int(10) unsigned DEFAULT NULL,
  `added` timestamp NULL DEFAULT NULL,
  `editor` int(10) unsigned DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `fullyPopulated` tinyint(1) NOT NULL,
  `flowID` int(10) unsigned NOT NULL,
  `stepID` int(10) unsigned NOT NULL,
  `statusID` int(10) unsigned NOT NULL,
  `lastStatusChange` timestamp NULL DEFAULT NULL,
  `profileID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`flowInstanceID`),
  KEY `FK_flowengine_flow_instances_1` (`flowID`),
  KEY `FK_flowengine_flow_instances_2` (`stepID`),
  KEY `FK_flowengine_flow_instances_3` (`statusID`) USING BTREE,
  CONSTRAINT `FK_flowengine_flow_instances_1` FOREIGN KEY (`flowID`) REFERENCES `flowengine_flows` (`flowID`),
  CONSTRAINT `FK_flowengine_flow_instances_2` FOREIGN KEY (`stepID`) REFERENCES `flowengine_steps` (`stepID`),
  CONSTRAINT `FK_flowengine_flow_instances_3` FOREIGN KEY (`statusID`) REFERENCES `flowengine_flow_statuses` (`statusID`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_instances`
--

LOCK TABLES `flowengine_flow_instances` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_statuses`
--

DROP TABLE IF EXISTS `flowengine_flow_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_statuses` (
  `statusID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `managingTime` int(10) unsigned DEFAULT NULL,
  `isUserMutable` tinyint(1) NOT NULL,
  `isAdminMutable` tinyint(1) NOT NULL,
  `contentType` varchar(45) NOT NULL,
  `flowID` int(10) unsigned NOT NULL,
  `isUserDeletable` tinyint(1) NOT NULL,
  `isAdminDeletable` tinyint(1) NOT NULL,
  PRIMARY KEY (`statusID`) USING BTREE,
  KEY `FK_flowengine_flow_states_1` (`flowID`),
  CONSTRAINT `FK_flowengine_flow_statuses_1` FOREIGN KEY (`flowID`) REFERENCES `flowengine_flows` (`flowID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_statuses`
--

LOCK TABLES `flowengine_flow_statuses` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_statuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_tags`
--

DROP TABLE IF EXISTS `flowengine_flow_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_tags` (
  `flowID` int(10) unsigned NOT NULL,
  `tag` varchar(255) NOT NULL,
  PRIMARY KEY (`flowID`,`tag`),
  CONSTRAINT `FK_flowengine_flow_tags_1` FOREIGN KEY (`flowID`) REFERENCES `flowengine_flows` (`flowID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_tags`
--

LOCK TABLES `flowengine_flow_tags` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_type_admin_groups`
--

DROP TABLE IF EXISTS `flowengine_flow_type_admin_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_type_admin_groups` (
  `flowTypeID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowTypeID`,`groupID`),
  CONSTRAINT `FK_flowengine_flow_type_admin_groups_1` FOREIGN KEY (`flowTypeID`) REFERENCES `flowengine_flow_types` (`flowTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_type_admin_groups`
--

LOCK TABLES `flowengine_flow_type_admin_groups` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_type_admin_groups` DISABLE KEYS */;
INSERT INTO `flowengine_flow_type_admin_groups` VALUES (4,6);
/*!40000 ALTER TABLE `flowengine_flow_type_admin_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_type_admin_users`
--

DROP TABLE IF EXISTS `flowengine_flow_type_admin_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_type_admin_users` (
  `flowTypeID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`flowTypeID`,`userID`),
  CONSTRAINT `FK_flowengine_flow_type_admin_users_1` FOREIGN KEY (`flowTypeID`) REFERENCES `flowengine_flow_types` (`flowTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_type_admin_users`
--

LOCK TABLES `flowengine_flow_type_admin_users` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_type_admin_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_type_admin_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_type_allowed_queries`
--

DROP TABLE IF EXISTS `flowengine_flow_type_allowed_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_type_allowed_queries` (
  `flowTypeID` int(10) unsigned NOT NULL,
  `queryTypeID` varchar(255) NOT NULL,
  PRIMARY KEY (`flowTypeID`,`queryTypeID`),
  CONSTRAINT `FK_flowengine_flow_type_allowed_queries_1` FOREIGN KEY (`flowTypeID`) REFERENCES `flowengine_flow_types` (`flowTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_type_allowed_queries`
--

LOCK TABLES `flowengine_flow_type_allowed_queries` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_type_allowed_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flow_type_allowed_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flow_types`
--

DROP TABLE IF EXISTS `flowengine_flow_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flow_types` (
  `flowTypeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`flowTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flow_types`
--

LOCK TABLES `flowengine_flow_types` WRITE;
/*!40000 ALTER TABLE `flowengine_flow_types` DISABLE KEYS */;
INSERT INTO `flowengine_flow_types` VALUES (4,'Demo');
/*!40000 ALTER TABLE `flowengine_flow_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_flows`
--

DROP TABLE IF EXISTS `flowengine_flows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_flows` (
  `flowID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `shortDescription` text NOT NULL,
  `longDescription` longtext,
  `submittedMessage` longtext,
  `iconFileName` varchar(255) DEFAULT NULL,
  `icon` blob,
  `publishDate` date DEFAULT NULL,
  `unPublishDate` date DEFAULT NULL,
  `flowTypeID` int(10) unsigned NOT NULL,
  `categoryID` int(10) unsigned DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `usePreview` tinyint(1) NOT NULL,
  `flowFamilyID` int(10) unsigned NOT NULL,
  `version` int(10) unsigned NOT NULL,
  `requireAuthentication` tinyint(1) NOT NULL,
  `requireSigning` tinyint(1) unsigned NOT NULL,
  `externalLink` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`flowID`),
  KEY `FK_flowengine_flows_1` (`flowTypeID`),
  KEY `FK_flowengine_flows_2` (`categoryID`) USING BTREE,
  KEY `FK_flowengine_flows_3` (`flowFamilyID`),
  CONSTRAINT `FK_flowengine_flows_1` FOREIGN KEY (`flowTypeID`) REFERENCES `flowengine_flow_types` (`flowTypeID`),
  CONSTRAINT `FK_flowengine_flows_2` FOREIGN KEY (`categoryID`) REFERENCES `flowengine_flow_categories` (`categoryID`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FK_flowengine_flows_3` FOREIGN KEY (`flowFamilyID`) REFERENCES `flowengine_flow_families` (`flowFamilyID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_flows`
--

LOCK TABLES `flowengine_flows` WRITE;
/*!40000 ALTER TABLE `flowengine_flows` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_flows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_internal_message_attachments`
--

DROP TABLE IF EXISTS `flowengine_internal_message_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_internal_message_attachments` (
  `attachmentID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL,
  `size` int(10) unsigned NOT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `data` longblob NOT NULL,
  `messageID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`attachmentID`),
  KEY `FK_flowengine_internal_message_attachments_1` (`messageID`),
  CONSTRAINT `FK_flowengine_internal_message_attachments_1` FOREIGN KEY (`messageID`) REFERENCES `flowengine_internal_messages` (`messageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_internal_message_attachments`
--

LOCK TABLES `flowengine_internal_message_attachments` WRITE;
/*!40000 ALTER TABLE `flowengine_internal_message_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_internal_message_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_internal_message_read_receipts`
--

DROP TABLE IF EXISTS `flowengine_internal_message_read_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_internal_message_read_receipts` (
  `userID` int(10) unsigned NOT NULL,
  `messageID` int(10) unsigned NOT NULL,
  `read` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userID`,`messageID`),
  KEY `FK_flowengine_internal_message_read_receipts_1` (`messageID`),
  CONSTRAINT `FK_flowengine_internal_message_read_receipts_1` FOREIGN KEY (`messageID`) REFERENCES `flowengine_internal_messages` (`messageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_internal_message_read_receipts`
--

LOCK TABLES `flowengine_internal_message_read_receipts` WRITE;
/*!40000 ALTER TABLE `flowengine_internal_message_read_receipts` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_internal_message_read_receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_internal_messages`
--

DROP TABLE IF EXISTS `flowengine_internal_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_internal_messages` (
  `messageID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `poster` int(10) unsigned NOT NULL,
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `editor` int(10) unsigned DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `message` mediumtext NOT NULL,
  `flowInstanceID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`messageID`),
  KEY `FK_flowengine_internal_messages_1` (`flowInstanceID`),
  CONSTRAINT `FK_flowengine_internal_messages_1` FOREIGN KEY (`flowInstanceID`) REFERENCES `flowengine_flow_instances` (`flowInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_internal_messages`
--

LOCK TABLES `flowengine_internal_messages` WRITE;
/*!40000 ALTER TABLE `flowengine_internal_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_internal_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_query_descriptors`
--

DROP TABLE IF EXISTS `flowengine_query_descriptors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_query_descriptors` (
  `queryID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  `defaultQueryState` varchar(45) NOT NULL,
  `stepID` int(10) unsigned NOT NULL,
  `queryTypeID` varchar(255) NOT NULL,
  `exported` tinyint(1) NOT NULL,
  PRIMARY KEY (`queryID`),
  KEY `FK_flowengine_query_descriptors_1` (`stepID`),
  CONSTRAINT `FK_flowengine_query_descriptors_1` FOREIGN KEY (`stepID`) REFERENCES `flowengine_steps` (`stepID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2128 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_query_descriptors`
--

LOCK TABLES `flowengine_query_descriptors` WRITE;
/*!40000 ALTER TABLE `flowengine_query_descriptors` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_query_descriptors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_query_instance_descriptors`
--

DROP TABLE IF EXISTS `flowengine_query_instance_descriptors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_query_instance_descriptors` (
  `queryInstanceID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryState` varchar(45) NOT NULL,
  `populated` tinyint(1) NOT NULL,
  `flowInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_flowengine_query_instance_descriptors_1` (`flowInstanceID`),
  KEY `FK_flowengine_query_instance_descriptors_2` (`queryID`),
  CONSTRAINT `FK_flowengine_query_instance_descriptors_1` FOREIGN KEY (`flowInstanceID`) REFERENCES `flowengine_flow_instances` (`flowInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_flowengine_query_instance_descriptors_2` FOREIGN KEY (`queryID`) REFERENCES `flowengine_query_descriptors` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4571 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_query_instance_descriptors`
--

LOCK TABLES `flowengine_query_instance_descriptors` WRITE;
/*!40000 ALTER TABLE `flowengine_query_instance_descriptors` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_query_instance_descriptors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_standard_statuses`
--

DROP TABLE IF EXISTS `flowengine_standard_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_standard_statuses` (
  `statusID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `managingTime` int(10) unsigned DEFAULT NULL,
  `isUserMutable` tinyint(1) NOT NULL,
  `isUserDeletable` tinyint(1) NOT NULL,
  `isAdminMutable` tinyint(1) NOT NULL,
  `isAdminDeletable` tinyint(1) NOT NULL,
  `contentType` varchar(45) NOT NULL,
  PRIMARY KEY (`statusID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_standard_statuses`
--

LOCK TABLES `flowengine_standard_statuses` WRITE;
/*!40000 ALTER TABLE `flowengine_standard_statuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_standard_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_steps`
--

DROP TABLE IF EXISTS `flowengine_steps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_steps` (
  `stepID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  `flowID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`stepID`),
  KEY `FK_flowengine_steps_1` (`flowID`),
  CONSTRAINT `FK_flowengine_steps_1` FOREIGN KEY (`flowID`) REFERENCES `flowengine_flows` (`flowID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=430 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_steps`
--

LOCK TABLES `flowengine_steps` WRITE;
/*!40000 ALTER TABLE `flowengine_steps` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_steps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_text_tags`
--

DROP TABLE IF EXISTS `flowengine_text_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_text_tags` (
  `textTagID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `defaultValue` text,
  `type` varchar(10) NOT NULL,
  PRIMARY KEY (`textTagID`),
  UNIQUE KEY `Index_2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_text_tags`
--

LOCK TABLES `flowengine_text_tags` WRITE;
/*!40000 ALTER TABLE `flowengine_text_tags` DISABLE KEYS */;
INSERT INTO `flowengine_text_tags` VALUES (4,'Testtagg','Testtagg för att testa','En exempeltext','TEXTFIELD'),(5,'kommun','Sundsvalls kommun','<p>Demo</p>','EDITOR');
/*!40000 ALTER TABLE `flowengine_text_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowengine_user_organizations`
--

DROP TABLE IF EXISTS `flowengine_user_organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flowengine_user_organizations` (
  `organizationID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `organizationNumber` varchar(16) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zipCode` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `contactByLetter` tinyint(1) unsigned NOT NULL,
  `contactByEmail` tinyint(1) unsigned NOT NULL,
  `contactBySMS` tinyint(1) unsigned NOT NULL,
  `contactByPhone` tinyint(1) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`organizationID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowengine_user_organizations`
--

LOCK TABLES `flowengine_user_organizations` WRITE;
/*!40000 ALTER TABLE `flowengine_user_organizations` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowengine_user_organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minimal_user_attributes`
--

DROP TABLE IF EXISTS `minimal_user_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minimal_user_attributes` (
  `userID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(1024) NOT NULL,
  PRIMARY KEY (`userID`,`name`),
  CONSTRAINT `FK_minimal_user_attributes_1` FOREIGN KEY (`userID`) REFERENCES `minimal_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minimal_user_attributes`
--

LOCK TABLES `minimal_user_attributes` WRITE;
/*!40000 ALTER TABLE `minimal_user_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `minimal_user_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minimal_user_groups`
--

DROP TABLE IF EXISTS `minimal_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minimal_user_groups` (
  `userID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  CONSTRAINT `FK_minimal_user_groups_1` FOREIGN KEY (`userID`) REFERENCES `minimal_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minimal_user_groups`
--

LOCK TABLES `minimal_user_groups` WRITE;
/*!40000 ALTER TABLE `minimal_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `minimal_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minimal_users`
--

DROP TABLE IF EXISTS `minimal_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minimal_users` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastlogin` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `language` varchar(76) DEFAULT NULL,
  `preferedDesign` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=2092 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minimal_users`
--

LOCK TABLES `minimal_users` WRITE;
/*!40000 ALTER TABLE `minimal_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `minimal_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_module_aliases`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_backgroundmodulealiases_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_aliases`
--

LOCK TABLES `openhierarchy_background_module_aliases` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_aliases` DISABLE KEYS */;
INSERT INTO `openhierarchy_background_module_aliases` VALUES (1,'*',9),(1,'exclude:administration*',0),(1,'exclude:browser/flow*',3),(1,'exclude:browser/flowoverview*',1),(1,'exclude:browser/submitted*',4),(1,'exclude:flowadmin*',5),(1,'exclude:flowinstanceadmin*',6),(1,'exclude:flowinstances*',2),(1,'exclude:myorganizations*',8),(1,'exclude:mysettings*',7),(2,'*',7),(2,'exclude:administration*',0),(2,'exclude:browser/flow*',3),(2,'exclude:browser/flowoverview*',1),(2,'exclude:flowadmin*',5),(2,'exclude:flowinstanceadmin*',6),(2,'exclude:flowinstances*',2),(2,'exclude:mysettings*',4),(3,'*',9),(3,'exclude:administration*',0),(3,'exclude:browser/flow*',3),(3,'exclude:browser/flowoverview*',1),(3,'exclude:browser/submitted*',4),(3,'exclude:flowadmin*',5),(3,'exclude:flowinstanceadmin*',6),(3,'exclude:flowinstances*',2),(3,'exclude:myorganizations*',8),(3,'exclude:mysettings*',7),(4,'*',0),(5,'exclude:flowinstances/flowinstance*',0),(5,'exclude:flowinstances/overview*',1),(5,'exclude:flowinstances/preview*',2),(5,'flowinstances*',3),(6,'mysettings*',0),(7,'exclude:flowinstances/flowinstance*',0),(7,'exclude:flowinstances/overview*',1),(7,'exclude:flowinstances/preview*',2),(7,'flowinstances*',5),(7,'myorganizations*',4),(7,'mysettings*',3),(8,'*',0),(10,'*',0);
/*!40000 ALTER TABLE `openhierarchy_background_module_aliases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_module_groups`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_backgroundmodulegroups_2` (`groupID`),
  CONSTRAINT `FK_backgroundmodulegroups_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_groups`
--

LOCK TABLES `openhierarchy_background_module_groups` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_module_settings`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_backgroundmodulesettings_1` (`moduleID`),
  CONSTRAINT `FK_backgroundmodulesettings_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_settings`
--

LOCK TABLES `openhierarchy_background_module_settings` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_settings` DISABLE KEYS */;
INSERT INTO `openhierarchy_background_module_settings` VALUES (67,8,'nrOfEvents','5'),(77,10,'profileSettingDescription','Sidhuvudet för den aktuella profilen'),(78,10,'profileSettingID','bgmodule-4'),(79,10,'profileSettingName','Sidhuvud'),(93,5,'mode','SHOW'),(94,4,'editFavouritesAlias','/mysettings'),(95,4,'mode','SHOW'),(96,2,'mode','SHOW'),(97,6,'mode','EDIT'),(120,7,'sectionID','5'),(161,3,'flowCount','5'),(162,3,'interval','72'),(173,1,'adminGroups','10'),(174,1,'adminGroups','6'),(175,1,'cssClass','htmloutputmodule'),(176,1,'html','<section class=\"clearboth\">\r\n	<div class>Text goes here</div>\r\n</section>\r\n'),(177,1,'htmlRequired','true');
/*!40000 ALTER TABLE `openhierarchy_background_module_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_module_slots`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_slots` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `slot` varchar(255) NOT NULL,
  PRIMARY KEY (`moduleID`,`slot`),
  CONSTRAINT `FK_backgroundmoduleslots_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_slots`
--

LOCK TABLES `openhierarchy_background_module_slots` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_slots` DISABLE KEYS */;
INSERT INTO `openhierarchy_background_module_slots` VALUES (1,'right-content-container.news'),(2,'right-content-container.favourites'),(3,'right-content-container.popular'),(4,'#menu-container.favourites'),(5,'left-content-container.favourites'),(6,'right-content-container.favourites'),(7,'left-content-container.mypagesmenu'),(8,'sectionmenu-content-container.newevents'),(10,'header.logotype');
/*!40000 ALTER TABLE `openhierarchy_background_module_slots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_module_users`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_backgroundmoduleusers_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_users`
--

LOCK TABLES `openhierarchy_background_module_users` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_modules`
--

DROP TABLE IF EXISTS `openhierarchy_background_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `xslPath` text,
  `xslPathType` varchar(255) DEFAULT NULL,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `dataSourceID` int(10) unsigned DEFAULT NULL,
  `staticContentPackage` varchar(255) DEFAULT NULL,
  `priority` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`),
  KEY `FK_backgroundmodules_1` (`sectionID`),
  KEY `FK_backgroundmodules_2` (`dataSourceID`),
  CONSTRAINT `FK_backgroundmodules_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_backgroundmodules_2` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_modules`
--

LOCK TABLES `openhierarchy_background_modules` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_modules` DISABLE KEYS */;
INSERT INTO `openhierarchy_background_modules` VALUES (1,'se.unlogic.hierarchy.backgroundmodules.htmloutput.HTMLOutputModule','Aktuellt','HTMLOutputModule.sv.xsl','Classpath',1,1,1,1,1,NULL,'staticcontent',0),(2,'com.nordicpeak.flowengine.UserFavouriteBackgroundModule','Mina favoriter','UserFavouriteBackgroundModule.sv.xsl','Classpath',0,1,1,0,1,NULL,'staticcontent',1),(3,'com.nordicpeak.flowengine.PopularFlowFamiliesModule','Populära e-tjänster','PopularFlowFamiliesModule.sv.xsl','Classpath',0,1,1,1,1,NULL,'staticcontent',2),(4,'com.nordicpeak.flowengine.UserFavouriteBackgroundModule','Mina favoriter','UserFavouriteMenuModule.sv.xsl','Classpath',0,1,1,0,1,NULL,'staticcontent',0),(5,'com.nordicpeak.flowengine.UserFavouriteBackgroundModule','Mina favoriter','UserFavouriteBackgroundModule.sv.xsl','Classpath',0,1,1,0,1,NULL,'staticcontent',1),(6,'com.nordicpeak.flowengine.UserFavouriteBackgroundModule','Redigera favoriter','UserFavouriteBackgroundModule.sv.xsl','Classpath',0,1,1,0,1,NULL,'staticcontent',1),(7,'com.nordicpeak.flowengine.UserFlowInstanceMenuModule','Mina sidor (Meny)','UserFlowInstanceMenuModuleTemplates.xsl','Classpath',0,1,1,1,1,NULL,NULL,0),(8,'com.nordicpeak.flowengine.NewEventsBackgroundModule','Mina meddelanden (bakgrund)','NewEventsBackgroundModule.sv.xsl','Classpath',0,1,1,1,1,NULL,'staticcontent',0),(10,'se.unlogic.openhierarchy.foregroundmodules.siteprofile.SiteProfileHTMLBackgroundModule','Sidhuvud','',NULL,1,1,1,1,1,NULL,'',1);
/*!40000 ALTER TABLE `openhierarchy_background_modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_data_sources`
--

DROP TABLE IF EXISTS `openhierarchy_data_sources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_data_sources` (
  `dataSourceID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(45) NOT NULL DEFAULT '',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `driver` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL DEFAULT '',
  `logAbandoned` tinyint(1) DEFAULT '0',
  `removeAbandoned` tinyint(1) DEFAULT '0',
  `removeTimeout` int(10) unsigned DEFAULT '30',
  `testOnBorrow` tinyint(1) DEFAULT '0',
  `validationQuery` varchar(255) DEFAULT 'SELECT 1',
  `maxActive` int(10) unsigned DEFAULT '30',
  `maxIdle` int(10) unsigned DEFAULT '8',
  `minIdle` int(10) unsigned DEFAULT '0',
  `maxWait` int(10) unsigned DEFAULT '0',
  `defaultCatalog` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dataSourceID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_data_sources`
--

LOCK TABLES `openhierarchy_data_sources` WRITE;
/*!40000 ALTER TABLE `openhierarchy_data_sources` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_data_sources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_filter_module_aliases`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_filtermodulealiases_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_module_aliases`
--

LOCK TABLES `openhierarchy_filter_module_aliases` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_module_aliases` DISABLE KEYS */;
INSERT INTO `openhierarchy_filter_module_aliases` VALUES (2,'*',0);
/*!40000 ALTER TABLE `openhierarchy_filter_module_aliases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_filter_module_groups`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_filtermodulegroups_2` (`groupID`),
  CONSTRAINT `FK_filtermodulegroups_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_module_groups`
--

LOCK TABLES `openhierarchy_filter_module_groups` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_module_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_filter_module_settings`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_filtermodulesettings_1` (`moduleID`),
  CONSTRAINT `FK_filtermodulesettings_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_module_settings`
--

LOCK TABLES `openhierarchy_filter_module_settings` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_module_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_filter_module_users`
--

DROP TABLE IF EXISTS `openhierarchy_filter_module_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_filtermoduleusers_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_module_users`
--

LOCK TABLES `openhierarchy_filter_module_users` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_module_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_filter_modules`
--

DROP TABLE IF EXISTS `openhierarchy_filter_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_filter_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `dataSourceID` int(10) unsigned DEFAULT NULL,
  `priority` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`),
  KEY `FK_filtermodules_1` (`dataSourceID`),
  CONSTRAINT `FK_filtermodules_1` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_modules`
--

LOCK TABLES `openhierarchy_filter_modules` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_modules` DISABLE KEYS */;
INSERT INTO `openhierarchy_filter_modules` VALUES (2,'se.unlogic.hierarchy.filtermodules.login.LoginTriggerModule','LoginTriggerModule',1,0,0,1,NULL,0);
/*!40000 ALTER TABLE `openhierarchy_filter_modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_foreground_module_groups`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_modulegroups_2` (`groupID`),
  CONSTRAINT `FK_modulegroups_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_module_groups`
--

LOCK TABLES `openhierarchy_foreground_module_groups` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_groups` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_module_groups` VALUES (37,6),(39,6),(69,6),(70,6),(71,6),(95,6),(102,6),(123,6),(133,6),(150,6),(155,6),(160,6),(165,6),(123,10),(133,10),(150,10),(160,10),(165,10),(133,11);
/*!40000 ALTER TABLE `openhierarchy_foreground_module_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_foreground_module_settings`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_modulesettings_1` (`moduleID`),
  CONSTRAINT `FK_modulesettings_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6260 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_module_settings`
--

LOCK TABLES `openhierarchy_foreground_module_settings` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_settings` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_module_settings` VALUES (842,84,'menuItemType','MENUITEM'),(864,64,'menuItemType','MENUITEM'),(935,56,'menuItemType','MENUITEM'),(998,70,'menuItemType','MENUITEM'),(1002,37,'menuItemType','MENUITEM'),(1320,102,'cssPath','/css/fck.css'),(1321,102,'menuItemType','MENUITEM'),(3102,9,'menuItemType','SECTION'),(3103,9,'redirectURL','/'),(3104,112,'menuItemType','MENUITEM'),(3199,125,'menuItemType','MENUITEM'),(4089,146,'diskThreshold','100'),(4090,146,'filestore','/path/not/set'),(4091,146,'menuItemType','MENUITEM'),(4092,146,'ramThreshold','500'),(4723,155,'emailFieldMode','OPTIONAL'),(4724,155,'formStyleSheet','MinimalUserProviderForm.sv.xsl'),(4725,155,'includeDebugData','false'),(4726,155,'menuItemType','MENUITEM'),(4727,155,'passwordAlgorithm','SHA-1'),(4728,155,'passwordFieldMode','DISABLED'),(4729,155,'priority','0'),(4730,155,'supportedAttributes','citizenIdentifier'),(4731,155,'userTypeName','E-legitimation användare (kan endast logga in med e-leg)'),(4732,155,'usernameFieldMode','DISABLED'),(4798,71,'formStyleSheet','SimpleUserProviderForm.sv.xsl'),(4799,71,'includeDebugData','false'),(4800,71,'menuItemType','MENUITEM'),(4801,71,'passwordAlgorithm','SHA-1'),(4802,71,'priority','0'),(4803,71,'supportedAttributes','citizenIdentifier'),(4804,71,'userTypeName','Användare med separat fält för användarnamn och lösenord'),(4809,61,'maxFileSize','1'),(4810,61,'maxRequestSize','5'),(4811,61,'menuItemType','MENUITEM'),(4812,61,'ramThreshold','500'),(4887,132,'menuItemType','MENUITEM'),(5218,133,'highPriorityThreshold','90'),(5219,133,'maxHitCount','20'),(5220,133,'maxRequestSize','1000'),(5221,133,'maxUnfilteredHitCount','100'),(5222,133,'mediumPriorityThreshold','60'),(5223,133,'menuItemType','SECTION'),(5224,133,'ramThreshold','500'),(5615,161,'defaultLogotype','classpath://com/nordicpeak/flowengine/pdf/staticcontent/pics/logo.png'),(5616,161,'includedFonts','/com/nordicpeak/flowengine/pdf/fonts/SourceSansPro-Bold.ttf\r\n/com/nordicpeak/flowengine/pdf/fonts/SourceSansPro-It.ttf\r\n/com/nordicpeak/flowengine/pdf/fonts/SourceSansPro-Regular.ttf\r\n/com/nordicpeak/flowengine/pdf/fonts/SourceSansPro-Semibold.ttf'),(5617,161,'menuItemType','MENUITEM'),(5618,161,'pdfDir','/path/not/set'),(5619,161,'pdfStyleSheet','FlowInstancePDF.sv.xsl'),(5620,161,'supportedActionIDs','com.nordicpeak.flowengine.FlowBrowserModule.submit'),(5621,161,'tempDir','/path/not/set'),(5622,161,'xhtmlDebug','false'),(5623,161,'xhtmlDebugFile','/path/not/set'),(5624,161,'xmlDebug','false'),(5625,161,'xmlDebugFile','/path/not/set'),(5638,113,'ckConnectorModuleAlias','/fileconnector'),(5639,113,'cssPath','/css/fck.css'),(5640,113,'includeDebugData','false'),(5641,113,'menuItemType','MENUITEM'),(5642,113,'pdfStyleSheet','CheckboxQueryPDF.sv.xsl'),(5643,113,'queryStyleSheet','CheckboxQuery.sv.xsl'),(5644,113,'useCKEditorForDescription','true'),(5652,116,'ckConnectorModuleAlias','/fileconnector'),(5653,116,'cssPath','/css/fck.css'),(5654,116,'includeDebugData','false'),(5655,116,'menuItemType','MENUITEM'),(5656,116,'pdfStyleSheet','DropDownQueryPDF.sv.xsl'),(5657,116,'queryStyleSheet','DropDownQuery.sv.xsl'),(5658,116,'useCKEditorForDescription','false'),(5659,119,'ckConnectorModuleAlias','/fileconnector'),(5660,119,'cleanupInterval','10'),(5661,119,'cssPath','/css/fck.css'),(5662,119,'fileStore','/path/not/set'),(5663,119,'includeDebugData','true'),(5664,119,'menuItemType','MENUITEM'),(5665,119,'pdfStyleSheet','FileUploadQueryPDF.sv.xsl'),(5666,119,'queryStyleSheet','FileUploadQuery.sv.xsl'),(5667,119,'tempFileStore','/path/not/set'),(5668,119,'useCKEditorForDescription','false'),(5669,149,'ckConnectorModuleAlias','/fileconnector'),(5670,149,'cssPath','/css/fck.css'),(5671,149,'includeDebugData','false'),(5672,149,'menuItemType','MENUITEM'),(5673,149,'pdfStyleSheet','OrganizationDetailQueryPDF.sv.xsl'),(5674,149,'queryStyleSheet','OrganizationDetailQuery.sv.xsl'),(5675,149,'useCKEditorForDescription','false'),(5676,131,'ckConnectorModuleAlias','/fileconnector'),(5677,131,'cssPath','/css/fck.css'),(5678,131,'includeDebugData','false'),(5679,131,'menuItemType','MENUITEM'),(5680,131,'pdfStyleSheet','ContactDetailQueryPDF.sv.xsl'),(5681,131,'queryStyleSheet','ContactDetailQuery.sv.xsl'),(5682,131,'useCKEditorForDescription','false'),(5683,115,'ckConnectorModuleAlias','/fileconnector'),(5684,115,'cssPath','/css/fck.css'),(5685,115,'includeDebugData','false'),(5686,115,'menuItemType','MENUITEM'),(5687,115,'pdfStyleSheet','RadioButtonQueryPDF.sv.xsl'),(5688,115,'queryStyleSheet','RadioButtonQuery.sv.xsl'),(5689,115,'useCKEditorForDescription','true'),(5696,117,'ckConnectorModuleAlias','/fileconnector'),(5697,117,'cssPath','/css/style.css'),(5698,117,'includeDebugData','false'),(5699,117,'menuItemType','MENUITEM'),(5700,117,'pdfStyleSheet','TextAreaQueryPDF.sv.xsl'),(5701,117,'queryStyleSheet','TextAreaQuery.sv.xsl'),(5702,117,'useCKEditorForDescription','false'),(5703,118,'ckConnectorModuleAlias','/fileconnector'),(5704,118,'includeDebugData','false'),(5705,118,'menuItemType','MENUITEM'),(5706,118,'pdfStyleSheet','TextFieldQueryPDF.sv.xsl'),(5707,118,'queryStyleSheet','TextFieldQuery.sv.xsl'),(5708,118,'useCKEditorForDescription','false'),(5726,69,'menuItemType','MENUITEM'),(5727,160,'cssPath','/css/fck.css'),(5728,160,'menuItemType','MENUITEM'),(5729,150,'menuItemType','MENUITEM'),(5797,107,'menuItemType','MENUITEM'),(5798,110,'connectionTimeout','10000'),(5799,110,'daoFactoryClass','se.unlogic.hierarchy.foregroundmodules.mailsenders.persisting.daos.mysql.MySQLMailDAOFactory'),(5800,110,'databaseID','3'),(5801,110,'exceptionInterval','60000'),(5802,110,'host','not.set'),(5803,110,'maxExceptionCount','5'),(5804,110,'maxQueueSize','50'),(5805,110,'maxResendCount','3'),(5806,110,'menuItemType','MENUITEM'),(5807,110,'noWorkInterval','10000'),(5808,110,'poolSize','10'),(5809,110,'port','25'),(5810,110,'priority','0'),(5811,110,'queueFullInterval','3000'),(5812,110,'resendInterval','30'),(5813,110,'shutdownTimeout','60000'),(5814,110,'socketTimeout','600000'),(5815,110,'useAuth','false'),(6162,165,'cssPath','/css/style.css'),(6163,165,'diskThreshold','100'),(6164,165,'fileStore','/path/not/set'),(6165,165,'menuItemType','MENUITEM'),(6166,165,'ramThreshold','500'),(6167,121,'maxRequestSize','1000'),(6168,121,'menuItemType','SECTION'),(6169,121,'ramThreshold','500'),(6170,39,'csspath','/css/fck.css'),(6171,39,'diskThreshold','100'),(6172,39,'filestore','/path/not/set'),(6173,39,'pageViewModuleAlias','page'),(6174,39,'pageViewModuleName','Sidvisare'),(6175,39,'pageViewModuleXSLPath','PageViewModule.sv.xsl'),(6176,39,'pageViewModuleXSLPathType','Classpath'),(6184,145,'allowPasswordChanging','false'),(6185,145,'cancelRedirectURI','flowinstances'),(6186,145,'emailFieldMode','REQUIRED'),(6187,145,'firstnameFieldMode','DISABLED'),(6188,145,'lastnameFieldMode','DISABLED'),(6189,145,'menuItemType','MENUITEM'),(6190,145,'supportedAttributes','address:Adress:50\r\n\r\nzipCode:Postnummer:5\r\n\r\npostalAddress:Ort:50\r\n\r\nmobilePhone:Mobiltelefon:15\r\n\r\nphone:Telefonnummer:15\r\n\r\ncontactByLetter\r\n\r\ncontactByEmail\r\n\r\ncontactBySMS\r\n\r\ncontactByPhone'),(6191,145,'usernameFieldMode','HIDDEN'),(6192,123,'adminGroupIDs','6'),(6193,123,'maxFlowIconHeight','65'),(6194,123,'maxFlowIconWidth','65'),(6195,123,'maxRequestSize','1000'),(6196,123,'menuItemType','SECTION'),(6197,123,'ramThreshold','500'),(6198,123,'tempDir','/path/not/set'),(6210,159,'accountDisabledMessage','<h1>Konto ej aktiverat</h1>\r\n<p>Ditt anv&auml;ndarkonto &auml;r inte aktiverat, kontakta systemadministrat&ouml;ren f&ouml;r mer information.</p>\r\n'),(6211,159,'addToLoginHandler','true'),(6212,159,'idpMetadataFilePath','/path/not/set'),(6213,159,'logXML','true'),(6214,159,'loginFailedMessage','<h1>Inloggningen misslyckades</h1>\r\n<p>Prova igen eller kontakta systemadministrat&ouml;ren f&ouml;r mer information.</p>\r\n'),(6215,159,'menuItemType','MENUITEM'),(6216,159,'priority','100'),(6217,159,'spMetadataFilePath','/path/not/set'),(6218,159,'userAdapterModuleID','156'),(6219,159,'userSessionTimeout','60'),(6220,162,'authifyAPIKey','not set'),(6221,162,'authifySecretKey','not set'),(6222,162,'includeDebugData','false'),(6223,162,'menuItemType','MENUITEM'),(6224,162,'userSSNAttributeName','citizenIdentifier'),(6225,8,'adminTimeout','60'),(6226,8,'default','true'),(6227,8,'logoutModuleAliases','/logout\r\n/logout/logout'),(6228,8,'menuItemType','SECTION'),(6229,8,'priority','100'),(6230,8,'userTimeout','30'),(6231,95,'allowAdminAdministration','true'),(6232,95,'allowGroupAdministration','true'),(6233,95,'filteringField','FIRSTNAME'),(6234,95,'menuItemType','MENUITEM'),(6247,114,'flowTypeIDs','4'),(6248,114,'listAllFlowTypes','true'),(6249,114,'maxHitCount','10'),(6250,114,'maxRequestSize','1000'),(6251,114,'menuItemType','SECTION'),(6252,114,'openExternalFlowsInNewWindow','true'),(6253,114,'popularFlowCount','5'),(6254,114,'popularInterval','72'),(6255,114,'ramThreshold','500'),(6256,114,'recommendedTags','Bygga\r\nRiva\r\nLivsmedel'),(6257,114,'showRelatedFlows','true'),(6258,114,'tempDir','/path/not/set'),(6259,114,'userFavouriteModuleAlias','/myfavourites');
/*!40000 ALTER TABLE `openhierarchy_foreground_module_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_foreground_module_users`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_module_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_moduleusers_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_module_users`
--

LOCK TABLES `openhierarchy_foreground_module_users` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_users` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_module_users` VALUES (61,3),(69,8);
/*!40000 ALTER TABLE `openhierarchy_foreground_module_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_foreground_modules`
--

DROP TABLE IF EXISTS `openhierarchy_foreground_modules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_foreground_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `alias` varchar(45) NOT NULL DEFAULT '',
  `description` text NOT NULL,
  `xslPath` text,
  `xslPathType` varchar(255) DEFAULT NULL,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `visibleInMenu` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `dataSourceID` int(10) unsigned DEFAULT NULL,
  `staticContentPackage` varchar(255) DEFAULT NULL,
  `requiredProtocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`moduleID`),
  UNIQUE KEY `Index_3` (`sectionID`,`alias`),
  KEY `FK_modules_1` (`sectionID`),
  KEY `FK_modules_2` (`dataSourceID`),
  CONSTRAINT `FK_modules_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_modules_2` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_modules`
--

LOCK TABLES `openhierarchy_foreground_modules` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_modules` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_modules` VALUES (8,'se.unlogic.hierarchy.foregroundmodules.login.UserProviderLoginModule','Logga in','login','Logga in','LoginModule.sv.xsl','Classpath',1,0,0,1,0,1,NULL,NULL,NULL),(9,'se.unlogic.hierarchy.foregroundmodules.logout.LogoutModule','Logga ut','logout','Logga ut','LogoutModule.sv.xsl','Classpath',0,1,1,1,0,1,NULL,NULL,NULL),(17,'se.unlogic.hierarchy.foregroundmodules.userprofile.UserProfileModule','Mina inställningar','userprofile','Modul för ändring av användaruppgifter','userprofile.xsl','Classpath',0,1,1,1,1,0,NULL,NULL,NULL),(37,'se.unlogic.hierarchy.foregroundmodules.menuadmin.MenuAdminModule','Menyer','menuadmin','Menyer','MenuAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(39,'se.unlogic.hierarchy.foregroundmodules.pagemodules.PageAdminModule','Sidor','pageadmin','Sidor','PageAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontentadmin',NULL),(56,'se.unlogic.hierarchy.foregroundmodules.runtimeinfo.RuntimeInfoModule','Systeminfo','runtimeinfo','Systeminfo','RuntimeInfoModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,NULL,NULL),(61,'se.unlogic.hierarchy.foregroundmodules.systemadmin.SystemAdminModule','Moduler och sektioner','systemadmin','Moduler och sektioner','SystemAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(64,'se.unlogic.hierarchy.foregroundmodules.threadinfo.ThreadInfoModule','Trådinfo','threadinfo','Trådinfo','ThreadInfoModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(67,'se.unlogic.hierarchy.foregroundmodules.staticcontent.StaticContentModule','Statiskt innehåll','static','Statiskt innehåll',NULL,NULL,1,1,1,1,0,1,NULL,NULL,NULL),(69,'se.unlogic.hierarchy.foregroundmodules.usersessionadmin.UserSessionAdminModule','Inloggade användare','sessionadmin','Inloggade användare','UserSessionAdminModule.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(70,'se.unlogic.hierarchy.foregroundmodules.groupadmin.GroupAdminModule','Grupper','groupadmin','Grupper','GroupAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(71,'se.unlogic.hierarchy.foregroundmodules.userproviders.SimpleUserProviderModule','SimpleUserProviderModule','userprovider','SimpleUserProviderModule',NULL,NULL,0,0,0,1,0,1,NULL,'/se/unlogic/hierarchy/foregroundmodules/useradmin/staticcontent',NULL),(84,'se.unlogic.hierarchy.foregroundmodules.datasourceadmin.DataSourceAdminModule','Datakällor','datasourceadmin','Datakällor','DataSourceAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(95,'se.unlogic.hierarchy.foregroundmodules.useradmin.UserAdminModule','Användare','useradmin','Användare','UserAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(102,'se.unlogic.hierarchy.foregroundmodules.htmloutput.HTMLOutputAdminModule','Högerkolumn - Admin','htmloutputadmin','Högerkolumn - Admin','HTMLOutputAdminModule.sv.xsl','Classpath',0,0,1,1,0,4,NULL,'staticcontent',NULL),(107,'se.unlogic.hierarchy.foregroundmodules.mailsenders.dummy.DummyEmailSenderModule','Dummy email sender','dummyemailsender','Receives last email sent',NULL,NULL,0,0,1,0,0,4,NULL,NULL,NULL),(108,'se.unlogic.hierarchy.foregroundmodules.test.EmailMailTestModule','E-post test modul','mailtest','Skickar ett test meddelande till den som klickar på länken',NULL,NULL,0,0,1,1,0,4,NULL,NULL,NULL),(110,'se.unlogic.hierarchy.foregroundmodules.mailsenders.persisting.PersistingMailSender','E-post kö','mailsender','E-post kö',NULL,NULL,0,0,1,1,1,4,NULL,NULL,NULL),(112,'com.nordicpeak.flowengine.QueryHandlerModule','QueryHandler','queryhandler','QueryHandler',NULL,NULL,0,0,1,1,0,1,NULL,NULL,NULL),(113,'com.nordicpeak.flowengine.queries.checkboxquery.CheckboxQueryProviderModule','CheckBoxQueryProvider','checkboxquery','CheckBoxQueryProvider','CheckboxQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(114,'com.nordicpeak.flowengine.FlowBrowserModule','E-tjänster','browser','E-tjänster','FlowInstanceBrowserModule.sv.xsl','Classpath',1,1,1,1,1,1,NULL,'staticcontent',NULL),(115,'com.nordicpeak.flowengine.queries.radiobuttonquery.RadioButtonQueryProviderModule','RadioButtonQueryProvider','radiobuttonquery','RadioButtonQueryProvider','RadioButtonQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(116,'com.nordicpeak.flowengine.queries.dropdownquery.DropDownQueryProviderModule','DropDownQueryProvider','dropdown','DropDownQueryProvider','DropDownQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(117,'com.nordicpeak.flowengine.queries.textareaquery.TextAreaQueryProviderModule','TextAreaQueryProvider','textarea','TextAreaQueryProvider','TextAreaQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(118,'com.nordicpeak.flowengine.queries.textfieldquery.TextFieldQueryProviderModule','TextFieldQueryProvider','textfieldprovider','TextFieldQueryProvider','TextFieldQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(119,'com.nordicpeak.flowengine.queries.fileuploadquery.FileUploadQueryProviderModule','FileUploadQueryProvider','fileuploadquery','FileUploadQueryProvider','FileUploadQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(121,'com.nordicpeak.flowengine.UserFlowInstanceModule','Mina ärenden','flowinstances','Mina ärenden','UserFlowInstanceModule.sv.xsl','Classpath',0,1,1,1,1,1,NULL,'staticcontent',NULL),(123,'com.nordicpeak.flowengine.FlowAdminModule','Adm. e-tjänster','flowadmin','Administrera e-tjänster','FlowAdminModule.sv.xsl','Classpath',0,0,0,1,1,1,NULL,'staticcontent',NULL),(124,'com.nordicpeak.flowengine.EvaluationHandlerModule','EvaluationHandler','evaluationhandler','EvaluationHandler',NULL,NULL,1,1,1,1,0,1,NULL,NULL,NULL),(125,'com.nordicpeak.flowengine.evaluators.querystateevaluator.QueryStateEvaluationProviderModule','QueryStateEvaluationProvider','querystateevaluationprovider','QueryStateEvaluationProvider','QueryStateEvaluationProviderModule.sv.xsl','Classpath',1,1,1,1,0,1,NULL,NULL,NULL),(131,'com.nordicpeak.flowengine.queries.contactdetailquery.ContactDetailQueryProviderModule','Kontaktvägar','contactdetails','Kontaktvägar','ContactDetailQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(132,'com.nordicpeak.flowengine.UserFavouriteForegroundModule','Mina favoriter','myfavourites','Mina favoriter',NULL,NULL,0,1,1,0,0,1,NULL,'staticcontent',NULL),(133,'com.nordicpeak.flowengine.FlowInstanceAdminModule','Adm. ärenden','flowinstanceadmin','Administrera ärenden','FlowInstanceAdminModule.sv.xsl','Classpath',0,0,1,1,1,1,NULL,'staticcontent',NULL),(144,'se.unlogic.hierarchy.foregroundmodules.groupproviders.SimpleGroupProviderModule','SimpleGroupProvider','simplegroupprovider','A group provider for simple groups',NULL,NULL,0,0,0,1,0,1,NULL,NULL,NULL),(145,'com.nordicpeak.flowengine.UserProfileModule','Mina uppgifter','mysettings','Mina uppgifter','UserProfileModule.sv.xsl','Classpath',0,1,0,1,0,1,NULL,'/com/nordicpeak/flowengine/staticcontent',NULL),(146,'com.nordicpeak.flowengine.CKConnectorModule','CKConnector','fileconnector','CKConnector',NULL,NULL,1,1,1,1,0,1,NULL,'staticcontent',NULL),(148,'com.nordicpeak.flowengine.UserOrganizationsModule','Mina företag','myorganizations','Mina företag','UserOrganizationsModule.sv.xsl','Classpath',0,1,1,1,0,1,NULL,'staticcontent',NULL),(149,'com.nordicpeak.flowengine.queries.organizationdetailquery.OrganizationDetailQueryProviderModule','Kontaktuppgiftsfråga (företag)','organizationdetails','Kontaktuppgiftsfråga (företag)','OrganizationDetailQueryAdmin.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(150,'se.unlogic.openhierarchy.foregroundmodules.siteprofile.SiteProfilesAdminModule','Profiler','profiler','Profiler','SiteProfilesAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(154,'com.nordicpeak.saml.SPMetadataSenderModule','SPMetadataSenderModule','metadata','SPMetadataSenderModule',NULL,NULL,1,1,1,0,0,1,NULL,NULL,NULL),(155,'se.unlogic.hierarchy.foregroundmodules.minimaluser.MinimalUserProviderModule','MinimalUserProvider','minimaluserprovider','MinimalUserProvider',NULL,NULL,0,0,0,1,0,1,NULL,'/se/unlogic/hierarchy/foregroundmodules/useradmin/staticcontent',NULL),(156,'com.nordicpeak.saml.minimaluseradapter.MinimalUserSAMLAdapterModule','MinimalUserSAMLAdapter','minimalusersamladapter','MinimalUserSAMLAdapter','',NULL,0,0,0,1,0,1,NULL,'',NULL),(159,'com.nordicpeak.saml.SAMLLoginProviderModule','SAML inloggning','saml','Inloggning via SAML',NULL,NULL,1,1,0,0,0,1,NULL,NULL,NULL),(160,'com.nordicpeak.flowengine.TextTagAdminModule','Taggar','tagadmin','Administrera taggar','TextTagAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(161,'com.nordicpeak.flowengine.pdf.PDFGeneratorModule','PDFGeneratorModule','pdfgen','PDFGeneratorModule',NULL,NULL,0,0,0,1,0,1,NULL,NULL,NULL),(162,'com.nordicpeak.authifyclient.AuthifySigningProvider','Signering - Authify','signing-authify','Signering - Authify','AuthifySigningProvider.sv.xsl','Classpath',1,1,1,1,0,1,NULL,'staticcontent',NULL),(165,'se.unlogic.hierarchy.foregroundmodules.htmloutput.HTMLOutputAdminModule','Administrera widgets','widgetadmin','Administrera widget','HTMLOutputAdminModule.sv.xsl','Classpath',0,0,1,1,0,4,NULL,'staticcontent',NULL);
/*!40000 ALTER TABLE `openhierarchy_foreground_modules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_menu_index`
--

DROP TABLE IF EXISTS `openhierarchy_menu_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_menu_index` (
  `menuIndexID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `menuIndex` int(10) unsigned NOT NULL DEFAULT '0',
  `moduleID` int(10) unsigned DEFAULT NULL,
  `uniqueID` varchar(255) DEFAULT NULL,
  `subSectionID` int(10) unsigned DEFAULT NULL,
  `menuItemID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`menuIndexID`),
  UNIQUE KEY `UniqueID / ModuleID` (`moduleID`,`uniqueID`,`sectionID`),
  UNIQUE KEY `Index_5` (`sectionID`,`subSectionID`),
  KEY `FK_menuindex_3` (`subSectionID`),
  KEY `FK_menuindex_4` (`menuItemID`),
  CONSTRAINT `FK_menuindex_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_2` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_3` FOREIGN KEY (`subSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_4` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=396 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 206848 kB; (`sectionID`) REFER `foraldramotet-o';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_menu_index`
--

LOCK TABLES `openhierarchy_menu_index` WRITE;
/*!40000 ALTER TABLE `openhierarchy_menu_index` DISABLE KEYS */;
INSERT INTO `openhierarchy_menu_index` VALUES (186,4,6,37,'se.unlogic.hierarchy.modules.menuadmin.MenuAdminModule',NULL,NULL),(188,4,21,39,'39',NULL,NULL),(190,1,48,NULL,NULL,4,NULL),(214,4,31,56,'56',NULL,NULL),(222,4,39,61,'61',NULL,NULL),(226,4,32,64,'64',NULL,NULL),(230,1,18,64,'64',NULL,NULL),(235,4,25,37,'37',NULL,NULL),(249,1,24,8,'8',NULL,NULL),(250,1,34,9,'9',NULL,NULL),(251,4,30,69,'69',NULL,NULL),(252,4,19,70,'70',NULL,NULL),(324,4,37,84,'84',NULL,NULL),(330,4,27,NULL,NULL,NULL,9),(331,4,3,NULL,NULL,NULL,10),(339,4,26,NULL,NULL,NULL,11),(346,4,18,95,'95',NULL,NULL),(347,1,37,39,'18',NULL,NULL),(348,1,38,39,'19',NULL,NULL),(356,4,33,NULL,NULL,NULL,12),(357,4,34,NULL,NULL,NULL,13),(374,4,36,110,'110',NULL,NULL),(375,1,43,116,'116',NULL,NULL),(376,1,44,121,'121',NULL,NULL),(377,1,46,123,'123',NULL,NULL),(381,1,42,114,'114',NULL,NULL),(382,1,45,NULL,NULL,NULL,14),(386,1,47,133,'133',NULL,NULL),(387,4,41,150,'150',NULL,NULL),(389,4,42,160,'160',NULL,NULL),(393,1,49,39,'20',NULL,NULL),(394,1,50,NULL,NULL,6,NULL),(395,6,1,39,'20',NULL,NULL);
/*!40000 ALTER TABLE `openhierarchy_menu_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_section_groups`
--

DROP TABLE IF EXISTS `openhierarchy_section_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_section_groups` (
  `sectionID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`sectionID`,`groupID`),
  CONSTRAINT `FK_sectiongroups_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_section_groups`
--

LOCK TABLES `openhierarchy_section_groups` WRITE;
/*!40000 ALTER TABLE `openhierarchy_section_groups` DISABLE KEYS */;
INSERT INTO `openhierarchy_section_groups` VALUES (4,6),(4,10),(6,6),(6,10);
/*!40000 ALTER TABLE `openhierarchy_section_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_section_users`
--

DROP TABLE IF EXISTS `openhierarchy_section_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_section_users` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`sectionID`,`userID`),
  CONSTRAINT `FK_sectionusers_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_section_users`
--

LOCK TABLES `openhierarchy_section_users` WRITE;
/*!40000 ALTER TABLE `openhierarchy_section_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_section_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_sections`
--

DROP TABLE IF EXISTS `openhierarchy_sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_sections` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentSectionID` int(10) unsigned DEFAULT NULL,
  `alias` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `visibleInMenu` tinyint(1) NOT NULL DEFAULT '0',
  `breadCrumb` tinyint(1) DEFAULT '1',
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `anonymousDefaultURI` varchar(255) DEFAULT NULL,
  `userDefaultURI` varchar(255) DEFAULT NULL,
  `requiredProtocol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sectionID`),
  UNIQUE KEY `Index_2` (`parentSectionID`,`alias`),
  CONSTRAINT `FK_sections_1` FOREIGN KEY (`parentSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_sections`
--

LOCK TABLES `openhierarchy_sections` WRITE;
/*!40000 ALTER TABLE `openhierarchy_sections` DISABLE KEYS */;
INSERT INTO `openhierarchy_sections` VALUES (1,NULL,'home',1,1,1,1,0,1,'Hem','Hemmasektionen','/browser','/browser',NULL),(4,1,'administration',1,0,0,1,1,1,'Systemadm.','Systemadministration och övervakning','/sessionadmin','/sessionadmin',NULL),(5,1,'mypages',1,0,1,1,0,1,'Mina ärenden','Mina ärenden','/not/set','/not/set',NULL),(6,1,'om',1,1,1,1,1,1,'Om','Om','/page/om','/page/om',NULL);
/*!40000 ALTER TABLE `openhierarchy_sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_virtual_menu_item_groups`
--

DROP TABLE IF EXISTS `openhierarchy_virtual_menu_item_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_virtual_menu_item_groups` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`menuItemID`,`groupID`),
  CONSTRAINT `FK_virtualmenuitemgroups_1` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_virtual_menu_item_groups`
--

LOCK TABLES `openhierarchy_virtual_menu_item_groups` WRITE;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_groups` DISABLE KEYS */;
INSERT INTO `openhierarchy_virtual_menu_item_groups` VALUES (9,6),(11,6);
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_virtual_menu_item_users`
--

DROP TABLE IF EXISTS `openhierarchy_virtual_menu_item_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_virtual_menu_item_users` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`menuItemID`,`userID`),
  CONSTRAINT `FK_virtualmenuitemusers_1` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_virtual_menu_item_users`
--

LOCK TABLES `openhierarchy_virtual_menu_item_users` WRITE;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_virtual_menu_items`
--

DROP TABLE IF EXISTS `openhierarchy_virtual_menu_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_virtual_menu_items` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `itemtype` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(45) DEFAULT NULL,
  `description` text,
  `url` text,
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`menuItemID`),
  KEY `FK_menuadmin_1` (`sectionID`),
  CONSTRAINT `FK_virtualmenuitems_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_virtual_menu_items`
--

LOCK TABLES `openhierarchy_virtual_menu_items` WRITE;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_items` DISABLE KEYS */;
INSERT INTO `openhierarchy_virtual_menu_items` VALUES (9,'TITLE','Övervakning','Övervakning',NULL,0,0,1,4),(10,'TITLE','Administration','Administration',NULL,0,0,0,4),(11,'BLANK',NULL,NULL,NULL,0,0,1,4),(12,'BLANK',NULL,NULL,NULL,0,0,1,4),(13,'TITLE','Övrigt','Övrigt',NULL,0,0,1,4),(14,'SECTION','Mina favoriter','Mina favoriter','#menu-container.favourites',0,0,0,1);
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_detail_queries`
--

DROP TABLE IF EXISTS `organization_detail_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_detail_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `helpText` text,
  `allowLetter` tinyint(1) unsigned NOT NULL,
  `allowSMS` tinyint(1) unsigned NOT NULL,
  `allowEmail` tinyint(1) unsigned NOT NULL,
  `allowPhone` tinyint(1) unsigned NOT NULL,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_detail_queries`
--

LOCK TABLES `organization_detail_queries` WRITE;
/*!40000 ALTER TABLE `organization_detail_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `organization_detail_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_detail_query_instances`
--

DROP TABLE IF EXISTS `organization_detail_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_detail_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryID` int(10) unsigned NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `organizationNumber` varchar(16) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `zipCode` varchar(10) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `contactByLetter` tinyint(1) unsigned DEFAULT NULL,
  `contactBySMS` tinyint(1) unsigned DEFAULT NULL,
  `contactByEmail` tinyint(1) unsigned DEFAULT NULL,
  `contactByPhone` tinyint(1) unsigned DEFAULT NULL,
  `organizationID` int(10) unsigned DEFAULT NULL,
  `persistOrganization` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_organization_detail_query_instances_1` (`queryID`),
  CONSTRAINT `FK_organization_detail_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `organization_detail_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4567 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_detail_query_instances`
--

LOCK TABLES `organization_detail_query_instances` WRITE;
/*!40000 ALTER TABLE `organization_detail_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `organization_detail_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page_groups`
--

DROP TABLE IF EXISTS `page_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_groups` (
  `pageID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`pageID`,`groupID`),
  CONSTRAINT `FK_pagegroups_1` FOREIGN KEY (`pageID`) REFERENCES `pages` (`pageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page_groups`
--

LOCK TABLES `page_groups` WRITE;
/*!40000 ALTER TABLE `page_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `page_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page_users`
--

DROP TABLE IF EXISTS `page_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_users` (
  `pageID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pageID`,`userID`),
  CONSTRAINT `FK_pageusers_1` FOREIGN KEY (`pageID`) REFERENCES `pages` (`pageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page_users`
--

LOCK TABLES `page_users` WRITE;
/*!40000 ALTER TABLE `page_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `page_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pages` (
  `pageID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `text` mediumtext NOT NULL,
  `enabled` varchar(45) NOT NULL DEFAULT '',
  `visibleInMenu` tinyint(1) NOT NULL DEFAULT '0',
  `anonymousAccess` tinyint(1) NOT NULL DEFAULT '0',
  `userAccess` tinyint(1) NOT NULL DEFAULT '0',
  `adminAccess` tinyint(1) NOT NULL DEFAULT '0',
  `sectionID` int(10) unsigned NOT NULL DEFAULT '0',
  `alias` varchar(255) NOT NULL DEFAULT '',
  `breadCrumb` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`pageID`),
  UNIQUE KEY `Index_3` (`sectionID`,`alias`),
  KEY `FK_pages_1` (`sectionID`),
  CONSTRAINT `FK_pages_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 191488 kB; (`sectionID`) REFER `fkdb-system/sec';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` VALUES (20,'Om','Om','<h2>Rubrik</h2>\r\n<p>Text</p>\r\n','1',0,1,1,1,6,'om',0);
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `query_state_evaluator_alternatives`
--

DROP TABLE IF EXISTS `query_state_evaluator_alternatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `query_state_evaluator_alternatives` (
  `evaluatorID` int(10) unsigned NOT NULL,
  `alternativeID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`evaluatorID`,`alternativeID`),
  CONSTRAINT `FK_query_state_evaluator_alternatives_1` FOREIGN KEY (`evaluatorID`) REFERENCES `query_state_evaluators` (`evaluatorID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `query_state_evaluator_alternatives`
--

LOCK TABLES `query_state_evaluator_alternatives` WRITE;
/*!40000 ALTER TABLE `query_state_evaluator_alternatives` DISABLE KEYS */;
/*!40000 ALTER TABLE `query_state_evaluator_alternatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `query_state_evaluators`
--

DROP TABLE IF EXISTS `query_state_evaluators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `query_state_evaluators` (
  `evaluatorID` int(10) unsigned NOT NULL,
  `selectionMode` varchar(45) NOT NULL,
  `queryState` varchar(45) NOT NULL,
  PRIMARY KEY (`evaluatorID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `query_state_evaluators`
--

LOCK TABLES `query_state_evaluators` WRITE;
/*!40000 ALTER TABLE `query_state_evaluators` DISABLE KEYS */;
/*!40000 ALTER TABLE `query_state_evaluators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `radio_button_queries`
--

DROP TABLE IF EXISTS `radio_button_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `radio_button_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `freeTextAlternative` varchar(255) DEFAULT NULL,
  `helpText` text,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `radio_button_queries`
--

LOCK TABLES `radio_button_queries` WRITE;
/*!40000 ALTER TABLE `radio_button_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `radio_button_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `radio_button_query_alternatives`
--

DROP TABLE IF EXISTS `radio_button_query_alternatives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `radio_button_query_alternatives` (
  `alternativeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`alternativeID`),
  KEY `FK_radio_button_query_alternatives_1` (`queryID`),
  CONSTRAINT `FK_radio_button_query_alternatives_1` FOREIGN KEY (`queryID`) REFERENCES `radio_button_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1759 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `radio_button_query_alternatives`
--

LOCK TABLES `radio_button_query_alternatives` WRITE;
/*!40000 ALTER TABLE `radio_button_query_alternatives` DISABLE KEYS */;
/*!40000 ALTER TABLE `radio_button_query_alternatives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `radio_button_query_instances`
--

DROP TABLE IF EXISTS `radio_button_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `radio_button_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  `alternativeID` int(10) unsigned DEFAULT NULL,
  `freeTextAlternativeValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_radio_button_query_instances_1` (`queryID`),
  KEY `FK_radio_button_query_instances_2` (`alternativeID`),
  CONSTRAINT `FK_radio_button_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `radio_button_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_radio_button_query_instances_2` FOREIGN KEY (`alternativeID`) REFERENCES `radio_button_query_alternatives` (`alternativeID`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `radio_button_query_instances`
--

LOCK TABLES `radio_button_query_instances` WRITE;
/*!40000 ALTER TABLE `radio_button_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `radio_button_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_group_attributes`
--

DROP TABLE IF EXISTS `simple_group_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_group_attributes` (
  `groupID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`groupID`,`name`),
  CONSTRAINT `FK_simple_group_attributes_1` FOREIGN KEY (`groupID`) REFERENCES `simple_groups` (`groupID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_group_attributes`
--

LOCK TABLES `simple_group_attributes` WRITE;
/*!40000 ALTER TABLE `simple_group_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `simple_group_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_groups`
--

DROP TABLE IF EXISTS `simple_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_groups` (
  `groupID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`groupID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_groups`
--

LOCK TABLES `simple_groups` WRITE;
/*!40000 ALTER TABLE `simple_groups` DISABLE KEYS */;
INSERT INTO `simple_groups` VALUES (6,'Systemadministratörer','Administratörer',1),(9,'Medborgare','Grupp för medborgare',1),(10,'E-tjänst administratörer','Grupp för användare som ska få bygga e-tjänster',1),(11,'Handläggare','Handläggare',1);
/*!40000 ALTER TABLE `simple_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_user_attributes`
--

DROP TABLE IF EXISTS `simple_user_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_user_attributes` (
  `userID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`userID`,`name`) USING BTREE,
  CONSTRAINT `FK_simple_user_attributes_1` FOREIGN KEY (`userID`) REFERENCES `simple_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_user_attributes`
--

LOCK TABLES `simple_user_attributes` WRITE;
/*!40000 ALTER TABLE `simple_user_attributes` DISABLE KEYS */;
INSERT INTO `simple_user_attributes` VALUES (1,'address','Rådhusgatan 11'),(1,'contactByEmail','true'),(1,'contactBySMS','true'),(1,'mobilePhone','0706153310'),(1,'phone','0706153310'),(1,'postalAddress','Sundsvall'),(1,'zipCode','85231');
/*!40000 ALTER TABLE `simple_user_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_user_groups`
--

DROP TABLE IF EXISTS `simple_user_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_user_groups` (
  `userID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  KEY `FK_usergroups_2` (`groupID`),
  CONSTRAINT `FK_usergroups_1` FOREIGN KEY (`userID`) REFERENCES `simple_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_user_groups`
--

LOCK TABLES `simple_user_groups` WRITE;
/*!40000 ALTER TABLE `simple_user_groups` DISABLE KEYS */;
INSERT INTO `simple_user_groups` VALUES (1,6),(1,11);
/*!40000 ALTER TABLE `simple_user_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `simple_users`
--

DROP TABLE IF EXISTS `simple_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `simple_users` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL DEFAULT '',
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL DEFAULT '',
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastlogin` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `language` varchar(76) DEFAULT NULL,
  `preferedDesign` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_users`
--

LOCK TABLES `simple_users` WRITE;
/*!40000 ALTER TABLE `simple_users` DISABLE KEYS */;
INSERT INTO `simple_users` VALUES (1,'admin','d033e22ae348aeb5660fc2140aec35850c4da997','FlowEngine','Admin','email@not.set',1,1,'2008-01-27 19:43:42','2014-09-11 20:53:41',NULL,NULL);
/*!40000 ALTER TABLE `simple_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_profile_domains`
--

DROP TABLE IF EXISTS `site_profile_domains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_profile_domains` (
  `profileID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `domain` varchar(255) NOT NULL,
  PRIMARY KEY (`profileID`,`domain`),
  CONSTRAINT `FK_site_profile_domains_1` FOREIGN KEY (`profileID`) REFERENCES `site_profiles` (`profileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_profile_domains`
--

LOCK TABLES `site_profile_domains` WRITE;
/*!40000 ALTER TABLE `site_profile_domains` DISABLE KEYS */;
INSERT INTO `site_profile_domains` VALUES (1,'demo.not.set');
/*!40000 ALTER TABLE `site_profile_domains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_profile_global_settings`
--

DROP TABLE IF EXISTS `site_profile_global_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_profile_global_settings` (
  `settingID` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`settingID`,`sortIndex`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_profile_global_settings`
--

LOCK TABLES `site_profile_global_settings` WRITE;
/*!40000 ALTER TABLE `site_profile_global_settings` DISABLE KEYS */;
INSERT INTO `site_profile_global_settings` VALUES ('kommun',1,'<p>Test</p>\r\n'),('pdf.flowinstance.logo',1,'classpath://com/nordicpeak/flowengine/pdf/staticcontent/pics/logo.png'),('Testtagg',1,'En exempeltext');
/*!40000 ALTER TABLE `site_profile_global_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_profile_settings`
--

DROP TABLE IF EXISTS `site_profile_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_profile_settings` (
  `settingID` varchar(255) NOT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  `value` varchar(4096) NOT NULL,
  `profileID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`settingID`,`sortIndex`,`profileID`) USING BTREE,
  KEY `FK_site_profile_settings_1` (`profileID`),
  CONSTRAINT `FK_site_profile_settings_1` FOREIGN KEY (`profileID`) REFERENCES `site_profiles` (`profileID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_profile_settings`
--

LOCK TABLES `site_profile_settings` WRITE;
/*!40000 ALTER TABLE `site_profile_settings` DISABLE KEYS */;
INSERT INTO `site_profile_settings` VALUES ('BaseMapQuery-lmUser',1,'sundsvall',1),('BaseMapQuery-searchPrefix',1,'SUNDSVALL',1),('BaseMapQuery-startExtent',1,'608114,6910996,641846,6932596',1),('bgmodule-4',1,'<p><img alt=\"\" height=\"70\" src=\"/images/sundsvall.e-tjansteportalen.se.png\" style=\"width: 1280px; height: 70px;\" width=\"1280\" ></p>\r\n',1),('FlowFamilyEmailAddresses:11',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:12',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:15',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:16',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:17',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:18',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:19',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:27',1,'miljonamnden@sundsvall.se',1),('FlowFamilyEmailAddresses:31',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:33',1,'bygglov@sundsvall.se',1),('FlowFamilyEmailAddresses:9',1,'bygglov@sundsvall.se',1),('kommun',1,'<p>Demo</p>\r\n',1),('pdf.flowinstance.logo',1,'file:///D:/Program Files/Apache Software Foundation/Tomcat 7.0/sites/kommun.e-tjansteportalen.se/filestore/logos/sundsvall.png',1),('Testtagg',1,'Demo text',1);
/*!40000 ALTER TABLE `site_profile_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_profiles`
--

DROP TABLE IF EXISTS `site_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_profiles` (
  `profileID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `design` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`profileID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_profiles`
--

LOCK TABLES `site_profiles` WRITE;
/*!40000 ALTER TABLE `site_profiles` DISABLE KEYS */;
INSERT INTO `site_profiles` VALUES (1,'Demo','default (xsl/design.xsl)');
/*!40000 ALTER TABLE `site_profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_versions`
--

DROP TABLE IF EXISTS `table_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_versions` (
  `tableGroupName` varchar(255) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tableGroupName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_versions`
--

LOCK TABLES `table_versions` WRITE;
/*!40000 ALTER TABLE `table_versions` DISABLE KEYS */;
INSERT INTO `table_versions` VALUES ('com.nordicpeak.flowengine.dao.FlowEngineDAOFactory',44),('com.nordicpeak.flowengine.evaluators.querystateevaluator.QueryStateEvaluationProviderModule',2),('com.nordicpeak.flowengine.queries.checkboxquery.CheckboxQueryProviderModule',7),('com.nordicpeak.flowengine.queries.contactdetailquery.ContactDetailQueryProviderModule',3),('com.nordicpeak.flowengine.queries.dropdownquery.DropDownQueryProviderModule',5),('com.nordicpeak.flowengine.queries.fileuploadquery.FileUploadQueryProviderModule',1),('com.nordicpeak.flowengine.queries.multigeometrymapquery.MultiGeometryMapQueryProvider',4),('com.nordicpeak.flowengine.queries.organizationdetailquery.OrganizationDetailQueryProviderModule',3),('com.nordicpeak.flowengine.queries.radiobuttonquery.RadioButtonQueryProviderModule',4),('com.nordicpeak.flowengine.queries.singlepolygonmapquery.SinglePolygonMapQueryProvider',5),('com.nordicpeak.flowengine.queries.textareaquery.TextAreaQueryProviderModule',1),('com.nordicpeak.flowengine.queries.textfieldquery.TextFieldQueryProviderModule',2),('com.nordicpeak.persistingsmssender.PersistingSMSSender',1),('se.unlogic.hierarchy.core.daos.implementations.mysql.MySQLCoreDAOFactory',31),('se.unlogic.hierarchy.foregroundmodules.forum.daos.mysql.MySQLForumDAOFactory',2),('se.unlogic.hierarchy.foregroundmodules.groupproviders.SimpleGroupProviderModule',3),('se.unlogic.hierarchy.foregroundmodules.mailsenders.persisting.daos.mysql.MySQLMailDAOFactory',1),('se.unlogic.hierarchy.foregroundmodules.minimaluser.MinimalUserProviderModule',2),('se.unlogic.hierarchy.foregroundmodules.pagemodules.daos.annotated.AnnotatedPageDAOFactory',3),('se.unlogic.hierarchy.foregroundmodules.userproviders.SimpleUserProviderModule',4),('se.unlogic.openhierarchy.foregroundmodules.siteprofile.SiteProfilesAdminModule',4);
/*!40000 ALTER TABLE `table_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_area_queries`
--

DROP TABLE IF EXISTS `text_area_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text_area_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `helpText` text,
  `maxLength` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_area_queries`
--

LOCK TABLES `text_area_queries` WRITE;
/*!40000 ALTER TABLE `text_area_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_area_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_area_query_instances`
--

DROP TABLE IF EXISTS `text_area_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text_area_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  `value` text,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_text_area_query_instances_1` (`queryID`),
  CONSTRAINT `FK_text_area_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `text_area_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_area_query_instances`
--

LOCK TABLES `text_area_query_instances` WRITE;
/*!40000 ALTER TABLE `text_area_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_area_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_field_queries`
--

DROP TABLE IF EXISTS `text_field_queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text_field_queries` (
  `queryID` int(10) unsigned NOT NULL,
  `description` text,
  `helpText` text,
  `layout` varchar(45) NOT NULL,
  PRIMARY KEY (`queryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_field_queries`
--

LOCK TABLES `text_field_queries` WRITE;
/*!40000 ALTER TABLE `text_field_queries` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_field_queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_field_query_instance_values`
--

DROP TABLE IF EXISTS `text_field_query_instance_values`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text_field_query_instance_values` (
  `textFieldValueID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `queryInstanceID` int(10) unsigned NOT NULL,
  `textFieldID` int(10) unsigned NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`textFieldValueID`),
  KEY `FK_text_field_query_instance_values_1` (`queryInstanceID`),
  KEY `FK_text_field_query_instance_values_2` (`textFieldID`),
  CONSTRAINT `FK_text_field_query_instance_values_1` FOREIGN KEY (`queryInstanceID`) REFERENCES `text_field_query_instances` (`queryInstanceID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_text_field_query_instance_values_2` FOREIGN KEY (`textFieldID`) REFERENCES `text_fields` (`textFieldID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=712 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_field_query_instance_values`
--

LOCK TABLES `text_field_query_instance_values` WRITE;
/*!40000 ALTER TABLE `text_field_query_instance_values` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_field_query_instance_values` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_field_query_instances`
--

DROP TABLE IF EXISTS `text_field_query_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text_field_query_instances` (
  `queryInstanceID` int(10) unsigned NOT NULL,
  `queryID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`queryInstanceID`),
  KEY `FK_text_field_query_instances_1` (`queryID`),
  CONSTRAINT `FK_text_field_query_instances_1` FOREIGN KEY (`queryID`) REFERENCES `text_field_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_field_query_instances`
--

LOCK TABLES `text_field_query_instances` WRITE;
/*!40000 ALTER TABLE `text_field_query_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_field_query_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `text_fields`
--

DROP TABLE IF EXISTS `text_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `text_fields` (
  `textFieldID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  `required` tinyint(1) NOT NULL,
  `width` int(10) unsigned DEFAULT NULL,
  `sortIndex` int(10) unsigned NOT NULL,
  `formatValidator` varchar(255) DEFAULT NULL,
  `queryID` int(10) unsigned NOT NULL,
  `maxContentLength` int(10) unsigned DEFAULT NULL,
  `invalidFormatMessage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`textFieldID`),
  KEY `FK_text_fields_1` (`queryID`),
  CONSTRAINT `FK_text_fields_1` FOREIGN KEY (`queryID`) REFERENCES `text_field_queries` (`queryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1613 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `text_fields`
--

LOCK TABLES `text_fields` WRITE;
/*!40000 ALTER TABLE `text_fields` DISABLE KEYS */;
/*!40000 ALTER TABLE `text_fields` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-11 23:22:22
