package com.github.guang19.juc.collections;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description : TODO      并发容器安全之List
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
public class ListDemo {
    /**
     * ArrayList:
     * 底层使用Object数组实现,初始化容量为10,动态扩容使用Arrays.copyof增长0.5倍 +１,线程不安全
     *
     * Vector:
     * 线程安全的ArrayList,底层方法使用synchronized保证线程安全,因此,效率低下,不建议使用
     *
     * LinkedList:
     * 底层使用双向链表实现,也是线程不安全的
     *
     *
     * 如何创建线程安全的 List:
     * 1:使用集合工具类Collections的 synchronizedList把普通的List转为线程安全的List,但是不建议使用,
     * 因为它底层是返回了Collections内部的一个List实现类,这个实现类的方法仍然使用synchronized,说白了,与Vector相似
     *
     * 2:使用写时复制类 CopyOnWriteArrayList,此类适合读多写少的场合,它的性能比Vector好的多,
     *   它的读取方法没有使用加锁操作,而是在使用add,set等修改操作的时候将原内容和要修改的内容复制到新的副本中,
     *   写完后,再将副本赋予原数据
     *
     */

    //ArrayList线程不安全的demo
    public static void main(String[] args) throws Exception
    {
        List<Integer> list = new ArrayList<>();
        Runnable run = ()->{
            list.add(5);
            System.out.println(list);
        };

        //30个线程对list进行修改和读取操作
        for(int i = 0 ; i < 30; ++i)
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
