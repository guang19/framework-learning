package com.github.guang19.rabbitmqlearning.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO
 */
public class Publisher
{

    public static final String TEST_TX_QUEUE = "test_tx_queue";

    private static final String TEST_MESSAGE = "test_message";

    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TEST_TX_QUEUE,false,false,false,null);

        try
        {
            channel.txSelect();

//            int num = 10 / 0;

            channel.basicPublish("",TEST_TX_QUEUE,null,TEST_MESSAGE.getBytes(StandardCharsets.UTF_8));

            channel.txCommit();
        }
        catch (Throwable e)
        {
            channel.txRollback();
            System.err.println("roll back");
        }

        channel.close();
        connection.close();
    }
}
