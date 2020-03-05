package com.github.guang19.jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @Description : TODO          自定义类加载器
 * @Author :    yangguang
 * @Date :      2019/11/19
 */
public class MyClassLoader extends ClassLoader{

    /***
     *
     * 注意: 自定义类加载器如果想打破 双亲委派机制,就要重写loadClass方法
     *      不想打破只需要重写findClass方法
     *
     */

    private String path = "/home/yangguang/下载/";
    private final String suffix = ".class";


    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException
    {
        System.out.println("load class by MyClassLoader");
        byte[] bytes = getFileBytes(className);
        return this.defineClass(className,bytes,0,bytes.length);
    }


    private byte[] getFileBytes(String filePath)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try( FileChannel channel = FileChannel.open(Path.of(path+filePath.replace(".","/")+suffix), StandardOpenOption.READ);
             bos)
        {
            ByteBuffer bf  = ByteBuffer.allocate(1024);
            int length = 0;
            while ((length = channel.read(bf)) != 0)
            {
                if(length == -1)
                {
                    break;
                }
                bf.flip();
                bos.write(bf.array(),0,length);
                bf.clear();
            }
        }catch (IOException e)
        {
        }
        return bos.toByteArray();
    }


    public static void main(String[] args) throws Exception
    {
        //每个类加载器都有自己的命名空间，命名空间由该加载器及其所有父加载器所有加载的类组成
        //在同一个命名空间中，不会出现类全限定名(包括包名)相同的2个类
        //在不同的命名空间中，可能会出现类全限定名(包括包名)相同的2个类

        //此处2个类加载器 myClassLoader1 和 myClassLoader2，都有自己的命名空间
        //所以它们即使加载相同的类，但是，Class对象却不同。

        MyClassLoader myClassLoader1 = new MyClassLoader();
        MyClassLoader myClassLoader2 = new MyClassLoader();

        //clazz1和clazz2不同
        Class<?> clazz1 = myClassLoader1.loadClass("com.github.guang19.jvm.classloader.ArrayClassLoaderTest");
        Class<?> clazz2 = myClassLoader2.loadClass("com.github.guang19.jvm.classloader.ArrayClassLoaderTest");

        Object object1 = clazz1.getConstructor().newInstance();
        Object object2 = clazz2.getConstructor().newInstance();

        System.out.println(clazz1.hashCode());
        System.out.println(clazz1.getClassLoader());
        System.out.println(object1.hashCode());

        System.out.println(clazz2.hashCode());
        System.out.println(clazz2.getClassLoader());
        System.out.println(object2.hashCode());

        Thread.sleep(10000);

        //类对象被回收
        object1 = null;
        //类被gc
        clazz1 = null;
        //类的类加载器实例被gc
        myClassLoader1 = null;
        System.gc();

        Thread.sleep(10000);

        //类对象被回收
        object2 = null;
        //类被gc
        clazz2 = null;
        //类的类加载器实例被gc
        myClassLoader2 = null;
        Thread.sleep(10000);
    }
}

