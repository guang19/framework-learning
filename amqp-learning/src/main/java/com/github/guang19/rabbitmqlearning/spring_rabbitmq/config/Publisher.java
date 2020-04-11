package com.github.guang19.rabbitmqlearning.spring_rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @Author yangguang
 * @Date 2020/1/27
 * @Description TODO
 */
@Controller
public class Publisher
{

    @Autowired
    @Qualifier("tRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @ResponseBody
    @GetMapping("/test/produce")
    public String produce() throws Exception
    {
        rabbitTemplate.convertAndSend("exchange-1", "test.save", "test", message->
        {
            System.out.println(new String(message.getBody()));
            return message;
        },new CorrelationData(UUID.randomUUID().toString()));
        return "ok";
    }
}
