package com.github.yangguang19.jvm.gcs;

import java.util.ArrayList;

/**
 * @Description : TODO          测试 Parallel Scavenge 收集器
 * @Author :    yangguang
 * @Date :      2019/11/28
 */
public class ParallelScavengeGC {
    /**
     * 
     * 设置 Parallel Scavenge 收集器的参数:
     * -XX:+UseParallelGC
     * 
     * ParallelGC老年代默认使用的 Parallel Old GC 回收器
     *
     * 并行收集器打印的年轻代的信息为:
     *  PSYoungGen ....
     *
     *  老年代的信息为:
     *  ParOldGen ....
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
