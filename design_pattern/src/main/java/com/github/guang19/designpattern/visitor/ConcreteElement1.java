package com.github.guang19.designpattern.visitor;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体元素1
 * @since 1.0.0
 */
public class ConcreteElement1 extends Element
{
    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
