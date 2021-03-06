USE [EDE]
GO

IF (OBJECT_ID('fn_string_distance') IS NOT NULL) BEGIN
    DROP FUNCTION [dbo].[fn_string_distance]
END
GO
-- using levenshtein distance algorithm
CREATE FUNCTION [dbo].[fn_string_distance](@source NVARCHAR(1000), @desk NVARCHAR(1000))
RETURNS INTEGER
AS BEGIN

    SELECT  @source = UPPER(@source),
            @desk = UPPER(@desk)

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

IF (OBJECT_ID('fn_matcher_string') IS NOT NULL) BEGIN
    DROP FUNCTION [fn_matcher_string]
END
GO
-- gender to ‱ 
-- xem khớp nhau vao nhiêu phần vạn ps:// vì phần % độ chính xác có thể không cao
CREATE FUNCTION [dbo].[fn_matcher_string](@source NVARCHAR(1000), @desk NVARCHAR(1000))
RETURNS FLOAT
AS BEGIN
    DECLARE @result FLOAT,
            @source_length FLOAT,
            @desk_length FLOAT
    SELECT @result = [dbo].[fn_string_distance](@source, @desk), @source_length = LEN(@source),  @desk_length = LEN(@desk)
    RETURN 10000 - (@result / (@source_length + @desk_length)) * 10000
END
GO


IF (OBJECT_ID('view_product_search') IS NOT NULL) BEGIN
    DROP VIEW [view_product_search]
END
GO
-- view product add keyword
CREATE VIEW [view_product_search]
AS
    SELECT  product.*,
            product.name -- keysearch
            as keysearch 
        FROM product
GO
