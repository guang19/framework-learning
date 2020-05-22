package com.github.guang19.designpattern.proxy;

/**
 * @author guang19
 * @date 2020/5/22
 * @description 代理模式测试
 * @since 1.0.0
 */
public class ProxyTest
{
    public static void main(String[] args)
    {
        Subject realSubject = new RealSubject();
        Subject proxySubject = new ProxySubject(realSubject);

        proxySubject.response("127.0.0.1");

    }
}
