package top.yyyin.boot.exception.service;

import org.springframework.stereotype.Service;
import top.yyyin.boot.exception.enums.ErrorCode;
import top.yyyin.boot.exception.exception.ServerException;

@Service
public class TestService {
    public void method() {
        throw new ServerException("余额不足");

    }

    public void method2() {
        throw new ServerException(ErrorCode.FORBIDDEN);
    }
}