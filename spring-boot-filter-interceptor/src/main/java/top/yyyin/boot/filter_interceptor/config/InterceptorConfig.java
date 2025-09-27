package top.yyyin.boot.filter_interceptor.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yyyin.boot.filter_interceptor.interceptor.MyInterceptor;
import top.yyyin.boot.filter_interceptor.interceptor.YourInterceptor;

@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final MyInterceptor myInterceptor;
    private final YourInterceptor yourInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/api/test");
        registry.addInterceptor(yourInterceptor).addPathPatterns("/api/test");
    }
}
