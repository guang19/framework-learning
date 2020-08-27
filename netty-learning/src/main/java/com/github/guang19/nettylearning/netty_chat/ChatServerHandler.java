package com.github.guang19.nettylearning.netty_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author guang19
 * @date 2020/8/27
 * @description 聊天服务器服务端Handler
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String>
{
    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        Channel channel = ctx.channel();
        System.out.println("[SERVER RECEIVE] - " + msg);
        for (Channel channel0 : channelGroup)
        {
            if (channel != channel0)
            {
                channel0.writeAndFlush(msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[SERVER] - " + channel.remoteAddress() + " 已加入聊天室");
        channelGroup.add(channel);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Channel channel = ctx.channel();
        channelGroup.remove(channel);
        System.out.println("[SERVER] - " + channel.remoteAddress() + " 已离开聊天室");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        Channel channel = ctx.channel();
        channel.writeAndFlush("[SERVER] - 发生异常");
        channelGroup.remove(channel);
        ctx.close();
    }
}
