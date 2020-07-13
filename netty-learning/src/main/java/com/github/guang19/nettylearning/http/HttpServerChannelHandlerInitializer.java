package com.github.guang19.nettylearning.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author guang19
 * @date 2020/7/13
 * @description
 */
public class HttpServerChannelHandlerInitializer extends ChannelInitializer<SocketChannel>
{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception
    {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("MyHttpCodec",new HttpServerCodec());
        pipeline.addLast("MyHttpServerHandler",new HttpServerHandler());
    }
}
