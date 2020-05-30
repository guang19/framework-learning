package com.github.guang19.designpattern.builder;

/**
 * @author guang19
 * @date 2020/5/20
 * @description  具体Computer构建者
 * @since 1.0.0
 */
public class ConcreteComputerBuilder extends ComputerAbstractBuilder
{
    @Override
    public void buildCpu(String cpu)
    {
        this.computer.setCpu(cpu);
    }

    @Override
    public void buildGpu(String gpu)
    {
        this.computer.setGpu(gpu);
    }

    @Override
    public void buildKeyboard(String keyboard)
    {
        this.computer.setKeyboard(keyboard);
    }
}
