package com.github.yangguang19.jvm.gcs;

import java.util.ArrayList;

/**
 * @Description : TODO      测试 CMS(Concurrent Mark Sweep) 收集器
 * @Author :    yangguang
 * @Date :      2019/11/28
 */
public class CMSGC {

    /**
     *
     * 设置 CMS 收集器参数:
     * -XX:+UseConcMarkSweepGC
     *
     * 使用ConcMarkSweepGC收集器后,它的年轻代使用的是:
     * ParNew收集器.
     *
     * 当ConcMarkSweepGC收集器出现异常时,会将CMS替换成Serial Old收集器
     *
     * CMS回收分为4个阶段:
     *
     * 初始标记:
     * 标记与GC Root直接可达的对象.       (Stop the world 暂停用户线程)
     *
     * 并发标记:
     * 从第一步标记的可达的对象开始,并发的标记所有可达的对象 (并发的,不暂停用户线程)
     *
     * 重新标记:
     * 在第二步的并发标记阶段,由于程序运行导致被标记对象间引用的关系发生变化,就需要重新标记 (Stop the world 暂停用户线程)
     *
     * 并发清除:        (并发的,不暂停用户线程)
     * 这个阶段不暂停用户线程,并且并发的去清除未被标记的对象
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
