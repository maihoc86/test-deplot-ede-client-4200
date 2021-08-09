-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               Microsoft SQL Server 2019 (RTM-CU11) (KB5003249) - 15.0.4138.2
-- Server OS:                    Linux (Ubuntu 20.04.2 LTS) <X64>
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for EDE
DROP DATABASE IF EXISTS "EDE";
CREATE DATABASE IF NOT EXISTS "EDE";
USE "EDE";

-- Dumping structure for table EDE.cart
DROP TABLE IF EXISTS "cart";
CREATE TABLE IF NOT EXISTS "cart" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"user_id" NVARCHAR(50) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"product_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"quantity" INT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.cart: 0 rows
/*!40000 ALTER TABLE "cart" DISABLE KEYS */;
/*!40000 ALTER TABLE "cart" ENABLE KEYS */;

-- Dumping structure for table EDE.evaluate
DROP TABLE IF EXISTS "evaluate";
CREATE TABLE IF NOT EXISTS "evaluate" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"rate" INT NOT NULL,
	"content" NVARCHAR(300) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"date" DATE NOT NULL,
	"id_user" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.evaluate: 0 rows
/*!40000 ALTER TABLE "evaluate" DISABLE KEYS */;
/*!40000 ALTER TABLE "evaluate" ENABLE KEYS */;

-- Dumping structure for table EDE.evaluate_image
DROP TABLE IF EXISTS "evaluate_image";
CREATE TABLE IF NOT EXISTS "evaluate_image" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"evaluate_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"image_url" NVARCHAR(250) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.evaluate_image: 0 rows
/*!40000 ALTER TABLE "evaluate_image" DISABLE KEYS */;
/*!40000 ALTER TABLE "evaluate_image" ENABLE KEYS */;

-- Dumping structure for table EDE.help_answer
DROP TABLE IF EXISTS "help_answer";
CREATE TABLE IF NOT EXISTS "help_answer" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_help_question" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"answer" TEXT NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"answer_question" DATETIME NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.help_answer: 0 rows
/*!40000 ALTER TABLE "help_answer" DISABLE KEYS */;
/*!40000 ALTER TABLE "help_answer" ENABLE KEYS */;

-- Dumping structure for table EDE.help_category
DROP TABLE IF EXISTS "help_category";
CREATE TABLE IF NOT EXISTS "help_category" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"name" NVARCHAR(128) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.help_category: 0 rows
/*!40000 ALTER TABLE "help_category" DISABLE KEYS */;
/*!40000 ALTER TABLE "help_category" ENABLE KEYS */;

-- Dumping structure for table EDE.help_question
DROP TABLE IF EXISTS "help_question";
CREATE TABLE IF NOT EXISTS "help_question" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_help_category" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"question" TEXT NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_user" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"email" VARCHAR(128) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"question_date" DATETIME NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.help_question: 0 rows
/*!40000 ALTER TABLE "help_question" DISABLE KEYS */;
/*!40000 ALTER TABLE "help_question" ENABLE KEYS */;

-- Dumping structure for table EDE.keyword
DROP TABLE IF EXISTS "keyword";
CREATE TABLE IF NOT EXISTS "keyword" (
	"id" INT NOT NULL,
	"key_value" NVARCHAR(250) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"date_search" DATE NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.keyword: 0 rows
/*!40000 ALTER TABLE "keyword" DISABLE KEYS */;
/*!40000 ALTER TABLE "keyword" ENABLE KEYS */;

-- Dumping structure for table EDE.notify
DROP TABLE IF EXISTS "notify";
CREATE TABLE IF NOT EXISTS "notify" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"user_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"title" NVARCHAR(50) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"content" NVARCHAR(1000) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"image" NVARCHAR(200) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"create_date" DATETIME NOT NULL,
	"is_see" BIT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.notify: 0 rows
/*!40000 ALTER TABLE "notify" DISABLE KEYS */;
/*!40000 ALTER TABLE "notify" ENABLE KEYS */;

-- Dumping structure for table EDE.order
DROP TABLE IF EXISTS "order";
CREATE TABLE IF NOT EXISTS "order" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_order" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"phone" VARCHAR(20) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"status" NVARCHAR(100) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"create_date" DATE NOT NULL,
	"discount_code" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"total_amount" FLOAT NOT NULL,
	"note" NVARCHAR(1024) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.order: 0 rows
/*!40000 ALTER TABLE "order" DISABLE KEYS */;
/*!40000 ALTER TABLE "order" ENABLE KEYS */;

-- Dumping structure for table EDE.order_detail
DROP TABLE IF EXISTS "order_detail";
CREATE TABLE IF NOT EXISTS "order_detail" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product_option" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_user" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"price" FLOAT NOT NULL,
	"quantity" INT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.order_detail: 0 rows
/*!40000 ALTER TABLE "order_detail" DISABLE KEYS */;
/*!40000 ALTER TABLE "order_detail" ENABLE KEYS */;

-- Dumping structure for table EDE.payment
DROP TABLE IF EXISTS "payment";
CREATE TABLE IF NOT EXISTS "payment" (
	"id" INT NOT NULL,
	"name" NVARCHAR(150) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"date_create" DATE NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.payment: 0 rows
/*!40000 ALTER TABLE "payment" DISABLE KEYS */;
/*!40000 ALTER TABLE "payment" ENABLE KEYS */;

-- Dumping structure for table EDE.product
DROP TABLE IF EXISTS "product";
CREATE TABLE IF NOT EXISTS "product" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_shop" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_brand" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_category" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"origin" NVARCHAR(64) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"description" NVARCHAR(4000) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"guaratee" FLOAT NULL DEFAULT NULL,
	"enable" BIT NULL DEFAULT NULL,
	"deleted" BIT NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product: 0 rows
/*!40000 ALTER TABLE "product" DISABLE KEYS */;
/*!40000 ALTER TABLE "product" ENABLE KEYS */;

-- Dumping structure for table EDE.product_brand
DROP TABLE IF EXISTS "product_brand";
CREATE TABLE IF NOT EXISTS "product_brand" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"name" NVARCHAR(64) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"avatar" VARCHAR(128) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_brand: 0 rows
/*!40000 ALTER TABLE "product_brand" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_brand" ENABLE KEYS */;

-- Dumping structure for table EDE.product_category
DROP TABLE IF EXISTS "product_category";
CREATE TABLE IF NOT EXISTS "product_category" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_parent" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"name" NVARCHAR(128) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"image_url" VARCHAR(128) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"is_enable" BIT NOT NULL,
	"is_delete" BIT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_category: 0 rows
/*!40000 ALTER TABLE "product_category" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_category" ENABLE KEYS */;

-- Dumping structure for table EDE.product_detail
DROP TABLE IF EXISTS "product_detail";
CREATE TABLE IF NOT EXISTS "product_detail" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"title" NVARCHAR(128) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"content" NVARCHAR(512) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"numbering_order" INT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_detail: 0 rows
/*!40000 ALTER TABLE "product_detail" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_detail" ENABLE KEYS */;

-- Dumping structure for table EDE.product_meta
DROP TABLE IF EXISTS "product_meta";
CREATE TABLE IF NOT EXISTS "product_meta" (
	"id" INT NOT NULL,
	"date_view" DATE NOT NULL,
	"cookie" NVARCHAR(150) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"user_id" CHAR(36) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_meta: 0 rows
/*!40000 ALTER TABLE "product_meta" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_meta" ENABLE KEYS */;

-- Dumping structure for table EDE.product_option
DROP TABLE IF EXISTS "product_option";
CREATE TABLE IF NOT EXISTS "product_option" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"dispaly_name" NVARCHAR(64) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"price" FLOAT NOT NULL,
	"size" NVARCHAR(64) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"quantity" INT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_option: 0 rows
/*!40000 ALTER TABLE "product_option" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_option" ENABLE KEYS */;

-- Dumping structure for table EDE.product_option_image
DROP TABLE IF EXISTS "product_option_image";
CREATE TABLE IF NOT EXISTS "product_option_image" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product_option" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"alternative" NVARCHAR(512) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"image" VARCHAR(128) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_option_image: 0 rows
/*!40000 ALTER TABLE "product_option_image" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_option_image" ENABLE KEYS */;

-- Dumping structure for table EDE.product_tag
DROP TABLE IF EXISTS "product_tag";
CREATE TABLE IF NOT EXISTS "product_tag" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_product" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"tag" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.product_tag: 0 rows
/*!40000 ALTER TABLE "product_tag" DISABLE KEYS */;
/*!40000 ALTER TABLE "product_tag" ENABLE KEYS */;

-- Dumping structure for table EDE.shiper_partner
DROP TABLE IF EXISTS "shiper_partner";
CREATE TABLE IF NOT EXISTS "shiper_partner" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"name" NVARCHAR(200) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"api" NVARCHAR(max) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.shiper_partner: 0 rows
/*!40000 ALTER TABLE "shiper_partner" DISABLE KEYS */;
/*!40000 ALTER TABLE "shiper_partner" ENABLE KEYS */;

-- Dumping structure for table EDE.shiper_shop
DROP TABLE IF EXISTS "shiper_shop";
CREATE TABLE IF NOT EXISTS "shiper_shop" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"shiper_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_shop" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.shiper_shop: 0 rows
/*!40000 ALTER TABLE "shiper_shop" DISABLE KEYS */;
/*!40000 ALTER TABLE "shiper_shop" ENABLE KEYS */;

-- Dumping structure for table EDE.shop
DROP TABLE IF EXISTS "shop";
CREATE TABLE IF NOT EXISTS "shop" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"user_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"image" NVARCHAR(200) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"create_date" DATE NOT NULL,
	"address" NVARCHAR(200) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.shop: 0 rows
/*!40000 ALTER TABLE "shop" DISABLE KEYS */;
/*!40000 ALTER TABLE "shop" ENABLE KEYS */;

-- Dumping structure for table EDE.system_config
DROP TABLE IF EXISTS "system_config";
CREATE TABLE IF NOT EXISTS "system_config" (
	"system_name" CHAR(20) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"system_key" NVARCHAR(100) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"system_value" NVARCHAR(250) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"system_date_create" DATE NULL DEFAULT NULL,
	PRIMARY KEY ("system_name")
);

-- Dumping data for table EDE.system_config: 0 rows
/*!40000 ALTER TABLE "system_config" DISABLE KEYS */;
/*!40000 ALTER TABLE "system_config" ENABLE KEYS */;

-- Dumping structure for table EDE.user
DROP TABLE IF EXISTS "user";
CREATE TABLE IF NOT EXISTS "user" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"username" VARCHAR(50) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"password" NVARCHAR(100) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"first_name" NVARCHAR(50) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"last_name" NVARCHAR(50) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"email" NVARCHAR(100) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"gender" CHAR(1) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"address" NVARCHAR(200) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"phone" VARCHAR(20) NULL DEFAULT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"is_delete" BIT NOT NULL,
	"role" BIT NOT NULL,
	"otp" NVARCHAR(64) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.user: 0 rows
/*!40000 ALTER TABLE "user" DISABLE KEYS */;
/*!40000 ALTER TABLE "user" ENABLE KEYS */;

-- Dumping structure for table EDE.user_address
DROP TABLE IF EXISTS "user_address";
CREATE TABLE IF NOT EXISTS "user_address" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"user_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"adress" NVARCHAR(200) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.user_address: 0 rows
/*!40000 ALTER TABLE "user_address" DISABLE KEYS */;
/*!40000 ALTER TABLE "user_address" ENABLE KEYS */;

-- Dumping structure for table EDE.user_follow
DROP TABLE IF EXISTS "user_follow";
CREATE TABLE IF NOT EXISTS "user_follow" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_shop" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_user" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"evalute_status" BIT NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.user_follow: 0 rows
/*!40000 ALTER TABLE "user_follow" DISABLE KEYS */;
/*!40000 ALTER TABLE "user_follow" ENABLE KEYS */;

-- Dumping structure for table EDE.user_voucher
DROP TABLE IF EXISTS "user_voucher";
CREATE TABLE IF NOT EXISTS "user_voucher" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"user_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"voucher_id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.user_voucher: 0 rows
/*!40000 ALTER TABLE "user_voucher" DISABLE KEYS */;
/*!40000 ALTER TABLE "user_voucher" ENABLE KEYS */;

-- Dumping structure for table EDE.voucher
DROP TABLE IF EXISTS "voucher";
CREATE TABLE IF NOT EXISTS "voucher" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_user" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"is_admin_create" BIT NOT NULL,
	"content" TEXT NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"image" VARCHAR(128) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"discount" FLOAT NOT NULL,
	"start_date" DATETIME NOT NULL,
	"end_date" DATETIME NOT NULL,
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.voucher: 0 rows
/*!40000 ALTER TABLE "voucher" DISABLE KEYS */;
/*!40000 ALTER TABLE "voucher" ENABLE KEYS */;

-- Dumping structure for table EDE.voucher_type
DROP TABLE IF EXISTS "voucher_type";
CREATE TABLE IF NOT EXISTS "voucher_type" (
	"id" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_cate ry" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	"id_voucher" CHAR(36) NOT NULL COLLATE 'SQL_Latin1_General_CP1_CI_AS',
	PRIMARY KEY ("id")
);

-- Dumping data for table EDE.voucher_type: 0 rows
/*!40000 ALTER TABLE "voucher_type" DISABLE KEYS */;
/*!40000 ALTER TABLE "voucher_type" ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
EDE