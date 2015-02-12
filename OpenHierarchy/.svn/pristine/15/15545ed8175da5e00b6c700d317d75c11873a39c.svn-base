CREATE TABLE `news` (
  `newsID` int(10) unsigned NOT NULL auto_increment,
  `text` text NOT NULL,
  `title` varchar(45) NOT NULL,
  `posterID` int(10) unsigned NOT NULL,
  `added` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `updated` timestamp NULL default NULL,
  `sectionID` int(10) unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `editorID` int(10) unsigned default NULL,
  PRIMARY KEY  USING BTREE (`newsID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;