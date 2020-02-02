package com.github.yangguang19.juc.aqs;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description : TODO          测试 AQS
 * @Author :    yangguang
 * @Date :      2019/11/29
 */
public class AQS {

    private static int resource = 0;

    private static Sync sync = new Sync();

    public static void main(String[] args) throws Exception
    {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,500, 4,TimeUnit.SECONDS,new LinkedBlockingDeque<>(500)
                , Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy()
        );
        try
        {
            for(int i = 0 ; i < 10000; ++i)
            {
                threadPoolExecutor.execute(()->{
//                    sync.lock();
                    resource++;
//                    sync.unlock();
                });
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            threadPoolExecutor.shutdown();
        }

        //等待线程执行完毕
        TimeUnit.SECONDS.sleep(5);

        //打印最后值
        System.out.println("resource final value = " + resource);
    }




    /**
     *
     * 实现我自己的同步器:
     * 1: 继承AQS同步器
     * 2:　如果我们需要实现独占锁,那么我们只需要实现tryAcquire和tryRelease方法
     * 　　如果需要共享锁,那么只需要实现tryAcquireShare和tryReleaseShare方法
     *
     */


    private static class Sync extends AbstractQueuedSynchronizer
    {

        public  void lock()
        {
            acquire(1);
        }

        public boolean unlock()
        {
            return release(1);
        }

        public boolean tryLock()
        {
            return tryAcquire(1);
        }

        public boolean isLocked()
        {
            return isHeldExclusively();
        }


        @Override
        protected boolean tryAcquire(int arg) {
            //CAS比较state的值,如果state的值是0,那么设置state为1,并且把
            // 当前线程设置为独占线程.
            if(compareAndSetState(0,1))
            {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //如果锁已被释放,抛出异常
            if(getState() == 0)
            {
                throw new IllegalMonitorStateException();
            }
            //把当前独占的线程设置为null,并把state的值设置为0,代表锁空闲
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //线程是否已经占用
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }


}
