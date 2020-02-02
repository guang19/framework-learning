package com.github.yangguang19.juc.lock.saleTickets;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description : TODO      模拟卖票员买票程序
 *                          多个卖票员同时卖30张票
 * @Author :    yangguang
 * @Date :      2019/11/18
 */
class Ticket
{
    //30张票
    private volatile int count = 30;

    //基于Java API层面的可重入锁
    private Lock lock = new ReentrantLock();

    public void sale()
    {

        lock.lock();
        try
        {
            if(count > 0)
            {
                System.out.println("当前线程: " + Thread.currentThread().getName() + " 卖出第 " + (count--) + " 张票,还剩: " + count);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
}

public class SaleTickets {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()->{for (int i = 0 ;i < 40; ++i){ticket.sale();}},"A").start();
        new Thread(()->{for (int i = 0 ;i < 40; ++i){ticket.sale();}},"B").start();
        new Thread(()->{for (int i = 0 ;i < 40; ++i){ticket.sale();}},"C").start();
    }
}
