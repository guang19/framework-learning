package com.github.yangguang19.juc.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Description : TODO          信号量
 * @Author :    yangguang
 * @Date :      2019/11/21
 */
public class SemaphoreDemo {
    /**
     *
     * Semaphore 用于控制多线车公对多资源的控制
     *
     */

    //模拟多个小朋友抢凳子的游戏0
    public static void main(String[] args)
    {
        //一共有3个凳子
        Semaphore semaphore = new Semaphore(3);

        //模拟5个小朋友
        for (int i = 0 ; i < 6; ++i)
        {
            new Thread(()->{
               try
               {
                   semaphore.acquire();
                   System.out.println(Thread.currentThread().getName() + " 抢到了凳子");
                   Thread.sleep(2000);
                   System.out.println(Thread.currentThread().getName() + "离开了凳子");
               }
               catch(Exception e)
               {
                   e.printStackTrace();
               } finally {
                   semaphore.release();
               }
            }).start();
        }
    }
}
