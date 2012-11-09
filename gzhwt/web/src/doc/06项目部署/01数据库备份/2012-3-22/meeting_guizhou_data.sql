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

USE `meeting_guizhou`;


#
# Data for the `admin_role` table  (LIMIT 0,500)
#

INSERT INTO `admin_role` (`id`, `role_name`, `create_time`, `modify_time`, `creator`, `modifier`, `state`, `comments`) VALUES 
  (1,'系统管理员',NULL,NULL,NULL,NULL,1,NULL),
  (3,'集团管理员',NULL,NULL,NULL,NULL,1,NULL),
  (4,'会议管理员',NULL,NULL,NULL,NULL,1,NULL);

COMMIT;

#
# Data for the `admin_user` table  (LIMIT 0,500)
#

INSERT INTO `admin_user` (`id`, `mobile`, `password`, `name`, `mailbox`, `gender`, `birthday`, `idtype`, `idnum`, `country`, `city`, `address`, `create_time`, `modify_time`, `state`, `comments`, `job`, `org_id`, `role_id`, `imei`, `tel`, `can_org`) VALUES 
  (1,'18900000000','8a1df2fa132b5c25','系统管理员','',NULL,NULL,NULL,NULL,NULL,NULL,'',NULL,'2011-11-22 21:56:10',1,'','会议管理员',0,1,NULL,NULL,1),
  (79,'admin','8a1df2fa132b5c25','集团超级管理员','',0,NULL,NULL,NULL,NULL,NULL,'',NULL,'2011-11-29 16:53:53',1,'','会议管理员',0,1,NULL,'',1),
  (117,'anshun','8a1df2fa132b5c25','安顺','',0,NULL,NULL,NULL,NULL,NULL,'',NULL,'2012-03-21 09:59:20',0,'','会议管理员',62,3,NULL,'',1),
  (118,'duwei','8a1df2fa132b5c25','杜帷','',0,NULL,NULL,NULL,NULL,NULL,'',NULL,'2012-03-21 10:25:51',1,'','会议管理员',64,3,NULL,'',1);

COMMIT;

#
# Data for the `base_menu` table  (LIMIT 0,500)
#

INSERT INTO `base_menu` (`id`, `type`, `name`, `img_url`, `content_url`, `state`, `terminal_type`, `description`, `default_sort_code`, `modify_time`, `download_url`, `package_name`, `is_Initial`, `meeting_type`) VALUES 
  (13,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,'WEB','会议议程',1,NULL,NULL,NULL,1,NULL),
  (15,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,'WEB','会议资料',2,NULL,NULL,NULL,1,NULL),
  (16,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,'WEB','互动交流',3,NULL,NULL,NULL,1,NULL),
  (17,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,'WEB','餐饮安排 ',4,NULL,NULL,NULL,1,NULL),
  (18,6,'酒店住宿',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',0,'WEB','酒店住宿',5,NULL,NULL,NULL,1,NULL),
  (22,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,'WAP','首页',1,NULL,NULL,NULL,1,NULL),
  (23,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,'WAP','会议议程',2,NULL,NULL,NULL,1,NULL),
  (25,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,'WAP','互动交流',4,NULL,NULL,NULL,1,NULL),
  (27,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,'WAP','会场位置',5,NULL,NULL,NULL,1,NULL),
  (29,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,'WAP','餐饮安排',7,NULL,NULL,NULL,1,NULL),
  (31,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,'WEB','首页',0,NULL,NULL,NULL,1,NULL),
  (32,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,'WEB','会场位置',7,NULL,NULL,NULL,1,NULL),
  (37,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,'WAP','通讯录',9,NULL,NULL,NULL,1,NULL),
  (38,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,'WEB','通讯录',9,NULL,NULL,NULL,1,NULL),
  (39,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,'WEB','私信',10,NULL,NULL,NULL,1,NULL),
  (40,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,'WAP','私信',10,NULL,NULL,NULL,1,NULL),
  (70,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,'WAP','签到',6,NULL,NULL,NULL,0,NULL),
  (71,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,'WAP','签到码',11,NULL,NULL,NULL,1,NULL),
  (72,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,'WEB','签到',6,NULL,NULL,NULL,0,NULL),
  (73,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,'WEB','签到码',11,NULL,NULL,NULL,1,NULL),
  (100,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,'WAP','会议资料',3,NULL,NULL,NULL,1,NULL),
  (109,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,'WEB','投票管理',20,NULL,NULL,NULL,1,NULL),
  (110,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,'WAP','投票管理',1,NULL,NULL,NULL,1,NULL),
  (129,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,'WAP','首页',1,NULL,NULL,NULL,1,1),
  (130,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,'WAP','投票管理',1,NULL,NULL,NULL,1,1),
  (131,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,'WAP','会议议程',2,NULL,NULL,NULL,1,1),
  (132,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,'WAP','会议资料',3,NULL,NULL,NULL,1,1),
  (133,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,'WAP','互动交流',4,NULL,NULL,NULL,1,1),
  (134,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,'WAP','会场位置',5,NULL,NULL,NULL,1,1),
  (135,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,'WAP','签到',6,NULL,NULL,NULL,0,1),
  (136,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,'WAP','餐饮安排',7,NULL,NULL,NULL,1,1),
  (137,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,'WAP','通讯录',9,NULL,NULL,NULL,1,1),
  (138,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,'WAP','私信',10,NULL,NULL,NULL,1,1),
  (139,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,'WAP','签到码',11,NULL,NULL,NULL,1,1),
  (141,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,'WEB','首页',0,NULL,NULL,NULL,1,1),
  (142,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,'WEB','会议议程',1,NULL,NULL,NULL,1,1),
  (143,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,'WEB','会议资料',2,NULL,NULL,NULL,1,1),
  (144,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,'WEB','互动交流',3,NULL,NULL,NULL,1,1),
  (145,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,'WEB','餐饮安排 ',4,NULL,NULL,NULL,1,1),
  (146,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,'WEB','签到',6,NULL,NULL,NULL,0,1),
  (147,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,'WEB','会场位置',7,NULL,NULL,NULL,1,1),
  (148,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,'WEB','通讯录',9,NULL,NULL,NULL,1,1),
  (149,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,'WEB','私信',10,NULL,NULL,NULL,1,1),
  (150,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,'WEB','签到码',11,NULL,NULL,NULL,1,1),
  (152,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,'WEB','投票管理',20,NULL,NULL,NULL,1,1),
  (441,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,'WAP','投票',1,NULL,NULL,NULL,1,NULL),
  (745,3,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,'WEB','会场建议',8,NULL,NULL,NULL,1,1),
  (746,11,'用车安排 ',NULL,'portal/pri/journey/getJourneyList.action?meetingId=',1,'WEB','用车安排 ',4,NULL,NULL,NULL,0,NULL),
  (748,17,'用车安排',NULL,'wap/pri/journey/getJourneyList.action?meetingId=',1,'WAP','用车安排',17,NULL,NULL,NULL,0,NULL),
  (755,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,'WEB','本地土特产',18,NULL,NULL,NULL,1,NULL),
  (756,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,'WAP','本地土特产',18,NULL,NULL,NULL,1,NULL),
  (757,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,'WEB','本地土特产',18,NULL,NULL,NULL,1,1),
  (758,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,'WAP','本地土特产',18,NULL,NULL,NULL,1,1),
  (811,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,'WEB','新闻中心',5,NULL,NULL,NULL,1,NULL),
  (812,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,'WAP','新闻中心',5,NULL,NULL,NULL,1,NULL),
  (813,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,'WEB','新闻中心',5,NULL,NULL,NULL,1,1),
  (814,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,'WAP','新闻中心',5,NULL,NULL,NULL,1,1),
  (815,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,'WEB','抽奖信息',8,NULL,NULL,NULL,1,NULL),
  (816,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,'WAP','抽奖信息',10,NULL,NULL,NULL,1,NULL),
  (817,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,'WEB','抽奖信息',8,NULL,NULL,NULL,1,1),
  (818,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,'WAP','抽奖信息',10,NULL,NULL,NULL,1,1),
  (819,2,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,'WEB','会场建议',8,NULL,NULL,NULL,1,NULL),
  (820,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,'WAP','会场建议',10,NULL,NULL,NULL,1,NULL),
  (822,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,'WAP','会场建议',10,NULL,NULL,NULL,1,1),
  (823,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,'WEB','嘉宾信息',8,NULL,NULL,NULL,1,NULL),
  (824,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,'WAP','嘉宾信息',8,NULL,NULL,NULL,1,NULL),
  (825,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,'WEB','嘉宾信息',8,NULL,NULL,NULL,1,1),
  (826,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,'WAP','嘉宾信息',8,NULL,NULL,NULL,1,1),
  (827,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,'WEB','酒店信息',21,NULL,NULL,NULL,1,NULL),
  (828,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,'WEB','酒店信息',21,NULL,NULL,NULL,1,1),
  (829,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,'WAP','酒店信息',21,NULL,NULL,NULL,1,NULL),
  (830,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,'WAP','酒店信息',21,NULL,NULL,NULL,1,1);

COMMIT;

#
# Data for the `base_module_title` table  (LIMIT 0,500)
#

INSERT INTO `base_module_title` (`id`, `base_menu_id`, `meeting_type_id`, `name`, `title_name`, `key_name`, `terminal_type`) VALUES 
  (1,2,-1,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (2,2,-1,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (3,4,-1,'互动交流','发言','interaction.post.title','CLIENT'),
  (4,4,-1,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (5,10,-1,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (6,10,-1,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (7,5,-1,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (8,11,-1,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (9,708,12,'会议议程','议程讨论','agenda.team.detail.title','CLIENT'),
  (10,708,12,'会议议程','查看其他讨论分组','agenda.team.others.title','CLIENT'),
  (11,712,12,'互动交流','发言','interaction.post.title','CLIENT'),
  (12,712,12,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (13,715,12,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (14,715,12,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (15,716,12,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (16,717,12,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (17,118,1,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (18,118,1,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (19,121,1,'互动交流','发言','interaction.post.title','CLIENT'),
  (20,121,1,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (21,124,1,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (22,124,1,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (23,125,1,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (24,126,1,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (25,203,2,'互动交流','发言','interaction.post.title','CLIENT'),
  (26,203,2,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (27,206,2,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (28,206,2,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (29,207,2,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (30,208,2,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (31,241,3,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (32,241,3,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (33,244,3,'互动交流','发言','interaction.post.title','CLIENT'),
  (34,244,3,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (35,247,3,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (36,247,3,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (37,248,3,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (38,249,3,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (39,323,4,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (40,323,4,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (41,326,4,'互动交流','发言','interaction.post.title','CLIENT'),
  (42,326,4,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (43,329,4,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (44,329,4,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (45,330,4,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (46,331,4,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (47,405,5,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (48,405,5,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (49,408,5,'互动交流','发言','interaction.post.title','CLIENT'),
  (50,408,5,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (51,411,5,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (52,411,5,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (53,412,5,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (54,413,5,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (55,621,9,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (56,621,9,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (57,625,9,'互动交流','发言','interaction.post.title','CLIENT'),
  (58,625,9,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (59,628,9,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (60,628,9,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (61,629,9,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (62,630,9,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (63,11,-1,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (64,3,-1,'会议资料','下载管理','file.download.title','CLIENT'),
  (80,120,1,'会议资料','下载管理','file.download.title','CLIENT'),
  (81,126,1,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (82,208,2,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (83,243,3,'会议资料','下载管理','file.download.title','CLIENT'),
  (84,249,3,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (85,325,4,'会议资料','下载管理','file.download.title','CLIENT'),
  (86,331,4,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (87,407,5,'会议资料','下载管理','file.download.title','CLIENT'),
  (88,413,5,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (89,624,9,'会议资料','下载管理','file.download.title','CLIENT'),
  (90,630,9,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (91,711,12,'会议资料','下载管理','file.download.title','CLIENT'),
  (92,717,12,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (93,6,-1,'会场位置','会场位置','location.map.title','CLIENT'),
  (94,122,1,'会场位置','会场位置','location.map.title','CLIENT'),
  (95,245,3,'会场位置','会场位置','location.map.title','CLIENT'),
  (96,327,4,'会场位置','会场位置','location.map.title','CLIENT'),
  (97,409,5,'会场位置','会场位置','location.map.title','CLIENT'),
  (98,626,9,'会场位置','会场位置','location.map.title','CLIENT'),
  (99,713,12,'会场位置','会场位置','location.map.title','CLIENT'),
  (101,765,13,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (102,765,13,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (103,768,13,'会议资料','下载管理','file.download.title','CLIENT'),
  (104,769,13,'互动交流','发言','interaction.post.title','CLIENT'),
  (105,769,13,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (106,770,13,'会场位置','会场位置','location.map.title','CLIENT'),
  (107,772,13,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (108,772,13,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (109,773,13,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (110,774,13,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (111,774,13,'私信','查看私信','privatemsg.view.title','CLIENT');

COMMIT;

#
# Data for the `data_dict` table  (LIMIT 0,500)
#

INSERT INTO `data_dict` (`data_type`, `data_type_name`, `data_type_desc`) VALUES 
  ('act_type','活动类型','活动类型'),
  ('client_login_bg_pic','客户端登录背景图片','客户端登录背景图片'),
  ('custom_bg_pic','定制背景图片','定制的客户端登录背景图片，供用户选择'),
  ('find_back_password','找回密码','找回密码配置'),
  ('menu_type','菜单类型','菜单类型'),
  ('terminal_type','菜单访问类型','菜单访问类型');

COMMIT;

#
# Data for the `data_dict_config` table  (LIMIT 0,500)
#

INSERT INTO `data_dict_config` (`id`, `data_type`, `act_type`, `data_code`, `data_name`, `data_desc`, `cond`, `data_value`, `order_by`, `remark`) VALUES 
  (1,'act_type','common','0','会议',NULL,NULL,'1',NULL,NULL),
  (2,'act_type','common','1','旅游',NULL,NULL,'2',NULL,NULL),
  (3,'dinner_type','common','1','自助餐',NULL,NULL,'false',NULL,NULL),
  (4,'dinner_type','common','2','桌餐',NULL,NULL,'false',NULL,NULL),
  (5,'dinner_type','common','3','会餐',NULL,NULL,'true',NULL,NULL),
  (6,'dinner_type','common','4','家宴',NULL,NULL,'true',NULL,NULL),
  (7,'dinner_type','common','5','其他',NULL,NULL,'false',NULL,NULL),
  (8,'find_back_password','common','1','找回密码配置',NULL,NULL,'D',NULL,'D表示为动态找回,S表示找回原来的'),
  (10,'terminal_type','common','WEB','WEB门户',NULL,NULL,NULL,NULL,NULL),
  (11,'terminal_type','common','WAP','WAP门户',NULL,NULL,NULL,NULL,NULL),
  (12,'menu_type','common','0','网页链接',NULL,NULL,NULL,NULL,NULL),
  (13,'menu_type','common','1','基本功能',NULL,NULL,NULL,NULL,NULL),
  (14,'menu_type','common','2','第三方软件',NULL,NULL,NULL,NULL,NULL),
  (15,'client_login_bg_pic','common','0','客户端登录背景图片',NULL,NULL,'images/client/default.png',NULL,NULL),
  (17,'custom_bg_pic','common','1','默认图片',NULL,NULL,'images/client/default.png',NULL,NULL),
  (18,'vehicle_type','common','商务车','商务车',NULL,NULL,NULL,NULL,NULL),
  (19,'vehicle_type','common','小轿车','小轿车',NULL,NULL,NULL,NULL,NULL),
  (20,'vehicle_type','common','大客车','大客车',NULL,NULL,NULL,NULL,NULL),
  (21,'vehicle_type','common','面包车','面包车',NULL,NULL,NULL,NULL,NULL);

COMMIT;

#
# Data for the `meeting_type` table  (LIMIT 0,500)
#

INSERT INTO `meeting_type` (`id`, `name`, `description`, `create_time`, `state`, `logo_image`, `theme_id`) VALUES 
  (1,'会议','一般会务活动类型','2012-01-13',1,'1330406086506.gif',1);

COMMIT;

#
# Data for the `organization` table  (LIMIT 0,500)
#

INSERT INTO `organization` (`id`, `name`, `address`, `create_time`, `modify_time`, `state`, `comments`, `linker`, `linker_tel`, `level`, `parent_id`, `root_id`) VALUES 
  (0,'站点管理','',NULL,'2011-11-28 13:18:17',1,'','111','',0,NULL,NULL);
UPDATE `organization` SET `id`=0 WHERE `id`=LAST_INSERT_ID();
INSERT INTO `organization` (`id`, `name`, `address`, `create_time`, `modify_time`, `state`, `comments`, `linker`, `linker_tel`, `level`, `parent_id`, `root_id`) VALUES 
  (60,'贵州移动通信有限公司','',NULL,'2012-02-28 12:38:30',1,'','','',1,0,NULL),
  (62,'安顺分公司','',NULL,'2012-03-21 09:59:53',0,'','','',2,0,NULL),
  (63,'安顺分公司','',NULL,'2012-03-21 10:00:25',1,'','','',2,60,NULL),
  (64,'省公司','',NULL,'2012-03-21 10:00:56',1,'','','',2,60,NULL);

COMMIT;

#
# Data for the `page_custom` table  (LIMIT 0,500)
#

INSERT INTO `page_custom` (`id`, `property_name`, `property_value`, `description`, `meeting_id`) VALUES 
  (1,'files_show_type','bookshelf','会议资料显示方式配置，bookshelf为书架，list为列表',164);

COMMIT;

#
# Data for the `page_theme` table  (LIMIT 0,500)
#

INSERT INTO `page_theme` (`id`, `name`, `title`, `description`, `thumbnail_name`, `state`) VALUES 
  (1,'','蓝色天空','系统默认蓝色主题皮肤','images/thumbnail.png',1),
  (3,'green','春意盎然','绿色春意的主题皮肤（注：这是测试用主题）','green/images/thumbnail.png',1),
  (4,'creative','','','creative/images/thumbnail.png',1);

COMMIT;

#
# Data for the `role` table  (LIMIT 0,500)
#

INSERT INTO `role` (`id`, `role_name`, `create_time`, `modify_time`, `creator`, `modifier`, `state`, `comments`) VALUES 
  (1,'超级管理员',NULL,NULL,NULL,NULL,1,NULL),
  (2,'系统管理员',NULL,NULL,NULL,NULL,1,NULL),
  (3,'会议管理员',NULL,NULL,NULL,NULL,1,NULL),
  (4,'会议云客服',NULL,NULL,NULL,NULL,1,NULL),
  (5,'会议成员',NULL,NULL,NULL,NULL,1,NULL),
  (6,'会议主持人',NULL,NULL,NULL,NULL,1,NULL);

COMMIT;

#
# Data for the `trigger_task` table  (LIMIT 0,500)
#

INSERT INTO `trigger_task` (`id`, `meeting_id`, `trigger_notify`, `delay_time`, `send_sms`, `display_welcome`, `send_meeting_agenda`, `send_other_Info`, `other_info`, `sms_template`) VALUES 
  (1,NULL,0,0,0,0,0,0,NULL,'欢迎{username}进入会场。');

COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
