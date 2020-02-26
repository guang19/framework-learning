package com.github.guang19.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author yangguang
 * @date 2020/2/26
 * @description <p></p>
 */
public class AIOClient
{
    public static void main(String[] args) throws Exception
    {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        client.connect(new InetSocketAddress("127.0.0.1", 8080)).get();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello server".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        Future<Integer> write = client.write(byteBuffer);
        if(write.get() != null)
        {
            System.out.println("client send data success");
            client.close();
        }
    }
}