package com.fhyj.cms.config;


import com.fhyj.cms.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.concurrent.TimeUnit;


/**
 * @author qinc
 * @description
 * @date 2018/12/11
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Bean
    public LoginInterceptor getTokenInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * 授权拦截的路径 addPathPatterns：拦截的路径 excludePathPatterns：不拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/checkLogin", "/static/**");
        super.addInterceptors(registry);
    }

    /**
     * 修改springboot中默认的静态文件路径
     * addResourceHandler请求路径
     * addResourceLocations 在项目中的资源路径
     * setCacheControl 设置静态资源缓存时间
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/class").setViewName("class");
        registry.addViewController("/dic").setViewName("dic");
        registry.addViewController("/menu").setViewName("menu");
        registry.addViewController("/role").setViewName("role");
        registry.addViewController("/log").setViewName("log");
        registry.addViewController("/manager").setViewName("manager");
        registry.addViewController("/db/mould").setViewName("db/mould");
        registry.addViewController("/db/table").setViewName("db/table");
        registry.addViewController("/db/resource").setViewName("db/resource");
        super.addViewControllers(registry);
    }
}
