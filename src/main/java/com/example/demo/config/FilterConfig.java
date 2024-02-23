package com.example.demo.config;

import com.example.demo.config.filter.CustomFilter1;
import com.example.demo.config.filter.CustomFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Security FilterChain 아닌 필터 만드는 법.
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter1> customFilter1() {

        FilterRegistrationBean<CustomFilter1> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new CustomFilter1());

        filterFilterRegistrationBean.addUrlPatterns("/*"); // 모든 URL 필터
        filterFilterRegistrationBean.setOrder(0);  // 낮은 번호가 높은 순서
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<CustomFilter2> customFilter2() {

        FilterRegistrationBean<CustomFilter2> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new CustomFilter2());

        filterFilterRegistrationBean.addUrlPatterns("/*"); // 모든 URL 필터
        filterFilterRegistrationBean.setOrder(1);  // 낮은 번호가 높은 순서
        return filterFilterRegistrationBean;
    }
}
