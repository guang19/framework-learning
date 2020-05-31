package com.github.guang19.designpattern.memento;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 管理人
 * @since 1.0.0
 */
public class Caretaker
{
    private Memento memento;

    public void setMemento(Memento memento)
    {
        this.memento = memento;
    }

    public Memento getMemento()
    {
        return memento;
    }
}
