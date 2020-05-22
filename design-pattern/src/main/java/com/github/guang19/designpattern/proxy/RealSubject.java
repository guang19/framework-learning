package com.github.guang19.designpattern.proxy;

/**
 * @author guang19
 * @date 2020/5/22
 * @description 真实主题
 * @since 1.0.0
 */
public class RealSubject implements Subject
{
    @Override
    public void response(String client)
    {
        System.out.println("client : " + client + " request");
    }
}
