package com.github.guang19.designpattern.mediator;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体同事2
 * @since 1.0.0
 */
public class ConcreteColleague2 extends Colleague
{
    @Override
    public void send(String message)
    {
        System.out.println("具体同事2发送消息 : " + message);
        mediator.forwardMessage(this,message);
    }

    @Override
    public void receive(String message)
    {
        System.out.println("具体同事2收到消息：" + message);
    }
}
