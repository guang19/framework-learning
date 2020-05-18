package com.github.guang19.designpattern.factorymethod;

/**
 * @author guang19
 * @date 2020/5/18
 * @description 具体工厂2 , 生产具体产品2
 * @since 1.0.0
 */
public class ConcreteFactory2 extends AbstractFactory
{
    @Override
    public AbstractProduct createProduct()
    {
        return new ConcreteProduct2();
    }
}
