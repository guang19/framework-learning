<!-- TOC -->
    
   * [Mysql Log日志](#mysql-log日志)
       * [binlog](#binlog)
       * [redolog](#redolog)
       * [undolog](#undolog)

<!-- /TOC -->

# Mysql Log日志

Mysql中有几种重要的日志文件，Mysql需要依赖这些日志文件完成非常重要的功能

#### binlog
二进制日志可以说是Mysql最重要的日志，它记录了所有的DDL和DML(SELECT和SHOW不会记录)语句。
**binlog的作用主要在于恢复记录和复制记录。**

#### redolog
**重做日志，它记录了事务的操作。**

它的作用是确保事务的持久性，防止因为外部原因，数据没来得及写入磁盘而丢失。
在Mysql启动时，会根据redolog重新执行事务操作，从而达到事务持久性。

**redolog在事务开启后就产生了，在事务的执行过程中，就被写入了redolog。**
redolog日志大小是固定的，当事务执行完后，数据落盘后，redolog就会被覆盖掉。

#### undolog
回滚日志,它记录了事务开始前数据的一个版本状态，如果事务在记录到redolog时，
就出现了意外，就可以将数据回滚到事务之前的这个状态。