package com.github.yangguang19.jvm.gcs;

import java.util.ArrayList;

/**
 * @Description : TODO          测试ParNewGC收集器
 * @Author :    yangguang
 * @Date :      2019/11/27
 */
public class ParNewGC {
    /**
     * 
     * 设置ParNewGC回收器的参数为:
     * -XX:+UseParNewGC
     * 
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
