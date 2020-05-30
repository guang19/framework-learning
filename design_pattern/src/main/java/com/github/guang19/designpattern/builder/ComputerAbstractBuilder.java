package com.github.guang19.designpattern.builder;

/**
 * @author guang19
 * @date 2020/5/20
 * @description Computer抽象建造者
 * @since 1.0.0
 */
public abstract class ComputerAbstractBuilder
{
    protected Computer computer = new Computer();

    //各个组件的构造定义
    public abstract void buildCpu(String cpu);
    public abstract void buildGpu(String gpu);
    public abstract void buildKeyboard(String keyboard);


    //创建Computer
    public Computer buildComputer()
    {
        return computer;
    }
}
