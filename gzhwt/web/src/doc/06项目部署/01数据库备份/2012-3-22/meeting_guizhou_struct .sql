# SQL Manager 2005 for MySQL 3.6.5.8
# ---------------------------------------
# Host     : 10.196.136.96
# Port     : 3306
# Database : meeting_guizhou


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES gbk */;

SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `meeting_guizhou`;

CREATE DATABASE `meeting_guizhou`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `meeting_guizhou`;

#
# Structure for the `admin_role` table : 
#

CREATE TABLE `admin_role` (
  `id` int(11) unsigned NOT NULL,
  `role_name` varchar(40) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` char(20) DEFAULT NULL,
  `modifier` char(20) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `admin_user` table : 
#

CREATE TABLE `admin_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(20) COLLATE utf8_bin NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 NOT NULL,
  `name` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `mailbox` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `gender` smallint(6) DEFAULT NULL,
  `birthday` char(8) CHARACTER SET utf8 DEFAULT NULL,
  `idtype` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `idnum` varchar(18) CHARACTER SET utf8 DEFAULT NULL,
  `country` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `city` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `state` smallint(6) NOT NULL DEFAULT '1',
  `comments` varchar(400) CHARACTER SET utf8 DEFAULT NULL,
  `job` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `imei` varchar(128) CHARACTER SET utf8 DEFAULT NULL,
  `tel` varchar(32) CHARACTER SET utf8 DEFAULT NULL,
  `can_org` smallint(6) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `org_id_idx` (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#
# Structure for the `admin_user_role` table : 
#

CREATE TABLE `admin_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `analytics_log` table : 
#

CREATE TABLE `analytics_log` (
  `id` varchar(64) NOT NULL COMMENT 'uuid',
  `access_time` datetime DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `user_id` bigint(11) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `meeting_id` bigint(11) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `from_type` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `params` varchar(5000) DEFAULT NULL,
  `menu_id` bigint(11) DEFAULT NULL,
  `menu_name` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`meeting_id`),
  KEY `NewIndex2` (`user_id`),
  KEY `NewIndex3` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `answerpaper` table : 
#

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `area` table : 
#

CREATE TABLE `area` (
  `area_code` varchar(20) NOT NULL,
  `parent_code` varchar(20) DEFAULT NULL,
  `area_name` varchar(20) NOT NULL,
  `area_level` smallint(6) NOT NULL,
  PRIMARY KEY (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `asset` table : 
#

CREATE TABLE `asset` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `asset_no` varchar(20) NOT NULL,
  `asset_name` varchar(50) NOT NULL,
  `property` varchar(256) DEFAULT NULL,
  `asset_value` decimal(9,2) DEFAULT NULL,
  `storage_date` datetime DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `meeting_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for the `base_menu` table : 
#

CREATE TABLE `base_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(3) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `img_url` varchar(512) DEFAULT NULL,
  `content_url` varchar(512) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `terminal_type` varchar(10) DEFAULT NULL COMMENT '终端类型',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `default_sort_code` int(11) DEFAULT NULL COMMENT '默认排序码,用于初始化菜单',
  `modify_time` datetime DEFAULT NULL,
  `download_url` varchar(512) DEFAULT NULL COMMENT '下载地址',
  `package_name` varchar(512) DEFAULT NULL COMMENT '包名',
  `is_Initial` tinyint(1) DEFAULT '1',
  `meeting_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=831 DEFAULT CHARSET=utf8;

#
# Structure for the `base_module_title` table : 
#

CREATE TABLE `base_module_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `base_menu_id` int(11) NOT NULL,
  `meeting_type_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `title_name` varchar(100) DEFAULT NULL,
  `key_name` varchar(100) DEFAULT NULL,
  `terminal_type` varchar(10) DEFAULT NULL COMMENT '终端类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='基础模块标题';

#
# Structure for the `card` table : 
#

CREATE TABLE `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='RFID卡片信息';

#
# Structure for the `client_menu` table : 
#

CREATE TABLE `client_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(3) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `img_url` varchar(512) DEFAULT NULL,
  `content_url` varchar(512) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `terminal_type` varchar(10) DEFAULT NULL COMMENT '终端类型',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  `meeting_id` int(11) DEFAULT NULL,
  `menu_type` varchar(50) DEFAULT NULL COMMENT 'custome,system',
  `default_sort_code` int(11) DEFAULT NULL COMMENT '默认排序码,用于初始化菜单',
  `content_id` int(11) DEFAULT NULL,
  `base_menu_id` int(11) DEFAULT NULL COMMENT '基础表关联ID',
  `package_name` varchar(512) DEFAULT NULL,
  `download_url` varchar(512) DEFAULT NULL,
  `is_Initial` tinyint(4) DEFAULT '1' COMMENT '是否需要初始化选中',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3087 DEFAULT CHARSET=utf8;

#
# Structure for the `client_version` table : 
#

CREATE TABLE `client_version` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `version` int(6) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

#
# Structure for the `data_dict` table : 
#

CREATE TABLE `data_dict` (
  `data_type` varchar(20) NOT NULL,
  `data_type_name` varchar(60) NOT NULL,
  `data_type_desc` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`data_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

#
# Structure for the `data_dict_config` table : 
#

CREATE TABLE `data_dict_config` (
  `id` int(11) NOT NULL,
  `data_type` varchar(20) NOT NULL,
  `act_type` varchar(20) NOT NULL,
  `data_code` varchar(20) NOT NULL,
  `data_name` varchar(60) NOT NULL,
  `data_desc` varchar(60) DEFAULT NULL,
  `cond` varchar(50) DEFAULT NULL,
  `data_value` varchar(60) DEFAULT NULL,
  `order_by` varchar(60) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='act_type：区分用在不同的活动类型，如：会议、旅游，默认为common';

#
# Structure for the `dining_room` table : 
#

CREATE TABLE `dining_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `business_hours` varchar(128) DEFAULT NULL,
  `table_cnt` int(6) DEFAULT NULL,
  `capability` int(6) DEFAULT NULL,
  `additional_info` varchar(2048) DEFAULT NULL,
  `floor` varchar(10) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Structure for the `dinner_table` table : 
#

CREATE TABLE `dinner_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dinner_id` int(11) NOT NULL,
  `meeting_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL,
  `table_code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meeting_id_idx` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=536 DEFAULT CHARSET=utf8;

#
# Structure for the `gift` table : 
#

CREATE TABLE `gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `introduction` varchar(1024) DEFAULT NULL,
  `description` longtext,
  `modify_time` datetime DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `img_url` varchar(128) DEFAULT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

#
# Structure for the `gift_order` table : 
#

CREATE TABLE `gift_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `gift_id` int(11) NOT NULL,
  `amount` int(6) NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `meeting_id` int(11) unsigned NOT NULL,
  `total_price` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

#
# Structure for the `group_plan` table : 
#

CREATE TABLE `group_plan` (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(100) DEFAULT NULL,
  `plan_type` smallint(6) DEFAULT NULL,
  `plan_desc` varchar(2000) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  `import_file` varchar(512) DEFAULT NULL,
  `is_open` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='state：0无效 1有效\r\nplan_type：0 议程分组 1就餐分组';

#
# Structure for the `group_plan_detail` table : 
#

CREATE TABLE `group_plan_detail` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) DEFAULT NULL,
  `group_name` varchar(100) DEFAULT NULL,
  `group_leader` varchar(200) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `meeting_id` bigint(11) DEFAULT NULL,
  `detail` varchar(1000) DEFAULT NULL,
  `showindex` int(11) DEFAULT '1',
  PRIMARY KEY (`group_id`),
  KEY `group_id_idx` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8 COMMENT='分组计划明细';

#
# Structure for the `group_plan_member` table : 
#

CREATE TABLE `group_plan_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `modify_user` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `sort_code` int(11) DEFAULT '0',
  `comments` mediumtext,
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`group_id`),
  KEY `NewIndex2` (`member_id`),
  KEY `NewIndex3` (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `guest` table : 
#

CREATE TABLE `guest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `rank` varchar(100) DEFAULT NULL,
  `about` text,
  `meetId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

#
# Structure for the `hotel` table : 
#

CREATE TABLE `hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` smallint(6) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Structure for the `hotel_image` table : 
#

CREATE TABLE `hotel_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `title` varchar(512) DEFAULT NULL,
  `sort` int(6) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `upload_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Structure for the `hotel_room` table : 
#

CREATE TABLE `hotel_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `hotel_room_type_id` int(11) NOT NULL,
  `room_no` varchar(30) DEFAULT NULL,
  `floor` smallint(6) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

#
# Structure for the `hotel_room_type` table : 
#

CREATE TABLE `hotel_room_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `area` float DEFAULT NULL,
  `capability` smallint(6) DEFAULT NULL,
  `bed_width` float DEFAULT NULL,
  `additional_info` varchar(2048) DEFAULT NULL,
  `facilities` varchar(1024) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Structure for the `hotel_room_user` table : 
#

CREATE TABLE `hotel_room_user` (
  `hotel_room_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`hotel_room_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `imei_user` table : 
#

CREATE TABLE `imei_user` (
  `imei` varchar(128) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`imei`),
  KEY `imei_idx` (`imei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `issue_card` table : 
#

CREATE TABLE `issue_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `issue_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `card_id` int(11) DEFAULT NULL,
  `loss_time` datetime DEFAULT NULL,
  `loss_reason` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='RFID卡片发放信息';

#
# Structure for the `journey` table : 
#

CREATE TABLE `journey` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `departure` varchar(200) DEFAULT NULL,
  `destination` varchar(200) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  `meeting_id` bigint(11) DEFAULT NULL,
  `vehicle_id` bigint(11) DEFAULT NULL,
  `driver_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

#
# Structure for the `journey_member` table : 
#

CREATE TABLE `journey_member` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` bigint(11) DEFAULT NULL,
  `journey_id` bigint(11) DEFAULT NULL,
  `user_id` bigint(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2288 DEFAULT CHARSET=utf8;

#
# Structure for the `local_specialty` table : 
#

CREATE TABLE `local_specialty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `memo` varchar(2000) DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='全局级别的土特产信息';

#
# Structure for the `lucky` table : 
#

CREATE TABLE `lucky` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `award` varchar(200) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `meetId` int(11) DEFAULT NULL,
  `checked` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

#
# Structure for the `luckyhistory` table : 
#

CREATE TABLE `luckyhistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adminId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `luckyId` int(11) DEFAULT NULL,
  `meetId` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

#
# Structure for the `luckyuser` table : 
#

CREATE TABLE `luckyuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `luckyId` int(11) DEFAULT NULL,
  `meetingId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting` table : 
#

CREATE TABLE `meeting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_name` varchar(200) DEFAULT NULL,
  `meeting_type` int(11) DEFAULT '1',
  `topic` varchar(2000) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `creator` char(20) DEFAULT NULL,
  `modifier` char(20) DEFAULT NULL,
  `location_xy` varchar(255) DEFAULT NULL,
  `access_start_time` datetime DEFAULT NULL,
  `access_end_time` datetime DEFAULT NULL,
  `service_number` varchar(30) NOT NULL,
  `free_sms_num` int(11) DEFAULT NULL,
  `notice` varchar(1000) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `district` varchar(20) DEFAULT NULL,
  `join_time` varchar(200) DEFAULT NULL,
  `pwd_Is_Dynamic` varchar(200) DEFAULT NULL,
  `host` varchar(100) DEFAULT NULL COMMENT '主办方',
  `organizer` varchar(100) DEFAULT NULL COMMENT '承办方',
  `logo_image` varchar(50) DEFAULT NULL COMMENT 'logo图片',
  `theme_id` int(11) DEFAULT NULL COMMENT '主题皮肤id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8 COMMENT='会议组织人员通过会务组织管理模块进行会议创建，会议创建包括确定会议名称、会议主要议题、会议规模、会议开始和结束时间、会议';

#
# Structure for the `meeting_access_log` table : 
#

CREATE TABLE `meeting_access_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `portal_type` smallint(6) DEFAULT NULL,
  `access_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=334 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_agenda` table : 
#

CREATE TABLE `meeting_agenda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) DEFAULT NULL,
  `topic` varchar(128) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `host` varchar(30) DEFAULT NULL,
  `location` varchar(128) DEFAULT NULL,
  `date` char(10) DEFAULT NULL,
  `start_time` char(5) DEFAULT NULL,
  `end_time` char(5) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` smallint(6) DEFAULT NULL,
  `modifier` smallint(6) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `attendee` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meeting_id_idx` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_client_menu` table : 
#

CREATE TABLE `meeting_client_menu` (
  `meeting_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `sort` int(3) DEFAULT NULL,
  `member_level` smallint(6) NOT NULL,
  `terminal_Type` varchar(20) DEFAULT NULL COMMENT '冗余出来,便于查询',
  PRIMARY KEY (`meeting_id`,`menu_id`,`member_level`),
  KEY `meeting_id_idx` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_comment` table : 
#

CREATE TABLE `meeting_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `creator_type` smallint(6) DEFAULT NULL,
  `creator_job` varchar(64) DEFAULT NULL,
  `creator_name` varchar(30) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `comment_body` varchar(512) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modifier` smallint(6) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `post_id_idx` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_content` table : 
#

CREATE TABLE `meeting_content` (
  `meeting_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content_title` varchar(128) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content` longtext,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` char(20) DEFAULT NULL,
  `modifier` char(20) DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `sort_no` int(11) DEFAULT NULL COMMENT '排序号',
  `category` int(11) DEFAULT NULL COMMENT 'web,wap,client0-7组合',
  `icon_img` varchar(255) DEFAULT NULL COMMENT '菜单图片',
  `is_list` varchar(10) DEFAULT NULL COMMENT '1是列表0是单个菜单',
  `parent_id` int(11) DEFAULT NULL COMMENT '属于哪一个list',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_dining_room` table : 
#

CREATE TABLE `meeting_dining_room` (
  `meeting_id` int(11) NOT NULL,
  `dining_room_id` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`dining_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_dinner` table : 
#

CREATE TABLE `meeting_dinner` (
  `dinner_id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) DEFAULT NULL,
  `dinner_date` varchar(50) NOT NULL,
  `dinner_section` char(1) NOT NULL,
  `start_time` varchar(10) NOT NULL,
  `end_time` varchar(10) NOT NULL,
  `dinner_type` char(1) NOT NULL,
  `dinner_address` varchar(60) DEFAULT NULL,
  `creator` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`dinner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='dinner_section： 1 早餐  2 中餐 3 晚餐\r\ndinner_type：1自助餐 2 桌餐';

#
# Structure for the `meeting_files` table : 
#

CREATE TABLE `meeting_files` (
  `meeting_id` int(11) NOT NULL,
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `file_type` smallint(6) DEFAULT NULL,
  `file_name` varchar(128) DEFAULT NULL,
  `file_postfix` varchar(32) DEFAULT NULL,
  `file_path` varchar(128) DEFAULT NULL,
  `file_author` varchar(40) DEFAULT NULL,
  `file_tag` varchar(200) DEFAULT NULL,
  `file_size` double DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` char(20) DEFAULT NULL,
  `modifier` char(20) DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `file_save_name` varchar(100) DEFAULT NULL COMMENT '保存名称',
  `pre_page` int(11) DEFAULT NULL,
  `assort_Id` int(11) DEFAULT NULL COMMENT '分类ID',
  `sort_Code` int(11) DEFAULT NULL COMMENT '排序',
  `file_access` varchar(20) DEFAULT NULL COMMENT '文件权限',
  `download_flg` smallint(3) DEFAULT NULL,
  `preview_flg` smallint(3) DEFAULT NULL,
  `file_cover_path` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10142 DEFAULT CHARSET=utf8 COMMENT='上传的会议文件资料';

#
# Structure for the `meeting_files_assort` table : 
#

CREATE TABLE `meeting_files_assort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assort_name` varchar(50) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='会议资料分类列表';

#
# Structure for the `meeting_group` table : 
#

CREATE TABLE `meeting_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plan_id` int(11) DEFAULT NULL,
  `relation_id` int(11) DEFAULT NULL,
  `type` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `relation_id_idx` (`relation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_hotel` table : 
#

CREATE TABLE `meeting_hotel` (
  `meeting_id` int(11) NOT NULL,
  `hotel_id` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_hotel_room_type` table : 
#

CREATE TABLE `meeting_hotel_room_type` (
  `meeting_id` int(11) NOT NULL,
  `hotel_room_type_id` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`hotel_room_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_member` table : 
#

CREATE TABLE `meeting_member` (
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `member_level` smallint(6) DEFAULT NULL,
  `sort_Code` smallint(6) DEFAULT NULL,
  `mobile_is_display` smallint(6) DEFAULT '1',
  `add_in_contacts` varchar(10) DEFAULT NULL,
  `room_Number` varchar(100) DEFAULT NULL,
  `mailbox` varchar(50) DEFAULT NULL,
  `country` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `room_Number_Is_Display` smallint(6) DEFAULT '1' COMMENT '是否显示房间号,1表示显示,0表示不显示',
  `job_Is_Display` smallint(6) DEFAULT '1' COMMENT ' 1表示显示,0表示不显示',
  `department` varchar(30) DEFAULT NULL COMMENT '单位,与会议代表有关',
  `book_job` varchar(100) DEFAULT NULL COMMENT '通讯录职位，可存储多条',
  `vip` varchar(1) DEFAULT 'N',
  `hotel_room_id` int(11) DEFAULT NULL,
  `upload_authority` varchar(10) DEFAULT 'N' COMMENT '上传权限,Y表示有上传权限,不为Y表示无上传权限',
  PRIMARY KEY (`meeting_id`,`user_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `meeting_id_idx` (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_module_title` table : 
#

CREATE TABLE `meeting_module_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `base_module_title_id` int(11) NOT NULL,
  `base_menu_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `title_name` varchar(100) DEFAULT NULL,
  `key_name` varchar(100) DEFAULT NULL,
  `terminal_type` varchar(10) DEFAULT NULL COMMENT '终端类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=utf8 COMMENT='会议模块标题';

#
# Structure for the `meeting_post` table : 
#

CREATE TABLE `meeting_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) DEFAULT NULL,
  `content` longtext,
  `imgurl` varchar(256) DEFAULT NULL,
  `videourl` varchar(256) DEFAULT NULL,
  `poster` int(11) DEFAULT NULL,
  `view_count` smallint(6) DEFAULT NULL,
  `comment_count` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `checker` smallint(6) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  `poster_name` varchar(30) DEFAULT NULL,
  `poster_job` varchar(64) DEFAULT NULL,
  `poster_type` smallint(6) DEFAULT NULL COMMENT '0: user; 1: admin user',
  PRIMARY KEY (`id`),
  KEY `meeting_id_idx` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=682 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_sms` table : 
#

CREATE TABLE `meeting_sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `messages` varchar(5000) NOT NULL,
  `delay` tinyint(1) NOT NULL DEFAULT '0',
  `sendtime` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `state` smallint(6) NOT NULL DEFAULT '0',
  `comments` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meeting_id_idx` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2005 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_sms_recipient` table : 
#

CREATE TABLE `meeting_sms_recipient` (
  `user_id` int(11) NOT NULL,
  `meeting_sms_id` int(11) NOT NULL,
  `state` smallint(6) NOT NULL DEFAULT '0' COMMENT '0:not send; 1 sent; 9 sent failure;',
  PRIMARY KEY (`user_id`,`meeting_sms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_specialty` table : 
#

CREATE TABLE `meeting_specialty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) DEFAULT NULL,
  `memo` varchar(2000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='会议下面创建的本地特产信息';

#
# Structure for the `meeting_specialty_relative` table : 
#

CREATE TABLE `meeting_specialty_relative` (
  `meeting_specialty_id` int(11) NOT NULL,
  `local_specialty_id` int(11) NOT NULL,
  PRIMARY KEY (`meeting_specialty_id`,`local_specialty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会议特产表与全局土特产表的关联信息中间表';

#
# Structure for the `meeting_task` table : 
#

CREATE TABLE `meeting_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '????',
  `task_name` varchar(50) DEFAULT NULL COMMENT '????????',
  `task_description` varchar(500) DEFAULT NULL COMMENT '????????',
  `meeting_id` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_task_detail` table : 
#

CREATE TABLE `meeting_task_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detail_name` varchar(50) DEFAULT NULL,
  `detail_description` varchar(100) DEFAULT NULL,
  `charge` varchar(200) DEFAULT NULL,
  `execute_start_time` datetime DEFAULT NULL,
  `execute_end_time` datetime DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `meeting_Id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL COMMENT '??????ID,0?????????????,???????',
  `task_full_path` varchar(50) DEFAULT NULL COMMENT '?????????·??,?????0-1-2-3-4',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

#
# Structure for the `meeting_type` table : 
#

CREATE TABLE `meeting_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `logo_image` varchar(50) DEFAULT NULL,
  `theme_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Structure for the `message` table : 
#

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(128) DEFAULT NULL,
  `message` varchar(512) NOT NULL,
  `sender` int(11) NOT NULL,
  `send_time` datetime NOT NULL,
  `send_flag` smallint(3) NOT NULL DEFAULT '0',
  `meeting_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

#
# Structure for the `message_recipient` table : 
#

CREATE TABLE `message_recipient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient` int(11) NOT NULL,
  `read_flag` smallint(3) NOT NULL DEFAULT '0',
  `meeting_id` int(11) DEFAULT NULL,
  `read_time` datetime NOT NULL,
  `message_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

#
# Structure for the `news` table : 
#

CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` mediumtext,
  `source` varchar(100) DEFAULT NULL,
  `image_news` int(11) DEFAULT NULL,
  `thumbnail_image` varchar(50) DEFAULT NULL,
  `hit_count` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  `admin_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='新闻信息表';

#
# Structure for the `organization` table : 
#

CREATE TABLE `organization` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  `linker` varchar(50) DEFAULT NULL,
  `linker_tel` varchar(30) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `root_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id_idx` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='机构表';

#
# Structure for the `page_custom` table : 
#

CREATE TABLE `page_custom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(20) NOT NULL,
  `property_value` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Structure for the `page_theme` table : 
#

CREATE TABLE `page_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `thumbnail_name` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for the `paper` table : 
#

CREATE TABLE `paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `state` int(11) NOT NULL,
  `create_time` date DEFAULT NULL,
  `meetId` int(11) DEFAULT NULL,
  `adminUserId` int(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `question` table : 
#

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `questionitem` table : 
#

CREATE TABLE `questionitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `questionId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `role` table : 
#

CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(40) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `creator` char(20) DEFAULT NULL,
  `modifier` char(20) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `comments` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Structure for the `scenic_hotel` table : 
#

CREATE TABLE `scenic_hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` smallint(6) DEFAULT NULL,
  `content` longtext,
  `creator` varchar(32) DEFAULT NULL,
  `modifier` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='景点名胜酒店信息表';

#
# Structure for the `sign_in` table : 
#

CREATE TABLE `sign_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `sign_time` datetime DEFAULT NULL,
  `sign_code` varchar(32) DEFAULT NULL,
  `portal_type` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `meeting_id_idx` (`meeting_id`),
  KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8359 DEFAULT CHARSET=utf8 COMMENT='签到表';

#
# Structure for the `sms_send_template` table : 
#

CREATE TABLE `sms_send_template` (
  `id` int(11) NOT NULL,
  `type` varchar(10) NOT NULL,
  `title` varchar(20) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `optuser` bigint(20) DEFAULT NULL,
  `optdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送模板';

#
# Structure for the `spokesman` table : 
#

CREATE TABLE `spokesman` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `gender` smallint(6) DEFAULT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `meeting_id` int(11) DEFAULT NULL COMMENT 'meeting_id 记录与那个会议相关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='会议发言人';

#
# Structure for the `trigger_task` table : 
#

CREATE TABLE `trigger_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) DEFAULT NULL,
  `trigger_notify` int(11) DEFAULT NULL,
  `delay_time` int(11) DEFAULT NULL,
  `send_sms` int(11) DEFAULT NULL,
  `display_welcome` int(11) DEFAULT NULL,
  `send_meeting_agenda` int(11) DEFAULT NULL,
  `send_other_Info` int(11) DEFAULT NULL,
  `other_info` varchar(200) DEFAULT NULL,
  `sms_template` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `meeting_id` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='RFID卡片标签触发任务';

#
# Structure for the `user` table : 
#

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(11) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `mailbox` varchar(50) DEFAULT NULL,
  `gender` smallint(6) DEFAULT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `idtype` varchar(10) DEFAULT NULL,
  `idnum` varchar(18) DEFAULT NULL,
  `country` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `state` smallint(6) NOT NULL DEFAULT '1',
  `comments` varchar(400) DEFAULT NULL,
  `job` varchar(64) DEFAULT NULL,
  `org_id` int(11) unsigned DEFAULT NULL,
  `imei` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile_index` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=4475 DEFAULT CHARSET=utf8;

#
# Structure for the `user_apply` table : 
#

CREATE TABLE `user_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meeting_id` int(11) NOT NULL,
  `mobile` char(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `mailbox` varchar(50) DEFAULT NULL,
  `gender` smallint(6) DEFAULT NULL,
  `job` varchar(64) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `auditor` int(11) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `audit_opinion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

#
# Structure for the `user_login_log` table : 
#

CREATE TABLE `user_login_log` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `LOGIN_TIME` varchar(20) DEFAULT NULL,
  `LOGIN_IP` varchar(20) DEFAULT NULL,
  `portal_type` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6592 DEFAULT CHARSET=utf8;

#
# Structure for the `user_role` table : 
#

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `vehicle` table : 
#

CREATE TABLE `vehicle` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `number` varchar(100) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  `meeting_id` bigint(11) DEFAULT NULL,
  `TYPE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

#
# Structure for the `vehicle_driver` table : 
#

CREATE TABLE `vehicle_driver` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `gender` int(10) DEFAULT NULL,
  `age` int(10) DEFAULT NULL,
  `comments` varchar(500) DEFAULT NULL,
  `meeting_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

#
# Structure for the `votebase` table : 
#

CREATE TABLE `votebase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` mediumtext NOT NULL,
  `create_time` date NOT NULL,
  `type` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `meetId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

#
# Structure for the `votehistory` table : 
#

CREATE TABLE `votehistory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `voteId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Structure for the `voteitem` table : 
#

CREATE TABLE `voteitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  `count` int(11) NOT NULL,
  `create_time` date NOT NULL,
  `voteId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `voteitem_fk` (`voteId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
