
# ChannelHandler和ChannelPipeline
在Netty组件中我们已经介绍了ChannelHandler和ChannelPipeline的关系，这里我们将继续深入了解这两个核心
组件的细节。在学习本章内容之前，请各位同学温习一遍Netty组件部分的内容。

### ChannelHandler家族

#### Channel的生命周期
在Channel的生命周期中，它的状态与ChannelHandler是密切相关的，下列是Channel组件的四个状态：

|        状态             |      描述                       |
|       :---             |     :---                        |
|  ChannelUnregistered   |    Channel没有注册到EventLoop    |
|  ChannelRegistered     |    Channel被注册到了EventLoop    |
|  ChannelActive         |    Channel已经连接到它的远程节点，处于活动状态，可以收发数据 |
|  ChannelInactive       |    Channel与远程节点断开不再处于活动状态      |

Channel的生命周期如下图所示，当这些状态发生改变时，将会生成对应的事件，ChannelPipeline中的ChannelHandler
就可以及时做出处理。


#### ChannelHandler生命周期
ChannelHandler接口定义了其生命周期中的操作，当ChanelHandler被添加到ChannelPipeline
或从ChannelPipeline中移除时，会调用这些操作，ChannelHandler的生命周期如下：

|       方法          |    描述   |
|       :---         |     :---  |
|   handlerAdded     |   当把ChannelHandler添加到ChannelPipeline中时调用此方法       |
|   handlerRemoved   |   当把ChannelHandler从ChannelPipeline中移除的时候会调用此方法 |
|   exceptionCaught  |   当ChannelHandler在处理数据的过程中发生异常时会调用此方法      |


#### ChannelInboundHandler接口
ChannelInboundHandler会在接受数据或者其对应的Channel状态发生改变时调用其生命周期的方法，
ChannelInboundHandler的生命周期和Channel的生命周期其实是密切相关的。
以下是ChannelInboundHandler的生命周期方法：

|     方法     |    描述    |
|   :---      |    :---    |
| ChannelRegistered     |  当Channel被注册到EventLoop且能够处理IO事件时会调用此方法 |
| ChannelUnregistered   |  当Channel从EventLoop注销且无法处理任何IO事件时会调用此方法 | 
| ChannelActive         |  当Channel已经连接到远程节点(或者已绑定本地address)且处于活动状态时会调用此方法 | 
| ChannelInactive       |  当Channel与远程节点断开，不再处于活动状态时调用此方法   |
| ChannelReadComplete   |  当Channel的某一个读操作完成时调用此方法               |
| ChannelRead           |  当Channel有数据可读时调用此方法                      |
| ChannelWritabilityChanged | 当Channel的可写状态发生改变时调用此方法，可以调用Channel的isWritable方法检测Channel的可写性，还可以通过ChannelConfig来配置write操作相关的属性  |
| userEventTriggered    |  当ChannelInboundHandler的fireUserEventTriggered方法被调用时才调用此方法。  |

**这里有一个细节一定需要注意：当我们实现ChannelInboundHandler的channelRead方法时，请一定要记住
使用ReferenceCountUtil的release方法释放ByteBuf，这样可以减少内存的消耗，所以我们可以实现一个
ChannelHandler来完成对ByteBuf的释放，就像下面这样：**

![ChannelInboundHandler释放ByteBuf](../img/netty/ChannelInboundHandler释放ByteBuf.png)


**一个更好的办法是继承SimpleChannelInboundHandler，因为SimpleChannelInboundHandler已经帮我们
把与业务无关的逻辑在ChannelRead方法实现了，我们只需要实现它的channelRead0方法来完成我们的逻辑就够了：**

![SimpleChannelInboundHandler的ChannelRead方法](../img/netty/SimpleChannelInboundHandler的ChannelRead方法.png)

**可以看到SimpleChannelInboundHandler已经将释放资源的逻辑实现了，而且会自动调用ChannelRead0方法
来完成我们业务逻辑。**


#### ChannelOutboundHandler接口
出站数据将由ChannelOutboundHandler处理，它的方法将被Channel，ChannelPipeline以及ChannelHandlerContext调用
（Channel，ChannelPipeline，ChannelHandlerContext都拥有write操作），以下是ChannelOutboundHandler的主要方法：

|       状态       |      描述       |
|       :---      |     :---        |
|      bind       |    当Channel绑定到本地address时会调用此方法  |
|      connect    |    当Channel连接到远程节点时会调用此方法     |
|      disconnect |    当Channel和远程节点断开时会调用此方法     |
|      close      |    当关闭Channel时会调用此方法              |
|      dereigster |    当Channel从它的EventLoop注销时会调用此方法   |
|      read       |    当从Channel读取数据时会调用此方法         |
|      flush      |    当Channel将数据冲刷到远程节点时调用此方法  |
|      write      |    当通过Channel将数据写入到远程节点时调用此方法   |

**ChannelOutboundHandler的大部分方法都需要一个ChannelPromise类型的参数，ChannelPromise是
ChannelFuture的一个子接口，这样你就可以明白ChannelPromise实际的作用和ChannelFuture是一样的，
没错，ChannelPromise正是用于在ChannelOutboundHandler的操作完成后执行的回调。**