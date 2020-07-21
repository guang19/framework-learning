# Netty线程模型和EventLoop
由于线程模型确定了代码执行的方式，它可能带来一些副作用以及不确定因素，
可以说这是并发编程中最大的难点，因此，我们需要了解Netty所采用的线程模型，这样可以减少我们
在编写Netty程序时所犯的错误。


## 线程模型概述
现代操作系统几乎都具有多个核心的CPU，所以我们可以使用多线程技术以有效地利用系统资源。在早期的
Java多线程编程中，我们使用线程的方式一般都是继承Thread或者实现Runnable以此创建新的Thread，
这是一种比较原始且浪费资源的处理线程的方式。JDK5之后引入了Executor API，其核心思想是使用池化技术
来重用Thread，以此达到提高线程响应速度和降低资源浪费的目的。


## EventLoop事件循环
事件循环正如它所命名的那样，处于一个循环之中。我们以前在编写网络程序的时候，会使我们处理连接的逻辑
处于一个死循环之中，这样可以不断的接受请求。

Netty的EventLoop采用了两个基本的API：并发和网络。
Netty的并发包io.netty.util.concurrent是基于Java的并发包java.util.concurrent之上的，
它主要用于提供Executor的支持。Netty的io.netty.channel包提供了与客户端Channel的事件交互的支持。
以下是EventLoop类层次结构图：

![EventLoop类层次结构图](../img/netty/EventLoop类层次结构图.png)

在EventLoop模型中，**EventLoop将有一个永远不会改变的Thread，即Netty会给EventLoop分配一个
Thread，在EventLoop生命周期之中的所有IO操作和事件都由这个Thread执行。 根据配置和CPU核心的不同，
Netty可以创建多个EventLoop，且单个EventLoop可能会服务于多个客户端Channel。**

在EventLoop中，**事件或任务的执行总是以FIFO先进先出的顺序执行的，这样可以保证字节总是按正确的
顺序被处理，消除潜在的数据损坏的可能性。**


## 任务调度
有时候我们需要在指定的时间之后触发任务或者周期性的执行某一个人物，这都需要使用到任务调度。


### JDK任务调度
JDK主要有Timer和ScheduledExecutorService两种实现任务调度的方式，但是这两种原生的API的
性能都不太适合高负载应用。


### EventLoop任务调度
上面介绍过的EventLoop类层次结构图，可以看到EventLoop扩展了ScheduledExecutorService，
所以我们可以通过EventLoop来实现任务调度，其编程模型如下：

![EventLoop任务调度](../img/netty/EventLoop任务调度.png)

使用Channel获取其对应的EventLoop，然后调用schedule方法给其分配一个Runnable执行。Netty的任务调度
比JDK的任务调度性能性能要好，这主要是由于Netty底层的线程模型设计的非常优秀。
