

IF (OBJECT_ID('fn_string_distance') IS NOT NULL) BEGIN
    DROP FUNCTION [dbo].[fn_string_distance]
END

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



IF (OBJECT_ID('fn_cover_unsign_string') IS NOT NULL) BEGIN
    DROP FUNCTION [fn_cover_unsign_string]
END

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

IF (OBJECT_ID('fn_matcher_string') IS NOT NULL) BEGIN
    DROP FUNCTION [fn_matcher_string]
END
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


IF (OBJECT_ID('view_product_search') IS NOT NULL) BEGIN
    DROP VIEW [view_product_search]
END
GO
-- view product add keyword
CREATE VIEW [view_product_search]
AS
    SELECT  product.id as id_product,
            product.name -- keysearch
            as keysearch 
        FROM product
GO
