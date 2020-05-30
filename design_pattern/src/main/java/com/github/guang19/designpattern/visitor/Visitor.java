package com.github.guang19.designpattern.visitor;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 抽象访问者
 * @since 1.0.0
 */
public abstract class Visitor
{
    //访问具体元素1
    public abstract void visit(ConcreteElement1 element1);

    //访问具体元素2
    public abstract void visit(ConcreteElement2 element2);
}
