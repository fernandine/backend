#!/bin/bash

mkdir -p ~/unica/backup

docker exec mysql-container /usr/bin/mysqldump -u root --password=123456 unica > ~/unica/backup/backup_$(date +"%Y%m%d%H%M%S").sql
gzip ~/unica/backup/backup_$(date +"%Y%m%d%H%M%S").sql
