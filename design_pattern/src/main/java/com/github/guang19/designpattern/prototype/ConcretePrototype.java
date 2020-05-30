package com.github.guang19.designpattern.prototype;

/**
 * @author guang19
 * @date 2020/5/21
 * @description 具体原型
 * @since 1.0.0
 */
public class ConcretePrototype implements Cloneable
{
    public ConcretePrototype(){}

    //实现抽象原型的clone方法
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
