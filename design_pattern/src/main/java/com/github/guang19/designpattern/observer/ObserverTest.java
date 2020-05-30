package com.github.guang19.designpattern.observer;

/**
 * @author guang19
 * @date 2020/5/28
 * @description
 * @since 1.0.0
 */
public class ObserverTest
{
    public static void main(String[] args)
    {
        AbstractTarget abstractTarget = new ConcreteTarget();
        Observer observer1 = new ConcreteObserver();
        Observer observer2 = new ConcreteObserver();

        abstractTarget.addObserver(observer1);
        abstractTarget.addObserver(observer2);


        abstractTarget.doSomething();
    }
}
