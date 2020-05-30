package com.github.guang19.designpattern.decorator;

/**
 * @author guang19
 * @date 2020/5/24
 * @description    具体装饰
 * @since 1.0.0
 */
public class ConcreteDecorator extends Decorator
{
    public ConcreteDecorator(Component component)
    {
        super(component);
    }

    @Override
    public void operation()
    {
        super.operation();
        addFunction();
    }

    public void addFunction()
    {
        System.out.println("concrete decorator add a function");
    }
}
