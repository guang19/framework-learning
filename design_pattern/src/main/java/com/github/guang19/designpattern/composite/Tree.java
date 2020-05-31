package com.github.guang19.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 树枝构件: 树
 * @since 1.0.0
 */
public class Tree implements Component
{
    //树的子部件是树枝
    private List<Component> treeBranches = new ArrayList<>();

    private String name;

    public Tree(String name)
    {
        this.name = name;
    }

    @Override
    public void add(Component component)
    {
        this.treeBranches.add(component);
    }

    @Override
    public void remove(Component component)
    {
        this.treeBranches.remove(component);
    }

    @Override
    public List<Component> getChildren()
    {
        return treeBranches;
    }

    @Override
    public void operation()
    {
        System.out.println("********************************************************************************************************");
        System.out.println("当前Tree: " + name + "的操作: ");
        for (Component branch : treeBranches)
        {
            branch.operation();
        }
        System.out.println("********************************************************************************************************");
    }
}
