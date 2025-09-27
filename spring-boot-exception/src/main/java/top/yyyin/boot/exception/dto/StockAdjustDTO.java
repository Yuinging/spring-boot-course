package top.yyyin.boot.exception.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class StockAdjustDTO implements Serializable {

    @NotNull(message = "调整数量不能为空")
    @Min(value = -9999, message = "单次出库不能超过 9999")
    @Min(value = 1, message = "单次入库不能少于 1")
    private Integer quantity;
}