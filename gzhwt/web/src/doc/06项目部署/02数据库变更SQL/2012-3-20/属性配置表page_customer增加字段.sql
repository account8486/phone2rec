/*
* 修改属性配置表，增加meeting_id字段
*/ 

alter table page_custom add column meeting_id int;

/*
* 修改会议资料文件表，增加file_cover_path字段
*/ 

alter table meeting_files add column file_cover_path varchar(100) ;