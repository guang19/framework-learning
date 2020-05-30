package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description  Haier工厂，生产 Haier空调和冰箱
 * @since 1.0.0
 */
public class HaierFactory extends AbstractFactory
{
    @Override
    public AirConditioning createAirConditioning()
    {
        return new HaierAirConditioning();
    }

    @Override
    public Fridge createFridge()
    {
        return new HaierFridge();
    }
}
