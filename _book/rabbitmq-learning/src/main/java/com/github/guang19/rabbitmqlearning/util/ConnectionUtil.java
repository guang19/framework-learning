package com.github.guang19.rabbitmqlearning.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author yangguang
 * @Date 2020/1/24
 * @Description TODO        获取连接的工具类
 */
public class ConnectionUtil
{
    public static Connection getConnection() throws Exception
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/virtual_host1");
        connectionFactory.setUsername("YangGuang");
        connectionFactory.setPassword("YangGuang20000721!");
        return connectionFactory.newConnection();
    }
}
