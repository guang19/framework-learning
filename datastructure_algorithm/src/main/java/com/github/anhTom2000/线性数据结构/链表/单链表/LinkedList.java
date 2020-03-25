package com.github.anhTom2000.线性数据结构.链表.单链表;

import java.util.Stack;

/**
 * Created on 20:03  17/03/2020
 * Description:
 * TODO 链表实现
 *
 * @author Weleness
 */
class ListNode { // 创建一个类当作链表的节点
    int val; // 节点的值
    ListNode next; // 节点的下一个节点的引用

    public ListNode (int val){
        this.val = val;
    }
}

class LinkedList {

    int size ; // 链表的长度
    ListNode head; // 节点

    public LinkedList() {
        head = new ListNode(0); // 为链表节点初始化
        size = 0;
    }



    public int get(int index) {
        if(index < 0 || index >= size) return -1;
        ListNode cur = head;
        for(int i = 0; i <=index;i++) cur = cur.next;

        return cur.val;
    }

    public void addAtHead(int val) {
        addAtIndex(0,val);
    }


    public void addAtTail(int val) {
        addAtIndex(size,val);
    }


    public void addAtIndex(int index, int val) {
        if(index > size) return;
        if(index < 0) index = 0;
        ListNode cur = head;
        for(int i = 0 ; i < index ; i++) cur = cur.next;
        ListNode temp = cur.next;
        cur.next = new ListNode(val);
        cur.next.next = temp;
        size++;
    }


    public void deleteAtIndex(int index) {
        if(index< 0 || index >= size) return;
        ListNode cur = head;
        for(int i = 0; i < index; i++) cur = cur.next;
        cur.next = cur.next.next;
        size--;
    }

    @Override
    public String toString() {
        Stack<Integer> stack = new Stack<>();
        stack.pop();
       StringBuilder sb = new StringBuilder();
       ListNode cur = head.next;
       while(cur != null){
           sb.append("[").append(cur.val).append("]--->");
           cur = cur.next;
       }
       sb.append("null");
       return sb.toString();
    }
}
