package com.github.guang19.rabbitmqlearning.spring_rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    public RabbitAdmin rabbitAdmin(@Qualifier("tRabbitTemplate")RabbitTemplate rabbitTemplate)
    {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean("tRabbitTemplate")
    public RabbitTemplate rabbitTemplate( @Qualifier("rabbitConnectionFactory") ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) ->
        {
            System.out.println("correlation : " + correlationData);
            if (!ack) {
                System.err.println(cause);
            }
        };
        RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) ->
        {
            System.out.println(message);
            System.out.println(replyCode);
            System.out.println(replyText);
            System.out.println(exchange);
            System.out.println(routingKey);
        };

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        return rabbitTemplate;
    }
}
