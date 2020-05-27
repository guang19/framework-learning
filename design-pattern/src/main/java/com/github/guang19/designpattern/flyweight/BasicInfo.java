package com.github.guang19.designpattern.flyweight;

/**
 * @author guang19
 * @date 2020/5/27
 * @description     非享元角色： Person的基本信息
 * @since 1.0.0
 */
public class BasicInfo
{
    private int age;

    private int height;

    public BasicInfo(){}

    public BasicInfo(int age, int height)
    {
        this.age = age;
        this.height = height;
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
