<!-- TOC -->
 
   * [Redis缓存淘汰策略(key回收)](#redis缓存淘汰策略key回收)
       * [noeviction](#noeviction)
       * [volatile](#volatile)
       * [allkeys](#allkeys)

<!-- TOC -->

# Redis缓存淘汰策略(key回收)

**当Redis使用的内存超出物理内存限制时，
Redis的内存会和Swap(虚拟内存)频繁切换，造成Redis性能的急剧下降。
为了不让Redis内存占用超过物理内存占用，可以给Redis配置一个 maxmemory 的值，
当Redis占用内存超过了这个maxamemory的值，
那么Redis将启用缓存淘汰策略来删除内存中的key，
缓存淘汰策略可以通过 maxmemory-policy 设置。**

缓存淘汰策略主要分为3类:

1. noeviction
2. volatile
3. allkeys


#### noeviction

noeviction策略是Redis默认的淘汰策略。
**它可以继续接受请求并执行，但是不会执行写请求，
这样做可以保证内存中的数据不会被删除，但是却不能继续完成写请求。**


#### volatile

**volatile只针对 设置了过期时间的key做淘汰。**

volatile有三种算法实现:

1. **volatile-lru**:根据lru算法淘汰设置了过期时间的key，**lru算法优先删除最近最少使用的key**。

2. **volatile-ttl**:根据key的过期时间淘汰设置了过期的key，**过期时间越小的key优先被删除**。

3. **volatile-random**:**随机淘汰设置了过期时间的key**。

#### allkeys

**allkeys对所有key无差别淘汰**

allkeys有两种算法实现:

1. **allkeys-lru**: 根据lru算法淘汰所有的key，最近最少使用的key优先被删除。

2. **allkeys-random**:随机淘汰所有的key。