package com.github.guang19.nettylearning.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;


/**
 * @author guang19
 * @date 2020/7/6
 * @description ByteBuf 测试
 */
public class ByteBufTest
{
    public static void main(String[] args)
    {
        //创建未被池化的直接内存的buffer
        ByteBuf byteBuf1 = Unpooled.directBuffer();
        ByteBuf byteBuf2 = Unpooled.directBuffer();
        byteBuf1.writeBytes("i love you , java".getBytes(StandardCharsets.UTF_8));
        byteBuf2.writeBytes("i love you , java".getBytes(StandardCharsets.UTF_8));


        System.out.println(byteBuf1.readableBytes());
        System.out.println(byteBuf2.readableBytes());

        System.out.println(ByteBufUtil.hexDump(byteBuf1));
        System.out.println(ByteBufUtil.hexDump(byteBuf2));

        //true
        System.out.println(ByteBufUtil.equals(byteBuf1,byteBuf2));

        System.out.println(byteBuf1.readerIndex()); //0

        byteBuf1.readByte();

        System.out.println(byteBuf1.readerIndex()); //1


    }
}
