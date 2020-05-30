package com.github.guang19.juc.lock.deadlock;

/**
 * @Description : TODO          死锁 demo
 * @Author :    yangguang
 * @Date :      2019/11/25
 */
public class DeadLockDemo {

    static class DeadLock implements Runnable
    {
        //资源1
        Object resource1;
        //资源2
        Object resource2;

        //全参构造
        public DeadLock(Object resource1, Object resource2) {
            this.resource1 = resource1;
            this.resource2 = resource2;
        }

        @Override
        public void run() {
            synchronized (resource1)
            {
                System.out.println(Thread.currentThread().getName() + " 持有资源1 : " + resource1 + " 尝试获取资源2 : " + resource2);
                synchronized (resource2)
                {
                    System.out.println(resource2);
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Object object1 = new Object();
        Object object2 = new Object();
        new Thread(new DeadLock(object1,object2),"A").start();
        new Thread(new DeadLock(object2,object1),"B").start();
    }
}
