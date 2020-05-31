package com.github.guang19.designpattern.memento;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 发起人
 * @since 1.0.0
 */
public class Originator
{
    //状态
    private String state;

    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    //创建备忘录
    public Memento createMemento()
    {
        return new Memento(state);
    }

    //根据备忘录重新恢复状态
    public void restoreMemento(Memento memento)
    {
        this.setState(memento.getState());
    }
}
