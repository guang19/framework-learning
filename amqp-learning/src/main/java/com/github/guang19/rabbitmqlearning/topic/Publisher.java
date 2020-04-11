package com.github.guang19.rabbitmqlearning.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO            主题模式生产者
 */
public class Publisher
{

    public static final String TEST_TOPIC_EXCHANGE = "test_topic_exchange";

    private static final String TEST_TOPIC_MESSAGE = "test_topic_message";

    private static final String TEST_TOPIC_ROUTING_KEY = "AA.2";

    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TEST_TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        channel.basicPublish(TEST_TOPIC_EXCHANGE,TEST_TOPIC_ROUTING_KEY,null,TEST_TOPIC_MESSAGE.getBytes(StandardCharsets.UTF_8));

        channel.close();
        connection.close();
    }
}
