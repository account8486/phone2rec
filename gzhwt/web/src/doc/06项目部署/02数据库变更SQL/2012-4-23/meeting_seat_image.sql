CREATE TABLE `meeting_seat_image` (
  `offset_y` varchar(128) DEFAULT '0.0' COMMENT '截图时偏移的Y，即上半边空出来的位置',
  `offset_x` varchar(128) DEFAULT '0.0' COMMENT '截图时偏移的X，即左半边空出来的位置',
  `image` mediumblob COMMENT ' 会场安排的截图',
  `meeting_id` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
