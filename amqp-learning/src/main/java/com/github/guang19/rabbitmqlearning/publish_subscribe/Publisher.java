package com.github.guang19.rabbitmqlearning.publish_subscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO        订阅者
 */
public class Publisher
{
    //交换器
    public static final String TEST_EXCHANGE = "test_exchange";

    //exchange message
    private static final String TEST_EXCHANGE_MESSAGE = "test_exchange_message";

    public static void main(String[] args) throws Exception
    {
        try(Connection connection = getConnection();
            Channel channel = connection.createChannel();)
        {
            channel.exchangeDeclare(TEST_EXCHANGE, BuiltinExchangeType.FANOUT);
            channel.basicPublish(TEST_EXCHANGE,"",null,TEST_EXCHANGE_MESSAGE.getBytes());
        }
    }
}
