USE [EDE]
GO
/****** Object:  UserDefinedFunction [dbo].[fn_cover_unsign_string]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  UserDefinedFunction [dbo].[fn_matcher_string]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  UserDefinedFunction [dbo].[fn_string_distance]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[authorities]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[cart]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[cart_item]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[evaluate]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[evaluate_image]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[help_answer]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[help_category]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[help_question]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[history_view_page]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[keyword]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[notify]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[order_detail]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[order_discount]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[orders]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[payment]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_brand]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_child_category]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_detail]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_discount]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_meta]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_option]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_option_image]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_parent_category]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_parent_child_category]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_tag]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[product_tag_search]    Script Date: 11/27/2021 6:31:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_tag_search](
	[id] [char](36) NOT NULL,
	[cookie] [varchar](200) NULL,
	[id_tag] [char](36) NOT NULL,
	[date_view] [date] NOT NULL,
 CONSTRAINT [PK_product_tag_search] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[receive_news]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[roles]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[shiper_partner]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[shiper_shop]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[shop]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[system_config]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[user_address]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[user_follow]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[user_voucher]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[users]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[voucher]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  Table [dbo].[voucher_type]    Script Date: 11/27/2021 6:31:48 PM ******/
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
/****** Object:  View [dbo].[view_product_discount]    Script Date: 11/27/2021 6:31:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_discount]
AS
SELECT id
FROM     dbo.product_discount
GO
/****** Object:  View [dbo].[view_product_evaluate]    Script Date: 11/27/2021 6:31:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_evaluate]
AS
SELECT id
FROM     dbo.evaluate
GO
/****** Object:  View [dbo].[view_product_option]    Script Date: 11/27/2021 6:31:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_option]
AS
SELECT id
FROM     dbo.product_option
GO
/****** Object:  View [dbo].[view_product_option_image]    Script Date: 11/27/2021 6:31:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_option_image]
AS
SELECT id
FROM     dbo.product_option_image
GO
/****** Object:  View [dbo].[view_product_option_order_detail]    Script Date: 11/27/2021 6:31:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_product_option_order_detail]
AS
SELECT id
FROM     dbo.order_detail
GO
/****** Object:  View [dbo].[view_product_search]    Script Date: 11/27/2021 6:31:48 PM ******/
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
INSERT [dbo].[cart] ([id], [user_id]) VALUES (N'5da3bb22-8837-4231-9dbe-b55ba1fac82f', N'111111111111111111111111111111111111')
INSERT [dbo].[cart] ([id], [user_id]) VALUES (N'b473b7b1-052d-4b8f-aa0f-f10d89c61a8d', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8')
GO
INSERT [dbo].[cart_item] ([id], [id_cart], [id_product_option], [quantity]) VALUES (N'154d2115-d95e-4bc7-aa48-70d3c342ccca', N'b473b7b1-052d-4b8f-aa0f-f10d89c61a8d', N'fdf52667-a84e-443c-bdf7-9723803301c2', 1)
INSERT [dbo].[cart_item] ([id], [id_cart], [id_product_option], [quantity]) VALUES (N'd777d18c-9a34-4871-87b2-00281fbcc816', N'5da3bb22-8837-4231-9dbe-b55ba1fac82f', N'fdf52667-a84e-443c-bdf7-9723803301c2', 1)
GO
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'68020791-1c13-4413-b646-ea492dcbdbdb', N'117.7.221.3', NULL, CAST(N'2021-11-25' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'8b9f2401-ab4f-4cc0-806a-e6ecb0d13b47', N'117.7.221.3', N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzNzYzMzYwMywiZXhwIjoxNjM5NDMzNjAzfQ.qACCNTt3qVh22FxnGh8ADT1uVWHkhDjGkixHfG-X1-WNJewJmkl-a0XIwhMZLUeKkwVln7uLYilfc6jyX11WRQ', CAST(N'2021-11-23' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'90e7f4fe-8aba-4dfa-8ca2-f6d77810c522', N'117.7.221.3', NULL, CAST(N'2021-09-20' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'90e7f4fe-8aba-4dfa-8ca2-f6d77810c580', N'117.7.221.3', NULL, CAST(N'2021-11-22' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'aaace31d-5021-4cee-858d-c6440925a8f7', N'117.7.221.3', N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNzcxNDM3MCwiZXhwIjoxNjM5NTE0MzcwfQ.TKhxUgtaJs0w8m6fgdhv36iUKW8z92R-e6UicZHu3mbj13SqURtPCagyeeZZB4hatBTSunMOOog6sFr506CflQ', CAST(N'2021-11-24' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'b4a212a0-684c-4ef9-ac97-193b081d358b', N'117.7.220.80', NULL, CAST(N'2021-11-26' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'b808dd44-1e4a-4550-bb23-9a0a214c9642', N'117.7.220.80', NULL, CAST(N'2021-11-25' AS Date))
INSERT [dbo].[history_view_page] ([id], [ip], [cookie], [date_view]) VALUES (N'ee4a6bc7-9d51-4fc6-8336-14c2e6517b4c', N'117.7.220.80', NULL, CAST(N'2021-11-27' AS Date))
GO
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'7596a5cf-2684-42e7-b83d-37e4d6bcef76', N'08d2d432-62b9-4500-bca4-132150da506b', N'7596a5cf-2684-42e7-b83d-37e4d6bcef76', 10000, 12, 13333)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'8d61d600-4cd7-11ec-81d3-0242ac130003', N'7ccefc30-09dd-4b88-bb97-f79bc4ca44bc', N'1aae9a90-742c-4c29-9643-dc4e05f3bd90', 20000, 22, 30000)
INSERT [dbo].[order_detail] ([id], [id_product_option], [orderid], [price], [quantity], [fee_ship]) VALUES (N'dafe13e2-4d18-11ec-81d3-0242ac130003', N'fdf52667-a84e-443c-bdf7-9723803301c2', N'1aae9a90-742c-4c29-9643-dc4e05f3bd90', 222222, 133, 22222)
GO
INSERT [dbo].[order_discount] ([id], [total], [discount], [todate], [enddate], [status]) VALUES (N'9c6acf44-4515-4181-92b5-105bfdea7410', 20000000, 30, CAST(N'2021-10-13' AS Date), CAST(N'2021-10-30' AS Date), 1)
INSERT [dbo].[order_discount] ([id], [total], [discount], [todate], [enddate], [status]) VALUES (N'c07e1427-ec26-440a-a466-90592948b12c', 100000, 22, CAST(N'2021-10-31' AS Date), CAST(N'2021-11-28' AS Date), 1)
GO
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'1aae9a90-742c-4c29-9643-dc4e05f3bd90', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'079912838', CAST(N'2021-11-24' AS Date), 250000, NULL, NULL, N'Đã giao')
INSERT [dbo].[orders] ([id], [id_user], [phone], [create_date], [total_amount], [note], [id_discount], [status]) VALUES (N'7596a5cf-2684-42e7-b83d-37e4d6bcef76', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'079812308', CAST(N'2021-11-24' AS Date), 300000, NULL, NULL, N'Đã giao')
GO
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'556e75e6-8fbd-405e-ac54-20ae6f0acd36', N'Laptop Dell G3', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'770d64d6-d8d4-447c-9cbd-ee7557a785ec', N'Đà Nẵng', N'Viet Nam', N'THÔNG SỐ KĨ THUẬT: - Cpu: Intel(R) core I5-4200U - Cực mạnh - Ram: 4Gb - SSD 128Gb CAO CẤP - CỰC NHANH - CARD màn hình: Intel HD Graphics 4400 - Kết nối: HDMI, USB 3.0, DisplayPort mini, headphone / microphone, SD Card, SmartCard, - Pin chờ: trên 3 tiếng - Màn hình: 12.5 inch nhỏ gọn - Kích thước 310.5 x 211 x 20 mm - TRỌNG LƯỢNG: 1.34KG - TẶNG: Sạc, Chuột quang và lót chuột GIỚI THIỆU VỀ LAPTOP DELL E7240 Máy có thiết kế thời trang hấp dẫn thon gọn và bắt mắt với nắp nhôm cao cấp cho bạn sự sang trọng quý phái. Trang bị cấu hình cao cấp với bộ vi xử lý thế hệ thứ tư Intel Core i5 cùng chipset đồ họa cao cấp. Dell Latitude E7240 thực sự là một sự lựa chọn hoàn hảo cho bạn với những ưu điểm được tóm tắt dưới đây: Touchpad thông minh. Thiết kế nhỏ gọn, kiểu dáng hấp dẫn. Cấu hình cao cấp cho bộ máy chạy mượt mà và ổn định. Màn hình 12.5 inch thiết kế nhỏ gọn với độ phân giải cao cho bạn thỏa sức làm việc và giải trí. Bộ xử lý đồ họa Intel HD Graphics 4400 giúp bạn chơi game và thiết kế đồ họa rất ổn định. * KHÁCH YÊU LƯU Ý GIÚP SHOP VỀ CHẾ ĐỘ BẢO HÀNH NHƯ SAU Ạ: 1. KHÁCH ĐƯỢC TEST MÁY 5 NGÀY, LỖI PHẦN CỨNG TRẢ LẠI SHOP, SHOP BAO HẾT TIỀN SHIP NHA. 2. TRONG THỜI GIAN BẢO HÀNH CÒN LẠI, NẾU LỖI PHẦN CỨNG SHOP BẢO HÀNH BẰNG TRÁCH NHIỆM VÀ LƯƠNG TÂM (CÁI NÀY DO BỆNH NGHỀ NGHIỆP NGHE, DO GIÁM ĐỐC CÔNG TY XUẤT THÂN TỪ GIÁO VIÊN!!!!!!). TRÊN PHƯƠNG CHÂM KHÔNG ĐỂ KHÁCH HÀNG CHỊU BẤT KỲ THIỆT THÒI NÀO. 3. VIỆC XẢY RA LỖI LÀ RẤT HY HỮU, SHOP KHÔNG MUỐN, KHÁCH CŨNG KHÔNG MUỐN NÊN RẤT MONG KHÁCH YÊU THIỆN CHÍ HỢP TÁC VỚI SHOP ĐỂ GIẢI QUYẾT. SĐT LIÊN HỆ: 0966275195 (GẶP ANH HUY) ##laptop  #dell7240 #maytinhdell  #laptopsinhvien #laptop dell#laptop #laptopcu #laptopcugiare #laptopgiare #laptopxachtay #laptopsinhvien #laptopben #maytinhxachtay #laptopvanphong #laptopMT #laptopgiare #thoitrang #sinhvien #vanphong #laptop cũ', 1, 0, CAST(N'2021-11-23' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'955c285b-209a-4488-ad60-04f4ac9ffacd', N'testtt', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'e6518066-fbfc-11eb-123s-0242ac130003', N'43ed3f71-25f8-445d-af99-d6b989a77f4a', N'Bà Rịa Vũng Tàu', N'Anguilla', N'', 1, 0, CAST(N'2021-11-24' AS Date))
INSERT [dbo].[product] ([id], [name], [id_shop], [id_brand], [id_category], [location], [origin], [description], [enable], [deleted], [createdate]) VALUES (N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Iphone X', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'8fd0c8e4-04e6-11ec-9a03-0242ac130003', N'f7adbb9c-fd08-11eb-9a03-0242ac130003', N'Khánh Hòa', N'Viet Nam', N'iPhone 13 Pro Max. Một nâng cấp hệ thống camera chuyên nghiệp hoành tráng chưa từng có. Màn hình Super Retina XDR với ProMotion cho cảm giác nhanh nhạy hơn. Chip A15 Bionic thần tốc. Mạng 5G siêu nhanh.1 Thiết kế bền bỉ và thời lượng pin dài nhất từng có trên iPhone (2).', 1, 0, NULL)
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
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'cf0736c8-2ced-4256-bec7-6aec0a7edc12', N'12560872-9b3b-419e-8e2c-6ec1c335393b', N'testtt', N'f_17d5af606dd_59881a03-5e62-49d5-ab58-4e7500e23d4f.jpg', 1, 1)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'd5ccf22d-fbfd-458c-96cf-833fbaf9d40e', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Smart Phone 2021', N'applewatch1.jpg', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'db4fd316-de56-4101-8f97-7211ee06649e', N'0f034c3a-c669-4b73-987f-9f1001fbc917', N'Asus ROG 2021', N'37428_asus_rog_strix_g17_g713qm_k4113t_r7_5800h_197c27cd1bce4217baedb68f8353d1c5_master.png', 1, 0)
INSERT [dbo].[product_child_category] ([id], [id_parent], [name], [image_url], [is_enable], [is_delete]) VALUES (N'f7adbb9c-fd08-11eb-9a03-0242ac130003', N'042c5e5b-1394-4c93-81fa-4a9d676017da', N'Iphone X', N'test.png', 1, 0)
GO
INSERT [dbo].[product_discount] ([id], [id_product_option], [discount], [startdate], [enddate], [status]) VALUES (N'6891d3a6-f420-4f59-bd53-f34e7dd899f8', N'7ccefc30-09dd-4b88-bb97-f79bc4ca44bc', 22, CAST(N'2021-11-23' AS Date), CAST(N'2021-11-30' AS Date), 1)
GO
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'1a1fe3b9-fa4e-4594-98ef-98034579bc2d', CAST(N'2021-11-24' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNzc1MTk5MiwiZXhwIjoxNjM5NTUxOTkyfQ.VJG_oY1j8ReWzTMR5oJ91-rW85j7wWec_7nogm7mx9bA2hMxL6x9D1mRy7U4wrpF46vXHzhkL_xEJIX2PzRQtQ', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'fed55496-bedf-45fc-ac6c-e1077915c940')
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'7e680fc9-13d0-4293-8f37-1d66f4857771', CAST(N'2021-11-25' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNzgxNTI0NSwiZXhwIjoxNjM5NjE1MjQ1fQ.dWlp0AH6rVgXAMqk1MxMgQR46MEng92vC3Yu9QySKk1D-Z3y3_XRTGUmu1XySs00rsEb1NM_TCNWNDt_RfgUmA', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'556e75e6-8fbd-405e-ac54-20ae6f0acd36')
INSERT [dbo].[product_meta] ([id], [date_view], [cookie], [user_id], [id_product]) VALUES (N'b27efa76-c661-41f4-9596-0126736d3779', CAST(N'2021-11-24' AS Date), N'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGFuaCIsImlhdCI6MTYzNzc1MTk5MiwiZXhwIjoxNjM5NTUxOTkyfQ.VJG_oY1j8ReWzTMR5oJ91-rW85j7wWec_7nogm7mx9bA2hMxL6x9D1mRy7U4wrpF46vXHzhkL_xEJIX2PzRQtQ', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'556e75e6-8fbd-405e-ac54-20ae6f0acd36')
GO
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'08d2d432-62b9-4500-bca4-132150da506b', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Màu xám', 25000000, N'', 100, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'11cf954f-4cf2-421c-beda-58c96714f37e', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Màu xám', 25000000, N'', 100, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'1de591f4-be2a-4835-b897-fa83d722598c', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Màu xám', 25000000, N'', 100, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'41c33fec-fd98-44ee-be0c-2d7dfc484dfa', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Màu xám', 25000000, N'', 100, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'42b38588-f188-4497-bc85-a08e859e5663', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Màu xám', 25000000, N'', 100, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'7730c89d-47d1-4c24-b899-4ca7732e7cde', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Màu xám', 25000000, N'', 100, 13, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'7ccefc30-09dd-4b88-bb97-f79bc4ca44bc', N'556e75e6-8fbd-405e-ac54-20ae6f0acd36', N'Màu trắng', 10000000, N'', 150, 15, 0)
INSERT [dbo].[product_option] ([id], [id_product], [display_name], [price], [size], [weight], [quantity], [is_delete]) VALUES (N'fdf52667-a84e-443c-bdf7-9723803301c2', N'955c285b-209a-4488-ad60-04f4ac9ffacd', N'testt', 11111, N'', 111, 111, 1)
GO
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'162c3ae5-12b4-4805-b9b8-21019a826c15', N'1de591f4-be2a-4835-b897-fa83d722598c', N'f_17d566a603b_8c1d098a-31cb-4277-96e8-8151af4edc8f.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'318b57ae-4c94-4e1b-a761-c2b856dff342', N'42b38588-f188-4497-bc85-a08e859e5663', N'f_17d566bf159_2a6ad58b-6558-42bd-a4b7-18913ccc87f3.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'383e29c7-e9e6-409e-8c2c-33217c25dd4c', N'7ccefc30-09dd-4b88-bb97-f79bc4ca44bc', N'f_17d4bed4e89_31fcf738-3b89-4e57-b013-d759c55600a7.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'88743f51-025e-4aaa-a551-4fcf703f4015', N'11cf954f-4cf2-421c-beda-58c96714f37e', N'f_17d566c2b91_930ece2d-a1dc-4ef1-ba28-9e5c81ba5d9b.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'93226c5c-bec6-42fd-a6bf-c392c2d2744a', N'41c33fec-fd98-44ee-be0c-2d7dfc484dfa', N'f_17d566c0e2a_f5a4f945-c600-4389-adf2-18e68d6d2386.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'9f220aa3-ab90-48ff-b451-b0981b90fec9', N'7730c89d-47d1-4c24-b899-4ca7732e7cde', N'f_17d566aae7a_672fd7cb-e348-4d12-9104-41a30387b571.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'b2912986-29f5-4fdc-b443-3740a8fb4910', N'fdf52667-a84e-443c-bdf7-9723803301c2', N'f_17d4c19ec0c_345bc6a6-f717-4900-b1e4-3a5606c46307.jpg')
INSERT [dbo].[product_option_image] ([id], [id_product_option], [image]) VALUES (N'd5d3abc9-6812-4220-9945-e188e16ec109', N'08d2d432-62b9-4500-bca4-132150da506b', N'f_17d4beb707c_f4fed670-f2f9-4239-85ae-e46e1204c1a5.jpg')
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
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'447b837f-7b83-4edd-b34e-69952b476b7b', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'Iphone')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'a807646f-5700-4fc9-aae4-60470ee32d8f', N'955c285b-209a-4488-ad60-04f4ac9ffacd', N'testt')
INSERT [dbo].[product_tag] ([id], [id_product], [tag]) VALUES (N'd8a3d5f1-4ce2-4bcc-b220-39900f8db0a0', N'fed55496-bedf-45fc-ac6c-e1077915c940', N'smartphone')
GO
INSERT [dbo].[product_tag_search] ([id], [cookie], [id_tag], [date_view]) VALUES (N'408b830c-4f5d-11ec-81d3-0242ac130003', N'test', N'447b837f-7b83-4edd-b34e-69952b476b7b', CAST(N'2021-01-01' AS Date))
INSERT [dbo].[product_tag_search] ([id], [cookie], [id_tag], [date_view]) VALUES (N'408b830c-4f5d-11ec-81d3-0242ac130033', N'tessss', N'447b837f-7b83-4edd-b34e-69952b476b7b', CAST(N'2021-11-27' AS Date))
INSERT [dbo].[product_tag_search] ([id], [cookie], [id_tag], [date_view]) VALUES (N'955c285b-209a-4488-ad60-04f4ac9ffacd', N'qwewq', N'a807646f-5700-4fc9-aae4-60470ee32d8f', CAST(N'2021-11-27' AS Date))
GO
INSERT [dbo].[roles] ([id], [name]) VALUES (N'AD', N'ADMIN')
INSERT [dbo].[roles] ([id], [name]) VALUES (N'US', N'USER')
GO
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f30333', N'Shop TIKI 2', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'f_17c0de68e46_b54a8fd9-75f4-48ef-a07c-15af8be6e53a.jpg', CAST(N'2021-11-21' AS Date), N'xzcv', N'359 Nguyễn Văn Cừ, Thành Nhất, Buôn Ma Thuột, Đắk Lắk', N'f_17c0de6907b_ec6893cb-3523-4560-b58f-c378ade51997.png', 1)
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'111111111111111111111111111111111111', N'KimThanh123', N'111111111111111111111111111111111111', N'avt.jpg', CAST(N'2021-09-25' AS Date), NULL, N'359 Nguyễn Văn Cừ, Thành Nhất, Buôn Ma Thuột, Đắk Lắk', N'f_17c0de6907b_ec6893cb-3523-4560-b58f-c378ade51997.png', 1)
INSERT [dbo].[shop] ([id], [name], [user_id], [image], [create_date], [description], [address], [image_sub], [status]) VALUES (N'89337ca3-cf66-4ca5-b604-76652d68b026', N'thaihuy1234433', N'ae68ec72-02f3-11ec-9a03-0242ac130003', N'avt.jpg', CAST(N'2021-08-28' AS Date), NULL, N'asdasdasd, Ngọc Hải, Đồ Sơn, Hải Phòng', NULL, 1)
GO
INSERT [dbo].[user_address] ([id], [user_id], [first_name], [last_name], [phone], [address]) VALUES (N'86510bd8-f839-420a-b790-ac5bf19ff946', N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'Thái', N'Hà', N'0798123094', N'327 Không Quân,Sông Xoài,Tân Thành,Bà Rịa Vũng Tàu')
GO
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'0fd7abe4-3c7d-4b75-97b8-dcbcb1f302d8', N'thanh', N'123', N'Hoàng Thái', N'Học', N'maihoc12344@gmail.com', N'M', N'359/33 Nguyễn Văn Cừ, Mỹ Thạnh, Hàm Thuận Bắc, Bình Thuận  ', N'0798089123', 0, 1, NULL, NULL, CAST(N'2021-10-21' AS Date))
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'111111111111111111111111111111111111', N'admin', N'admin', N'nguyen', N'viet', N'viet@gmail.com', N'0', N'vb', N'0122456789', 0, 1, NULL, NULL, CAST(N'2021-11-21' AS Date))
INSERT [dbo].[users] ([id], [username], [password], [first_name], [last_name], [email], [gender], [address], [phone], [is_delete], [is_active], [otp], [photo], [create_date]) VALUES (N'ae68ec72-02f3-11ec-9a03-0242ac130003', N'vinh', N'pass', N'Lê Minh', N'Vinh', N'mvinhle22@gmail.com', N'M', N'Trường Tiểu Học Vĩnh Thới 3', N'0856538112', 0, 1, NULL, NULL, CAST(N'2021-11-21' AS Date))
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_shop]    Script Date: 11/27/2021 6:31:49 PM ******/
ALTER TABLE [dbo].[shop] ADD  CONSTRAINT [IX_shop] UNIQUE NONCLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__users__F3DBC5728DDF85C9]    Script Date: 11/27/2021 6:31:49 PM ******/
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
ALTER TABLE [dbo].[product_tag_search] ADD  CONSTRAINT [DF_product_tag_search_date_view]  DEFAULT (getdate()) FOR [date_view]
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
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[cart_item] CHECK CONSTRAINT [FK_cart_item_cart]
GO
ALTER TABLE [dbo].[cart_item]  WITH CHECK ADD  CONSTRAINT [FK_cart_item_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[cart_item] CHECK CONSTRAINT [FK_cart_item_product_option]
GO
ALTER TABLE [dbo].[evaluate]  WITH CHECK ADD  CONSTRAINT [FK_evaluate_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[evaluate] CHECK CONSTRAINT [FK_evaluate_product]
GO
ALTER TABLE [dbo].[evaluate]  WITH CHECK ADD  CONSTRAINT [FK_evaluate_users] FOREIGN KEY([id_user])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[evaluate] CHECK CONSTRAINT [FK_evaluate_users]
GO
ALTER TABLE [dbo].[evaluate_image]  WITH CHECK ADD  CONSTRAINT [FK_evaluate_image_evaluate] FOREIGN KEY([evaluate_id])
REFERENCES [dbo].[evaluate] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[evaluate_image] CHECK CONSTRAINT [FK_evaluate_image_evaluate]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_order] FOREIGN KEY([orderid])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_order]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
ON DELETE CASCADE
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
ALTER TABLE [dbo].[product_detail]  WITH CHECK ADD  CONSTRAINT [FK_product_detail_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
GO
ALTER TABLE [dbo].[product_detail] CHECK CONSTRAINT [FK_product_detail_product]
GO
ALTER TABLE [dbo].[product_discount]  WITH CHECK ADD  CONSTRAINT [FK_product_discount_product_option] FOREIGN KEY([id_product_option])
REFERENCES [dbo].[product_option] ([id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[product_discount] CHECK CONSTRAINT [FK_product_discount_product_option]
GO
ALTER TABLE [dbo].[product_meta]  WITH CHECK ADD  CONSTRAINT [FK_product_meta_product] FOREIGN KEY([id_product])
REFERENCES [dbo].[product] ([id])
ON DELETE CASCADE
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
ALTER TABLE [dbo].[product_tag_search]  WITH CHECK ADD  CONSTRAINT [FK_product_tag_search_product_tag] FOREIGN KEY([id_tag])
REFERENCES [dbo].[product_tag] ([id])
GO
ALTER TABLE [dbo].[product_tag_search] CHECK CONSTRAINT [FK_product_tag_search_product_tag]
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
