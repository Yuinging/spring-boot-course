package top.yyyin.boot.redis.service.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yyyin.boot.redis.cache.RedisCache;
import top.yyyin.boot.redis.cache.RedisKeys;
import top.yyyin.boot.redis.dto.LoginRequest;
import top.yyyin.boot.redis.enums.ErrorCode;
import top.yyyin.boot.redis.exception.ServerException;
import top.yyyin.boot.redis.service.LoginService;
import top.yyyin.boot.redis.utils.CommonUtils;
import top.yyyin.boot.redis.vo.LoginResponse;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final RedisCache redisCache;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String phone = loginRequest.getPhone();
        String inputCode = loginRequest.getCode();

        // 1.校验手机号格式
        if (!CommonUtils.checkPhone(phone)) {
            throw new ServerException(ErrorCode.PHONE_ERROR);
        }

        // 2.校验验证码是否为空
        if (inputCode == null || inputCode.trim().isEmpty()) {
            throw new ServerException(ErrorCode.PHONE_CODE_EMPTY);
        }

        // 3. 从Redis中获取验证码
        String redisKey = RedisKeys.getSmsKey(phone);
        String redisCode = redisCache.get(redisKey).toString();

        // 4. 验证码不存在或已过期
        if (redisCode == null) {
            // 重新发送验证码或提示用户重新获取
            throw new ServerException(ErrorCode.PHONE_CODE_EXPIRED);
        }


        // 5.验证码不匹配
        if (!inputCode.equals(redisCode)) {
            throw new ServerException(ErrorCode.PHONE_CODE_ERROR);
        }

        // 6.验证成功，删除Redis中的验证码
        redisCache.delete(redisKey);

        // 7.生成token并返回登录信息
        String token = generateToken(phone);

        log.info("用户 {} 登录成功", phone);
        return new LoginResponse(token, phone);
    }

    private String generateToken(String phone) {
        return UUID.randomUUID().toString().replace("-", "") + phone.hashCode();
    }
}
