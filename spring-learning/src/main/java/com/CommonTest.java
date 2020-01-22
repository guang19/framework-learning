package com;

import com.entity.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description : TODO
 * @Author :    yangguang
 * @Date :      2019/12/24
 */
public class CommonTest {

    public static void main(String[] args)
    {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.github.yangguang19");
        Person person = annotationConfigApplicationContext.getBean("person",Person.class);
        System.out.println(person);
    }
}
