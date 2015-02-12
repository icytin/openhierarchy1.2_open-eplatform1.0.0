-- MySQL dump 10.13  Distrib 5.1.73, for Win64 (unknown)
--
-- Host: localhost    Database: openhierarchy_sample_db
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
-- Table structure for table `blog_comments`
--

DROP TABLE IF EXISTS `blog_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_comments` (
  `commentID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `added` datetime NOT NULL,
  `updated` datetime DEFAULT NULL,
  `message` text NOT NULL,
  `posterID` int(10) unsigned DEFAULT NULL,
  `editorID` int(10) unsigned DEFAULT NULL,
  `posterName` varchar(255) DEFAULT NULL,
  `posterEmail` varchar(255) DEFAULT NULL,
  `posterWebsite` varchar(255) DEFAULT NULL,
  `postID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`commentID`),
  KEY `FK_comments_1` (`postID`),
  CONSTRAINT `FK_comments_1` FOREIGN KEY (`postID`) REFERENCES `blog_posts` (`postID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_comments`
--

LOCK TABLES `blog_comments` WRITE;
/*!40000 ALTER TABLE `blog_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_posts`
--

DROP TABLE IF EXISTS `blog_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_posts` (
  `postID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `added` datetime NOT NULL,
  `updated` datetime DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `message` mediumtext NOT NULL,
  `posterID` int(10) unsigned NOT NULL,
  `editorID` int(10) unsigned DEFAULT NULL,
  `readCount` int(10) unsigned NOT NULL DEFAULT '0',
  `split` tinyint(1) NOT NULL,
  `blogID` varchar(255) NOT NULL,
  PRIMARY KEY (`postID`),
  UNIQUE KEY `Index_2` (`alias`,`blogID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_posts`
--

LOCK TABLES `blog_posts` WRITE;
/*!40000 ALTER TABLE `blog_posts` DISABLE KEYS */;
INSERT INTO `blog_posts` VALUES (9,'sample post 1','2014-08-19 08:13:04',NULL,'A sample blog post','<p>Hello world</p>\r\n<p>Another line here.</p>',1,NULL,0,0,'default'),(10,'another-sample','2014-08-19 08:17:16','2014-08-19 08:43:44','Annother sample post','<p>A snippet from: <a href=\"http://en.wikipedia.org/wiki/Bill_Joy\">http://en.wikipedia.org/wiki/Bill_Joy</a></p>\r\n<p>According to a Salon article, during the early 1980s, DARPA had contracted the company Bolt, Beranek and Newman (BBN) to add TCP/IP to Berkeley UNIX. Joy had been instructed to plug BBN&#39;s stack into Berkeley Unix, but he refused to do so, as he had a low opinion of BBN&#39;s TCP/IP. So, Joy wrote his own high-performance TCP/IP stack. According to John Gage,</p>\r\n<blockquote>\r\n	<p>BBN had a big contract to implement TCP/IP, but their stuff didn&#39;t work, and grad student Joy&#39;s stuff worked. So they had this big meeting and this grad student in a T-shirt shows up, and they said, &quot;How did you do this?&quot; And Bill said, &quot;It&#39;s very simple &mdash; you read the protocol and write the code.&quot;</p>\r\n</blockquote>\r\n<p><br >&nbsp;</p>',1,1,1,0,'default');
/*!40000 ALTER TABLE `blog_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_tags`
--

DROP TABLE IF EXISTS `blog_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog_tags` (
  `postID` int(10) unsigned NOT NULL,
  `tag` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  PRIMARY KEY (`postID`,`tag`) USING BTREE,
  CONSTRAINT `FK_tags_1` FOREIGN KEY (`postID`) REFERENCES `blog_posts` (`postID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_tags`
--

LOCK TABLES `blog_tags` WRITE;
/*!40000 ALTER TABLE `blog_tags` DISABLE KEYS */;
INSERT INTO `blog_tags` VALUES (9,'A tag'),(9,'Another tag'),(9,'One more tag'),(10,'A tag'),(10,'Another tag'),(10,'Dont whine'),(10,'Keep things simple'),(10,'Write good code');
/*!40000 ALTER TABLE `blog_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openhierarchy_background_module_aliases`
--

DROP TABLE IF EXISTS `openhierarchy_background_module_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openhierarchy_background_module_aliases` (
  `moduleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_backgroundmodulealiases_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_background_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_aliases`
--

LOCK TABLES `openhierarchy_background_module_aliases` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_aliases` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_settings`
--

LOCK TABLES `openhierarchy_background_module_settings` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_settings` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_module_slots`
--

LOCK TABLES `openhierarchy_background_module_slots` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_module_slots` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_background_modules`
--

LOCK TABLES `openhierarchy_background_modules` WRITE;
/*!40000 ALTER TABLE `openhierarchy_background_modules` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_data_sources`
--

LOCK TABLES `openhierarchy_data_sources` WRITE;
/*!40000 ALTER TABLE `openhierarchy_data_sources` DISABLE KEYS */;
INSERT INTO `openhierarchy_data_sources` VALUES (4,'jdbc:mysql://localhost:3306','SystemManaged',1,'com.mysql.jdbc.Driver','root','root','FlowEngine',NULL,NULL,NULL,1,'SELECT 1',10,2,0,0,'flowengine-development');
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
  `alias` varchar(255) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `listIndex` int(10) unsigned NOT NULL,
  PRIMARY KEY (`moduleID`,`alias`),
  CONSTRAINT `FK_filtermodulealiases_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_filter_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_module_aliases`
--

LOCK TABLES `openhierarchy_filter_module_aliases` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_module_aliases` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_filter_modules`
--

LOCK TABLES `openhierarchy_filter_modules` WRITE;
/*!40000 ALTER TABLE `openhierarchy_filter_modules` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=877 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_module_settings`
--

LOCK TABLES `openhierarchy_foreground_module_settings` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_settings` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_module_settings` VALUES (177,93,'changeEmail','true'),(178,93,'changeFirstname','false'),(179,93,'changeLastname','false'),(180,93,'changePassword','true'),(181,93,'changeUsername','true'),(251,39,'csspath','/css/fck.css'),(252,39,'diskThreshold','100'),(253,39,'filestore','c:\\afolder\\anotherfolder'),(254,39,'ramThreshold','20'),(260,8,'adminTimeout','120'),(261,8,'default','true'),(262,8,'logoutModuleAliases','/logout'),(263,8,'menuItemType','SECTION'),(264,8,'userTimeout','30'),(266,9,'menuItemType','SECTION'),(277,61,'menuItemType','MENUITEM'),(570,56,'menuItemType','MENUITEM'),(667,16,'allowAdminAdministration','true'),(668,16,'allowGroupAdministration','true'),(669,16,'filteringField','USERNAME'),(670,16,'menuItemType','MENUITEM'),(724,102,'adminUserIDs','1'),(725,102,'allowAnonymousComments','true'),(726,102,'archiveBundleLimit','10'),(727,102,'blogID','default'),(728,102,'blogPostsPerPage','3'),(729,102,'cssPath','/css/fck.css'),(730,102,'diskThreshold','100'),(731,102,'displayFullName','false'),(732,102,'enableRSSFeed','false'),(733,102,'feedDescription','Description of My Blog...'),(734,102,'feedItemsPerChannel','0'),(735,102,'feedLanguage','sv-se'),(736,102,'feedLinkToChannel','http://subdomain.domain.tld/blog'),(737,102,'feedTTL','60'),(738,102,'feedTitle','My Blog'),(739,102,'filestorePath','c:\\afolder\\anotherfolder'),(740,102,'maxCommentLength','1000'),(741,102,'menuItemType','MENUITEM'),(742,102,'ramThreshold','20'),(743,102,'showArchiveBundle','true'),(744,102,'showMainMenuitem','false'),(745,102,'showTagsBundle','true'),(746,102,'tagBundleLimit','10'),(749,95,'cssPath','/css/fck.css'),(750,95,'invitationModuleAlias','invitation'),(751,95,'invitationModuleName','Invitations'),(752,95,'invitationModuleXSLPath','InvitationModule.en.xsl'),(753,95,'invitationModuleXSLPathType','Classpath'),(754,95,'menuItemType','MENUITEM'),(859,119,'checkbox','true'),(860,119,'dropdownreq','0'),(861,119,'multilistreq','0'),(862,119,'multilistreq','1'),(863,119,'multilistreq','2'),(864,119,'multilistreq','3'),(865,119,'radiobuttonreq','1'),(866,119,'textarea','default textarea value\r\nline two'),(867,119,'textfield','default text field value'),(868,94,'menuItemType','MENUITEM'),(869,71,'formStyleSheet','SimpleUserProviderForm.en.xsl'),(870,71,'includeDebugData','false'),(871,71,'menuItemType','MENUITEM'),(872,71,'passwordAlgorithm','MySQL'),(873,71,'priority','0'),(874,71,'userTypeName','SimpleUser'),(875,116,'menuItemType','MENUITEM'),(876,116,'priority','0');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_module_users`
--

LOCK TABLES `openhierarchy_foreground_module_users` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_module_users` DISABLE KEYS */;
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
  CONSTRAINT `FK_modules_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_modules_2` FOREIGN KEY (`dataSourceID`) REFERENCES `openhierarchy_data_sources` (`dataSourceID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_foreground_modules`
--

LOCK TABLES `openhierarchy_foreground_modules` WRITE;
/*!40000 ALTER TABLE `openhierarchy_foreground_modules` DISABLE KEYS */;
INSERT INTO `openhierarchy_foreground_modules` VALUES (8,'se.unlogic.hierarchy.foregroundmodules.login.UserProviderLoginModule','Login','login','Login','LoginModule.en.xsl','Classpath',1,0,0,1,1,1,NULL,NULL,NULL),(9,'se.unlogic.hierarchy.foregroundmodules.logout.LogoutModule','Logout','logout','Logout','LogoutModule.en.xsl','Classpath',0,1,1,1,1,1,NULL,NULL,NULL),(16,'se.unlogic.hierarchy.foregroundmodules.useradmin.UserAdminModule','Users','useradmin','User administration','UserAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(17,'se.unlogic.hierarchy.foregroundmodules.userprofile.UserProfileModule','User profile','userprofile','User profile','userprofile.xsl','Classpath',0,1,1,1,1,0,NULL,NULL,NULL),(37,'se.unlogic.hierarchy.foregroundmodules.menuadmin.MenuAdminModule','Menu','menuadmin','Menu administration','MenuAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(39,'se.unlogic.hierarchy.foregroundmodules.pagemodules.PageAdminModule','Pages','pageadmin','Page administration','PageAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontentadmin',NULL),(40,'se.unlogic.hierarchy.foregroundmodules.test.XSLReload','Reload XSL','xslreload','Module for reloading XSL stylesheet',NULL,NULL,0,0,1,1,1,6,NULL,'staticcontent',NULL),(56,'se.unlogic.hierarchy.foregroundmodules.runtimeinfo.RuntimeInfoModule','Runtime info','runtimeinfo','Visar info om minne och processorer','RuntimeInfoModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(61,'se.unlogic.hierarchy.foregroundmodules.systemadmin.SystemAdminModule','Modules & Sections','systemadmin','System administration','SystemAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(64,'se.unlogic.hierarchy.foregroundmodules.threadinfo.ThreadInfoModule','Thread info','threadinfo','List all active threads','ThreadInfoModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(67,'se.unlogic.hierarchy.foregroundmodules.staticcontent.StaticContentModule','Staticcontent','static','Modul som möjligör åtkomt till statiska filer i classpath',NULL,NULL,1,1,1,1,0,1,NULL,NULL,NULL),(69,'se.unlogic.hierarchy.foregroundmodules.usersessionadmin.UserSessionAdminModule','Logged in users','sessionadmin','Currently logged in users','UserSessionAdminModule.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(70,'se.unlogic.hierarchy.foregroundmodules.groupadmin.GroupAdminModule','Groups','groups','Group administration','GroupAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(71,'se.unlogic.hierarchy.foregroundmodules.userproviders.SimpleUserProviderModule','SimpleUserProvider','userprovider','Provides users and groups from multiple sources',NULL,NULL,0,1,0,1,0,4,NULL,'/se/unlogic/hierarchy/foregroundmodules/useradmin/staticcontent',NULL),(93,'se.unlogic.hierarchy.foregroundmodules.userprofile.UserProfileModule','Edit profile','profile','Edit your profile at unlogic.se','UserProfileModule.en.xsl','Classpath',0,1,1,1,0,1,NULL,NULL,NULL),(94,'se.unlogic.hierarchy.foregroundmodules.mailsenders.dummy.DummyEmailSenderModule','Dummy E-mail Sender','dummy-email-sender','Pretends sending e-mails',NULL,NULL,0,0,1,1,1,4,NULL,NULL,NULL),(95,'se.unlogic.hierarchy.foregroundmodules.invitation.SimpleInvitationAdminModule','Invitations','invitations','Administrates invitations','InvitationAdminModule.sv.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontentadmin',NULL),(102,'se.unlogic.hierarchy.foregroundmodules.blog.BlogModule','Blog','myblog','My blog','BlogModule.en.xsl','Classpath',1,1,1,1,1,12,NULL,'staticcontent',NULL),(108,'se.unlogic.hierarchy.foregroundmodules.resolver.ResolverModule','Resolver','resolver','Resolver module','ResolverModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,NULL,NULL),(109,'se.unlogic.hierarchy.foregroundmodules.datasourceadmin.DataSourceAdminModule','Datasources','datasources','Datasources','DataSourceAdminModule.en.xsl','Classpath',0,0,1,1,1,4,NULL,'staticcontent',NULL),(116,'se.unlogic.hierarchy.foregroundmodules.groupproviders.SimpleGroupProviderModule','SimpleGroupProvider','simplegroupprovider','A group provider for simple groups',NULL,NULL,0,1,0,1,0,4,NULL,'staticcontent',NULL),(117,'se.unlogic.hierarchy.foregroundmodules.test.rest.RESTTestModule','REST','resttest','REST Test module',NULL,NULL,0,1,0,1,1,6,NULL,NULL,NULL),(118,'se.unlogic.hierarchy.foregroundmodules.test.ExceptionModule','Exceptions','exception','A module for testing the exception handling in OpenHierarchy',NULL,NULL,1,1,0,1,1,6,NULL,NULL,NULL),(119,'se.unlogic.hierarchy.foregroundmodules.test.SettingTestModule','Settings','settingtest','A module for testing module settings',NULL,NULL,1,1,0,1,1,6,NULL,NULL,NULL);
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
  UNIQUE KEY `UniqueID / ModuleID` (`moduleID`,`uniqueID`,`sectionID`) USING BTREE,
  UNIQUE KEY `Index_5` (`sectionID`,`subSectionID`),
  KEY `FK_menuindex_3` (`subSectionID`),
  KEY `FK_menuindex_4` (`menuItemID`),
  CONSTRAINT `FK_menuindex_1` FOREIGN KEY (`moduleID`) REFERENCES `openhierarchy_foreground_modules` (`moduleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_2` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_3` FOREIGN KEY (`subSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_menuindex_4` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=379 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 206848 kB; (`sectionID`) REFER `foraldramotet-o';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_menu_index`
--

LOCK TABLES `openhierarchy_menu_index` WRITE;
/*!40000 ALTER TABLE `openhierarchy_menu_index` DISABLE KEYS */;
INSERT INTO `openhierarchy_menu_index` VALUES (182,4,23,16,'16',NULL,NULL),(185,4,24,40,'40',NULL,NULL),(186,4,6,37,'se.unlogic.hierarchy.modules.menuadmin.MenuAdminModule',NULL,NULL),(188,4,25,39,'39',NULL,NULL),(190,1,31,NULL,NULL,4,NULL),(214,4,40,56,'56',NULL,NULL),(222,4,26,61,'61',NULL,NULL),(226,4,27,64,'64',NULL,NULL),(230,1,18,64,'64',NULL,NULL),(231,1,25,NULL,NULL,6,NULL),(235,4,22,37,'37',NULL,NULL),(236,6,1,NULL,NULL,4,NULL),(249,1,32,8,'8',NULL,NULL),(250,1,36,9,'9',NULL,NULL),(251,4,21,69,'69',NULL,NULL),(252,4,19,70,'70',NULL,NULL),(259,1,3,NULL,NULL,12,NULL),(289,1,29,93,'93',NULL,NULL),(290,6,10,64,'64',NULL,NULL),(292,4,28,94,'94',NULL,NULL),(293,4,29,95,'95',NULL,NULL),(295,4,39,NULL,NULL,NULL,1),(307,12,1,102,'102',NULL,NULL),(308,12,2,102,'archiveBundle',NULL,NULL),(309,12,3,102,'tagBundle',NULL,NULL),(320,4,34,39,'28',NULL,NULL),(336,1,33,NULL,NULL,NULL,2),(337,4,45,NULL,NULL,NULL,2),(338,1,34,108,'108',NULL,NULL),(339,4,46,108,'108',NULL,NULL),(349,1,35,39,'49',NULL,NULL),(350,4,17,109,'109',NULL,NULL),(356,4,43,39,'54',NULL,NULL),(360,6,15,NULL,NULL,NULL,3),(362,6,16,NULL,NULL,NULL,4),(365,1,38,39,'63',NULL,NULL),(370,4,44,117,'117',NULL,NULL),(371,1,39,118,'118',NULL,NULL),(372,1,40,119,'119',NULL,NULL),(373,6,22,119,'119',NULL,NULL),(374,6,23,118,'118',NULL,NULL),(375,6,24,117,'117',NULL,NULL),(376,6,25,40,'40',NULL,NULL),(377,4,41,NULL,NULL,NULL,6),(378,4,35,NULL,NULL,NULL,7);
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
  CONSTRAINT `FK_sections_1` FOREIGN KEY (`parentSectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_sections`
--

LOCK TABLES `openhierarchy_sections` WRITE;
/*!40000 ALTER TABLE `openhierarchy_sections` DISABLE KEYS */;
INSERT INTO `openhierarchy_sections` VALUES (1,NULL,'OpenHierarchy sample site',1,1,1,1,1,1,'Home','Unlogic.se','/page/firstpage','/page/firstpage',NULL),(4,1,'administration',1,0,0,1,1,1,'Adm.','System administration','/sessionadmin','/sessionadmin',NULL),(6,1,'test',1,1,1,1,1,1,'Test modules','Various test and sample modules','/page/test','/page/test',NULL),(12,1,'blogs',1,1,1,1,1,1,'Blog','My blog about varoius things in life','/myblog','/myblog',NULL);
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
  PRIMARY KEY (`menuItemID`,`groupID`) USING BTREE,
  CONSTRAINT `FK_virtualmenuitemgroups_1` FOREIGN KEY (`menuItemID`) REFERENCES `openhierarchy_virtual_menu_items` (`menuItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_virtual_menu_item_groups`
--

LOCK TABLES `openhierarchy_virtual_menu_item_groups` WRITE;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_item_groups` DISABLE KEYS */;
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
  PRIMARY KEY (`menuItemID`) USING BTREE,
  KEY `FK_menuadmin_1` (`sectionID`),
  CONSTRAINT `FK_virtualmenuitems_1` FOREIGN KEY (`sectionID`) REFERENCES `openhierarchy_sections` (`sectionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openhierarchy_virtual_menu_items`
--

LOCK TABLES `openhierarchy_virtual_menu_items` WRITE;
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_items` DISABLE KEYS */;
INSERT INTO `openhierarchy_virtual_menu_items` VALUES (1,'TITLE','Server status','Server status header',NULL,0,0,1,4),(2,'TITLE','Tools','Tools',NULL,0,0,1,4),(3,'BLANK',NULL,NULL,NULL,1,1,1,6),(4,'TITLE','Test modules','A set of test modules',NULL,1,1,1,6),(6,'BLANK',NULL,NULL,NULL,1,1,1,4),(7,'BLANK',NULL,NULL,NULL,1,1,1,4);
/*!40000 ALTER TABLE `openhierarchy_virtual_menu_items` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 191488 kB; (`sectionID`) REFER `fkdb-system/sec';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` VALUES (8,'Firstpage','The firstpage of this site','<h1>Welcome to the OpenHierachy sample site</h1>\r\n<p>Welcome to the OpenHierarchy sample site.</p>\r\n<p>Login with username <strong>openhierarchy</strong> and password <strong>sample</strong> and then change the credentials right away.</p>\r\n','1',0,1,1,1,1,'firstpage',0),(15,'Test','Test modules intro page','<h1>Test modules</h1>\r\n<p style=\"text-align: left;\">In this sections you&#39;ll find a few OpenHierarchy test modules. Have a look source files in the package <strong>se.unlogic.hierarchy.foregroundmodules.test</strong> for further information on what they do.</p>\r\n','1',0,1,1,1,6,'test',0);
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_groups`
--

LOCK TABLES `simple_groups` WRITE;
/*!40000 ALTER TABLE `simple_groups` DISABLE KEYS */;
INSERT INTO `simple_groups` VALUES (8,'Default group','Default group',1),(10,'Administrators','Group for administrators',1);
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
INSERT INTO `simple_user_groups` VALUES (1,8),(1,10);
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
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=latin1 COMMENT='Users table;InnoDB free: 274432 kB';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `simple_users`
--

LOCK TABLES `simple_users` WRITE;
/*!40000 ALTER TABLE `simple_users` DISABLE KEYS */;
INSERT INTO `simple_users` VALUES (1,'openhierarchy','*18CEC0B12394150D87DD1C214207742851272BA6','John','Doe','foo@bar.not.set',1,1,'2008-01-27 19:43:42','2014-08-19 08:12:56',NULL,NULL);
/*!40000 ALTER TABLE `simple_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_versions`
--

DROP TABLE IF EXISTS `table_versions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_versions` (
  `tableGroupName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tableGroupName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_versions`
--

LOCK TABLES `table_versions` WRITE;
/*!40000 ALTER TABLE `table_versions` DISABLE KEYS */;
INSERT INTO `table_versions` VALUES ('se.unlogic.hierarchy.core.daos.implementations.mysql.MySQLCoreDAOFactory',31),('se.unlogic.hierarchy.foregroundmodules.forum.daos.mysql.MySQLForumDAOFactory',3),('se.unlogic.hierarchy.foregroundmodules.registration.AnnotatedConfirmationRegistrationModule',2),('se.unlogic.hierarchy.foregroundmodules.userproviders.SimpleUserProviderModule',4),('se.unlogic.hierarchy.foregroundmodules.pagemodules.daos.annotated.AnnotatedPageDAOFactory',3),('se.unlogic.hierarchy.foregroundmodules.blog.daos.mysql.MySQLBlogDAOFactory',2),('com.nordicpeak.filearchive.FileArchiveModule',3),('se.unlogic.hierarchy.foregroundmodules.invitation.SimpleInvitationAdminModule',1),('com.nordicpeak.flowengine.queries.checkboxquery.CheckboxQueryProviderModule',4),('com.nordicpeak.flowengine.dao.FlowEngineDAOFactory',3),('se.unlogic.hierarchy.foregroundmodules.groupproviders.SimpleGroupProviderModule',3);
/*!40000 ALTER TABLE `table_versions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_invitation_type_groups`
--

DROP TABLE IF EXISTS `user_invitation_type_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitation_type_groups` (
  `invitationTypeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `groupID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`invitationTypeID`,`groupID`) USING BTREE,
  CONSTRAINT `FK_invitationtypegroups_1` FOREIGN KEY (`invitationTypeID`) REFERENCES `user_invitation_types` (`invitationTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_invitation_type_groups`
--

LOCK TABLES `user_invitation_type_groups` WRITE;
/*!40000 ALTER TABLE `user_invitation_type_groups` DISABLE KEYS */;
INSERT INTO `user_invitation_type_groups` VALUES (1,4),(2,7),(3,6),(4,5);
/*!40000 ALTER TABLE `user_invitation_type_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_invitation_types`
--

DROP TABLE IF EXISTS `user_invitation_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitation_types` (
  `invitationTypeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `senderName` varchar(255) NOT NULL,
  `senderEmail` varchar(255) NOT NULL,
  `registrationText` text NOT NULL,
  `registeredText` text NOT NULL,
  PRIMARY KEY (`invitationTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_invitation_types`
--

LOCK TABLES `user_invitation_types` WRITE;
/*!40000 ALTER TABLE `user_invitation_types` DISABLE KEYS */;
INSERT INTO `user_invitation_types` VALUES (1,'OpenHierarchy Developers','OpenHierarchy developer invitation','Hello $recipient-firstname!\r\n\r\nYou have been invited to Unlogic.se (www.unlogic.se) as an OpenHierarchy developer.\r\n\r\nClick on the link below to create an account:\r\n\r\n$invitation-link\r\n\r\nWith your account at Unlogic.se you will be able to access the issuetracker for OpenHierarchy, Utils and other related projects.\r\n\r\n/Robert \"Unlogic\" Olofsson','Robert \"Unlogic\" Olofsson','unlogic@unlogic.se','',''),(2,'Patrik Hermanssons svensexa','Bilder och filmer från svensexan!','Hej $recipient-firstname!\r\n\r\nHär kommer en inbjudan till min hemsida (www.unlogic.se).\r\n\r\nKlicka på på länken nedan för välja användarnamn och lösenord:\r\n\r\n$invitation-link\r\n\r\nEfter att du registrerat dig och loggat in kommer du att kunna se dom bilder som övriga deltagarna på svensexan laddat upp, ladda upp dina egna bilder samt skriva bild kommentarer.\r\n\r\nInom kort kommer även filmen från svensexan att publiceras på hemsida. \r\n\r\n/Robban','Robert Olofsson','unlogic@unlogic.se','',''),(3,'Relatives','Inbjudan till min hemsida!','Hej $recipient-firstname, här kommer en inbjudan till min hemsida!\r\n\r\nJag håller på att lägga ut alla de bilder som Malin och jag knäppt genom åren på min hemsida. Det är bilder från Julfiranden, födelsedagar, Oliver mm.\r\n\r\nFör att inte vem som helst ska komma åt bilderna har jag gjort så att man måste ha ett konto på min hemsida för att se bilderna så därför kommer här en inbjudan.\r\n\r\nKlicka på länken nedan för att skapa ett konto på min hemsida:\r\n\r\n$invitation-link\r\n\r\nEfter att du skapat ditt konto är det bara trycka på \"Login\" länken uppe till höger på sidan och logga in.\r\n\r\nNär du gjort detta kommer du åt mängder med bilder i de olika gallerierna som du hittar under \"Galleries\" länken som ligger strax under logotypen på sidan.\r\n\r\nSidan är till stora delar på utrikiska (Engelska) men dom flesta bild gallerier är på svenska och det går alldeles utmärkt att skriva svenska kommentarer till bilderna i galleriet.\r\n\r\nPs. Hör av dig om det är något du funderar över!\r\n\r\n/Robban','Robert Olofsson','unlogic@unlogic.se','',''),(4,'Friends (swedish)','Inbjudan till Unlogic.se','Hej $recipient-firstname!\r\n\r\nHär kommer en inbjudan till min hemsida Unlogic.se!\r\n\r\nKlicka på länken nedan för skapa ett konto:\r\n\r\n$invitation-link\r\n\r\n/Robban\r\n','Robert Olofsson','unlogic@unlogic.se','','');
/*!40000 ALTER TABLE `user_invitation_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_invitations`
--

DROP TABLE IF EXISTS `user_invitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_invitations` (
  `invitationID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invitationTypeID` int(10) unsigned NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `linkID` varchar(36) NOT NULL,
  `sendCount` int(10) unsigned NOT NULL,
  `lastSent` datetime DEFAULT NULL,
  PRIMARY KEY (`invitationID`),
  UNIQUE KEY `Index_3` (`email`),
  KEY `FK_Invitations_1` (`invitationTypeID`),
  CONSTRAINT `FK_Invitations_1` FOREIGN KEY (`invitationTypeID`) REFERENCES `user_invitation_types` (`invitationTypeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_invitations`
--

LOCK TABLES `user_invitations` WRITE;
/*!40000 ALTER TABLE `user_invitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_invitations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-19 10:14:49
