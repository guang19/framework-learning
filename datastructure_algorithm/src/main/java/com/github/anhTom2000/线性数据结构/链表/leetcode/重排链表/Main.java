package com.github.anhTom2000.线性数据结构.链表.leetcode.重排链表;

/**
 * Created on 22:09  19/03/2020
 * Description:
 *  TODO 快慢指针
 *  当快指针走到链表的尾部时，慢指针刚好走到链表的中点
 *  然后将慢指针的引用的那部分链表进行反转，
 *  最后进行不断的交替插入
 * @author Weleness
 */


/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return val+"->"+next;
    }
}

class Main {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        Main main = new Main();
        main.add(head,2);
        main.add(head,3);
        main.add(head,4);
        main.add(head,5);
        main.reorderList(head);
        System.out.println(head);
    }
    void  add(ListNode head,int val){
        ListNode cur = head;
        while(cur.next != null){
            cur = cur.next;
        }
        cur.next = new ListNode(val);

    }
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        //    找中点，链表分成两个
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode newHead = slow.next;
        slow.next = null;

        //    第二个链表倒置
        newHead = reverseList(newHead);

    //    链表节点依次连接
        while(newHead != null){
            ListNode temp = newHead.next;
            newHead.next = head.next;
            head.next = newHead;
            head = newHead.next;
            newHead = temp;
        }
    }

    private ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode tail = head;
        head = head.next;

        tail.next = null;

        while (head != null) {
            ListNode temp = head.next;
            head.next = tail;
            tail = head;
            head = temp;
        }
        return tail;
    }
}
