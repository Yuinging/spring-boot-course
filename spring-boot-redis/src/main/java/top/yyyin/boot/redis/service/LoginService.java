package top.yyyin.boot.redis.service;

import top.yyyin.boot.redis.dto.LoginRequest;
import top.yyyin.boot.redis.vo.LoginResponse;

public interface LoginService {
    /**
     * 登录
     *
     * @param request 登录请求参数
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request);
}