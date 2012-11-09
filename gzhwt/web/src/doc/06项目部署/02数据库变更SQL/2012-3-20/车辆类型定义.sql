/*
* 车辆类型定义
*/ 

insert into `data_dict_config` (`id`, `data_type`, `act_type`, `data_code`, `data_name`, `data_desc`, `cond`, `data_value`, `order_by`, `remark`) values('18','vehicle_type','common','商务车','商务车',NULL,NULL,NULL,NULL,NULL);
insert into `data_dict_config` (`id`, `data_type`, `act_type`, `data_code`, `data_name`, `data_desc`, `cond`, `data_value`, `order_by`, `remark`) values('19','vehicle_type','common','小轿车','小轿车',NULL,NULL,NULL,NULL,NULL);
insert into `data_dict_config` (`id`, `data_type`, `act_type`, `data_code`, `data_name`, `data_desc`, `cond`, `data_value`, `order_by`, `remark`) values('20','vehicle_type','common','大客车','大客车',NULL,NULL,NULL,NULL,NULL);
insert into `data_dict_config` (`id`, `data_type`, `act_type`, `data_code`, `data_name`, `data_desc`, `cond`, `data_value`, `order_by`, `remark`) values('21','vehicle_type','common','面包车','面包车',NULL,NULL,NULL,NULL,NULL);

/*
* 修改车辆表结构
*/

ALTER TABLE vehicle ADD COLUMN TYPE VARCHAR(50);