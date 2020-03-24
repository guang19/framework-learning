package com.github.guang19.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description : TODO     CuntDownLatch是并发编程辅助工具之一
 * @Author :    yangguang
 * @Date :      2019/11/20
 */
public class CountDownLatchDemo {

    /**
     * 多线程在完成同一任务时,如果想让它们一起完成,也就是没有完成时阻塞,CountDownLatch就是非常好的工具
     */
    private static int count = 6;

    //模拟6个运动员一起比赛,只有6个运动员都到达终点才结束比赛

    private static final CountDownLatch countDownLatch = new CountDownLatch(count);

    public static void main(String[] args) throws Exception
    {
        for(int i = 0 ; i < 6; ++i)
        {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " 到达了终点");
                //减少state计数
                countDownLatch.countDown();
            }).start();
        }
        //线程到此处阻塞,只有当state==0的时候才被唤醒
        countDownLatch.await();

        TimeUnit.SECONDS.sleep(2);

        for(int i = 0 ; i < 6; ++i)
        {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " 到达了终点2");
                //减少state计数
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();

        System.out.println("比赛结束");
    }
}
