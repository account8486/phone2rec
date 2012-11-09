CREATE TABLE `meeting_seat_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '模板名称',
  `seat_no` int(11) DEFAULT NULL COMMENT '座位号',
  `x` varchar(255) NOT NULL,
  `y` varchar(255) NOT NULL,
  `type` int(11) NOT NULL COMMENT '1-表示为座位，2-表示为盆栽，3-表示为投影',
  `seat_type` int(11) DEFAULT NULL COMMENT '当type是1时有效，此处1-表示听众座位，2-表示主席台座位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=utf8;
