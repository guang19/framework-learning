package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description haier空调
 * @since 1.0.0
 */
public class HaierAirConditioning extends AirConditioning
{
    @Override
    public void adjustTemperature(int temperature)
    {
        System.out.println("use Haier air conditioning adjust temperature.");
    }
}
