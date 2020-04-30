<!-- TOC -->

   * [Mysql存储引擎](#mysql存储引擎)
       * [InnoDB](#innodb)
       * [MyISAM](#myisam)
       * [MRG_MyISAM](#mrg_myisam)
       * [MEMORY](#memory)
       * [BLACKHOLE](#blackhole)
       * [CSV](#csv)
       * [ARCHIVE](#archive)
       * [FEDERATED](#federated)
       * [PERFORMANCE_SCHEMA](#performance_schema)

<!-- /TOC -->

# Mysql存储引擎

````text
使用: SHOW ENGINES 命令可以查看Mysql所有的存储引擎。
````

#### InnoDB
InnoDB是Mysql最常用的存储引擎之一。

InnoDB主要特点如下:

- 采用聚集索引: InnoDB采用聚集索引(B+树)，即数据和索引存放在一个文件(.ibd)中。

- 自适应Hash: 网上有的资料说InnoDB引擎支持Hash，也有的说不支持。
Hash索引的查询(定位)效率是很高的，只是范围和有序查询是它的硬伤，抛开这点，
Hash索引的优势还是很大的。**InnoDB会自适应优化，如果判断建立Hash索引确实能够提高效率，
那么InnoDB将自己建立相关索引。**

参考: [58沈剑前辈的文章](https://zhuanlan.51cto.com/art/201910/604054.htm###)

- 支持事务: 在Mysql的所有引擎中，只有InnoDB支持事务，这也是它被广泛采用的原因之一。

- 既支持行锁也支持表锁

- 支持缓存: InnoDB支持缓存，既能缓存数据，也能缓存索引。

- 支持外键

- 支持全文索引: Mysql 5.6开始，InnoDB也支持全文索引。

#### MyISAM
MyISAM也是Mysql最常用的引擎之一。

MyISAM主要特点如下:

- 采用非聚集索引: MyISAM引擎采用的是非聚集索引(B+树)，即索引和数据分开存放，
**索引的叶子节点存放指向数据的指针。.MYD文件存放数据, .MYI文件存放索引**。

- 不支持事务: **MyISAM引擎不支持事务**，可以说这是它最大的缺点了。

- 只支持表锁: 相比InnoDB，MyISAM只支持表锁，所以在并发方面，MyISAM可能没有InnoDB那么出色

- 不支持外键

- 支持全文索引

- 保存表的行数: MyISAM保存表的行数，而InnoDB不保存，
所以使用 SELECT COUNT(*)查询，MyISAM比InnoDB要快。

#### MRG_MyISAM
MRG_MyISAM引擎也称为MERGE引擎。

**它实际上是一系列相同的MyISAM表的集合，创建MERGE表时，必须指定成员表。**
**对MERGE表的操作，就是对成员表的操作，对成员表的操作，也会反馈到MERGE表中。**

>假如对MERGE进行INSERT，其实是INSERT到MERGE表的成员表上，
>对成员表进行INSERT，结果也会体现到MERGE表上。

**MERGE引擎要求merge的表必须都是MyISAM引擎，
且这些表必须拥有相同数据类型的属性列，相同顺序的索引，
但是也允许每个表对应列和索引的名称不同。**

#### MEMORY
MEMORY内存引擎。

MEMORY主要特点如下:

- 不支持持久化机制: MEMORY表的数据是存放在内存中的，且MEMORY不支持持久化机制。
**也就是说一旦MYSQL服务器发生宕机等故障，那么数据是很可能丢失的。** 所以MEMORY适用于临时数据表。

- 只支持行锁，不支持表锁

- 默认采用Hash索引，但支持B树索引: HASH索引执行条件SQL非常快，但如果是范围查询，则需要全表扫描。

#### BLACKHOLE
BLACKHOLE 黑洞引擎。

它的主要特点如下:

- 不存储数据: 如它的名字一样:黑洞。 写入的数据都会被吞噬掉，只进不出，
不会存储数据，只有一个.sdi(.frm)文件，但是它会记录binlog。

- 分担主库压力: BLACKHOLE不存储数据就没有用了吗?当然不是。

>在普通的主从架构下，slave节点直接连接master节点进行binlog同步压力是比较大的，
>可以让BLACKHOLE引擎的数据库节点与master进行搭配，
>再让其他slave节点与BLAKHOLE节点进行同步，这样就降低了master的压力。


#### CSV
CSV文件存储引擎。

CSV主要特点如下:

- CSV引擎的表不支持主键和其他普通索引

- 所有字段必须为NOT NULL: CSV引擎的表要求所有字段必须非空。

- 逗号分隔列数据: CSV引擎如它的名字一样，以.csv文件存储数据，且列数据之间以逗号分割。
关于CSV文件还可以参考:[CSV文件](https://baike.baidu.com/item/CSV/10739)


#### ARCHIVE
ARCHIVE归档引擎，主要解决了冷数据存储问题。

ARCHIVE主要有以下特点:

- 占用空间小: 同样的数据，ARCHIVE引擎表占用的空间要比InnoDB小80%左右。

- 不支持删除和修改: ARCHIVE引擎的表不允许UPDATE和DELETE操作。

- 支持行锁

- 不支持索引: ARCHIVE引擎的表支持主键和自增，但是不支持普通索引。


#### FEDERATED
FEDERATED是一种特殊的存储引擎，在我的版本中(8.0.x)默认是不开启的。

FEDERATED引擎使得我们可以访问远程Mysql服务器中数据库的数据。

FEDERATED类型的表由两部分组成:
1. 远程表: 远程表的引擎可以是Mysql支持的任何类型，如:InnoDB,MyISAM等。
 
2. 本地存放的.frm文件，.frm文件是对表的定义，包含了指向远程表的字符串。
 
**FEDERATED引擎的本地表并不存放数据，当对本地表进行操作的时候，实际上是操作远程表。**

#### PERFORMANCE_SCHEMA
PERFORMANCE_SCHEMA系统存储引擎，**它是Mysql的一个特性,**
主要用来收集Mysql数据库在运行时的性能，
具体参考:[Mysql:PERFORMANCE_SCHEMA文档](https://dev.mysql.com/doc/refman/5.6/en/performance-schema.html)
