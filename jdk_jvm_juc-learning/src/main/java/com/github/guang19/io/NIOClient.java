package com.github.guang19.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yangguang
 * @date 2020/2/22
 * @description <p></p>
 */
public class NIOClient
{
    public static void main(String[] args)
    {
        NIOClient nioClient = new NIOClient();
        try(final Selector  selector = SelectorProvider.provider().openSelector();
            final  SocketChannel  clientSocketChannel = SocketChannel.open();)
        {
            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.register(selector, SelectionKey.OP_CONNECT);
            clientSocketChannel.connect(new InetSocketAddress("127.0.0.1",8080));
            while (true)
            {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if(!selectionKeys.isEmpty())
                {
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                    while (selectionKeyIterator.hasNext())
                    {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        selectionKeyIterator.remove();
                        nioClient.handleResponse(selector,selectionKey);
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    private void handleResponse(Selector selector , SelectionKey selectionKey) throws Exception
    {
        if(selectionKey.isConnectable())
        {
            SocketChannel clientSocket = (SocketChannel)selectionKey.channel();
            if(clientSocket.isConnectionPending())
            {
                clientSocket.finishConnect();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put("hello server".getBytes(StandardCharsets.UTF_8));
                byteBuffer.flip();
                clientSocket.write(byteBuffer);

                clientSocket.configureBlocking(false);
                clientSocket.register(selector,SelectionKey.OP_READ);
                byteBuffer.clear();
            }
        }
        else if(selectionKey.isReadable())
        {
            SocketChannel clientSocket = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            if(clientSocket.read(byteBuffer) > 0)
            {
                byteBuffer.flip();
                System.out.println("server data : " + new String(byteBuffer.array(), StandardCharsets.UTF_8));
            }
            clientSocket.configureBlocking(false);
            clientSocket.register(selector,SelectionKey.OP_WRITE);

            byteBuffer.clear();
        }
        else if(selectionKey.isWritable())
        {
            SocketChannel clientSocket = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            clientSocket.configureBlocking(false);
            clientSocket.register(selector,SelectionKey.OP_READ);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine())
            {
                byteBuffer.put(scanner.nextLine().getBytes(StandardCharsets.UTF_8));
                byteBuffer.flip();
                clientSocket.write(byteBuffer);
                byteBuffer.clear();
            }
        }
    }
}
