<!-- TOC -->

   * [JVM调优相关](#jvm调优相关)
        * [JVM常见参数](#jvm常见参数)
           * [堆栈相关](#堆栈相关)
           * [GC相关](#gc相关)
           * [其他](#其他)
        * [Java常用调优命令和工具](#java常用调优命令和工具)

<!-- /TOC -->


# JVM调优相关

### JVM常见参数

#### 堆栈相关
* -Xss
>调整线程栈大小。

* -Xms
>设置堆内存初始化大小。

* -Xmx / -XX:MaxHeapSize=?
>设置堆内存最大值。

* -Xmn / -XX:NewSize=?
>设置新生代大小。
 
* -XX:NewRatio=?
>设置老年代与新生代的空间占比。
>如: -XX:NewRatio=2,那么老年代:新生代=2:1。

* -XX:SurvivorRatio=?
>设置eden与survivor的空间占比。
>如: -XX:SurvivorRatio=2,那么eden:from survivor:to survivor=2:1:1

* -XX:MetaspaceSize=? / -XX:PerGenSize=?
>-XX:MetaspaceSize=9m
>设置元空间的初始化大小为9m,此参数只在jdk8以后的版本有效。
>
>-XX:PerGenSize=9m
>设置永久代的初始化大小为9m，此参数只在jdk8以前的版本有效。

* -XX:MaxMetaspaceSize=? / -XX:MaxPerGenSize=?
>-XX:MaxMetaspaceSize=50m
>设置元空间最大值为50m,此参数只在jdk8以后的版本有效。
>
>-XX:MaxPerGenSize=50m
>设置永久代的最大值为50m,此参数只在jdk8以前的版本有效。

* -XX:+HeapDumpOnOutOfMemoryError
>此参数使程序发生OOM时，dump错误堆栈信息。

* -XX:HeapDumpPath=?
>-XX:HeapDumpPath=/home/log
>此参数指定发生OOM时，dump错误堆栈信息存放的日志文件或目录。
>此参数只在 -XX:+HeapDumpOnOutOfMemoryError 开启时生效。

#### GC相关
* -XX:+PrintGCDetails / -Xlog:gc*
>打印GC的日志信息。 -Xlog:gc* 在我使用的版本(jdk11)是更受推荐的。

* -XX:+TraceClassUnloading / -Xlog:class+unload=info
>打印类卸载的日志信息。 -Xlog:class+unload=info 在我使用的版本(jdk11)是更受推荐的。

* -XX:+UseSerialGC
>使用Serial串行回收器。

* -XX:+UseParallelGC
>使用Parallel并行回收器。

* -XX:ParallelGCThreads=?
>设置并行收集的线程数,如-XX:ParallelGCThreads=5。

* -XX:+UseConcMarkSweepGC
>使用CMS收集器，它默认的新生代搜集器为ParNew。
>可以与参数: -XX:+UseSerialGC 一起使用，就替换掉了ParNew，
>使用Serial作为CMS的新生代收集器。

* -XX:+UseG1GC
>使用G1收集器。

* -XX:MaxTenuringThreshold=?
>设置新生代对象晋升到老年代的最大年龄阈值。


#### 其他
* -server / -client
>-server:以服务端模式运行应用程序，server模式适用于服务端应用程序。
>JVM在此模式下，会对服务端运行效率做很大优化。
>
>-client:以客户端模式运行应用城西，client模式适用于客户端桌面程序(GUI)。
>JVM在此模式下，会对客户端运行做很大优化。


### Java常用调优命令和工具
* jps(个人认为非常重要)
>jps 命令类似于 linux的 ps 命令，不过ps命令是用于查看系统进程的，
>而jps用于查看当前系统运行的java进程。
>
````text
jps -q 只输出java进程id
jps -l 输出java进程main函数的详细路径
jps -v 输出java进程时指定的jvm参数
jps -m 输出java进程执行时main函数的参数
````

* jstat
>jstat用于查看java进程的运行状态.

````text
jstat -class pid    用于查看java进程类的情况
jstat -compiler pid 用于查看java进程编译的情况
jstat -gc pid       用于查看java进程gc的情况
````

* jinfo
>jinfo 查看正在运行的java进程的jvm参数

```text
jinfo -flag MetaspaceSize pid  查看java进程的jvm的元空间大小
jinfo -flag MaxHeapSize pid    查看java进程的jvm的最大堆的大小
...
```

* jmap
>jmap 既可以dump java程序的快照，也可以查看对象的统计信息。

```text
jmap -heap pid               查看java进程堆的信息
jmap -histo pid              查看java进程对象的信息
jmap -dump:file=filename pid 生成java进程jvm的堆快照到指定文件 
```

* jstack
>jstack用于分析java线程栈信息

```text
jstack pid
```

* jconsole
>jconsole 是jdk提供的对java程序进行分析的GUI界面工具。