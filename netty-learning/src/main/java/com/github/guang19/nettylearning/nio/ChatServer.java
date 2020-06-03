package com.github.guang19.nettylearning.nio;

import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author guang19
 * @date 2020/6/2
 * @description 基于NIO的聊天室的服务端
 * @since 1.0.0
 */
public class ChatServer
{
    private int port = 10000;

    public ChatServer(){}

    public ChatServer(int port)
    {
        this.port = port;
    }

    public void start()
    {
        try(
                ServerSocketChannel serverSocketChannel = bindServerSocketChannel(port);
                Selector selector = registerSelectableChannel(null,serverSocketChannel,SelectionKey.OP_ACCEPT);
        )
        {

            System.out.println("chat server start at port : " + port);
            listen(selector);
            System.out.println("chat server stop...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //初始化server socket channel
    private ServerSocketChannel bindServerSocketChannel(int port) throws IOException
    {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",port));
        serverSocketChannel.configureBlocking(false);
        return serverSocketChannel;
    }

    //将channel注册到selector
    private Selector registerSelectableChannel(Selector selector,SelectableChannel selectableChannel,int nextOp) throws IOException
    {
        if (selector == null)
        {
            selector = Selector.open();
        }
        selectableChannel.configureBlocking(false);
        selectableChannel.register(selector,nextOp);
        return selector;
    }

    private void listen(Selector selector) throws IOException
    {
        while (true)
        {
            int isEvent = selector.select();

            if (isEvent <= 0)
            {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            SelectionKey selectionKey = null;
            while (selectionKeyIterator.hasNext())
            {
                selectionKey = selectionKeyIterator.next();

                //server socket channel 监听到客户端的socket连接了
                if (selectionKey.isAcceptable())
                {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();

                    SocketChannel clientSocketChannel = serverSocketChannel.accept();

                    //将当前客户端连接的消息广播到其他客户端去
                    String msg = "address 为: " + clientSocketChannel.getRemoteAddress()+ " 的用户加入到了当前聊天室.";
                    System.out.println(msg);
                    broadcastMsgToOthers(selector,clientSocketChannel,msg);

                    registerSelectableChannel(selector,clientSocketChannel,SelectionKey.OP_READ);
                }
                //client socket channel can read
                else if(selectionKey.isReadable())
                {
                    SocketChannel clientSocketChannel = (SocketChannel)selectionKey.channel();

                    //读取数据
                    this.readDataFromSocketChannel(selector,clientSocketChannel);

                    registerSelectableChannel(selector,clientSocketChannel,SelectionKey.OP_READ);
                }
                selectionKeyIterator.remove();
            }
        }
    }

    //从 socket channel 读取数据
    private void readDataFromSocketChannel(Selector selector, SocketChannel socketChannel) throws IOException
    {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuilder data = new StringBuilder("address 为: " + socketChannel.getRemoteAddress() + " 的用户说: ");
        int count = 0;
        try
        {
            while ((count = socketChannel.read(byteBuffer)) > 0)
            {
                byteBuffer.flip();
                byte[] bytes = new byte[count];
                byteBuffer.get(bytes);
                byteBuffer.clear();
                data.append(new String(bytes,StandardCharsets.UTF_8));
                ++count;
            }
            System.out.println(data);
            broadcastMsgToOthers(selector,socketChannel,data.toString());
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
        finally
        {
            byteBuffer.clear();
        }
    }

    //将当前 channel 的消息广播到其他客户端去
    private void broadcastMsgToOthers(Selector selector , SocketChannel socketChannel , String msg) throws IOException
    {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys)
        {
            if (key != null)
            {
                SelectableChannel clientChannel = key.channel();
                if (clientChannel instanceof SocketChannel && clientChannel != socketChannel)
                {
                    ((SocketChannel) clientChannel).write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                }
            }
        }
    }

    public static void main(String[] args)
    {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
