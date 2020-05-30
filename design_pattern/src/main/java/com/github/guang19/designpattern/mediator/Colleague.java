package com.github.guang19.designpattern.mediator;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 抽象同事角色
 * @since 1.0.0
 */
public abstract class Colleague
{
    protected Mediator mediator;

    public void setMediator(Mediator mediator)
    {
        this.mediator = mediator;
    }

    //发送消息
    public abstract void send(String message);

    //接收消息
    public abstract void receive(String message);
}
