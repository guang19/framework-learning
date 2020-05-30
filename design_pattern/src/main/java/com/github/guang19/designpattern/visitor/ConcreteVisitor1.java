package com.github.guang19.designpattern.visitor;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体访问者1
 * @since 1.0.0
 */
public class ConcreteVisitor1 extends Visitor
{
    @Override
    public void visit(ConcreteElement1 element1)
    {
        System.out.println("具体访问者1 访问 element1");
    }

    @Override
    public void visit(ConcreteElement2 element2)
    {
        System.out.println("具体访问者1 访问 element2");
    }
}
