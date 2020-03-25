package com.github.anhTom2000.线性数据结构.链表.leetcode.重排链表;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 22:39  20/03/2020
 * Description:
 * TODO 重排链表的第二种做法
 * 思路： 将链表的每一个节点的地址用集合保存，表明一个尾指针和一个头指针，
 * 然后不断的进行首尾拼接，直到头指针的位置等于尾指针的位置，
 * 然后将指针最后的位置的引用删除掉（不然会成环，会发生错误）
 *
 * @author Weleness
 */


class Main2 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        Main2 main2 = new Main2();
        main2.add(head, 2);
        main2.add(head, 3);
        main2.add(head, 4);
        main2.reorderList(head);
        System.out.println(head);
    }

    void add(ListNode head, int val) {
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new ListNode(val);

    }

    public void reorderList(ListNode head) {
        if (head == null) return;
        List<ListNode> list = new ArrayList<ListNode>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        int i = 0, j = list.size() - 1;
        while (i != j) {
            list.get(i).next = list.get(j);
            i++;
            if (i == j) break;
            list.get(j).next = list.get(i);
            j--;
        }
        list.get(i).next = null;
    }
}
