package com.github.guang19.nettylearning.netty_chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author guang19
 * @date 2020/8/27
 * @description
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String>
{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
    {
        System.out.println("[RECEIVE] - " + msg);
    }
}
