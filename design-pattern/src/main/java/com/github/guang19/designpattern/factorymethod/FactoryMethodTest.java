package com.github.guang19.designpattern.factorymethod;

/**
 * @author guang19
 * @date 2020/5/18
 * @description     工厂方法测试
 * @since 1.0.0
 */
public class FactoryMethodTest
{
    public static void main(String[] args)
    {
        AbstractFactory abstractFactory1 = new ConcreteFactory1();
        AbstractProduct product1 = abstractFactory1.createProduct();

        AbstractFactory abstractFactory2 = new ConcreteFactory2();
        AbstractProduct product2 = abstractFactory2.createProduct();


        product1.show();

        product2.show();

    }
}
