USE [EDE]
GO
/****** Object:  UserDefinedFunction [dbo].[fn_cover_unsign_string]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
-- cover sign string to unsign string
CREATE FUNCTION [dbo].[fn_cover_unsign_string](@inputVar NVARCHAR(MAX) )
RETURNS NVARCHAR(MAX)
AS
BEGIN    
    IF (@inputVar IS NULL OR @inputVar = '')  RETURN ''
   
    DECLARE @RT NVARCHAR(MAX)
    DECLARE @SIGN_CHARS NCHAR(256)
    DECLARE @UNSIGN_CHARS NCHAR (256)
 
    SET @SIGN_CHARS     = N'ăâđêôơưàảãạáằẳẵặắầẩẫậấèẻẽẹéềểễệếìỉĩịíòỏõọóồổỗộốờởỡợớùủũụúừửữựứỳỷỹỵýĂÂĐÊÔƠƯÀẢÃẠÁẰẲẴẶẮẦẨẪẬẤÈẺẼẸÉỀỂỄỆẾÌỈĨỊÍÒỎÕỌÓỒỔỖỘỐỜỞỠỢỚÙỦŨỤÚỪỬỮỰỨỲỶỸỴÝ' + NCHAR(272) + NCHAR(208)
    SET @UNSIGN_CHARS   = N'aadeoouaaaaaaaaaaaaaaaeeeeeeeeeeiiiiiooooooooooooooouuuuuuuuuuyyyyyAADEOOUAAAAAAAAAAAAAAAEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOUUUUUUUUUUYYYYYDD'
 
    DECLARE @COUNTER int
    DECLARE @COUNTER1 int
   
    SET @COUNTER = 1
    WHILE (@COUNTER <= LEN(@inputVar))
    BEGIN  
        SET @COUNTER1 = 1
        WHILE (@COUNTER1 <= LEN(@SIGN_CHARS) + 1)
        BEGIN
            IF UNICODE(SUBSTRING(@SIGN_CHARS, @COUNTER1,1)) = UNICODE(SUBSTRING(@inputVar,@COUNTER ,1))
            BEGIN          
                IF @COUNTER = 1
                    SET @inputVar = SUBSTRING(@UNSIGN_CHARS, @COUNTER1,1) + SUBSTRING(@inputVar, @COUNTER+1,LEN(@inputVar)-1)      
                ELSE
                    SET @inputVar = SUBSTRING(@inputVar, 1, @COUNTER-1) +SUBSTRING(@UNSIGN_CHARS, @COUNTER1,1) + SUBSTRING(@inputVar, @COUNTER+1,LEN(@inputVar)- @COUNTER)
                BREAK
            END
            SET @COUNTER1 = @COUNTER1 +1
        END
        SET @COUNTER = @COUNTER +1
    END
    -- SET @inputVar = replace(@inputVar,' ','-')
    RETURN @inputVar
END
GO
/****** Object:  UserDefinedFunction [dbo].[fn_matcher_string]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
-- Điểm khớp nhau, số càng cao càng khóp nhau
CREATE FUNCTION [dbo].[fn_matcher_string](@source NVARCHAR(1000), @desk NVARCHAR(1000))
RETURNS FLOAT
AS BEGIN
    DECLARE @result FLOAT,
            @source_length FLOAT,
            @desk_length FLOAT
    -- SELECT  @source = [dbo].[fn_cover_unsign_string](UPPER(@source)),
    --         @desk = [dbo].[fn_cover_unsign_string](UPPER(@desk))
    SELECT  @source = UPPER(@source),
            @desk = UPPER(@desk)
    SELECT @result = [dbo].[fn_string_distance](@source, @desk), @source_length = LEN(@source),  @desk_length = LEN(@desk)
    
    RETURN - (@result - ABS(@source_length - @desk_length))
END
GO
/****** Object:  UserDefinedFunction [dbo].[fn_string_distance]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
-- using levenshtein distance algorithm
CREATE FUNCTION [dbo].[fn_string_distance](@source NVARCHAR(1000), @desk NVARCHAR(1000))
RETURNS INTEGER
AS BEGIN

    SELECT  @source = @source,
            @desk = @desk

    DECLARE @result INTEGER = 0,
            @max_distance INTEGER = 700,
            @source_length INTEGER,
            @desk_length INTEGER,
            @source_char NCHAR,
            @result_change INTEGER,
            @result_round NVARCHAR(1000),
            @desk_array NVARCHAR(1000) = '',
            @result_min INTEGER,
            @i INTEGER = 1,
            @j INTEGER = 1
    
    SELECT  @source_length = LEN(@source),
            @desk_length = LEN(@desk)

    WHILE @j <= @desk_length -- using j insted i because hold i using late
        SELECT  @desk_array = @desk_array + NCHAR(@j), 
                @j = @j + 1

    WHILE @i <= @source_length BEGIN
        SELECT  @source_char = SUBSTRING(@source, @i, 1), -- get char at i
                @result_change = @i,
                @result = @i,
                @result_min = 1000,
                @result_round = '',
                @j = 1
        WHILE @j <= @desk_length BEGIN
            SET @result = @result + 1
            SET @result_change = @result_change - CASE WHEN @source_char = SUBSTRING(@desk, @j, 1) THEN 1 ELSE 0
        END
        IF @result > @result_change SET @result = @result_change -- change

        SET @result_change = UNICODE(SUBSTRING(@desk_array, @j, 1)) + 1
        IF @result > @result_change SET @result = @result_change -- remove or add

        IF @result < @result_min SET @result_min = @result
        SELECT  @result_round = @result_round + NCHAR(@result), 
                @j = @j + 1
    END
    IF @result_min > @max_distance BREAK
    SELECT @desk_array = @result_round, @i = @i + 1
  END
  RETURN CASE WHEN @result_min <= @max_distance AND @result <= @max_distance THEN @result ELSE @max_distance END
END
GO
/****** Object:  Table [dbo].[authorities]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[cart]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart](
	[id] [char](36) NOT NULL,
	[user_id] [char](36) NOT NULL,
 CONSTRAINT [PK__cart__3213E83F17221F55] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cart_item]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart_item](
	[id] [char](36) NOT NULL,
	[id_cart] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_cart_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[evaluate]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[evaluate_image]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[help_answer]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[help_category]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[help_question]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[history_view_page]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[history_view_page](
	[id] [char](36) NOT NULL,
	[ip] [varchar](50) NOT NULL,
	[cookie] [varchar](200) NULL,
	[date_view] [date] NULL,
 CONSTRAINT [PK_history_view_page] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[keyword]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[notify]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[order_detail]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
	[orderid] [char](36) NOT NULL,
	[price] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[fee_ship] [float] NOT NULL,
 CONSTRAINT [PK__order_de__3213E83FB8C40DA3] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_discount]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_discount](
	[id] [char](36) NOT NULL,
	[total] [float] NOT NULL,
	[discount] [float] NOT NULL,
	[todate] [date] NOT NULL,
	[enddate] [date] NOT NULL,
	[status] [bit] NOT NULL,
 CONSTRAINT [PK_order_discount] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [char](36) NOT NULL,
	[id_user] [char](36) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[create_date] [date] NOT NULL,
	[total_amount] [float] NOT NULL,
	[note] [nvarchar](1024) NULL,
	[id_discount] [char](36) NULL,
	[status] [nvarchar](100) NULL,
 CONSTRAINT [PK__order__3213E83F10487EDE] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product]    Script Date: 11/22/2021 2:15:36 PM ******/
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
	[createdate] [date] NULL,
 CONSTRAINT [PK__product__3213E83F4BEC915D] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_brand]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product_child_category]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product_detail]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product_discount]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_discount](
	[id] [char](36) NOT NULL,
	[id_product_option] [char](36) NOT NULL,
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
/****** Object:  Table [dbo].[product_meta]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_meta](
	[id] [char](36) NOT NULL,
	[date_view] [date] NOT NULL,
	[cookie] [nvarchar](400) NOT NULL,
	[user_id] [char](36) NULL,
	[id_product] [char](36) NOT NULL,
 CONSTRAINT [PK__product___3213E83F1B018BB4] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_option]    Script Date: 11/22/2021 2:15:36 PM ******/
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
	[weight] [float] NOT NULL,
	[quantity] [int] NOT NULL,
	[is_delete] [bit] NOT NULL,
 CONSTRAINT [PK__product___3213E83FCE64EF5B] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_option_image]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product_parent_category]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product_parent_child_category]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[product_tag]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[receive_news]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[receive_news](
	[id] [char](36) NOT NULL,
	[email] [nvarchar](100) NOT NULL,
	[create_date] [date] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[shiper_partner]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[shiper_shop]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[shop]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shop](
	[id] [char](36) NOT NULL,
	[name] [nvarchar](150) NULL,
	[user_id] [char](36) NOT NULL,
	[image] [varchar](200) NULL,
	[create_date] [date] NOT NULL,
	[description] [nvarchar](max) NULL,
	[address] [nvarchar](200) NOT NULL,
	[image_sub] [varchar](200) NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK__shop__3213E83F9F68A0A8] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[system_config]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[user_address]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_address](
	[id] [char](36) NOT NULL,
	[user_id] [char](36) NOT NULL,
	[first_name] [nvarchar](50) NOT NULL,
	[last_name] [nvarchar](50) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[address] [nvarchar](200) NOT NULL,
 CONSTRAINT [PK__user_add__3213E83F1AD077B7] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_follow]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[user_voucher]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[users]    Script Date: 11/22/2021 2:15:36 PM ******/
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
	[create_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[voucher]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  Table [dbo].[voucher_type]    Script Date: 11/22/2021 2:15:36 PM ******/
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
/****** Object:  View [dbo].[view_product_discount]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_discount]
AS
SELECT id
FROM     dbo.product_discount
GO
/****** Object:  View [dbo].[view_product_evaluate]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_evaluate]
AS
SELECT id
FROM     dbo.evaluate
GO
/****** Object:  View [dbo].[view_product_option]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_option]
AS
SELECT id
FROM     dbo.product_option
GO
/****** Object:  View [dbo].[view_product_option_image]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_option_image]
AS
SELECT id
FROM     dbo.product_option_image
GO
/****** Object:  View [dbo].[view_product_option_order_detail]    Script Date: 11/22/2021 2:15:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_option_order_detail]
AS
SELECT id
FROM     dbo.order_detail
GO
/****** Object:  View [dbo].[view_product_search]    Script Date: 11/22/2021 2:15:36 PM ******/
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
INSERT [dbo].[authorities] ([id], [id_role], [id_user]) VALUES (8, N'US', N'5e14928d-603f-46ba-9ffc-bce57bc91414')
SET IDENTITY_INSERT [dbo].[authorities] OFF
GO
INSERT [dbo].[cart] ([id], [user_id]) VALUES (N'5da3bb22-8837-4231-9dbe-b55ba1fac82f', N'111111111111111111111111111111111111')
INSERT [dbo].[cart] ([id], [user_id]) VALUES (N'b473b7b1-052d-4b8f-aa0f-f10d89c61a8d', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8')
GO
INSERT [dbo].[cart_item] ([id], [id_cart], [id_product_option], [quantity]) VALUES (N'132d76c8-2fa8-4df6-91c2-8d23d576417b', N'b473b7b1-052d-4b8f-aa0f-f10d89c61a8d', N'111d55ea-1eaa-4f24-87dd-df8d53219bbf', 1)
INSERT [dbo].[cart_item] ([id], [id_cart], [id_product_option], [quantity]) VALUES (N'43235420-a483-48d2-ad61-8d031e39c14f', N'5da3bb22-8837-4231-9dbe-b55ba1fac82f', N'111d55ea-1eaa-4f24-87dd-df8d53219bbf', 1)
GO
INSERT [dbo].[evaluate] ([id], [rate], [content], [date], [id_user], [id_product]) VALUES (N'4a1317b3-2569-4a58-8484-99c9fef4157c', 2, N'test', CAST(N'2021-10-19' AS Date), N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'85057c71-0152-4824-9235-9da71b1cadd6')
INSERT [dbo].[evaluate] ([id], [rate], [content], [date], [id_user], [id_product]) VALUES (N'754844c7-cd62-4cd7-a8cc-464d72b5a49b', 5, N'test', CAST(N'2021-10-19' AS Date), N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'233cfb4a-2588-4ac5-8585-b1b15011c497')
INSERT [dbo].[evaluate] ([id], [rate], [content], [date], [id_user], [id_product]) VALUES (N'998cf85a-319e-11ec-8d3d-0242ac130003', 2, N'test', CAST(N'2021-10-20' AS Date), N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'233cfb4a-2588-4ac5-8585-b1b15011c497')
GO
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'90e7f4fe-8aba-4dfa-8ca2-f6d77810c580', N'117.7.221.3', NULL, CAST(N'2021-11-22' AS Date))
GO
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'752cc0f6-1134-11ec-82a8-0242ac130003', N'c86eb32c-9a0d-458f-a189-67f4ee1fa18b', N'f6c71100-1082-11ec-82a8-0242ac130003', 223123, 11, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'752cc326-1134-11ec-82a8-0242ac130003', N'255e8197-a40a-4f72-9197-8db819c4b88f', N'f6c71100-1082-11ec-82a8-0242ac130003', 23123213, 55, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'752cc40c-1134-11ec-82a8-0242ac130003', N'337d4e85-92ee-4642-a056-3301b714c1fa', N'f6c71100-1082-11ec-82a8-0242ac130003', 2123123, 11, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'752cc4ca-1134-11ec-82a8-0242ac130003', N'ffbf9c3a-95be-4ae0-ae20-677b714a8608', N'f6c71100-1082-11ec-82a8-0242ac130003', 5555555, 11, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'752cc7c2-1134-11ec-82a8-0242ac130003', N'c86eb32c-9a0d-458f-a189-67f4ee1fa18b', N'f6c71100-1082-11ec-82a8-0242ac130003', 5213332, 5, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'8f57af36-108a-11ec-82a8-0242ac130003', N'81e3d1f6-43ff-44fc-a2a5-25a6e141451c', N'f6c71100-1082-11ec-82a8-0242ac130003', 111111, 111, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'bd632874-112a-11ec-82a8-0242ac130003', N'111d55ea-1eaa-4f24-87dd-df8d53219bbf', N'75d099ba-112a-11ec-82a8-0242ac130004', 23333, 11111, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'bd632c98-112a-11ec-82a8-0242ac130003', N'255e8197-a40a-4f72-9197-8db819c4b88f', N'75d099ba-112a-11ec-82a8-0242ac130003', 55555, 11, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'd7bac88e-112b-11ec-82a8-0242ac130003', N'ce3bdb1c-1663-4748-b577-12b96e2eaf97', N'a7a35c56-112b-11ec-82a8-0242ac130003', 2222, 11, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'd7bacb86-112b-11ec-82a8-0242ac130003', N'81e3d1f6-43ff-44fc-a2a5-25a6e141451c', N'a7a35e90-112b-11ec-82a8-0242ac130003', 2222, 2, 12000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'd7bacd16-112b-11ec-82a8-0242ac130003', N'0de5bfa0-1621-4bb9-9474-5613dad3c8e6', N'a7a360ac-112b-11ec-82a8-0242ac130003', 122222, 11, 12000)
GO
INSERT [dbo].[order_discount] ([id], [total], [discount], [todate], [enddate], [status]) VALUES (N'9c6acf44-4515-4181-92b5-105bfdea7410', 20000000, 30, CAST(N'2021-10-13' AS Date), CAST(N'2021-10-30' AS Date), 1)
INSERT [dbo].[order_discount] ([id], [total], [discount], [todate], [enddate], [status]) VALUES (N'c07e1427-ec26-440a-a466-90592948b12c', 100000, 22, CAST(N'2021-10-31' AS Date), CAST(N'2021-11-28' AS Date), 1)
GO
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'75d099ba-112a-11ec-82a8-0242ac130003', N'111111111111111111111111111111111111', N'0798123884', CAST(N'2021-09-09' AS Date), 500000, NULL, NULL, N'Đã giao')
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'75d099ba-112a-11ec-82a8-0242ac130004', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'0798123842', CAST(N'2021-09-09' AS Date), 2333333, NULL, NULL, N'Đã giao')
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'a7a35c56-112b-11ec-82a8-0242ac130003', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'0798123884', CAST(N'2021-09-09' AS Date), 599999, NULL, NULL, N'Đã hủy')
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'a7a35e90-112b-11ec-82a8-0242ac130003', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'0798123884', CAST(N'2021-09-09' AS Date), 2132444, NULL, NULL, N'Đã giao')
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'a7a360ac-112b-11ec-82a8-0242ac130003', N'111111111111111111111111111111111111', N'0798123774', CAST(N'2021-09-09' AS Date), 4424000, NULL, NULL, N'Đã giao')
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'f6c71100-1082-11ec-82a8-0242ac130003', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'0798089123', CAST(N'2021-01-01' AS Date), 1000000, NULL, N'9c6acf44-4515-4181-92b5-105bfdea7410', N'Đã giao')
GO
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'233cfb4a-2588-4ac5-8585-b1b15011c497', N'Iphone 11 PRO MAX 2021', N'111111111111111111111111111111111111', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'4627985b-7f14-4e80-9770-102a98e16583', N'Cần Thơ', N'Croatia', N'🍓🍓🍓 QUẦN JEAN VỀ SẴN 

👖HÀNG SIÊU NÉT !!! JEANS Cao cấp 
FULL SIZE S / M / L 

✅Eo ôi em thề. Với các ce luôn là tìm ở đâu cho được cái quần jeans chất phát ngất như của e đây ạ!?
Phải nói là chất nó mịn mượt quá ạ, bảo mặc jeans mùa này nóng nhưng không đâu ạ mặc nó mát ko hề bó sát cứng đơ đâu ạ.
 
📩 TUYỂN SỈ TỪ 5SP BẤT KÌ
       CHỐT ĐƠN BUÔN : ib + ZALO 0962251859

Các bạn phân vân khi không biết mình mặc có vừa không thì chat với chúng mình nhé!

CHÍNH SÁCH BÁN HÀNG
- Hàng có sẵn, cam kết giao hàng ngay khi nhận được đơn .
- 100% hình ảnh cho shop tự chụp (Hình thật 100%).
- Sản phẩm trước khi gửi cho bạn được kiểm tra kĩ càng, cẩn thận.
- Hoàn tiền nếu sản phẩm không giống với mô tả.
- Shop hỗ trợ đổi hàng khi không vừa size.
- Giao hàng trên toàn quốc, nhận hàng trả tiền 
- Hỗ trợ đổi trả theo quy định của Shopee 
- Khi nhận hàng mà gặp bất kỳ vấn đề gì về sản phẩm thì nhắn tin ngay cho shop hỗ trợ nhé, còn các bạn ưng ý về sản phẩm hãy đánh giá 5* cho shop nhé. ', 1, 0, CAST(N'2021-02-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'2d2a5b14-9ea8-407e-b372-c56a30e73f88', N'Quần Jeans 2021', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'903c5509-bdf1-4e42-9bff-9579c14af274', N'Long An', N'Anguilla', N'', 1, 0, CAST(N'2021-05-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'3ad3b746-bfde-4398-834d-526f5199efe2', N'Laptop Dell Latitude', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'b597fb56-d58a-4af0-aafd-7a81ea765e3e', N'Phú Yên', N'Dominica', N'', 1, 0, CAST(N'2021-05-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'481bd157-abcc-4370-8cf5-226a42dee147', N'Áo thun PRO 2021', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-9a03-0242ac130003', N'a60e02b8-f4be-496e-bff1-2b2100376432', N'Kiên Giang', N'Australia', N'', 1, 0, CAST(N'2021-09-02' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'6509605e-98df-4d20-b594-cc100172b5d9', N'Áo cam cá tính', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-9a03-0242ac130003', N'a60e02b8-f4be-496e-bff1-2b2100376432', N'Thừa Thiên Huế', N'Azerbaijan', N'🍓🍓🍓 ÁO CAM CÁ TÍNH

👖HÀNG SIÊU NÉT !!! JEANS Cao cấp 
FULL SIZE S / M / L 

✅Eo ôi em thề. Với các ce luôn là tìm ở đâu cho được cái quần jeans chất phát ngất như của e đây ạ!?
Phải nói là chất nó mịn mượt quá ạ, bảo mặc jeans mùa này nóng nhưng không đâu ạ mặc nó mát ko hề bó sát cứng đơ đâu ạ.
 
📩 TUYỂN SỈ TỪ 5SP BẤT KÌ
       CHỐT ĐƠN BUÔN : ib + ZALO 0962251859

Các bạn phân vân khi không biết mình mặc có vừa không thì chat với chúng mình nhé!

CHÍNH SÁCH BÁN HÀNG
- Hàng có sẵn, cam kết giao hàng ngay khi nhận được đơn .
- 100% hình ảnh cho shop tự chụp (Hình thật 100%).
- Sản phẩm trước khi gửi cho bạn được kiểm tra kĩ càng, cẩn thận.
- Hoàn tiền nếu sản phẩm không giống với mô tả.
- Shop hỗ trợ đổi hàng khi không vừa size.
- Giao hàng trên toàn quốc, nhận hàng trả tiền 
- Hỗ trợ đổi trả theo quy định của Shopee 
- Khi nhận hàng mà gặp bất kỳ vấn đề gì về sản phẩm thì nhắn tin ngay cho shop hỗ trợ nhé, còn các bạn ưng ý về sản phẩm hãy đánh giá 5* cho shop nhé. CẢM ƠN CÁC BẠN NHÌU NHÌU ', 1, 0, CAST(N'2021-09-30' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'7cacb402-2250-4fcc-b1f6-bd539ce4bfe1', N'iPad 8 128GB 2020', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'4e75891d-abed-40c5-9af4-70aebbf908b1', N'Bà Rịa Vũng Tàu', N'Australia', N'', 1, 0, CAST(N'2021-06-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'7ebf09e7-80ae-444f-9de8-1df8c5cabd14', N'Quần thun nam 2021', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'903c5509-bdf1-4e42-9bff-9579c14af274', N'Bắc Ninh', N'Anguilla', N'', 1, 0, CAST(N'2021-10-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'85057c71-0152-4824-9235-9da71b1cadd6', N'Ipad Pro 2020 V2', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'5eb43443-c6ed-4744-963d-6a6b5c6260aa', N'Đồng Nai', N'Azerbaijan', N'', 1, 0, CAST(N'2021-10-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'9a4017c6-a6d1-43ea-b4b3-a6bb6d09a9ed', N'Apple Ipad 2020', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'1475ac58-d7cd-444d-b68c-c3cf0894a3a8', N'Bình Thuận  ', N'Dominica', N'', 1, 0, CAST(N'2021-03-04' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'ad1c3533-d5f2-47be-90cc-f50eda037d4b', N'Laptop Dell G3', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'b597fb56-d58a-4af0-aafd-7a81ea765e3e', N'Long An', N'Bhutan', N'', 1, 0, CAST(N'2021-09-28' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'be35cd7c-96c3-4406-a1c2-b1adf0b9c1e9', N'Laptop Asus X415', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'62e80784-6804-472d-969c-9198eec175e8', N'43ed3f71-25f8-445d-af99-d6b989a77f4a', N'Bắc Ninh', N'Azerbaijan', N'', 1, 0, CAST(N'2021-09-28' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'e75c2fb8-e2b7-4d94-89f3-16d1dfac973d', N'Laptop Gaming Asus', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'62e80784-6804-472d-969c-9198eec175e8', N'b832dc07-4019-4ebb-880d-7dbf77909e2e', N'Bà Rịa Vũng Tàu', N'Azerbaijan', N'', 1, 0, CAST(N'2021-11-20' AS Date))
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
INSERT [dbo].[product_discount] ([id], [id_product_option], [discount], [startdate], [enddate], [status]) VALUES (N'61452020-32a7-4326-9023-04ca33949b70', N'6dba3ac0-2277-47da-8b82-167ce2723d77', 11, CAST(N'2021-10-11' AS Date), CAST(N'2021-10-11' AS Date), 1)
INSERT [dbo].[product_discount] ([id], [id_product_option], [discount], [startdate], [enddate], [status]) VALUES (N'61452020-32a7-4326-9023-04ca33949b71', N'a3156084-4d52-4953-830b-8d4b90e5e686', 20, CAST(N'2021-10-17' AS Date), CAST(N'2021-10-17' AS Date), 1)
GO
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'48140109-ad12-4d31-af65-af1f8e18ae35', CAST(N'2021-10-23' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNDk2NjE1NCwiZXhwIjoxNjM2NzY2MTU0fQ.IiNXpV5Yx6VHV3D3o2SGB0p-i1aSXliKhMP7xIBCOD2LQPNanwVw--dr-I2YaBMZ_STytvkjRKL-OBGnoOVGFw', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'3ad3b746-bfde-4398-834d-526f5199efe2')
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'991710ac-4ee7-40bc-bd07-c3123c8ee4e1', CAST(N'2021-10-19' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNDY0ODU3NywiZXhwIjoxNjM2NDQ4NTc3fQ.1WkyLjiPtnTDCYIpSf6OiNUHKJBP_FsWBX0IXqsvDV2RclM8SdOyKBXdiNhaWR4q4I8NNmUmMDXyWUvDt3vJqQ', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'3ad3b746-bfde-4398-834d-526f5199efe2')
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'aa3fe8c3-a0e7-4286-9af4-10973bbaf878', CAST(N'2021-10-23' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNDk2NjE1NCwiZXhwIjoxNjM2NzY2MTU0fQ.IiNXpV5Yx6VHV3D3o2SGB0p-i1aSXliKhMP7xIBCOD2LQPNanwVw--dr-I2YaBMZ_STytvkjRKL-OBGnoOVGFw', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'ad1c3533-d5f2-47be-90cc-f50eda037d4b')
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'e057e1fa-fc7f-4384-a194-ed7d6b625885', CAST(N'2021-10-23' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNDk2NjE1NCwiZXhwIjoxNjM2NzY2MTU0fQ.IiNXpV5Yx6VHV3D3o2SGB0p-i1aSXliKhMP7xIBCOD2LQPNanwVw--dr-I2YaBMZ_STytvkjRKL-OBGnoOVGFw', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'2d2a5b14-9ea8-407e-b372-c56a30e73f88')
GO
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'0de5bfa0-1621-4bb9-9474-5613dad3c8e6', N'ad1c3533-d5f2-47be-90cc-f50eda037d4b', N'Màu đen thái học 2', 15500000, N'', 1000, 12, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'111d55ea-1eaa-4f24-87dd-df8d53219bbf', N'3ad3b746-bfde-4398-834d-526f5199efe2', N'Màu xám 2', 134444444, N'', 1000, 10, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'1d0ac1f9-fe6d-44bb-a26f-bf424515f65b', N'ad1c3533-d5f2-47be-90cc-f50eda037d4b', N'Màu đen thái học 1', 15500000, N'', 1000, 12, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'1f0d0501-322d-480a-9453-9a52ec798783', N'be35cd7c-96c3-4406-a1c2-b1adf0b9c1e9', N'Màu trắng', 22000000, N'', 1000, 15, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'255e8197-a40a-4f72-9197-8db819c4b88f', N'7cacb402-2250-4fcc-b1f6-bd539ce4bfe1', N'Màu đen', 7600000, N'', 1000, 22, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'27e90686-d158-4428-ab5c-85474e6320fc', N'6509605e-98df-4d20-b594-cc100172b5d9', N'Cam', 1300000, N'chu_m', 1000, 11, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'337d4e85-92ee-4642-a056-3301b714c1fa', N'85057c71-0152-4824-9235-9da71b1cadd6', N'Màu trắng', 85000000, N'', 1000, 55, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'4a495fd1-6e75-42c6-9c25-af1ba909ef28', N'3ad3b746-bfde-4398-834d-526f5199efe2', N'Màu xám', 134444444, N'', 1000, 10, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'6dba3ac0-2277-47da-8b82-167ce2723d77', N'2d2a5b14-9ea8-407e-b372-c56a30e73f88', N'Màu xanh', 2500000, N'so_33', 1000, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'6e1ea00a-9049-4f04-8b4c-5a843db26945', N'e75c2fb8-e2b7-4d94-89f3-16d1dfac973d', N'Màu xám', 1233333, N'', 1000, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'81e3d1f6-43ff-44fc-a2a5-25a6e141451c', N'233cfb4a-2588-4ac5-8585-b1b15011c497', N'Màu xám đen', 22000000, N'', 1000, 11, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'a3156084-4d52-4953-830b-8d4b90e5e686', N'9a4017c6-a6d1-43ea-b4b3-a6bb6d09a9ed', N'Màu đen', 7000000, N'', 1000, 3, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'c86eb32c-9a0d-458f-a189-67f4ee1fa18b', N'ad1c3533-d5f2-47be-90cc-f50eda037d4b', N'Màu đen thái học 3', 15500000, N'', 1000, 12, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'ce3bdb1c-1663-4748-b577-12b96e2eaf97', N'481bd157-abcc-4370-8cf5-226a42dee147', N'Màu đen', 1300000, N'chu_XL', 1000, 55, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'ffbf9c3a-95be-4ae0-ae20-677b714a8608', N'7ebf09e7-80ae-444f-9de8-1df8c5cabd14', N'Màu đen', 15000000, N'chu_m', 1000, 13, 0)
GO
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'01409ace-ebb9-4cd0-8d4c-d6ac295eca40', N'81e3d1f6-43ff-44fc-a2a5-25a6e141451c', N'f_17c1d3cae4e_8f739f64-5a6d-4e48-b1db-add958cdd5d7.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'134407e6-c1e8-4da9-b0cd-7803edd69a20', N'6dba3ac0-2277-47da-8b82-167ce2723d77', N'f_17babc08e3d_c562bc92-79a3-4f28-bfe6-1fd47d44f6c6.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'175b3aa6-f97f-4af3-bb1b-304919aad525', N'27e90686-d158-4428-ab5c-85474e6320fc', N'f_17c1d3dbf99_c6855c09-a06f-41ac-8f54-4442ed06e7d0.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'436cf13f-46fa-48ab-b852-5bfb9fe4891f', N'ffbf9c3a-95be-4ae0-ae20-677b714a8608', N'f_17baf6bd327_9c8c54a8-4152-4c01-ac0c-9c8faacbdb33.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'4532a6f9-07b7-4d6d-b574-ecf561145ba1', N'255e8197-a40a-4f72-9197-8db819c4b88f', N'f_17baf335903_af4fc6fa-9ab3-417c-ada7-84d9d80dfefe.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'4665c6f4-0ec4-440c-b9a5-e8c01878adab', N'4a495fd1-6e75-42c6-9c25-af1ba909ef28', N'f_17bb9456776_815ba39c-754b-4b19-b0ef-d0d436ec3e2e.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'47d74bb9-5625-4953-9309-636e01130bd9', N'4a495fd1-6e75-42c6-9c25-af1ba909ef28', N'f_17bb9456777_898ef77d-dc0e-4e63-a0b7-447fecd0d52c.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'50bb17cf-1d8c-4028-b9b3-aaf780c4b661', N'ce3bdb1c-1663-4748-b577-12b96e2eaf97', N'f_17babc31b25_68a6e5bd-596f-4f4f-b579-984a9e796a9b.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'5437d729-9624-4046-b5dc-8aa2202abb81', N'c86eb32c-9a0d-458f-a189-67f4ee1fa18b', N'f_17bb928448d_e07d3945-b596-420b-9ac4-ef3b408989e0.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'96741e31-1d14-415b-9b3e-e2711c6683d0', N'111d55ea-1eaa-4f24-87dd-df8d53219bbf', N'f_17bb948100b_14d6eac2-b5ec-4dff-b449-eefe4759a455.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'9c04ba39-4adb-4913-95ce-7394a945b4c7', N'1d0ac1f9-fe6d-44bb-a26f-bf424515f65b', N'f_17bb92478b4_d3720846-9543-48c9-823e-6fde3fa4a09e.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'c8a4395e-2ccc-4216-a5dd-34e467ed87f3', N'a3156084-4d52-4953-830b-8d4b90e5e686', N'f_17bac78385a_e7feed34-750e-4353-b970-6bcbdb03b9af.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'd6b3e3d2-453c-40d9-84be-7e0bc3aa5eb5', N'0de5bfa0-1621-4bb9-9474-5613dad3c8e6', N'f_17bb92505c8_da066dda-5301-4538-a2ae-e7931f4f8464.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'e61ba279-ea45-4eb6-ab00-39dfa480ff2a', N'6e1ea00a-9049-4f04-8b4c-5a843db26945', N'f_17baf42cce3_8e03ffa7-2dc4-464d-92aa-24527fffc9ba.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'ee44e553-f9dc-43dd-a624-df210f8cdc96', N'1f0d0501-322d-480a-9453-9a52ec798783', N'f_17baf932590_75efaf15-94da-4a82-bf66-7e70dcc5c586.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'f6460170-fb63-49d5-b578-70639adfa444', N'337d4e85-92ee-4642-a056-3301b714c1fa', N'f_17bac773197_b7ecc19b-64a5-4c88-b2c8-c56afa138344.png')
GO
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'1233ffe4-ce7d-4e5f-ac55-be9e51753a33', N'tessss', N'f_17bf3452397_5ba2527d-7acb-465f-bf2a-37a7d903562b.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'3e779985-5184-4872-bf4e-ba498ea49fc6', N'testtt', N'f_17bea0f7c4d_e2f7becc-fa9d-4f11-937f-1df10abe5102.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'6c0e7aa7-cd6a-4986-b92d-fae66e3d9c49', N'Quần áo', N'daychuyengang.jfif', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'6f740447-8b4a-4c79-8ee1-3affed8022a7', N'Laptop', N'test.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'7fa890d2-fbfe-11eb-9a03-0242ac130003', N'Điện thoại - Máy tính bảng', N'test.png', 1, 0)
INSERT [dbo].[product_parent_category] ([id], [name], [image_url], [is_enable], [is_delete]) VALUES (N'82bbbce7-deb0-4e67-8144-da9360d56412', N'asdasd', N'f_17bb53d2960_06dc3b6c-48f5-4064-aa4d-42783627ca46.png', 1, 0)
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
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'28ad12da-a7c3-41aa-90b5-89f56d7bc6bf', N'6509605e-98df-4d20-b594-cc100172b5d9', N'adasdasdsad')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'7bf40155-d810-4d1b-a1ae-51196cb7d328', N'481bd157-abcc-4370-8cf5-226a42dee147', N'Áo đen')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'b097b734-22b9-49bd-bb0f-07777397d8f3', N'6509605e-98df-4d20-b594-cc100172b5d9', N'adasdasdsad')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'bb0c1b2f-cc79-4a1d-bfa7-5ae8ce21d114', N'2d2a5b14-9ea8-407e-b372-c56a30e73f88', N'Quần')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'c0145a82-c490-49a5-8890-195736e3109a', N'6509605e-98df-4d20-b594-cc100172b5d9', N'22')
GO
INSERT [dbo].[roles] ([id], [name]) VALUES (N'AD', N'ADMIN')
INSERT [dbo].[roles] ([id], [name]) VALUES (N'US', N'USER')
GO
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'Shop TIKI 2', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'f_17c0de68e46_b54a8fd9-75f4-48ef-a07c-15af8be6e53a.jpg', CAST(N'2021-11-21' AS Date), N'xzcv', N'359 Nguyễn Văn Cừ, Thành Nhất, Buôn Ma Thuột, Đắk Lắk', N'f_17c0de6907b_ec6893cb-3523-4560-b58f-c378ade51997.png', 1)
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'111111111111111111111111111111111111', N'KimThanh123', N'111111111111111111111111111111111111', N'avt.jpg', CAST(N'2021-09-25' AS Date), NULL, N'359 Nguyễn Văn Cừ, Thành Nhất, Buôn Ma Thuột, Đắk Lắk', N'f_17c0de6907b_ec6893cb-3523-4560-b58f-c378ade51997.png', 1)
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'50c49b5d-65ea-4428-8ebc-2fe9e81b5b18', N'asdasd123', N'5e14928d-603f-46ba-9ffc-bce57bc91414', N'bia.jpg', CAST(N'2021-11-17' AS Date), NULL, N'Can Tho, Cam Nghĩa, Cam Ranh, Khánh Hòa', NULL, 1)
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'89337ca3-cf66-4ca5-b604-76652d68b026', N'thaihuy1234433', N'ae68ec72-02f3-11ec-9a03-0242ac130003', N'avt.jpg', CAST(N'2021-08-28' AS Date), NULL, N'asdasdasd, Ngọc Hải, Đồ Sơn, Hải Phòng', NULL, 1)
GO
INSERT [dbo].[user_address] ([id], [user_id], [first_name], [last_name], [phone], [address]) VALUES (N'86510bd8-f839-420a-b790-ac5bf19ff946', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'Thái', N'Hà', N'0798123094', N'327 Không Quân,Sông Xoài,Tân Thành,Bà Rịa Vũng Tàu')
GO
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'thanh', N'123', N'Hoàng Thái', N'Học', N'maihoc12344@gmail.com', N'M', N'359/33 Nguyễn Văn Cừ, Mỹ Thạnh, Hàm Thuận Bắc, Bình Thuận  ', N'0798089123', 0, 1, NULL, NULL, CAST(N'2021-10-21' AS Date))
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'111111111111111111111111111111111111', N'admin', N'admin', N'nguyen', N'viet', N'viet@gmail.com', N'0', N'vb', N'0122456789', 0, 1, NULL, NULL, CAST(N'2021-11-21' AS Date))
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'5e14928d-603f-46ba-9ffc-bce57bc91414', N'asdasd123', N'maihoc63Asd', N'Thái Hoàng', N'Học', N'maihoc348@gmail.com', N'M', N'Can Tho, Cam Nghĩa, Cam Ranh, Khánh Hòa', N'0798089717', 0, 0, N'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNDc3MzkiLCJpYXQiOjE2MzcxMzcwMzEsImV4cCI6MTYzNzEzNzMzMX0.ecm30A5Lc4aa83IGKfTElL4fIvNWUUDqXVxA2mi7e6k', NULL, CAST(N'2021-01-21' AS Date))
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'ae68ec72-02f3-11ec-9a03-0242ac130003', N'vinh', N'pass', N'Lê Minh', N'Vinh', N'mvinhle22@gmail.com', N'M', N'Trường Tiểu Học Vĩnh Thới 3', N'0856538112', 0, 1, NULL, NULL, CAST(N'2021-11-21' AS Date))
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_shop]    Script Date: 11/22/2021 2:15:37 PM ******/
ALTER TABLE [dbo].[shop] ADD  CONSTRAINT [IX_shop] UNIQUE NONCLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__F3DBC5728DDF85C9]    Script Date: 11/22/2021 2:15:37 PM ******/
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
ALTER TABLE [dbo].[history_view_page] ADD  CONSTRAINT [DF_history_view_page_date_view]  DEFAULT (getdate()) FOR [date_view]
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
ALTER TABLE [dbo].[product] ADD  CONSTRAINT [DF_product_createdate]  DEFAULT (getdate()) FOR [createdate]
GO
ALTER TABLE [dbo].[product_meta] ADD  CONSTRAINT [DF__product_m__user___123EB7A3]  DEFAULT (NULL) FOR [user_id]
GO
ALTER TABLE [dbo].[product_option] ADD  CONSTRAINT [DF__product_o__is_de__160F4887]  DEFAULT ((0)) FOR [is_delete]
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
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF_users_create_date]  DEFAULT (getdate()) FOR [create_date]
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
ALTER TABLE [dbo].[cart]  WITH CHECK ADD  CONSTRAINT [FK_cart_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[cart] CHECK CONSTRAINT [FK_cart_users]
GO
ALTER TABLE [dbo].[cart_item]  WITH CHECK ADD  CONSTRAINT [FK_cart_item_cart] FOREIGN KEY([id_cart])
REFERENCES [dbo].[cart] ([id])
GO
ALTER TABLE [dbo].[cart_item] CHECK CONSTRAINT [FK_cart_item_cart]
GO
ALTER TABLE [dbo].[cart_item]  WITH CHECK ADD  CONSTRAINT [FK_cart_item_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
GO
ALTER TABLE [dbo].[cart_item] CHECK CONSTRAINT [FK_cart_item_product_option]
GO
ALTER TABLE [dbo].[evaluate]  WITH CHECK ADD  CONSTRAINT [FK_evaluate_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
GO
ALTER TABLE [dbo].[evaluate] CHECK CONSTRAINT [FK_evaluate_product]
GO
ALTER TABLE [dbo].[evaluate]  WITH CHECK ADD  CONSTRAINT [FK_evaluate_users] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[evaluate] CHECK CONSTRAINT [FK_evaluate_users]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_order] FOREIGN KEY([orderid])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_order]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_product_option]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FK_order_users] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FK_order_users]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FK_orders_order_discount] FOREIGN KEY([id_discount])
REFERENCES [dbo].[order_discount] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FK_orders_order_discount]
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
ALTER TABLE [dbo].[product_discount]  WITH CHECK ADD  CONSTRAINT [FK_product_discount_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
GO
ALTER TABLE [dbo].[product_discount] CHECK CONSTRAINT [FK_product_discount_product_option]
GO
ALTER TABLE [dbo].[product_meta]  WITH CHECK ADD  CONSTRAINT [FK_product_meta_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
GO
ALTER TABLE [dbo].[product_meta] CHECK CONSTRAINT [FK_product_meta_product]
GO
ALTER TABLE [dbo].[product_meta]  WITH CHECK ADD  CONSTRAINT [FK_product_meta_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[product_meta] CHECK CONSTRAINT [FK_product_meta_users]
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
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'product', @level2type=N'COLUMN',@level2name=N'createdate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'product_option', @level2type=N'COLUMN',@level2name=N'is_delete'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "product_discount"
            Begin Extent = 
               Top = 7
               Left = 51
               Bottom = 170
               Right = 242
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_discount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_discount'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "evaluate"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 170
               Right = 242
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_evaluate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_evaluate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = -120
         Left = 0
      End
      Begin Tables = 
         Begin Table = "product_option"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 170
               Right = 242
            End
            DisplayFlags = 280
            TopColumn = 3
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_option'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_option'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "product_option_image"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 148
               Right = 264
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_option_image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_option_image'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "order_detail"
            Begin Extent = 
               Top = 7
               Left = 48
               Bottom = 170
               Right = 264
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_option_order_detail'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'view_product_option_order_detail'
GO
