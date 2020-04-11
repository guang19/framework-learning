package com.github.guang19.rabbitmqlearning.dlx_queue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.github.guang19.rabbitmqlearning.dlx_queue.Publisher.TEST_NORMAL_EXCHANGE;
import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/26
 * @Description TODO
 */
public class Consumer
{

    private static final String TEST_NORMAL_QUEUE = "test_normal_queue";

    private static final String TEST_DLX_EXCHANGE = "test_dlx_exchange";

    private static final String TEST_DLX_QUEUE = "test_dlx_queue";

    public static void main(String[] args) throws Exception
    {
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        Map<String,Object> argss = new HashMap<>();
        argss.put("x-dead-letter-exchange",TEST_DLX_EXCHANGE);
        channel.exchangeDeclare(TEST_NORMAL_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(TEST_NORMAL_QUEUE,false,false,false,argss);
        channel.queueBind(TEST_NORMAL_QUEUE,TEST_NORMAL_EXCHANGE,"normal.save");

        channel.exchangeDeclare(TEST_DLX_EXCHANGE,BuiltinExchangeType.TOPIC);
        channel.queueDeclare(TEST_DLX_QUEUE,false,false,false,null);
        channel.queueBind(TEST_DLX_QUEUE,TEST_DLX_EXCHANGE,"#");

        DeliverCallback deliverCallback = (consumerTag,delivery)->
        {
            System.out.println("normal queue received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };

        channel.basicConsume(TEST_NORMAL_QUEUE,false,deliverCallback,consumerTag->{});
    }
}
