# SQL Manager 2005 for MySQL 3.6.5.8
# ---------------------------------------
# Host     : 180.168.71.6
# Port     : 8098
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
  (79,'admin','8a1df2fa132b5c25','集团超级管理员','',0,NULL,NULL,NULL,NULL,NULL,'',NULL,'2011-11-29 16:53:53',1,'','会议管理员',0,1,NULL,'',1);

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
# Data for the `client_menu` table  (LIMIT 0,500)
#

INSERT INTO `client_menu` (`id`, `type`, `name`, `img_url`, `content_url`, `state`, `modify_time`, `terminal_type`, `description`, `meeting_id`, `menu_type`, `default_sort_code`, `content_id`, `base_menu_id`, `package_name`, `download_url`, `is_Initial`) VALUES 
  (2672,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,NULL,'WAP','首页',157,'SYSTEM',1,NULL,129,NULL,NULL,1),
  (2673,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,NULL,'WAP','投票管理',157,'SYSTEM',1,NULL,130,NULL,NULL,1),
  (2674,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,NULL,'WAP','会议议程',157,'SYSTEM',2,NULL,131,NULL,NULL,1),
  (2675,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,NULL,'WAP','会议资料',157,'SYSTEM',3,NULL,132,NULL,NULL,1),
  (2676,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,NULL,'WAP','互动交流',157,'SYSTEM',4,NULL,133,NULL,NULL,1),
  (2677,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,NULL,'WAP','会场位置',157,'SYSTEM',5,NULL,134,NULL,NULL,1),
  (2678,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,NULL,'WAP','签到',157,'SYSTEM',6,NULL,135,NULL,NULL,0),
  (2679,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,NULL,'WAP','餐饮安排',157,'SYSTEM',7,NULL,136,NULL,NULL,1),
  (2680,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WAP','通讯录',157,'SYSTEM',9,NULL,137,NULL,NULL,1),
  (2681,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,NULL,'WAP','私信',157,'SYSTEM',10,NULL,138,NULL,NULL,1),
  (2682,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,NULL,'WAP','签到码',157,'SYSTEM',11,NULL,139,NULL,NULL,1),
  (2684,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,NULL,'WEB','首页',157,'SYSTEM',0,NULL,141,NULL,NULL,1),
  (2685,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,NULL,'WEB','会议议程',157,'SYSTEM',1,NULL,142,NULL,NULL,1),
  (2686,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,NULL,'WEB','会议资料',157,'SYSTEM',2,NULL,143,NULL,NULL,1),
  (2687,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,NULL,'WEB','互动交流',157,'SYSTEM',3,NULL,144,NULL,NULL,1),
  (2688,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,NULL,'WEB','餐饮安排 ',157,'SYSTEM',4,NULL,145,NULL,NULL,1),
  (2689,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,NULL,'WEB','签到',157,'SYSTEM',6,NULL,146,NULL,NULL,0),
  (2690,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,NULL,'WEB','会场位置',157,'SYSTEM',7,NULL,147,NULL,NULL,1),
  (2691,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WEB','通讯录',157,'SYSTEM',9,NULL,148,NULL,NULL,1),
  (2692,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,NULL,'WEB','私信',157,'SYSTEM',10,NULL,149,NULL,NULL,1),
  (2693,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,NULL,'WEB','签到码',157,'SYSTEM',11,NULL,150,NULL,NULL,1),
  (2695,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,NULL,'WEB','投票管理',157,'SYSTEM',20,NULL,152,NULL,NULL,1),
  (2696,3,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',157,'SYSTEM',8,NULL,745,NULL,NULL,1),
  (2697,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,NULL,'WEB','本地土特产',157,'SYSTEM',18,NULL,757,NULL,NULL,1),
  (2698,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,NULL,'WAP','本地土特产',157,'SYSTEM',18,NULL,758,NULL,NULL,1),
  (2716,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,NULL,'WAP','首页',158,'SYSTEM',1,NULL,129,NULL,NULL,1),
  (2717,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,NULL,'WAP','投票管理',158,'SYSTEM',1,NULL,130,NULL,NULL,1),
  (2718,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,NULL,'WAP','会议议程',158,'SYSTEM',2,NULL,131,NULL,NULL,1),
  (2719,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,NULL,'WAP','会议资料',158,'SYSTEM',3,NULL,132,NULL,NULL,1),
  (2720,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,NULL,'WAP','互动交流',158,'SYSTEM',4,NULL,133,NULL,NULL,1),
  (2721,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,NULL,'WAP','会场位置',158,'SYSTEM',5,NULL,134,NULL,NULL,1),
  (2722,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,NULL,'WAP','签到',158,'SYSTEM',6,NULL,135,NULL,NULL,0),
  (2723,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,NULL,'WAP','餐饮安排',158,'SYSTEM',7,NULL,136,NULL,NULL,1),
  (2724,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WAP','通讯录',158,'SYSTEM',9,NULL,137,NULL,NULL,1),
  (2725,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,NULL,'WAP','私信',158,'SYSTEM',10,NULL,138,NULL,NULL,1),
  (2726,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,NULL,'WAP','签到码',158,'SYSTEM',11,NULL,139,NULL,NULL,1),
  (2728,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,NULL,'WEB','首页',158,'SYSTEM',0,NULL,141,NULL,NULL,1),
  (2729,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,NULL,'WEB','会议议程',158,'SYSTEM',1,NULL,142,NULL,NULL,1),
  (2730,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,NULL,'WEB','会议资料',158,'SYSTEM',2,NULL,143,NULL,NULL,1),
  (2731,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,NULL,'WEB','互动交流',158,'SYSTEM',3,NULL,144,NULL,NULL,1),
  (2732,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,NULL,'WEB','餐饮安排 ',158,'SYSTEM',4,NULL,145,NULL,NULL,1),
  (2733,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,NULL,'WEB','签到',158,'SYSTEM',6,NULL,146,NULL,NULL,0),
  (2734,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,NULL,'WEB','会场位置',158,'SYSTEM',7,NULL,147,NULL,NULL,1),
  (2735,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WEB','通讯录',158,'SYSTEM',9,NULL,148,NULL,NULL,1),
  (2736,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,NULL,'WEB','私信',158,'SYSTEM',10,NULL,149,NULL,NULL,1),
  (2737,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,NULL,'WEB','签到码',158,'SYSTEM',11,NULL,150,NULL,NULL,1),
  (2739,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,NULL,'WEB','投票管理',158,'SYSTEM',20,NULL,152,NULL,NULL,1),
  (2740,3,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',158,'SYSTEM',8,NULL,745,NULL,NULL,1),
  (2741,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,NULL,'WEB','本地土特产',158,'SYSTEM',18,NULL,757,NULL,NULL,1),
  (2742,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,NULL,'WAP','本地土特产',158,'SYSTEM',18,NULL,758,NULL,NULL,1),
  (2760,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,NULL,'WAP','首页',159,'SYSTEM',1,NULL,129,NULL,NULL,1),
  (2761,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,NULL,'WAP','投票管理',159,'SYSTEM',1,NULL,130,NULL,NULL,1),
  (2762,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,NULL,'WAP','会议议程',159,'SYSTEM',2,NULL,131,NULL,NULL,1),
  (2763,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,NULL,'WAP','会议资料',159,'SYSTEM',3,NULL,132,NULL,NULL,1),
  (2764,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,NULL,'WAP','互动交流',159,'SYSTEM',4,NULL,133,NULL,NULL,1),
  (2765,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,NULL,'WAP','会场位置',159,'SYSTEM',5,NULL,134,NULL,NULL,1),
  (2766,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,NULL,'WAP','签到',159,'SYSTEM',6,NULL,135,NULL,NULL,0),
  (2767,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,NULL,'WAP','餐饮安排',159,'SYSTEM',7,NULL,136,NULL,NULL,1),
  (2768,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WAP','通讯录',159,'SYSTEM',9,NULL,137,NULL,NULL,1),
  (2769,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,NULL,'WAP','私信',159,'SYSTEM',10,NULL,138,NULL,NULL,1),
  (2770,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,NULL,'WAP','签到码',159,'SYSTEM',11,NULL,139,NULL,NULL,1),
  (2772,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,NULL,'WEB','首页',159,'SYSTEM',0,NULL,141,NULL,NULL,1),
  (2773,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,NULL,'WEB','会议议程',159,'SYSTEM',1,NULL,142,NULL,NULL,1),
  (2774,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,NULL,'WEB','会议资料',159,'SYSTEM',2,NULL,143,NULL,NULL,1),
  (2775,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,NULL,'WEB','互动交流',159,'SYSTEM',3,NULL,144,NULL,NULL,1),
  (2776,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,NULL,'WEB','餐饮安排 ',159,'SYSTEM',4,NULL,145,NULL,NULL,1),
  (2777,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,NULL,'WEB','签到',159,'SYSTEM',6,NULL,146,NULL,NULL,0),
  (2778,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,NULL,'WEB','会场位置',159,'SYSTEM',7,NULL,147,NULL,NULL,1),
  (2779,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WEB','通讯录',159,'SYSTEM',9,NULL,148,NULL,NULL,1),
  (2780,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,NULL,'WEB','私信',159,'SYSTEM',10,NULL,149,NULL,NULL,1),
  (2781,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,NULL,'WEB','签到码',159,'SYSTEM',11,NULL,150,NULL,NULL,1),
  (2783,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,NULL,'WEB','投票管理',159,'SYSTEM',20,NULL,152,NULL,NULL,1),
  (2784,3,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',159,'SYSTEM',8,NULL,745,NULL,NULL,1),
  (2785,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,NULL,'WEB','本地土特产',159,'SYSTEM',18,NULL,757,NULL,NULL,1),
  (2786,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,NULL,'WAP','本地土特产',159,'SYSTEM',18,NULL,758,NULL,NULL,1),
  (2787,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,'2012-03-12 17:53:42','WEB','酒店信息',159,'SYSTEM',21,NULL,808,NULL,NULL,1),
  (2788,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,'2012-03-12 17:53:24','WAP','酒店信息',159,'SYSTEM',21,NULL,810,NULL,NULL,1),
  (2789,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,NULL,'WEB','新闻中心',159,'SYSTEM',5,NULL,813,NULL,NULL,1),
  (2790,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,NULL,'WAP','新闻中心',159,'SYSTEM',5,NULL,814,NULL,NULL,1),
  (2791,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WEB','抽奖信息',159,'SYSTEM',8,NULL,817,NULL,NULL,1),
  (2792,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WAP','抽奖信息',159,'SYSTEM',10,NULL,818,NULL,NULL,1),
  (2793,2,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',159,'SYSTEM',8,NULL,821,NULL,NULL,1),
  (2794,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,NULL,'WAP','会场建议',159,'SYSTEM',10,NULL,822,NULL,NULL,1),
  (2795,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,NULL,'WEB','嘉宾信息',159,'SYSTEM',8,NULL,825,NULL,NULL,1),
  (2796,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,NULL,'WAP','嘉宾信息',159,'SYSTEM',8,NULL,826,NULL,NULL,1),
  (2814,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,NULL,'WAP','首页',160,'SYSTEM',1,NULL,129,NULL,NULL,1),
  (2815,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,NULL,'WAP','投票管理',160,'SYSTEM',1,NULL,130,NULL,NULL,1),
  (2816,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,NULL,'WAP','会议议程',160,'SYSTEM',2,NULL,131,NULL,NULL,1),
  (2817,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,NULL,'WAP','会议资料',160,'SYSTEM',3,NULL,132,NULL,NULL,1),
  (2818,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,NULL,'WAP','互动交流',160,'SYSTEM',4,NULL,133,NULL,NULL,1),
  (2819,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,NULL,'WAP','会场位置',160,'SYSTEM',5,NULL,134,NULL,NULL,1),
  (2820,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,NULL,'WAP','签到',160,'SYSTEM',6,NULL,135,NULL,NULL,0),
  (2821,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,NULL,'WAP','餐饮安排',160,'SYSTEM',7,NULL,136,NULL,NULL,1),
  (2822,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WAP','通讯录',160,'SYSTEM',9,NULL,137,NULL,NULL,1),
  (2823,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,NULL,'WAP','私信',160,'SYSTEM',10,NULL,138,NULL,NULL,1),
  (2824,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,NULL,'WAP','签到码',160,'SYSTEM',11,NULL,139,NULL,NULL,1),
  (2826,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,NULL,'WEB','首页',160,'SYSTEM',0,NULL,141,NULL,NULL,1),
  (2827,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,NULL,'WEB','会议议程',160,'SYSTEM',1,NULL,142,NULL,NULL,1),
  (2828,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,NULL,'WEB','会议资料',160,'SYSTEM',2,NULL,143,NULL,NULL,1),
  (2829,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,NULL,'WEB','互动交流',160,'SYSTEM',3,NULL,144,NULL,NULL,1),
  (2830,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,NULL,'WEB','餐饮安排 ',160,'SYSTEM',4,NULL,145,NULL,NULL,1),
  (2831,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,NULL,'WEB','签到',160,'SYSTEM',6,NULL,146,NULL,NULL,0),
  (2832,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,NULL,'WEB','会场位置',160,'SYSTEM',7,NULL,147,NULL,NULL,1),
  (2833,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WEB','通讯录',160,'SYSTEM',9,NULL,148,NULL,NULL,1),
  (2834,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,NULL,'WEB','私信',160,'SYSTEM',10,NULL,149,NULL,NULL,1),
  (2835,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,NULL,'WEB','签到码',160,'SYSTEM',11,NULL,150,NULL,NULL,1),
  (2837,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,NULL,'WEB','投票管理',160,'SYSTEM',20,NULL,152,NULL,NULL,1),
  (2838,3,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',160,'SYSTEM',8,NULL,745,NULL,NULL,1),
  (2839,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,NULL,'WEB','本地土特产',160,'SYSTEM',18,NULL,757,NULL,NULL,1),
  (2840,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,NULL,'WAP','本地土特产',160,'SYSTEM',18,NULL,758,NULL,NULL,1),
  (2841,1,'閰掑簵淇℃伅','','portal/pri/hotel/list.action?meetingId=',1,NULL,'WEB','閰掑簵淇℃伅',160,'SYSTEM',21,NULL,808,NULL,NULL,1),
  (2842,1,'閰掑簵淇℃伅','','wap/pri/hotel/list.action?meetingId=',1,NULL,'WAP','閰掑簵淇℃伅',160,'SYSTEM',21,NULL,810,NULL,NULL,1),
  (2843,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,NULL,'WEB','新闻中心',160,'SYSTEM',5,NULL,813,NULL,NULL,1),
  (2844,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,NULL,'WAP','新闻中心',160,'SYSTEM',5,NULL,814,NULL,NULL,1),
  (2845,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WEB','抽奖信息',160,'SYSTEM',8,NULL,817,NULL,NULL,1),
  (2846,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WAP','抽奖信息',160,'SYSTEM',10,NULL,818,NULL,NULL,1),
  (2847,2,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',160,'SYSTEM',8,NULL,821,NULL,NULL,1),
  (2848,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,NULL,'WAP','会场建议',160,'SYSTEM',10,NULL,822,NULL,NULL,1),
  (2849,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,NULL,'WEB','嘉宾信息',160,'SYSTEM',8,NULL,825,NULL,NULL,1),
  (2850,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,NULL,'WAP','嘉宾信息',160,'SYSTEM',8,NULL,826,NULL,NULL,1),
  (2851,0,'首页',NULL,'wap/pri/meeting/getMeetingById.action?returnType=wap&meeting.id=',1,NULL,'WAP','首页',161,'SYSTEM',1,NULL,129,NULL,NULL,1),
  (2852,1,'投票',NULL,'wap/pri/vote/vote_findEnableVote.action?meetingId=',1,NULL,'WAP','投票管理',161,'SYSTEM',1,NULL,130,NULL,NULL,1),
  (2853,1,'会议议程',NULL,'wap/pri/agenda/view.action?meetingId=',1,NULL,'WAP','会议议程',161,'SYSTEM',2,NULL,131,NULL,NULL,1),
  (2854,2,'会议资料','','wap/pri/files/getMeetingFilesPager.action?meetingId=',1,NULL,'WAP','会议资料',161,'SYSTEM',3,NULL,132,NULL,NULL,1),
  (2855,3,'互动交流',NULL,'wap/pri/interaction/postView.action?meetingId=',1,NULL,'WAP','互动交流',161,'SYSTEM',4,NULL,133,NULL,NULL,1),
  (2856,5,'会场位置',NULL,'wap/pri/map/view.action?meeting.id=',1,NULL,'WAP','会场位置',161,'SYSTEM',5,NULL,134,NULL,NULL,1),
  (2857,6,'签到',NULL,'pages/wap/pri/sign/signIn.jsp?meetingId=',1,NULL,'WAP','签到',161,'SYSTEM',6,NULL,135,NULL,NULL,0),
  (2858,7,'餐饮安排',NULL,'wap/pri/meeting/showDinner.action?meetingId=',1,NULL,'WAP','餐饮安排',161,'SYSTEM',7,NULL,136,NULL,NULL,1),
  (2859,9,'通讯录',NULL,'wap/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WAP','通讯录',161,'SYSTEM',9,NULL,137,NULL,NULL,1),
  (2860,10,'站内私信',NULL,'wap/pri/message/messagebox.action?meetingId=',1,NULL,'WAP','私信',161,'SYSTEM',10,NULL,138,NULL,NULL,1),
  (2861,11,'签到码',NULL,'wap/pri/meeting/signCode.action?meetingId=',1,NULL,'WAP','签到码',161,'SYSTEM',11,NULL,139,NULL,NULL,1),
  (2863,0,'首页',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_viewmeeting&meeting.id=',1,NULL,'WEB','首页',161,'SYSTEM',0,NULL,141,NULL,NULL,1),
  (2864,1,'会议议程','','portal/pri/agenda/agendaList.action?meetingId=',1,NULL,'WEB','会议议程',161,'SYSTEM',1,NULL,142,NULL,NULL,1),
  (2865,3,'会议资料',NULL,'portal/pri/meeting/getMeetingFilesPager.action?from=portal&meetingId=',1,NULL,'WEB','会议资料',161,'SYSTEM',2,NULL,143,NULL,NULL,1),
  (2866,4,'互动交流',NULL,'portal/pri/interaction/postList.action?meetingId=',1,NULL,'WEB','互动交流',161,'SYSTEM',3,NULL,144,NULL,NULL,1),
  (2867,5,'餐饮安排 ',NULL,'portal/pri/meeting/getDinnerInfo.action?meetingId=',1,NULL,'WEB','餐饮安排 ',161,'SYSTEM',4,NULL,145,NULL,NULL,1),
  (2868,6,'签到',NULL,'pages/portal/pri/sign/signIn.jsp?meetingId=',1,NULL,'WEB','签到',161,'SYSTEM',6,NULL,146,NULL,NULL,0),
  (2869,5,'会场位置',NULL,'portal/pri/meeting/getMeetingById.action?returnType=portal_map&meeting.id=',1,NULL,'WEB','会场位置',161,'SYSTEM',7,NULL,147,NULL,NULL,1),
  (2870,9,'通讯录',NULL,'portal/pri/meeting/getMeetingUsers.action?meeting.id=',1,NULL,'WEB','通讯录',161,'SYSTEM',9,NULL,148,NULL,NULL,1),
  (2871,10,'站内私信',NULL,'portal/pri/message/list.action?meetingId=',1,NULL,'WEB','私信',161,'SYSTEM',10,NULL,149,NULL,NULL,1),
  (2872,11,'签到码',NULL,'portal/pri/meeting/signCode.action?meetingId=',1,NULL,'WEB','签到码',161,'SYSTEM',11,NULL,150,NULL,NULL,1),
  (2874,17,'投票',NULL,'portal/pri/meeting/vote_findEnableVote.action?meetingId=',1,NULL,'WEB','投票管理',161,'SYSTEM',20,NULL,152,NULL,NULL,1),
  (2875,3,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',161,'SYSTEM',8,NULL,745,NULL,NULL,1),
  (2876,1,'本地土特产 ',NULL,'portal/pri/reception/specialty_show.action?meetingId=',1,NULL,'WEB','本地土特产',161,'SYSTEM',18,NULL,757,NULL,NULL,1),
  (2877,1,'本地土特产',NULL,'wap/pri/reception/specialty_show.action?meetingId=',1,NULL,'WAP','本地土特产',161,'SYSTEM',18,NULL,758,NULL,NULL,1),
  (2878,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,NULL,'WEB','新闻中心',161,'SYSTEM',5,NULL,813,NULL,NULL,1),
  (2879,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,NULL,'WAP','新闻中心',161,'SYSTEM',5,NULL,814,NULL,NULL,1),
  (2880,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WEB','抽奖信息',161,'SYSTEM',8,NULL,817,NULL,NULL,1),
  (2881,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WAP','抽奖信息',161,'SYSTEM',10,NULL,818,NULL,NULL,1),
  (2882,2,'会场建议',NULL,'portal/pri/meeting/paper_findEnablePaper.action?meetingId=',1,NULL,'WEB','会场建议',161,'SYSTEM',8,NULL,821,NULL,NULL,1),
  (2883,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,NULL,'WAP','会场建议',161,'SYSTEM',10,NULL,822,NULL,NULL,1),
  (2884,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,NULL,'WEB','嘉宾信息',161,'SYSTEM',8,NULL,825,NULL,NULL,1),
  (2885,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,NULL,'WAP','嘉宾信息',161,'SYSTEM',8,NULL,826,NULL,NULL,1),
  (2886,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,NULL,'WEB','酒店信息',161,'SYSTEM',21,NULL,828,NULL,NULL,1),
  (2887,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,NULL,'WAP','酒店信息',161,'SYSTEM',21,NULL,830,NULL,NULL,1),
  (2888,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,NULL,'WEB','新闻中心',158,'SYSTEM',5,NULL,813,NULL,NULL,1),
  (2889,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,NULL,'WAP','新闻中心',158,'SYSTEM',5,NULL,814,NULL,NULL,1),
  (2890,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WEB','抽奖信息',158,'SYSTEM',8,NULL,817,NULL,NULL,1),
  (2891,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WAP','抽奖信息',158,'SYSTEM',10,NULL,818,NULL,NULL,1),
  (2893,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,NULL,'WAP','会场建议',158,'SYSTEM',10,NULL,822,NULL,NULL,1),
  (2894,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,NULL,'WEB','嘉宾信息',158,'SYSTEM',8,NULL,825,NULL,NULL,1),
  (2895,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,NULL,'WAP','嘉宾信息',158,'SYSTEM',8,NULL,826,NULL,NULL,1),
  (2896,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,NULL,'WEB','酒店信息',158,'SYSTEM',21,NULL,828,NULL,NULL,1),
  (2897,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,NULL,'WAP','酒店信息',158,'SYSTEM',21,NULL,830,NULL,NULL,1),
  (2898,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,NULL,'WEB','酒店信息',159,'SYSTEM',21,NULL,828,NULL,NULL,1),
  (2899,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,NULL,'WAP','酒店信息',159,'SYSTEM',21,NULL,830,NULL,NULL,1),
  (2900,1,'新闻中心 ',NULL,'portal/pri/news/show.action?meetingId=',1,NULL,'WEB','新闻中心',157,'SYSTEM',5,NULL,813,NULL,NULL,1),
  (2901,1,'新闻中心',NULL,'wap/pri/news/show.action?meetingId=',1,NULL,'WAP','新闻中心',157,'SYSTEM',5,NULL,814,NULL,NULL,1),
  (2902,2,'抽奖信息',NULL,'portal/pri/meeting/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WEB','抽奖信息',157,'SYSTEM',8,NULL,817,NULL,NULL,1),
  (2903,5,'抽奖信息',NULL,'wap/pri/lucky/lucky_findLuckyHistory.action?meetingId=',1,NULL,'WAP','抽奖信息',157,'SYSTEM',10,NULL,818,NULL,NULL,1),
  (2905,5,'会场建议',NULL,'wap/pri/paper/paper_findEnablePaper.action?meetingId=',1,NULL,'WAP','会场建议',157,'SYSTEM',10,NULL,822,NULL,NULL,1),
  (2906,2,'嘉宾信息',NULL,'portal/pri/meeting/guest_findAllGuest.action?meetingId=',1,NULL,'WEB','嘉宾信息',157,'SYSTEM',8,NULL,825,NULL,NULL,1),
  (2907,5,'嘉宾信息',NULL,'wap/pri/guest/guest_findAllGuest.action?meetingId=',1,NULL,'WAP','嘉宾信息',157,'SYSTEM',8,NULL,826,NULL,NULL,1),
  (2908,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,NULL,'WEB','酒店信息',157,'SYSTEM',21,NULL,828,NULL,NULL,1),
  (2909,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,NULL,'WAP','酒店信息',157,'SYSTEM',21,NULL,830,NULL,NULL,1),
  (2910,1,'酒店信息','','portal/pri/hotel/list.action?meetingId=',1,NULL,'WEB','酒店信息',160,'SYSTEM',21,NULL,828,NULL,NULL,1),
  (2911,1,'酒店信息','','wap/pri/hotel/list.action?meetingId=',1,NULL,'WAP','酒店信息',160,'SYSTEM',21,NULL,830,NULL,NULL,1);

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
  (60,'贵州移动通信有限公司','',NULL,'2012-02-28 12:38:30',1,'','','',1,0,NULL);

COMMIT;

#
# Data for the `page_theme` table  (LIMIT 0,500)
#

INSERT INTO `page_theme` (`id`, `name`, `title`, `description`, `thumbnail_name`, `state`) VALUES 
  (1,'','蓝色天空','系统默认蓝色主题皮肤','images/thumbnail.png',1),
  (3,'green','春意盎然','绿色春意的主题皮肤（注：这是测试用主题）','green/images/thumbnail.png',1);

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



#
# Data for the `meeting_module_title` table  (LIMIT 0,500)
#

INSERT INTO `meeting_module_title` (`id`, `meeting_id`, `base_module_title_id`, `base_menu_id`, `name`, `title_name`, `key_name`, `terminal_type`) VALUES 
  (289,157,17,118,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (290,157,18,118,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (291,157,80,120,'会议资料','下载管理','file.download.title','CLIENT'),
  (292,157,19,121,'互动交流','发言','interaction.post.title','CLIENT'),
  (293,157,20,121,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (294,157,94,122,'会场位置','会场位置','location.map.title','CLIENT'),
  (295,157,21,124,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (296,157,22,124,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (297,157,23,125,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (298,157,24,126,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (299,157,81,126,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (300,158,17,118,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (301,158,18,118,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (302,158,80,120,'会议资料','下载管理','file.download.title','CLIENT'),
  (303,158,19,121,'互动交流','发言','interaction.post.title','CLIENT'),
  (304,158,20,121,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (305,158,94,122,'会场位置','会场位置','location.map.title','CLIENT'),
  (306,158,21,124,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (307,158,22,124,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (308,158,23,125,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (309,158,24,126,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (310,158,81,126,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (311,159,17,118,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (312,159,18,118,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (313,159,80,120,'会议资料','下载管理','file.download.title','CLIENT'),
  (314,159,19,121,'互动交流','发言','interaction.post.title','CLIENT'),
  (315,159,20,121,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (316,159,94,122,'会场位置','会场位置','location.map.title','CLIENT'),
  (317,159,21,124,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (318,159,22,124,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (319,159,23,125,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (320,159,24,126,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (321,159,81,126,'私信','查看私信','privatemsg.view.title','CLIENT'),
  (322,160,17,118,'会议议程','分组详情','agenda.team.detail.title','CLIENT'),
  (323,160,18,118,'会议议程','其他分组','agenda.team.others.title','CLIENT'),
  (324,160,80,120,'会议资料','下载管理','file.download.title','CLIENT'),
  (325,160,19,121,'互动交流','发言','interaction.post.title','CLIENT'),
  (326,160,20,121,'互动交流','发表评论','interaction.reply.title','CLIENT'),
  (327,160,94,122,'会场位置','会场位置','location.map.title','CLIENT'),
  (328,160,21,124,'餐饮安排','就餐详情','dinner.team.detail.title','CLIENT'),
  (329,160,22,124,'餐饮安排','其他分组','dinner.team.others.title','CLIENT'),
  (330,160,23,125,'通讯录','通讯录详情 ','memberlist.detail.title','CLIENT'),
  (331,160,24,126,'私信','发表私信','privatemsg.post.title','CLIENT'),
  (332,160,81,126,'私信','查看私信','privatemsg.view.title','CLIENT');

COMMIT;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
