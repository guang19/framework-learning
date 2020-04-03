<!-- TOC -->
  
  * [Mysql数据类型](#mysql数据类型)
      * [UTF8和UTF8MB4](#utf8和utf8mb4)
      * [CHAR 和 VARCHAR](#char-和-varchar)
      * [TIMESTAMP 和 DATETIME](#timestamp-和-datetime)
      * [如何在TIMESTAMP和DATETIME之中选择?](#如何在timestamp和datetime之中选择)
    
<!-- TOC -->


# Mysql数据类型

#### UTF8和UTF8MB4

在mysql中，UTF8编码最多能存放3个字节的数据，也就是说几乎可以存放所有的中文汉字了。
但是如果遇到像emoji这种特殊的字符，那UTF8就不够用了。
在mysql5.5.3版本后，mysql加入了一种新的字符: UTF8MB4,他是UTF8的超集，
最多可以支持4个字节的数据，所以解决了UTF8兼容性的问题。

#### CHAR 和 VARCHAR
CHAR是固定长度的字符串,VARCHAR是可变的字符串。

**mysql5.0版本以上，CHAR(M)和VARCHAR(M)的长度M不再指字节长度，
而单纯的就是指长度。**

如CHAR(1)和VARCHAR(1), 既可以存放1个中文字符，也可以存放1一个英文字符，只不过它们底层的字节长度不同。
一个中文字符约等于3个字节，所以CHAR(1)如果存放一个中文字符，那么mysql底层的字节长度将为3。
(VARCHAR的实际最大长度还得看表的字符编码,UTF8最大只支持3个字节，UTF8MB4支持4个字节)

它们的主要区别如下:
- 长度上限不同: CHAR长度的上限为255个字节(约85个汉字),
  VARCHAR长度的上限为65535个字节(约21845个汉字)(mysql 5.0 以上)，和text相同。

- 占用空间不同: 使用CHAR 作为类型的话，无论该字段字符的长度是否小于指定长度，都会占用指定长度的空间。
  而VARCHAR则是根据字符的长度来决定占用的空间，这就是可变长字符串的好处。
              
- 是否删除字符串末尾的空字符串: **如果字符串末尾有空格字符，CHAR则会删除其末尾的空格，
  而VARCHAR不会删除字符串末尾的空格。**

- 效率问题: 由于VARCHAR是可变长的，如果对字符串进行更改的时候，
  那么也会修改其相应的空间，这在效率上就没有CHAR那么高效了。
          
     
#### TIMESTAMP 和 DATETIME

TIMESTAMP和DATETIME都是用于表示时间的类型。

它们主要有如下区别:

- **占用空间不同**: TIMESTAMP占用4个字节；DATETIME占用8个字节,因此TIMESTAMP更加节省空间。

- **时间范围不同**: TIMESTAMP表示 1970 - 2038年；DATETIME表示: 1000 - 9999 年。

- **是否受时区影响**: TIMESTAMP的时间会受当前Mysql的时区影响；
  而DATETIME则不受影响，存进去什么时间，拿出来就什么时间。


- **默认值不同**: TIMESTAMP字段的值如果为NULL，那么将会自动更新到当前时间；
  DATETIME存进去NULL，就默认为NULL。

#### 如何在TIMESTAMP和DATETIME之中选择?
如果你想让一个时间字段表示的范围更大，且不受系统时区影响，那么建议选择DATETIME。
其他情况考虑TIMESTAMP吧。