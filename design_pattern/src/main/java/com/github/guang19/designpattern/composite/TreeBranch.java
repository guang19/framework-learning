package com.github.guang19.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 树枝构件: 树枝
 * @since 1.0.0
 */
public class TreeBranch implements Component
{
    //树枝的子部件是树叶
    private List<Component> leaves = new ArrayList<>();

    private String name;

    public TreeBranch(String name)
    {
        this.name = name;
    }

    @Override
    public void add(Component component)
    {
        this.leaves.add(component);
    }

    @Override
    public void remove(Component component)
    {
        this.leaves.remove(component);
    }

    @Override
    public List<Component> getChildren()
    {
        return leaves;
    }

    @Override
    public void operation()
    {
        System.out.println("***********************************************************");
        System.out.println("当前TreeBranch: " + name + "的操作: ");
        for (Component leaf : leaves)
        {
            leaf.operation();
        }
        System.out.println("***********************************************************");
    }
}
