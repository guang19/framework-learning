package com.github.yangguang19.juc.volatiles;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description : TODO      测试ABA问题的产生与解决
 * @Author :    yangguang
 * @Date :      2019/11/24
 */
public class ABA {



    public static void main(String[] args) throws Exception
    {
        System.out.println("ABA问题---------------------------------");
        AtomicReference<Integer> atomicReference = new AtomicReference<>(10);

        new Thread(()->{
            //先将数据修改成其他值,再修改回原值
            System.out.println(atomicReference.compareAndSet(10 , 11));
            //修改回原值 :1000
            System.out.println(atomicReference.compareAndSet(11, 10));
        },"A").start();

        new Thread(()->{
            try
            {
                //让当前线程停止3秒中,让 A 线程先完成ABA问题的修改,然后此线程再执行
                TimeUnit.SECONDS.sleep(3);

                System.out.println("经过ABA操作后,数据修改: "+
                        atomicReference.compareAndSet(10,11)+" 为: " + atomicReference.get());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        },"B").start();

        TimeUnit.SECONDS.sleep(4);
        System.out.println("\n\nABA问题的解决办法---------------------------------------------------");

        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(10,1);

        new Thread(()->{
                atomicStampedReference.compareAndSet(10,11,
                        atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
                //更改值的时候也更改版本号
                System.out.println("C线程第一次修改后的版号为 ：　" +atomicStampedReference.getStamp());
                //改回原值也要更新版本号
                atomicStampedReference.compareAndSet(11,10,
                        atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
                System.out.println("C线程第二次修改后的版号为 ：　" +atomicStampedReference.getStamp());
        },"C").start();

        new Thread(()->{
            try
            {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(
                        "D线程修改: "
                                +
                         atomicStampedReference.compareAndSet(10,11,1,atomicStampedReference.getStamp()+1)
                );

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        },"D").start();
    }
}
