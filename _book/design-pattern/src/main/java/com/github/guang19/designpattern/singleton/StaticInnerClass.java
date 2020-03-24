package com.github.guang19.designpattern.singleton;

/**
 * @author yangguang
 * @date 2020/3/5
 * @description <p>单例模式-静态内部类</p>
 */
public class StaticInnerClass
{
    public static String a = "a";

    private StaticInnerClass(){}

    static
    {
        System.out.println("outer class static block");
    }

    private static class innerClass
    {
        static
        {
            System.out.println("inner class static block");
        }

        private static final StaticInnerClass  staticInnerClass = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance()
    {
        return innerClass.staticInnerClass;
    }
}
