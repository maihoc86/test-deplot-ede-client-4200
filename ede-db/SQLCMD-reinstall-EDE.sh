echo '-----'
sqlcmd -S localhost -U sa -P 123 -i scriptEDE-ReInstall.sql
echo '-----'
sqlcmd -S localhost -U sa -P 123 -i scriptEDE.sql
echo '-----'
sqlcmd -S localhost -U sa -P 123 -i scriptEDE-Extend.sql
echo '-----'
