
# ChannelHandler和ChannelPipeline
在Netty组件中我们已经介绍了ChannelHandler和ChannelPipeline的关系，这里我们将继续深入了解这两个核心
组件的细节。在学习本章内容之前，请各位同学温习一遍Netty组件部分的内容。

## ChannelHandler家族

### Channel的生命周期
在Channel的生命周期中，它的状态与ChannelHandler是密切相关的，下列是Channel组件的四个状态：

|        状态             |      描述                       |
|       :---             |     :---                        |
|  ChannelUnregistered   |    Channel没有注册到EventLoop    |
|  ChannelRegistered     |    Channel被注册到了EventLoop    |
|  ChannelActive         |    Channel已经连接到它的远程节点，处于活动状态，可以收发数据 |
|  ChannelInactive       |    Channel没有连接到远程节点      |

Channel的生命周期如下图所示，当这些状态发生改变时，将会生成对应的事件，ChannelPipeline中的ChannelHandler
就可以及时做出处理。