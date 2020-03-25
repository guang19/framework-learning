package com.github.anhTom2000.线性数据结构.栈;

/**
 * Created on 13:10  23/03/2020
 * Description:
 * TODO 链表实现栈
 *
 * @author Weleness
 */
class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }
}

class ListStack {
    private ListNode head;
    /**
     * @Method
     * Description:
     *  入栈操作
     * @Author weleness
     *
     * @Return
     */
    public void  push(int data){
        if(head == null ){
            head = new ListNode(data);
        }else {
            ListNode newNode = new ListNode(data);
            newNode.next = head;
            head = newNode;
        }
    }
    /**
     * @Method
     * Description:
     *  出栈操作
     * @Author weleness
     *
     * @Return
     */
    public int pop(){
        if(isEmpty()){
            throw  new RuntimeException("Stack empty");
        }
        int top = head.val;
        head = head.next;
        return top;
    }
    /**
     * @Method
     * Description:
     *  查看栈顶元素
     * @Author weleness
     *
     * @Return
     */
    public int peek(){
        if(isEmpty()) return -1;
        return head.val;
    }
    /**
     * @Method
     * Description:
     *  判空操作
     * @Author weleness
     *
     * @Return
     */
    public boolean isEmpty(){
        return head == null;
    }
    /**
     * @Method
     * Description:
     *  清空栈
     * @Author weleness
     *
     * @Return
     */
    public void  clear(){
        head = null;
    }
}
