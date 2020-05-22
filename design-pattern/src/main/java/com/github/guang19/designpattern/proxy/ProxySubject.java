package com.github.guang19.designpattern.proxy;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author guang19
 * @date 2020/5/22
 * @description 代理主题
 * @since 1.0.0
 */
public class ProxySubject implements Subject
{
    //真实主题
    private Subject realSubject;

    private long begin;


    public ProxySubject(Subject realSubject)
    {
        this.realSubject = realSubject;
    }

    @Override
    public void response(String client)
    {
        beforeResponse();
        realSubject.response(client);
        afterResponse();
    }

    //对真实主题做出前置扩展
    private void beforeResponse()
    {
        this.begin = System.currentTimeMillis();
    }

    //对真实主题做出后置扩展
    private void afterResponse()
    {
        System.out.println("response spend time ： " + (System.currentTimeMillis() - begin) + " milliseconds.");
    }
}
