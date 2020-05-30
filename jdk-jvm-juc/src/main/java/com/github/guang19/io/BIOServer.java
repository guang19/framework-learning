package com.github.guang19.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author yangguang
 * @date 2020/2/22
 * @description <p>BIO测试服务端</p>
 */
public class BIOServer
{
    public static void main(String[] args)
    {
        BIOServer bioServer = new BIOServer();
        ServerSocket server = null;
        InputStream inputStream = null;
        try
        {
            server = new ServerSocket();
            server.bind(new InetSocketAddress("127.0.0.1",8080));
            System.out.println("start listen...");
            while (true)
            {
                Socket clientSocket = server.accept();
                //使用多线程虽然可以应对多客户端的同时请求,但是如果并发数过高，服务器创建的线程过多
                //那么就可能造成操作系统线程资源耗尽。。。
                new Thread(()->
                {
                    //阻塞处理请求
                    bioServer.doAccept(clientSocket);
                }).start();
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                inputStream.close();
                server.close();
            }
            catch (Exception e)
            {
            }
        }
    }



    //处理客户端强求
    private void doAccept(Socket clientSocket)
    {
        int i = 0;
        byte[] bytes = new byte[1024];
        InputStream inputStream = null;
        try
        {
            inputStream = clientSocket.getInputStream();
            while ((i = inputStream.read(bytes)) != -1)
            {
                System.out.println(new String(bytes, StandardCharsets.UTF_8));
                System.out.println(Thread.currentThread().getName() + " accept request start sleep");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " accept request end sleep");
            }
        }
        catch (IOException | InterruptedException e)
        {
        }
        finally
        {
            try
            {
                inputStream.close();
                clientSocket.close();
            }
            catch (Exception e)
            {
            }
        }
    }
}
