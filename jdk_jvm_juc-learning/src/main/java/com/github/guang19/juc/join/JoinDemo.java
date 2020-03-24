package com.github.guang19.juc.join;

import java.util.concurrent.TimeUnit;

/**
 * @author yangguang
 * @date 2020/3/24
 * @description <p></p>
 */
public class JoinDemo
{
    public static void main(String[] args) throws Exception
    {

        Thread a = new Thread(()->
        {
            try
            {
                TimeUnit.SECONDS.sleep(1);

            }catch (Exception e){}

            System.out.println("thread join");
        });
        a.start();
        a.join();

        System.out.println("main");
    }
}
