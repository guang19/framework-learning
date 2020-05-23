package com.guang19.springmvc.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangguang
 * @date 2020/3/9
 * @description <p></p>
 */
//@Configuration
public class MyInterceptor extends WebMvcConfigurationSupport implements HandlerInterceptor
{
    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/test").addPathPatterns("/test2");
    }


    private long startTime = 0L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        startTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        System.out.println("request spend time : " + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        System.out.println("page reader completed");
    }
}
