package com.github.guang19.rabbitmqlearning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqLearningApplicationTests
{

    @Autowired
    @Qualifier("tRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void test01() throws Exception
    {
        Queue queue = new org.springframework.amqp.core.Queue("test_queue",true,false,false,null);
        rabbitAdmin.declareQueue(queue);
        TopicExchange topicExchange = new TopicExchange("test_exchange",true,false);
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with("test_save"));
    }




    @Test
    public void test02() throws Exception
    {
        rabbitTemplate.convertAndSend("exchange-1", "test.save", "test", message->
        {
            System.out.println(new String(message.getBody()));
            return message;
        },new CorrelationData(UUID.randomUUID().toString()));

    }

    @Test
    public void test03() throws Exception
    {
//        System.out.println(rabbitTemplate);
        System.out.println(rabbitTemplate.receive("queue-1"));
//        rabbitTemplate.receive("test_queue");
    }


}
