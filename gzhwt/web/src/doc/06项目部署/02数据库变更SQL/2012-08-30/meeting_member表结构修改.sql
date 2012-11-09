alter table `meeting_guizhou`.`meeting_member` 
   add column `in_Private_Message` boolean NULL COMMENT '是否在私信选择人员中' ;


alter table `meeting_guizhou`.`meeting_member` 
   add column `delegation` varchar(50) NULL COMMENT '所属代表团' ;