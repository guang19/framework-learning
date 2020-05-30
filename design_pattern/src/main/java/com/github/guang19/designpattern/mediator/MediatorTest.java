package com.github.guang19.designpattern.mediator;


/**
 * @author guang19
 * @date 2020/5/30
 * @description 中介者模式测试
 * @since 1.0.0
 */
public class MediatorTest
{
    public static void main(String[] args)
    {
        Mediator mediator = new ConcreteMediator();

        Colleague colleague1 = new ConcreteColleague1();
        Colleague colleague2 = new ConcreteColleague2();

        mediator.registerColleague(colleague1);
        mediator.registerColleague(colleague2);

        colleague1.send("同事1发送的消息");
        System.out.println("---------------------------------");
        colleague2.send("同事2发送的消息");
    }
}
