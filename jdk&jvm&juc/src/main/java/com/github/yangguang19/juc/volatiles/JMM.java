package com.github.yangguang19.juc.volatiles;

/**
 * @Description : TODO      JMM : Java Memory Model Java内存模型(讲解Volatile的作用)
 * @Author :    yangguang
 * @Date :      2019/11/20
 */
public class JMM {

    /**
     * 以一个共享变量number 测试volatile的作用
     */

    //测试变量
    private volatile int number = 10;

    public static void main(String[] args) throws Exception
    {
        JMM jmm = new JMM();

        //线程一:修改number的值
        Runnable run1 = ()->{
            try
            {
                //让当前线程停顿3秒,让线程2先行
                Thread.sleep(3000);
                //改变number的值
                jmm.number = 15;
                System.out.println(Thread.currentThread().getName() + " 改变了number的值");
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        };

        //线程二:如果number没被修改,就不结束
        Runnable run2 = ()->{
            //如果number的值一直为10,就不结束线程
            while (jmm.number == 10){}
        };

        new Thread(run1).start();
        new Thread(run2).start();
    }
}
