package com.github.guang19.nettylearning.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;

/**
 * @author guang19
 * @date 2020/7/13
 * @description
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception
    {
        if (msg instanceof HttpRequest)
        {
            if (!((HttpRequest) msg).uri().equals("/favicon.ico"))
            {
                System.out.println("pipeline hashcode " + ctx.pipeline().hashCode());
                System.out.println("handler hashcode " + this.hashCode());
//              System.out.println("received client msg : "  + msg.toString());
                ByteBuf content = Unpooled.copiedBuffer("hello client , i am server", CharsetUtil.UTF_8);
                FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                httpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
                httpResponse.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                ctx.writeAndFlush(httpResponse);
            }
        }
//        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
    }
}
