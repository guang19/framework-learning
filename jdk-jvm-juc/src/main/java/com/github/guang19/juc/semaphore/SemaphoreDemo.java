package com.github.guang19.juc.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Description : TODO          信号量
 * @Author :    yangguang
 * @Date :      2019/11/21
 */
public class SemaphoreDemo {
    /**
     *
     * Semaphore 用于控制多线程对多资源的控制
     *
     */

    //模拟多个小朋友抢凳子的游戏0
    public static void main(String[] args)
    {
        //一共有3个凳子
        Semaphore semaphore = new Semaphore(2);

        //模拟5个小朋友
        for (int i = 0 ; i < 6; ++i)
        {
            new Thread(()->{
               try
               {
                   //最多允许2个线程获取到锁
                   semaphore.acquire();
                   System.out.println(Thread.currentThread().getName() + " 抢到了凳子");
//                   Thread.sleep(1000);
                   System.out.println(Thread.currentThread().getName() + "离开了凳子");
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               } finally {
                   //释放锁
                   semaphore.release();
               }
            }).start();
        }
    }
}
