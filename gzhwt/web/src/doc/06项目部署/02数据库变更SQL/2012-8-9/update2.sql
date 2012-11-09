--修改RFID签到记录表结构

alter table rfid_sign_in drop column sign_time;

alter table rfid_sign_in add column sign_date varchar(10);
alter table rfid_sign_in add column sign_time varchar(10);