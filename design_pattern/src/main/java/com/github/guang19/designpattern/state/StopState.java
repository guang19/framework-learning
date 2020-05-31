package com.github.guang19.designpattern.state;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 具体状态
 * @since 1.0.0
 */
public class StopState extends State
{
    @Override
    public void doAction(Context context)
    {
        System.out.println("context stop ...");
        context.setState(new StartState());
    }
}
