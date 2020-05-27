package com.github.guang19.designpattern.flyweight;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 抽象享元角色： Person
 * @since 1.0.0
 */
public abstract class Person
{
    protected String name;

    protected int age;

    protected int height;

    public abstract void work(BasicInfo basicInfo);

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}
