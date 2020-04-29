package com.github.anhTom2000.datastructure.list;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author guang19
 * @date 2020/4/29
 * @description 基于数组实现的ArrayList线性表
 */
public class ArrayList<T>
{
    //数组线性表
    private Object[] list = {};

    //线性表中元素的数量
    private int size;

    //默认初始化容量,第一次添加的时候，如果list尚未初始化，那么就初始化list的容量为10
    private static final int DEFAULT_CAPACITY = 10;

    //以我的测试，虚拟机最大能为数组分配的容量是 Integer.MAX_VALUE - 2
    private static final int MAX_CAPACITY = Integer.MAX_VALUE - 2;

    /**
     * 默认初始化
     */
    public ArrayList() {}

    /**
     * 容量初始化
     * @param capacity 指定的容量
     */
    public ArrayList(int capacity)
    {
        this.list = new Object[capacity];
    }

    /**
     * 添加元素
     * @param element 指定的元素
     */
    public void add(T element)
    {
        addLast(element);
    }

    //向数组末尾添加元素
    private void addLast(T element)
    {
        list = grow(list,size);
        list[size] = element;
        ++size;
    }

    //将数组扩容为原来的1/2倍
    private Object[] grow(Object[] list,int size)
    {
        //如果元素数量已经达到了数组长度那么扩容为原来的1/2倍
        if(size == list.length)
        {
             //最小的容量就是当前元素数量+1
             int minCapacity = size + 1;
             //扩容为原来的1/2倍
             int newCapacity = size + (size >> 1);
             //如果扩容后的容量仍然不足以容纳，那么可能是list未初始化或者数组溢出
             if (newCapacity - minCapacity <= 0)
             {
                 //未初始化就初始化
                 if (list.length == 0)
                 {
                     //创建新数组
                     return new Object[DEFAULT_CAPACITY];
                 }
                 //如果mincapacity 超出了最大容量 说明数组的元素数量导致它溢出了
                 if(minCapacity >= MAX_CAPACITY)
                 {
                     throw new OutOfMemoryError("list overflow");
                 }
             }
             Object[] newList = null;
             //如果新的容量超过了最大容量，那么不再扩容
             if(newCapacity < MAX_CAPACITY)
             {
                 newList = new Object[newCapacity];
             }
             else
             {
                 newList = new Object[MAX_CAPACITY];
             }
            System.arraycopy(list,0,newList,0,size);
            return newList;
        }
        else
        {
            return list;
        }
    }


    /**
     * 移除指定位置的元素
     * @param index 指定的下标
     * @return      被移除的元素
     */
    @SuppressWarnings("unchecked")
    public T remove(int index)
    {
        //这里第二个参数为啥不写list.length,而是size呢？
        //因为list只会扩容，不会缩容,不会缩容就意味着对尾的元素可能为null，所以就无需删除队尾元素
        final int currentSize = size;
        Objects.checkIndex(index,currentSize);
        return removeByIndex(list,index,currentSize);
    }

    //通过下标移除指定元素
    public T removeByIndex(Object[] list , int index,int size)
    {
        T oldVal = (T)list[index];
        int newSize = size - 1;
        //如果队列中不止一个元素
        if(newSize > index)
        {
            System.arraycopy(list,index + 1,list,index,newSize - index);
        }
        list[newSize] = null;
        size = newSize;
        return oldVal;
    }

    /**
     * 删除线性表中第一次出现的指定的值
     * @param removeVal 要删除的指定值
     */
    public void remove(T removeVal)
    {
        final int currentSize = size;
        int i = 0;
        for (; i < currentSize; ++i)
        {
            if(Objects.equals(removeVal,list[i]))
            {
                 break;
            }
        }
        this.removeByIndex(list,i,currentSize);
    }


    /**
     * 设置指定位置的元素为新值
     * @param index  指定的下标
     * @param newVal 新值
     * @return       旧值
     *
     */
    @SuppressWarnings("unchecked")
    public T set(int index,T newVal)
    {
        Objects.checkIndex(index,size);
        T oldVal = (T)list[index];
        list[index] = newVal;
        return oldVal;
    }


    //返回list的元素数量
    public int size()
    {
        return size;
    }


    //toString
    @Override
    public String toString()
    {
        StringJoiner stringJoiner = new StringJoiner(",","[","]");
        for (Object element : list)
        {
            if(element != null)
            {
                stringJoiner.add(element.toString());
            }
        }
        return stringJoiner.toString();
    }

    public static void main(String[] args)
    {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.addLast(1);
        list1.addLast(2);
        list1.addLast(3);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        System.out.println(list1.set(1, 3));
        System.out.println(list1);

    }
}
