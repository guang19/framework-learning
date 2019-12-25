package com.github.yangguang19.context;

import com.github.yangguang19.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : TODO
 * @Author :    yangguang
 * @Date :      2019/12/24
 */
@Configuration
public class Context {

    @Bean("person")
    public Person getPerson()
    {
        return new Person(1L,"杨小光",19);
    }
}
