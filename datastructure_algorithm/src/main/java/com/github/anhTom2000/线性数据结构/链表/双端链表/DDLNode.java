package com.github.anhTom2000.线性数据结构.链表.双端链表;

/**
 * Created on 23:07  21/03/2020
 * Description:
 * TODO 双端链表实现
 * @author Weleness
 */

public class DDLNode {
    class ListNode {
        int val;
        ListNode pre;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

   class DDList {
        ListNode head;
        public DDList(int val) {
            this.head = new ListNode(val);
        }

        void show() {
            ListNode temp = head;
            while (temp != null) {
                System.out.print(temp.val + " ");
                temp = temp.next;
            }
        }

        /**
         * @param val 要插入的元素
         * @param dir 选择前插还是后插
         * @param index  插入的位置
         * @Method
         * Description:
         *
         * @Author weleness
         *
         * @Return
         */
        void add(int val, int dir, int index) {
            ListNode temp = head;
            ListNode newNode = new ListNode(val);
            while (temp != null) {
                if (temp.val == index) {
                    if (dir == 0) { // 前插
                        if (temp.pre != null) {
                            ListNode tmp = temp.pre;
                            temp.pre = newNode;
                            newNode.next = temp;
                            newNode.pre = tmp;
                            tmp.next = newNode;
                        } else {
                            newNode.next = temp;
                            temp.pre = newNode;
                            head = newNode;
                        }
                    } else { // 后插
                        if (temp.next != null) {
                            ListNode tmp = temp.next;
                            temp.next = newNode;
                            newNode.pre = temp;
                            newNode.next = tmp;
                            tmp.pre = newNode;
                        } else {
                            temp.next = newNode;
                            newNode.pre = temp;
                        }
                    }
                    break;
                }
                temp = temp.next;
            }
        }

        /**
         * @Method
         * Description:
         *  删除最后一个元素
         * @Author weleness
         *
         * @Return
         */
        void delete(){
            ListNode temp = head;
            while(temp.next != null){
                temp = temp.next;
            }
            ListNode pre = temp.pre;
            pre.next = null;
            temp.pre = null;
        }
    }
}
