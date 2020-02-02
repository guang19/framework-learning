package com.github.yangguang19.jvm.gcs;

import java.util.ArrayList;

/**
 * @Description : TODO      测试G1回收
 * @Author :    yangguang
 * @Date :      2019/11/28
 */
public class G1GC {

    /**
     *
     * 因为我的机器的jdk版本是11,所以无需指定垃圾回收器
     * 指定G1回收器的参数是: -XX:+UseG1GC
     *
     * G1回收和CMS相似分4个阶段,但和CMS不同的是,G1的所有阶段都不需要停顿:
     * 1:初始标记:
     *   标记所有与GC Root直接可达的对象
     *
     * 2:并发标记
     * 　从第一个阶段标记的对象开始,trace标记
     *
     * 4:重新标记
     * 　在第二步并发标记的阶段,由于程序执行,导致被标记对象之间的引用关系发生变化,所以需要重新调整标记
     *
     * 5:筛选回收:
     *  和CMS的并发回收不一样,这里G1会在较短时间内,优先筛选出Region较大的区域进行回收,
     *  这样可以保证在有限的时间内获得最大的回收率.
     *
     */
    public static void main(String[] args)
    {
        ArrayList<Object> list = new ArrayList<>();
        while (true)
        {
            list.add(new Object());
        }
    }
}
