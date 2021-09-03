USE [EDE]
GO
/****** Object:  Table [dbo].[authorities]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[authorities](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_role] [nvarchar](10) NULL,
	[id_user] [char](36) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cart]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart](
	[id] [char](36) NOT NULL,
	[user_id] [char](36) NOT NULL,
	[product_id] [char](36) NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK__cart__3213E83F17221F55] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[evaluate]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[evaluate](
	[id] [char](36) NOT NULL,
	[rate] [int] NOT NULL,
	[content] [nvarchar](300) NOT NULL,
	[date] [date] NOT NULL,
	[id_user] [char](36) NOT NULL,
	[id_product] [char](36) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[evaluate_image]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[evaluate_image](
	[id] [char](36) NOT NULL,
	[evaluate_id] [char](36) NOT NULL,
	[image_url] [nvarchar](250) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[help_answer]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[help_answer](
	[id] [char](36) NOT NULL,
	[id_help_question] [char](36) NULL,
	[answer] [text] NULL,
	[answer_question] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[help_category]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[help_category](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](128) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[help_question]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[help_question](
	[id] [char](36) NOT NULL,
	[id_help_category] [char](36) NULL,
	[question] [text] NULL,
	[id_user] [char](36) NULL,
	[email] [varchar](128) NULL,
	[question_date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[keyword]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[keyword](
	[id] [int] NOT NULL,
	[key_value] [nvarchar](250) NOT NULL,
	[date_search] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notify]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notify](
	[id] [char](36) NOT NULL,
	[user_id] [char](36) NOT NULL,
	[title] [nvarchar](50) NOT NULL,
	[content] [nvarchar](1000) NOT NULL,
	[image] [nvarchar](200) NOT NULL,
	[create_date] [datetime] NOT NULL,
	[is_see] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[id] [char](36) NOT NULL,
	[id_user] [char](36) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[status] [nvarchar](100) NOT NULL,
	[create_date] [date] NOT NULL,
	[discount_code] [char](36) NOT NULL,
	[total_amount] [float] NOT NULL,
	[note] [nvarchar](1024) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
	[id_oder] [char](36) NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment](
	[id] [int] NOT NULL,
	[name] [nvarchar](150) NULL,
	[date_create] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](150) NOT NULL,
	[id_shop] [char](36) NOT NULL,
	[id_brand] [char](36) NOT NULL,
	[id_category] [char](36) NOT NULL,
	[location] [nvarchar](64) NULL,
	[origin] [nvarchar](64) NOT NULL,
	[description] [nvarchar](4000) NULL,
	[enable] [bit] NOT NULL,
	[deleted] [bit] NOT NULL,
 CONSTRAINT [PK__product__3213E83F4BEC915D] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_brand]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_brand](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](64) NOT NULL,
	[avatar] [varchar](128) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_child_category]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_child_category](
	[id] [char](36) NOT NULL,
	[id_parent] [char](36) NULL,
	[name] [nvarchar](128) NULL,
	[image_url] [varchar](128) NULL,
	[is_enable] [bit] NULL,
	[is_delete] [bit] NULL,
 CONSTRAINT [PK_product_child_category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_detail]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_detail](
	[id] [char](36) NOT NULL,
	[id_product] [char](36) NOT NULL,
	[title] [nvarchar](128) NOT NULL,
	[content] [nvarchar](512) NOT NULL,
	[numbering_order] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_discount]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_discount](
	[id] [char](36) NOT NULL,
	[id_product] [char](36) NOT NULL,
	[discount] [float] NOT NULL,
	[startdate] [date] NOT NULL,
	[enddate] [date] NOT NULL,
	[status] [bit] NOT NULL,
 CONSTRAINT [PK_product_discount] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_meta]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_meta](
	[id] [int] NOT NULL,
	[date_view] [date] NOT NULL,
	[cookie] [nvarchar](150) NOT NULL,
	[user_id] [char](36) NULL,
	[id_product] [char](36) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_option]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_option](
	[id] [char](36) NOT NULL,
	[id_product] [char](36) NOT NULL,
	[display_name] [nvarchar](64) NOT NULL,
	[price] [float] NOT NULL,
	[size] [nvarchar](64) NOT NULL,
	[quantity] [int] NOT NULL,
	[is_delete] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_option_image]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_option_image](
	[id] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
	[image] [varchar](128) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_parent_category]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_parent_category](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](128) NOT NULL,
	[image_url] [varchar](128) NULL,
	[is_enable] [bit] NOT NULL,
	[is_delete] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_parent_child_category]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_parent_child_category](
	[id] [char](36) NOT NULL,
	[id_parent] [char](36) NULL,
	[name] [nvarchar](128) NULL,
	[image_url] [varchar](128) NULL,
	[is_enable] [bit] NULL,
	[is_delete] [bit] NULL,
 CONSTRAINT [PK_product_parent_child_category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_tag]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_tag](
	[id] [char](36) NOT NULL,
	[id_product] [char](36) NOT NULL,
	[tag] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK__product___3213E83F534E4855] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[id] [nvarchar](10) NOT NULL,
	[name] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shiper_partner]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shiper_partner](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](200) NOT NULL,
	[api] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shiper_shop]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shiper_shop](
	[id] [char](36) NOT NULL,
	[shiper_id] [char](36) NOT NULL,
	[id_shop] [char](36) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shop]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shop](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](150) NULL,
	[user_id] [char](36) NOT NULL,
	[image] [nvarchar](200) NOT NULL,
	[create_date] [date] NOT NULL,
	[description] [nvarchar](max) NULL,
	[address] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK__shop__3213E83F9F68A0A8] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[system_config]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[system_config](
	[system_name] [char](20) NOT NULL,
	[system_key] [nvarchar](100) NULL,
	[system_value] [nvarchar](250) NULL,
	[system_date_create] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[system_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_address]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_address](
	[id] [char](36) NOT NULL,
	[user_id] [char](36) NOT NULL,
	[adress] [nvarchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_follow]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_follow](
	[id] [char](36) NOT NULL,
	[id_shop] [char](36) NOT NULL,
	[id_user] [char](36) NOT NULL,
	[evalute_status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_voucher]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_voucher](
	[id] [char](36) NOT NULL,
	[user_id] [char](36) NOT NULL,
	[voucher_id] [char](36) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id] [char](36) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[first_name] [nvarchar](50) NOT NULL,
	[last_name] [nvarchar](50) NOT NULL,
	[email] [nvarchar](100) NULL,
	[gender] [char](1) NOT NULL,
	[address] [nvarchar](200) NOT NULL,
	[phone] [varchar](20) NULL,
	[is_delete] [bit] NOT NULL,
	[is_active] [bit] NOT NULL,
	[otp] [varchar](132) NULL,
	[photo] [nvarchar](64) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[voucher]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[voucher](
	[id] [char](36) NOT NULL,
	[id_user] [char](36) NOT NULL,
	[is_admin_create] [bit] NOT NULL,
	[content] [text] NOT NULL,
	[image] [varchar](128) NOT NULL,
	[discount] [float] NOT NULL,
	[start_date] [datetime] NOT NULL,
	[end_date] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[voucher_type]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[voucher_type](
	[id] [char](36) NOT NULL,
	[id_cate ry] [char](36) NOT NULL,
	[id_voucher] [char](36) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[view_product_search]    Script Date: 8/28/2021 10:27:41 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
-- view product add keyword
CREATE VIEW [dbo].[view_product_search]
AS
    SELECT  product.id as id_product,
            product.name -- keysearch
            as keysearch 
        FROM product
GO
SET IDENTITY_INSERT [dbo].[authorities] ON 

INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (1, N'AD', N'111111111111111111111111111111111111')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (2, N'US', N'111111111111111111111111111111111111')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (5, N'US', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (6, N'AD', N'ae68ec72-02f3-11ec-9a03-0242ac130003')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (7, N'US', N'ae68ec72-02f3-11ec-9a03-0242ac130003')
SET IDENTITY_INSERT [dbo].[authorities] OFF
GO
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'1b906c75-784b-4621-b996-870fdc72d103', N'Quần xịn siêu 202022', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'903c5509-bdf1-4e42-9bff-9579c14af274', N'Thanh Hóa', N'Antigua and Barbuda', N'asdasdsad', 1, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'9e1e0ad9-cbcb-45d2-9c00-c9d59b09eba2', N'Quần xịn siêu 20211sss', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'a60e02b8-f4be-496e-bff1-2b2100376432', N'Thanh Hóa', N'Antigua and Barbuda', N'asdasdsad', 1, 1)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'd2fa266d-e6e5-4914-a697-d8779df14b60', N'asdasdasdasd', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-123s-0242ac130003', N'd5ccf22d-fbfd-458c-96cf-833fbaf9d40e', N'Hải Phòng', N'Anguilla', N'asdasdasd', 0, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'd9d47ec0-0513-4aa5-9d82-00d71266af79', N'Điện thoại OPPO', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-9a03-0242ac130003', N'a58155e1-8df4-45bb-88e1-a8d6391c1f31', N'Cần Thơ', N'Viet Nam', N'asdasdsadsad', 1, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'e733b5ff-a83f-4e84-bc22-d20d6924246b', N'Siêu phẩm WOWO', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'903c5509-bdf1-4e42-9bff-9579c14af274', N'Khánh Hòa', N'Andorra', N'zxcxzc', 1, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'ee6c231e-e72b-42cc-bc48-a201cc86f3e5', N'Quần xịn siêu 1111', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'1475ac58-d7cd-444d-b68c-c3cf0894a3a8', N'Thanh Hóa', N'Antigua and Barbuda', N'asdasdsad', 1, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted]) VALUES (N'fcde2213-ab6e-4973-b364-e0fdd7291a7f', N'Quần xịn siêu 202022', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'903c5509-bdf1-4e42-9bff-9579c14af274', N'Thanh Hóa', N'Antigua and Barbuda', N'asdasdsad', 1, 0)
GO
INSERT [dbo].[product_brand] ([id], [name], [avatar]) VALUES (N'62e80784-6804-472d-969c-9198eec175e8', N'Asus', N'avt.png')
INSERT [dbo].[product_brand] ([id], [name], [avatar]) VALUES (N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'Khác', N'avt.png')
INSERT [dbo].[product_brand] ([id], [name], [avatar]) VALUES (N'e6518066-fbfc-11eb-123s-0242ac130003', N'Dio', N'avt.png')
INSERT [dbo].[product_brand] ([id], [name], [avatar]) VALUES (N'e6518066-fbfc-11eb-9a03-0242ac130003', N'Gucci', N'avt.png')
GO
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'1475ac58-d7cd-444d-b68c-c3cf0894a3a8', N'dc68601c-5204-4f30-85d9-e243af28794b', N'Ipad 2020', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'43ed3f71-25f8-445d-af99-d6b989a77f4a', N'0f034c3a-c669-4b73-987f-9f1001fbc917', N'ASUS GAMING', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'4627985b-7f14-4e80-9770-102a98e16583', N'9074d480-ea66-477c-b5bf-f94d3fd6045c', N'Iphone Pro max', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'4e75891d-abed-40c5-9af4-70aebbf908b1', N'dc68601c-5204-4f30-85d9-e243af28794b', N'Ipad', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'5eb43443-c6ed-4744-963d-6a6b5c6260aa', N'e369c6dd-7568-4b3d-bd10-eda568d638f7', N'IPAD PRO', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'770d64d6-d8d4-447c-9cbd-ee7557a785ec', N'8957e144-9518-43fb-8048-c5b2e679d1e9', N'DELL GAMING', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'903c5509-bdf1-4e42-9bff-9579c14af274', N'12560872-9b3b-419e-8e2c-6ec1c335393b', N'Quần Việt Nam', N'3-min-6a26dd24-00a6-47a6-8740-95039e4f7c2a.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'a58155e1-8df4-45bb-88e1-a8d6391c1f31', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone XS Max', N'test.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'a60e02b8-f4be-496e-bff1-2b2100376432', N'12560872-9b3b-419e-8e2c-6ec1c335393b', N'Gucci 2021', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'b597fb56-d58a-4af0-aafd-7a81ea765e3e', N'0f034c3a-c669-4b73-987f-9f1001fbc917', N'Dell latitude', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'b6654030-d20a-443c-a09b-b9b949cde175', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone 7', N'test.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'b832dc07-4019-4ebb-880d-7dbf77909e2e', N'8957e144-9518-43fb-8048-c5b2e679d1e9', N'ROG ASUS', N'', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'd5ccf22d-fbfd-458c-96cf-833fbaf9d40e', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Smart Phone 2021', N'applewatch1.jpg', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'db4fd316-de56-4101-8f97-7211ee06649e', N'0f034c3a-c669-4b73-987f-9f1001fbc917', N'Asus ROG 2021', N'37428_asus_rog_strix_g17_g713qm_k4113t_r7_5800h_197c27cd1bce4217baedb68f8353d1c5_master.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'f7adbb9c-fd08-11eb-9a03-0242ac130003', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone X', N'test.png', 1, 0)
GO
INSERT [dbo].[product_discount] ([id], [id_product], [discount], [startdate], [enddate], [status]) VALUES (N'60ef89d1-52f7-40e9-8b12-c9528f8dff2a', N'e733b5ff-a83f-4e84-bc22-d20d6924246b', 11, CAST(N'2021-08-28' AS Date), CAST(N'2021-09-02' AS Date), 1)
GO
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'19b178c8-c590-46aa-bdd3-5025a31c7b80', N'9e1e0ad9-cbcb-45d2-9c00-c9d59b09eba2', N'Màu vàng 2w2', 3000000, N'chu_XX:', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'3571694a-24cd-437b-bbf2-8baaaa71397b', N'ee6c231e-e72b-42cc-bc48-a201cc86f3e5', N'Màu vàng 212', 3000000, N'chu_XL', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'4d702b34-c2c5-48e9-9f10-b64d71eebfeb', N'1b906c75-784b-4621-b996-870fdc72d103', N'Màu vàng 2w', 3000000, N'chu_XX:', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'73a9a500-518e-4c7d-ba48-f13b52999d5a', N'9e1e0ad9-cbcb-45d2-9c00-c9d59b09eba2', N'Màu vàng 2w2', 3000000, N'chu_XX:', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'94fc338c-340f-43af-972b-4e1e77369f3e', N'9e1e0ad9-cbcb-45d2-9c00-c9d59b09eba2', N'Màu vàng chanhh', 3000000, N'chu_XX:', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'a8b737e8-168c-4ebe-b229-7f579f4c86c0', N'd2fa266d-e6e5-4914-a697-d8779df14b60', N'Điện thoại 2020xzc', 123123123, N'', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'abdf4461-edc1-4921-bce2-6ccd7ec11889', N'd9d47ec0-0513-4aa5-9d82-00d71266af79', N'Đỏ', 111111, N'', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'cec88e6e-cbe7-4371-96b2-ff32395b332e', N'e733b5ff-a83f-4e84-bc22-d20d6924246b', N'sdsdsd', 1231232, N'so_33', 1, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [quantity], [is_delete]) VALUES (N'd235ff40-eeeb-4c58-b272-61941728d252', N'e733b5ff-a83f-4e84-bc22-d20d6924246b', N'Máy ủi', 1231232, N'so_33', 1, 0)
GO
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'0ee0cd62-e894-4591-ae3b-e0fa01cb5a3c', N'd235ff40-eeeb-4c58-b272-61941728d252', N'595bda14-1da3-4f3a-9af4-52268283952d.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'41aff5ea-1fb3-44a5-9871-4f9dd584c3a7', N'a8b737e8-168c-4ebe-b229-7f579f4c86c0', N'41aff5ea-1fb3-44a5-9871-4f9dd584c3a7.png')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'58b95747-ad28-4ccb-aeb5-f8b14bb1a756', N'cec88e6e-cbe7-4371-96b2-ff32395b332e', N'58b95747-ad28-4ccb-aeb5-f8b14bb1a756.png')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'942bc5c0-83d0-42a9-ba58-910b969d2b9c', N'4d702b34-c2c5-48e9-9f10-b64d71eebfeb', N'942bc5c0-83d0-42a9-ba58-910b969d2b9c.png')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'9933e072-8113-4322-8fcc-85bab082ff5d', N'd235ff40-eeeb-4c58-b272-61941728d252', N'6b3c43cb-6ac5-427c-9e12-31a285dc93ae.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'a77644da-40cb-4096-9e4b-d1cdfcb03d95', N'd235ff40-eeeb-4c58-b272-61941728d252', N'5d177b02-af89-4291-a198-4f8972c7156b.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'b6084f82-f41a-4ba8-bf7d-5cba45231d7b', N'd235ff40-eeeb-4c58-b272-61941728d252', N'71f690d0-16cb-4e7a-a45d-268cfaf2a07c.png')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'd91cfb95-fe49-4e24-afc6-9fc85b1ed822', N'cec88e6e-cbe7-4371-96b2-ff32395b332e', N'd91cfb95-fe49-4e24-afc6-9fc85b1ed822.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'd9ed5811-197c-4044-8d7c-f2ca27c6b0ea', N'cec88e6e-cbe7-4371-96b2-ff32395b332e', N'd9ed5811-197c-4044-8d7c-f2ca27c6b0ea.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'ecf938e7-ca37-461f-b6ac-308f60b17b19', N'abdf4461-edc1-4921-bce2-6ccd7ec11889', N'ecf938e7-ca37-461f-b6ac-308f60b17b19.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'f255ecec-f38e-4c33-9f84-be7bb4c446b0', N'cec88e6e-cbe7-4371-96b2-ff32395b332e', N'f255ecec-f38e-4c33-9f84-be7bb4c446b0.jpg')
GO
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'6c0e7aa7-cd6a-4986-b92d-fae66e3d9c49', N'Quần áo', N'daychuyengang.jfif', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'6f740447-8b4a-4c79-8ee1-3affed8022a7', N'Laptop', N'test.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Điện thoại - Máy tính bảng', N'test.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'b0cb6614-0c37-4464-b022-3109941ed5d6', N'Điện thoại', N'test.png', 1, 0)
GO
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Smartphone chính hãng', N'test.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'0da75bb3-27b2-4ced-9abe-3a7cacbbfb24', N'6c0e7aa7-cd6a-4986-b92d-fae66e3d9c49', N'Quần áo xu hướng', N'', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'0f034c3a-c669-4b73-987f-9f1001fbc917', N'6f740447-8b4a-4c79-8ee1-3affed8022a7', N'Laptop chính hãng', N'37428_asus_rog_strix_g17_g713qm_k4113t_r7_5800h_197c27cd1bce4217baedb68f8353d1c5_master.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'12560872-9b3b-419e-8e2c-6ec1c335393b', N'6c0e7aa7-cd6a-4986-b92d-fae66e3d9c49', N'Quần áo chính hãng', N'3-min-6a26dd24-00a6-47a6-8740-95039e4f7c2a.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'8957e144-9518-43fb-8048-c5b2e679d1e9', N'6f740447-8b4a-4c79-8ee1-3affed8022a7', N'Laptop gaming', N'', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'9074d480-ea66-477c-b5bf-f94d3fd6045c', N'b0cb6614-0c37-4464-b022-3109941ed5d6', N'Điện thoại xu hướng', N'', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'dc68601c-5204-4f30-85d9-e243af28794b', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Máy tính bảng - điện thoại nổi bật', N'test.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'e369c6dd-7568-4b3d-bd10-eda568d638f7', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Máy tính bảng - điện thoại xu hướng', N'test.png', 1, 0)
GO
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'1fff3035-62fc-4d38-a793-a3ffd2dcac0f', N'e733b5ff-a83f-4e84-bc22-d20d6924246b', N'asds')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'b2cbeeb8-bdd2-46e4-80e3-f54e680db155', N'e733b5ff-a83f-4e84-bc22-d20d6924246b', N'sdsdsd')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'ead3d9f8-ea2f-47de-b8c7-5d149e1ccd76', N'e733b5ff-a83f-4e84-bc22-d20d6924246b', N'ss')
GO
INSERT [dbo].[roles] ([id], [name]) VALUES (N'AD', N'ADMIN')
INSERT [dbo].[roles] ([id], [name]) VALUES (N'US', N'USER')
GO
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'Shop TIKI', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'test.png', CAST(N'2021-01-01' AS Date), NULL, N'359/33 Nguyễn Văn Cừ')
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address]) VALUES (N'7080aecd-eea3-46b7-9848-03e1f4644235', N'asdasd123cxv', N'bb6260e1-5b8f-401e-bf05-d6307fcbc91b', N'bia.jpg', CAST(N'2021-08-28' AS Date), NULL, N'xcvxcvxzcv, Hàm Thạnh, Hàm Thuận Nam, Bình Thuận  ')
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address]) VALUES (N'89337ca3-cf66-4ca5-b604-76652d68b026', N'thaihuy1234433', N'9402e66a-7edf-4b1d-8b9d-ff621ce8bec5', N'bia.jpg', CAST(N'2021-08-28' AS Date), NULL, N'asdasdasd, Ngọc Hải, Đồ Sơn, Hải Phòng')
GO
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'thaihuy12344', N'maihoc63Asd', N'Hoàng Thái', N'Học', N'maihoc12344@gmail.com', N'M', N'359/33 Nguyễn Văn Cừ, Mỹ Thạnh, Hàm Thuận Bắc, Bình Thuận  ', N'0798089123', 0, 1, NULL, NULL)
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo]) VALUES (N'111111111111111111111111111111111111', N'admin', N'admin', N'nguyen', N'viet', N'viet@gmail.com', N'0', N'vb', N'0122456789', 0, 1, NULL, NULL)
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo]) VALUES (N'ae68ec72-02f3-11ec-9a03-0242ac130003', N'vinh', N'pass', N'Lê Minh', N'Vinh', N'mvinhle22@gmail.com', N'M', N'Trường Tiểu Học Vĩnh Thới 3', N'0856538112', 0, 1, NULL, NULL)
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_shop]    Script Date: 8/28/2021 10:27:41 PM ******/
ALTER TABLE [dbo].[shop] ADD  CONSTRAINT [IX_shop] UNIQUE NONCLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__F3DBC5728C6D1277]    Script Date: 8/28/2021 10:27:41 PM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[help_answer] ADD  DEFAULT (NULL) FOR [id_help_question]
GO
ALTER TABLE [dbo].[help_answer] ADD  DEFAULT (NULL) FOR [answer]
GO
ALTER TABLE [dbo].[help_answer] ADD  DEFAULT (NULL) FOR [answer_question]
GO
ALTER TABLE [dbo].[help_category] ADD  DEFAULT (NULL) FOR [name]
GO
ALTER TABLE [dbo].[help_question] ADD  DEFAULT (NULL) FOR [id_help_category]
GO
ALTER TABLE [dbo].[help_question] ADD  DEFAULT (NULL) FOR [question]
GO
ALTER TABLE [dbo].[help_question] ADD  DEFAULT (NULL) FOR [id_user]
GO
ALTER TABLE [dbo].[help_question] ADD  DEFAULT (NULL) FOR [email]
GO
ALTER TABLE [dbo].[help_question] ADD  DEFAULT (NULL) FOR [question_date]
GO
ALTER TABLE [dbo].[payment] ADD  DEFAULT (NULL) FOR [name]
GO
ALTER TABLE [dbo].[payment] ADD  DEFAULT (NULL) FOR [date_create]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__id_shop__693CA210]  DEFAULT (NULL) FOR [id_shop]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__id_bran__6A30C649]  DEFAULT (NULL) FOR [id_brand]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__id_cate__6B24EA82]  DEFAULT (NULL) FOR [id_category]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__origin__6C190EBB]  DEFAULT (NULL) FOR [origin]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__descrip__6D0D32F4]  DEFAULT (NULL) FOR [description]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__enable__6EF57B66]  DEFAULT (NULL) FOR [enable]
GO
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF__product__deleted__6FE99F9F]  DEFAULT (NULL) FOR [deleted]
GO
ALTER TABLE [dbo].[product_meta] ADD  DEFAULT (NULL) FOR [user_id]
GO
ALTER TABLE [dbo].[product_option] ADD  DEFAULT ((0)) FOR [is_delete]
GO
ALTER TABLE [dbo].[product_parent_category] ADD  DEFAULT (NULL) FOR [image_url]
GO
ALTER TABLE [dbo].[system_config] ADD  DEFAULT (NULL) FOR [system_key]
GO
ALTER TABLE [dbo].[system_config] ADD  DEFAULT (NULL) FOR [system_value]
GO
ALTER TABLE [dbo].[system_config] ADD  DEFAULT (NULL) FOR [system_date_create]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [email]
GO
ALTER TABLE [dbo].[users] ADD  DEFAULT (NULL) FOR [phone]
GO
ALTER TABLE [dbo].[authorities]  WITH CHECK ADD  CONSTRAINT [fk_role] FOREIGN KEY([id_role])
REFERENCES [dbo].[roles] ([id])
GO
ALTER TABLE [dbo].[authorities] CHECK CONSTRAINT [fk_role]
GO
ALTER TABLE [dbo].[authorities]  WITH CHECK ADD  CONSTRAINT [fk_user] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[authorities] CHECK CONSTRAINT [fk_user]
GO
ALTER TABLE [dbo].[cart]  WITH CHECK ADD  CONSTRAINT [FK_cart_user_address] FOREIGN KEY([product_id])
REFERENCES [dbo].[user_address] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[cart] CHECK CONSTRAINT [FK_cart_user_address]
GO
ALTER TABLE [dbo].[evaluate]  WITH CHECK ADD  CONSTRAINT [FK_evaluate_users] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[evaluate] CHECK CONSTRAINT [FK_evaluate_users]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK_order_users] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK_order_users]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_order] FOREIGN KEY([id_oder])
REFERENCES [dbo].[order] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_order]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_product_option]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK_product_product_brand] FOREIGN KEY([id_brand])
REFERENCES [dbo].[product_brand] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK_product_product_brand]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK_product_product_child_category1] FOREIGN KEY([id_category])
REFERENCES [dbo].[product_child_category] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK_product_product_child_category1]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK_product_shop] FOREIGN KEY([id_shop])
REFERENCES [dbo].[shop] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK_product_shop]
GO
ALTER TABLE [dbo].[product_child_category]  WITH CHECK ADD  CONSTRAINT [FK_product_child_category_product_parent_child_category] FOREIGN KEY([id_parent])
REFERENCES [dbo].[product_parent_child_category] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_child_category] CHECK CONSTRAINT [FK_product_child_category_product_parent_child_category]
GO
ALTER TABLE [dbo].[product_discount]  WITH CHECK ADD  CONSTRAINT [FK_product_discount_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_discount] CHECK CONSTRAINT [FK_product_discount_product]
GO
ALTER TABLE [dbo].[product_meta]  WITH CHECK ADD  CONSTRAINT [FK_product_meta_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
GO
ALTER TABLE [dbo].[product_meta] CHECK CONSTRAINT [FK_product_meta_product]
GO
ALTER TABLE [dbo].[product_option]  WITH CHECK ADD  CONSTRAINT [FK_product_option_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_option] CHECK CONSTRAINT [FK_product_option_product]
GO
ALTER TABLE [dbo].[product_option_image]  WITH CHECK ADD  CONSTRAINT [FK_product_option_image_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_option_image] CHECK CONSTRAINT [FK_product_option_image_product_option]
GO
ALTER TABLE [dbo].[product_parent_child_category]  WITH CHECK ADD  CONSTRAINT [FK_product_parent_child_category_product_parent_category] FOREIGN KEY([id_parent])
REFERENCES [dbo].[product_parent_category] ([id])
GO
ALTER TABLE [dbo].[product_parent_child_category] CHECK CONSTRAINT [FK_product_parent_child_category_product_parent_category]
GO
ALTER TABLE [dbo].[product_tag]  WITH CHECK ADD  CONSTRAINT [FK_product_tag_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_tag] CHECK CONSTRAINT [FK_product_tag_product]
GO
ALTER TABLE [dbo].[user_address]  WITH CHECK ADD  CONSTRAINT [FK_user_address_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[user_address] CHECK CONSTRAINT [FK_user_address_users]
GO
ALTER TABLE [dbo].[user_follow]  WITH CHECK ADD  CONSTRAINT [FK_user_follow_users] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[user_follow] CHECK CONSTRAINT [FK_user_follow_users]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'product_option', @level2type=N'COLUMN',@level2name=N'is_delete'
GO
