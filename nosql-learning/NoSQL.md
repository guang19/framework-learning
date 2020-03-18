<!-- TOC -->

   * [NoSQL(Not-only SQL)](#nosqlnot-only-sql)
      * [什么是NoSQL数据库?](#什么是nosql数据库)
      * [为什么要有NoSQL数据库?](#为什么要有nosql数据库)
      * [非关系型数据库与关系型数据库的区别](#非关系型数据库与关系型数据库的区别)
      * [有哪些类型的NoSQL数据库?](#有哪些类型的nosql数据库)
   * [Redis](#redis)
      * [什么是Redis?](#什么是redis)
         * [Redis优缺点](#redis优缺点)
         * [Redis为什么这么快?](#redis为什么这么快)
         * [Redis应用场景](#redis应用场景)
         * [为什么不用Map或Guava做缓存?](#为什么不用map或guava做缓存)
         * [Redis和Memcached异同](#redis和memcached异同)
      * [Redis IO模型](#redis-io模型)
         * [Redis 6 之前](#redis-6-之前)
         * [Redis 6 之后](#redis-6-之后)
      * [Redis事务](#redis事务)
         * [Redis事务执行过程](#redis事务执行过程)
         * [Redis事务队列](#redis事务队列)
         * [Redis事务错误](#redis事务错误)

<!-- /TOC -->

## NoSQL(Not-only SQL)

````text
如有错误之处，敬请指教。
````

PS:部分图片源于网络,如有侵权，请联系俺，俺会立刻删除。

### 什么是NoSQL数据库?
>NoSQL数据库泛指非关系型数据库，与关系型数据库不同，非关系型数据库并没有一种固定的存储数据的结构，
>相对来说比较灵活。

### 为什么要有NoSQL数据库?
>个人认为非关系型数据库与关系型数据库是一种相辅相成的关系，起到了互补的作用。
>
>关系型数据库的数据看上去很直观且支持事务，保证了数据的一致性。
>
>非关系型数据库读写速度块，在高并发的压力下仍有不俗的表现且数据结构丰富，
>更多的是对关系型数据库的一种补充。

### 非关系型数据库与关系型数据库的区别
- 存储结构
>关系型数据库按照结构化的方式存储数据，需要先定义好数据库表的字段，
>再存储数据。这样做的好处就是可靠性比较高，但是如果后期应用扩展功能，
>需要扩展表的话，会有些受限。
>
>非关系型数据库存储的结构则不像关系型数据库那样固定，相对来说较为灵活，
>可以根据数据调整数据库的结构。

- 存储方式
>关系型数据库大多采用行和列这样的表格关系存储数据。
>
>非关系型数据库存储数据的方式则不固定，有的采用K-V键值对存储，
>有的采用文档存储，还有的图数据库使用图结构存储。

- SQL标准
>关系型数据库采用结构化的语言SQL来对数据库进行操作，并且SQL已成为大多数数据库的标准规范。
>
>非关系型数据库则各自为战，一直没有一个统一的标准，每种厂商提供的数据库规范都不一样。

- 读写速度
>关系型数据库强调数据的一致性，所以在遇到高并发读写操作时，会显得力不从心。
>
>非关系型数据库强调BASE理论:Basically Available(基本可用),Soft-state(软状态),Eventual Consistency(最终一致性)，
>它允许一定程度的数据不一致，但保证数据的最终一致性。
>因此，面对高并发读写操作时，表现的会比关系型数据库好的多，
>这也是redis,memcached这类高性能的NoSQL数据库被用于缓存的主要原因。

### 有哪些类型的NoSQL数据库?

- K-V键值对: K-V键值对类型的NoSQL数据库类似于Hash表，将数据存储在内存中，
操作速度非常的快，因此常被用于缓存数据库。K-V键值对类型的NoSQL数据库主要有:Memcached,Redis等。

- 文档: 文档类型的NoSQL数据库结构则不固定，无需像关系型数据库一样预先定义字段，它存储数据的方式类似于JSON，
可以清晰的描述数据之间的复杂关系。文档类型的NoSQL数据库主要有:MongoDB,CouchDB等。

- 列式存储: 列式存储的NoSQL数据库以列簇形式存储数据，将同一列的数据存储在一起，
这样可以分割为多列，查询速度是很快的，但是列式存储的数据库功能也会收到限制。
列式NoSQL数据库主要有:HBase等;

![行式存储和列式存储](../img/database/redis/行式存储和列式存储.png)

- 图结构: 图数据库主要用于构建节点的关系图谱，以图算法和图结构进行计算和存储。
 图数据库主要有:Neo4j等。


## Redis

### 什么是Redis?
>Redis是一个使用ANSI C语言编写，遵守BSD协议规范的开源的K-V类型的NoSQL数据库服务器。
>Redis是当前最流行的K-V类型的NoSQL数据库之一，在通往系统架构的方向，它是我们不得不学习的知识。

[Redis官网](https://www.redis.net.cn/)


#### Redis优缺点

- Redis的优点:
  
  - 性能高: 用c语言编写的应用我就没见过慢的~_~。
  
  - 丰富的数据结构: 总体上来说Redis是以K-V形式存储数据，但是细分来说，
                  它支持STRING，HASH，LIST，SET，SORTED_SET，HyperLogLog等多种数据结构。 

  - 支持Lua脚本: Redis使用Lua脚本解释器来执行脚本，所以它支持Lua脚本。
  
  - 支持事务: Redis是为数不多的支持事务的NoSQL数据库之一。
  
  - 支持数据持久化: Redis支持rdb和aof两种数据持久化方式。
  
  - 支持发布者/订阅者功能
  
  - 支持主从模式: Redis支持Sentinel哨兵模式搭建高可用集群配置。

- Redis的缺点:

 - 受限于物理内存: Redis属于内存数据库，它在内存中存储数据的大小是受物理内存限制的，
                 所以它不适合存储海量数据。

#### Redis为什么这么快?

- 内存操作: Redis绝大部分操作都是基于内存的，想不快都难。

- 优秀的数据结构: Redis虽然支持的数据结构众多，但是它的每种数据结构都是专门设计和优化过的。

- 多路复用IO: Redis整体采用多路复用IO模型，核心操作使用单线程处理。

#### Redis应用场景     

- 缓存: 缓存可能是Redis用的最多的场景了。由于Redis的高性能，
高并发场景下作为缓存服务数据库，再适合不过了。
并且Redis支持的key自动过期功能，更是可以定制热点数据的过期时间。

- 多功能业务场景: Redis支持多种丰富的数据结构，不仅可以存储简单的K-V数据，还可以使用Hash存储用户，商品等信息，
List存储有序的数据，Set还有交集，并集，差集等功能。

- 分布式锁: Redis的操作具有原子性的，可以利用这点来完成分布式锁。


#### 为什么不用Map或Guava做缓存?
>因为无论是Map还是Guava，都属于本地缓存，在一个JVM进程内的，如果是单机模式，这样做尚可。
>但如果是分布式或者Java应用有多个实例，那就不能保证每个JVM进程内的缓存是一致的，
>所以需要使用Redis这种第三方数据库作为缓存容器。
   
   
#### Redis和Memcached异同   

- 都属于内存数据库

- 持久化支持: Redis支持RDB和AOF两种持久化机制；
             Memcached只在内存中存储数据，不支持持久化机制。

- 数据结构: Redis从整体上来说是以K-V类型的为存储结构，
        但它细分可以支持String，Hash，List，Set，Sorted Set等数据类型；
        Memcached则只支持K-V类型的存储结构。

- IO模型: Redis是以多路复用IO为模型的设计；
          Memached是以非阻塞IO为模型的设计。

- 事件库: Redis采用自制的AeEven事件库处理Socket事件;
         Memcached采用的是LibEvent事件库。

- 使用场景: 不考虑性能，Redis更适用于需要复杂数据结构，需要持久化的应用，
           如果你的应用以后需要扩展，那么也可以选择Redis；
           Memcached则适用于高并发和只需要K-V数据结构的应用。  

---       
       
### Redis IO模型
Redis IO模型按Redis的版本可以分为Redis 6之前和Redis 6之后。

#### Redis 6 之前
> Redis是基于多路复用IO模型处理Socket请求的，关于多路复用的知识，我在
[Linux五种IO模型中](https://github.com/guang19/framework-learning/blob/dev/jdk_jvm_juc-learning/Jdk&Jvm&Juc.md#%E5%A4%9A%E8%B7%AF%E5%A4%8D%E7%94%A8io%E7%BD%91%E7%BB%9Cio%E6%A8%A1%E5%9E%8B)
>已经说过了：
>多路复用IO模型依赖于操作系统的select/poll/epoll函数，
>epoll函数使得内核不断轮询客户端socket，
>用户进程(线程)也需要阻塞在对epoll函数的调用上，当Socket有事件时，
>用户线程便发起系统调用，处理Socket事件。
>
>多路复用IO模型简单理解就是一个线程处理多个Socket连接。
>所以**可以把Redis看成是单线程模型，但并不是说Redis只有一个线程，
>而是说它执行核心操作的线程只有一个，它还有其他辅助线程完成其它功能。
>Redis这样设计就避免了多线程切换的开销和简化了Redis的设计。**

Redis IO模型:

![Redis 6之前IO模型](../img/database/redis/Redis 6之前IO模型.png)

#### Redis 6 之后
>Redis 6 之前一个线程处理所有的Socket和核心操作，
>这样做的好处就是单线程无需考虑像多线程切换带来的困扰，
>简化了Redis的模型，但随之而来的也有性能上的瓶颈。
>
>**虽然Redis确实够快，但它是在内存中操作的，会收到内存的限制。
>且一个线程处理所有的Socket会带来网络IO的限制，并不能发挥多核CPU的优势。**
>
>那有什么办法既能够发挥多核cpu的优势，又不会复杂化Redis的架构呢？
>
>Redis 6 之前的瓶颈主要在于内存和网络IO。
>
>关于内存这块，只得靠Redis自身的优化和机器条件了，
>但是网络IO这块就可以通过新增多线程来处理大量Socket连接来优化了。
>Redis 6 正是通过新增监听线程来解决网络IO的瓶颈，线程监听到Socket事件后，
>再交由main线程处理。
>所以**整体来说，Redis 6 在处理Socket事件上有单线程优化成了多线程，
>但核心操作还是由单线程执行。**

Redis 6 IO模型：

![Redis 6 IO模型](../img/database/redis/Redis 6 IO模型.png)

---

### Redis事务

>Redis的事务主要依赖于WATCH ,UNWATCH,MULTI , EXEC, DISCARD等命令。
>其中 MULTI , EXEC , DISCARD 分别对应关系型数据库的 
>BEGIN,COMMIT,ROLLBACK操作。

#### Redis事务执行过程
>客户端使用MULTI命令开启事务，此时用户就可以开始发出要执行的命令。
>如果命令为WATCH/MULTI/EXEC/DISCARD这四个中的任意一个，
>那么会被直接执行，因为它们属于事务操作。
>当执行DISCARD的时候，会清空事务队列并退出事务。
>如果是普通命令，就将命令加入事务队列，然后当EXEC命令执行时，
>事务中的队列将会被一一执行，最后执行的结果也是一个数组。

![Redis执行事务](../img/database/redis/Redis执行事务.png)

参考: [Redis事务的设计与实现](https://redisbook.readthedocs.io/en/latest/feature/transaction.html)

#### Redis事务队列
在开启事务后，用户命令并不会被立刻执行，而是被添加到事务队列中，
这个队列其实是一个数组，每个数组元素由3部分组成:
1. 要执行的命令(cmd)
2. 命令的参数(argv)
3. 参数的数量(argc)

命令被添加到队列中的结构大致如下:

![Redis事务队列详情-执行前](../img/database/redis/Redis事务队列详情-执行前.png)

命令被执行后，也会生成一个结果数组，Redis就将这个结果数组返回:

![Redis事务队列详情-执行后.png](../img/database/redis/Redis事务队列详情-执行后.png)

#### Redis事务错误

Redis事务可能有两个错误时机。

- EXEC执行命令之前出现错误
>在EXEC命令之前的错误，也就是开启事务后，用户发出了错误的命令，
>参数数量不对或其他原因，服务端会累积这些错误。
>当EXEC命令执行时，将拒绝执行事务，并返回错误原因，清空事务队列。

![Redis事务错误-EXEC执行前](../img/database/redis/Redis事务错误-EXEC执行前.png)

- EXEC执行命令时出现错误
>EXEC执行命令时出现错误，也就是用户发出的命令没有错，
>但是在执行命令的时候出现了错误(可能是参数类型不对)，这时候仍然返回结果数组。
>也就是说错误的命令并不影响其他命令的执行。

![Redis事务错误-EXEC执行时](../img/database/redis/Redis事务错误-EXEC执行时.png)







