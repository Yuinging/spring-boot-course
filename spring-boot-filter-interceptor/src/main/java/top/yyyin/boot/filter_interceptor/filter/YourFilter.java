package top.yyyin.boot.filter_interceptor.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class YourFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("YourFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("YourFilter 在请求接口之前执行的逻辑");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("YourFilter 在请求接口之后执行的逻辑");
    }

    @Override
    public void destroy() {
        log.info("YourFilter 销毁了");
    }
}
