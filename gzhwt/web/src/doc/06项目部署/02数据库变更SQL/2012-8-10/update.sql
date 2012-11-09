DROP TABLE IF EXISTS `sign_event`;
CREATE TABLE `sign_event` (
  `id` int(11) NOT NULL auto_increment,
  `event_name` varchar(200) NOT NULL,
  `signin_begin_time` varchar(20) default NULL,
  `signin_end_time` varchar(20) default NULL,
  `meeting_id` int(11) default NULL,
  `signout_begin_time` varchar(20) default NULL,
  `signout_end_time` varchar(20) default NULL,
  `sign_date` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `rfid_sign_in`;
CREATE TABLE `rfid_sign_in` (
  `id` int(11) NOT NULL auto_increment,
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `tag_id` varchar(32) default NULL,
  `event_id` int(11) default NULL,
  `sign_date` varchar(10) default NULL,
  `sign_time` varchar(10) default NULL,
  `sign_type` int(11) default '0',
  `sign_state` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='RFIDÇ©µ½¼ÇÂ¼±í';