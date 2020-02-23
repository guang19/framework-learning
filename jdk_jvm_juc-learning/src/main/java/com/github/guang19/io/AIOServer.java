package com.github.guang19.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
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
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress("127.0.0.1",8080));
        server.accept();
    }

}
