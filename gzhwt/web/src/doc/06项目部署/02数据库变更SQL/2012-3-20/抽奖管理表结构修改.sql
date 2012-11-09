#
# Structure for the `lucky` table : 
#

DROP TABLE IF EXISTS `luckyuser`;

CREATE TABLE `luckyuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `luckyId` int(11) DEFAULT NULL,
  `meetingId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table `lucky` add column checked INTEGER ;
