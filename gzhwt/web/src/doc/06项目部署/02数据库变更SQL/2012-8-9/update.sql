#
# 增加签到事件表 
#
DROP TABLE IF EXISTS `sign_event`;

CREATE TABLE `sign_event` (
  `id` int(11) NOT NULL auto_increment,
  `event_name` varchar(200) NOT NULL,
  `begin_time` varchar(20) default NULL,
  `end_time` varchar(20) default NULL,
  `meeting_id` int(11) default NULL,
  `status` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

#
# 修改RFID签到表结构
#
ALTER TABLE rfid_sign_in DROP COLUMN sign_event;
ALTER TABLE rfid_sign_in ADD COLUMN event_id int; 