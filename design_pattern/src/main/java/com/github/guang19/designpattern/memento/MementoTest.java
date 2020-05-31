package com.github.guang19.designpattern.memento;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 备忘录模式测试
 * @since 1.0.0
 */
public class MementoTest
{
    public static void main(String[] args)
    {
        Originator originator = new Originator();
        originator.setState("初始化状态");

        System.out.println("originator的初始化状态:" + originator.getState());

        //保存初始化状态
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("更新后的状态");
        System.out.println("originator更新后的状态:" + originator.getState());

        originator.restoreMemento(caretaker.getMemento());
        System.out.println("originator恢复后的状态:" + originator.getState());
    }
}
