package com.github.guang19.rabbitmqlearning.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;
import static com.github.guang19.rabbitmqlearning.workqueue.Publisher.TEST_WORK_QUEUE;

/**
 * @Author yangguang
 * @Date 2020/1/24
 * @Description TODO        工作队列消费者2
 */
public class Consumer2
{
    public static void main(String[] args) throws Exception
    {
        //获取连接
        Connection connection = getConnection();

        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(TEST_WORK_QUEUE,true,false,false,null);

        //每次只接受一条消息
        channel.basicQos(1);

        //回调函数异步监听生产者生产的消息
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            try
            {
//                TimeUnit.SECONDS.sleep(1);
                System.out.println("work queue received message is : " + new String(delivery.getBody(), StandardCharsets.UTF_8));
                //获取到消息后,手动应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }catch (Throwable throwable)
            {
            }
        };

        //监听消费,关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(TEST_WORK_QUEUE,autoAck,deliverCallback,consumerTag->{});
    }
}
