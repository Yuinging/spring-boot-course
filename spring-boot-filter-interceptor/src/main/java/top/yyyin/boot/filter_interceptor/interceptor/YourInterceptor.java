package top.yyyin.boot.filter_interceptor.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
public class YourInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("YourInterceptor preHandle: {}", requestURI);
        LocalDateTime beginTime = LocalDateTime.now();
        request.setAttribute("beginTime", beginTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LocalDateTime beginTime = (LocalDateTime) request.getAttribute("beginTime");
        LocalDateTime endTime = LocalDateTime.now();

        // 计算请求执行时间
        Duration duration = Duration.between(beginTime, endTime);
        long milliseconds = duration.toMillis();

        String requestURI = request.getRequestURI();
        log.info("YourInterceptor 执行结束: {}, 接口耗时: {}ms", requestURI, milliseconds);
    }
}