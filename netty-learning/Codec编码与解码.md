# 什么是编码器和解码器?
从网络传输的角度来讲，数组总是以字节的格式在网络之中进行传输的。
每当源主机发送数据到目标主机时，数据会从本地格式被转换成字节进行传输，这种转换被称为编码，编码的逻辑由
编码器处理。
每当目标主机接受来自源主机的数据时，数据会从字节转换为我们需要的格式，这种转换被称为解码，解码的逻辑由
解码器处理。

在Netty中，编码解码器实际上是ChannelOutboundHandler和ChannelInboundHandler的实现，
因为编码和解码都属于对数据的处理，由此看来，编码解码器被设计为ChannelHandler也就无可厚非。


## 解码器
在Netty中，解码器是ChannelInboundHandler的实现，即处理入站数据。
解码器主要分为两种：

- 将字节解码为Message消息 ，即 ByteToMessageDecoder和ReplayingDecoder。
- 将一种消息解码为另一种消息，即 MessageToMessageDecoder。


### ByteToMessageDecoder
ByteToMessageDecoder是将字节解码为消息的基类，如果我们想自定义解码器，就需要继承这个类并实现decode方法。
decode方法是自定解码器必须实现的方法，它被调用时会传入一个包含了数据的ByteBuf和一个用来添加解码消息的List。
对decode方法的调用会重复进行，直至确认没有新元素被添加到该List或ByteBuf没有可读字节为止。最后，如果List不为空，
那么它的内容会被传递给ChannelPipeline中的下一个ChannelInboundHandler。  

下面是ByteToMessageDecoder的编程模型：

````java
public class ToIntegerDecoder extends ByteToMessageDecoder //扩展ByteToMessageDecoder
{  

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        if (in.readableBytes() >= 4) {  //检查ByteBuf是否仍有4个字节可读
            out.add(in.readInt());  //从ByteBuf读取消息到List中
        }
    }
}
````

上面这种编程模式很简单，但是在读取ByteBuf之前验证其是否可读的步骤显得有些多余，所以可以使用ReplayingDecoder
来解决这个问题。


