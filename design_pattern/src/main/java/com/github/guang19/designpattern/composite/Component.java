package com.github.guang19.designpattern.composite;

import java.util.List;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 抽象构件
 * @since 1.0.0
 */
public interface Component
{
    //添加子部件
    public void add(Component component);

    //移除子部件
    public  void remove(Component component);

    //获取子部件
    public List<Component> getChildren();

    //当前部件的操作
    public void operation();
}
