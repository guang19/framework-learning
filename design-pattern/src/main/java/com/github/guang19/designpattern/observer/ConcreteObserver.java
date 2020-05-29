package com.github.guang19.designpattern.observer;

/**
 * @author guang19
 * @date 2020/5/28
 * @description 具体观察角色
 * @since 1.0.0
 */
public class ConcreteObserver implements Observer
{
    @Override
    public void update()
    {
        System.out.println("observer has be notified.");
    }
}
