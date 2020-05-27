package com.github.guang19.designpattern.flyweight;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 具体享元角色: Programmer
 * @since 1.0.0
 */
public class Programmer extends Person
{
    @Override
    public void work(BasicInfo basicInfo)
    {
        setAge(basicInfo.getAge());
        setHeight(basicInfo.getHeight());

        System.out.println("i am coding : " + this);
    }

    @Override
    public String toString()
    {
        return "Programmer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
