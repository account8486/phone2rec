/*
* 为RFID签到记录表增加‘签到事件’字段
*/

alter table rfid_sign_in add column sign_event varchar(100);