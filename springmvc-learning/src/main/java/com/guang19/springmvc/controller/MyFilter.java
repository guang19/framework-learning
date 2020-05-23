package com.guang19.springmvc.controller;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author yangguang
 * @date 2020/3/9
 * @description <p></p>
 */
@Configuration
public class MyFilter implements Filter
{

    @Bean
    public FilterRegistrationBean<MyFilter> filterFilterRegistrationBean()
    {
        FilterRegistrationBean<MyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new MyFilter());
//        filterRegistrationBean.addUrlPatterns("/test");
        return filterRegistrationBean;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void destroy()
    {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        System.out.println("before method");
        chain.doFilter(request,response);
        System.out.println("after method");
    }
}
