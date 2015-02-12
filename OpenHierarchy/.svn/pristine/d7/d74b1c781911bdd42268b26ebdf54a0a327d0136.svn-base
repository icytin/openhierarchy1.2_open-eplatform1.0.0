CREATE TABLE `pictures` (
  `pictureID` int(10) unsigned NOT NULL auto_increment,
  `filename` varchar(255) NOT NULL,
  `smallThumb` blob NOT NULL,
  `mediumThumb` mediumblob NOT NULL,
  `galleryID` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`pictureID`),
  UNIQUE KEY `Index_3` (`galleryID`,`filename`),
  KEY `FK_images_1` (`galleryID`),
  CONSTRAINT `FK_images_1` FOREIGN KEY (`galleryID`) REFERENCES `galleries` (`galleryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;