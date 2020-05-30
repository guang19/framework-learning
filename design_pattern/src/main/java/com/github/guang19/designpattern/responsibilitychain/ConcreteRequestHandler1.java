package com.github.guang19.designpattern.responsibilitychain;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体处理者1
 * @since 1.0.0
 */
public class ConcreteRequestHandler1 extends AbstractRequestHandler
{
    @Override
    public void handle(String request)
    {
        System.out.println("当前具体处理者1 处理请求 : " + request);

        if (getRequestHandler() != null)
        {
            getRequestHandler().handle(request);
        }
    }
}
