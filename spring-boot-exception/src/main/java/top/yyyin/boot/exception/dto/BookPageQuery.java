package top.yyyin.boot.exception.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BookPageQuery {
    @Min(1)
    private Integer current = 1;

    @Min(1)
    private Integer size = 10;
}
