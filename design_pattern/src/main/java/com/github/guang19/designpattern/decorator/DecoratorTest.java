package com.github.guang19.designpattern.decorator;

/**
 * @author guang19
 * @date 2020/5/24
 * @description 装饰器模式测试
 * @since 1.0.0
 */
public class DecoratorTest
{
    public static void main(String[] args)
    {
        Component component = new ConcreteComponent();

        Decorator decorator = new ConcreteDecorator(component);

        decorator.operation();
    }
}
