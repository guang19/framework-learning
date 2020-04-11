package com.github.guang19.rabbitmqlearning.simple_queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;


/**
 * @Author yangguang
 * @Date 2020/1/24
 * @Description TODO        简单队列的发布者(生产者)
 */
public class Publisher
{

    public static final String TEST_QUEUE = "test_queue";

    private static final String TEST_MESSAGE = "test_message";

    public static void main(String[] args) throws Exception
    {
        //获取连接
        Connection connection = getConnection();
        //创建管道
        Channel channel = connection.createChannel();
        //声明要发布的队列
        channel.queueDeclare(TEST_QUEUE,false,false,false,null);
        //发布消息
        channel.basicPublish("",TEST_QUEUE,null,TEST_MESSAGE.getBytes(StandardCharsets.UTF_8));

        //关闭管道和连接
        channel.close();
        connection.close();
    }
}
