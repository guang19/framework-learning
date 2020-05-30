package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description     Gree 工厂， 生产 Gree 空调和冰箱
 * @since 1.0.0
 */
public class GreeFactory extends AbstractFactory
{
    @Override
    public AirConditioning createAirConditioning()
    {
        return new GreeAirConditioning();
    }

    @Override
    public Fridge createFridge()
    {
        return new GreeFridge();
    }
}
