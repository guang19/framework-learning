package com.github.anhTom2000.线性数据结构.队列;

/**
 * Created on 21:12  15/03/2020
 * Description:
 * TODO 动态数组实现队列`12pws
 * 优点 : 相比于静态数组的实现方式，动态数组不用一开始就声明数组的长度，且抛开了数组会满的局限性
 * 缺点： 可能会浪费空间
 *
 * @author Weleness
 */

public class DynamicArrayImplementationQueue {
    public static void main(String[] args) {

    }
    private static class Queue {
        private int head; // 头指针
        private int tail; // 尾指针
        private int maxSize; // 最大容量
        private int[] array; // 存放数据的数组

        private Queue() {
            maxSize = 1;
            head = -1;
            tail = -1;
            array = new int[1];
        }

        /**
         * @Method Description:
         * TODO 获得一个队列实例
         * @Author weleness
         * @Return
         */
        public static Queue getInstance() {
            return new Queue();
        }

        /**
         * @Method Description:
         * TODO 判空操作
         * @Author weleness
         * @Return
         */
        public boolean isEmpty() {
            return head == -1;
        }

        /**
         * @Method Description:
         * TODO 获得队列长度
         * @Author weleness
         * @Return
         */
        public int size() {
            if (head == -1) return 0;
            int size = (maxSize + tail - head) % maxSize;  // 获得有效元素的个数
            if (size == 0) return maxSize;
            return size;
        }

        /**
         * @Method Description:
         * TODO 数组扩容操作
         * @Author weleness
         * @Return
         */
        public void expansionQueue() {
            maxSize *= 2;
            int[] oldArray = array;
            array = new int[maxSize];
            for (int i = 0; i < oldArray.length; i++) {
                array[i] = oldArray[i];
            }
        }

        /**
         * @Method Description:
         * TODO 获得队首元素
         * @Author weleness
         * @Return
         */
        public int peek() {
            if (isEmpty()) throw new RuntimeException("Queue is empty and cannot be peeked");
            return array[head];
        }

        private boolean isFull() {
            return (tail + 1) % maxSize == head;
        }

        /**
         * @Method Description:
         * TODO  入队操作
         * @Author weleness
         * @Return
         */
        public void add(int data) {
            if (isFull()) expansionQueue(); // 如果越界就扩容
            tail++;
            array[tail] = data;
            if (head == -1) head = tail;
        }

        /**
         * @Method Description:
         * TODO 出队操作
         * @Author weleness
         * @Return
         */
        public int poll() {
            if (isEmpty()) throw new RuntimeException("Queue is empty and cannot be polled");
            int data = array[head];
            if (head == tail) head = tail = -1;
            else head = head + 1;
            return data;
        }

    }

}
