package com.github.guang19.designpattern.mediator;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体同事1
 * @since 1.0.0
 */
public class ConcreteColleague1 extends Colleague
{
    @Override
    public void send(String message)
    {
        System.out.println("具体同事1发送消息 : " + message);
        mediator.forwardMessage(this,message);
    }

    @Override
    public void receive(String message)
    {
        System.out.println("具体同事1收到消息：" + message);
    }
}
