package com.github.guang19.designpattern.visitor;


/**
 * @author guang19
 * @date 2020/5/30
 * @description 访问者模式测试
 * @since 1.0.0
 */
public class VisitorTest
{
    public static void main(String[] args) throws Exception
    {
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.add(new ConcreteElement1());
        objectStructure.add(new ConcreteElement2());

        Visitor visitor1 = new ConcreteVisitor1();
        Visitor visitor2 = new ConcreteVisitor2();

        objectStructure.accept(visitor1);
        objectStructure.accept(visitor2);
    }
}
