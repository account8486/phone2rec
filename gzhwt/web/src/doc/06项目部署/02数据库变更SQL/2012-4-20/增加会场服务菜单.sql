/*
* 增加会场服务菜单(web+touch)
*/
/* 把菜单base_menu表中做为模板菜单 */
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','会场服务',NULL,'portal/pri/meeting/liveService_requestService.action?meetingId=','1','WEB','会场服务','17',NULL,NULL,NULL,'0',NULL);
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('0','会场服务','upload/menu_icon/touch/meeting_service.png','touch/pri/meeting/liveService_requestService.action?meetingId=','1','TOUCH','会场服务','17',NULL,NULL,NULL,'0',NULL);

/* 把菜单插入base_menu表中做为会议云类型的菜单  */
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','会场服务 ',NULL,'portal/pri/meeting/liveService_requestService.action?meetingId=','1','WEB','会场服务','15',NULL,NULL,NULL,'0',1);
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('0','会场服务','upload/menu_icon/touch/meeting_service.png','touch/pri/meeting/liveService_requestService.action?meetingId=','1','TOUCH','会场服务','17',NULL,NULL,NULL,'0','1');