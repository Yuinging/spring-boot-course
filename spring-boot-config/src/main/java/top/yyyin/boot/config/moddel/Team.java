package top.yyyin.boot.config.moddel;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class Team {
    @Value("${team.leader}")
    @NotNull
    private String leader;

    @Value("${team.phone}")
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号格式不正确")
    private String phone;

    @Value("${team.age}")
    @Max(5)
    @Min(1)
    private Integer age;

    @Value("${team.createDate}")
    @Past(message = "创建时间必须早于当前时间")
    private LocalDate createDate;
}
