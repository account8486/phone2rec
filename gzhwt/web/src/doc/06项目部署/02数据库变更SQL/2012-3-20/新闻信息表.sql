/*
* 新闻信息表
*/ 

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻信息表';