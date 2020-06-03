package com.github.guang19.nettylearning.nio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author guang19
 * @date 2020/6/2
 * @description 基于NIO的聊天室的客户端
 * @since 1.0.0
 */
public class ChatClient
{

    //服务端的ip和port
    private final String serverAddr = "127.0.0.1";
    private final int serverPort = 10000;

    public ChatClient(){}

    public void start()
    {
        try(
                SocketChannel clientSocketChannel = bindSocketChannel();
                Selector selector = connectServer(clientSocketChannel);)
        {
            System.out.println("client start connect...");
            listen(selector,clientSocketChannel);
            System.out.println("client stop connect...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private SocketChannel bindSocketChannel() throws IOException
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        return socketChannel;
    }

    //初始化server socket channel
    private Selector connectServer(SocketChannel clientSocketChannel) throws IOException
    {
        Selector selector = registerSelectableChannel(null,clientSocketChannel, SelectionKey.OP_CONNECT);

        clientSocketChannel.connect(new InetSocketAddress(serverAddr,serverPort));

        return selector;
    }


    //将channel注册到selector
    private Selector registerSelectableChannel(Selector selector, SelectableChannel selectableChannel, int nextOp) throws IOException
    {
        if (selector == null)
        {
            selector = Selector.open();
        }
        selectableChannel.configureBlocking(false);
        selectableChannel.register(selector,nextOp);
        return selector;
    }

    public void listen(Selector selector,SocketChannel socketChannel) throws IOException
    {
        new Thread(new SocketChannelReader(selector)).start();

        sendMsgToSocketChannel(socketChannel);
    }

    private void sendMsgToSocketChannel(SocketChannel socketChannel) throws IOException
    {
        ByteBuffer byteBuffer = null;
        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入: ");

            while (scanner.hasNextLine())
            {
                System.out.print("请输入: ");
                String input = scanner.nextLine();
                socketChannel.write(byteBuffer = ByteBuffer.wrap(input.getBytes(StandardCharsets.UTF_8)));
                byteBuffer.clear();
            }
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
        finally
        {
            if (byteBuffer != null)
            {
                byteBuffer.clear();
                byteBuffer = null;
            }
        }
    }

    private class SocketChannelReader extends Thread
    {

        //单独开一个线程读取数据

        private Selector selector;

        public SocketChannelReader(Selector selector)
        {
            this.selector = selector;
        }

        @Override
        public void run()
        {
            try
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

                        //client socket channel connect server
                        if (selectionKey.isConnectable())
                        {
                            SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();

                            //完成连接过程
                            clientSocketChannel.finishConnect();

                            registerSelectableChannel(selector, clientSocketChannel, SelectionKey.OP_READ);
                        }
                        //server socket channel can write
                        else if (selectionKey.isReadable())
                        {
                            SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
                            readDataFromSocketChannel(selector, clientSocketChannel);
                            registerSelectableChannel(selector, clientSocketChannel, SelectionKey.OP_READ);
                        }
                        selectionKeyIterator.remove();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //从 socket channel 读取数据
        private void readDataFromSocketChannel(Selector selector, SocketChannel socketChannel) throws IOException
        {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            StringBuilder data = new StringBuilder();
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

    }

    public static void main(String[] args) throws Exception
    {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }
}
