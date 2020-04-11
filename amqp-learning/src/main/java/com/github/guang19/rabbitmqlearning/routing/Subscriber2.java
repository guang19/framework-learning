package com.github.guang19.rabbitmqlearning.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.routing.Publisher.TEST_ROUTING_EXCHANGE;
import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO
 */
public class Subscriber2
{
    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TEST_ROUTING_EXCHANGE, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName,TEST_ROUTING_EXCHANGE,"B");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag,delivery)->
        {
            System.out.println("test exchange received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),true);
        };

        channel.basicConsume(queueName,false,deliverCallback,consumerTag->{});
    }
}
