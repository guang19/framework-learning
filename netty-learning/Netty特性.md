# Netty特性

## 丰富的缓冲实现：ByteBuf容器
Netty使用自建的Buffer API实现ByteBuf，而不是使用JDK NIO的ByteBuffer来表示一个连续的字节序列。与JDK NIO的
ByteBuffer相比，Netty的ByteBuf有更加明显的优势，这些优势可以弥补Java原生ByteBuffer的底层缺点，并提供
更加方便的编程模型：

- 正常情况下，ByteBuf比ByteBuffer的性能更好；

- 实现了ReferenceCounted引用计数接口，优化了内存的使用；

- 容量可以动态增长，如StringBuilder之于String；

- 在读和写这两种模式切换时，无需像ByteBuffer一样调用flip方法，更易于操作；

...