package com.github.guang19.designpattern.iterator;

/**
 * @author guang19
 * @date 2020/5/31
 * @description   抽象迭代器
 * @since 1.0.0
 */
public interface Iterator
{
    public abstract Object first();
    public abstract Object next();
    public abstract boolean hasNext();
}
