package com.github.guang19.rabbitmqlearning.dlx_queue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/26
 * @Description TODO 死信队列(DLX Dead Letter Exchange) 生产者
 *
 * 死信产生的几种情况:
 * 1.消息被拒绝
 * 2.消息过期
 * 3.队列达到最大长度,无法继续接受消息
 */
public class Publisher
{

    public static final String TEST_NORMAL_EXCHANGE = "test_normal_exchange";

    private static final String NORMAL_ROUTING_KEY = "normal.save";

    private static final String TEST_MESSAGE = "test_message";

    public static void main(String[] args) throws Exception
    {
        try (Connection connection = getConnection();
             Channel channel = connection.createChannel();)
        {
            channel.exchangeDeclare(TEST_NORMAL_EXCHANGE, BuiltinExchangeType.TOPIC);
            AMQP.BasicProperties basicProperties  = new AMQP.BasicProperties.Builder().deliveryMode(2).expiration("10000").build();
            channel.basicPublish(TEST_NORMAL_EXCHANGE,NORMAL_ROUTING_KEY,basicProperties,TEST_MESSAGE.getBytes(StandardCharsets.UTF_8));
        }
    }
}
