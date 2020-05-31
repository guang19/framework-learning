package com.github.guang19.designpattern.command;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 命令发送者
 * @since 1.0.0
 */
public class Invoker
{
    private Command command;

    public Invoker(Command command)
    {
        this.command = command;
    }

    public void invoke()
    {
        System.out.println("invoker 调用command");
        command.execute();
    }
}
