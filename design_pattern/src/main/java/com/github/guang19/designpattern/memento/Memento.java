package com.github.guang19.designpattern.memento;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 备忘录
 * @since 1.0.0
 */
public class Memento
{
    //状态
    private String state;

    public Memento(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
