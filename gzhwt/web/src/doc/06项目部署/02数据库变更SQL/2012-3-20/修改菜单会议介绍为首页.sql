/**
* 修改菜单“会议介绍”为“首页”
*/

update base_menu set name='首页', description='首页' where name like '会议介绍';
update client_menu set name='首页', description='首页' where name like '会议介绍';

commit;