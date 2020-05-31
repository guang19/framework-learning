package com.github.guang19.designpattern.state;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 状态模式测试
 * @since 1.0.0
 */
public class StateTest
{
    public static void main(String[] args)
    {
        Context context = new Context();

        context.doAction();
        context.doAction();
    }
}
