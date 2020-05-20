package com.github.guang19.designpattern.builder;

/**
 * @author guang19
 * @date 2020/5/20
 * @description 构建者测试
 * @since 1.0.0
 */
public class BuilderTest
{
    public static void main(String[] args)
    {
        ComputerAbstractBuilder computerBuilder = new ConcreteComputerBuilder();
        computerBuilder.buildCpu("my cpu");
        computerBuilder.buildGpu("my gpu");
        computerBuilder.buildKeyboard("my keyboard");

        Director director = new Director(computerBuilder);

        Computer computer = director.construct();

        System.out.println(computer);
    }
}
