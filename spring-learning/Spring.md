## Spring常见面试题

#### 什么是Spring Framework
Spring是一个轻量级的，开源的Java应用程序开发框架。它提供的IOC和AOP等核心功能，能够使开发者很方便的开发出松耦合的应用。
 
#### Spring的优缺点
##### Spring的优点:
1. 方便解耦，简化开发
将系统的类之间的复杂关系，交由Spring IOC工厂容器管理。

2. AOP支持
Spring 提供 AOP模块，能够很方便的编写出AOP程序.

3. 声明式事务
只需要通过配置或注解就可以完成对事务的支持，而不需要手动的编写事务代码

4. 第三方框架无缝集成
Spring可以很方便的将第三方框架继承到系统中，很灵活。

...
##### Spring的缺点:
1. 复杂
Spring发展到现在，确认有些复杂了，但是对于它解决的问题来说，复杂已经不算什么了。

2. 效率
Spring内部依赖反射，而反射的效率正是它的缺点。
 

#### Spring 主要提供了哪些模块
1. core模块提供IOC和DI等核心功能

2. aop模块提供面向切面编程的实现

3. web模块提供对web应用的支持

4. dao模块提供数据库方面的支持

5. test模块提供测试方面的支持
 
#### Spring主要使用了哪些设计模式
```text
Spring使用的设计模式有很多，此处只列举几个常见的
```
1. 工厂模式
BeanFactory 就是简单工厂的实现，用来创建和获取Bean

2. 单例模式
Spring容器的Bean默认是单例的

3. 代理模式
aop使用的就是代理模式

4. 模板方法模式
jdbcTemplate等就使用模板方法模式

5. 观察者模式
当有事件触发时，该事件的监听者(观察者)就会做出相应的动作。
...
 
#### Spring IOC容器的配置方式有哪些？
 1. xml (不再推荐使用)
 2. 注解(推荐使用)
 3. Java API(和注解一起使用)
 
 #### Spring 的IOC容器是什么?它有什么作用?常常提到的依赖注入和控制反转与IOC容器是什么关系?
 Spring IOC容器是Spring框架的核心.IOC容器的作用是负责管理我们规定好的bean实例,并负责处理容器里bean之间的依赖关系.
 IOC容器管理bean主要是管理bean的生命周期:创建,初始化,使用和销毁.依赖注入是指容器会自动装配和设置bean所需的依赖;控制反转是指
 我们创建对象和获取并使用的过程,有容器完成和提供.
 
 #### Spring依赖注入的方式有几种?
 1.setter方法注入
 2.构造器注入
 3.工厂方法注入(静态工厂方法和实例bean的工厂方法)
 
 #### IOC容器有几种?他们的区别是什么?
 1.BeanFactory
 2.ApplicationContext
  BeanFactory使用了工厂模式,它是IOC容器的顶级接口,它包含了容器的基本逻辑,也就是获取bean和判断bean属性的基本方法.
  ApplicationContext是BeanFactory的应用拓展接口,提供了BeanFactory所没有的额外功能,如ApplicationContext支持bean的懒加载和预加载,而BeanFactory只支持bean的懒加载;
  总之,记住ApplicationContext是BeanFactory拓展子接口
  
 #### 一个bean的定义包含了什么?(BeanDefinition)  
 bean的元数据如作用域,initMethod,destroyMethod,依赖(depends on)和懒加载等等
 
 #### bean的作用域有哪些?
 1. singleton: 单例bean.IOC容器中只会保存该bean的一个实例,并且全局都使用这一个bean实例
 2. prototype: 原型bean.每次获取bean的时候,都会创建该bean的实例
 3. request:  用于web应用.每次Http请求,都会创建该bean的实例,并且该bean的实例只会在相应的Http请求内有效.
 4. session:  用于web应用.不同的Http Session,有不同的bean实例,该bean实例只在对应的Session域内生效,Session销毁,bean实例也就销毁
 5. global-session: 用于portlet web应用(与servlet应用相似),如果 global-session 用于 portlet web应用,那么该bean的实例作用于该应用的整个session域内,而如果是用于普通的java ee(servlet)应用,只在对应的session域内生效.
 
 #### bean的生命周期?(这个生命周期我是以xml配置文件的方式测试的,注解的方式应该也是如此)
 1. 加载xml文件
 2. 如果容器中有BeanFactoryPostProcessor,那么执行该接口的postProcessBeanFactory方法,该接口是对BeanFactory的一个拓展点,因为BeanFactory是IOC容器的顶级接口,所以这个方法允许用户修改该BeanFactory(是ConfigurableListableBeanFactory)
 3. 实例化bean,也就是调用bean的构造方法
 4. 如果容器中有BeanFactoryAware,BeanNameAware,ApplicationContextAware这几个Aware接口的实例,则执行他们几个里面的唯一方法(setBeanFactory,setBeanName,setApplicationContext).
 5. 如果容器中有BeanPostProcessor,那么执行该接口的postProcessBeforeInitialize方法,BeanPostProcessor是对bean的拓展
 6. 如果容器中有InitializingBean,那么执行该接口的afterPropertiesSet方法,这一步是在bean实例的所有属性被注入完后被调用的
 7. 如果bean声明了init-method方法,那么执行init-method方法
 8. 如果容器中有BeanPostProcessor,那么执行postProcessAfterInitialize发放方法
 9. 可以获取bean实例了.
 10. 容器如果被销毁,并且如果容器中有DisposableBean,那么执行该接口的destroy方法.
 11. 如果bean声明了destroy-method方法,那么执行destroy-method方法
 
 #### Spring各种常用的注解?
 ......
 
 #### 什么是AOP?
 AOP:面向切面编程.在我看来,AOP是一种程序设计思想,它将程序的主要逻辑和辅助逻辑分离开来,可以实现程序的解耦,也可以将通用的辅助逻辑用于所有需要的主要逻辑.
 
 #### AOP的元素有哪些?
 1.连接点(join point):他是程序执行的某个位置,如方法的调用前,方法调用后,方法返回时,方法抛出异常时
 2.切点(pointcut): 把连接点看做是静态的,那么切点就是对应这些静态点的拦截者
 3.切面(aspect): 点画成线,线画成面,不同切点的组成就是切面了,在Java程序中,就是一个类,这个类包含了一系列的切点增强.
 3.增强(advice): 切点把连接点拦截到后,准备做的事情,准备调用的方法就是增强.
 4.织入(weave): 将增强应用到连接点的过程,通常使用代理模式实现.
 
 #### AOP的几种实现? 
AOP是通过代理模式实现的,代理模式又分为静态代理和动态代理.Aspectj就属于AOP静态代理的一种实现,我们常说动态代理都好在哪,静态代理怎么不好,但是Aspectj可不是普通的静态代理,它先由自己的acj编译器把Aspect类(切面)编译成字节码,然后在目标类编译时织入,即编译期增强.
而另外Spring AOP是由CGLIB和JDK动态代理实现的,CGLIB和JDK的区别我就不说了.由于Aspectj和SpringAOP起到了互补的作用,所以Spring AOP集成了这三者的技术,实现了完美的AOP框架.

#### Spring事务隔离级别有哪些?
 Spring的事务隔离级别对应了数据库的事务隔离性.
 来都来了,顺便复习下事务的4大特性和隔离级别吧.
 ACID:
 Atomic:原子性.指事务是一个不可再分割的操作.事务里的操作要么都执行,要么都不执行.
 Consistency:一致性.事务前后,数据的完整性是一致的.打个比方:A给B转钱,不管转钱是否成功,A和B的钱的和是不变的.
 Isolation:隔离性.多个用户(多线程)访问数据,数据库为每个用户开启一个事务,那么每个事务都应该不受其他事务干扰,事务应该相互隔离.
 Durability:持久性.事务一旦被提交,那么不论发生什么,它对数据的改变和影响应该是永久性的.
 
#### 事务的隔离级别:
 Mysql默认支持的事务隔离级别为 REPEATABLE-READ(可重复读),可通过SELECT @@transaction_isolation查看,
  Mysql支持的事务隔离级别有READ_UNCOMMITED(读未提交),READ_COMMITTED(读已提交),REPEATABLE_READ(可重复读),SERIALIZABLE(串行化)
  
  下面说说这几种隔离级别会出现的问题,以及解决办法:
  mysql设置数据库隔离级别的SQL为 SET SESSION TRANSACTION ISOLATION LEVEL (上面那些隔离级别).
  
 ##### 处于READ_UNCOMMITTED的时候:
  可能会发生脏读:2个事务同时对数据库进行查询操作,第一个事务先把要查询的值2改为了3,但是没有提交事务,第二个事务读取到了3并提交了事务,那么第二个事务读取到的3就是脏数据.
  
  脏读也就是读取到另一个事务未提交的数据,这种隔离级别的解决办法就是把READ_UNCOMMITTED改为READ_COMMITTED,这样,只有第一个事务提交了,第二个事务才会读取到3,否则第二个事务读取到的就是2了.
  
 ##### 处于READ_COMMITTED的时候:
  虽然 读已提交(READ_COMMI TTED)解决了脏读问题,却出现了不可重复读的问题,也就是在一个事务内,多次查询的数据结果不一致:
  第一个事务第一次读取了2,但是没有提交,第二个事务把2修改为了3,并提交了,那么第一个事务由于是READ_COMMITTED,所以就读到了3.
  那么第一个事务在它还没有提交的时候,2次查询的结果却不一样,这样就造成了不可重复读的问题.
  
  不可重复读也就是在一个事务内,多次读取到的数据不一致,但是首先要肯定的是,事务此时处于READ_COMMITTED隔离级别,所以它读取到的确是正常的数据.
  解决不可重复读的问题的方法是把隔离级别设置为REPEATABLE_READ(可重复读).
  
 ##### 处于REPEATABLE_READ的时候:
  前面说过,REPEATABLE_READ其实保证的是在自己的事务内不受影响,颇有些掩耳盗铃的思想,虽然我保证了我的事务内是可重复读的,但是如果别人对实际的数据做出了修改,你操作的时候还是可能会出问题,这就是幻读了:
  第一个事务在自己的事务内准备插入或更新或删除一条数据,第二个事务却抢先把这条要进行操作的记录给插入或更新或删除了,那么第一个事务的操作就会失败.第一个事务就感觉像出现了幻觉.
  
  值得一提的是我之前在测试的时候,第一个事务在进行更新操作的时候,另一个事务不能对相同条件的数据做出修改,只能等第一个事务提交后才能操作,这就是行锁.我当时测试的环境是mysql 8.xx
  
  那么解决幻读问题的方法当然只剩最后一种,就是从REPEATABLE_READ改为SERIALIZABLE了,但是我想应该是没人会做出这样的决定了,听名字就知道,串行化,当然只允许一个事务执行.
  所以暂时最高能使用的隔离级别就是REPEATABLE_READ了.
  但我想说的是,我的Mysql引擎为InnoDB,InnoDB支持行锁和表锁,而MyISAM的话就只支持表锁了,所以看各位同学的选择了.
  
  最后有一点:幻读和脏读的区别,可能会有同学迷惑这点.但我想说的是,如果真正理解这2个问题发生的场景,就会发现其实这2个问题毛关系都没有.
  脏读是读取到别的事务没有提交的数据,这种事务隔离级别是READ_UNCOMMITTED.但是幻读发生的隔离级别是REPEATABLE_READ,可重复读,
  是不会受别的事务的影响而错误数据的,可重复读虽然避免了读数据的不一致,但是它有可能会在增,改,删这种操作上出现错误,也就是说幻读侧重于对数据的
  修改等操作,而不是读操作,隔离级别都已经是REPEATABLE_READ了,正常是不会发生读问题的.
  
#### 事务的传播行为是什么?有哪些?
 首先需要明白一点:事务的传播行为Propagation是Spring独有的对事务增强的特性,不属于数据库的事务特性.
 
 事务的传播行为是描述 由事务传播行为修饰的方法被另一个方法调用,被事务传播行为修饰的方法的事务该如何传播.
  
 Spring定义了7种事务的传播行为:REQUIRED,SUPPORTS,MANDATORY,REQUIRES_NEW,NOT_SUPPORTED,NEVER,NESTED
 
 ##### REQUIRED:
 默认的事务传播行为是REQUIRED.
 如果负责调用的那个方法开启了事务,那么被传播行为修饰的方法就加入到那个事务,这时,2个方法的操作要么同时成功,要么同时失败.
 如果负责调用的那个方法没有开启事务,被传播行为修饰的方法会自己开启事务,与负责调用的那个方法互不干扰.
 
 ##### SUPPORTS:
 如果负责调用的那个方法开启了事务,那么被传播行为修饰的方法就加入到那个事务,否则就不以事务的方式运行.
  
 ##### MANDATORY:
 被传播行为修饰的方法必须在事务中运行,否则会抛出异常.
 
 ##### REQUIRES_NEW:
 无论负责调用的那个方法是否开启事务,被传播行为修饰的方法都会自己开启事务,与负责调用的那个方法互不干扰,事务之间相互隔离.
 
 ##### NOT_SUPPORTED:
 被传播行为修饰的方法不能以事务的方式运行,如果外围方法开启了事务,那么就把外围方法的事务挂起,先以非事务方式运行,运行完成后才会回复
 被挂起的事务.
  
 ##### NEVER:
 被传播行为修饰的方法不能以事务的方式运行,否则会抛出异常.
 
 ##### NESTED: 
 与REQUIRED相似,如果负责调用的那个方法没有开启事务,被传播行为修饰的方法会自己开启事务,与负责调用的那个方法互不干扰.
 但是,如果负责调用的那个方法开启了事务,那么被传播行为修饰的方法就作为那个事务的子事务运行,
 这时,外围事务如果失败,子事务也失败;如果子事务失败,但不影响外围事务.
 
   
  
 