package com.github.guang19.designpattern.iterator;

/**
 * @author guang19
 * @date 2020/5/31
 * @description     抽象聚合
 * @since 1.0.0
 */
public interface Collection
{
    public void add(Object obj);
    public void remove(Object obj);

    public Iterator iterator();
}
