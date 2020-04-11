package com.github.guang19.rabbitmqlearning.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO    发布者
 */
public class Publisher
{

    public static final String TEST_CONFIRM_QUEUE = "test_confirm_queue";

    private static final String TEST_CONFIRM_MESSAGE = "test_confirm_message";

    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(TEST_CONFIRM_QUEUE,false,false,false,null);

        channel.confirmSelect();

        for(int i = 0 ; i < 10; ++i)
        {
            channel.basicPublish("",TEST_CONFIRM_QUEUE,null,TEST_CONFIRM_MESSAGE.getBytes(StandardCharsets.UTF_8));
        }

        channel.waitForConfirmsOrDie(5000);

        channel.close();
        connection.close();

    }
}
