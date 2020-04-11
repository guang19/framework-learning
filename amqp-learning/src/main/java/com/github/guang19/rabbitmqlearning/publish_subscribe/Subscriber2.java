package com.github.guang19.rabbitmqlearning.publish_subscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.publish_subscribe.Publisher.TEST_EXCHANGE;
import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO            订阅者1(消费者1)
 */
public class Subscriber2
{
    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();


        //声明交换器
        channel.exchangeDeclare(TEST_EXCHANGE, BuiltinExchangeType.FANOUT);

        String queueName = channel.queueDeclare().getQueue();
        //把队列与交换器绑定
        channel.queueBind(queueName, TEST_EXCHANGE, "");

        //收到消息时的回调
        DeliverCallback deliverCallback = (consumerTag, delivery) ->
        {
            System.out.println("exchange queue received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
        };
        //监听消费
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

    }
}
