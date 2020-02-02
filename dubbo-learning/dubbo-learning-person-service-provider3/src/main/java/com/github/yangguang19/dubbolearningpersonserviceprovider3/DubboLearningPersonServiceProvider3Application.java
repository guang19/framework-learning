package com.github.yangguang19.dubbolearningpersonserviceprovider3;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboLearningPersonServiceProvider3Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(DubboLearningPersonServiceProvider3Application.class, args);
    }

}
