alter table `meeting_guizhou`.`meeting_sms` 
   add column `mobile` varchar(20) NULL COMMENT '接收短信手机号' after `comments`, 
   add column `reciever_name` varchar(50) NULL COMMENT '接收人名称' after `mobile`