package top.yyyin.boot.exception.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yyyin.boot.exception.entity.BookAccount;
import top.yyyin.boot.exception.mapper.BookAccountMapper;
import top.yyyin.boot.exception.service.BookAccountService;

@Service
@Transactional
public class BookAccountServiceImpl extends ServiceImpl<BookAccountMapper, BookAccount> implements BookAccountService {

    private  void processUserBeforeSave(BookAccount bookAccount) {
        //默认未删除
        if (bookAccount.getDeleted() == null){
            bookAccount.setDeleted(0);
        }
        //默认版本号
        if(bookAccount.getVersion() == null){
            bookAccount.setVersion(0);
        }
    }
}
