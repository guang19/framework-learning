package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description 抽象工厂测试
 * @since 1.0.0
 */
public class AbstractFactoryTest
{
    public static void main(String[] args)
    {
        AbstractFactory haierFactory = new HaierFactory();
        AirConditioning haierAirConditioning = haierFactory.createAirConditioning();
        Fridge haierFridge = haierFactory.createFridge();

        haierAirConditioning.adjustTemperature(30);
        haierFridge.storeFood("tomato");

        AbstractFactory greeFactory = new GreeFactory();
        AirConditioning greeAirConditioning = greeFactory.createAirConditioning();
        Fridge greeFridge = greeFactory.createFridge();

        greeAirConditioning.adjustTemperature(25);
        greeFridge.storeFood("potato");
    }
}
