package com.github.guang19.rabbitmqlearning.routing;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO        路由模式下的 发布者
 */
public class Publisher
{

    public static final String TEST_ROUTING_EXCHANGE = "test_routing_exchange";

    private static final String TEST_ROUTING_MESSAGE = "test_routing_message";

    private static final String ROUTING_KEY = "A";

    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TEST_ROUTING_EXCHANGE, BuiltinExchangeType.DIRECT);

        channel.basicPublish(TEST_ROUTING_EXCHANGE,ROUTING_KEY,null,TEST_ROUTING_MESSAGE.getBytes(StandardCharsets.UTF_8));

        channel.close();
        connection.close();
    }
}
