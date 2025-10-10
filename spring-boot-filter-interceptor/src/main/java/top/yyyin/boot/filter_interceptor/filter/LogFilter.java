package top.yyyin.boot.filter_interceptor.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
@Component
public class LogFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter 初始化...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURL().toString();
        String method = httpRequest.getMethod();
        String ip = httpRequest.getRemoteAddr();

        StringBuilder params = new StringBuilder();
        Enumeration<String> paramNames = httpRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            params.append(name).append("=").append(httpRequest.getParameter(name)).append("&");
        }

        String paramStr = params.length() > 0 ? params.substring(0, params.length() - 1) : "";

        log.info("请求开始 - URL: {}, 方法: {}, IP: {}, 参数: {}", url, method, ip, paramStr);

        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();

        log.info("请求结束 - URL: {}, 耗时: {}ms", url, endTime - startTime);
    }

    @Override
    public void destroy() {
        log.info("LogFilter 销毁...");
    }
}
