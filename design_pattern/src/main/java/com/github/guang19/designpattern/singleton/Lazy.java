package com.github.guang19.designpattern.singleton;

import java.util.TreeMap;
import java.util.UUID;

/**
 * @author yangguang
 * @date 2020/3/5
 * @description <p>单例模式-懒汉式 double check</p>
 */
public class Lazy
{
    private static volatile Lazy lazy = null;

    public static final String s = "a";

    public static final String s2 = UUID.randomUUID().toString();

    static
    {
        System.out.println("init");
    }

    private Lazy(){}

    //懒汉式只在第一次获取的时候创建单例
    public static Lazy getInstance()
    {
        if (lazy == null)
        {
            synchronized (Lazy.class)
            {
                if(lazy == null)
                {
                    lazy = new Lazy();
                }
            }
        }
        return lazy;
    }
}
