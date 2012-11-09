drop table if exists meeting_admin;
CREATE TABLE `meeting_admin` (
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会务人员';

update admin_role set role_name = "会议管理员" where id =3;
update admin_role set role_name = "会务人员" where id =4;