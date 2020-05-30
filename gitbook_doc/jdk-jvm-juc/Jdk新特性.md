<!-- TOC -->

  * [Jdk新特性](#jdk新特性)
      * [Jdk8新特性](#jdk8新特性)
      * [Jdk9新特性](#jdk9新特性)
      * [jdk10新特性](#jdk10新特性)
      * [jdk11新特性](#jdk11新特性)
      * [jdk12新特性](#jdk12新特性)
      * [jdk13新特性](#jdk13新特性)
      * [Jdk14新特性](#Jdk14新特性)

<!-- TOC -->

# Jdk新特性

总结的不全，还请各位同学补充。

### Jdk8新特性

- Lambda / 方法引用

- 接口新增default方法

- Stream API

- Optional API

- 新的时间API(java.time强烈推荐使用)

- 内置Base64工具

### Jdk9新特性
PS: jdk9应该是继jdk8之后，又一个重要的版本，后续jdk的迭代，都是基于jdk9来完成的。

- 模块化: 模块化是jdk9或者说jdk8之后最大的改进，在jdk8及以前，jvm启动时，需要加载非常多的不需要的外部扩展类库，导致程序消耗的内存非常大，
并且在打包后，应用的归档包也是比较庞大。但是在模块化系统中，jvm只需要加载每个模块需要的类库，这就大大减少了jvm的开销。模块化的好处
当然不止我所说的这些，其中奥妙，还请各位同学实践出真知。

- 集合的工厂方法

- try语句升级

- 接口新增private方法

- 响应式流(Doug Lea大师编写的，个人认为很重要，也是Webflux的基础)

- JShell 交互式编程环境

- String改为byte数组实现(记住这个特性)

PS: String类在jdk9后的每个版本，好像都会新增一些API，这个不再重复。

### jdk10新特性

- var类型推断: 这一功能在其他语言中早有实现，比如我接触过的c++的auto,js中的var(当然，js并不是强类型语言)。
其实我个人认为此特性意义不是特别重大，因为java本身就是强类型语言，var只能使用于局部变量推断。如果大量使用var,
反而可能造成代码可读性下降。

- 集合工厂方法，使用集合工厂创建的集合是不可变的集合

- 移除javah(在编写本地jni库时，需要javah生成c/c++头文件，javah被移除了，说明另有他法来解决这个问题)。


jdk10的特性还是有很多的，但是并没有像模块化这样大的改动。

### jdk11新特性

- java命令直接可以编译并运行java源文件

- HttpClient: 长期以来，java类库之中只有一个HttpUrlConnection可以使用，且HttpUrlConnection使用起来较为麻烦。

- Javascript引擎更换: Javascript引擎由Nashorn改为GraalVM。

- String类新增了许多好用的API，如: strip,isBlank等。

### jdk12新特性

- switch语法糖

- Unicode11支持

...

### jdk13新特性

- 文本块

- Socket API被重新实现

### Jdk14新特性

- instanceof 模式匹配

- Record结构(实用)

- **CMS收集器被删除了。**

- Parallel Scavenge 和 Serial Old这对组合被弃用了。(我觉着也是，本来Parallel Scavenge和Parallel Old，
Serial和Serial Old这两对收集器各自搭配的挺好，Parallel Scavenge非要脚踏两只船)


展望: ZGC是11中引入的一款新的垃圾回收器。G1收集器本身已经很高效了，但是停顿时间这一块缺陷是所有收集器的缺点，
而ZGC不仅对停顿时间这个缺点做了大量优化，也提供了非常多当高级功能。
似乎ZGC的到来，是要主宰Java GC的未来了。。。

关于ZGC可以参考这篇文章: [ZGC - 掘金](https://juejin.im/entry/5b86a276f265da435c4402d4)