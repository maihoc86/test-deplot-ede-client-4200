IF (db_id('EDE') IS NOT NULL) BEGIN
    USE [master]
    DROP DATABASE [EDE]
END
GO
IF (db_id('EDE') IS NULL) BEGIN
    USE [master]
    CREATE DATABASE [EDE]
END
GO