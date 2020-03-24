package com.github.guang19.spring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p></p>
 */
@Component
public class BeanFactoryPostProcessorT implements BeanFactoryPostProcessor
{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        System.out.println("BeanPostProcessBeanFactory : " + beanFactory);
    }
}
