package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description  抽象工厂
 * @since 1.0.0
 */
public abstract class AbstractFactory
{
    //创建空调
    public abstract AirConditioning createAirConditioning();

    //创建冰箱
    public abstract Fridge          createFridge();
}
