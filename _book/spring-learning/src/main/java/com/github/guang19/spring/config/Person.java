package com.github.guang19.spring.config;

import com.github.guang19.spring.config.BeanPostProcessorT;
import lombok.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p>测试bean</p>
 */
@Getter
@ToString
@Component

public class Person implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,InitializingBean, DisposableBean
{
    public Person()
    {
        System.out.println("Person:create Person");
    }

    @Override
    public void destroy() throws Exception
    {
        System.out.println("DisposableBean: bean destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        System.out.println("InitializingBean : after properties set");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException
    {
        System.out.println("BeanFactoryAware:  " + beanFactory);
    }

    @Override
    public void setBeanName(String name)
    {
        System.out.println("BeanNameAware:  " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        System.out.println("ApplicationContextAware:  " + applicationContext);
    }

    @PostConstruct
    public void initMethod()
    {
        System.out.println("Person : init method");
    }

    @PreDestroy
    public void destroyMethod()
    {
        System.out.println("Person : destroy method");
    }
}
