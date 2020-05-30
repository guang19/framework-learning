package com.github.guang19.designpattern.singleton;

/**
 * @author yangguang
 * @date 2020/3/5
 * @description <p>单例模式-饿汉式</p>
 */
public class Eager
{
    //预先创建对象，但是只有在初始化阶段才会初始化静态变量。
    //所以所谓的饿汉式与懒汉式并没有什么区别
    //因为初始化有8种情况，但如果正常使用，只有第一次获取单例的时候才会初始化
    private static final Eager eager = new Eager();

    private Eager()
    {
        System.out.println("init");
    }

    public static Eager getInstance()
    {
        return eager;
    }

}
