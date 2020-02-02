package com.github.yangguang19.jvm.reference.phantomreference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Description : TODO              虚引用(幽灵引用)
 * @Author :    yangguang
 * @Date :      2019/11/26
 */
public class PhantomReferenceDemo {

    public static void main(String[] args)
    {
        Object object = new Object();

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        PhantomReference<Object> phantomReference = new PhantomReference<>(object,referenceQueue);

        //不出意外,打印下面的所有输出都打印 null,因为虚引用无法获取与之相关的对象

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        object = null;
        System.gc();

        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
