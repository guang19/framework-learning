package com.github.guang19.designpattern.responsibilitychain;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 责任链模式测试
 * @since 1.0.0
 */
public class ResponsibilityChainTest
{
    public static void main(String[] args)
    {
        ConcreteRequestHandler1 requestHandler1 = new ConcreteRequestHandler1();
        ConcreteRequestHandler2 requestHandler2 = new ConcreteRequestHandler2();

        requestHandler1.setRequestHandler(requestHandler2);

        requestHandler1.handle("http请求");
    }
}
