package top.yyyin.boot.mp.mapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.yyyin.boot.mp.entity.UserAccount;

import java.util.List;

@SpringBootTest
@Slf4j
class UserAccountMapperTest {

    @Resource
    private UserAccountMapper userAccountMapper;

    @Test
    void testSelectAll() {
        List<UserAccount> userAccountList = userAccountMapper.selectList(null);
        log.info("查询所有用户结果：{}", userAccountList);
    }

    @Test
    void testSelectOne() {
        UserAccount userAccount = userAccountMapper.selectById(8);
        log.info("根据id查询用户结果：{}", userAccount);
    }




}