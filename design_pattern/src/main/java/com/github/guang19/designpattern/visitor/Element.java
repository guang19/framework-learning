package com.github.guang19.designpattern.visitor;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 抽象元素
 * @since 1.0.0
 */
public abstract class Element
{
    public abstract void accept(Visitor visitor);
}
