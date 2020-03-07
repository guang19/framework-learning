package com.github.guang19.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p></p>
 */
public class ApplicationContextTest
{
    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.github.guang19.spring.config");
        System.out.println(context.getBean("person"));
    }
}
