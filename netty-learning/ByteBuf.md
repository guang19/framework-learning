<!-- TOC -->
         
  * [ByteBuf--Netty的数据容器](#ByteBuf--Netty的数据容器)
     * [ByteBuf实现](#ByteBuf实现)
     * [ByteBuf使用模式](#ByteBuf使用模式)

<!--  TOC -->

# ByteBuf--Netty的数据容器

网络传输的基本单位是字节，在Java NIO中，JDK提供了Buffer接口，以及其相关的实现作为NIO操作
数据的容器，如ByteBuffer等等。 而Netty为了解决Buffer原生接口的复杂操作提供了ByteBuf，
ByteBuf是一个很好的经过优化过的数据容器，我们可以将字节数据添加到ByteBuf中或从ByteBuf中获取数据，
相比于原生Buffer，ByteBuf更加灵活和易用。

Netty的数据处理主要通过两个API提供：

1. abstract class ByteBuf
2. interface ByteBufHolder

使用ByteBuf API能够给我们带来良好的编码体验，如

- 在读和写这两种模式切换时，无需像ByteBuffer一样调用flip方法。

- 容量可以动态增长，如StringBuilder之于String。

- 支持方法的链式调用，如"a".append("b").append("c")这种形式。

...

ByteBuf还有很多好处，上面列举的只是一部分，其他优点就需要各位同学慢慢了解了。


### ByteBuf实现
ByteBuf维护了两个不同的索引：一个是用于读取的readerIndex ， 一个是用于写入的writerIndex。
当我们写入字节到ByteBuf后，writerIndex增加，开始读取字节后，readerIndex开始增加。读取字节直到
readerIndex和writerIndex到达同一位置（已经读取到末尾了），ByteBuf就变为不可读。
这就好比当我们访问数组时，超出了它的范围时，程序会抛出IndexOutOfBoundException。

当我们调用ByteBuf以read或write开头的方法时，将会增加这个ByteBuf的读索引或写索引，而诸如set或get
的方法则不会改变索引。

我们可以指定ByteBuf的最大容量，如果对ByteBuf的写入操作导致writerIndex超出了最大人容量，那么程序将会
抛出一个异常，ByteBuf的最大人容量是Integer.MAX_VALUE。 

ByteBuf大致的结构和状态：

![ByteBuf结构](../img/netty/ByteBuf结构.png)


### ByteBuf使用模式
ByteBuf有多种使用模式，我们可以根据需求构建不同使用模式的ByteBuf。

- 堆缓冲区(HeapByteBuf)： 最常用的ByteBuf模式是将数据存储在JVM的堆空间中，实际上是通过数组存储数据，
所以这种模式被称为支撑数组（Backing Array ）。所以这种模式被称为支撑数组可以在没有使用池化的情况下快速分配和释放，
适合用于有遗留数据需要处理的情况。

![堆缓冲区模式](../img/netty/堆缓冲区模式.png)

- 直接缓冲区(DirectByteBuf)： 在Java中，我们创建的对象大部分都是存储在堆区之中的，但这不是绝对的，在NIO的API中，
允许Buffer分配直接内存，即操作系统的内存，这样做的好处非常明显： 前面在传输章节介绍过的零拷贝技术的
特点之一就是规避了多次IO拷贝，而现在数据直接就在直接内存中，而不是在JVM应用进程中，这不仅减少了拷贝次数，
是否还意味着减少了用户态与内核态的上下文切换呢？
直接缓冲区的缺点也比较明显： 直接内存的分配和释放都较为昂贵，而且因为直接
缓冲区的数据不是在堆区的，所以我们在某些时候可能需要将直接缓冲区的数据先拷贝一个副本到堆区，
再对这个副本进行操作。 与支撑数组相比，直接缓冲区的工作可能更多，所以如果事先知道数据会作为
一个数组来被访问，那么我们应该使用堆内存。

![直接缓冲区模式](../img/netty/直接缓冲区模式.png)

- 复合缓冲区（CompositeByteBuf）： CompositeByteBuf为多个ByteBuf提供了一个聚合视图，
我们可以根据需要，向CompositeByteBuf中添加或删除ByteBuf实例，所以CompositeByteBuf中可能
同时包含直接缓冲区模式和堆缓冲区模式的ByteBuf。对于CompositeByteBuf的hasArray方法，
**如果CompositeByteBuf中只有一个ByteBuf实例，那么CompositeByteBuf的hasArray方法
将直接返回这唯一一个ByteBuf的hasArray方法的结果，否则返回false。**
除此之外，CompositeByteBuf还提供了许多附加的功能，可以查看Netty的文档学习。

![复合缓冲区模式](../img/netty/复合缓冲区模式.png)


### 字节级别的操作
除了普通的读写操作，ByteBuf还提供了修改数据的方法。


#### 索引访问
如数组的索引访问一样，ByteBuf的索引访问也是从零开始的，第一个字节的索引是0,最后一个字节的索引总是
capacity - 1：

![随机访问索引](../img/netty/随机访问索引.png)

注意：使用getByte方式访问，既不会改变readerIndex，也不会改变writerIndex。

JDK的ByteBuffer只有一个索引position，所以当ByteBuffer在读和写模式之间切换时，需要使用flip方法。
而ByteBuf同时具有读索引和写索引，则无需切换模式，在ByteBuf内部，其索引满足：

````text
0 <= readerIndex <= writerIndex <= capacity
````

这样的规律，当使用readerIndex读取字节，或使用writerIndex写入字节时，ByteBuf内部的分段大致如下：

![ByteBuf内部分段](../img/netty/ByteBuf内部分段.png)

上图介绍了在ByteBuf内部大致有3个分段，接下来我们就详细的介绍下这三个分段。


#### 可丢弃字节
上图中，当readerIndex读取一部分字节后，之前读过的字节就属于已读字节，可以被丢弃了，通过调用
ByteBuf的discardReadBytes方法我们可以丢弃这个分段，丢弃这个分段实际上是删除这个分段的已读字节，
然后回收这部分空间：

![DiscardReadBytes回收后ByteBuf内部分段](../img/netty/DiscardReadBytes回收后ByteBuf内部分段.png)


#### 可读字节
ByteBuf的可读字节分段存储了尚未读取的字节，我们可以使用readBytes等方法来读取这部分数据，如果我们读取
的范围超过了可读字节分段，那么ByteBuf会抛出IndexOutOfBoundsException异常，所以在读取数据之前，我们
需要使用isReadable方法判断是否仍然有可读字节分段。


#### 可写字节
可写字节分段即没有被写入数据的区域，我们可以使用writeBytes等方法向可写字节分段写入数据，如果我们写入
的字节超过了ByteBuf的容量，那么ByteBuf也会抛出IndexOutOfBoundsException异常。


#### 索引管理
我们可以通过markReaderIndex，markWriterIndex方法来标记当前readerIndex和writerIndex的位置，
然后使用resetReaderIndex，resetWriterIndex方法来将readerIndex和writerIndex重置为之前标记过的
位置。

我们还可以使用clear方法来将readerIndex和writerIndex重置为0，但是clear方法并不会清空ByteBuf的
内容，下面clear方法的实现：

![ByteBuf的clear方法](../img/netty/ByteBuf的clear方法.png)

其过程是这样的：

![ByteBuf的clear方法调用前](../img/netty/ByteBuf的clear方法调用前.png)

![ByteBuf的clear方法调用后](../img/netty/ByteBuf的clear方法调用后.png)

由于调用clear后，数据并没有被清空，但整个ByteBuf仍然是可写的，这比discardReadBytes轻量的多，
DiscardReadBytes还要回收已读字节空间。
