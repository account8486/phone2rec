DROP TABLE IF EXISTS asset;
CREATE TABLE asset (
  id bigint(11) NOT NULL AUTO_INCREMENT,
  asset_no varchar(20) NOT NULL,
  asset_name varchar(50) NOT NULL,
  property varchar(256),
  asset_value decimal(9, 2),
  storage_date datetime,
  remark varchar(128),
  meeting_id bigint(11),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;