package com.github.guang19.nettylearning.zerocopy;


import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author guang19
 * @date 2020/6/20
 * @description 零拷贝测试
 */
public class ZeroCopyTest
{
    public static void main(String[] args) throws Exception
    {
//        mmap();
//        sendFile();
    }

    /**
     *
     * sendfile测试
     *
     */
    public static void sendFile() throws IOException
    {
        FileChannel fileChannelIn = FileChannel.open(Path.of("/home/guang19/图片/壁纸/SC01C25.jpg"),StandardOpenOption.READ);
        FileChannel fileChannelTo = FileChannel.open(Path.of("/home/guang19/a.jpg"),StandardOpenOption.WRITE);
        fileChannelIn.transferTo(0,fileChannelIn.size(),fileChannelTo);

        fileChannelIn.close();
        fileChannelTo.close();
    }


    /**
     *
     * mmap测试
     *
     */
    public static void mmap() throws IOException
    {
        FileChannel fileChannelIn = FileChannel.open(Path.of("/home/guang19/图片/壁纸/SC01C25.jpg"), StandardOpenOption.READ);
        MappedByteBuffer mappedByteBuffer = fileChannelIn.map(FileChannel.MapMode.READ_ONLY,0,fileChannelIn.size());

        FileChannel fileChannelTo = FileChannel.open(Path.of("/home/guang19/a.jpg"),StandardOpenOption.WRITE);
        fileChannelTo.write(mappedByteBuffer);

        //如果是处理网络请求
//        SocketChannel socketChannel = SocketChannel.open();
//        socketChannel.write(mappedByteBuffer);

        fileChannelIn.close();
        fileChannelTo.close();
    }
}
