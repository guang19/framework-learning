package com.github.guang19.designpattern.flyweight;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 享元模式
 * @since 1.0.0
 */
public class FlyweightTest
{
    public static void main(String[] args)
    {
        PersonFactory personFactory = new PersonFactory();
        Person person1 = personFactory.getPerson("programmer1");
        Person person2 = personFactory.getPerson("programmer2");


        System.out.println(person1);
        System.out.println(person2);

        person1.work(new BasicInfo(18,170));
        person2.work(new BasicInfo(20,170));


        Person person3 = personFactory.getPerson("programmer1");
        Person person4 = personFactory.getPerson("programmer2");

        System.out.println(person3 == person1);
        System.out.println(person4 == person2);
    }
}
