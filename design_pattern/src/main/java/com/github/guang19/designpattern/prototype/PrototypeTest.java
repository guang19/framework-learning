package com.github.guang19.designpattern.prototype;

/**
 * @author guang19
 * @date 2020/5/21
 * @description
 * @since 1.0.0
 */
public class PrototypeTest
{
    public static void main(String[] args) throws CloneNotSupportedException
    {
        ConcretePrototype concretePrototype = new ConcretePrototype();
        ConcretePrototype cloneConcretePrototype= (ConcretePrototype)concretePrototype.clone();

        System.out.println(concretePrototype == cloneConcretePrototype);
        System.out.println("concretePrototype hashcode : " + concretePrototype.hashCode());
        System.out.println("cloneConcretePrototype hashcode : " + cloneConcretePrototype.hashCode());
    }
}
