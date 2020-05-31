package com.github.guang19.designpattern.state;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 环境上下文
 * @since 1.0.0
 */
public class Context
{
    private State state;

    public Context()
    {
        this.state = new StartState();
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public State getState()
    {
        return state;
    }

    public void doAction()
    {
        this.state.doAction(this);
    }
}
