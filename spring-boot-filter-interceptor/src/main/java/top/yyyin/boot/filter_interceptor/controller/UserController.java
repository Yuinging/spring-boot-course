package top.yyyin.boot.filter_interceptor.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.yyyin.boot.filter_interceptor.annotation.RequireRole;
import top.yyyin.boot.filter_interceptor.common.Result;
import top.yyyin.boot.filter_interceptor.dto.LoginRequest;
import top.yyyin.boot.filter_interceptor.dto.LoginResponse;
import top.yyyin.boot.filter_interceptor.entity.User;
import top.yyyin.boot.filter_interceptor.service.UserService;
import top.yyyin.boot.filter_interceptor.utils.JwtUtil;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("用户登录请求：{}", loginRequest.getUsername());
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return Result.error("用户名和密码不能为空");
        }

        User user = userService.authenticate(loginRequest);
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        log.info("用户 {} 登录成功", user);

        // 生成 JWT token
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        LoginResponse loginResponse = LoginResponse.builder().userId(user.getId()).username(user.getUsername()).role(user.getRole()).token(token).build();
        log.info("用户 {} 登录成功，生成token", user.getUsername());

        return Result.ok(loginResponse);
    }

    /**
     * 查看个人信息（admin、user 权限均可访问）
     */
    @RequestMapping(value = "/profile", method = {RequestMethod.GET, RequestMethod.POST})
    @RequireRole(value = {"ADMIN", "USER"})
    public Result<User> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("token");

        if (token == null) {
            return Result.error("未提供token");
        }

        // 从token中解析出用户名
        String username = jwtUtil.getUsernameFromToken(token);
        // 根据用户名找到用户信息
        User user = userService.getUserByUsername(username);
        if (user != null) {
            // 隐藏掉密码
            user.setPassword(null);
        }
        return Result.ok(user);
    }

    /**
     * 查看部门信息（admin 权限可访问，user 权限不可访问）
     */
    @RequestMapping(value = "/department", method = {RequestMethod.GET, RequestMethod.POST})
    @RequireRole(value = "ADMIN")
    public Result<String> getDepartmentInfo() {
        return Result.ok("部门信息");
    }
}
