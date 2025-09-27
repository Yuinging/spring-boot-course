package top.yyyin.boot.redis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(400, "请求参数不合法"),
    UNAUTHORIZED(401, "登录失效,请重新登录"),
    FORBIDDEN(403, "无权限访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误,请稍后再试"),

    PHONE_ERROR(40001, "手机号格式错误"),
    PHONE_NOT_EXIST(40002, "手机号不存在"),
    PHONE_CODE_EMPTY(40003, "验证码不能为空"),
    PHONE_CODE_ERROR(40004, "验证码错误"),
    PHONE_CODE_EXPIRED(40005, "验证码不存在或已过期"),
    PHONE_CODE_USED(40006, "验证码已使用"),
    PHONE_CODE_SEND_TOO_MANY(40007, "验证码发送过于频繁"),
    PHONE_CODE_SEND_FAILED(40008, "验证码发送失败"),
    PHONE_CODE_SEND_LIMIT(40009, "验证码发送次数已达上限");

    private final int code;
    private final String msg;
}
