package com.github.anhTom2000.datastructure.queue;

import java.util.StringJoiner;

/**
 * @author guang19
 * @date 2020/4/29
 * @description 基于数组实现的循环队列
 */
public class ArrayCircleQueue<T>
{
    //数组存放元素
   private Object[] queue;

   //队列头指针，头指针直接指向首元素
   private int head;

   //队列尾指针,尾指针并不直接指向最后一个元素，而是指向最后一个元素的后一个位置，即tail指向的永远是一个空位置，队列可以使用的容量就始终为 capacity - 1
   private int tail;

   //队列的最大容量
   private final int capacity;

    /**
     * 空构造,默认容量为5
     */
   public ArrayCircleQueue()
   {
       this.queue = new Object[this.capacity = 5];
   }

    /**
     * 以队列容量构造
     * @param capacity 指定容量
     */
   public ArrayCircleQueue(int capacity)
   {
       if(capacity <= 0)
       {
           throw new IllegalArgumentException("the capacity of queue cannot be less than 0");
       }
       this.queue = new Object[this.capacity = capacity];
   }

    /**
     * 添加元素到队列尾，如果队列已满，将抛出异常
     * @param element   要添加的元素
     */
   public void offer(T element)
   {
       if(isFull())
       {
           throw new IllegalStateException("queue has full , cannot continue add element");
       }
       //tail指向的是队列的最后一个元素的后一个位置，所以直接赋值就行了
       queue[tail] = element;
       //tail需要向后移动，但是不能简单的自增，而需要取模计算
       tail = (tail + 1) % capacity;
   }

    /**
     * 取出队首元素，并将其删除掉，如果队列为空，将抛出异常
     * @return 队首元素
     */
    @SuppressWarnings("unchecked")
   public T poll()
   {
       if(isEmpty())
       {
           throw new IllegalStateException("queue is empty , cannot poll element");
       }
       //如果可以取出首元素，那么先保存首元素，再将其删掉
       T element = (T)queue[head];
       queue[head] = null;
       //将head向后移动，和tail一样，head同样不能简单的自赠，也需要取摸
       head = (head + 1) % capacity;
       return element;
   }

    /**
     * 判断队列是否已满
     * @return 如果队列已满，返回true，否则返回false
     */
   public boolean isFull()
   {
       //这里我的想法是: tail往后挪一位不为空，就证明没有空间可以使用了
//       return queue[(tail + 1) % capacity] != null;
       //或者tail往后挪一位等于head，也是没有空间可以用了
       return (tail  + 1) % capacity == head;
   }

    /**
     * 判断队列是否为空
     * @return 如果队列为空，返回true,否则返回false
     */
   public boolean isEmpty()
   {
       return head == tail;
   }

    /**
     * @return 返回队列中的元素数量
     */
   public int length()
   {
        return (tail - head + capacity) % capacity;
   }

   //toString 打印
   @Override
   public String toString()
   {
       if(isEmpty())
       {
           return "[]";
       }
       StringJoiner stringJoiner = new StringJoiner(",","[","]");
       int cur = head;
       while (cur != tail)
       {
           stringJoiner.add(queue[cur].toString());
           cur = (cur + 1) % capacity;
       }
       return stringJoiner.toString();
   }



   public static void main(String[] args)
   {
       ArrayCircleQueue<Integer> queue1 = new ArrayCircleQueue<>();
       queue1.offer(1);
       queue1.offer(2);
       queue1.offer(3);
       queue1.offer(4);
//       System.out.println(queue1.poll());

       System.out.println(queue1.isEmpty());
       System.out.println(queue1.isFull());
       System.out.println(queue1.length());
       System.out.println(queue1);
   }
}
