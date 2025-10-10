package top.yyyin.boot.filter_interceptor.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yyyin.boot.filter_interceptor.interceptor.BusinessLogInterceptor;
import top.yyyin.boot.filter_interceptor.interceptor.ParamValidateInterceptor;
import top.yyyin.boot.filter_interceptor.interceptor.RoleAuthInterceptor;
import top.yyyin.boot.filter_interceptor.interceptor.TimeStatInterceptor;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final TimeStatInterceptor timeStatInterceptor;
    private final BusinessLogInterceptor businessLogInterceptor;
    private final RoleAuthInterceptor roleAuthInterceptor;
    private final ParamValidateInterceptor paramValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册耗时统计拦截器，此处省略
        // 注册业务日志拦截器，此处省略
        // 注册权限拦截器
        registry.addInterceptor(roleAuthInterceptor)
                .addPathPatterns("/api/**")
                // 注册登录接口不拦截
                .excludePathPatterns("/user/login", "/user/register")
                // 晚于 TimeStatInterceptor 执行
                .order(3);

        // 注册参数校验拦截器
        registry.addInterceptor(paramValidateInterceptor)
                .addPathPatterns("/api/*")
                // 优先于权限拦截器
                .order(0);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 拦截所有路径
        registry.addMapping("/**")
                // 允许的前端域名和端口
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}