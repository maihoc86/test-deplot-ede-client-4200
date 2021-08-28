@echo off
set _username="sa"
set _password="123"
echo "---"
sqlcmd -S localhost\SQLEXPRESS -U %_username% -P %_password% -i scriptEDE-ReInstall.sql
echo "---"
sqlcmd -S localhost\SQLEXPRESS -U %_username% -P %_password% -i scriptEDE.sql
echo "---"
sqlcmd -S localhost\SQLEXPRESS -U %_username% -P %_password% -i scriptEDE-Extend.sql
echo "---"
pause