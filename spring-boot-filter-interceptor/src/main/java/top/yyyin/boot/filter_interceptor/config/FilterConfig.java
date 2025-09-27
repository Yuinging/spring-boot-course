package top.yyyin.boot.filter_interceptor.config;


import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.yyyin.boot.filter_interceptor.filter.RateLimitFilter;

@Configuration
@AllArgsConstructor
public class FilterConfig {
//    private final MyFilter myFilter;
//    private final YourFilter yourFilter;
    private final RateLimitFilter rateLimitFilter;

//    @Bean
//    public FilterRegistrationBean<MyFilter> myFilterRegistrationBean() {
//        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(myFilter);
//        registrationBean.addUrlPatterns("/api/test");
////        registrationBean.setOrder(2);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<YourFilter> youFilterRegistrationBean() {
//        FilterRegistrationBean<YourFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(yourFilter);
//        registrationBean.addUrlPatterns("/api/test");
////        registrationBean.setOrder(1);
//        return registrationBean;
//    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistrationBean() {
        FilterRegistrationBean<RateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(rateLimitFilter);
        registrationBean.addUrlPatterns("/api/pay/*");
        registrationBean.setOrder(3);
        return registrationBean;
    }
}
