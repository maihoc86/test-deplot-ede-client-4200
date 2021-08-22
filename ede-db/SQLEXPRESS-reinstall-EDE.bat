@echo off
set _username="sa"
set _password="123"

sqlcmd -S localhost\SQLEXPRESS -U %_username% -P %_password% -i scriptEDE-ReInstall.sql
sqlcmd -S localhost\SQLEXPRESS -U %_username% -P %_password% -i scriptEDE.sql
sqlcmd -S localhost\SQLEXPRESS -U %_username% -P %_password% -i scriptEDE-Extend.sql

#pause