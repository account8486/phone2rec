/**
* Meeting表增加主办方和承办方字段
*/

alter table meeting add host varchar(100) comment '主办方';
alter table meeting add organizer varchar(100) comment '承办方';

/**
* Meeting表增加logo图片地址及皮肤主题字段
*/

alter table meeting add logo_image varchar(50) comment 'logo图片';
alter table meeting add theme_id int comment '主题皮肤id';

--统一修改theme_id值为1，即默认类型
update meeting set theme_id=1;
commit;