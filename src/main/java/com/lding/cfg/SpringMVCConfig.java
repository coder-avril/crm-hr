package com.lding.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
    @Autowired
    private MyInterceptor interceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 添加视图映射
        registry.addViewController("/access").setViewName("admin/login");
        registry.addViewController("/admin/password").setViewName("admin/password");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.interceptor).addPathPatterns("/**");
    }
}
