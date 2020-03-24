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


        try(final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            final Selector selector = SelectorProvider.provider().openSelector())
        {
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",8080));
            serverSocketChannel.configureBlocking(false);//非阻塞
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("start listen...");
            while (true)
            {
                /**
                 *
                 * NIO 非阻塞的是 对事件的监听
                 *
                 */

                //此处就是bug存在
                //select 方法应该是阻塞的，直到有channel事件发生才会返回，继续执行下面的逻辑代码
                //但由于jdk和epoll函数的问题，导致有可能没有检测到事件就返回，所以必须判断
                //检测到的事件是否为0。不然他就会继续往下执行，导致 while(true)一直执行无效代码。
                //其实这里就算判断作用也不大，因为如果select函数不阻塞，那么也会执行while(true)到此处的代码。。。
                int select = selector.select();
                if(select == 0)
                {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if(!selectionKeys.isEmpty())
                {
                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                    while (selectionKeyIterator.hasNext())
                    {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        selectionKeyIterator.remove();

                        //但执行逻辑代码,如果selector上某个事件处理的时间过长，仍然是阻塞的
                        System.out.println("server starting block...");

                        nioServer.handleRequest(selector,selectionKey);

                        System.out.println("server ending block...");
                    }
                }
            }
        }
        catch (Exception e)
        {
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
            //selector线程在读事件上阻塞
            //逻辑代码阻塞执行任务的线程
            //************//
//            TimeUnit.SECONDS.sleep(5);

            SocketChannel clientSocketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            if (clientSocketChannel.read(byteBuffer) > 0)
            {
                byteBuffer.flip();
                System.out.println("current thread " + Thread.currentThread().getName() + " clint accept client data : " + new String(byteBuffer.array(), StandardCharsets.UTF_8));
            }

            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.register(selector,SelectionKey.OP_WRITE);
            byteBuffer.clear();
        }
        //server socket can send data to client
        else if(selectionKey.isWritable())
        {

            //selector线程在写事件上阻塞
            //逻辑代码阻塞执行任务的线程
            //************//
//            TimeUnit.SECONDS.sleep(5);

            SocketChannel clientSocketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(("current thread " + Thread.currentThread().getName() + " send data : hello client").getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();
            clientSocketChannel.write(byteBuffer);

            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.register(selector,SelectionKey.OP_READ);
            byteBuffer.clear();
        }
    }

}
