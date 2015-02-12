-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.39 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.2.0.4675
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for openhierarchy-system
CREATE DATABASE IF NOT EXISTS `openhierarchy-system` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `openhierarchy-system`;


-- Dumping structure for table openhierarchy-system.attachments
CREATE TABLE IF NOT EXISTS `attachments` (
  `attachmenID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `data` longblob NOT NULL,
  PRIMARY KEY (`attachmenID`),
  KEY `FK_attachments_1` (`emailID`),
  CONSTRAINT `FK_attachments_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.attachments: ~0 rows (approximately)
DELETE FROM `attachments`;
/*!40000 ALTER TABLE `attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachments` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.bccrecipients
CREATE TABLE IF NOT EXISTS `bccrecipients` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`),
  KEY `FK_bccrecipient_1` (`emailID`),
  CONSTRAINT `FK_bccrecipient_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.bccrecipients: ~0 rows (approximately)
DELETE FROM `bccrecipients`;
/*!40000 ALTER TABLE `bccrecipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `bccrecipients` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.ccrecipients
CREATE TABLE IF NOT EXISTS `ccrecipients` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`),
  KEY `FK_ccrecipient_1` (`emailID`),
  CONSTRAINT `FK_ccrecipient_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.ccrecipients: ~0 rows (approximately)
DELETE FROM `ccrecipients`;
/*!40000 ALTER TABLE `ccrecipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `ccrecipients` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.emails
CREATE TABLE IF NOT EXISTS `emails` (
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

-- Dumping data for table openhierarchy-system.emails: ~0 rows (approximately)
DELETE FROM `emails`;
/*!40000 ALTER TABLE `emails` DISABLE KEYS */;
/*!40000 ALTER TABLE `emails` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_background_modules
CREATE TABLE IF NOT EXISTS `openhierarchy_background_modules` (
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
  `priority` int(10) DEFAULT NULL,
  PRIMARY KEY (`moduleID`),
  KEY `FK_backgroundmodules_1` (`sectionID`),
  KEY `FK_backgroundmodules_2` (`dataSourceID`),
  CONSTRAINT `FK_openhierarchy_background_modules_openhierarchy_data_sources` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_openhierarchy_background_modules_openhierarchy_sections` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_background_modules: ~0 rows (approximately)
DELETE FROM `openhierarchy_background_modules`;
/*!40000 ALTER TABLE `openhierarchy_background_modules` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_modules` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_background_module_aliases
CREATE TABLE IF NOT EXISTS `openhierarchy_background_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_openhierarchy_background_module_aliases_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_background_module_aliases: ~0 rows (approximately)
DELETE FROM `openhierarchy_background_module_aliases`;
/*!40000 ALTER TABLE `openhierarchy_background_module_aliases` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_aliases` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_background_module_groups
CREATE TABLE IF NOT EXISTS `openhierarchy_background_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_backgroundmodulegroups_2` (`groupID`),
  CONSTRAINT `FK_openhierarchy_background_module_groups` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_background_module_groups: ~0 rows (approximately)
DELETE FROM `openhierarchy_background_module_groups`;
/*!40000 ALTER TABLE `openhierarchy_background_module_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_background_module_settings
CREATE TABLE IF NOT EXISTS `openhierarchy_background_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_backgroundmodulesettings_1` (`moduleID`),
  CONSTRAINT `FK_openhierarchy_background_module_settings_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_background_module_settings: ~0 rows (approximately)
DELETE FROM `openhierarchy_background_module_settings`;
/*!40000 ALTER TABLE `openhierarchy_background_module_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_settings` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_background_module_slots
CREATE TABLE IF NOT EXISTS `openhierarchy_background_module_slots` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `slot` varchar(255) NOT NULL,
  PRIMARY KEY (`moduleID`,`slot`),
  CONSTRAINT `FK_openhierarchy_background_module_slots_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_background_module_slots: ~0 rows (approximately)
DELETE FROM `openhierarchy_background_module_slots`;
/*!40000 ALTER TABLE `openhierarchy_background_module_slots` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_slots` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_background_module_users
CREATE TABLE IF NOT EXISTS `openhierarchy_background_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_openhierarchy_background_module_users_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_background_module_users: ~0 rows (approximately)
DELETE FROM `openhierarchy_background_module_users`;
/*!40000 ALTER TABLE `openhierarchy_background_module_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_background_module_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_data_sources
CREATE TABLE IF NOT EXISTS `openhierarchy_data_sources` (
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
  `maxWait` int(10) DEFAULT '0',
  `defaultCatalog` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dataSourceID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_data_sources: ~1 rows (approximately)
DELETE FROM `openhierarchy_data_sources`;
/*!40000 ALTER TABLE `openhierarchy_data_sources` DISABLE KEYS */;
INSERT INTO `openhierarchy_data_sources` (`dataSourceID`, `url`, `type`, `enabled`, `driver`, `username`, `password`, `name`, `logAbandoned`, `removeAbandoned`, `removeTimeout`, `testOnBorrow`, `validationQuery`, `maxActive`, `maxIdle`, `minIdle`, `maxWait`, `defaultCatalog`) VALUES
	(1, 'jdbc:mysql://127.0.0.1:3306/openhierarchy-system', 'SystemManaged', 1, 'com.mysql.jdbc.Driver', 'root', 'root', 'Användardatabas', 1, 1, 30, 1, 'SELECT 1', 100, 30, 0, 10000, NULL);
/*!40000 ALTER TABLE `openhierarchy_data_sources` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_filter_modules
CREATE TABLE IF NOT EXISTS `openhierarchy_filter_modules` (
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
  CONSTRAINT `FK_openhierarchy_filter_modules_openhierarchy_data_sources` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_filter_modules: ~0 rows (approximately)
DELETE FROM `openhierarchy_filter_modules`;
/*!40000 ALTER TABLE `openhierarchy_filter_modules` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_modules` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_filter_module_aliases
CREATE TABLE IF NOT EXISTS `openhierarchy_filter_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_openhierarchy_filter_module_aliases_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_filter_module_aliases: ~0 rows (approximately)
DELETE FROM `openhierarchy_filter_module_aliases`;
/*!40000 ALTER TABLE `openhierarchy_filter_module_aliases` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_aliases` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_filter_module_groups
CREATE TABLE IF NOT EXISTS `openhierarchy_filter_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_filtermodulegroups_2` (`groupID`),
  CONSTRAINT `FK_openhierarchy_filter_module_groups_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_filter_module_groups: ~0 rows (approximately)
DELETE FROM `openhierarchy_filter_module_groups`;
/*!40000 ALTER TABLE `openhierarchy_filter_module_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_filter_module_settings
CREATE TABLE IF NOT EXISTS `openhierarchy_filter_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_filtermodulesettings_1` (`moduleID`),
  CONSTRAINT `FK_openhierarchy_filter_module_settings_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_filter_module_settings: ~0 rows (approximately)
DELETE FROM `openhierarchy_filter_module_settings`;
/*!40000 ALTER TABLE `openhierarchy_filter_module_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_settings` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_filter_module_users
CREATE TABLE IF NOT EXISTS `openhierarchy_filter_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_openhierarchy_filter_module_users_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_filter_module_users: ~0 rows (approximately)
DELETE FROM `openhierarchy_filter_module_users`;
/*!40000 ALTER TABLE `openhierarchy_filter_module_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_filter_module_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_foreground_modules
CREATE TABLE IF NOT EXISTS `openhierarchy_foreground_modules` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) NOT NULL DEFAULT '',
  `name` text NOT NULL,
  `alias` varchar(45) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
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
  CONSTRAINT `FK_openhierarchy_foreground_modules_openhierarchy_data_sources` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_openhierarchy_foreground_modules_openhierarchy_sections` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_foreground_modules: ~20 rows (approximately)
DELETE FROM `openhierarchy_foreground_modules`;
/*!40000 ALTER TABLE `openhierarchy_foreground_modules` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_modules` (`moduleID`, `classname`, `name`, `alias`, `description`, `xslPath`, `xslPathType`, `anonymousAccess`, `userAccess`, `adminAccess`, `enabled`, `visibleInMenu`, `sectionID`, `dataSourceID`, `staticContentPackage`, `requiredProtocol`) VALUES
	(1, 'se.unlogic.hierarchy.foregroundmodules.login.UserProviderLoginModule', 'Logga in', 'login', 'Modul för inloggning', 'LoginModule.sv.xsl', 'Classpath', 1, 0, 0, 1, 1, 1, 1, NULL, NULL),
	(2, 'se.unlogic.hierarchy.foregroundmodules.logout.LogoutModule', 'Logga ut', 'logout', 'Modul för utloggning', 'LogoutModule.sv.xsl', 'Classpath', 0, 1, 1, 1, 0, 1, NULL, NULL, NULL),
	(3, 'se.unlogic.hierarchy.foregroundmodules.useradmin.UserAdminModule', 'Användare', 'useradmin', 'Modul för användaradministration', 'UserAdminModule.sv.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, 1, 'staticcontent', NULL),
	(4, 'se.unlogic.hierarchy.foregroundmodules.userprofile.UserProfileModule', 'Mina inställningar', 'userprofile', 'Modul för ändring av användaruppgifter', 'UserProfileModule.sv.xsl', 'Classpath', 0, 1, 1, 1, 1, 2, 1, NULL, NULL),
	(5, 'se.unlogic.hierarchy.foregroundmodules.datasourceadmin.DataSourceAdminModule', 'Datakällor', 'datasources', 'Administration av datakällor', 'DataSourceAdminModule.en.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontent', NULL),
	(6, 'se.unlogic.hierarchy.foregroundmodules.systemadmin.SystemAdminModule', 'Moduler och Sektioner', 'systemadmin', 'Modul för administration av systemets olika moduler och sektioner', 'SystemAdminModule.sv.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontent', NULL),
	(7, 'se.unlogic.hierarchy.foregroundmodules.pagemodules.PageViewModule', 'Sidvisningsmodul', 'page', 'Modul för visning av statiska sidor', 'PageViewModule.sv.xsl', 'Classpath', 1, 1, 1, 1, 1, 2, NULL, NULL, NULL),
	(8, 'se.unlogic.hierarchy.foregroundmodules.menuadmin.MenuAdminModule', 'Meny', 'menuadmin', 'Modul för menyadministration', 'MenuAdminModule.sv.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontent', NULL),
	(9, 'se.unlogic.hierarchy.foregroundmodules.pagemodules.PageAdminModule', 'Sidor', 'pageadmin', 'Modul för administration av statiska sidor', 'PageAdminModule.sv.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontentadmin', NULL),
	(10, 'se.unlogic.hierarchy.foregroundmodules.test.XSLReload', 'Ladda om stilmall', 'xslreload', 'Module for reloading XSL stylesheet', '', NULL, 0, 0, 1, 1, 1, 2, NULL, NULL, NULL),
	(11, 'se.unlogic.hierarchy.foregroundmodules.runtimeinfo.RuntimeInfoModule', 'Systeminfo', 'runtimeinfo', 'Visar info om minne och processorer', 'RuntimeInfoModule.en.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontent', NULL),
	(12, 'se.unlogic.hierarchy.foregroundmodules.threadinfo.ThreadInfoModule', 'Trådinfo', 'threadinfo', 'Trådinfo', 'ThreadInfoModule.en.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontent', NULL),
	(13, 'se.unlogic.hierarchy.foregroundmodules.staticcontent.StaticContentModule', 'Modul för statiska filer', 'static', 'Modul för statiska filer', NULL, NULL, 1, 1, 1, 1, 0, 1, NULL, NULL, NULL),
	(14, 'se.unlogic.hierarchy.foregroundmodules.userproviders.SimpleUserProviderModule', 'DBUserProvider', 'dbuserprovider', 'DBUserProvider', NULL, NULL, 0, 0, 1, 1, 0, 2, 1, '/se/unlogic/hierarchy/foregroundmodules/useradmin/staticcontent', NULL),
	(15, 'se.unlogic.hierarchy.foregroundmodules.groupadmin.GroupAdminModule', 'Grupper', 'groups', 'Modul för administration av grupper', 'GroupAdminModule.sv.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, 1, 'staticcontent', NULL),
	(16, 'se.unlogic.hierarchy.foregroundmodules.usersessionadmin.UserSessionAdminModule', 'Inloggade användare', 'sessions', 'Visar samtliga inloggade användare', 'UserSessionAdminModule.xsl', 'Classpath', 0, 0, 1, 1, 1, 2, NULL, 'staticcontent', NULL),
	(17, 'se.unlogic.hierarchy.foregroundmodules.issuetracker.PrivilegeAdminModule', 'Administrera rättigheter i Ärendehanteringen', 'privilegeadminmodule', 'Administrera rättigheter i Ärendehanteringen', 'PrivilegeAdminModule.xsl', 'Classpath', 0, 0, 1, 0, 1, 2, 1, 'staticcontent', NULL),
	(18, 'se.unlogic.hierarchy.foregroundmodules.mailsenders.persisting.PersistingMailSender', 'E-post kö', 'mailsender', 'Modul för utskick av e-post', NULL, NULL, 0, 0, 1, 0, 1, 2, NULL, NULL, NULL),
	(19, 'se.unlogic.hierarchy.foregroundmodules.mailsenders.dummy.DummyEmailSenderModule', 'Dummy email sender', 'dummyemailsender', 'Receives last email sent', NULL, NULL, 0, 0, 1, 0, 1, 2, NULL, NULL, NULL),
	(20, 'se.unlogic.hierarchy.foregroundmodules.groupproviders.SimpleGroupProviderModule', 'SimpleGroupProvider', 'simplegroupprovider', 'A group provider for simple groups', NULL, NULL, 0, 0, 1, 1, 0, 1, NULL, NULL, NULL);
/*!40000 ALTER TABLE `openhierarchy_foreground_modules` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_foreground_module_groups
CREATE TABLE IF NOT EXISTS `openhierarchy_foreground_module_groups` (
  `moduleID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`groupID`),
  KEY `FK_modulegroups_2` (`groupID`),
  CONSTRAINT `FK_openhierarchy_foreground_module_groups_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_foreground_module_groups: ~0 rows (approximately)
DELETE FROM `openhierarchy_foreground_module_groups`;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_foreground_module_settings
CREATE TABLE IF NOT EXISTS `openhierarchy_foreground_module_settings` (
  `counter` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `moduleID` int(10) unsigned NOT NULL,
  `id` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  PRIMARY KEY (`counter`),
  KEY `FK_modulesettings_1` (`moduleID`),
  CONSTRAINT `FK_openhierarchy_foreground_module_settings_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2480 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_foreground_module_settings: ~37 rows (approximately)
DELETE FROM `openhierarchy_foreground_module_settings`;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_settings` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_module_settings` (`counter`, `moduleID`, `id`, `value`) VALUES
	(2, 15, 'menuItemType', 'MENUITEM'),
	(3, 5, 'menuItemType', 'MENUITEM'),
	(4, 8, 'menuItemType', 'MENUITEM'),
	(5, 18, 'connectionTimeout', '10000'),
	(6, 18, 'daoFactoryClass', 'se.unlogic.hierarchy.modules.mailsenders.persisting.daos.mysql.MySQLMailDAOFactory'),
	(7, 18, 'databaseID', '3'),
	(8, 18, 'exceptionInterval', '60000'),
	(9, 18, 'host', 'localhost'),
	(10, 18, 'maxExceptionCount', '5'),
	(11, 18, 'maxQueueSize', '50'),
	(12, 18, 'maxResendCount', '3'),
	(13, 18, 'menuItemType', 'MENUITEM'),
	(14, 18, 'noWorkInterval', '10000'),
	(15, 18, 'poolSize', '10'),
	(16, 18, 'port', '25'),
	(17, 18, 'priority', '0'),
	(18, 18, 'queueFullInterval', '3000'),
	(19, 18, 'resendInterval', '30'),
	(20, 18, 'shutdownTimeout', '60000'),
	(21, 18, 'useAuth', 'false'),
	(29, 19, 'menuItemType', 'MENUITEM'),
	(30, 11, 'menuItemType', 'MENUITEM'),
	(31, 14, 'passwordAlgorithm', 'MySQL'),
	(2425, 13, 'enableGlobalContentLinks', 'true'),
	(2426, 13, 'globalContentLinksFile', 'defaultGlobalContentLinks.properties'),
	(2445, 2, 'menuItemType', 'MENUITEM'),
	(2446, 2, 'redirectURL', '/'),
	(2455, 9, 'diskThreshold', '100'),
	(2456, 9, 'filestore', '/www/'),
	(2457, 9, 'pageViewModuleAlias', 'page'),
	(2458, 9, 'pageViewModuleName', 'Page viewer'),
	(2459, 9, 'pageViewModuleXSLPath', 'PageViewModule.sv.xsl'),
	(2460, 9, 'pageViewModuleXSLPathType', 'Classpath'),
	(2461, 3, 'allowAdminAdministration', 'true'),
	(2462, 3, 'allowGroupAdministration', 'true'),
	(2463, 3, 'filteringField', 'FIRSTNAME'),
	(2464, 3, 'menuItemType', 'MENUITEM');
/*!40000 ALTER TABLE `openhierarchy_foreground_module_settings` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_foreground_module_users
CREATE TABLE IF NOT EXISTS `openhierarchy_foreground_module_users` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`moduleID`,`userID`),
  CONSTRAINT `FK_openhierarchy_foreground_module_users_openhierarchy` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_foreground_module_users: ~1 rows (approximately)
DELETE FROM `openhierarchy_foreground_module_users`;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_users` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_module_users` (`moduleID`, `userID`) VALUES
	(3, 1);
/*!40000 ALTER TABLE `openhierarchy_foreground_module_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_menu_index
CREATE TABLE IF NOT EXISTS `openhierarchy_menu_index` (
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
  CONSTRAINT `FK_openhierarchy_menu_index_openhierarchy_foreground_modules` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_openhierarchy_menu_index_openhierarchy_sections` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_openhierarchy_menu_index_openhierarchy_sections_2` FOREIGN KEY (`subSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_openhierarchy_menu_index_openhierarchy_virtual_menu_items` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 206848 kB; (`sectionID`) REFER `foraldramotet-o';

-- Dumping data for table openhierarchy-system.openhierarchy_menu_index: ~44 rows (approximately)
DELETE FROM `openhierarchy_menu_index`;
/*!40000 ALTER TABLE `openhierarchy_menu_index` DISABLE KEYS */;
INSERT INTO `openhierarchy_menu_index` (`menuIndexID`, `sectionID`, `menuIndex`, `moduleID`, `uniqueID`, `subSectionID`, `menuItemID`) VALUES
	(182, 2, 3, 3, '16', NULL, NULL),
	(183, 2, 4, 5, '25', NULL, NULL),
	(185, 2, 8, 10, '40', NULL, NULL),
	(186, 2, 5, 8, 'se.unlogic.hierarchy.modules.menuadmin.MenuAdminModule', NULL, NULL),
	(187, 2, 12, 6, '28', NULL, NULL),
	(188, 2, 15, 9, '39', NULL, NULL),
	(190, 1, 36, NULL, NULL, 2, NULL),
	(192, 2, 1, 8, 'menuadmin53', NULL, NULL),
	(214, 2, 16, 11, '56', NULL, NULL),
	(229, 2, 14, 8, 'menuadmin67', NULL, NULL),
	(230, 2, 13, 8, 'menuadmin68', NULL, NULL),
	(235, 2, 17, 12, '70', NULL, NULL),
	(237, 1, 28, NULL, NULL, 5, NULL),
	(265, 2, 2, NULL, NULL, NULL, 53),
	(268, 2, 10, 8, '37', NULL, NULL),
	(277, 1, 42, 2, '9', NULL, NULL),
	(278, 2, 6, 15, '79', NULL, NULL),
	(279, 2, 9, 16, '80', NULL, NULL),
	(327, 2, 7, 17, '110', NULL, NULL),
	(377, 1, 44, 9, '8', NULL, NULL),
	(378, 1, 41, 9, '9', NULL, NULL),
	(379, 1, 46, 9, '7', NULL, NULL),
	(389, 2, 38, 18, '140', NULL, NULL),
	(390, 2, 19, 19, '141', NULL, NULL),
	(452, 2, 53, 4, '17', NULL, NULL),
	(454, 1, 30, NULL, NULL, 6, NULL),
	(461, 1, 31, NULL, NULL, 7, NULL),
	(462, 1, 32, NULL, NULL, 8, NULL),
	(467, 1, 43, 2, '2', NULL, NULL),
	(468, 2, 58, 5, '5', NULL, NULL),
	(469, 2, 57, 16, '16', NULL, NULL),
	(470, 2, 62, 10, '10', NULL, NULL),
	(471, 2, 59, 8, '8', NULL, NULL),
	(472, 2, 64, 11, '11', NULL, NULL),
	(473, 2, 63, 12, '12', NULL, NULL),
	(475, 2, 54, 3, '3', NULL, NULL),
	(476, 2, 55, 15, '15', NULL, NULL),
	(477, 2, 56, 4, '4', NULL, NULL),
	(478, 2, 60, 6, '6', NULL, NULL),
	(479, 2, 61, 9, '9', NULL, NULL),
	(480, 1, 47, 1, '1', NULL, NULL),
	(489, 1, 39, NULL, NULL, NULL, 55),
	(490, 2, 65, NULL, NULL, NULL, 55),
	(492, 1, 45, NULL, NULL, NULL, 56),
	(500, 1, 48, 9, '1', NULL, NULL);
/*!40000 ALTER TABLE `openhierarchy_menu_index` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_sections
CREATE TABLE IF NOT EXISTS `openhierarchy_sections` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parentSectionID` int(10) unsigned DEFAULT NULL,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
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
  CONSTRAINT `FK_openhierarchy_sections_openhierarchy_sections` FOREIGN KEY (`parentSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_sections: ~2 rows (approximately)
DELETE FROM `openhierarchy_sections`;
/*!40000 ALTER TABLE `openhierarchy_sections` DISABLE KEYS */;
INSERT INTO `openhierarchy_sections` (`sectionID`, `parentSectionID`, `alias`, `enabled`, `anonymousAccess`, `userAccess`, `adminAccess`, `visibleInMenu`, `breadCrumb`, `name`, `description`, `anonymousDefaultURI`, `userDefaultURI`, `requiredProtocol`) VALUES
	(1, NULL, 'firstpage', 1, 1, 1, 1, 1, 1, 'Förstasidan', 'Förstasidan...', '/login', '/page/firstpage', NULL),
	(2, 1, 'sysadmin', 1, 0, 0, 1, 1, 1, 'Systemadmin', 'System administration', '/useradmin', '/useradmin', NULL);
/*!40000 ALTER TABLE `openhierarchy_sections` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_section_groups
CREATE TABLE IF NOT EXISTS `openhierarchy_section_groups` (
  `sectionID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`sectionID`,`groupID`),
  CONSTRAINT `FK_openhierarchy_section_groups_openhierarchy_sections` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_section_groups: ~0 rows (approximately)
DELETE FROM `openhierarchy_section_groups`;
/*!40000 ALTER TABLE `openhierarchy_section_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_section_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_section_users
CREATE TABLE IF NOT EXISTS `openhierarchy_section_users` (
  `sectionID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`sectionID`,`userID`),
  CONSTRAINT `FK_openhierarchy_section_users_openhierarchy_sections` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_section_users: ~0 rows (approximately)
DELETE FROM `openhierarchy_section_users`;
/*!40000 ALTER TABLE `openhierarchy_section_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_section_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_virtual_menu_items
CREATE TABLE IF NOT EXISTS `openhierarchy_virtual_menu_items` (
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
  CONSTRAINT `FK_menuadmin_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_virtual_menu_items: ~3 rows (approximately)
DELETE FROM `openhierarchy_virtual_menu_items`;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_items` DISABLE KEYS */;
INSERT INTO `openhierarchy_virtual_menu_items` (`menuItemID`, `itemtype`, `name`, `description`, `url`, `anonymousAccess`, `userAccess`, `adminAccess`, `sectionID`) VALUES
	(53, 'TITLE', 'Systemadmin', 'Systemadmin', NULL, 0, 0, 1, 2),
	(55, 'TITLE', 'Förstasidan', 'Förstasidan', NULL, 0, 1, 1, 1);
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_items` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_virtual_menu_item_groups
CREATE TABLE IF NOT EXISTS `openhierarchy_virtual_menu_item_groups` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`menuItemID`,`groupID`),
  CONSTRAINT `FK_openhierarchy_virtual_menu_item_groups` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_virtual_menu_item_groups: ~0 rows (approximately)
DELETE FROM `openhierarchy_virtual_menu_item_groups`;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.openhierarchy_virtual_menu_item_users
CREATE TABLE IF NOT EXISTS `openhierarchy_virtual_menu_item_users` (
  `menuItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`menuItemID`,`userID`),
  CONSTRAINT `FK_virtualmenuitemusers_1` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.openhierarchy_virtual_menu_item_users: ~0 rows (approximately)
DELETE FROM `openhierarchy_virtual_menu_item_users`;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.pages
CREATE TABLE IF NOT EXISTS `pages` (
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 191488 kB; (`sectionID`) REFER `fkdb-system/sec';

-- Dumping data for table openhierarchy-system.pages: ~1 rows (approximately)
DELETE FROM `pages`;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` (`pageID`, `name`, `description`, `text`, `enabled`, `visibleInMenu`, `anonymousAccess`, `userAccess`, `adminAccess`, `sectionID`, `alias`, `breadCrumb`) VALUES
	(1, 'Förstasidan', 'Förstasidan', '<h1>V&auml;lkommen till ProjectTemplate</h1>', '1', 1, 0, 1, 1, 1, 'firstpage', 0);
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.page_groups
CREATE TABLE IF NOT EXISTS `page_groups` (
  `pageID` int(10) unsigned NOT NULL,
  `groupID` int(10) NOT NULL,
  PRIMARY KEY (`pageID`,`groupID`),
  CONSTRAINT `FK_pagegroups_1` FOREIGN KEY (`pageID`) REFERENCES `pages` (`pageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.page_groups: ~0 rows (approximately)
DELETE FROM `page_groups`;
/*!40000 ALTER TABLE `page_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `page_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.page_users
CREATE TABLE IF NOT EXISTS `page_users` (
  `pageID` int(10) unsigned NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`pageID`,`userID`),
  CONSTRAINT `FK_pageusers_1` FOREIGN KEY (`pageID`) REFERENCES `pages` (`pageID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.page_users: ~0 rows (approximately)
DELETE FROM `page_users`;
/*!40000 ALTER TABLE `page_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `page_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.recipients
CREATE TABLE IF NOT EXISTS `recipients` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`),
  KEY `FK_recipient_1` (`emailID`),
  CONSTRAINT `FK_recipient_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.recipients: ~0 rows (approximately)
DELETE FROM `recipients`;
/*!40000 ALTER TABLE `recipients` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipients` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.replyto
CREATE TABLE IF NOT EXISTS `replyto` (
  `tableID` char(36) NOT NULL,
  `emailID` char(36) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`tableID`),
  KEY `FK_replyto_1` (`emailID`),
  CONSTRAINT `FK_replyto_1` FOREIGN KEY (`emailID`) REFERENCES `emails` (`emailID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.replyto: ~0 rows (approximately)
DELETE FROM `replyto`;
/*!40000 ALTER TABLE `replyto` DISABLE KEYS */;
/*!40000 ALTER TABLE `replyto` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.simple_groups
CREATE TABLE IF NOT EXISTS `simple_groups` (
  `groupID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`groupID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.simple_groups: ~0 rows (approximately)
DELETE FROM `simple_groups`;
/*!40000 ALTER TABLE `simple_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `simple_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.simple_group_attributes
CREATE TABLE IF NOT EXISTS `simple_group_attributes` (
  `groupID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`groupID`,`name`),
  CONSTRAINT `FK_simple_group_attributes_simple_groups` FOREIGN KEY (`groupID`) REFERENCES `simple_groups` (`groupID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table openhierarchy-system.simple_group_attributes: ~0 rows (approximately)
DELETE FROM `simple_group_attributes`;
/*!40000 ALTER TABLE `simple_group_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `simple_group_attributes` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.simple_users
CREATE TABLE IF NOT EXISTS `simple_users` (
  `userID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.simple_users: ~1 rows (approximately)
DELETE FROM `simple_users`;
/*!40000 ALTER TABLE `simple_users` DISABLE KEYS */;
INSERT INTO `simple_users` (`userID`, `username`, `password`, `firstname`, `lastname`, `email`, `admin`, `enabled`, `added`, `lastlogin`, `language`, `preferedDesign`) VALUES
	(1, 'admin', '*4ACFE3202A5FF5CF467898FC58AAB1D615029441', 'Admin', 'Adminsson', 'admin@admin.com', 1, 1, '2014-10-15 21:27:00', '2014-10-15 22:23:49', NULL, NULL);
/*!40000 ALTER TABLE `simple_users` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.simple_user_attributes
CREATE TABLE IF NOT EXISTS `simple_user_attributes` (
  `userID` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(4096) NOT NULL,
  PRIMARY KEY (`userID`,`name`),
  CONSTRAINT `FK_simple_user_attributes_simple_users` FOREIGN KEY (`userID`) REFERENCES `simple_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table openhierarchy-system.simple_user_attributes: ~0 rows (approximately)
DELETE FROM `simple_user_attributes`;
/*!40000 ALTER TABLE `simple_user_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `simple_user_attributes` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.simple_user_groups
CREATE TABLE IF NOT EXISTS `simple_user_groups` (
  `userID` int(10) unsigned NOT NULL,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  KEY `FK_usergroups_2` (`groupID`),
  CONSTRAINT `FK_simple_user_groups_simple_groups` FOREIGN KEY (`groupID`) REFERENCES `simple_groups` (`groupID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_usergroups_1` FOREIGN KEY (`userID`) REFERENCES `simple_users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.simple_user_groups: ~0 rows (approximately)
DELETE FROM `simple_user_groups`;
/*!40000 ALTER TABLE `simple_user_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `simple_user_groups` ENABLE KEYS */;


-- Dumping structure for table openhierarchy-system.table_versions
CREATE TABLE IF NOT EXISTS `table_versions` (
  `tableGroupName` varchar(255) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tableGroupName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table openhierarchy-system.table_versions: ~4 rows (approximately)
DELETE FROM `table_versions`;
/*!40000 ALTER TABLE `table_versions` DISABLE KEYS */;
INSERT INTO `table_versions` (`tableGroupName`, `version`) VALUES
	('se.unlogic.hierarchy.core.daos.implementations.mysql.MySQLCoreDAOFactory', 31),
	('se.unlogic.hierarchy.foregroundmodules.groupproviders.SimpleGroupProviderModule', 3),
	('se.unlogic.hierarchy.foregroundmodules.pagemodules.daos.annotated.AnnotatedPageDAOFactory', 3),
	('se.unlogic.hierarchy.foregroundmodules.userproviders.SimpleUserProviderModule', 4);
/*!40000 ALTER TABLE `table_versions` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
