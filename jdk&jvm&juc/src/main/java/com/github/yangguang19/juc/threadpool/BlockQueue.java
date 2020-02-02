package com.github.yangguang19.juc.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description : TODO           阻塞队列,线程池的核心
 * @Author :    yangguang
 * @Date :      2019/11/21
 */
public class BlockQueue {

    
    public static void main(String[] args) throws Exception
    {

        BlockingQueue<String> arrayBlockQueue = new ArrayBlockingQueue<>(3);
        //add remove element 抛出异常
//        System.out.println(arrayBlockQueue.add("a"));
//        System.out.println(arrayBlockQueue.add("b"));
//        System.out.println(arrayBlockQueue.add("c"));
////        System.out.println(arrayBlockQueue.add("d"));//超出队列界限,抛出异常
//        System.out.println(arrayBlockQueue.remove());
//        System.out.println(arrayBlockQueue.remove());
//        System.out.println(arrayBlockQueue.remove());
////        System.out.println(arrayBlockQueue.remove());//队列中没有元素,将抛出异常
//        System.out.println(arrayBlockQueue.element()); //队列中没有元素,检查时也会抛出异常


        //这一组不会抛出异常,而是返回false,null值
//        System.out.println(arrayBlockQueue.offer("a"));
//        System.out.println(arrayBlockQueue.offer("b"));
//        System.out.println(arrayBlockQueue.offer("c"));
//        System.out.println(arrayBlockQueue.offer("d"));//返回false

//        System.out.println(arrayBlockQueue.poll());
//        System.out.println(arrayBlockQueue.poll());
//        System.out.println(arrayBlockQueue.poll());
//        System.out.println(arrayBlockQueue.poll()); //返回null


        //这一组不会返回特殊值,而是阻塞等待,也就是适用于线程池的消费者和生产者
//        arrayBlockQueue.put("a");
//        arrayBlockQueue.put("b");
//        arrayBlockQueue.put("c");
//        arrayBlockQueue.put("d"); //main线程阻塞

//        System.out.println(arrayBlockQueue.take());
//        System.out.println(arrayBlockQueue.take());
//        System.out.println(arrayBlockQueue.take());
//        System.out.println(arrayBlockQueue.take());//main线程阻塞

        //这一组不会一直阻塞等待,而是阻塞一段时间
        System.out.println(arrayBlockQueue.offer("a", 3L, TimeUnit.SECONDS));
        System.out.println(arrayBlockQueue.offer("b", 3L, TimeUnit.SECONDS));
        System.out.println(arrayBlockQueue.offer("c", 3L, TimeUnit.SECONDS));
        //等待3秒,还没有空间可以存放,就返回false
//        System.out.println(arrayBlockQueue.offer("d", 3L, TimeUnit.SECONDS));

        System.out.println(arrayBlockQueue.poll(3L,TimeUnit.SECONDS));
        System.out.println(arrayBlockQueue.poll(3L,TimeUnit.SECONDS));
        System.out.println(arrayBlockQueue.poll(3L,TimeUnit.SECONDS));
        //等待3秒,还没有元素可以poll,就返回null
        System.out.println(arrayBlockQueue.poll(3L,TimeUnit.SECONDS));
    }

}
