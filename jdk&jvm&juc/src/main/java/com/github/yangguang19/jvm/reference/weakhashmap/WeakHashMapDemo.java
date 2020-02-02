package com.github.yangguang19.jvm.reference.weakhashmap;

import java.util.WeakHashMap;

/**
 * @Description : TODO   测试虚引用 map
 * @Author :    yangguang
 * @Date :      2019/11/26
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        Object object = new Object();
        WeakHashMap<Object,Object> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(object,new Object());

        System.out.println(weakHashMap);

        System.out.println("=======================================");

        object = null;
        System.gc();
        //不出意外,打印null
        System.out.println(weakHashMap);
    }
}
