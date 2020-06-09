
# Netty

**本文章摘抄自 《Netty in Action》(Netty实战)，再根据本人实际学习体验总结而成，
所以本文内容可能不那么全面，但是我尽量挑选Netty中我认为比较重要的部分做讲解。**

学习Netty，相信大部分同学都会选择 《Netty in Action》 ， 这里我推荐它的一个Gitbook精髓版本的，
此版本的作者对《Netty in Action》做出了更为精简的概述，所以各位同学可酌情挑选阅读。


- [Netty in Action(精髓)](https://waylau.com/essential-netty-in-action/index.html)


````text
如有错误之处，敬请指教。
````


## Netty是什么?

Netty是Red Hat开源的，一个利用Java的高级网络能力，隐藏其(Java API)背后的复杂性而提供一个易于使用的 NIO 客户端/服务端框架。
Netty提供了高性能和可扩展性，让你自由地专注于你真正感兴趣的东西。 Netty简化了网络程序的开发过程，使用它
我们可以快速简单地开发网络应用程序，比如客户端和服务端的通信协议，TCP和UDP的Socket开发。

在学习Netty之前，我们需要对 IO模型(网络IO模型)有一个大概的认知，可以参考我编写的：
[IO](https://guang19.github.io/framework-learning/gitbook_doc/jdk-jvm-juc/IO.html) 。


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



### Netty架构模型
Netty有 Bootstrap/ServerBootstrap,Channel,EventLoop,ChannelFuture,
ChannelHandler,ChannelPipeline等核心组件。   


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
Socket是对底层操作系统IO操作(如read,write,bind,connect等)的封装，
因此我们必须去学习Socket才能完成网络编程，而Socket的操作其实是比较复杂的，想要使用好它有一定难度，
所以Netty提供了Channel，更加方便我们处理IO事件。


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


#### ChannelPipeline
上面介绍了ChannelHandler的作用，它使我们更关注于特定事件的数据处理，但如何使我们自定义的
ChannelHandler能够在事件触发时被使用呢？ Netty提供了ChannelPipeline接口，它
提供了存放ChannelHandler链的容器，且ChannelPipeline定义了在这条ChannelHandler链上
管理入站和出站事件流的API。
当一个Channel被初始化时，会使用ChannelInitializer接口的initChannel方法，ChannelInitializer
会在ChannelPipeline中添加一组自定义的ChannelHandler。




