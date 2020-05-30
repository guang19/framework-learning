package com.github.guang19.designpattern.factorymethod;

/**
 * @author guang19
 * @date 2020/5/18
 * @description 具体产品2，由具体工厂2创建
 * @since 1.0.0
 */
public class ConcreteProduct2 extends AbstractProduct
{
    @Override
    public void show()
    {
        System.out.println("concrete product2 was created by concrete factory2");
    }
}
