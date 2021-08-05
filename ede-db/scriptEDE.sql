IF (db_id('EDE') IS NOT NULL)
BEGIN
	USE [master]
	DROP DATABASE [EDE]
END
GO
IF (db_id('EDE') IS NULL)
BEGIN
	CREATE DATABASE [EDE]
END
GO
USE [EDE];

--------------
CREATE TABLE "cart" (
	"id" CHAR(36) NOT NULL,
	"user_id" NVARCHAR(50) NOT NULL,
	"product_id" CHAR(36) NOT NULL,
	"quantity" INT NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "evaluate" (
	"id" CHAR(36) NOT NULL,
	"rate" INT NOT NULL,
	"content" NVARCHAR(300) NOT NULL,
	"date" DATE NOT NULL,
	"id_user" CHAR(36) NOT NULL,
	"id_product" CHAR(36) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "evaluate_image" (
	"id" CHAR(36) NOT NULL,
	"evaluate_id" CHAR(36) NOT NULL,
	"image_url" NVARCHAR(250) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "help_answer" (
	"id" CHAR(36) NOT NULL,
	"id_help_question" CHAR(36) NULL DEFAULT NULL,
	"answer" TEXT NULL DEFAULT NULL,
	"answer_question" DATETIME NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "help_category" (
	"id" CHAR(36) NOT NULL,
	"name" NVARCHAR(128) NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "help_question" (
	"id" CHAR(36) NOT NULL,
	"id_help_category" CHAR(36) NULL DEFAULT NULL,
	"question" TEXT NULL DEFAULT NULL,
	"id_user" CHAR(36) NULL DEFAULT NULL,
	"email" VARCHAR(128) NULL DEFAULT NULL,
	"question_date" DATETIME NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "keyword" (
	"id" INT NOT NULL,
	"key_value" NVARCHAR(250) NOT NULL,
	"date_search" DATE NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "notify" (
	"id" CHAR(36) NOT NULL,
	"user_id" CHAR(36) NOT NULL,
	"title" NVARCHAR(50) NOT NULL,
	"content" NVARCHAR(1000) NOT NULL,
	"image" NVARCHAR(200) NOT NULL,
	"create_date" DATETIME NOT NULL,
	"is_see" BIT NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "order" (
	"id" CHAR(36) NOT NULL,
	"id_order" CHAR(36) NOT NULL,
	"phone" VARCHAR(20) NOT NULL,
	"status" NVARCHAR(100) NOT NULL,
	"create_date" DATE NOT NULL,
	"discount_code" CHAR(36) NOT NULL,
	"total_amount" FLOAT NOT NULL,
	"note" NVARCHAR(1024) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "order_detail" (
	"id" CHAR(36) NOT NULL,
	"id_product_option" CHAR(36) NOT NULL,
	"id_user" CHAR(36) NOT NULL,
	"price" FLOAT NOT NULL,
	"quantity" INT NOT NULL,
	PRIMARY KEY ("id")
);
GO


CREATE TABLE "payment" (
	"id" INT NOT NULL,
	"name" NVARCHAR(150) NULL DEFAULT NULL,
	"date_create" DATE NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product" (
	"id" CHAR(36) NOT NULL,
	"id_shop" CHAR(36) NULL DEFAULT NULL,
	"id_brand" CHAR(36) NULL DEFAULT NULL,
	"id_category" CHAR(36) NULL DEFAULT NULL,
	"origin" NVARCHAR(64) NULL DEFAULT NULL,
	"description" NVARCHAR(4000) NULL DEFAULT NULL,
	"guaratee" FLOAT NULL DEFAULT NULL,
	"enable" BIT NULL DEFAULT NULL,
	"deleted" BIT NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_brand" (
	"id" CHAR(36) NOT NULL,
	"name" NVARCHAR(64) NOT NULL,
	"avatar" VARCHAR(128) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_category" (
	"id" CHAR(36) NOT NULL,
	"id_parent" CHAR(36) NULL DEFAULT NULL,
	"name" NVARCHAR(128) NOT NULL,
	"image_url" VARCHAR(128) NULL DEFAULT NULL,
	"is_enable" BIT NOT NULL,
	"is_delete" BIT NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_detail" (
	"id" CHAR(36) NOT NULL,
	"id_product" CHAR(36) NOT NULL,
	"title" NVARCHAR(128) NOT NULL,
	"content" NVARCHAR(512) NOT NULL,
	"numbering_order" INT NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_meta" (
	"id" INT NOT NULL,
	"date_view" DATE NOT NULL,
	"cookie" NVARCHAR(150) NOT NULL,
	"user_id" CHAR(36) NULL DEFAULT NULL,
	"id_product" CHAR(36) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_option" (
	"id" CHAR(36) NOT NULL,
	"id_product" CHAR(36) NOT NULL,
	"dispaly_name" NVARCHAR(64) NOT NULL,
	"price" FLOAT NOT NULL,
	"size" NVARCHAR(64) NOT NULL,
	"quantity" INT NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_option_image" (
	"id" CHAR(36) NOT NULL,
	"id_product_option" CHAR(36) NOT NULL,
	"alternative" NVARCHAR(512) NOT NULL,
	"image" VARCHAR(128) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "product_tag" (
	"id" CHAR(36) NOT NULL,
	"id_product" CHAR(36) NOT NULL,
	"tag" CHAR(36) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "shiper_partner" (
	"id" CHAR(36) NOT NULL,
	"name" NVARCHAR(200) NOT NULL,
	"api" NVARCHAR(max) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "shiper_shop" (
	"id" CHAR(36) NOT NULL,
	"shiper_id" CHAR(36) NOT NULL,
	"id_shop" CHAR(36) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "shop" (
	"id" CHAR(36) NOT NULL,
	"user_id" CHAR(36) NOT NULL,
	"image" NVARCHAR(200) NOT NULL,
	"create_date" DATE NOT NULL,
	"address" NVARCHAR(200) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "system_config" (
	"system_name" CHAR(20) NOT NULL,
	"system_key" NVARCHAR(100) NULL DEFAULT NULL,
	"system_value" NVARCHAR(250) NULL DEFAULT NULL,
	"system_date_create" DATE NULL DEFAULT NULL,
	PRIMARY KEY ("system_name")
);
GO

CREATE TABLE "users" (
	"id" CHAR(36) NOT NULL,
	"username" VARCHAR(50) NOT NULL UNIQUE,
	"password" NVARCHAR(100) NOT NULL,
	"first_name" NVARCHAR(50) NOT NULL,
	"last_name" NVARCHAR(50) NOT NULL,
	"email" NVARCHAR(100) NULL DEFAULT NULL,
	"gender" CHAR(1) NOT NULL, --M-an W-oman D-ifferent
	"address" NVARCHAR(200) NOT NULL,
	"phone" VARCHAR(20) NULL DEFAULT NULL,
	"is_delete" BIT NOT NULL,
	"role" CHAR(2) NOT NULL, --AD UR SL GE
	"otp" NVARCHAR(64) NULL,
	"photo" NVARCHAR(64) NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "user_address" (
	"id" CHAR(36) NOT NULL,
	"user_id" CHAR(36) NOT NULL,
	"adress" NVARCHAR(200) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "user_follow" (
	"id" CHAR(36) NOT NULL,
	"id_shop" CHAR(36) NOT NULL,
	"id_user" CHAR(36) NOT NULL,
	"evalute_status" BIT NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "user_voucher" (
	"id" CHAR(36) NOT NULL,
	"user_id" CHAR(36) NOT NULL,
	"voucher_id" CHAR(36) NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "voucher" (
	"id" CHAR(36) NOT NULL,
	"id_user" CHAR(36) NOT NULL,
	"is_admin_create" BIT NOT NULL,
	"content" TEXT NOT NULL,
	"image" VARCHAR(128) NOT NULL,
	"discount" FLOAT NOT NULL,
	"start_date" DATETIME NOT NULL,
	"end_date" DATETIME NOT NULL,
	PRIMARY KEY ("id")
);
GO

CREATE TABLE "voucher_type" (
	"id" CHAR(36) NOT NULL,
	"id_cate ry" CHAR(36) NOT NULL,
	"id_voucher" CHAR(36) NOT NULL,
	PRIMARY KEY ("id")
);
GO
