package com.github.yangguang19.jvm.classLoader;

/**
 * @Description : TODO      类加载器
 * @Author :    yangguang
 * @Date :      2019/11/19
 */
public class ClassLoaderDemo {

    /**
     * 类加载器负责加载java编译后的.class文件,.class文件开头有特殊的文件标识(cafe babe)
     * 并将加载后的class文件的字节码加载到内存,并将这些内容(元数据,类的版本,字段,方法等描述信息)存储到元空间(jdk1.8之前的方法区)
     *
     * ClassLoader只负责加载.class文件,而class是否可以执行,不由它决定,而是由 Execution Engine(执行引擎)决定
     *
     * ClassLoader有几种?
     *
     * 1: BootStrapClassLoader: 最顶层jvm的启动类加载器,由c++编写
     *                          负责加载 JAVA_HOME/lib下的所有jar和类或被-x bootclasspath指定路径的所有基础类,简单来说就是加载jdk的所有类
     *
     * 2: PlatformClassLoader(由ExtensionClassLoader更新): 平台类加载器,加载平台相关的模块类,jdk9之前的ExtensionClassLoader加载javax等扩展类,但是
     *                          jdk9后引入模块化系统,就把 ExtensionClassLoader 取消了,PlatformClassLoader的双亲是BootStrapClassLoader
     *
     * 3 : AppClassLoader:     应用程序加载器,是面向我们用户的,负责加载当前应用classpath下的所有jar和类,AppClassLoader的双亲是PlatformClassLoader
     *
     *
     * 双亲委派机制:
     * 每一个类都有一个它对应的类加载器.
     * 在加载类的时候,系统手写会判断当前类是否被加载过了,如果已经被加载,那么直接返回已被加载的类.
     * 如果没有加载,手写会把加载的请求传递给它的父类加载器,这样一层一层,传递,最终会到顶级的BootStrapClassLoader,当父类加载器无法加载时,
     * 才由自己处理.这样避免了已经加载过的类重复被加载
     *
     */


    public static void main(String[] args)
    {
        Object object = new Object();
        //因为Object是jdk的,所以由BootStrapClassLoader加载,而 BootStrapClassLoader 由 c++编写,这里打印null,代表 BootStrapClassLoader
        System.out.println(object.getClass().getClassLoader());

        //这里会抛出 NullPointException,因为BootStrapClassLoader已经是最顶级的类加载器了,不可能有parent了
//        System.out.println(object.getClass().getClassLoader().getParent());

        System.out.println("----------------------");

        ClassLoaderDemo myObject = new ClassLoaderDemo();

        //因为 ClassLoaderDemo 是我编写的属于此程序的类,所以由 AppClassLoader 加载
        System.out.println(myObject.getClass().getClassLoader());

        //获取 PlatformClassLoader
        System.out.println(myObject.getClass().getClassLoader().getParent());

        //获取 BootStrapClassLoader
        System.out.println(myObject.getClass().getClassLoader().getParent().getParent());

        //空指针异常
//        System.out.println(myObject.getClass().getClassLoader().getParent().getParent().getParent());
    }
}
