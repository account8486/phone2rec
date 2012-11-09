
/* 酒店 */
DROP TABLE IF EXISTS `hotel`;

CREATE TABLE `hotel` (
  `id` Integer(11) NOT NULL AUTO_INCREMENT,
  `type` smallint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `base_info` varchar(2048) DEFAULT NULL,
  `introduction` varchar(2048) DEFAULT NULL,
  `peripheral_info` varchar(2048) DEFAULT NULL,
  `additional_info` varchar(2048) DEFAULT NULL,
  `linking` varchar(128) DEFAULT NULL,
  `service` varchar(1024) DEFAULT NULL,
  `facilities` varchar(1024) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 会议酒店关联表 */
DROP TABLE IF EXISTS `meeting_hotel`;
CREATE TABLE `meeting_hotel` (
  `meeting_id` Integer(11) NOT NULL,
  `hotel_id` Integer(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 房型 */
DROP TABLE IF EXISTS `hotel_room_type`;
CREATE TABLE `hotel_room_type` (
  `id` Integer(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` Integer(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `area` float DEFAULT NULL,
  `capability` smallint DEFAULT NULL,
  `bed_width` float DEFAULT NULL,
  `additional_info` varchar(2048) DEFAULT NULL,
  `facilities` varchar(1024) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 会议房型关联表 */
DROP TABLE IF EXISTS `meeting_hotel_room_type`;
CREATE TABLE `meeting_hotel_room_type` (
  `meeting_id` Integer(11) NOT NULL,
  `hotel_room_type_id` Integer(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`hotel_room_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 房间 */
DROP TABLE IF EXISTS `hotel_room`;
CREATE TABLE `hotel_room` (
  `id` Integer(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` Integer(11) NOT NULL,
  `hotel_room_type_id` Integer(11) NOT NULL,
  `room_no` varchar(30) DEFAULT NULL,
  `floor` smallint(6) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 房间客户安排 */
DROP TABLE IF EXISTS `hotel_room_user`;
CREATE TABLE `hotel_room_user` (
  `hotel_room_id` Integer(11) NOT NULL,
  `user_id` Integer(11) NOT NULL,
  PRIMARY KEY (`hotel_room_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/** 餐厅 */
DROP TABLE IF EXISTS `dining_room`;
CREATE TABLE `dining_room` (
  `id` Integer(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` Integer(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `business_hours` varchar(128) DEFAULT NULL,
  `table_cnt` Integer(6) DEFAULT NULL,
  `capability` Integer(6) DEFAULT NULL,
  `additional_info` varchar(2048) DEFAULT NULL,
  `floor` varchar(10) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 会议餐厅关联表 */
DROP TABLE IF EXISTS `meeting_dining_room`;
CREATE TABLE `meeting_dining_room` (
  `meeting_id` Integer(11) NOT NULL,
  `dining_room_id` Integer(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`dining_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 酒店图片 */
DROP TABLE IF EXISTS `hotel_image`;
CREATE TABLE `hotel_image` (
  `id` Integer(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` Integer(11) DEFAULT NULL,
  `title` varchar(512) DEFAULT NULL,
  `sort` Integer(6) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `upload_user` Integer(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table meeting_member add column vip varchar(1) default 'N';
alter table meeting_member add column hotel_room_id Integer(11) default null;

INSERT INTO `base_menu` (`type`,`name`,`img_url`,`content_url`,`state`,`terminal_type`,`description`,`default_sort_code`,`modify_time`,`download_url`,`package_name`,`is_Initial`,`meeting_type`) VALUES 
(1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,'WEB','酒店信息',21,NULL,NULL,NULL,1,NULL),
(1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,'WEB','酒店信息',21,NULL,NULL,NULL,1,1),
(1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,'WAP','酒店信息',21,NULL,NULL,NULL,1,NULL),
(1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,'WAP','酒店信息',21,NULL,NULL,NULL,1,1);
