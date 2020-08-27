package com.github.guang19.nettylearning.netty_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author guang19
 * @date 2020/8/27
 * @description 聊天室服务器启动
 */
public class ChatServer
{
    public static void main(String[] args) throws Exception
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(
                            new ChannelInitializer<SocketChannel>()
                            {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception
                                {
                                    ch.pipeline().addLast(new StringDecoder());
                                    ch.pipeline().addLast(new StringEncoder());
                                    ch.pipeline().addLast(new ChatServerHandler());
                                }
                            });
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress("127.0.0.1",8080)).sync();
            channelFuture.channel().closeFuture().sync();
        }
        finally
        {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
