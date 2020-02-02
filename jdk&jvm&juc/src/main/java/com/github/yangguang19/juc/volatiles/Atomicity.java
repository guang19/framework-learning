package com.github.yangguang19.juc.volatiles;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description : TODO      测试volatile的原子性
 * @Author :    yangguang
 * @Date :      2019/11/22
 */
public class Atomicity {

    //操作数
    private static volatile int number = 0;


    //CAS原子类测试
    private static AtomicInteger number2 = new AtomicInteger(0);
    //线程数
    private static final int threadCount = 15;

    //30个线程对number操作
    private static final CountDownLatch countDownLatch = new CountDownLatch(threadCount << 1);

    public static void main(String[] args) throws Exception
    {

        ExecutorService executorService = new ThreadPoolExecutor(1,
                threadCount,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(threadCount),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        try
        {
            //因为线程池的最大线程数量 +　阻塞队列容量为　threadCount+threadCount,所以这里设置为最大线程
            int count = threadCount << 1;
            for (int i = 0 ; i < count; ++i)
            {
                executorService.
                        execute(()-> {
                    for (int k = 0 ; k < 1000; ++k)
                    {
                        number++;
                        number2.incrementAndGet();
                    }
                    countDownLatch.countDown();
                });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }
        countDownLatch.await();

        //此处的结果在我的机器上运行的结果没有一次等于30000,由此可见volatile并不保证原子性
        System.out.println("15个线程对number进行非原子性操作后,number的值为:" + number);

        //30000
        System.out.println("15个线程对number进行非原子性操作后,number2的值为:" + number2);

    }
}
