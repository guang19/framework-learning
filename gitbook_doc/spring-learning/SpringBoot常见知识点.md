<!-- TOC -->

  * [SpringBoot常见知识点](#springboot常见知识点)
       * [什么是SpringBoot?](#什么是springboot)
       * [SpringBoot的优点](#springboot的优点)
       * [SpringBoot缺点](#springboot缺点)
       * [SpringBoot的核心注解是哪个?](#springboot的核心注解是哪个)
       * [Java API配置的好处](#java-api配置的好处)
       * [SpringBoot自动配置原理](#springboot自动配置原理)
       * [SpringBoot配置文件加载顺序](#springboot配置文件加载顺序)
       * [SpringBoot 怎么切换生产环境和开发环境配置](#springboot-怎么切换生产环境和开发环境配置)
       * [SpringBoot是如何推断应用类型和main的](#springboot是如何推断应用类型和main的)


<!-- /TOC -->


# SpringBoot常见知识点

#### 什么是SpringBoot?

SpringBoot是Spring开源组织Pivotal为Spring应用提供的一站式解决方案，
简化了Spring应用开发的流程，并提供了非常多的相关生态组件，非常强大。

#### SpringBoot的优点

- 容易上手，编码简单: 只要学过Spring/SpringMVC，那么就能很快的编写出一个Spring应用。

- 内嵌Web容器: SpringBoot无需配置外部Servlet容器就可以轻松的编写出Web应用。
  并且SpringBoot支持多种容器，如Tomcat，Undertow，jetty，netty等。

- 开箱即用，习惯大于配置: 即使你不做任何配置，也能够跑起来一个HelloWorld。

- 天然集成SpringCloud微服务

#### SpringBoot缺点

- 易学难精: 说SpringBoot开启了Java EE的新时代一点也不为过，但是它为我们带来这么多好处的同时，
我们想要真正了解他的全貌，已经是很难了。虽然上手容易，但是SpringBoot的特性之多，
恐怕需要很长时间了才能琢磨清楚。

#### SpringBoot的核心注解是哪个?
核心注解自然是启动SpringBoot应用的那个注解啦:@SpringBootApplication。

它由

````text
@SpringBootConfiguration(@Configuration)

@EnableAutoConfiguration

@ComponentScan 
````

3个核心注解组成。

- @SpringBootConfiguration等同于@Configuration,它声明一个类为配置类。

- @EnableAutoConfiguration是SpringBoot自动配置的核心注解，没有它，
SpringBoot的自动配置就不会生效

- @ComponentScan 容器组件扫描的注解，负责扫描所有的容器组件，也是最核心的注解之一。

#### Java API配置的好处

Spring应用有三种配置组件的方式:注解，Java API(Java Config)，XML.
其中注解和Java API的组合，完全可以干掉冗余的XML配置。

初学Spring的时候，见得最多的不是某行代码，而是XML配置啊。
有的同学恨不得要把Spring的约束文件给背下来了~_~。。

而@Configuration和@Bean的到来，无疑是开启了Spring应用配置方式的另一个时代。

**Java配置的最大好处就是配置灵活，编写简单，其次是修改方便。**
但是个人认为还是有缺点的，因为使用Java配置就意味着你需要创建很多的配置类，
如果把所有配置都写在一个类里面，那会变得很难维护，所以较好的选择是不同的配置，
创建不同的类，这样易于维护。

**XML配置的优点就是很通俗易懂，不然XML语言不可能成为通用的数据传输语言。**
但是想想一个应用里的配置何其多，这就是它最大的缺点:繁琐。

#### SpringBoot自动配置原理

**@EnableAutoConfiguration使得容器会读取类路径下 META-INF/spring.factories 文件，
并导入文件中声明的自动配置类。**
spring.factories里定义的都是扩展组件的自动配置类,
这些配置类往往都会依赖于XXXProperties.java的属性类，
我们在application.properties/yml文件里配置的属性，
就是配置的这些XXXProperties.java类的属性。

**SpringBoot自动配置体现的是SPI(Service Provider Interface)的思想，
包括JDK中都大量使用到了这种思想。关于SPI可见: [知乎-SPI](https://zhuanlan.zhihu.com/p/28909673)**

#### SpringBoot配置文件加载顺序
1. application.properties
2. application.yml
3. ...

#### SpringBoot 怎么切换生产环境和开发环境配置
因为springboot优先读取application.properties,
所以可以在application.properties中配置spring.profile.active = dev/pro/...
来进行配置文件的切换。

#### SpringBoot是如何推断应用类型和main的

在SpringBoot中，有一个WebApplicationType的枚举类定义了3种Web类型:

1. NONE
2. SERVLET(Servlet 类型)
3. REACTIVE(响应式类型)，

SpringBoot在启动时会根据对应类型的核心类是否存在，据此判断应用的类型。

而判断main方法是根据异常堆栈来判断的。