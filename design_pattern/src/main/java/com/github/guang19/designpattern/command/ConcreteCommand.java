package com.github.guang19.designpattern.command;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 具体命令
 * @since 1.0.0
 */
public class ConcreteCommand extends Command
{
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver)
    {
        this.receiver = new Receiver();
    }

    @Override
    public void execute()
    {
        System.out.println("command 执行");
        receiver.receive();
    }
}
