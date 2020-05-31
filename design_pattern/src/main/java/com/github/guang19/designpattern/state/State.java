package com.github.guang19.designpattern.state;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 抽象状态
 * @since 1.0.0
 */
public abstract class State
{
    public abstract void doAction(Context context);
}
