package top.yyyin.boot.filter_interceptor.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("MyFilter 在请求接口之前执行的逻辑");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("MyFilter 在请求接口之后执行的逻辑");
    }

    @Override
    public void destroy() {
        log.info("MyFilter 销毁了");
    }
}
