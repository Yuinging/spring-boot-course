package top.yyyin.boot.exception.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class BookCreateDTO implements Serializable {

    /** 书名 */
    @NotBlank(message = "书名不能为空")
    @Size(max = 200, message = "书名长度不能超过200")
    private String title;

    /** 作者 */
    @NotBlank(message = "作者不能为空")
    @Size(max = 128, message = "作者长度不能超过128")
    private String author;

    /** ISBN */
    @NotBlank(message = "ISBN不能为空")
    @Size(max = 32, message = "ISBN长度不能超过32")
    private String isbn;

    /** 图书分类 */
    @Size(max = 64, message = "图书分类长度不能超过64")
    private String category;

    /** 库存数量 */
    private Integer stock;
}
