#!/bin/bash

# Configurações
DB_USER="root"
DB_PASSWORD=123456
DB_NAME="unica"
BACKUP_DIR="/unica/backup/"

# Nome do arquivo de backup
BACKUP_FILE="$BACKUP_DIR/backup_$(date +\%Y\%m\%d_\%H\%M\%S).sql"

# Comando mysqldump
mysqldump -u$DB_USER -p$DB_PASSWORD --single-transaction --routines --triggers --databases $DB_NAME > $BACKUP_FILE

# Compactar o backup
gzip $BACKUP_FILE