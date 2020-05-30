package com.github.anhTom2000.datastructure.list;


import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author guang19
 * @date 2020/4/29
 * @description 基于双端链表实现的线性表,队列和栈,头节点的prev是null，last节点的next也是null
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
        public Node()
        {
        }

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
     *
     * @param element 要添加的元素
     */
    public void add(T element)
    {
        //如果队列为空,那么新节点既是首节点，也是尾节点
        final Node<T> oldTail = tail;
        tail = new Node<>(null, element, null);
        //如果队列不为空，那么新节点就是尾节点
        if (oldTail != null)
        {
            oldTail.next = tail;
            tail.prev = oldTail;
        }
        //如果链表没有节点
        else
        {
            head = tail;
        }
        ++size;
    }

    /**
     * 添加指定元素到链表末尾
     * @param element 要添加的元素
     */
    public void offer(T element)
    {
        add(element);
    }

    /**
     * 将元素入栈
     * @param element 要入栈的元素
     */
    public void push(T element)
    {
        add(element);
    }

    /**
     * 将栈顶元素出栈,如果栈为空，抛出异常
     */
    public T pop()
    {
        if(size == 0)
        {
            throw new IllegalStateException("stack is empty");
        }
        return unlinkTail();
    }

    //移除尾节点
    private T unlinkTail()
    {
        T oldVal = tail.data;
        tail.data = null;
        Node<T> newTail = tail.prev;
        tail.prev = null;
        tail = newTail;
        if(newTail == null)
        {
            head = null;
        }
        else
        {
            newTail.next = null;
        }
        --size;
        return oldVal;
    }

    /**
     * 移除指定位置的节点
     *
     * @param index 指定下标
     * @return 移除的节点的值
     */
    public T remove(int index)
    {
        Objects.checkIndex(index, size);
        //要删除的节点
        Node<T> removeNode = null;
        T oldVal = null;
        //先根据index的值，判断是从head节点开始遍历快还是从tail节点遍历快
        if (index < (size >> 1))
        {
            //从头节点开始遍历
            removeNode = head;
            while (index > 0)
            {
                removeNode = removeNode.next;
                --index;
            }
        }
        else
        {
            //从尾节点开始遍历
            removeNode = tail;
            int nSize = size - 1;
            while (index < nSize)
            {
                removeNode = removeNode.prev;
                ++index;
            }
        }
        return unlinkNode(removeNode);
    }

    /**
     * 删除指定元素的节点，只删除第一个节点
     * @param element   指定的元素
     * @return          是否删除成功
     */
    public boolean remove(T element)
    {
        if (element != null)
        {
            Node<T> cur = head;
            while (cur != null)
            {
                if (Objects.equals(cur,element))
                {
                    unlinkNode(cur);
                    return true;
                }
                cur = cur.next;
            }
        }
        return false;
    }

    //将指定节点从链表中移除
    private T unlinkNode(Node<T> removeNode)
    {
        //更新要删除的节点的前后节点的引用
        Node<T> prev = removeNode.prev;
        Node<T> next = removeNode.next;

        //prev为null，代表删除的是head节点，直接把head指向next就行了
        if (prev == null)
        {
            head = next;
        }
        else
        {
            prev.next = next;
            //gc
            removeNode.prev = null;
        }
        //next为null，代表删除的是tail节点,直接把tail指向prev就行了
        if (next == null)
        {
            tail = prev;
        }
        else
        {
            next.prev = prev;
            //gc
            removeNode.next = null;
        }
        T oldVal = removeNode.data;
        //gc
        removeNode.data = null;
        removeNode = null;
        --size;
        return oldVal;
    }


    /**
     * 删除队列的首节点,如果队列为空，则抛出异常
     * @return 手节点的值
     */
    public T poll()
    {
        if (size == 0)
        {
            throw new IllegalStateException("queue is empty");
        }
        return unlinkHead();
    }

    //移除首节点
    private T unlinkHead()
    {
        T oldVal = head.data;
        Node<T> newHead = head.next;
        head.data = null;
        head.next = null;
        head = newHead;
        if (newHead == null)
        {
            tail = null;
        }
        else
        {
            newHead.prev = null;
        }
        --size;
        return oldVal;
    }


    //判断队列是否为空
    public boolean isEmpty()
    {
        return size == 0;
    }


    //获取节点数量
    public int size()
    {
        return size;
    }


    //toString
    @Override
    public String toString()
    {
        StringJoiner stringJoiner = new StringJoiner(",","[","]");
        Node<T> cur = head;
        while (cur != null)
        {
            stringJoiner.add(cur.data == null ? "null" : cur.data.toString());
            cur = cur.next;
        }
        return stringJoiner.toString();
    }

    public static void main(String[] args)
    {
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        System.out.println(linkedList.remove(1));

        System.out.println(linkedList.size);
        System.out.println(linkedList);


        System.out.println("---------------------------");
        LinkedList<String> stack = new LinkedList<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        System.out.println(stack.pop());
        System.out.println(stack.size);
        System.out.println(stack);

        System.out.println("---------------------------");
        LinkedList<String> queue = new LinkedList<>();
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.size);
        System.out.println(queue);
    }
}
