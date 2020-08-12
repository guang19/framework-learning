<!-- TOC -->
   
   * [Mybatis源码分析](#mybatis源码分析)
      * [1. 解析配置文件，创建SQLSessionFactory](#1-解析配置文件创建sqlsessionfactory)
      * [2. 开启java程序和数据库之间的会话：](#2-开启java程序和数据库之间的会话)
      * [3. 获取mapper代理对象:](#3-获取mapper代理对象)
      * [4. 执行mapper接口方法:](#4-执行mapper接口方法)   
      * [mybatis源码总结](#mybatis源码总结)

<!-- /TOC -->

# Mybatis源码分析

#### 1. 解析配置文件，创建SQLSessionFactory

````
InputStream inputStream = CommonTest.class.getClassLoader().getResourceAsStream("mybatis-configuration.xml");
SQLSessionFactory SQLSessionFactory =
                new SQLSessionFactoryBuilder().build(inputStream);
````

这一步首先读取了mybatis的configuration xml配置文件,用这个流构造了Factory的Builder,它底层是使用Mybatis自己的XMLConfigBuilder解析器去解析了这个Configuration文件,
然后调用了解析器的parse方法,SQLSessionFactory就被构造出来了:

````
   public SQLSessionFactory build(InputStream inputStream, String environment, Properties properties) {
        SQLSessionFactory var5;
        try {
          
            //文件解析器
            XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
           
            //构造SQLSessionFactory
            var5 = this.build(parser.parse());
     
        } catch (Exception var14) {
            throw ExceptionFactory.wrapException("Error building SQLSession.", var14);
        } finally {
            ErrorContext.instance().reset();

            try {
                inputStream.close();
            } catch (IOException var13) {
            }

        }

        return var5;
    }
````

根据上面代码可知,SQLSessionFactory被创建的核心是 XMLConfigBuilder的 parse方法,
也就是解析文件的那个步骤,它又是怎么解析的呢?

初次学Mybatis的时候,配置的那个Configuration全局文件里有很多属性对吧,各种节点,
environment,mapper,setting...的,可想它内部肯定是对这些节点做了解析的:

````
    private void parseConfiguration(XNode root) {
        try {
            this.propertiesElement(root.evalNode("properties"));
            Properties settings = this.settingsAsProperties(root.evalNode("settings"));
            
            //解析全局配置文件的各个节点,并加载配置
            this.loadCustomVfs(settings);
            this.loadCustomLogImpl(settings);
            this.typeAliasesElement(root.evalNode("typeAliases"));
            this.pluginElement(root.evalNode("plugins"));
            this.objectFactoryElement(root.evalNode("objectFactory"));
            this.objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
            this.reflectorFactoryElement(root.evalNode("reflectorFactory"));
            this.settingsElement(settings);
            this.environmentsElement(root.evalNode("environments"));
            this.databaseIdProviderElement(root.evalNode("databaseIdProvider"));
            this.typeHandlerElement(root.evalNode("typeHandlers"));
        
            //解析mapper的各个元素(SQL,resultmap......)    
            this.mapperElement(root.evalNode("mappers"));
        } catch (Exception var3) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
        }
    }
````

从上面代码可以分析,其实解析是大概分为2个部分的,一个是解析全局配置文件里的属性,
一个是解析mapper文件的各个属性,对已经学过mybatis的同学来说,
都知道mybatis底层是使用了动态代理模式来操作接口方法的,那么从第二个部分:解析mapper的部分就尤为重要了.

先看第一个部分,其实其第一个部分最重要的一点就是分析出了,mybatis所有的属性,
配置全部由一个Configuration对象保存了起来,随便抽一个方法:

````
    private void settingsElement(Properties props) {
        this.configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(props.getProperty("autoMappingBehavior", "PARTIAL")));
        this.configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.valueOf(props.getProperty("autoMappingUnknownColumnBehavior", "NONE")));
        this.configuration.setCacheEnabled(this.booleanValueOf(props.getProperty("cacheEnabled"), true));
        this.configuration.setProxyFactory((ProxyFactory)this.createInstance(props.getProperty("proxyFactory")));
        this.configuration.setLazyLoadingEnabled(this.booleanValueOf(props.getProperty("lazyLoadingEnabled"), false));
        this.configuration.setAggressiveLazyLoading(this.booleanValueOf(props.getProperty("aggressiveLazyLoading"), false));
        this.configuration.setMultipleResultSetsEnabled(this.booleanValueOf(props.getProperty("multipleResultSetsEnabled"), true));
        this.configuration.setUseColumnLabel(this.booleanValueOf(props.getProperty("useColumnLabel"), true));
        this.configuration.setUseGeneratedKeys(this.booleanValueOf(props.getProperty("useGeneratedKeys"), false));
        ......
       
    }
````

再看看Configuration类的属性,就印证了之前说过的,
Configuration就是一个贯穿的mybatis整个生命周期的核心配置类:

````
public class Configuration {
    protected Environment environment;
    protected boolean safeRowBoundsEnabled;
    protected boolean safeResultHandlerEnabled;
    protected boolean mapUnderscoreToCamelCase;
    protected boolean aggressiveLazyLoading;
    protected boolean multipleResultSetsEnabled;
    protected boolean useGeneratedKeys;
    protected boolean useColumnLabel;
    protected boolean cacheEnabled;
    protected boolean callSettersOnNulls;
    protected boolean useActualParamName;
    protected boolean returnInstanceForEmptyRow;
    protected String logPrefix;
    protected Class<? extends Log> logImpl;
    protected Class<? extends VFS> vfsImpl;
    protected LocalCacheScope localCacheScope;
    protected JdbcType jdbcTypeForNull;
    protected Set<String> lazyLoadTriggerMethods;
   ......
   ......
  }
````

之前说过,XMLConfigBuilder负责解析mybatis全局配置文件,而解析阶段又大致分为2个阶段:解析基本属性;
解析mapper文件.解析的基本属性都存放在了全局的Configuration之中.
再看mapperElement方法,也就是开始解析的那个方法,可以直接锁定XMLMapperBuilder类,
看这个类的名字就知道它是解析mapper文件的:

````
    private void mapperElement(XNode parent) throws Exception {
        if (parent != null) {
            Iterator var2 = parent.getChildren().iterator();

            while(true) {
                while(var2.hasNext()) {
                    XNode child = (XNode)var2.next();
                    String resource;
                    if ("package".equals(child.getName())) {
                        resource = child.getStringAttribute("name");
                        this.configuration.addMappers(resource);
                    } else {
                        resource = child.getStringAttribute("resource");
                        String url = child.getStringAttribute("url");
                        String mapperClass = child.getStringAttribute("class");
                        XMLMapperBuilder mapperParser;
                        InputStream inputStream;
                        if (resource != null && url == null && mapperClass == null) {
                            ErrorContext.instance().resource(resource);
                            inputStream = Resources.getResourceAsStream(resource);

                            //开始解析mapper文件
                            mapperParser = new XMLMapperBuilder(inputStream, this.configuration, resource, this.configuration.getSQLFragments());
                            mapperParser.parse();
                        
                           } else if (resource == null && url != null && mapperClass == null) {
                            ErrorContext.instance().resource(url);
                            inputStream = Resources.getUrlAsStream(url);
                            mapperParser = new XMLMapperBuilder(inputStream, this.configuration, url, this.configuration.getSQLFragments());
                            mapperParser.parse();
                        } else {
                            if (resource != null || url != null || mapperClass == null) {
                                throw new BuilderException("A mapper element may only specify a url, resource or class, but not more than one.");
                            }

                            Class<?> mapperInterface = Resources.classForName(mapperClass);
                            this.configuration.addMapper(mapperInterface);
                        }
                    }
                }

                return;
            }
        }
    }
````

在说解析mapper文件之前,先想想mapper文件里有什么,最核心的就那几个:resultMap,statement(也就是SQL),
cache缓存,那么XMLMapperBuilder肯定也是围绕那个几个去解析的,或者还解析了其他的东西:

````
    public void parse() {
        if (!this.configuration.isResourceLoaded(this.resource)) {

            //这里是解析Mapper文件的核心,它内部把Mapper的select,delete,update,insert这些
            //标签添加到了MapperStatement内,也就是SQL语句
            this.configurationElement(this.parser.evalNode("/mapper"));
           
            this.configuration.addLoadedResource(this.resource);

            //这一步也是非常重要的,它绑定了mapper文件的命名空间
            this.bindMapperForNamespace();
        }

        //解析还没有解析的各个元素
        this.parsePendingResultMaps();
        this.parsePendingCacheRefs();
        this.parsePendingStatements();
    }
````
其实mybatis解析mapper文件的步骤还是比较深入的,这里由于篇幅关系,怕说长了,就脱离此文的目的,还是以理解为主,直到mybatis做了哪些事情就行,但还是需要去看看mybatis到底是如何解析mapp文件的,也就是
上面的:
````
this.configurationElement(this.parser.evalNode("/mapper"));
......
````

最后,回到解析Configuration文件的起点,解析完文件后,SQLSessionFactory就被build方法构造出来了,其实他是个DefaultSQLSessionFactory:
````
   public SQLSessionFactory build(Configuration config) {
        return new DefaultSQLSessionFactory(config);
    }
````

总结下SQLSessionFactory被创建的过程: 
首先Mybatis使用XMLConfigBuilder文件解析器,解析全局配置文件,XMLMapperBuilder,解析mapper文件,解析完后将所有的属性封装在了Configuration对象中,然后使用这个全局的Configuration对象构造了DefaultSQLSessionFactory.


#### 2. 开启java程序和数据库之间的会话：
````
   SQLSession SQLSession = SQLSessionFactory.openSession();
````
从第一步创建SQLSessionFactory的过程可知,SQLSessionFactory是一个DefaultSQLSessionFactory,所以openSession也是调用了DefaultSQLSessionFactory的openSession:
`````
   public SQLSession openSession() {
        return this.openSessionFromDataSource(this.configuration.getDefaultExecutorType(), (TransactionIsolationLevel)null, false);
    }
`````

可以看到openSession是调用了openSessionFromDataSource方法,那它是怎么实现的呢:

````
    private SQLSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit) {
        Transaction tx = null;

        DefaultSQLSession var8;
        try {
            Environment environment = this.configuration.getEnvironment();

            //事物管理
            TransactionFactory transactionFactory = this.getTransactionFactoryFromEnvironment(environment);
            tx = transactionFactory.newTransaction(environment.getDataSource(), level, autoCommit);

            //核心Executor执行器
            Executor executor = this.configuration.newExecutor(tx, execType);
            
            //SQLSession就是DefaultSQLSession
            var8 = new DefaultSQLSession(this.configuration, executor, autoCommit);
        } catch (Exception var12) {
            this.closeTransaction(tx);
            throw ExceptionFactory.wrapException("Error opening session.  Cause: " + var12, var12);
        } finally {
            ErrorContext.instance().reset();
        }
````

从上面代码可以看出,SQLSession是DefaultSQLSession,然后还创建了一个Executor这个核心的执行器对象,那么首先看看这个Executor是什么,为什么说它是核心呢？
首先看看它内部的方法吧:
````
public interface Executor {
    ResultHandler NO_RESULT_HANDLER = null;

    int update(MappedStatement var1, Object var2) throws SQLException;

    <E> List<E> query(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4, CacheKey var5, BoundSQL var6) throws SQLException;

    <E> List<E> query(MappedStatement var1, Object var2, RowBounds var3, ResultHandler var4) throws SQLException;

    <E> Cursor<E> queryCursor(MappedStatement var1, Object var2, RowBounds var3) throws SQLException;

    List<BatchResult> flushStatements() throws SQLException;

    void commit(boolean var1) throws SQLException;

    void rollback(boolean var1) throws SQLException;
    ...
    ...
}
````

从Executor内部方法可以看出,它负责执行Statement的执行操作.既然它是一个接口,那么必然有对应的实现类,这里先看configuration的newExecutor方法是怎么创建它的吧:

````
    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        executorType = executorType == null ? this.defaultExecutorType : executorType;
        executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
        Object executor;

        //批处理的Executor
        if (ExecutorType.BATCH == executorType) {
            executor = new BatchExecutor(this, transaction);
        }
        //可复用的Executor
        else if (ExecutorType.REUSE == executorType) {
            executor = new ReuseExecutor(this, transaction);
        } 
        //简单的Executor,如果不做配置,那么就默认是它了
        else {
            executor = new SimpleExecutor(this, transaction);
        }
        
        //缓存Executor,这里就是二级缓存的关键之处,把已经创建好的Executor,装成CachingExecutor
        if (this.cacheEnabled) {
            executor = new CachingExecutor((Executor)executor);
        }
        //插件拓展,原来插件是在创建Executor的时候被封装的.
        Executor executor = (Executor)this.interceptorChain.pluginAll(executor);
        return executor;
    }
````

从上面代码就可以分析出:创建SQLSession的时机其实是创建Executor的时机,也是封装plugin的时机,
也可以猜测Executor就是Mybatis的核心组件之一,负责执行一系列的SQL(Statement).

总结下第二步获取SQLSession的过程:
使用DefaultSQLSessionFactory的Configuration创建出对应类型的Executor,
并封装配置中的插件,再使用Executor和Configuration创建DefaultSQLSession,
由此可见Configuration从被构建出来,流转到了DefaultSQLSession之中.

#### 3. 获取mapper代理对象:
````
        PersonMapper personMapper = SQLSession.getMapper(PersonMapper.class);
````

已经知到了上面返回的PersonMapper是一个MapperProxy对象,那么它是怎么被创建出来的呢?
回想下上面的几个步骤,DefaultSQLSession包含了Configuration,而Configuration是解析的全局配置文件和mapper文件被构造出来的,Configuration也包含了相应的属性,
所以MapperProxy应该也是从Configuration获取:
````
 public <T> T getMapper(Class<T> type) {
        return this.configuration.getMapper(type, this);
    }

-----------------------------------------------------------
   public <T> T getMapper(Class<T> type, SQLSession SQLSession) {
        return this.mapperRegistry.getMapper(type, SQLSession);
    }
````

可以看到最终是由MapperRegistry对象获取的,那MapperRegistry是如何获取的呢:
````
    ...
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap();

    public <T> T getMapper(Class<T> type, SQLSession SQLSession) {
        //获取mapper接口对应的工厂
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory)this.knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
        } else {
            try {
                //使用工厂创建mapper接口
                return mapperProxyFactory.newInstance(SQLSession);
            } catch (Exception var5) {
                throw new BindingException("Error getting mapper instance. Cause: " + var5, var5);
            }
        }
    }
````

MapperRegistry内部有一个map,保存着mapper接口的class到相应的MapperProxyFactory的工厂,当我们需要获取某个mapper接口的时候,就利用相对的工厂创建mapper接口代理对象.

需要搞清楚的是MapperProxyFactory是如何创建Mapper接口代理对象的呢?直接锁定newInstance方法:
````
    protected T newInstance(MapperProxy<T> mapperProxy) {
        //jdk动态代理
        return Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy);
    }

    public T newInstance(SQLSession SQLSession) {
        //首先创建MapperProxy对象,MapperProxy对象实现了InvocationalHandler接口,所以它可以被jdk动态代理
        MapperProxy<T> mapperProxy = new MapperProxy(SQLSession, this.mapperInterface, this.methodCache);
        return this.newInstance(mapperProxy);
    }
````

上面代码就不解释了,直接总结第3步吧:
Configuration从MapperRegistry里获取对应的Mapper接口的代理工厂MapperProxyFactory,MapperProxyFactory使用jdk动态代理创建Mapper接口的动态代理对象.

#### 4. 执行mapper接口方法:
````
  personMapper.selectPersonById(1L);
````

上面分析到由SQLSession获取的Mapper对象其实是MapperProxyFactory创建的MapperProxy代理对象,那么SQL代码的执行也肯定是在MapperProxy类的invoke中了,所以直接锁定MapperProxy类的invoke方法:
````
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            //如果是Object类的方法
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }

             //jdk8后的接口允许默认方法,所以在这里做判断
            if (method.isDefault()) {
                if (privateLookupInMethod == null) {
                    return this.invokeDefaultMethodJava8(proxy, method, args);
                }

                return this.invokeDefaultMethodJava9(proxy, method, args);
            }
        } catch (Throwable var5) {
            throw ExceptionUtil.unwrapThrowable(var5);
        }
       
        MapperMethod mapperMethod = this.cachedMapperMethod(method);
         //真正执行Statement的入口
        return mapperMethod.execute(this.SQLSession, args);
    }
````

根据invoke方法可以分析出,invoke方法执行的也就是MapperMethod的execute方法,那MapperMethod是什么呢?先猜下：在mybatis所有核心组件基本都是xxxHandler命名,所有与接口有关的基本都是mapperxx命名,mapperProxy是接口代理对象,mapperMethod就有可能是接口的执行方法.
看下mapperProxy的属性:
````
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;
    private static final int ALLOWED_MODES = 15;
    private static final Constructor<Lookup> lookupConstructor;
    private static final Method privateLookupInMethod;
    private final SQLSession SQLSession;
    private final Class<T> mapperInterface;
    //存储着mapperProxy对应接口的方法
    private final Map<Method, MapperMethod> methodCache;
        ....
    }
````
再看刚才在invoke方法里的一个细节:

````
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       .....
        //根据接口要调用的方法,获取对应的mapperMethod
        MapperMethod mapperMethod = this.cachedMapperMethod(method);
        //使用获取到的mapperMethod执行SQL
        return mapperMethod.execute(this.SQLSession, args);
    }
````

已经可以确定了,在调用mapper接口方法的时候,mapperProxy内部已经维护了对应接口的所有方法,只等我们调用的时候execute执行了.

那mapperMethod是如何执行的呢?
````
    public Object execute(SQLSession SQLSession, Object[] args) {
        Object result;
        Object param;
        switch(this.command.getType()) {

         //insert操作
        case INSERT:
            param = this.method.convertArgsToSQLCommandParam(args);
            result = this.rowCountResult(SQLSession.insert(this.command.getName(), param));
            break;

        //update操作
        case UPDATE:
            param = this.method.convertArgsToSQLCommandParam(args);
            result = this.rowCountResult(SQLSession.update(this.command.getName(), param));
            break;
    
        //delete操作
        case DELETE:
            param = this.method.convertArgsToSQLCommandParam(args);
            result = this.rowCountResult(SQLSession.delete(this.command.getName(), param));
            break;

        //select操作
        case SELECT:
            if (this.method.returnsVoid() && this.method.hasResultHandler()) {
                this.executeWithResultHandler(SQLSession, args);
                result = null;
            } else if (this.method.returnsMany()) {
                result = this.executeForMany(SQLSession, args);
            } else if (this.method.returnsMap()) {
                result = this.executeForMap(SQLSession, args);
            } else if (this.method.returnsCursor()) {
                result = this.executeForCursor(SQLSession, args);
            } else {
                param = this.method.convertArgsToSQLCommandParam(args);
                result = SQLSession.selectOne(this.command.getName(), param);
                if (this.method.returnsOptional() && (result == null || !this.method.getReturnType().equals(result.getClass()))) {
                    result = Optional.ofNullable(result);
                }
            }
            break;
        
        //flush操作
        case FLUSH:
            result = SQLSession.flushStatements();
            break;
        default:
            throw new BindingException("Unknown execution method for: " + this.command.getName());
        }

        if (result == null && this.method.getReturnType().isPrimitive() && !this.method.returnsVoid()) {
            throw new BindingException("Mapper method '" + this.command.getName() + " attempted to return null from a method with a primitive return type (" + this.method.getReturnType() + ").");
        } else {
            return result;
        }
    }
````

我的selectPersonById方法是SELECT,就看看SELECT执行的逻辑吧：
````
  case SELECT:
            //如果方法没有返回值
            if (this.method.returnsVoid() && this.method.hasResultHandler()) {
                this.executeWithResultHandler(SQLSession, args);
                result = null;
            }
            //如果方法返回集合
             else if (this.method.returnsMany()) {
                result = this.executeForMany(SQLSession, args);
            } 
            //如果方法返回map
            else if (this.method.returnsMap()) {
                result = this.executeForMap(SQLSession, args);
            }
            //返回游标类
            else if (this.method.returnsCursor()) {
                result = this.executeForCursor(SQLSession, args);
            } else {
                //正常普通返回值

                //抓换
                param = this.method.convertArgsToSQLCommandParam(args);
                result = SQLSession.selectOne(this.command.getName(), param);
                if (this.method.returnsOptional() && (result == null || !this.method.getReturnType().equals(result.getClass()))) {
                    result = Optional.ofNullable(result);
                }
            }
````

锁定最后一个 selectOne 方法:

````
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        List var5;
        try {
            //获取mapperStatement
            MappedStatement ms = this.configuration.getMappedStatement(statement);
            //执行器执行
            var5 = this.executor.query(ms, this.wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);
        } catch (Exception var9) {
            throw ExceptionFactory.wrapException("Error querying database.  Cause: " + var9, var9);
        } finally {
            ErrorContext.instance().reset();
        }

        return var5;
    }
 
````

可以看到SQLSession的select还是代理到Executor的query方上了:
````
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        //获取SQL
        BoundSQL boundSQL = ms.getBoundSQL(parameter);
        //创建二级缓存的key,这个key非常长
        CacheKey key = this.createCacheKey(ms, parameter, rowBounds, boundSQL);
        //查询
        return this.query(ms, parameter, rowBounds, resultHandler, key, boundSQL);
    }
````

继续深入重载的query方法:
````
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSQL boundSQL) throws SQLException {
        ErrorContext.instance().resource(ms.getResource()).activity("executing a query").object(ms.getId());
        if (this.closed) {
            throw new ExecutorException("Executor was closed.");
        } else {
            //上一个缓存已经查询完成,并且标注了FlushCache=true的属性,那么清空本地缓存
            if (this.queryStack == 0 && ms.isFlushCacheRequired()) {
                this.clearLocalCache();
            }

            List list;
            try {
                ++this.queryStack;
                //如果没有指定resultHandler,那么线程本地缓存查询
                list = resultHandler == null ? (List)this.localCache.getObject(key) : null;
                if (list != null) {
                    //如果有缓存,就覆盖当前参数值,但只针对CALLABLE
                    this.handleLocallyCachedOutputParameters(ms, key, parameter, boundSQL);
                } else {
                    //从数据库查询
                    list = this.queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSQL);
                }
            } finally {
                --this.queryStack;
            }

            if (this.queryStack == 0) {
                Iterator var8 = this.deferredLoads.iterator();

                while(var8.hasNext()) {
                    BaseExecutor.DeferredLoad deferredLoad = (BaseExecutor.DeferredLoad)var8.next();
                    deferredLoad.load();
                }

                this.deferredLoads.clear();
                //查询完清除缓存
                if (this.configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
                    this.clearLocalCache();
                }
            }

            return list;
        }
    }
````
继续深入 queryFromDatabase 方法,看Executor到底是怎么查询的:
````
    private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSQL boundSQL) throws SQLException {
        this.localCache.putObject(key, ExecutionPlaceholder.EXECUTION_PLACEHOLDER);

        List list;
        try {
            //又是一个接口方法,有需要深入
            list = this.doQuery(ms, parameter, rowBounds, resultHandler, boundSQL);
        } finally {
            this.localCache.removeObject(key);
        }

        this.localCache.putObject(key, list);
        if (ms.getStatementType() == StatementType.CALLABLE) {
            this.localOutputParameterCache.putObject(key, parameter);
        }

        return list;
    }
````

继续看doQuery方法,因为我没有指定Executor的类型,所以这个doQuery肯定是在SimpleExecutor中了:
````
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSQL boundSQL) throws SQLException {
        Statement stmt = null;

        List var9;
        try {
            Configuration configuration = ms.getConfiguration();
            //来了,又是一个核心对象
            StatementHandler handler = configuration.newStatementHandler(this.wrapper, ms, parameter, rowBounds, resultHandler, boundSQL);
            stmt = this.prepareStatement(handler, ms.getStatementLog());
            var9 = handler.query(stmt, resultHandler);
        } finally {
            this.closeStatement(stmt);
        }

        return var9;
    }
````

在doQuery方法中根据Configuration创建了StatementHandler,它是SQL的处理器,看看Configuration是怎么创建它的:
````
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSQL boundSQL) {
        //RoutingStatementHandler
        StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSQL);
        //再次对插件进行了封装
        StatementHandler statementHandler = (StatementHandler)this.interceptorChain.pluginAll(statementHandler);
        return statementHandler;
    }

````

上面最重要的创建RoutingStatementHandler那句代码,RoutingStatementHandler是个什么东西,看样子它是路由的StatementHandler,岂不是创建各种类型的StatementHandler:
````
    public RoutingStatementHandler(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSQL boundSQL) {
        switch(ms.getStatementType()) {
        //普通的StatementHandler
        case STATEMENT:
            this.delegate = new SimpleStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSQL);
            break;
        //预编译StatementHandler,也就是预编译的SQL
        case PREPARED:
            this.delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSQL);
            break;
        //CALLABLE类型的StatementHandler
        case CALLABLE:
            this.delegate = new CallableStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSQL);
            break;
        default:
            throw new ExecutorException("Unknown statement type: " + ms.getStatementType());
        }

    }
````
从RoutingStatementHandler的源码可知,它负责创建不同类型的SQL的StatementHandler.

那么创建完这个StatementHandler后,有啥用呢？
回到doQuery方法:
````
    public <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSQL boundSQL) throws SQLException {
        Statement stmt = null;

        List var9;
        try {
            Configuration configuration = ms.getConfiguration();
           //创建StatementHandler
            StatementHandler handler = configuration.newStatementHandler(this.wrapper, ms, parameter, rowBounds, resultHandler, boundSQL);
             //预编译StatemenT    
            stmt = this.prepareStatement(handler, ms.getStatementLog());
            var9 = handler.query(stmt, resultHandler);
        } finally {
            this.closeStatement(stmt);
        }

        return var9;
    }

````
可以看到下面的一个 prepareStatement 方法直接预编译出来了一个Statement,Statement相信各位同学不陌生吧,java原生的SQL操作啊,由StatementHandler预编译成Statement这个方法肯定是做些事情的,到现在还没有设置参数呢,而且参数一直都随着那几颗方法：
````
   private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
        Connection connection = this.getConnection(statementLog);
        Statement stmt = handler.prepare(connection, this.transaction.getTimeout());
         //设置参数  
        handler.parameterize(stmt);
        return stmt;
    }
````

看看parameterize是如何设置参数的吧:

````
  public void parameterize(Statement statement) throws SQLException {
        //ParameterHandler出来了,它时StatementHandler的一个属性,负责SQL的入参
        this.parameterHandler.setParameters((PreparedStatement)statement);
    }
````

继续看看ParameterHandler是如何完成入参的吧：

````
   public void setParameters(PreparedStatement ps) {
        ErrorContext.instance().activity("setting parameters").object(this.mappedStatement.getParameterMap().getId());
       
        //从BoundSQL中获取ParameterMapping,也就是参数
        List<ParameterMapping> parameterMappings = this.boundSQL.getParameterMappings();
        if (parameterMappings != null) {
            for(int i = 0; i < parameterMappings.size(); ++i) {
                ParameterMapping parameterMapping = (ParameterMapping)parameterMappings.get(i);
                //如果参数是输入参数
                if (parameterMapping.getMode() != ParameterMode.OUT) {

                    //这里就是获取参数的对应的属性名
                    String propertyName = parameterMapping.getProperty();

                    //参数值
                    Object value;
                    //获取参数值
                    if (this.boundSQL.hasAdditionalParameter(propertyName)) {
                        value = this.boundSQL.getAdditionalParameter(propertyName);
                    } else if (this.parameterObject == null) {
                        value = null;

                    //如果 TypeHandler已经有了这个参数值的类型
                    } else if (this.typeHandlerRegistry.hasTypeHandler(this.parameterObject.getClass())) {
                        value = this.parameterObject;
                    } else {
                        MetaObject metaObject = this.configuration.newMetaObject(this.parameterObject);
                        value = metaObject.getValue(propertyName);
                    }

                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = this.configuration.getJdbcTypeForNull();
                    }

                    try {
                        //TypeHandler设置参数,内部仍然是Statement设置参数
                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
                    } catch (SQLException | TypeException var10) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + var10, var10);
                    }
                }
            }
        }

    }
````

上面设置完参数后,回到doQuery方法,使用StatementHandler的query方法执行:

````
  public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        PreparedStatement ps = (PreparedStatement)statement;
        //执行
        ps.execute();
        //处理结果集
        return this.resultSetHandler.handleResultSets(ps);
    }
````

最后看看ResultSetHandler是如何处理结果集的吧:
````
  public List<Object> handleResultSets(Statement stmt) throws SQLException {
        ErrorContext.instance().activity("handling results").object(this.mappedStatement.getId());
        List<Object> multipleResults = new ArrayList();
        int resultSetCount = 0;
        
        //获取第一个结果集
        ResultSetWrapper rsw = this.getFirstResultSet(stmt);
        List<ResultMap> resultMaps = this.mappedStatement.getResultMaps();
        int resultMapCount = resultMaps.size();
        this.validateResultMapsCount(rsw, resultMapCount);
        //一个resultMap对应一个结果集,不断遍历
        while(rsw != null && resultMapCount > resultSetCount) {
            ResultMap resultMap = (ResultMap)resultMaps.get(resultSetCount);
            //处理结果集
            this.handleResultSet(rsw, resultMap, multipleResults, (ResultMapping)null);
            //获取下一个结果集
            rsw = this.getNextResultSet(stmt);
            this.cleanUpAfterHandlingResultSet();
            ++resultSetCount;
        }

        String[] resultSets = this.mappedStatement.getResultSets();
        if (resultSets != null) {
            while(rsw != null && resultSetCount < resultSets.length) {
                ResultMapping parentMapping = (ResultMapping)this.nextResultMaps.get(resultSets[resultSetCount]);
                if (parentMapping != null) {
                    String nestedResultMapId = parentMapping.getNestedResultMapId();
                    ResultMap resultMap = this.configuration.getResultMap(nestedResultMapId);
                    this.handleResultSet(rsw, resultMap, (List)null, parentMapping);
                }

                rsw = this.getNextResultSet(stmt);
                this.cleanUpAfterHandlingResultSet();
                ++resultSetCount;
            }
        }

        return this.collapseSingleResultList(multipleResults);
    }
````

算是完成了对mybatis执行过程的一个简单的源码分析吧,由于我功力浅薄,
即使是分析出来了这么一个大致的运行流程,其中的大部分细节我仍然是不懂的,所以我会继续学习.

#### mybatis源码总结

Mybatis最核心的对象莫过于Configuration了,
Configuration在解析完配置文件和mapper文件后就一直流转于整个mybatis执行的生命周期内。

首先由Configuration创建出Executor,从而创建DefaultSQLSession,
又由Configuration内的MapperRegistry获取MapperProxy对象,执行SQL的时候,
也由Configuration创建StatementHandler,几乎可以说Configuration是无处不在。

---

然后说下Mybatis核心的组件:Executor。

负责调度StatementHandler。
StatementHandler负责调度ParameterHandler对Statement
进行参数处理,执行Statement调用ResultSetHandler对SQL执行的结果做出封装。

ParameterHandler负责Statement的参数处理,
ResultSetHandler负责Statement执行后的结果集处理.

在分析mybatis源码的过程中,我觉得mybatis整个框架的设计和面向对象的思想是发挥的淋漓尽致的。

其实有很多人说mybatis不够智能化,但是我想说的是,
Mybatis帮我们做掉这么多繁琐的事情,还能让我们灵活的掌握SQL，在设计上实属np。
听说Hibernate不需要写SQL,我没学过,不好妄下定论。
但是我觉得SQL本身就不属于Java语言这个范畴,**如果连SQL都不需要写,是什么ORM框架,又怎么谈SQL优化呢?**