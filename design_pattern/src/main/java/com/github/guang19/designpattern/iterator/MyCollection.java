package com.github.guang19.designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 具体聚合
 * @since 1.0.0
 */
public class MyCollection implements Collection
{
    private List<Object> list = new ArrayList<>();

    @Override
    public void add(Object obj)
    {
        this.list.add(obj);
    }

    @Override
    public void remove(Object obj)
    {
        this.list.remove(obj);
    }

    @Override
    public Iterator iterator()
    {
        return new MyIterator(this.list);
    }
}
