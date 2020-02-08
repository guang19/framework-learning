package com.github.guang19.dubbolearningpersonserviceprovider2;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboLearningPersonServiceProvider2Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(DubboLearningPersonServiceProvider2Application.class, args);
    }

}
