package com.github.guang19.juc.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description : TODO          并发容器安全之 Map
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
public class MapDemo {

    /**
     *  HashMap:
     *  HashMap的初始化默认容量为16,扩容后为原来的2倍.
     *  线程不安全.
     *  HashMap在jdk1.8之前使用拉链法实现(数组+链表),jdk1.8之后使用 数组+链表/红黑树,当链表节点数量
     *  大于阈值(默认为8)时,将链表转为红黑树,小于6时又将红黑树转为链表.
     *  HashMap的扩容根据 threshold 来决定,当HashMap的size>=threshold时,就准备扩容.threshold是
     *  capacity * loadFactory的结果.
     *  loadFactory是加载因子,loadFactory越趋近于1,那么数组存放的元素就越多,访问效率就较低
     *  loadFactory越趋近于0,那么数组存放的元素就越少,数组的使用率就越低.建议使用默认的0.75
     *
     *
     *  HashTable:
     *  与HashMap相似,但HashTable是线程安全,HashTable的同步基于synchronized,所以它的效率并不高.
     *
     *  HashTable不允许null-key和null-value,而HashMap支持一个null-key,多个null-value. Hash
     *
     *  HashTable的默认初始化容量为11,扩容后为原来的2倍+1.
     *
     *  HashTable不像HashMap有将链表转红黑树的机制,它底层仍然采用拉链法
     *
     *  TreeMap:
     *  红黑树实现,不允许null,允许自然排序Comparable和比较器Comparator
     *
     *  线程安全的Map:
     *  1: Collections仍然提供了 SynchronizedMap,底层还是基于 synchronized,不建议使用个
     *
     *  2: ConcurrentHashMap: 并发map,很好的支持高性能和高并发.jdk1.7之前使用分段数组+链表实现,jdk1.8后
     *     使用 数组+链表/红黑树实现.
     *     jdk1.7之前给每段数据加锁,当一个线程访问其中一段数据时,其他数据也能被其他线程访问,也是非常的高效
     *     jdk1.8后使用数组+链表/红黑树实现,其扩容等机制与HashMap一样,但是控制并发的方法改为了CAS+synchronized
     *     synchronized锁的只是链表的首节点或红黑树的首节点,这样一来,只要节点不冲突(hash不冲突),synchronized也不会触发,更加高效
     *
     *  3: ConcurrentSkipListMap : 跳表map
     */

    public static void main(String[] args) {

        Map<String,Object> hashMap = new HashMap<>();



        Runnable run = ()->{
            hashMap.put(Thread.currentThread().getName(),UUID.randomUUID().toString());
            System.out.println(hashMap);
        };

        //30个线程对map进行修改和读取操作
//        for(int i = 0 ; i < 100; ++i)
//        {
//            new Thread(run).start();
//        }
        /**
         *
         * 大概率可能会抛出 ConcurrentModificationException 异常
         *
         * 此异常是同时对集合进行修改时会抛出的异常
         */
    }
}
