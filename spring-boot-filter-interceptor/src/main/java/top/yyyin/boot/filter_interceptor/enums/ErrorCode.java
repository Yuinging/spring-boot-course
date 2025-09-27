package top.yyyin.boot.filter_interceptor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "请求参数不合法"),
    UNAUTHORIZED(401, "登录失效，请重新登录"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误,请稍后再试");



    private final int code;
    private final String msg;
}
