package top.yyyin.boot.redis.cache;

public class RedisKeys {
    /**
     * 验证码 Key
     */
    public static String getSmsKey(String phone) {
        return "sms:code:" + phone;
    }
}
