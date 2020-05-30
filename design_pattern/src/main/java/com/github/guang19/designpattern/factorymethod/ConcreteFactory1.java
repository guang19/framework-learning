package com.github.guang19.designpattern.factorymethod;

/**
 * @author guang19
 * @date 2020/5/18
 * @description     具体工厂1,生产具体产品1
 * @since 1.0.0
 */
public class ConcreteFactory1 extends AbstractFactory
{
    @Override
    public AbstractProduct createProduct()
    {
        return new ConcreteProduct1();
    }
}
