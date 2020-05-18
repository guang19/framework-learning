package com.github.guang19.designpattern.abstractfactory;

/**
 * @author guang19
 * @date 2020/5/18
 * @description haier 冰箱
 * @since 1.0.0
 */
public class HaierFridge extends Fridge
{
    @Override
    public void storeFood(String food)
    {
        System.out.println("use Haier fridge store food.");
    }
}
