## Spring复习

 对于Spring的源码我不想一段一段的贴,毕竟Spring的代码体量是相当多了,不过我不贴不代表不看,相反必须看,因为Spring加载容器时的拓展点很多,有必要搞清楚这些拓展点的执行顺序.
 然后我整理下关于Spring的常见问题,这些问题的回答有我个人的理解,也有参考网上一些大神的文章.
--- 

 #### 什么是Spring Framework?
 Spring是一个轻量级,开源的Java应用开发框架.它使开发Java应用程序变得更加简单,并且使用它开发可以降低应用架构之间的耦合性.
 
 #### Spring Framework的好处?
 1. 应用程序模块之间的解耦
 2. 组件的自由度(Spring提供了各种不同类型的组件(aop,tx,dao,web等等)用户可以自由挑选需要组件与Spring无缝集成)
 3. 开源免费
 
 ...
 #### Spring Framework提供了哪些模块?
 1. IOC模块: core,bean,context,spel,aop...
 2. DAO模块: jdbc,orm,oxm,transaction,jms,tx...
 3. WEB模块: web,mvc,webflux,websocket...
 4. AOP模块: aop,aspectj....
 5. 测试模块: test
 
 #### Spring IOC容器的配置方式有哪些？
 1. xml (不再推荐使用)
 2. 注解(推荐使用)
 3. Java API(和注解一起使用)
 
 ####Spring 的IOC容器是什么?它有什么作用?常常提到的依赖注入和控制反转与IOC容器是什么关系?
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
  
  处于READ_UNCOMMITTED的时候:
  可能会发生脏读:2个事务同时对数据库进行查询操作,第一个事务先把要查询的值2改为了3,但是没有提交事务,第二个事务读取到了3并提交了事务,那么第二个事务读取到的3就是脏数据.
  
  脏读也就是读取到另一个事务未提交的数据,这种隔离级别的解决办法就是把READ_UNCOMMITTED改为READ_COMMITTED,这样,只有第一个事务提交了,第二个事务才会读取到3,否则第二个事务读取到的就是2了.
  
  处于READ_COMMITTED的时候:
  虽然 读已提交(READ_COMMITTED)解决了脏读问题,却出现了不可重复读的问题,也就是在一个事务内,多次查询的数据结果不一致:
  第一个事务第一次读取了2,但是没有提交,第二个事务把2修改为了3,并提交了,那么第一个事务由于是READ_COMMITTED,所以就读到了3.
  那么第一个事务在它还没有提交的时候,2次查询的结果却不一样,这样就造成了不可重复读的问题.
  
  不可重复读也就是在一个事务内,多次读取到的数据不一致,但是首先要肯定的是,事务此时处于READ_COMMITTED隔离级别,所以它读取到的确是正常的数据.
  解决不可重复读的问题的方法是把隔离级别设置为REPEATABLE_READ(可重复读).
  
  处于REPEATABLE_READ的时候:
  前面说过,REPEATABLE_READ其实保证的是在自己的事务内不受影响,颇有些掩耳盗铃的思想,虽然我保证了我的事务内是可重复读的,但是如果别人对实际的数据做出了修改,你操作的时候还是可能会出问题,这就是幻读了:
  第一个事务在自己的事务内准备插入或更新或删除一条数据,第二个事务却抢先把这条要进行操作的记录给插入或更新或删除了,那么第一个事务的操作就会失败.第一个事务就感觉像出现了幻觉.
  
  值得一提的是我之前在测试的时候,第一个事务在进行更新操作的时候,另一个事务不能对相同条件的数据做出修改,只能等第一个事务提交后才能操作,这就是行锁.我当时测试的环境是mysql 8.xx
  
  那么解决幻读问题的方法当然只剩最后一种,就是从REPEATABLE_READ改为SERIALIZABLE了,但是我想应该是没人会做出这样的决定了,听名字就知道,串行化,当然只允许一个事务执行.
  所以暂时最高能使用的隔离级别就是REPEATABLE_READ了.
  但我想说的是,我的Mysql引擎为InnoDB,InnoDB支持行锁和表锁,而MyISAM的话就只支持表锁了,所以看各位同学的选择了.
  
#### 事务的传播行为有哪些?
明天再更...
  
   
  
 