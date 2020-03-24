package com.github.guang19.jvm.gcs;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @Description : TODO          测试串行GC
 * @Author :    yangguang
 * @Date :      2019/11/27
 */
public class SerialGC {
    /**
     *
     * 设置VM参数:
     *  -XX:+Xlogs:gc* 打印gc信息
     *  -XX:+PrintCommandLineFlags  打印java版本信息
     *  -XX:+UseSerialGC 使用串行GC
     *
     */

    public static void main(String[] args)
    {
        ArrayList<Object> list = new ArrayList<>();
        
//        while (true)
//        {
//            list.add(new Object());
//        }
    }
}
