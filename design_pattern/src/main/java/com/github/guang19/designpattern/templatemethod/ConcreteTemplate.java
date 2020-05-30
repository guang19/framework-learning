package com.github.guang19.designpattern.templatemethod;

/**
 * @author guang19
 * @date 2020/5/29
 * @description 具体模板
 * @since 1.0.0
 */
public class ConcreteTemplate extends AbstractTemplate
{
    @Override
    protected void check()
    {
        System.out.println("校验用户的信息");
    }

    @Override
    protected void doSomething()
    {
        System.out.println("钩子方法 ： doSomething");
    }
}
