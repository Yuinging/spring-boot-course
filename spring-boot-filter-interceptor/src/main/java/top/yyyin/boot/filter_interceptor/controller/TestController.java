package top.yyyin.boot.filter_interceptor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yyyin.boot.filter_interceptor.common.Result;



@RestController
@Slf4j
@RequestMapping("/api")
public class TestController {
    @GetMapping("/pay/{id}")
    public Result<String> pay(@PathVariable int id) {
        log.info("开始支付");
        //try {
        //    Thread.sleep(3000);
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        return Result.ok("支付成功，订单号：" + id);
    }

    @GetMapping("/test")
    public Result<String> test() {
        log.info("进入 TestController");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.ok("hello world");
    }
}
