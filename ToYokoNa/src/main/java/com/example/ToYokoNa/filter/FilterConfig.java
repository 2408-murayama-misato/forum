package com.example.ToYokoNa.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ManageAccountFilter> manageAccountFilter() {
        FilterRegistrationBean<ManageAccountFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new ManageAccountFilter());
        //  管理者権限が必要なURL
        bean.addUrlPatterns("/userManage");
        bean.addUrlPatterns("/userEdit");
        bean.addUrlPatterns("/userCreate");
        return bean;
    }
}