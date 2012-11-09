#
# Structure for the `guest` table : 
#

DROP TABLE IF EXISTS `guest`;

CREATE TABLE `guest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `rank` varchar(100) DEFAULT NULL,
  `about` text,
  `meetId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



