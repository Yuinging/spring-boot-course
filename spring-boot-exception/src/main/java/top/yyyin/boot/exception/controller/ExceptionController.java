package top.yyyin.boot.exception.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import top.yyyin.boot.exception.common.Result;
import top.yyyin.boot.exception.entity.User;
import top.yyyin.boot.exception.service.ExceptionService;

@RestController
@AllArgsConstructor
public class ExceptionController {
    private final ExceptionService exceptionService;

    @GetMapping("/test/{id}")
    public Result<String> getInfo(@PathVariable int id) {
        if (id == 1) {
            exceptionService.unAuthorizedError();
        } else if (id == 2) {
            exceptionService.systemError();
        }
        return Result.ok("请求成功");
    }

    @PostMapping("/user")
    public Result<User> createUser(@Valid @RequestBody User user) {
        return Result.ok(user);
    }


}
