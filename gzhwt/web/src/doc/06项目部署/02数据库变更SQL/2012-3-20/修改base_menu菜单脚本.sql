/**
*修改base_menu菜单脚本
*/

/*
* 增加新闻中心菜单(web+wap)
*/
/* 把菜单base_menu表中做为模板菜单 */
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=','1','WEB','新闻中心','5',NULL,NULL,NULL,'1',NULL);
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','新闻中心',NULL,'wap/pri/news/show.action?meetingId=','1','WAP','新闻中心','5',NULL,NULL,NULL,'1',NULL);

/* 把菜单插入base_menu表中做为会议云类型的菜单  */
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=','1','WEB','新闻中心','5',NULL,NULL,NULL,'1',1);
insert into `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) values('1','新闻中心',NULL,'wap/pri/news/show.action?meetingId=','1','WAP','新闻中心','5',NULL,NULL,NULL,'1',1);


/*
* 增加抽奖信息(web+wap)
*/
INSERT INTO `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES 
  (2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,'WEB','抽奖信息',8,NULL,NULL,NULL,1,NULL),
  (5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,'WAP','抽奖信息',10,NULL,NULL,NULL,1,NULL);

/* 把菜单插入base_menu表中做为会议云类型的菜单  */
INSERT INTO `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES 
  (2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,'WEB','抽奖信息',8,NULL,NULL,NULL,1,1),
  (5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,'WAP','抽奖信息',10,NULL,NULL,NULL,1,1);



/*
* 增加会场建议(web+wap)
*/

INSERT INTO `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES 
  (2,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,'WEB','会场建议',8,NULL,NULL,NULL,1,NULL),
  (5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,'WAP','会场建议',10,NULL,NULL,NULL,1,NULL);


/* 把菜单插入base_menu表中做为会议云类型的菜单  */
INSERT INTO `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES 
  (2,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,'WEB','会场建议',8,NULL,NULL,NULL,1,1),
  (5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,'WAP','会场建议',10,NULL,NULL,NULL,1,1);



/*
* 增加嘉宾信息(web+wap)
*/
INSERT INTO `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES
 	(2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,'WEB','嘉宾信息',8,NULL,NULL,NULL,1,NULL),
 	(5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,'WAP','嘉宾信息',8,NULL,NULL,NULL,1,NULL);


/* 把菜单插入base_menu表中做为会议云类型的菜单  */
INSERT INTO `base_menu` (`type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES
 	(2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,'WEB','嘉宾信息',8,NULL,NULL,NULL,1,1),
 	(5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,'WAP','嘉宾信息',8,NULL,NULL,NULL,1,1);

/**
* 删除所有的客户端菜单
*/

delete from base_menu where terminal_type='CLIENT';
delete from client_menu where terminal_type='CLIENT';

delete from base_menu where name='礼品订购';
delete from client_menu where name='礼品订购';

update base_menu set name='投票' where name='投票管理';
update client_menu set name='投票' where name='投票管理';
commit;