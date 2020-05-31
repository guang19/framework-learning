package com.github.guang19.designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 具体迭代器
 * @since 1.0.0
 */
public class MyIterator implements Iterator
{
    private List<Object> list;

    private int cur = 0;

    public MyIterator()
    {
        this.list = new ArrayList<>();
    }

    public MyIterator(List<Object> list)
    {
        this.list = list;
    }

    @Override
    public Object first()
    {
        return list.get(cur = 0);
    }

    @Override
    public Object next()
    {
        if (hasNext())
        {
            return this.list.get(cur++);
        }
        return null;
    }

    @Override
    public boolean hasNext()
    {
        if (cur < list.size())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
