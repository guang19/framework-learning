package com.github.guang19.rabbitmqlearning.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/24
 * @Description TODO    工作队列发布者
 */
public class Publisher
{
    public static final String TEST_WORK_QUEUE = "test_work_queue";

    public static final String TEST_WORK_MESSAGE = "test_work_message";

    public static void main(String[] args) throws Exception
    {
        //获取连接
        Connection connection = getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        //第二个参数指定 队列是否支持持久化
        channel.queueDeclare(TEST_WORK_QUEUE,true,false,false,null);

        //发送10条消息
        for (int i = 0 ; i < 10 ; ++i)
        {
            channel.basicPublish("",TEST_WORK_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN,TEST_WORK_MESSAGE.concat(i+"").getBytes(StandardCharsets.UTF_8));
        }

        //关闭管道和连接
        channel.close();
        connection.close();
    }
}
