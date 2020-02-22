package com.github.guang19.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yangguang
 * @date 2020/2/22
 * @description <p></p>
 */
public class NIOServer
{

    public static void main(String[] args)
    {
        NIOServer nioServer = new NIOServer();
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try
        {
            selector = SelectorProvider.provider().openSelector();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",8080));
            serverSocketChannel.configureBlocking(false);//非阻塞
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("start listen...");
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
                        nioServer.handleRequest(selector,selectionKey);
                    }
                }
            }

        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                serverSocketChannel.close();
                selector.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    private void handleRequest(Selector selector , SelectionKey selectionKey) throws Exception
    {
        //server socket channel accept client request
        if (selectionKey.isAcceptable())
        {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
            SocketChannel clientSocket = serverSocketChannel.accept();
            clientSocket.configureBlocking(false);
            clientSocket.register(selector,SelectionKey.OP_READ);
        }
        //client socket send data to server
        else if(selectionKey.isReadable())
        {
            SocketChannel clientSocketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            if (clientSocketChannel.read(byteBuffer) > 0)
            {
                byteBuffer.flip();
                System.out.println("client data : " + new String(byteBuffer.array(), StandardCharsets.UTF_8));
            }
            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.register(selector,SelectionKey.OP_WRITE);
            byteBuffer.clear();
        }
        //server socket can send data to client
        else if(selectionKey.isWritable())
        {
            SocketChannel clientSocketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("hello client".getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();
            clientSocketChannel.write(byteBuffer);
            clientSocketChannel.register(selector,SelectionKey.OP_READ);
            byteBuffer.clear();
        }
    }

}
