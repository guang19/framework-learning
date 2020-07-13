package com.github.guang19.nettylearning.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author guang19
 * @date 2020/7/13
 * @description 基于Netty的http服务器
 */
public class HttpServerBootstrap
{
    public static void main(String[] args)
    {
        startup();
    }

    private static void startup()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup,workerGroup)
                    .localAddress(new InetSocketAddress("127.0.0.1",8080))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerChannelHandlerInitializer());

            System.out.println("http server start...");
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }
        catch (Exception e){}
        finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
