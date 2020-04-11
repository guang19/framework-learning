package com.github.guang19.rabbitmqlearning;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitmqLearningApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RabbitmqLearningApplication.class, args);
    }
}
