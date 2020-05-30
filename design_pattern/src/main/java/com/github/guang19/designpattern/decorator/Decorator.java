package com.github.guang19.designpattern.decorator;

/**
 * @author guang19
 * @date 2020/5/24
 * @description 抽象装饰
 * @since 1.0.0
 */
public abstract class Decorator implements Component
{
    private Component component;

    public Decorator(Component component)
    {
        this.component = component;
    }

    @Override
    public void operation()
    {
        component.operation();
    }
}
