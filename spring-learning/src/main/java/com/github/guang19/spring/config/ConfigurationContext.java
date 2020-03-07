package com.github.guang19.spring.config;

import com.github.guang19.spring.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p></p>
 */
@Configuration
public class ConfigurationContext
{

    @Bean
    public Person person()
    {
        return new Person(1L,"yxg1",19);
    }

}
