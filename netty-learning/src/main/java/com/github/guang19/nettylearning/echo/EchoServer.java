package com.github.guang19.nettylearning.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author guang19
 * @date 2020/6/3
 * @description 基于netty的echo服务端
 * @since 1.0.0
 */
public class EchoServer
{

    private int port;

    public EchoServer(){}

    public EchoServer(int port)
    {
        this.port = port;
    }

    //启动服务器
    public void start() throws InterruptedException
    {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try
        {
            ServerBootstrap serverBootStrap = new ServerBootstrap();
            serverBootStrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress("127.0.0.1",port))
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootStrap.bind().sync();

            System.out.println("echo server start listen on " + channelFuture.channel().localAddress());

            channelFuture.channel().closeFuture().sync();
        }
        finally
        {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws Exception
    {
        new EchoServer(8085).start();
    }
}
