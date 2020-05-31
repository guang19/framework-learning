package com.github.guang19.designpattern.composite;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 组合模式测试
 * @since 1.0.0
 */
public class CompositeTest
{
    public static void main(String[] args)
    {
        Component tree = new Tree("root");

        Component branch1 = new TreeBranch("branch1");
        Component branch2 = new TreeBranch("branch2");

        branch1.add(new Leaf("leaf1"));
        branch1.add(new Leaf("leaf2"));
        branch1.add(new Leaf("leaf3"));
        branch1.add(new Leaf("leaf4"));
        branch1.add(new Leaf("leaf5"));

        branch2.add(new Leaf("leaf6"));
        branch2.add(new Leaf("leaf7"));
        branch2.add(new Leaf("leaf8"));
        branch2.add(new Leaf("leaf9"));
        branch2.add(new Leaf("leaf10"));
        branch2.add(new Leaf("leaf11"));
        branch2.add(new Leaf("leaf12"));


        tree.add(branch1);
        tree.add(branch2);

        tree.operation();
    }
}
