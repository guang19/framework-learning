package com.github.guang19.rabbitmqlearning.spring_rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author yangguang
 * @Date 2020/1/27
 * @Description TODO        监听消费
 */
@Component
public class Listener
{

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name="${spring.rabbitmq.listener.queue.name}",durable = "${spring.rabbitmq.listener.queue.durable}",
            exclusive = "${spring.rabbitmq.listener.queue.exclusive}",autoDelete = "${spring.rabbitmq.listener.queue.autoDelete}",
            ignoreDeclarationExceptions = "${spring.rabbitmq.listener.queue.ignoreDeclarationExceptions}"),
            exchange = @Exchange(name = "${spring.rabbitmq.listener.exchange.name}",type = "${spring.rabbitmq.listener.exchange.type}",
                    durable = "${spring.rabbitmq.listener.exchange.durable}",autoDelete = "${spring.rabbitmq.listener.exchange.autoDelete}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.exchange.ignoreDeclarationExceptions}"),key = "${spring.rabbitmq.listener.exchange.routing-key}"))
    @RabbitHandler
    public void onMessage1(Message message , Channel channel) throws Exception
    {
        System.out.println("-----------------1 ");
        System.out.println("message : " + message.getPayload());
        channel.basicAck(message.getHeaders().get(AmqpHeaders.DELIVERY_TAG,Long.class),false);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name="${spring.rabbitmq.listener.queue.name}",durable = "${spring.rabbitmq.listener.queue.durable}",
            exclusive = "${spring.rabbitmq.listener.queue.exclusive}",autoDelete = "${spring.rabbitmq.listener.queue.autoDelete}",
            ignoreDeclarationExceptions = "${spring.rabbitmq.listener.queue.ignoreDeclarationExceptions}"),
            exchange = @Exchange(name = "${spring.rabbitmq.listener.exchange.name}",type = "${spring.rabbitmq.listener.exchange.type}",
                    durable = "${spring.rabbitmq.listener.exchange.durable}",autoDelete = "${spring.rabbitmq.listener.exchange.autoDelete}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.exchange.ignoreDeclarationExceptions}"),key = "${spring.rabbitmq.listener.exchange.routing-key}"))
    @RabbitHandler
    public void onMessage2(Message message , Channel channel, @Headers Map<String,Object> headers) throws Exception
    {
        System.out.println("-----------------2 ");
        System.out.println("message : " + message.getPayload());
        channel.basicAck((long)headers.get(AmqpHeaders.DELIVERY_TAG),false);
    }
}
