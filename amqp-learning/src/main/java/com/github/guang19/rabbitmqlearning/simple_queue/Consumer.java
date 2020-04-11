package com.github.guang19.rabbitmqlearning.simple_queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.simple_queue.Publisher.TEST_QUEUE;
import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/24
 * @Description TODO        简单队列的消费者
 */
public class Consumer
{
    public static void main(String[] args) throws Exception
    {
        //获取连接
        Connection connection = getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(TEST_QUEUE,false,false,false,null);

        //通过回调函数异步接受生产者发布的消息
        DeliverCallback deliverCallback = (consumerTag,delivery) ->{
            System.out.println("received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
        };

        //监听消费
        channel.basicConsume(TEST_QUEUE,true,deliverCallback,consumerTag->{});
    }
}
