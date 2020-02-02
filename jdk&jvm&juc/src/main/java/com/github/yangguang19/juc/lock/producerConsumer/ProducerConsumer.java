package com.github.yangguang19.juc.lock.producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO       生产者和消费者
 * @Author :    yangguang
 * @Date :      2019/11/18
 */



public class ProducerConsumer {



    //资源
    private volatile int number = 0;


    //增加
    public synchronized void increment()
    {
        try {
            while(number > 0)
            {
                this.wait();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ++number;

        System.out.println(Thread.currentThread().getName() + " --> " + number);
        //唤醒其他线程
        this.notifyAll();
    }

    //减少
    public synchronized void decrement()
    {
        try {
            while (number <= 0)
            {
                this.wait();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        --number;
        System.out.println(Thread.currentThread().getName() + " --> " + number);
        //唤醒其他线程
        this.notifyAll();
    }

    public static void main(String[] args)
    {
        /**
         * 模拟多个线程对number ++ 或--
         * 多轮操作后,number依然为 0
         */
        ProducerConsumer producerConsumer = new ProducerConsumer();
//        new Thread(()->{for (int i = 0 ; i < 10; ++i){producerConsumer.increment();}},"A").start();
//        new Thread(()->{for (int i = 0 ; i < 10; ++i){producerConsumer.increment();}},"B").start();
//        new Thread(()->{for (int i = 0 ; i < 10 ; ++i){producerConsumer.decrement();}},"C").start();
//        new Thread(()->{for (int i = 0 ; i < 10 ; ++i){producerConsumer.decrement();}},"D").start();
        new Thread(()->{for (int i = 0 ; i < 20; ++i){producerConsumer.increment2();}},"A").start();
        new Thread(()->{for (int i = 0 ; i < 20; ++i){producerConsumer.increment2();}},"B").start();
        new Thread(()->{for (int i = 0 ; i < 20 ; ++i){producerConsumer.decrement2();}},"C").start();
        new Thread(()->{for (int i = 0 ; i < 20 ; ++i){producerConsumer.decrement2();}},"D").start();
    }


    /**
     * 下面生产者和消费者使用JAVA API 层面的 Lock 实现
     */
    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increment2()
    {

        lock.lock();
        try
        {
            while (number > 0)
            {
                condition.await();
            }
            ++number;
            System.out.println(Thread.currentThread().getName() + " --> " + number);
            condition.signalAll();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void decrement2()
    {
        lock.lock();
        try
        {
            while (number <= 0)
            {
                condition.await();
            }
            --number;
            System.out.println(Thread.currentThread().getName() + " --> " + number);
            condition.signalAll();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
}

