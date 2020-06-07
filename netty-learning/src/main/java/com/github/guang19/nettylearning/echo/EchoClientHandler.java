package com.github.guang19.nettylearning.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author guang19
 * @date 2020/6/7
 * @description 基于netty的一个简单的echo服务器客户端Handler
 * @since 1.0.0
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>
{
    /**
     * 客户端与服务器的连接建立后调用此方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello echo server", StandardCharsets.UTF_8));
    }

    /**
     * 从服务器受到数据后调用此方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception
    {
        System.out.println("echo client received message : " + byteBuf.toString(StandardCharsets.UTF_8));
    }

    /**
     * 捕获到异常时调用此方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
