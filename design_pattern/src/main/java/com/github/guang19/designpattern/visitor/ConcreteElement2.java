package com.github.guang19.designpattern.visitor;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体元素2
 * @since 1.0.0
 */
public class ConcreteElement2 extends Element
{
    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
