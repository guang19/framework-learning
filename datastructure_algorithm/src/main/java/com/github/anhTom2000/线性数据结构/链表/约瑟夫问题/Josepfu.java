package com.github.anhTom2000.线性数据结构.链表.约瑟夫问题;

import java.util.Objects;

/**
 * Created on 19:41  26/03/2020
 * Description:
 * TODO 约瑟夫问题实现
 *
 * Josephu 问题为： 设编号为1,2,...,n 的n个人围坐一圈，约定编号为   k(1<=k<=n)的人从1开始报数，数到m的那个人出列，它的下一位又开始从   1开始报数，数到m的那个人又出列，以此类推，知道所有人出列位置。由此   产生一个出队编号的序列
 *
 * @author Weleness
 */

public class Josepfu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        //circleSingleLinkedList.add(50);
        //circleSingleLinkedList.show();
        circleSingleLinkedList.out(1, 10, 50);
    }


    static class CircleSingleLinkedList {
        Node head; // 链表的头节点

        /**
         * @param k        从哪个地方开始数
         * @param countNum 数几下
         * @param m        表示最初有多少个小孩
         * @Method Description:
         * @Author weleness
         * @Return
         */
        public void out(int k, int countNum, int m) {
            add(m);
            // 先对数据进行校验
            if (head == null || k < 0 || countNum > m) {
                return;
            }
            //    创建辅助指针
            Node helper = head;
            while (helper.next != head) { // 指向链表尾部
                helper = helper.next;
            }
            // 移动到报数位置
            for (int i = 0; i < k - 1; i++) {
                head = head.next;
                helper = helper.next;
            }

            //    开始报数
            boolean flag= true;
            int step = 0; // 计数器
            while(flag){
                step++;
                head = head.next;
                helper = helper.next;
                if(step == countNum-1){
                    //System.out.println(head.number);
                    head = head.next; // 让那个人出圈
                    helper.next = head; // 跟紧脚步
                    step = 0;
                }
                if(helper== head) { // 剩下最后一个人，退出
                    flag = false;
                }
            }
            System.out.println(head.number);
        }

        public void add(int num) {
            if (num < 1) {
                System.err.println("环不能小于1");
                return;
            }
            Node cur = null; // 辅助指针，用来帮助构建环形链表
            for (int i = 1; i <= num; i++) {
                // 创建节点
                Node nn = new Node(i);
                if (i == 1) {
                    head = nn;
                    head.next = head;// 如果只有一个节点，自生成环
                    cur = head; // 当前节点指向head
                } else {
                    cur.next = nn; // 添加进环形链表
                    nn.next = head; // 新节点的下一个节点指向头部，成环
                    cur = nn; // 移动到新节点
                }
            }
        }

        public void show() {
            if (Objects.isNull(head)) {
                System.err.println("链表不能为空");
                return;
            }
            Node temp = head;
            boolean flag = true;
            while (flag) { // 因为是成环的，所以遍历的时候要判断
                System.out.println(temp.number);
                temp = temp.next;
                if (temp == head) flag = false;
            }
        }
    }

    static class Node {
        int number; // 编号
        Node next; // 指向下一个节点

        public Node(int number) {
            this.number = number;
        }
    }
}
