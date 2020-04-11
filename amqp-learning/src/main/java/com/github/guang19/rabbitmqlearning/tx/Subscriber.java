package com.github.guang19.rabbitmqlearning.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.tx.Publisher.TEST_TX_QUEUE;
import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO
 */
public class Subscriber
{
    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(TEST_TX_QUEUE,false,false,false,null);

        DeliverCallback deliverCallback = (consumerTag,delivery)->
        {
            System.out.println("received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
        };
        try
        {
            channel.txSelect();

            channel.basicConsume(TEST_TX_QUEUE,true,deliverCallback,delivery->{});

            channel.txCommit();
        }
        catch (Throwable e)
        {
            channel.txRollback();
            System.err.println("roll back");
        }
    }
}
