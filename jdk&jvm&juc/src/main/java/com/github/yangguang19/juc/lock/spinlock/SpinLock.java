package com.github.yangguang19.juc.lock.spinlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description : TODO          实现简单自旋锁
 * @Author :    yangguang
 * @Date :      2019/11/25
 */
public class SpinLock {

    private static final AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //加锁
    public  void lock()
    {
        //获取当前锁
        Thread thread = Thread.currentThread();

        //如果期盼值是null,也就是代表没有锁引用了,就设置为当前线程引用,如果不成功就while
        while (!atomicReference.compareAndSet(null,thread))
        {

        }
        System.out.println(thread.getName() + " 获取到锁了 ");
    }

    //解锁
    public  void unlock()
    {
        //当前获取锁的线程
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + " 释放锁了 ");
        //释放锁,让下一个线程获取
        atomicReference.compareAndSet(thread,null);
    }

    public static void main(String[] args) throws Exception
    {
        SpinLock spinLock = new SpinLock();

        new Thread(()->{
            try
            {
                spinLock.lock();
                //暂停3秒
                TimeUnit.SECONDS.sleep(3);
                spinLock.unlock();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        },"A").start();


        new Thread(()->{
            try
            {
                spinLock.lock();
                spinLock.unlock();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        },"B").start();
    }
}
