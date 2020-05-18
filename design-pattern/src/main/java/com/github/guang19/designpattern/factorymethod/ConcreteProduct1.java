package com.github.guang19.designpattern.factorymethod;

/**
 * @author guang19
 * @date 2020/5/18
 * @description     具体产品1，由具体工厂1生产
 * @since 1.0.0
 */
public class ConcreteProduct1 extends AbstractProduct
{
    @Override
    public void show()
    {
        System.out.println("concrete product1 was created by concrete factory1");
    }
}
