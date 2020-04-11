package com.github.guang19.rabbitmqlearning.async_confirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static com.github.guang19.rabbitmqlearning.util.ConnectionUtil.getConnection;

/**
 * @Author yangguang
 * @Date 2020/1/25
 * @Description TODO 生产者
 */
public class Publisher
{

    public static final String TEST_ASYNC_CONFIRM_QUEUE = "test_async_confirm_queue";

    private static final String TEST_MESSAGE = "test_message";

    public static void main(String[] args) throws Exception
    {
        try(Connection connection = getConnection();
            Channel channel = connection.createChannel();)
        {
            channel.queueDeclare(TEST_ASYNC_CONFIRM_QUEUE,false,false,false,null);

            channel.confirmSelect();

            //正常确认时的回调
            ConfirmCallback successConfirmCallback = (sequenceNum,multiple)->
            {
                System.out.println("success" + sequenceNum);
                if (multiple) {
                    System.out.println("success -- multiple" + sequenceNum);

                }
                else {
                    System.out.println("success -- single" + sequenceNum);

                }
            };
            //异常确认时的回调
            ConfirmCallback failConfirmCallback = (sequenceNum,multiple)->
            {
                System.out.println("fail" + sequenceNum);
                if (multiple) {
                    System.out.println("fail -- multiple");

                }
                else {
                    System.out.println("fail -- single");

                }
            };
            channel.addConfirmListener(successConfirmCallback, failConfirmCallback);

            for (int i = 0 ; i  < 10 ; ++i)
            {
                channel.basicPublish("",TEST_ASYNC_CONFIRM_QUEUE,null,TEST_MESSAGE.concat(i+"").getBytes(StandardCharsets.UTF_8));
            }

            TimeUnit.SECONDS.sleep(3);
        }
    }
}
