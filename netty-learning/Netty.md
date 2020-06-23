<!-- TOC -->

   * [Netty](#netty)
      * [Netty是什么?](#netty是什么)
         * [Netty的特点](#netty的特点)
         * [Netty组件介绍](#netty组件介绍)
            * [Bootstrap/ServerBootstrap](#bootstrapserverbootstrap)
            * [Channel](#channel)
            * [EventLoop](#eventloop)
            * [ChannelFuture](#channelfuture)
            * [ChannelHandler](#channelhandler)
            * [ChannelPipeline](#channelpipeline)
            * [入站事件和出站事件的流向](#入站事件和出站事件的流向)
            * [进一步了解ChannelHandler](#进一步了解channelhandler)
            * [编码器和解码器](#编码器和解码器)
            * [SimpleChannelInboundHandler](#simplechannelinboundhandler)
         * [传输(Transport)](#传输transport)
            * [传输API](#传输api)
            * [Netty内置的传输](#netty内置的传输)
            * [零拷贝](#零拷贝)
            * [内存映射（Memory Mapped）](#内存映射memory-mapped)
            * [文件传输(SendFile)](#文件传输sendfile)

<!-- /TOC -->


# Netty

**本文章部分摘抄自 《Netty in Action》(Netty实战)，再根据本人实际学习体验总结而成，
所以本文内容可能不那么全面，但是我尽量挑选Netty中我认为比较重要的部分做讲解。**

学习Netty，相信大部分同学都会选择 《Netty in Action》 ， 这里我推荐它的一个Gitbook精髓版本的，
此版本的作者对《Netty in Action》做出了更为精简的概述，所以各位同学可酌情挑选阅读。


- [Netty in Action(精髓)](https://waylau.com/essential-netty-in-action/index.html)


在学习Netty之前，我们需要对 IO模型(网络IO模型)有一个大概的认知，可以参考我编写的：
[IO](https://guang19.github.io/framework-learning/gitbook_doc/jdk-jvm-juc/IO.html) 。

````text
如有错误之处，敬请指教。
````


## Netty是什么?

Netty是Red Hat开源的，一个利用Java的高级网络能力，隐藏其(Java API)背后的复杂性而提供一个易于使用的 NIO 客户端/服务端框架。
Netty提供了高性能和可扩展性，让你自由地专注于你真正感兴趣的东西。 Netty简化了网络程序的开发过程，使用它
我们可以快速简单地开发网络应用程序，比如客户端和服务端的通信协议，TCP和UDP的Socket开发。


### Netty的特点
Netty作为一款优秀的网络框架，自然有令人折服的特点：

- 设计：
  
  - 针对多种传输类型的同一接口。
  
  - 简单但更强大的线程模型。
  
  - 真正的无连接的数据报套接字支持。
  
  - 链接逻辑复用。
  
- 性能： Netty的高性能是它被广泛使用的一个重要的原因，我们可能都认为Java不太适合
编写游戏服务端程序，但Netty的到来无疑是消除了这种见解。

  - 较原生Java API有更好的吞吐量，较低的延时。
  
  - 资源消耗更少(共享池和重用)。
  
  - 减少内存拷贝。
  
- 健壮性： 原生NIO的客户端/服务端程序编写较为麻烦，如果某个地方处理的不好，可能会
  导致一些意料之外的异常，如内存溢出，死循环等等，而Netty则为我们简化了原生API
  的使用，这使得我们编写出来的程序不那么容易出错。
  
- 社区： Netty快速发展的一个重要的原因就是它的社区非常活跃，这也使得采用它的开发者越来越多。



### Netty组件介绍

Netty有 Bootstrap/ServerBootstrap，Channel，EventLoop，ChannelFuture，
ChannelHandler，ChannelPipeline，编码器和解码器等核心组件。 

**在学习Netty组件之前建议各位同学先编写一个Netty的Demo，你不必了解这个Demo的细节，
只需要让它在你的脑海中留下一个记忆，然后对照Demo来学习以下组件，会事半功倍。** 

[Demo](https://github.com/guang19/framework-learning/tree/dev/netty-learning/src/main/java/com/github/guang19/nettylearning/echo)



#### Bootstrap/ServerBootstrap
Bootstrap和ServerBootstrap是Netty应用程序的引导类，它提供了用于应用程序网络层的配置。
一般的Netty应用程序总是分为客户端和服务端，所以引导分为客户端引导Bootstrap和服务端引导ServerBootstrap，
ServerBootstrap作为服务端引导，它将服务端进程绑定到制定的端口，而Bootstrap则是将客户端连接到
指定的远程服务器。
Bootstrap和ServerBootstrap除了职责不同，它们所需的EventLoopGroup的数量也不同，
Bootstrap引导客户端只需要一个EventLoopGroup，而ServerBootstrap则需要两个EventLoopGroup
(2个EventLoopGroup可以是同一个实例)，至于原因嘛，后续再讲 * _ * 。
    
![Bootstrap引导类功能](../img/netty/Bootstrap引导类功能.png)


#### Channel
在我们使用某种语言，如c/c++,java,go等，进行网络编程的时候，我们通常会使用到Socket，
Socket是对底层操作系统网络IO操作(如read,write,bind,connect等)的封装，
因此我们必须去学习Socket才能完成网络编程，而Socket的操作其实是比较复杂的，想要使用好它有一定难度，
所以Netty提供了Channel(io.netty.Channel，而非java nio的Channel)，更加方便我们处理IO事件。


#### EventLoop
EventLoop用于服务端与客户端连接的生命周期中所发生的事件。 
EventLoop 与 EventLoopGroup，Channel的关系模型如下：

![EventLoop模型](../img/netty/EventLoop模型.png)

一个EventLoopGroup通常包含一个或多个EventLoop，一个EventLoop可以处理多个Channel的IO事件，
一个Channel也只会被注册到一个EventLoop上。在EventLoop的生命周期中，它只会和一个Thread线程绑定，这个
EventLoop处理的IO事件都将在与它绑定的Thread内被处理。


#### ChannelFuture
在Netty中，所有的IO操作都是异步执行的，所以一个操作会立刻返回，但是如何获取操作执行完的结果呢？
Netty就提供了ChannelFuture接口，它的addListener方法会向Channel注册ChannelFutureListener，
以便在某个操作完成时得到通知结果。


#### ChannelHandler
我们知道Netty是一个款基于事件驱动的网络框架，当特定事件触发时，我们能够按照自定义的逻辑去处理数据。
**ChannelHandler则正是用于处理入站和出站数据钩子**，它可以处理几乎所有类型的动作，所以ChannelHandler会是
我们开发者更为关注的一个接口。

ChannelHandler主要分为处理入站数据的 ChannelInboundHandler和出站数据的 ChannelOutboundHandler 接口。

![ChannelHandler接口层次图](../img/netty/ChannelHandler接口层次图.png)

Netty以适配器的形式提供了大量默认的 ChannelHandler实现，主要目的是为了简化程序开发的过程，我们只需要
重写我们关注的事件和方法就可以了。 通常我们会以继承的方式使用以下适配器和抽象:

- ChannelHandlerAdapter
- ChannelInboundHandlerAdapter
- ChannelDuplexHandler
- ChannelOutboundHandlerAdapter


#### ChannelPipeline
上面介绍了ChannelHandler的作用，它使我们更关注于特定事件的数据处理，但如何使我们自定义的
ChannelHandler能够在事件触发时被使用呢？ Netty提供了ChannelPipeline接口，它
提供了存放ChannelHandler链的容器，且ChannelPipeline定义了在这条ChannelHandler链上
管理入站和出站事件流的API。
当一个Channel被初始化时，会使用ChannelInitializer接口的initChannel方法在ChannelPipeline中
添加一组自定义的ChannelHandler。


#### 入站事件和出站事件的流向

从服务端角度来看，如果一个事件的运动方向是从客户端到服务端，那么这个事件是入站的，如果事件运动的方向
是从服务端到客户端，那么这个事件是出站的。

![Netty出站入站](../img/netty/Netty出站入站.png)

上图是Netty事件入站和出站的大致流向，入站和出站的ChannelHandler可以被安装到一个ChannelPipeline中，
**如果一个消息或其他的入站事件被[读取]，那么它会从ChannelPipeline的头部开始流动，并传递给第一个ChannelInboundHandler
，这个ChannelHandler的行为取决于它的具体功能，不一定会修改消息。 在经历过第一个ChannelInboundHandler之后，
消息会被传递给这条ChannelHandler链的下一个ChannelHandler，最终消息会到达ChannelPipeline尾端，消息的读取也就结束了。**

**数据的出站(消息被[写出])流程与入站是相似的，在出站过程中，消息从ChannelOutboundHandler链的尾端开始流动，
直到到达它的头部为止，在这之后，消息会到达网络传输层进行后续传输。**


#### 进一步了解ChannelHandler
鉴于入站操作和出站操作是不同的，可能有同学会疑惑：为什么入站ChannelHandler和出站ChannelHandler的数据
不会窜流呢(为什么入站的数据不会到出站ChannelHandler链中)？ 因为Netty可以区分ChannelInboundHandler和
ChannelOutboundHandler的实现，并确保**数据只在两个相同类型的ChannelHandler直接传递，即数据要么在
ChannelInboundHandler链之间流动，要么在ChannelOutboundHandler链之间流动。**

**当ChannelHandler被添加到ChannelPipeline中后，它会被分配一个ChannelHandlerContext，
它代表了ChannelHandler和ChannelPipeline之间的绑定。 我们可以使用ChannelHandlerContext
获取底层的Channel，但它最主要的作用还是用于写出数据。**


#### 编码器和解码器
当我们通过Netty发送(出站)或接收(入站)一个消息时，就会发生一次数据的转换，因为数据在网络中总是通过字节传输的，
所以当数据入站时，Netty会解码数据，即把数据从字节转为为另一种格式(通常是一个Java对象)，
当数据出站时，Netty会编码数据，即把数据从它当前格式转为为字节。

Netty为编码器和解码器提供了不同类型的抽象，这些编码器和解码器其实都是ChannelHandler的实现，
它们的名称通常是ByteToMessageDecoder和MessageToByteEncoder。

对于入站数据来说，解码其实是解码器通过重写ChannelHandler的read事件，然后调用它们自己的
decode方法完成的。
对于出站数据来说，编码则是编码器通过重写ChannelHandler的write事件，然后调用它们自己的
encode方法完成的。

**为什么编码器和解码器被设计为ChannelHandler的实现呢?**

我觉得这很符合Netty的设计，上面已经介绍过Netty是一个事件驱动的框架，其事件由特定的ChannelHandler
完成，我们从用户的角度看，编码和解码其实是属于应用逻辑的，按照应用逻辑实现自定义的编码器和解码器就是
理所应当的。


#### SimpleChannelInboundHandler
在我们编写Netty应用程序时，会使用某个ChannelHandler来接受入站消息，非常简单的一种方式
是扩展SimpleChannelInboundHandler< T >，T是我们需要处理消息的类型。 继承SimpleChannelInboundHandler
后，我们只需要重写其中一个或多个方法就可以完成我们的逻辑。


---


### 传输(Transport)
在网络中传递的数据总是具有相同的类型：字节。 这些字节流动的细节取决于网络传输，它是一个帮我们抽象
底层数据传输机制的概念，我们不需要关心字节流动的细节，只需要确保字节被可靠的接收和发送。

当我们使用Java网络编程时，可能会接触到多种不同的网络IO模型，如NIO，BIO(OIO: Old IO)，AIO等，我们可能因为
使用这些不同的API而遇到问题。 
Netty则为这些不同的IO模型实现了一个通用的API，我们使用这个通用的API比直接使用JDK提供的API要
简单的多，且避免了由于使用不同API而带来的问题，大大提高了代码的可读性。
在传输这一部分，我们将主要学习这个通用的API，以及它与JDK之间的对比。


#### 传输API
传输API的核心是Channel(io.netty.Channel，而非java nio的Channel)接口，它被用于所有的IO操作。

Channel结构层次：

![Channel接口层次](../img/netty/Channel接口层次.png)

每个Channel都会被分配一个ChannelPipeline和ChannelConfig，
ChannelConfig包含了该Channel的所有配置，并允许在运行期间更新它们。

ChannelPipeline在上面已经介绍过了，它存储了所有用于处理出站和入站数据的ChannelHandler，
我们可以在运行时根据自己的需求添加或删除ChannelPipeline中的ChannelHandler。

此外，Channel还有以下方法值得留意：

| 方法名 | 描述 |
| :---: | :---: |
| eventLoop | 返回当前Channel注册到的EventLoop |
| pipeline  | 返回分配给Channel的ChannelPipeline |
| isActive  | 判断当前Channel是活动的，如果是则返回true。 此处活动的意义依赖于底层的传输，如果底层传输是TCP Socket，那么客户端与服务端保持连接便是活动的；如果底层传输是UDP Datagram，那么Datagram传输被打开就是活动的。 |
| localAddress | 返回本地SocketAddress |
| remoteAddress | 返回远程的SocketAddress |
| write     | 将数据写入远程主机，数据将会通过ChannelPipeline传输 |
| flush     | 将之前写入的数据刷新到底层传输 |
| writeFlush | 等同于调用 write 写入数据后再调用 flush 刷新数据 |


#### Netty内置的传输
Netty内置了一些开箱即用的传输，我们上面介绍了传输的核心API是Channel，那么这些已经封装好的
传输也是基于Channel的。

Netty内置Channel接口层次：

![Netty内置Channel接口层次](../img/netty/Netty内置Channel接口层次.png)

| 名称    |  包   | 描述 |
| :---:  | :---: | :---| 
| NIO    | io.netty.channel.socket.nio | NIO Channel基于java.nio.channels，其io模型为IO多路复用 |
| Epoll  | io.netty.channel.epoll      | Epoll Channel基于操作系统的epoll函数，其io模型为IO多路复用，不过Epoll模型只支持在Linux上的多种特性，比NIO性能更好 |
| KQueue | io.netty.channel.kqueue     | KQueue 与 Epoll 相似，它主要被用于 FreeBSD 系统上，如Mac等 |
| OIO(Old Io) | io.netty.channel.socket.oio | OIO Channel基于java.net包，其io模型是阻塞的，且此传输被Netty标记为deprecated，故不推荐使用，最好使用NIO / EPOLL / KQUEUE 等传输 |
| Local       | io.netty.channel.local      | Local Channel 可以在VM虚拟机内部进行本地通信 |
| Embedded    | io.netty.channel.embedded   | Embedded Channel允许在没有真正的网络传输中使用ChannelHandler，可以非常有用的测试ChannelHandler |


#### 零拷贝
零拷贝(Zero-Copy)是一种目前只有在使用NIO和Epoll传输时才可使用的特性。
在我之前写过的IO模型中，所有的IO的数据都是从内核复制到用户应用进程，再由用户应用进程处理。
而零拷贝则可以快速地将数据从源文件移动到目标文件，无需经过用户空间。

在学习零拷贝技术之前先学习一下普通的IO拷贝过程吧，
这里举个栗子： 我要使用一个程序将一个目录下的文件复制到另一个目录下，
在普通的IO中，其过程如下：

![普通IO拷贝](../img/netty/普通IO拷贝.png)

**应用程序启动后，向内核发出read调用（用户态切换到内核态），操作系统收到调用请求后，
会检查文件是否已经缓存过了，如果缓存过了，就将数据从缓冲区（直接内存）拷贝到用户应用进程（内核态切换到用户态），
如果是第一次访问这个文件，则系统先将数据先拷贝到缓冲区（直接内存），然后CPU将数据从缓冲区拷贝到应用进程内（内核态切换到用户态），
应用进程收到内核的数据后发起write调用，将数据拷贝到目标文件相关的堆栈内存（用户态切换到内核态），
最后再从缓存拷贝到目标文件。**

根据上面普通拷贝的过程我们知道了其缺点主要有： 

1. 用户态与内核态之间的上下文切换次数较多（用户态发送系统调用与内核态将数据拷贝到用户空间）。
2. 拷贝次数较多，每次IO都需要DMA和CPU拷贝。

而零拷贝正是针对普通拷贝的缺点做了很大改进，使得其拷贝速度在处理大数据的时候很是出色。

零拷贝主要有两种实现技术：

1. 内存映射（mmp）
2. 文件传输（sendfile）

可以参照我编写的demo进行接下来的学习：

[zerocopy](https://github.com/guang19/framework-learning/tree/dev/netty-learning/src/main/java/com/github/guang19/nettylearning/zerocopy/ZeroCopyTest.java)


#### 内存映射（Memory Mapped）
````text
内存映射对应JAVA NIO的API为
FileChannel.map。
````

当用户程序发起 mmp 系统调用后，操作系统会将文件的数据直接映射到内核缓冲区中，
且缓冲区会与用户空间共享这一块内存，这样就无需将数据从内核拷贝到用户空间了，用户程序接着发起write
调用，操作系统直接将内核缓冲区的数据拷贝到目标文件的缓冲区，最后再将数据从缓冲区拷贝到目标文件。

其过程如下：

![mmp零拷贝](../img/netty/mmp零拷贝.png)

内存映射由原来的四次拷贝减少到了三次，且拷贝过程都在内核空间，这在很大程度上提高了IO效率。

但是mmp也有缺点： 当我们使用mmp调用映射一个文件到内存后，如果另一个进程同时对这个文件阶段或是做出写的操作，
那么系统如果此时正在将数据write到目标文件，用户程序可能会因为访问非法地址而产生一个错误的信号从而终止。 

试想一种情况：我们的服务器接收一个客户端的下载请求，客户端请求的是一个超大的文件，服务端开启一个线程
使用mmp和write将文件拷贝到Socket进行响应，如果此时又有一个客户端请求对这个文件做出修改，
由于这个文件先前已经被第一个线程mmp了，可能第一个线程会因此出现异常，客户端也会请求失败。

解决这个问题的最简单的一种方法就对这个文件加读写锁，当一个线程对这个文件进行读或写时，其他线程不能操作此文件，
不过这样处理并发的能力可能就大打折扣了。


#### 文件传输(SendFile)

````text
文件传输对应JAVA NIO的API为
FileChannel.transferFrom/transferTo
````

在了解sendfile之前，先来看一下它的函数原型（linux系统的同学可以使用 man sendfile 查看）：

````text
#include<sys/sendfile.h>

ssize_t 
sendfile(int out_fd,
        int in_fd,
        off_t *offset,
        size_t count);
````

sendfile在代表输入文件的文件描述符 in_fd 和 输入文件的文件描述符 out_fd 之间传输文件内容，
这个传输过程完全是在内核之中进行的，程序只需要把输入文件的描述符和输出文件的描述符传递给
sendfile调用，系统自然会完成拷贝。 当然，sendfile和mmp一样都有相同的缺点，在传输过程中，
如果有其他进程截断了这个文件的话，用户程序仍然会被终止。

sendfile传输过程如下：

![sendfile零拷贝](../img/netty/sendfile零拷贝.png)

它的拷贝次数与mmp一样，但是无需像mmp一样与用户进程共享内存了。


--- 


### ByteBuf API
网络传输的基本单位是字节，在Java NIO中，JDK提供了Buffer接口，以及其相关的实现作为NIO操作
数据的容器，如ByteBuffer等等。 而Netty为了解决Buffer原生接口的复杂操作提供了ByteBuf，
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