package top.yyyin.boot.exception.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("book")
@Accessors(chain = true)
public class BookAccount implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @Size(max = 200, message = "书名长度不能超过200")
    private String title;

    @Size(max = 128, message = "作者长度不能超过128")
    private String author;

    @Size(max = 32, message = "isbn长度不能超过32")
    private String isbn;

    @Size(max = 64, message = "图书分类长度不能超过64")
    private String category;

    private Integer stock;

    /**
     * 逻辑删除：1-已删除，0-未删除（默认未删除）
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}