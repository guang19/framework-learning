package com.github.anhTom2000.datastructure.list;


import java.util.Objects;

/**
 * @author guang19
 * @date 2020/4/29
 * @description 基于双向链表实现的线性表和队列
 */
public class LinkedList<T>
{
    //双向链表的节点
    static class Node<T>
    {
        //当前节点的前一个节点
        Node<T> prev;
        //当前节点存储的数据
        T data;
        //当前节点的后一个节点
        Node<T> next;

        //默认构造
        public Node(){}

        //全参构造
        public Node(Node<T> prev, T data, Node<T> next)
        {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

    }

    //头节点
    private Node<T> head;

    //尾节点
    private Node<T> tail;

    //节点数量
    private int size;

    //空构造
    public LinkedList() {}

    /**
     * 添加指定元素
     * @param element 要添加的元素
     */
    public void add(T element)
    {
        //如果队列为空,那么新节点既是首节点，也是尾节点
        Node<T> newNode = new Node<>(tail,element,null);
        Node<T> oldTail = tail;
        tail = newNode;
        if(head == null)
        {
            head = newNode;
        }
        //如果队列不为空，那么新节点就是尾节点
        else
        {
            oldTail.next = tail;
        }
        ++size;
    }

    /**
     * 移除指定位置的节点
     * @param index 指定下标
     * @return      移除的节点的值
     */
    public T remove(int index)
    {
        Objects.checkIndex(index,size);

        //要删除的节点
        Node<T> removeNode = null;
        T oldVal = null;
        //先根据index的值，判断是从head节点开始遍历快还是从tail节点遍历快
        if(index < size >> 1)
        {
            //从头节点开始遍历
            removeNode = head;
            while (index > 0)
            {
                removeNode = removeNode.next;
                --index;
            }

            //更新要删除的节点的前后节点的引用
            Node<T> prev = removeNode.prev;
            Node<T> next = removeNode.next;
            oldVal = removeNode.data;
            removeNode = null;
            prev.next = next;
            next.prev = prev;
        }
        else
        {
            //从尾节点开始遍历
            removeNode = tail;
            while (index > 0)
            {
                removeNode = removeNode.prev;
                --index;
            }
        }
        --size;
    }
}
