package com.github.guang19.designpattern.singleton;

import java.util.TreeMap;

/**
 * @author yangguang
 * @date 2020/3/5
 * @description <p>单例模式-懒汉式 double check</p>
 */
public class Lazy
{
    private static volatile Lazy lazy = null;

    private Lazy(){}

    //懒汉式只在第一次获取的时候创建单例
    public static Lazy getLazy()
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

    static class T{
        private String name;

        public T()
        { }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }

    public static void main(String[] args)
    {
        TreeMap<T,Object> treeMap =  new TreeMap<>();
        treeMap.put(new T(),"a");
        treeMap.put(new T(),"b");
        treeMap.put(new T(),"c");
        System.out.println(treeMap);
    }
}
