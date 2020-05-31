package com.github.guang19.designpattern.iterator;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 迭代器模式测试
 * @since 1.0.0
 */
public class IteratorTest
{
    public static void main(String[] args)
    {
        Collection collection = new MyCollection();
        collection.add(1);
        collection.add(2);
        collection.add(5);
        collection.add(4);
        collection.add(3);

        Iterator iterator = collection.iterator();
        while (iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
//        System.out.println(iterator.first());
    }
}
