USE [EDE]
GO
/****** Object:  Table [dbo].[authorities]    Script Date: 8/19/2021 10:27:30 PM ******/
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
/****** Object:  Table [dbo].[cart]    Script Date: 8/19/2021 10:27:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart](
	[id] [char](36) NOT NULL,
	[user_id] [nvarchar](50) NOT NULL,
	[product_id] [char](36) NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[evaluate]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[evaluate_image]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[help_answer]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[help_category]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[help_question]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[keyword]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[notify]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[order]    Script Date: 8/19/2021 10:27:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[id] [char](36) NOT NULL,
	[id_order] [char](36) NOT NULL,
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
/****** Object:  Table [dbo].[order_detail]    Script Date: 8/19/2021 10:27:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
	[id_user] [char](36) NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_brand]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_child_category]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_detail]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_meta]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_option]    Script Date: 8/19/2021 10:27:31 PM ******/
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
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_option_image]    Script Date: 8/19/2021 10:27:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_option_image](
	[id] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
	[alternative] [nvarchar](512) NOT NULL,
	[image] [varchar](128) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_parent_category]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_parent_child_category]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[product_tag]    Script Date: 8/19/2021 10:27:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_tag](
	[id] [char](36) NOT NULL,
	[id_product] [char](36) NOT NULL,
	[tag] [char](36) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[shiper_partner]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[shiper_shop]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[shop]    Script Date: 8/19/2021 10:27:31 PM ******/
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
	[address] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK__shop__3213E83F9F68A0A8] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[system_config]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[user_address]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[user_follow]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[user_voucher]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[users]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[voucher]    Script Date: 8/19/2021 10:27:31 PM ******/
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
/****** Object:  Table [dbo].[voucher_type]    Script Date: 8/19/2021 10:27:31 PM ******/
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
SET IDENTITY_INSERT [dbo].[authorities] ON 

INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (1, N'AD', N'111111111111111111111111111111111111')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (2, N'US', N'111111111111111111111111111111111111')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (5, N'US', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (6, N'AD', N'ae68ec72-02f3-11ec-9a03-0242ac130003')
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (7, N'US', N'ae68ec72-02f3-11ec-9a03-0242ac130003')

SET IDENTITY_INSERT [dbo].[authorities] OFF
GO
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [origin], [description], [enable], [deleted]) VALUES (N'4153f4f6-00a5-11ec-9a03-0242ac130003', N'tên gì', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-9a03-0242ac130003', N'f7adbb9c-fd08-11eb-9a03-0242ac130003', N'test', N'Không có', 1, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [origin], [description], [enable], [deleted]) VALUES (N'6a839ad1-d39d-45f6-9431-4386b045dc59', N'mún tên gì', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-9a03-0242ac130003', N'b6654030-d20a-443c-a09b-b9b949cde175', N'Mỹ', N'', 1, 0)
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [origin], [description], [enable], [deleted]) VALUES (N'c01f7a8c-97b7-4b13-a34b-dc0c4aae210c', N'có tên đó', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-9a03-0242ac130003', N'b6654030-d20a-443c-a09b-b9b949cde175', N'America', N'asdasdasdsad', 1, 0)
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo]) VALUES (N'ae68ec72-02f3-11ec-9a03-0242ac130003', N'vinh', N'pass', N'Lê Minh', N'Vinh', N'mvinhle22@gmail.com', N'M', N'Trường Tiểu Học Vĩnh Thới 3', N'0856538112', 0, 1, NULL, NULL)
GO
INSERT [dbo].[product_brand] ([id], [name], [avatar]) VALUES (N'e6518066-fbfc-11eb-9a03-0242ac130003', N'Gucci', N'avt.png')
GO
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'a58155e1-8df4-45bb-88e1-a8d6391c1f31', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone XS Max', N'test.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'b6654030-d20a-443c-a09b-b9b949cde175', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone 7', N'test.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'd5ccf22d-fbfd-458c-96cf-833fbaf9d40e', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Smart Phone', N'applewatch1.jpg', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'f7adbb9c-fd08-11eb-9a03-0242ac130003', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone X', N'test.png', 1, 0)
GO
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'6f740447-8b4a-4c79-8ee1-3affed8022a7', N'Laptop', N'test.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Điện thoại - Máy tỉnh bảng', N'test.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'b0cb6614-0c37-4464-b022-3109941ed5d6', N'Điện thoại', N'test.png', 1, 0)
GO
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Smartphone chính hãng', N'test.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'a02929f6-f056-4b0e-aeed-a0829a060943', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Dịch vụ, chính sách', N'test.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'dc68601c-5204-4f30-85d9-e243af28794b', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Nổi bật', N'test.png', 1, 0)
INSERT [dbo].[product_parent_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'e369c6dd-7568-4b3d-bd10-eda568d638f7', N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Xu hướng', N'test.png', 1, 0)
GO
INSERT [dbo].[roles] ([id], [name]) VALUES (N'AD', N'ADMIN')
INSERT [dbo].[roles] ([id], [name]) VALUES (N'US', N'USER')
GO
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [address]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'Shop TIKI', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'test.png', CAST(N'2021-01-01' AS Date), N'359/33 Nguyễn Văn Cừ')
GO
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'thaihuy12344', N'maihoc63Asd', N'Hoàng Thái Học Hoàng', N'Học', N'maihoc12344@gmail.com', N'M', N'359/33 Nguyễn Văn Cừ, Mỹ Thạnh, Hàm Thuận Bắc, Bình Thuận  ', N'0798089123', 0, 1, NULL, NULL)
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo]) VALUES (N'111111111111111111111111111111111111', N'admin', N'admin', N'nguyen', N'viet', N'viet@gmail.com', N'0', N'vb', N'0122456789', 0, 1, NULL, NULL)
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_shop]    Script Date: 8/19/2021 10:27:31 PM ******/
ALTER TABLE [dbo].[shop] ADD  CONSTRAINT [IX_shop] UNIQUE NONCLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__F3DBC572B1B1D8DF]    Script Date: 8/19/2021 10:27:31 PM ******/
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
GO
ALTER TABLE [dbo].[authorities] CHECK CONSTRAINT [fk_user]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK_product_product_child_category] FOREIGN KEY([id_category])
REFERENCES [dbo].[product_child_category] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK_product_product_child_category]
GO
ALTER TABLE [dbo].[product_child_category]  WITH CHECK ADD  CONSTRAINT [FK_product_child_category_product_parent_child_category] FOREIGN KEY([id_parent])
REFERENCES [dbo].[product_parent_child_category] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_child_category] CHECK CONSTRAINT [FK_product_child_category_product_parent_child_category]
GO
ALTER TABLE [dbo].[product_parent_child_category]  WITH CHECK ADD  CONSTRAINT [FK_product_parent_child_category_product_parent_category] FOREIGN KEY([id_parent])
REFERENCES [dbo].[product_parent_category] ([id])
GO
ALTER TABLE [dbo].[product_parent_child_category] CHECK CONSTRAINT [FK_product_parent_child_category_product_parent_category]
GO
ALTER TABLE [dbo].[shop]  WITH CHECK ADD  CONSTRAINT [FK_shop_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[shop] CHECK CONSTRAINT [FK_shop_users]
GO
