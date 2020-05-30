package com.github.guang19.designpattern.singleton;

/**
 * @author guang19
 * @date 2020/5/19
 * @description 单例模式测试
 * @since 1.0.0
 */
public class SingletonTest
{
    public static void main(String[] args)
    {
        Eager eager = Eager.getInstance();

        EnumSingleton enumSingleton = EnumSingleton.getInstance();

        Lazy lazy = Lazy.getInstance();

        StaticInnerClass staticInnerClass = StaticInnerClass.getInstance();
    }

}