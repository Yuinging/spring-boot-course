package top.yyyin.boot.redis.demo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.yyyin.boot.redis.entity.Address;
import top.yyyin.boot.redis.entity.Student;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TestRedis {
    @Resource
    private RedisTemplate<String, Student> redisTemplate;

    @Test
    public void test() {
//        redisTemplate.opsForValue().set("name", "张三", 10, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("age", "20", 10, TimeUnit.SECONDS);
    }

    @Test
    void testStudent() {
        Address address = Address.builder().province("江苏").city("南京").build();
        Student student = Student.builder().name("张三").age(20).address(address).build();
        redisTemplate.opsForValue().set("student", student, 120, TimeUnit.SECONDS);
    }
}