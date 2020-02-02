package com.github.yangguang19.juc.threadpool;

import java.util.concurrent.*;

/**
 * @Description : TODO      线程池知识
 * @Author :    yangguang
 * @Date :      2019/11/21
 */
public class ThreadPoolDemo {

    /**
     * 线程池的知识已在 README 详细讲解了
     *
     * 这里作为测试,自定义线程池
     */

    public static void main(String[] args)
    {
        //获取本机cpu核心数(本机为8)
        int coreSize = Runtime.getRuntime().availableProcessors();


        //创建自定义线程池
        ExecutorService threadPool =
                new ThreadPoolExecutor(1,
                        coreSize+1,
                        3L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(coreSize+1),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.CallerRunsPolicy());//默认的CallerRunsPolicy策略

        //根据我创建的线程池可知,如果使用1默认的拒绝策略,那么线程池最大可接受的任务数量为 coreSize+1 * 2(maximumPoolSize+workQueue.size)
        int maxSize = (coreSize+1) * 2;
        try
        {
            //这里任务就超出了线程池的最大限制,可以调整 拒绝策略,看看程序有啥反应
            //超出的线程有main线程执行
            for (int i = 0 ; i < maxSize + 2 ;++i)
            {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + "任务执行");
                });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            threadPool.shutdown();
        }
    }
}
