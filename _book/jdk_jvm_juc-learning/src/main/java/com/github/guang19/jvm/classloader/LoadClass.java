package com.github.guang19.jvm.classloader;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

/**
 * @author yangguang
 * @date 2020/2/28
 * @description <p></p>
 */
public class LoadClass
{
    static
    {
        System.out.println("bootstrap class static code block");
    }

    {
        System.out.println("bootstrap class common code block");
    }

    public static void main(String[] args) throws Throwable
    {
        //静态变量字段才会初始化类: getstatic
//        System.out.println($Class.b);

        //给静态变量赋值 : putstatic
//        System.out.println($Class.b = 1);

        //静态常量不会初始化类
//        System.out.println($Class.c);

        //访问接口的静态变量或常量并不会导致子类的初始化
//        System.out.println($Class.a1);
//        System.out.println($Class.a2);

        //调用类的静态方法也会初始化类：　invokestatic
//        $Class.m1();

        //使用反射操作类时也会初始化类
//        Class<?> aClass = Class.forName("com.github.guang19.jvm.classloader.$Class");
//        System.out.println(aClass);

//        $Class $class = $Class.class.getConstructor().newInstance();
//        System.out.println($class.hashCode());

        //MethodHandler是一种比反射更轻量的调用机制
        //MethodHandler 动态调用,虽然调用的是静态方法，但仍需先初始化这个类
//        MethodType methodType1 = MethodType.methodType(void.class);
//        MethodHandle methodHandle1 = MethodHandles.lookup().findStatic($Class.class,"m1",methodType1);
//        此处被编译成 invokestatic
//        methodHandle1.invoke();

        //VarHandle作为动态获取变量的调用
        VarHandle a1 = MethodHandles.lookup().findStaticVarHandle($Class.class, "a1", int.class);
//        Object o = a1.get();
          //此处被编译成 : getstatic
//        System.out.println(o);

    }
}
interface  $T
{
    public static int a1 = 10;
    public static final int a2 = 10;
}
class $$SuperClass implements $T
{
    static
    {
        System.out.println("$$super static code block");
    }

    {
        System.out.println("$$supper common code block");
    }
}

class $SuperClass extends $$SuperClass
{
    static
    {
        System.out.println("super static code block");
    }
    {
        System.out.println("super common code block");
    }
}


class $Class extends $SuperClass
{
    public static int b = 10;

    public static final int c = 10;

    public $Class(){}

    public static void m1()
    {
        System.out.println("static method");
    }

    static
    {
        System.out.println("child static code block");
    }

    {
        System.out.println("child common code block");
    }
}
