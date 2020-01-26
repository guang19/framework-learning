package com.github.yangguang19.rabbitmqlearning.spring_rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @Author yangguang
 * @Date 2020/1/26
 * @Description TODO
 */
@SpringBootConfiguration
public class RabbitMQConfig
{

    @Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory)
    {
      RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
      rabbitAdmin.setAutoStartup(true);
      return rabbitAdmin;
    }
}
