#!/bin/sh
backup_date=`date '+%F-%H-%M-%S'`
cd /opt/xampp/bin/;
./mysqldump -umeeting -p meeting_guizhou > /db_bak/guizhou_meeting_$backup_date.sql
cd /db_bak ; 
gzip guizhou_meeting_$backup_date.sql ;
