package com.github.guang19.jvm.jvmerror;

/**
 * @Description : TODO              测试JVM会抛出的StackOverflowError
 * @Author :    yangguang
 * @Date :      2019/11/27
 */
public class StackOverflowErrorDemo {

    /**
     *
     *  StackOverflowError:
     *  当前线程执行或请求的栈的大小超过了Java虚拟机栈的最大空间(比如递归嵌套调用太深),那么抛出StackOverflowError错误
     *
     */

    //测试 StackOverflowError 错误
    public static void stackOverflowError()
    {
        stackOverflowError();
    }

    public static void main(String[] args)
    {
        stackOverflowError();
    }
}
