package com.github.guang19.designpattern.decorator;

/**
 * @author guang19
 * @date 2020/5/24
 * @description     具体构件
 * @since 1.0.0
 */
public class ConcreteComponent implements Component
{
    @Override
    public void operation()
    {
        System.out.println("concrete component operation method.");
    }
}
