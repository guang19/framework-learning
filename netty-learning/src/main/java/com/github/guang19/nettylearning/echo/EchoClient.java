package com.github.guang19.nettylearning.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author guang19
 * @date 2020/6/7
 * @description 基于netty的echo客户端
 * @since 1.0.0
 */
public class EchoClient
{
    private int port;

    public EchoClient(){}

    public EchoClient(int port)
    {
        this.port = port;
    }

    public void start() throws InterruptedException
    {
        EventLoopGroup boss = new NioEventLoopGroup();

        try
        {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(boss)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1",port))
                    .handler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception
                        {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();

            future.channel().closeFuture().sync();
        }
        finally
        {
            boss.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception
    {
        new EchoClient(8085).start();
    }
}
