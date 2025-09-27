package top.yyyin.boot.exception.service;

import org.springframework.stereotype.Service;
import top.yyyin.boot.exception.enums.ErrorCode;
import top.yyyin.boot.exception.exception.ServerException;

@Service
public class ExceptionService {
    public void unAuthorizedError() {
        // 未登录
        throw new ServerException(ErrorCode.UNAUTHORIZED);
    }

    public void systemError() {
        // 系统异常
        throw new ServerException("系统异常");
    }
}
