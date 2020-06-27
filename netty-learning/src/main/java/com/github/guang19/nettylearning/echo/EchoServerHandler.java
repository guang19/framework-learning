package com.github.guang19.nettylearning.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author guang19
 * @date 2020/6/3
 * @description 基于netty的一个简单的echo服务器Handler
 * @since 1.0.0
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter
{
    /**
     * 每个信息入栈都会调用此方法处理
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println(byteBuf.getClass());
        System.out.println("echo server received message : " + byteBuf.toString(StandardCharsets.UTF_8));
        ChannelFuture future = ctx.writeAndFlush(byteBuf);
        future.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (future.isSuccess())
                {
                    System.out.println("echo server write message success");
                }
                else
                {
                    System.out.println("echo server write message failed");
                }
            }
        });
    }

    /**
     *   当前批处理的消息中最后一条消息时会被调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 读操作捕获到异常时会被调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
