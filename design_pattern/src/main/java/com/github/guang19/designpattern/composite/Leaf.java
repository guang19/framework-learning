package com.github.guang19.designpattern.composite;

import java.util.List;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 树叶构件： 树叶
 * @since 1.0.0
 */
public class Leaf implements Component
{

    private String name;

    public Leaf(String name)
    {
        this.name = name;
    }

    //树叶构件没有子部件，所以无法添加，删除和获取子部件
    @Override
    public void add(Component component)
    {

    }

    @Override
    public void remove(Component component)
    {

    }

    @Override
    public List<Component> getChildren()
    {
        return null;
    }

    @Override
    public void operation()
    {
        System.out.println("***********************************************************");
        System.out.println("当前Leaf: " + name);
        System.out.println("***********************************************************");
    }
}
