package top.yyyin.boot.redis.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import top.yyyin.boot.redis.exception.ServerException;
import top.yyyin.boot.redis.result.Result;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // ================ 自定义异常处理 ================
    @ExceptionHandler(ServerException.class)
    public Result<String> handleServerException(ServerException e) {
        // 这里可以按需记录日志
        log.warn("自定义异常: code={}, msg={}", e.getCode(), e.getMsg());
        return Result.error(e.getCode(), e.getMsg());
    }

    // ================ 参数校验: @Valid @RequestBody 触发（JSON 请求体） ================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("请求参数不合法");
        return Result.error(400, msg);
    }

    // ================ 参数校验: 表单/路径绑定失败（非 @RequestBody 场景） ================
    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException ex) {
        ConfigurationProperty fe = ex.getProperty() ;
        String mag = (fe != null) ? fe.getName() + " " + fe.getName() : "请求参数不合法" ;
        return Result.error(400, mag);
    }
    // ================ 单个参数校验: @RequestParam/@PathVariable 上的约束（如 @Min） ================
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolation(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations().stream().findFirst().map(v -> v.getPropertyPath() + " " + v.getMessage()).orElse("请求参数不合法");
        return Result.error(400, msg);
    }
    // ========== 请求体反序列化/类型不匹配等常见 400 ==========
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public Result<String> handleHttpMessageNotReadable(Exception ex) {
        return Result.error(400, "请求参数不合法");
    }
    // ========== 兜底：未知异常 ==========
    @ExceptionHandler(Exception.class)
    public Result<String> handleUnknown(Exception ex) {
        log.error("未知异常：", ex);
        return Result.error(500, "服务器异常，请稍后再试");
    }
}
