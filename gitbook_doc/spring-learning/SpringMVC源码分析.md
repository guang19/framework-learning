# Spring的源码分析

在分析SpringMVC源码之前我想先回顾一下JavaWeb的知识.JavaWeb的核心是Servlet,一个Servlet对应一个URL,
每次一个Http请求访问,那么对应URL的Servlet就会调用service方法处理。

其实这里我是对SpringMVC的一个复习,所以我先说说就我目前SpringMVC的理解吧。
大家都知道SpringMVC是一个MVC框架,但它还是脱离不了Tomcat,Undertow,Jetty这样的Servlet容器,
因为SpringMVC的核心还是是Servlet。

在初学SpringMVC的时候,各位同学可能都在web.xml里配置过DispatcherServlet,可能当时都没有想过为什么要去配置这个类,
甚至把它拦截的url配置成/**,我当时确实也没有想过,不过后来在学习的时候,已经明白了为什么这样去做,
并且已经明白了SpringMVC的设计思想。

---

```text
前方高能
```

SpringMVC通过一个DispatcherServlet拦截所有请求,也就是url为 /** 。
通过拦截所有请求,在内部通过路由匹配的方式把请求转给对应Controller的某个RequestMapping处理。
这就是SpringMVC的基本工作流程,我们传统的JavaWeb是一个Servlet对应一个URL,
而DispatcherServlet是一个Servlet拦截全部URL,并做分发处理.
这也是SpringMVC的设计的精妙之处。

当然上面只是简单的一个流程,在这个过程中肯定有很多细节值得我们细细揣摩。


我是以SpringBoot搭建的调试环境,再加上已经知晓DispatcherServlet是核心,
几乎可以直接定位到这个类了,但是在这之前可以看看DispatcherServlet的父类:FrameworkServlet。
上面说过,Servlet是以service方法处理请求的,所以直接定位到FrameworkServlet的service方法:

````
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
        if (httpMethod != HttpMethod.PATCH && httpMethod != null) {
            super.service(request, response);
        } else {
          
            //就是你了,骚年
            this.processRequest(request, response);
        
        }
    }
````

一个明显的方法 processRequest就进入了眼帘,看看这个processRequest做了什么吧:

````
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ...
        try {

            //就是这个方法了
            this.doService(request, response);

        } catch (IOException | ServletException var16) {
            failureCause = var16;
            throw var16;
        } catch (Throwable var17) {
            failureCause = var17;
            throw new NestedServletException("Request processing failed", var17);
        } finally {
            this.resetContextHolders(request, previousLocaleContext, previousAttributes);
            if (requestAttributes != null) {
                requestAttributes.requestCompleted();
            }
            this.logResult(request, response, (Throwable)failureCause, asyncManager);
            this.publishRequestHandledEvent(request, response, startTime, (Throwable)failureCause);
        }

    }
````

可以看到一大坨代码都是try这个doService方法,而这个doService方法肯定是核心了,
但是这个doService方法在FrameworkServlet类中是个abstract方法,所以直接回到
DispatcherServlet找到doService方法：

````
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //...此处省略一大坨看不懂的代码
        try {
            //从这里开始分析
            this.doDispatch(request, response);

        } finally {
            if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted() && attributesSnapshot != null) {
                this.restoreAttributesAfterInclude(request, attributesSnapshot);
            }

        }

    }
````

已经找到了目标:doDispatch,听名字这个方法就是做事情的方法了,
所谓的做事情当然就是处理request了,如果各位同学学到现在,
肯定有一个意识:在SpringMVC框架中,所有的核心方法几乎都是do开头,
并且都有2个必要的参数:HttpServletRequest和HttpServletResponse.

但是DispatcherServlet是我接触的所有框架依赖不那么扯的,
就是这个doDispatch方法,它里面几乎包含了我们即将要学习的所有知识了,
所以接下来的核心就是doDispatch方法,各位同学在做源码阅读的时候,建议一行也不要放过(实在看不懂就别为难自己了^-^)。

不过在看DispatcherServlet源码之前,我们最后看下这个DispatcherServlet究竟何德何能,
能够这么厉害,可以完成我们几乎所有的需求方法:

````
public class DispatcherServlet extends FrameworkServlet {
    ....
    @Nullable
    private MultipartResolver multipartResolver;
    @Nullable
    private LocaleResolver localeResolver;
    @Nullable
    private ThemeResolver themeResolver;
    @Nullable
    private List<HandlerMapping> handlerMappings;
    @Nullable
    private List<HandlerAdapter> handlerAdapters;
    @Nullable
    private List<HandlerExceptionResolver> handlerExceptionResolvers;
    @Nullable
    private RequestToViewNameTranslator viewNameTranslator;
    @Nullable
    private FlashMapManager flashMapManager;
    @Nullable
    private List<ViewResolver> viewResolvers;
    ....
   }
````

这就是DispatcherServlet的九大组件,正是这九大组件,
DispatcherServlet才能在支持请求和相应的同时,对许多功能和细节做出完善。

再看看doDispatch方法的源码,其实都是上面组件之间的配合完成任务:

````
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;

        //核心对象:HanderExecutorChain
        HandlerExecutionChain mappedHandler = null;

        boolean multipartRequestParsed = false;

        //管理异步请求
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

        try {
            try {
                ModelAndView mv = null;
                Object dispatchException = null;

                try {
                    //检查请求是否存在文件上传
                    processedRequest = this.checkMultipart(request);
                    multipartRequestParsed = processedRequest != request;
                
                    //getHandler方法是根据请求获取对应的Controller和方法
                    mappedHandler = this.getHandler(processedRequest);

                    if (mappedHandler == null) {

                        //如果没有获取到匹配的handler,就要么抛出异常要么设置响应码404
                        this.noHandlerFound(processedRequest, response);
                        return;
                    }

                    //通过handler获取HandlerAdapter,由HandlerAdapter完成方法的执行
                    HandlerAdapter ha = this.getHandlerAdapter(mappedHandler.getHandler());

                    String method = request.getMethod();
                    boolean isGet = "GET".equals(method);
                    if (isGet || "HEAD".equals(method)) {
                        long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                        if ((new ServletWebRequest(request, response)).checkNotModified(lastModified) && isGet) {
                            return;
                        }
                    }

                    //执行拦截器的preHandle方法,它内部如果执行不成功就会先执行afterCompletion,然后返回false,然后程序就退出
                    //这也是为什么拦截器的前置方法为什么返回false,程序就不会执行我们的逻辑了,等下会分析源码
                    if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                        return;
                    }

                    //执行方法
                    mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

                    if (asyncManager.isConcurrentHandlingStarted()) {
                        return;
                    }

                    //根据Request使用RequestToViewNameTranslator设置默认的视图
                    this.applyDefaultViewName(processedRequest, mv);

                  //倒序执行拦截器的postHandle方法    
                    mappedHandler.applyPostHandle(processedRequest, response, mv);

                } catch (Exception var20) {
                    dispatchException = var20;
                } catch (Throwable var21) {
                    dispatchException = new NestedServletException("Handler dispatch failed", var21);
                }

                //解析并渲染ModelAndView
                this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
          
            } catch (Exception var22) {

                //发生异常会倒序执行afterCompletion方法
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, var22);

            } catch (Throwable var23) {

              //发生异常会倒序执行afterCompletion方法
                this.triggerAfterCompletion(processedRequest, response, mappedHandler, new NestedServletException("Handler processing failed", var23));
         
            }
        } finally {
            if (asyncManager.isConcurrentHandlingStarted()) {
                if (mappedHandler != null) {
                    mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
                }
            } else if (multipartRequestParsed) {
                this.cleanupMultipart(processedRequest);
            }

        }
    }
````

首先看看MultiPartResolver是如何检查文件上传请求的吧(checkMultipart方法):

````
    protected HttpServletRequest checkMultipart(HttpServletRequest request) throws MultipartException {
       
        //关键在isMultpart方法
        if (this.multipartResolver != null && this.multipartResolver.  isMultipart(request)  ) {

            if (WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class) != null) {
                if (request.getDispatcherType().equals(DispatcherType.REQUEST)) {
                    this.logger.trace("Request already resolved to MultipartHttpServletRequest, e.g. by MultipartFilter");
                }
            } else if (this.hasMultipartException(request)) {
                this.logger.debug("Multipart resolution previously failed for current request - skipping re-resolution for undisturbed error rendering");
            } else {
                try {
                   
                    //执行到这里就说明HttpServletRequest是一个文件上传请求,那么就把HttpServletRequest包装成
                    //MultpartHttpServletRequest返回
                    return this.multipartResolver.resolveMultipart(request);
               
                } catch (MultipartException var3) {
                    if (request.getAttribute("javax.servlet.error.exception") == null) {
                        throw var3;
                    }
                }

                this.logger.debug("Multipart resolution failed for error dispatch", var3);
            }
        }

        return request;
    }
````

先看看isMultiPart是怎么判断请求是否为文件上传请求的吧(isMultipart方法)：

````
  public boolean isMultipart(HttpServletRequest request) {
        //判断Http请求包头的Content-Type
        return StringUtils.startsWithIgnoreCase(request.getContentType(), "multipart/");
    }
````

着重看看MultipartResolver是如何把普通请求包装成MultipartHttpServletRequest的吧(resolveMultipart方法):

````
  public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        //原来我们处理文件上传时拿到的请求是这个请求
        return new StandardMultipartHttpServletRequest(request, this.resolveLazily);
    }
````
从上面代码分析出,文件上传时,拿到的请求被包装成了StandardMultipartHttpServletRequest,
但是我不再深入,因为MultipartResolver是DispatcherServlet的一个
组件而已,再深入,就跑题了...也不是说跑题,毕竟都是SpringMVC的组成,
只是相信各位同学看到这里已经有能力去看看这个源码了,
可以明确的告诉各位这个地方不存在什么封装之类的曲折,单纯的就是
StandardMultipartHttpServletRequest内部对请求做了解析,并使用集合把解析的结果存储起来了而已 .

回到doDispatch方法,在执行完checkMultipart方法后,就通过getHandler方法,
获取了mappedHandler(HandlerExecutionChain)对象,这个HandlerExecutionChain是什么,
为什么getHandler返回它?先看看HandlerExecutionChain类的源码吧:

````
public class HandlerExecutionChain {
    private static final Log logger = LogFactory.getLog(HandlerExecutionChain.class);

    //handler,至于为什么设计成Object类型,我想可能是handler是执行method的关键,所以隐藏了细节,不对外暴露真实类型.
    //也有可能是在适配Controller的method的过程中需要多种类型的转换,总之不管哪种理由设计成Object类型,只有好好研究了.
    private final Object handler;

    @Nullable
    //一堆 HandlerInterceptor
    private HandlerInterceptor[] interceptors;

    @Nullable
    private List<HandlerInterceptor> interceptorList;
    private int interceptorIndex;

    public HandlerExecutionChain(Object handler) {
        this(handler, (HandlerInterceptor[])null);
    }
    ...
  }
````
**这个HandlerExecutionChain包含了handler和它的所有HandleInterceptor.**

再看看DispatcherServlet是如何根据HttpServletRequest获取HandlerExecutionChain的吧(getHandler方法):

````
    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (this.handlerMappings != null) {

            //之前说过HandlerMapping是DispatcherServlet的9大组件之一,所以这里它遍历HandlerMapping来获取
            Iterator var2 = this.handlerMappings.iterator();

            while(var2.hasNext()) {
                HandlerMapping mapping = (HandlerMapping)var2.next();
              
                //调用HandlerMapping的getHandler方法来获取HandlerExecutionChain
                HandlerExecutionChain handler = mapping.getHandler(request);

                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }
````

继续看看HandlerMapping的getHandler怎么实现的:

````
    public final HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        //首先就获取Handler
        Object handler = this.getHandlerInternal(request);

        if (handler == null) {
            handler = this.getDefaultHandler();
        }

        if (handler == null) {
            return null;
        } else {
            if (handler instanceof String) {
                String handlerName = (String)handler;
                handler = this.obtainApplicationContext().getBean(handlerName);
            }
        
            //这一步就构造了HandlerExecutionChain
            HandlerExecutionChain executionChain = this.getHandlerExecutionChain(handler, request);

            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Mapped to " + handler);
            } else if (this.logger.isDebugEnabled() && !request.getDispatcherType().equals(DispatcherType.ASYNC)) {
                this.logger.debug("Mapped to " + executionChain.getHandler());
            }

            if (this.hasCorsConfigurationSource(handler)) {
                CorsConfiguration config = this.corsConfigurationSource != null ? this.corsConfigurationSource.getCorsConfiguration(request) : null;
                CorsConfiguration handlerConfig = this.getCorsConfiguration(handler, request);
                config = config != null ? config.combine(handlerConfig) : handlerConfig;
                executionChain = this.getCorsHandlerExecutionChain(request, executionChain, config);
            }

            return executionChain;
        }
    }
````
在说上面一段代码之前,先回顾下,
之前介绍了HandlerExecutionChain由Handler和HandlerInterceptor组成,
那么我们就需要在getHandler里找到这2个关键的地方.
而第一行的getHandlerInternal方法就获取到了Handler,
后面又通过getHandlerExecutionChain直接构造出来了HandlerExecutionChain,
所以可以肯定:getHandlerInternal是根据URL映射找到了Handler
getHandlerExecutionChain通过HandlerInterceptor和Handler构造了HandlerExecutionChain对象,
至于我为什么这么肯定,
自然是因为我已经阅读过了源码啦...这里留个空子,希望有缘看到这里的同学能够自己看看实现的细节.

回到doDispatch方法,在获取到handler(HandlerExecutionChain)后,
紧接着判断获取的handler是否为空,如果为空,说明url不对,
没有匹配的handler,就会调用noHandlerFound方法处理.

接下来 DispatcherServlet调用了getHandlerAdapter方法获取了又一个9大组件,
开始的时候介绍过:HandlerMapping是通过Request获取匹配的handler(HandlerExecutionChain)对象,
而HandlerAdapter才是指挥handler干活的,
所以这一步相当重要,看看这个getHandlerAdapter是如何获 HandlerAdapter的吧：

`````
    protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
        if (this.handlerAdapters != null) {
            Iterator var2 = this.handlerAdapters.iterator();

            while(var2.hasNext()) {
                HandlerAdapter adapter = (HandlerAdapter)var2.next();
                //判断HandlerAdapter是否支持handler
                if (adapter.supports(handler)) {
                    return adapter;
                }
            }
        }

        throw new ServletException("No adapter for handler [" + handler + "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
    }
`````

可以看到getHandlerAdapter方法遍历DispatcherServlet的HandlerAdapter集合,
并且调用 HandlerAdapter 的 supports方法来选择合适的
HandlerAdapter,看看 supports 方法具体实现:

`````
   public final boolean supports(Object handler) {
        //这里的主要是第一个判断: handler instanceof HandlerMethod
        return handler instanceof HandlerMethod && this.supportsInternal((HandlerMethod)handler);
    }

    //鉴于方便,我直接把supportsInternal方法贴过来
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return true;
    }
`````
可以看到supportsInternal方法对于HandlerMethod类型的参数直接返回true,当然这只是一个HandlerAdapter的实现,但也足够说明问题了,最重要的是第一个判断:

````
handler instanceof HandlerMethod 
````

我们之前说过handler在HandlerExecutionChain内部是一个Object类型,
到了这里为什么就变成了HandlerMethod类型了。
其实早在getHandler那一步就变成了Handler,
到这一步只是适配确认一下.那来看看HandlerMethod是什么吧,
它为什么可以执行我们的RequestMapping方法呢?

````
public class HandlerMethod {
    ...
    //bean既可以作为Controller,也可以作为Controller的name
    private final Object bean;
   
    //Controller所在的容器
    private final BeanFactory beanFactory;
   
    //Controller的 Class
    private final Class<?> beanType;
  
    //关键之处:requestmapping 对应的方法
    private final Method method;
   
    //桥接方法,我google了下,他是起兼容作用的,应该是与method有互补作用
    //这是那位大神的分析:[bregedmethod]( https://www.cnblogs.com/guangshan/p/4661305.html )
    private final Method bridgedMethod;
  
    //方法的参数
    private final MethodParameter[] parameters;
   
    //Http状态码
    private HttpStatus responseStatus;

    //不得不说Spring真是流比,像我等之流,只能仰望,返回状态码还得给个原因
    private String responseStatusReason;

    //保留的一份HandlerMethod,这个是解析当前HttpMethod实例的那个HttpMethod
    private HandlerMethod resolvedFromHandlerMethod;
    ....  
   }
````
其实到这里基本就知道SpringMVC到底是怎么通过反射执行我们的方法了,
还是不断的封装和反射,对于这些框架来说,万物皆可封装,
你不服就封装的你服,不管你服不服,我是服了.

再回到doDispatcher方法吧,这个时候已经获取到了需要的handler了,
那么就该执行拦截器的preHandle方法了吧,看接下来的applyPreHandle方法:

````
   boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerInterceptor[] interceptors = this.getInterceptors();
        if (!ObjectUtils.isEmpty(interceptors)) {
            for(int i = 0; i < interceptors.length; this.interceptorIndex = i++) {
                HandlerInterceptor interceptor = interceptors[i];

                //执行拦截器的preHandle方法,如果preHandle方法返回false,那么直接执行所有拦截器的afterCompletion方法
                if (!interceptor.preHandle(request, response, this.handler)) {
                    this.triggerAfterCompletion(request, response, (Exception)null);
                    return false;
                }
            }
        }
        return true;
    }

````

再回到doDispatcher方法,执行完拦截器的preHandle方法后
,就直接调用HandlerAdapter的handle方法了：

````
  public final ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //调用了handleInternal方法
        return this.handleInternal(request, response, (HandlerMethod)handler);
    }
  
  //handleInteral方法
    protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        //检查是否支持请求的类型和是否需要session
        this.checkRequest(request);
       
        ModelAndView mav;
        if (this.synchronizeOnSession) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Object mutex = WebUtils.getSessionMutex(session);
                synchronized(mutex) {
                    //终于执行目标方法了 ~-~
                    mav = this.invokeHandlerMethod(request, response, handlerMethod);
                }
            } else {
                mav = this.invokeHandlerMethod(request, response, handlerMethod);
            }
        } else {
            mav = this.invokeHandlerMethod(request, response, handlerMethod);
        }

        if (!response.containsHeader("Cache-Control")) {
            if (this.getSessionAttributesHandler(handlerMethod).hasSessionAttributes()) {
                this.applyCacheSeconds(response, this.cacheSecondsForSessionAttributeHandlers);
            } else {
                this.prepareResponse(response);
            }
        }
        return mav;
    }
````

直接看 invokeHandlerMethod ,到底是如何执行method的:

````
    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        ModelAndView var15;
        try {
            WebDataBinderFactory binderFactory = this.getDataBinderFactory(handlerMethod);
            ModelFactory modelFactory = this.getModelFactory(handlerMethod, binderFactory);
            ServletInvocableHandlerMethod invocableMethod = this.createInvocableHandlerMethod(handlerMethod);
            if (this.argumentResolvers != null) {
                invocableMethod.setHandlerMethodArgumentResolvers(this.argumentResolvers);
            }

            if (this.returnValueHandlers != null) {
                invocableMethod.setHandlerMethodReturnValueHandlers(this.returnValueHandlers);
            }

            invocableMethod.setDataBinderFactory(binderFactory);
            invocableMethod.setParameterNameDiscoverer(this.parameterNameDiscoverer);
            ModelAndViewContainer mavContainer = new ModelAndViewContainer();
            mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
            modelFactory.initModel(webRequest, mavContainer, invocableMethod);
            mavContainer.setIgnoreDefaultModelOnRedirect(this.ignoreDefaultModelOnRedirect);
            AsyncWebRequest asyncWebRequest = WebAsyncUtils.createAsyncWebRequest(request, response);
            asyncWebRequest.setTimeout(this.asyncRequestTimeout);
            WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
            asyncManager.setTaskExecutor(this.taskExecutor);
            asyncManager.setAsyncWebRequest(asyncWebRequest);
            asyncManager.registerCallableInterceptors(this.callableInterceptors);
            asyncManager.registerDeferredResultInterceptors(this.deferredResultInterceptors);
            Object result;
            if (asyncManager.hasConcurrentResult()) {
                result = asyncManager.getConcurrentResult();
                mavContainer = (ModelAndViewContainer)asyncManager.getConcurrentResultContext()[0];
                asyncManager.clearConcurrentResult();
                LogFormatUtils.traceDebug(this.logger, (traceOn) -> {
                    String formatted = LogFormatUtils.formatValue(result, !traceOn);
                    return "Resume with async result [" + formatted + "]";
                });
                invocableMethod = invocableMethod.wrapConcurrentResult(result);
            }

            invocableMethod.invokeAndHandle(webRequest, mavContainer, new Object[0]);
            if (asyncManager.isConcurrentHandlingStarted()) {
                result = null;
                return (ModelAndView)result;
            }

            var15 = this.getModelAndView(mavContainer, modelFactory, webRequest);
        } finally {
            webRequest.requestCompleted();
        }

        return var15;
    }
````

上面这个代码...我哭了....^^^----^^^------^^^,总之我就看到了关于数据绑定,
SpringMVC的异步核心管理器,ModelAndViewContainer(看名字就知道,容纳了当前handler的很多数据,恩...)
其实吧,我没哭,只是确实以我的功力,
还是有很多知识没有理解...好吧,我差点哭了.....(说好的轻量级框架呢?)


继续看doDispatcher,逻辑方法执行完了,
RequestToViewNameTranslator 就会调用 applyDefaultViewName 方法设置默认的视图.
然后就会执行拦截器的 postHandle方法了:

````
    void applyPostHandle(HttpServletRequest request, HttpServletResponse response, @Nullable ModelAndView mv) throws Exception {
        HandlerInterceptor[] interceptors = this.getInterceptors();
        if (!ObjectUtils.isEmpty(interceptors)) {
            for(int i = interceptors.length - 1; i >= 0; --i) {
                HandlerInterceptor interceptor = interceptors[i];

                //执行postHandle方法
                interceptor.postHandle(request, response, this.handler, mv);
            }
        }

    }
````

最后,会通过processDispatchResult方法处理最终的结果:

````
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response, @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv, @Nullable Exception exception) throws Exception {
        boolean errorView = false;
        if (exception != null) {
            if (exception instanceof ModelAndViewDefiningException) {
                this.logger.debug("ModelAndViewDefiningException encountered", exception);
                mv = ((ModelAndViewDefiningException)exception).getModelAndView();
            } else {
                Object handler = mappedHandler != null ? mappedHandler.getHandler() : null;
                //通过HandlerExceptionResolver处理异常
                mv = this.processHandlerException(request, response, handler, exception);
                errorView = mv != null;
            }
        }

        if (mv != null && !mv.wasCleared()) {

            //通过View的render方法渲染视图
            this.render(mv, request, response);
            if (errorView) {
                WebUtils.clearErrorRequestAttributes(request);
            }
        } else if (this.logger.isTraceEnabled()) {
            this.logger.trace("No view rendering, null ModelAndView returned.");
        }

        if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            if (mappedHandler != null) {
                mappedHandler.triggerAfterCompletion(request, response, (Exception)null);
            }

        }
    }
````


这样一份源码也算是勉勉强强过了一遍,其实我发现从这个项目的第一份源码分析起到现在,
其中的一些知识不懂不是我笨,其实顺藤蘑摸瓜倒也好寻到一些线索。
只是像这样的框架我发现我根本没有去了解它的全貌.

我说一个现象:可能有很多同学在分析一份源码的时候,
不知道从哪就蹦出来一个比较陌生的对象,你知道它大概是干什么的,
与哪些你认识的类有关,但是你就是不知道它哪来了,久而久之,就会对这些框架形成敬畏感。
其实我确实对这些框架有敬畏感,毕竟从代码量和各种设计来说,就不得不有敬畏感,
到了他们这个体量,也很难再通过HelloWorld去了解他们的全貌了。

简单总结下SpringMVC工作的流程:

**SpringMVC通过DispatcherServlet拦截所有的请求,
并通过HandlerMapping与指定的请求找出匹配的handler,
handler实际是HandlerMethod对象。
再通过与handler适配的HandlerAdapter执行目标方法,
执行完目标方法后会返回ModelAndView对象,
最后通过ViewResolver解析ModelAndView的View视图。**