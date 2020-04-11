package com.github.guang19.rabbitmqlearning.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.topic.Publisher.TEST_TOPIC_EXCHANGE;
import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO            主题模式消费者
 */
public class Subscriber2
{
    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TEST_TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName,TEST_TOPIC_EXCHANGE,"AA.#");

        DeliverCallback deliverCallback = (consumerTag,delivery)->
        {
            System.out.println("topic exchange queue received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };

        channel.basicConsume(queueName,false,deliverCallback,consumerTag->{});
    }
}
