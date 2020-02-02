package com.github.yangguang19.juc.lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO          juc之 condition
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
public class ConditionDemo {

    /**
     *
     *  使用 Lock 和 Condition ,使3个线程顺序打印公共资源
     *
     */
    private volatile int num = 0;

    //标志位,控制什么时候3个线程打印, flag == 1 , 第一个线程打印 , flag == 2 , 第二个线程打印, flag==3,第三个线程打印
    private volatile int flag = 1;

    private Lock lock = new ReentrantLock();

    //三个线程的condition
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printByFirst()
    {
        lock.lock();
        try
        {
            while (flag != 1)
            {
                c1.await();
            }
            ++num;
            for (int i = 0 ;i < 5; ++i)
            {
                System.out.println(Thread.currentThread().getName() + " --> " + num);
            }

            //更新标志位,让第二个线程打印
            flag = 2;
            c2.signal();
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

    public void printBySecond()
    {
        lock.lock();
        try
        {
            while (flag != 2)
            {
                c2.await();
            }
            ++num;
            for (int i = 0 ;i < 5; ++i)
            {
                System.out.println(Thread.currentThread().getName() + " --> " + num);
            }
            flag = 3;

            c3.signal();
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

    public void printByThird()
    {
        lock.lock();
        try
        {
            while (flag != 3)
            {
                c3.await();
            }
            ++num;
            for (int i = 0 ;i < 5; ++i)
            {
                System.out.println(Thread.currentThread().getName() + " --> " + num);
            }
            flag = 1;

            c1.signal();
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

    public static void main(String[] args)
    {
        ConditionDemo conditionDemo = new ConditionDemo();
        new Thread(()->{for (int i = 0; i < 5 ; ++i){conditionDemo.printByFirst();}},"A").start();
        new Thread(()->{for (int i = 0; i < 5 ; ++i){conditionDemo.printBySecond();}},"B").start();
        new Thread(()->{for (int i = 0; i < 5 ; ++i){conditionDemo.printByThird();}},"C").start();
    }
}
