package com.github.guang19.jvm.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;

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

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException
    {
        //作为测试加载 ClassPerson 类
        File file = new File("/home/yangguang/Myproject/juc-jvm/target/classes/com/github/guang19/jvm/classLoader/ClassPerson.class");
        
        try
        {
            byte[] bytes = getFileBytes(file);
            Class<?> c = this.defineClass(name,bytes,0,bytes.length);
            if(Objects.nonNull(c))
            {
                return c;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return super.findClass(name);
    }


    private byte[] getFileBytes(File file) throws IOException
    {
        FileOutputStream in = new FileOutputStream(file);
        FileChannel channel = in.getChannel();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(bos);
        ByteBuffer bf  = ByteBuffer.allocate(1024);
        int i = 0;
        while ((i = channel.read(bf)) > 0)
        {
            bf.flip();
            wbc.write(bf);
            bf.clear();
        }

        return bos.toByteArray();
    }


    public static void main(String[] args) throws Exception
    {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> c = Class.forName("com.github.guang19.jvm.classLoader.ClassPerson",true,myClassLoader);
        ClassPerson person = (ClassPerson)c.getConstructor().newInstance();
//        打印0
        System.out.println(person.getAge());
    }
}
