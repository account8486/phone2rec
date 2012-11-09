#
# Structure for the `lucky` table : 
#

DROP TABLE IF EXISTS `lucky`;

CREATE TABLE `lucky` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `award` varchar(200) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `meetId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#
# Structure for the `luckyhistory` table : 
#

DROP TABLE IF EXISTS `luckyhistory`;

CREATE TABLE `luckyhistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adminId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `luckyId` int(11) DEFAULT NULL,
  `meetId` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
