package com.github.yangguang19.jvm.reference.weakreference;

import java.lang.ref.WeakReference;

/**
 * @Description : TODO   测试弱引用
 * @Author :    yangguang
 * @Date :      2019/11/26
 */
public class WeakReferenceDemo {

    public static void main(String[] args)
    {
        Object object = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(object);


        System.gc();
        System.out.println(weakReference.get());

        object = null;
        System.gc();
        //如果正常打印null
        System.out.println(weakReference.get());
    }
}
