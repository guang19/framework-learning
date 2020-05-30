package com.github.guang19.designpattern.mediator;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 抽象中介者
 * @since 1.0.0
 */
public abstract class Mediator
{
    //注册同事
    public abstract void registerColleague(Colleague colleague);

    //转发消息
    public abstract void forwardMessage(Colleague colleague,String message);
}
