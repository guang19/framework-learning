package com.github.guang19.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/28
 * @description 抽象目标角色
 * @since 1.0.0
 */
public abstract class AbstractTarget
{
    //注册在此目标的观察者
    protected List<Observer> observerList = new ArrayList<>();

    //添加观察者
    public void addObserver(Observer observer)
    {
        this.observerList.add(observer);
    }

    //移除观察者
    public void removeObserver(Observer observer)
    {
        this.observerList.remove(observer);
    }


    public void doSomething()
    {
        System.out.println("target update");
        notifyAllObserver();
    }

    //通知所有的观察者
    public abstract void notifyAllObserver();
}
