package com.github.guang19.designpattern.command;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 命令模式测试
 * @since 1.0.0
 */
public class CommandTest
{
    public static void main(String[] args)
    {
        Command command = new ConcreteCommand(new Receiver());

        Invoker invoker = new Invoker(command);
        invoker.invoke();
    }
}
