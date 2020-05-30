package com.github.guang19.juc.collections;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @Description : TODO          并发容器安全之 Set
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
public class SetDemo {
    /**
     *  HashSet:
     *  HashSet底层使用HashMap实现,它使用一个固定的Object作为Value,用户写的值为Key
     *  HashSet是线程不安全的,允许存null,不保证对象的有序性,因为HashSet的底层使用的是HashMap
     *  所以它默认的初始化容量为16
     *
     *  LinkedHashSet:
     *  底层使用LinkedHashMap实现,LinkedHashMap是HashMap的一个子类,底层使用双向链表实现
     *  但不是使用链表保存元素,它仍然使用HashMap保存元素,但链表用来维护元素插入的顺序,当遍历LinkedHashMap的时候,就
     *  按照插入的顺序来遍历
     *
     *  TreeSet:
     *  底层使用TreeMap(SortedMap,红黑树)实现,确保元素处于排序状态(根据键的自然排序 Comparable 接口或 比较器 Comparator)
     *
     *
     * 线程安全的Set:
     * 1: 和List一样Collections仍然提供了synchronizedSet
     * 2: CopyOnWriteArraySet,值得一提的是:CopyOnWriteArraySet使用CopyOnWriteArrayList实现
     *
     */

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        Runnable run = ()->{
            set.add(UUID.randomUUID().toString());
            System.out.println(set);
        };

        //30个线程对set进行修改和读取操作
        for(int i = 0 ; i < 100; ++i)
        {
            new Thread(run).start();
        }

        /**
         *
         * 大概率可能会抛出 ConcurrentModificationException 异常
         *
         * 此异常是同时对集合进行修改时会抛出的异常
         */
    }
}
