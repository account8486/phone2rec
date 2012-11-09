/*
* RFID相关表结构信息
*/

/*
* 表trigger_task增加字段sign_in(是否进行签到）
*/

alter table trigger_task add column sign_in int default 1;

/*
* RFID签到记录表
*/

CREATE TABLE `rfid_sign_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `sign_time` datetime DEFAULT NULL,
  `tag_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COMMENT='RFID签到记录表';