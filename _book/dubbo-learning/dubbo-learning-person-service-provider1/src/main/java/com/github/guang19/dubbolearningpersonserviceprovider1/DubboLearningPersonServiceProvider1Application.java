package com.github.guang19.dubbolearningpersonserviceprovider1;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@EnableDubbo
@SpringBootApplication
public class DubboLearningPersonServiceProvider1Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(DubboLearningPersonServiceProvider1Application.class, args);
    }

}
