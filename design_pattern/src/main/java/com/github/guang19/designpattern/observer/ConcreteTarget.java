package com.github.guang19.designpattern.observer;

/**
 * @author guang19
 * @date 2020/5/28
 * @description 具体目标角色
 * @since 1.0.0
 */
public class ConcreteTarget extends AbstractTarget
{
    @Override
    public void notifyAllObserver()
    {
        for (Observer observer : observerList)
        {
            observer.update();
        }
    }
}
