<!-- TOC -->

   * [Spring MVC常见知识点及源码解析](#spring-mvc常见知识点及源码解析)
        * [MVC 是什么 / 有什么优点?](#mvc-是什么--有什么优点)
        * [什么是 Spring MVC?](#什么是-spring-mvc)
        * [Spring MVC的优缺点?](#spring-mvc的优缺点)
        * [什么是DispatcherServlet?](#什么是dispatcherservlet)
        * [Spring MVC有哪些组件?(见:DispatcherServlet源码)](#spring-mvc有哪些组件见dispatcherservlet源码)
        * [简述SpringMVC原理/执行流程](#简述springmvc原理执行流程)
        * [Spring MVC  拦截器是什么 / 有什么作用 / 与 Filter有什么区别?](#spring-mvc--拦截器是什么--有什么作用--与-filter有什么区别)
        * [@Component @Controller @Service @Repository 区别?](#component-controller-service-repository-区别)

<!-- /TOC -->

# Spring MVC常见知识点及源码解析


#### MVC 是什么 / 有什么优点?

MVC是一种设计模式，遵循 模型(Model),视图(View) 和 控制器(Controller)的架构设计。
MVC的优点很明显: 应用层次分明，职责分明，使得系统的耦合性降低，并有利于系统的维护。


#### 什么是 Spring MVC?
Spring MVC是一个基于Spring框架的轻量级的MVC Web应用框架。

#### Spring MVC的优缺点?
优点：
1. 基于Spring，拥有Spring的所有优点
2. Spring MVC中的组件:角色分明，耦合性低，非常有利于应用的维护。
3. 支持多种视图技术:不仅支持JSP，还支持各种模板视图。
4. 功能特性强大:轻松的文件上传，数据校验与格式转换，异常处理，RESTful风格的API等等。
5. 支持前后端分离。

缺点:
1. Spring MVC是基于Spring的，这既是它的优点也是它的缺点，它必须与Spring一起使用，
个人认为这应该是它最大的一个限制了。


#### 什么是DispatcherServlet?

DispatcherServlet是Spring MVC的核心,
可以说SpringMVC就是这个DispatcherServlet。
它是一个Servlet，负责拦截所有的请求，并以调用各种组件来对请求进行分发并处理。

#### Spring MVC有哪些组件?(见:DispatcherServlet源码)
1. MultipartResolver: 核心组件之一，处理文件上传请求。MultipartResolver负责判断普通请求是否为文件上传请求，
并将普通请求(HttpServletRequest)解析为文件上传请求(MultipartHttpServletRequest)。

2. LocalResolver: 区域解析器。它主要被用于国际化的资源方面的解析。

3. ThemeResolver: 主题资源解析器。SpringMVC允许用户提供不同主题，主题就是一系列资源的集合使用这些主题可以提高用户体验。

4. ViewResolver: 核心组件之一，视图解析器。在Handler执行完请求后，ViewResolver将ModelAndView解析成物理视图，
并对物理视图进行model渲染。

5. HandlerMapping: 核心组件之一，请求处理器。根据用户的请求来匹配对应的Handler。

6. HandlerAdapter: 核心组件之一，Handler适配器。使用Handler处理请求，并返回处理后的视图(ModelAndView)。

7. HandlerExceptionResolver: 核心组件之一，异常处理解析器。在Handler执行请求的过程中可能出现异常，
HandlerExceptionResolver就负责处理Handler执行请求过程中的异常。

8. RequestToViewNameTranslator: 核心组件之一,请求到视图的转换器。根据Request设置最终的视图名,当Handler执行完请求后，它会将Request解析成视图名。

9. FlashMapManager: 核心组件之一，在请求进行重定向时，FlashMapManager用于保存请求中的参数。

#### 简述SpringMVC原理/执行流程

1. 用户发出的请求被DispatcherServlet拦截。

2. DispatcherServlet使用HandlerMapping根据请求匹配到相应的Handler。Handler实际上是一个(HandlerMethod)。

3. DispatcherServlet根据Handler适配合适的HandlerAdapter。

4. HandlerAdapter使用Handler执行请求，并返回ModelAndView。

5. 使用RequestToViewNameTranslator,HandlerExceptionResolver和ViewResolver等解析器，
解析并渲染ModelAndView，并处理相关异常信息。

6. 渲染后的结果反馈给用户。

#### Spring MVC  拦截器是什么 / 有什么作用 / 与 Filter有什么区别?

- HandlerInterceptor: Spring MVC拦截器是Spring MVC提供的对用户请求的目标资源做出拦截扩展的处理器。
它允许在目标方法执行前后以及View渲染后做出处理。

Servlet Filter: Filter是Servlet提供的过滤器，它会在目标方法执行前后做出拦截处理。

```text
要说Servlet Filter和HandlerInterceptor有啥区别，
个人认为除了它们提供的拦截时间不同，目的都是相同的，没啥区别。
```

#### @Component @Controller @Service @Repository 区别?

>@Component 声明一个类为IOC容器的组件，会被IOC容器管理。
>而@Controller,@Service和@Repository则拥有更细分的语义。

- **@Controller通常用于Web应用，被@Controller注解的类，应该作为一个处理请求的控制器。**

- **@Service则是声明一个类为Service类，处理业务逻辑。**

- **被@Repository注解的类，应该被用于处理与数据库交互和持久化相关的功能。**