CREATE TABLE `meeting_seat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `meeting_id` int(11) NOT NULL COMMENT '会议ID',
  `user_id` int(11) DEFAULT NULL COMMENT '与会人员ID，当type为1时有效',
  `seat_no` int(11) DEFAULT NULL COMMENT '座位号，当type为1时有效',
  `x` varchar(255) NOT NULL COMMENT '该组件的x坐标，为在flex舞台中确定位置。可能是会场中的座位、装饰品，比如花、植物等',
  `y` varchar(255) NOT NULL COMMENT '该座位的y坐标，为在flex舞台中确定位置。可能是会场中的座位、装饰品，比如花、植物等',
  `type` int(11) NOT NULL COMMENT '1-表示为座位，2-表示为盆栽，3-表示为投影',
  `seat_type` int(11) DEFAULT NULL COMMENT '当type是1时有效，此处1-表示听众座位，2-表示主席台座位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1486 DEFAULT CHARSET=utf8;
