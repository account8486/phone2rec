
#
# Structure for the `answerpaper` table : 
#

DROP TABLE IF EXISTS `answerpaper`;

CREATE TABLE `answerpaper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `itemId` int(11) DEFAULT NULL,
  `paperId` int(11) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `content` text,
  `questionId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;




#
# Structure for the `paper` table : 
#

DROP TABLE IF EXISTS `paper`;

CREATE TABLE `paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `state` int(11) NOT NULL,
  `create_time` date DEFAULT NULL,
  `meetId` int(11) DEFAULT NULL,
  `adminUserId` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



#
# Structure for the `question` table : 
#

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `create_time` date DEFAULT NULL,
  `paperId` int(11) DEFAULT NULL,
  `adminUserId` int(11) DEFAULT NULL,
  `question` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



#
# Structure for the `questionitem` table : 
#

DROP TABLE IF EXISTS `questionitem`;

CREATE TABLE `questionitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `questionId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;





