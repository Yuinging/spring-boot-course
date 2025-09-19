package top.yyyin.boot.mp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yyyin.boot.mp.entity.UserAccount;

@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    // 继承 BaseMapper 获得基础 CRUD 能力
    // 可以在此添加自定义查询方法
}