package com.github.anhTom2000.线性数据结构.队列;


import java.util.Scanner;

/**
 * Created on 17:36  15/03/2020
 * Description:
 * TODO 使用数组模拟队列
 *   局限性: 实现队列前必须声明数组的最大空间且不能改变，试图对一个满的队列或空的队列操作都会产生的一个针对的异常
 * @author Weleness
 */

public class ArraySimulationQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char choose;
        System.out.println("请先输入队列容量：");
        int size = sc.nextInt();
        Queue queue = new Queue(size);
        while (true) {
            System.out.println("a:添加一个元素到队列中");
            System.out.println("g:查看队首元素");
            System.out.println("d:将队首元素删除");
            System.out.println("i:查看队列是否为空");
            System.out.println("l:打印队列");
            System.out.println("e:退出");
            choose = sc.next().charAt(0);
            switch (choose) {
                case 'a':
                    System.out.println("请输入一个元素");
                    int element = sc.nextInt();
                    try {
                        queue.add(element);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'g':
                    try {
                        System.out.println(queue.peek());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'd':
                    try {
                        System.out.println(queue.poll());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 'i':
                    System.out.println(queue.isEmpty());
                    break;
                case 'l':
                    System.out.println(queue.show());
                    break;
                case 'e':
                    System.out.println("退出");
                    sc.close();
                    System.exit(0);
                    break;
            }
        }
    }

 private static class Queue {
        int maxSize; // 队列的最大容量
        int head; // 队列的头指针
        int tail; // 队列的尾指针
        int[] array; // 存放数据的数组

        /**
         * @Method Description:
         * TODO 初始化
         * @Author weleness
         * @Return
         */
        public Queue(int maxSize) {
            this.maxSize = maxSize; // 创建队列，用户输入队列容量
            array = new int[maxSize];// 初始化数组
            head = -1; // 初始化头指针
            tail = -1; // 初始化尾指针
        }

        /**
         * @Method Description:
         * TODO 判空操作
         * 算法复杂度 : O(1)
         * @Author weleness
         * @Return
         */
        public boolean isEmpty() {
            return head == -1; // 当头指针为-1，那么表示队列为空
        }

        /**
         * @Method Description:
         * TODO 判满操作
         * 算法复杂度 : O(1)
         * @Author weleness
         * @Return
         */
        public boolean isFull() {
            return (tail + 1) % maxSize == head; // 因为队列是环形的，当尾指针的下一个位置与头指针的位置重合时，这个队列时满的
        }

        /**
         * @Method Description:
         * TODO 入队操作
         * 算法复杂度 : O(1)
         * @Author weleness
         * @Return
         */
        public void add(int data) {
            //    首先判断队列是否满了
            if (isFull())
                throw new RuntimeException("The array is full. No more elements can be added");
            tail = (tail + 1) % maxSize;// 首先尾指针往后移，然后插入到尾指针所在的位置,%最大值表示不会超过最大值，例如：3%3 = 0
            array[tail] = data;
            if (head == -1) head = tail;
        }

        /**
         * @Method Description:
         * TODO 查看队首元素
         * 算法复杂度 : O(1)
         * @Author weleness
         * @Return
         */
        public int peek() {
            //    首先判断队列是否为空
            if (isEmpty())
                throw new RuntimeException("Queue is empty and cannot be peeked");
            return array[head];
        }

        /**
         * @Method Description:
         * TODO 出队操作
         * 算法复杂度 : O(1)
         * @Author weleness
         * @Return
         */
        public int poll() {
            //    首先判断队列是否为空
            if (isEmpty())
                throw new RuntimeException("Queue is empty and cannot be polled");
            int data = array[head];
            if (head == tail) { // 如果队列中只有一个元素，头指针往前移动，队列置空
                head = tail - 1;
            } else {
                head = (head + 1) % maxSize; // 否则头指针往后移动
            }
            return data;
        }

        /**
         * @Method
         * Description:
         *  TODO 展示队列操作
         *  算法复杂度 : O(n)
         *
         * @Author weleness
         *
         * @Return
         */
        public String show() {
            if (isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i <array.length; i++) {
                if (i != array.length - 1)
                    sb = sb.append(array[i]).append(",");
                else sb = sb.append(array[i]);
            }
            sb = sb.append("]");
            return sb.toString();
        }
    }

}
