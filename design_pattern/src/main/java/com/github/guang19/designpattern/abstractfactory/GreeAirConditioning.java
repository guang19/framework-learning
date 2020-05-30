package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description Gree 空调
 * @since 1.0.0
 */
public class GreeAirConditioning extends AirConditioning
{
    @Override
    public void adjustTemperature(int temperature)
    {
        System.out.println("use Gree air conditioning adjust temperature.");
    }
}
