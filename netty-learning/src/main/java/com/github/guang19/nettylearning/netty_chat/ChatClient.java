package com.github.guang19.nettylearning.netty_chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * @author guang19
 * @date 2020/8/27
 * @description 聊天室服务器启动
 */
public class ChatClient
{
    public static void main(String[] args) throws Exception
    {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(
                            new ChannelInitializer<SocketChannel>()
                            {
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception
                                {
                                    ch.pipeline().addLast(new StringDecoder());
                                    ch.pipeline().addLast(new StringEncoder());
                                    ch.pipeline().addLast(new ChatClientHandler());
                                }
                            });
            Channel channel = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true)
            {
                System.out.print("请输入： ");
                channel.writeAndFlush(reader.readLine());
            }
        }
        finally
        {
            workerGroup.shutdownGracefully();
        }
    }
}
