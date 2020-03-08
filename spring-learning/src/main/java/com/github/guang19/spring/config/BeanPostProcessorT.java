package com.github.guang19.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p></p>
 */
@Component
public class BeanPostProcessorT implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        System.out.println("BeanPostProcessor postProcessBeforeInitialization : " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {
        System.out.println("BeanPostProcessor postProcessAfterInitialization: " + beanName);
        return bean;
    }
}
