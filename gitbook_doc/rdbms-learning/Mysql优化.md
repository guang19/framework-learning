<!-- TOC -->

   * [Mysql优化](#mysql优化)
        * [Explain执行计划](#explain执行计划)
        * [开启慢查询日志](#开启慢查询日志)
        * [为什么要求字段尽量设置为NOT NULL?](#为什么要求字段尽量设置为not-null)
        * [为什么主键尽量设置成有序和整型?](#为什么主键尽量设置成有序和整型)

<!-- /TOC -->


# Mysql优化

#### Explain执行计划

>Explain是Mysql的优化神器，它可以对一条Query SQL进行分析，
>并输出SQL的详细信息，以便于优化SQL。

下面是Explain解析一条SELECT的例子:

![Explain执行计划](../../img/database/mysql/Explain执行计划.png)

EXPLAIN输出的属性如下:

- id: id是SELECT查询的序列号，表示一条SELECT或SELECT中子查询的执行顺序。主要有2种情况:
 
  - 如果id相同，可以被认为是同一分组，执行顺序，从上到下。
  - 如果id不同，id值越大，那么越先执行。


- **select_type**: select_type是为了区分简单查询，复杂查询，子查询等查询类型。
select_type主要有如下值:
  
  - SIMPLE: SIMPLE代表简单查询，SELECT中不包含子查询或联合查询等复杂查询。
  
  - SUBQUERY: SUBQUERY表示子查询部分。
  
  - PRIMARY: PRIMARY表示最外层查询，如果一个SELECT中包含子查询，
             那么最外层的查询就是PRIMARY。
  
  - DERIVED: DERIVED代表查询的结果被放入临时表中。
             如FROM 语句后跟着一个 SELECT ，那么SELECT的结果就可能被放入临时表中。
  
  - UNION: UNION代表联合查询，也就是UNION后的SELECT。
  
  - UNION RESULT: UNION RESULT代表联合查询的结果。
  
- table: table表示查询的表。

- partitions: partition代表查询匹配的分区，对于非分区表为NULL。

- **type**: 查询类型，是衡量SQL的一个重要的指标。
        一般来说，至少要达到range标准，最好达到ref。
        它有如下值：
    
  - SYSTEM: SYSTEM代表表只有一条记录，他是CONST类型的一个特例。
  
  - CONST: CONST表示表只最多只有一个匹配行，只用读一次，
           因此优化器可以视查询结果为一个常量。
  
  - EQ_REF: EQ_REF代表唯一性索引扫描。比如以 unique key索引或主键作为条件，
            这2种索引都是唯一的，表中只有一条记录与之匹配。
  
  - REF: REF代表非唯一索引匹配，返回匹配条件的所有行。
         比如WHERE条件的列有索引，经过匹配后，发现有多条符合条件的记录。
  
  - RANGE: RANGE表示范围扫描。如使用>,<,BETWWEN等条件。
  
  - INDEX: INDEX代表全表扫描，不过它与ALL的区别在于，
           INDEX是在索引列上进行全表扫描，一般要比ALL快。
  
  - ALL: ALL代表全表扫描，效率最低。
  
- possible_keys: poosible keys代表查询涉及到的字段如果有索引会被列出来，
                 但不一定使用。

- key: key代表查询实际使用到的索引，如果为NULL，则没有使用索引。

- key_len: key_len表示使用索引的实际长度。

- ref: ref表示与索引比较的列或这常量。
       假设 WHERE以主键作为条件 id = 10,那么ref显示的就是const，
       因为主键为10，就是一个常量。

- rows: rows代表查询优化器估算查询结果集要扫描的行数，
        也就是执行SELECT预计需要扫描的行数。

- filtered: filtered代表SELECT执行时过滤掉的记录与rows的百分比。
            filtered 显示的百分比 与 rows 的乘积就是查询时过滤掉的行数。

- EXTRA: 显示SQL执行时的附加信息


#### 开启慢查询日志

**慢查询用于记录查询超过某个临界值的SQL。**

使用:

````text
SHOW VARIABLES LIKE 'SLOW_QUERY_LOG';
````
查看慢查询是否开启，如果显示OFF，那么执行:


````text
set GLOBAL SLOW_QUERY_LOG = on 
````
开启慢查询日志。

---

使用:
````text
SHOW VARIABLES LIKE 'LONG_QUERY_TIME';
````
查看慢查询临界值,单位: 秒。

使用:
````text
SET LONG_QUERY_TIME=2
````
设置慢查询临界值。

**开启慢查询后，会在mysql的数据目录下产生xxx-slow.log慢查询日志文件，
一旦某条SQL超过了临界值，就会被记录到慢查询日志文件中。**


#### 为什么要求字段尽量设置为NOT NULL?
因为Mysql难以优化可以为空的字段，**一旦字段被建立了索引，
那么就需要额外的空间来存储此当前行记录是否为NULL的标识。**

#### 为什么主键尽量设置成有序和整型?
这个问题其实在索引章节就说过了，索引的结构是B+树，B+树最大的优点就是顺序查找，
有序的主键当然有利于查找，像UUID等这种长字符串，连排序都难以解决。
