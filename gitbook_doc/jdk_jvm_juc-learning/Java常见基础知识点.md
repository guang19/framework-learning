<!-- TOC -->

 * [java基础知识(部分图源:<a href="https://github.com/Snailclimb/JavaGuide">JavaGuide</a>)](#java基础知识部分图源javaguide)
      * [面向对象和面向过程的区别](#面向对象和面向过程的区别)
      * [OracleJdk与OpenJdk的区别](#oraclejdk与openjdk的区别)
      * [Java与C  的异同](#java与c的异同)
      * [JVM,JDK和JRE的区别](#jvmjdk和jre的区别)
      * [Java语言的特点](#java语言的特点)
      * [面向对象的特征](#面向对象的特征)
      * [重载和重写的区别](#重载和重写的区别)
      * [接口与抽象类的区别](#接口与抽象类的区别)
      * [Object类的方法有哪些?](#object类的方法有哪些)
      * [静态属性方法和成员属性方法区别](#静态属性方法和成员属性方法区别)
      * [子类属性与父类属性初始化顺序](#子类属性与父类属性初始化顺序)
      * [自动拆箱和装箱](#自动拆箱和装箱)
      * [String为什么不可变?](#string为什么不可变)
      * [final关键字的作用](#final关键字的作用)
      * [StringBuilder和StringBuffer区别](#stringbuilder和stringbuffer区别)
      * [equals知识点](#equals知识点)
      * [深拷贝与浅拷贝](#深拷贝与浅拷贝)
      * [IO流分类](#io流分类)
      * [使用字节流还是字符流?](#使用字节流还是字符流)
      * [BigDecimal](#bigdecimal)
      * [Java异常体系结构](#java异常体系结构)
      * [Comparable和Comparator](#comparable和comparator)
      * [为什么要慎用 Arrays.asList()?](#为什么要慎用-arraysaslist)
      * [Java中引用的类型](#java中引用的类型)

<!-- TOC -->


# java基础知识(部分图源:[JavaGuide](https://github.com/Snailclimb/JavaGuide))

**PS:以下部分内容希望各位同学下载openjdk的源码,亲自实践。**

openjdk8u:
* hotspot:[hotspot](http://hg.openjdk.java.net/jdk8u/hs-dev/hotspot/archive/tip.tar.gz)
* openjdk:[jdk](https://hg.openjdk.java.net/jdk8u/hs-dev/jdk/archive/tip.tar.gz)

#### 面向对象和面向过程的区别

>首先面向过程和面向对象的语言没有具体的性能高下之分,要依据每种语言的设计来做参考.
>个人认为面向过程与面向对象的最大区别在于: **面向过程的语言是结构化的,面向对象的语言是模块化的。**
>**模块化比结构化的代码更易于维护,复用与扩展。**

#### OracleJdk与OpenJdk的区别

>OpenJdk是基于Sum捐赠的源代码HotPot的,是开源的.
>OracleJdk是Oracle对Jdk的商业化版本,由Oracle发布并维护.
>因此OracleJdk比OpenJdk更可靠。

#### Java与C++的异同

- Java和C++都是基于面向对象思想的语言。

- Java不提供指针来访问内存,而C++允许指针访问内: Java不提供指针来访问内存,而C++允许指针访问内存。

- 垃圾回收机制: Java无需开发者手动释放内存,因为Java有垃圾回收机制。而C++则需要开发者手动释放内存,因此Java比C++在内存管理上相对安全。

- Java不支持多继承，而C++支持。

#### JVM,JDK和JRE的区别

- JVM: JVM(java virtual machine)是java虚拟机

- JRE: JRE(java runtime environment)是java运行时环境

- JDK: JDK(java development kit)是java开发工具包,不仅包含了jre和jvm,还提供了javac编译器和javadoc等其他开发所需的工具

#### Java语言的特点

- 面向对象

- 平台无关性,也就是跨平台(依靠JVM)

- 支持多线程

- 支持便捷的网络编程

- 编译与解释(JIT)

- 安全(个人认为所有语言写出来的代码的安全性是开发者决定的,
而不是语言本身决定的,语言能决定的只是提供方便或不便的安全的API)

#### 面向对象的特征
面向对象三大特征:封装,继承,多态。

- 封装: 封装是隐藏对象属性和的实现细节,只对外提供可访问或修改的接口。
**封装的目的是为了简化编程和增加程序的安全性,使得使用者无需了解对象的具体实现细节。**

- 继承: 继承是 在已存在的类上定义新的类的技术。
在Java中,已存在的类被称为基类(父类),新的类叫做派生类(子类).子类拥有父类的所有属性,方法。
但是子类对于父类中私有的方法或属性只是拥有,并不能访问和使用。
**继承的目的主要是为了代码的复用.**

- 多态: 多态指的是相同类型的对象,调用其相同的方法,参数也相同,但是它的表现形式也就是结果不同。
**多态的目的是为了程序的可扩展性和维护性。**
在Java中可以使用继承与接口2大特性实现多态。

#### 重载和重写的区别
个人认为重载和重写完全没有可比性,不知道为啥老有人喜欢拿它们做比较。

- 重载: 重载是描述一个类中的多个方法函数的名字是一样的,但是参数,类型,返回值,顺序都可能不同的。

- 重写: 重写是描述子类对父类的某个方法的逻辑进行了重新编写,但变的只是方法的内容,
方法名,参数,类型,顺序,返回值都是不变的。

#### 接口与抽象类的区别

- 接口需要被实现,而抽象类是需要被继承的。

- 接口里的方法都是公共抽象的，而抽象类既允许抽象也允许非抽象的方法(在jdk8中,接口被允许定义default方法,jdk9中还允许定义private私有方法)。

- 一个类允许实现多个接口,但只允许继承一个抽象父类。

- 接口是对类的规范,规范的是行为能力。而抽象类是对类的抽象,抽象的是逻辑。

#### Object类的方法有哪些?
1. getClass
2. equals
3. hashCode
4. toString
5. wait
6. wait(long): 让当前对象进入TIMED_WATING状态
7. wait(long,int):让当前对象进入TIMED_WATING状态
8. notify
9. nofifyAll
10. clone
11. finalize

#### 静态属性方法和成员属性方法区别
>**静态属性和方法属于类Class,而成员属性和方法属于实例化的对象。**
>
>静态方法只能使用静态方法和静态属性,不能使用成员属性和方法,
>因为静态属性和方法在对象还没被实例化的时候就存在了。
>
>**简单理解就是不允许一个已存在的事物使用一个不存在的事物。**

#### 子类属性与父类属性初始化顺序
1. 无论如何,静态数据首先加载,所以先初始化父类静态变量和静态初始化块,再初始化子类静态变量和静态初始化块。

2. 普通初始化块优先于构造方法,所以接下来加载父类的普通代码块,再调用父类构造方法。

3. 调用子类普通代码块和构造方法。

#### 自动拆箱和装箱
>自动拆箱和装箱实际上是Java编译器的一个语法糖。
>
>自动装箱是指: **将基本数据类型转为对应的包装类对象的过程。**
>
>自动拆箱是指: **将包装类转为对应的基本数据类型。**
>
>**自动装箱实际上是调用了包装类对象的valueof方法**,如: Integer.valueof(1)
>
>**自动拆箱实际上是调用了包装类的xxxValue方法**,如: Integer.intValue()
>
>**在自动装箱的时候,如果包装类允许缓存并且值在缓存的范围内,那么装箱生成的对象会被缓存到常量池中。**
>
>**Integer,Byte,Short,Long,Character包装类型具有缓存池,
>而其他三种:Float,Double,Boolean不具有缓存池。**
>
>包装类的缓存池缓存的范围基本都为: -128 - 127之间，
>除了Character的缓存范围为 0 - 127。

#### String为什么不可变?
>先说下我的看法:String是Java中最常使用的类没有之一,如果String是可变的,那么会发生非常多数不清的问题。
>
>如ip地址,人名,邮箱非常多的敏感数据,如果String是可变的,那么就会发生安全问题。
>如果String是可变的,那么字符串常量池也就无从谈起了。
>**String是不可变的,那么它本质上也是线程安全的。**
>
>**不可变类的缺点就是每个不同的值需要创建一个对象**

**String 是用final修饰的，保证了String类不能被扩展。
String内部的字段是用final修饰的(我的jdk版本是11,String由byte[]实现)，
并且没有对外提供修改字段的方法。这也是为什么String不可变的原理。**

#### final关键字的作用

- 被final修饰的类，不能被继承，并且这个类所有的成员方法都为final，不能被重写。

- 被final修饰的属性变量，不能被修改。如果该变量是基本数据类型的，那么其值在初始化后不能被修改。
如果该变量是引用类型的，那么该引用不能再指向其他对象。

- 被final修饰的方法不能被子类重写。

#### StringBuilder和StringBuffer区别
>**StringBuilder和StringBuffer都是可变的字符串,但是StringBuilder是线程不安全的。**
>
>StringBuffer是安全的,因此单线程情况下考虑使用StringBuilder,多线程情况下考虑使用StringBuffer。
>
>**他们之间的关系就好比HashMap和HashTable的关系。**
 
#### equals知识点

- **== 和 equals区别**:
  - ==比较的是对象的内存地址,equals比较的是对象的值。
  因此在Java中比较2个对象的值是否相等使用equals,判断2个对象是否是一个对象,使用==。

- hashCode方法返回的真是对象内存地址吗?

  - 在hotspot中，hashcode返回的不完全是地址
   (见：hotspot的/src/share/vm/runtime/synchronizer.cpp):

    ![hashcode方法源码](../../img/jdk_jvm_juc/hashcode方法源码.png)

    可以看到hashcode有多种返回策略:随机数，自增长序列，关联地址等多种方式。

- equals方法重写要求

  - 自反性: x.equals(x) == true 永远成立
  
  - 非空性: x.equals(null) == false 永远成立
  
  - 对称性: 如果 x.equals(y) == true , 那 y.equals(x)== true  
  
  - 传递性: 如果 x.equals(y) == true,并且 y.equals(z) == true,那么一定满足x.equals(z) == true
  
  - 一致性: 如果x.equals(y) == true , 那么只要x和y的值不变,那么x.equals(y) == true　永远成立

- 为什么重写equals方法一定要重写hashcode方法?

  在普通环境下(不涉及hash表),equals方法和hashcode方法一毛钱关系没有的,
  此时重写equals但不重写hashcode是没有关系的。
  但**当使用map,set这些散列表时,
  它们会根据对象的hashcode来计算对象在散列表中的位置的。**
  试想下,如果2个对象的值相等,但是由于它们是2个对象,hashcode却不相等。
  那么即使放入map,set(map)仍会存在重复数据。

#### 深拷贝与浅拷贝
>**深拷贝,听名字就知道,拷贝所有的内容,除了基本数据类型的变量复制一份,连引用类型的的对象也复制一份。**
>
>**浅拷贝,只是复制基本数据类型的变量,对于引用类型的变量,直接返回这个引用本身。**

#### IO流分类

1. 按照流的流向,分为:输入流和输入流。

2. 按照操作单元,分为:字节流和字符流。

#### 使用字节流还是字符流?
>考虑通用性,应该使用字节流。
>如果只是文本文件的操作,可以考虑使用字符流。

#### BigDecimal
>BigDecimal是Java中表示大浮点数的类型。
>
>在Java中,如果遇到浮点数的判断,可以使用BigDecimal来做计算,
>因为如果使用普通数据类型很可能会发生精度丢失的情况,这个时候的结果可能会出乎意料之外.

#### Java异常体系结构
在Java中,异常分为 Exception和Error,这2个类都继承自Throwable。

- Exception: Exception异常是程序本身可以处理的。Exception 分为运行时异常(RuntimeException)和
 非运行时异常(CheckedException)。
  
  - RuntimeException: RuntimeException(运行时异常)是在程序运行时可能会发生的异常,如NullPointException,
  这类异常往往是不可预料的,编译器也不会要求你手动try catch或throws。
  
  - CheckedException: CheckedException(非运行时异常)是RuntimeException以外的异常,如IOException，
  这类异常要求必须显示的try catch或throws ， 如果不处理,那么编译就不会通过。

- Error: Error错误是程序无法处理的,表示程序出现了无法解决的问题。


#### Comparable和Comparator

- Comparable: 自然排序接口。实现了它的类意味着就支持排序。

- Comparator: 外部比较器。无需让需要排序的对象实现排序逻辑，而是根据Comparator定义的逻辑来排序。
  Comparator相较于Comparable更加的灵活。
               
#### 为什么要慎用 Arrays.asList()?
>**因为Arrays.asList这个方法返回的根本就不是我们期盼的ArrayList,
>而是Arrays类内部实现的ArrayList,这个内部类只支持访问和set操作,
>并不支持remove,add,clear等修改操作。**

#### Java中引用的类型

Java中引用类型总共有四种: 强引用，软引用，弱引用，虚引用。

- 强引用(Strong Reference): Java程序中绝大部分都是强引用，一般使用new关键字创建的对象就是强引用。
  只要强引用存在，强引用的对象就不会被回收，除非不可达(参考jvm部分)

- 软引用(Soft Reference): 软引用一般不会回收，但是当堆内存不够的时候，
  比如几乎快要发生OOM的时候，就会发生回收掉软引用对象。

- 弱引用(Weak Reference): 只要垃圾回收开始，就会回收掉弱引用的对象

- 虚引用(Phantom Reference,又称幽灵引用): 和其他几种引用不同，虚引用不决定对象的生命周期，
  它在任何时候都可能被回收掉。