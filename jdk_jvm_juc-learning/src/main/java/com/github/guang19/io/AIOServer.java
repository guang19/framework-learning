package com.github.guang19.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.*;

/**
 * @author yangguang
 * @date 2020/2/23
 * @description <p></p>
 */
public class AIOServer
{

    public static void main(String[] args) throws Exception
    {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().
                bind(new InetSocketAddress("127.0.0.1",8080));
        while (true)
        {
            //阻塞至有客户端连接
            AsynchronousSocketChannel client  = server.accept().get();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.clear();
            while (true)
            {
                //设定5秒钟,如果还没有读取完成客户端的数据,那么直接break
                Integer result = client.read(byteBuffer).get(5L, TimeUnit.SECONDS);
                //数据读取完成
                if(result == -1)
                {
                    byteBuffer.flip();
                    System.out.println("client data : " + StandardCharsets.UTF_8.decode(byteBuffer));
                    byteBuffer.clear();
                    try
                    {
                        client.close();
                    }catch (Exception e){}
                    break;
                }
            }
        }
    }

}
