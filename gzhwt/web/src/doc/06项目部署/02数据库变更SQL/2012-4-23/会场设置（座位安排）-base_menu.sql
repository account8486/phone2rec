/*
* 增加会场设置(座位安排)菜单(web+touch)
*/
/* 把菜单base_menu表中做为模板菜单 */
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','座位安排',NULL,'portal/pri/meeting/managerseat_getSeatJsp.action?meetingId=','1','WEB','座位安排','15',NULL,NULL,NULL,'0',NULL);
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('0','座位安排','upload/menu_icon/touch/manage_seat.png','pages/touch/pri/seat/meetingSeat.jsp?meetingId=','1','TOUCH','座位安排','15',NULL,NULL,NULL,'0',NULL);

/* 把菜单插入base_menu表中做为会议云类型的菜单  */
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','座位安排 ',NULL,'portal/pri/meeting/managerseat_getSeatJsp.action?meetingId=','1','WEB','座位安排','15',NULL,NULL,NULL,'0',1);
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('0','座位安排','upload/menu_icon/touch/manage_seat.png','pages/touch/pri/seat/meetingSeat.jsp?meetingId=','1','TOUCH','座位安排','15',NULL,NULL,NULL,'0','1');