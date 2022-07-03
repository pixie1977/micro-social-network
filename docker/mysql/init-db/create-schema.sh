#!/bin/bash
mysql -u root -ppass -h <ip сервера> --port=6603  < /usr/local/bin/init-db/schema.sql
exit $?