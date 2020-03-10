package com.github.guang19.spring;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p></p>
 */
public class ApplicationContextTest
{
    public static void main(String[] args) throws Exception
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.github.guang19.spring.config");
//        System.out.println(context.getBean("person"));
        context.close();

//        BeanFactory beanFactory = new AnnotationConfigApplicationContext("com.github.guang19.spring.config");
//        System.out.println(beanFactory.getBean("person"));
    }
}
