DROP TABLE IF EXISTS `meeting_sms_template`;

CREATE TABLE `meeting_sms_template` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `type` smallint(6) NOT NULL DEFAULT '0',
   `name` varchar(32) NOT NULL,
   `content` varchar(500) NOT NULL,
   `sign` varchar(64) NOT NULL,
   `meeting_id` int(11) NOT NULL DEFAULT '0',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送模板'