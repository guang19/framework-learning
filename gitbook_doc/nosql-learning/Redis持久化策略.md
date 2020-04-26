<!-- TOC -->

  * [Redis持久化](#redis持久化)
      * [RDB(Redis Data Base)](#rdbredis-data-base)
      * [RDB优缺点](#rdb优缺点)
      * [AOF(Append Only File)](#aofappend-only-file)
      * [AOF优缺点](#aof优缺点)
      * [如何选择持久化策略?](#如何选择持久化策略)

<!-- TOC -->


# Redis持久化

Redis有2种持久化策略: RDB和AOF。

#### RDB(Redis Data Base)

RDB是Redis默认的持久化策略，这种策略是把内存的数据以二进制形式的副本保存在磁盘上。

- RDB持久化触发条件
  
  - SAVE命令: 当客户端执行SAVE命令时，会阻塞Redis主线程进行数据持久化，直到持久化完成。Redis在阻塞期间不能处理客户端的请求。 

  - BGSAVE命令: 当客户端执行BGSAVE命令时，Redis会fork一个子进程进行数据持久化，因此并不会阻塞Redis服务。
  
  - FLUSHALL命令: 当客户端执行FLUSHALL命令时，会清空Redis所有数据库的数据，并且也会触发数据同步。
  
  - save配置: Redis会按照配置文件中的save配置的条件进行数据同步，一旦满足条件，就会执行BGSAVE命令，即fork一个子进程进行同步。
  
  - shutdown: 当Redis服务关闭时，也会将数据同步到磁盘，以便下次启动时恢复。
  
  
#### RDB优缺点  
  
- RDB的优点:
 
  - 文件体积小,恢复大数据较快
  
  - 最大化Redis性能: Redis会fork出子进程进行数据同步，并不影响Redis的性能。


- RDB的缺点:
 
  - 数据安全性较低: 如果不显示的执行SAVE命令，那么Redis隔一段时间才会同步数据，可能会造成一定程度的数据丢失。

#### AOF(Append Only File)

AOF默认情况下是关闭的，当配置选项 appendonly 设置为yes后才会进行AOF的持久化。

appendfsync指定了AOF的同步策略，它有三个可选值。

1. no: no代表Redis不亲自持久化，而是通过系统调用write函数每隔一段时间将数据写入文件。
这种情况下如果服务器发生故障，可能会有数据还没来得及同步就丢失了。

2. always: always表示Redis每次执行写操作都会将数据同步到文件中。
这种策略虽然保证了数据的安全性，但是对Redis的性能会有影响。

3. everysec: everysec是AOF默认的持久化策略，这种策略下，
系统每一秒都会将数据写入文件，兼顾了性能和数据安全性。


#### AOF优缺点

- AOF优点:
 
  - 数据安全性较高,秒级丢失


- AOF缺点:

  - 文件体积大,恢复大数据较慢
  
#### 如何选择持久化策略?
两种持久化方式各有优缺点，可以选择混合的方式进行备份。
**混合持久化后，文件的内容大部分都是RDB格式的，恢复起来较快。
以AOF的方式同步也能保证数据的安全性。**

