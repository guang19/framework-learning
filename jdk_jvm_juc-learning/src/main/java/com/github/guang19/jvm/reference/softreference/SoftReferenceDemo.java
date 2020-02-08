package com.github.guang19.jvm.reference.softreference;

import java.lang.ref.SoftReference;

/**
 * @Description : TODO          测试软引用
 * @Author :    yangguang
 * @Date :      2019/11/26
 */
public class SoftReferenceDemo {

    /**
     *
     * 当内存足够或正常时不会回收软引用
     *
     */
    public static void main(String[] args) {
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference<>(object);

        System.gc();
        //对象不为bull
        System.out.println(softReference.get());

        object = null;
        System.gc();

        try
        {
            //创建一个接近堆内存大小的byte数组,这里会抛出 Throwable , 捕获,然后再 catch 里打印软引用是否为空
            byte[] bytes = new byte[1024 * 1024 * 98];
        }
        catch(Throwable throwable)
        {
            //不出意外,打印null
            System.out.println(softReference.get());
        }
    }
}
