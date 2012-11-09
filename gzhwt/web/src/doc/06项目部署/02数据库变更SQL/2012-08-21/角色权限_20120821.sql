
USE `meeting_guizhou`;

DROP TABLE IF EXISTS `security_role`;

CREATE TABLE `security_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `role_description` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `creator` BIGINT(20) DEFAULT NULL,
  `modify_time` DATETIME DEFAULT NULL,
  `modifier` BIGINT(20) DEFAULT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MYISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

/*Data for the table `security_role` */

INSERT  INTO `security_role`(`id`,`role_name`,`role_description`,`create_time`,`creator`,`modify_time`,`modifier`) VALUES (12,'标准版','标准版','2012-08-20 09:08:39',79,'2012-08-20 16:41:56',79),(11,'基本版','基本版','2012-08-20 09:08:03',79,'2012-08-20 16:01:04',79);

/*Table structure for table `security_role_unit` */

DROP TABLE IF EXISTS `security_role_unit`;

CREATE TABLE `security_role_unit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_id` INT(11) DEFAULT NULL,
  `unit_id` INT(11) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `creator` INT(11) DEFAULT NULL,
  `modify_time` DATETIME DEFAULT NULL,
  `modifier` INT(11) DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `role_id` (`role_id`,`unit_id`)
) ENGINE=MYISAM AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='配置菜单,角色与权限单元配合的一张表';

/*Data for the table `security_role_unit` */

INSERT  INTO `security_role_unit`(`id`,`role_id`,`unit_id`,`create_time`,`creator`,`modify_time`,`modifier`) VALUES (75,12,2,'2012-08-20 09:08:39',79,'2012-08-20 16:41:56',79),(84,11,9,'2012-08-20 16:01:04',79,NULL,NULL),(73,11,5,'2012-08-20 09:08:03',79,'2012-08-20 16:01:04',79),(86,11,15,'2012-08-20 16:01:04',79,NULL,NULL),(85,11,13,'2012-08-20 16:01:04',79,NULL,NULL),(95,12,11,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(111,12,1,'2012-08-20 16:41:56',79,NULL,NULL),(92,12,6,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(91,12,7,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(89,11,41,'2012-08-20 16:01:04',79,NULL,NULL),(88,11,40,'2012-08-20 16:01:04',79,NULL,NULL),(87,11,16,'2012-08-20 16:01:04',79,NULL,NULL),(72,11,4,'2012-08-20 09:08:03',79,'2012-08-20 16:01:04',79),(71,11,2,'2012-08-20 09:08:03',79,'2012-08-20 16:01:04',79),(82,11,1,'2012-08-20 16:01:04',79,NULL,NULL),(78,12,5,'2012-08-20 09:08:40',79,'2012-08-20 16:41:56',79),(83,11,6,'2012-08-20 16:01:04',79,NULL,NULL),(93,12,9,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(96,12,12,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(94,12,10,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(76,12,3,'2012-08-20 09:08:39',79,'2012-08-20 16:41:56',79),(97,12,13,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(98,12,14,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(99,12,15,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(100,12,16,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(101,12,17,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(102,12,18,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(103,12,19,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(104,12,20,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(105,12,26,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(106,12,27,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(107,12,30,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(108,12,31,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(109,12,40,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(110,12,41,'2012-08-20 16:01:35',79,'2012-08-20 16:41:56',79),(112,12,4,'2012-08-20 16:41:56',79,NULL,NULL);

/*Table structure for table `security_unit` */

DROP TABLE IF EXISTS `security_unit`;

CREATE TABLE `security_unit` (
  `id` INT(11) NOT NULL,
  `unit_name` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `unit_description` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `unit_type` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `unit_url` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `unit_parent_id` INT(100) DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `creator` INT(11) DEFAULT NULL,
  `modify_time` DATETIME DEFAULT NULL,
  `modifier` INT(11) DEFAULT NULL,
  `order_code` INT(11) DEFAULT NULL COMMENT '排序码',
  `return_default_menu` TINYINT(1) DEFAULT '0' COMMENT '是否回到默认菜单',
  PRIMARY KEY  (`id`)
) ENGINE=MYISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限单元';

/*Data for the table `security_unit` */

INSERT  INTO `security_unit`(`id`,`unit_name`,`unit_description`,`unit_type`,`unit_url`,`unit_parent_id`,`create_time`,`creator`,`modify_time`,`modifier`,`order_code`,`return_default_menu`) VALUES (1,'会议管理','会议管理','0',NULL,0,'2011-01-01 00:00:00',NULL,NULL,NULL,1,0),(2,'会议列表','会议列表','1','pages/admin/pri/meeting/listMeeting.action?meetingId=${meetingId}',1,NULL,NULL,NULL,NULL,NULL,1),(3,'添加会议','添加会议','1','pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo',1,NULL,NULL,NULL,NULL,NULL,1),(4,'会议安排','会议安排','0',NULL,0,NULL,NULL,NULL,NULL,2,0),(5,'基本信息','基本信息','1','pages/admin/pri/meeting/getMeetingById.action?returnType=manage_baseinfo&meeting.id=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(6,'参会人员','参会人员','1','pages/admin/pri/meeting/getMeetingUsers.action?isAdmin=1&meeting.id=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(7,'会议向导','会议向导','1','admin/pri/meeting/guide_begin.action',1,NULL,NULL,NULL,NULL,NULL,1),(9,'会议议程','会议议程','1','admin/pri/agenda/agendaList.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(10,'分组模版','分组模版','1','admin/pri/group/list.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(11,'会场设置','会场设置','1','pages/admin/pri/seat/ManagerSeat.html?contextPath=${ctx}&meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(12,'资料管理','资料管理','1','admin/pri/meeting/getMeetingFilesPager.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(13,'信息发布','信息发布','1','admin/pri/news/list.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(14,'用餐管理','用餐管理','1','admin/pri/meeting/listMeetingDinner.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(15,'互动管理','互动管理','1','admin/pri/interaction/postList.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(16,'短信管理','短信管理','1','admin/pri/message/getMessageList.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(17,'签到管理','签到管理','1','admin/pri/meeting/card_listRfidSignIn.action?rfidSignIn.meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(18,'卡牌管理','卡牌管理','1','admin/pri/meeting/card_listIssue.action?meetingId=${meetingId}',4,NULL,NULL,NULL,NULL,NULL,0),(19,'接待安排','接待安排','0',NULL,0,NULL,NULL,NULL,NULL,3,0),(20,'会场位置','会场位置','1','pages/admin/pri/meeting/getMeetingById.action?returnType=edit_map&meeting.id=${meetingId}',19,NULL,NULL,NULL,NULL,NULL,0),(21,'酒店管理','酒店管理','1','admin/pri/hotel/list.action?meetingId=${meetingId}',19,NULL,NULL,NULL,NULL,NULL,0),(22,'车辆管理','车辆管理','1','admin/pri/journey/listVehicle.action?meetingId=${meetingId}',19,NULL,NULL,NULL,NULL,NULL,0),(23,'会场互动','会场互动','0',NULL,0,NULL,NULL,NULL,NULL,4,0),(24,'抽奖管理','抽奖管理','1','admin/pri/lucky/lucky_findAllLucky.action?meetingId=${meetingId}',23,NULL,NULL,NULL,NULL,NULL,0),(25,'会场服务','会场服务','1','admin/pri/meeting/liveService_browse.action?meetingId=${meetingId}',23,NULL,NULL,NULL,NULL,NULL,0),(26,'会务管理','会务管理','0',NULL,0,NULL,NULL,NULL,NULL,5,0),(27,'资产管理','资产管理','1','admin/pri/asset/getAssetLst.action?meetingId=${meetingId}',26,NULL,NULL,NULL,NULL,NULL,0),(28,'任务管理','任务管理','1','admin/pri/task/getDetailListPager.action?meetingId=${meetingId}',26,NULL,NULL,NULL,NULL,NULL,0),(30,'界面个性化','界面个性化','0',NULL,0,NULL,NULL,NULL,NULL,6,0),(31,'菜单管理','菜单管理','1','admin/pri/basemenu/getBaseMenuPages.action?meetingId=${meetingId}',30,NULL,NULL,NULL,NULL,NULL,0),(32,'界面定制','界面定制','1','admin/pri/custom/meeting_goViewConfig.action?meetingId=${meetingId}',30,NULL,NULL,NULL,NULL,NULL,0),(40,'个人设置','个人设置','0',NULL,0,NULL,NULL,NULL,NULL,7,0),(41,'修改密码','修改密码','1','pages/admin/pri/user/modifyPwd.jsp',40,NULL,NULL,NULL,NULL,NULL,0);


ALTER TABLE `meeting_guizhou`.`admin_user` 
   ADD COLUMN `security_role_id` INT  NULL AFTER `can_org`;
