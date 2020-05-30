package com.github.guang19.designpattern.builder;

/**
 * @author guang19
 * @date 2020/5/20
 * @description Computer指挥者
 * @since 1.0.0
 */
public class Director
{
    private ComputerAbstractBuilder computerBuilder;

    public Director(ComputerAbstractBuilder computerBuilder)
    {
        this.computerBuilder = computerBuilder;
    }

    //指挥ComputerBuilder创建Computer
    public Computer construct()
    {
        return computerBuilder.buildComputer();
    }
}
