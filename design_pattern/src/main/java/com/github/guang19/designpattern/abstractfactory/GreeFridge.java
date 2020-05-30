package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description     Gree 冰箱
 * @since 1.0.0
 */
public class GreeFridge extends Fridge
{
    @Override
    public void storeFood(String food)
    {
        System.out.println("use Gree fridge store food.");
    }
}
