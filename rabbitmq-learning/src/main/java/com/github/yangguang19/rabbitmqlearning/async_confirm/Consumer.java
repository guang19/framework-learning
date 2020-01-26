package com.github.yangguang19.rabbitmqlearning.async_confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import static com.github.yangguang19.rabbitmqlearning.async_confirm.Publisher.TEST_ASYNC_CONFIRM_QUEUE;
import static com.github.yangguang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO        消费者
 */
public class Consumer
{

    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(TEST_ASYNC_CONFIRM_QUEUE,false,false,false,null);

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag,delivery)->
        {
            System.out.println("consumer received message is : " + new String(delivery.getBody(),"UTF-8"));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };

        channel.basicConsume(TEST_ASYNC_CONFIRM_QUEUE,false,deliverCallback,consumerTag->{});
    }

}
