package com.github.yangguang19.juc.lock.readWriteLock;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description : TODO      读写锁
 * @Author :    yangguang
 * @Date :      2019/11/21
 */
public class ReadWriteLockDemo
{
    /**
     *      * 读-读 : 无锁
     *      * 读-写　:　锁
     *      * 写－写　:　锁
     */
    //模拟缓存被读和被写

    static class Cache
    {
        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        private HashMap<String,Object> cache = new HashMap<>();

        public void put(String key , Object val)
        {
            readWriteLock.writeLock().lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+" 开始写入");
                cache.put(key,val);
                System.out.println(Thread.currentThread().getName()+" 写入完成");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                readWriteLock.writeLock().unlock();
            }
        }


        public void get(String key)
        {
            readWriteLock.readLock().lock();
            try
            {
                System.out.println(Thread.currentThread().getName() + " 开始读取");
                Object obj = cache.get(key);
                System.out.println(Thread.currentThread().getName() + " 读取完成 : " + obj);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
               readWriteLock.readLock().unlock();
            }
        }
    }

    public static void main(String[] args)
    {
        Cache cache = new Cache();
        for (int i = 0 ; i < 5; ++i)
        {
            final int tempI = i;
            new Thread(()->{
               cache.put(String.valueOf(tempI),tempI);
            }).start();
        }

        for (int i = 0 ; i < 5; ++i)
        {
            final int tempI = i;
            new Thread(()->{
                cache.get(String.valueOf(tempI));
            }).start();
        }
    }
}
