ALTER TABLE `meeting_member`  ADD COLUMN `upload_authority` VARCHAR(10) DEFAULT 'N'  COMMENT '上传权限,Y表示有上传权限,不为Y表示无上传权限';
