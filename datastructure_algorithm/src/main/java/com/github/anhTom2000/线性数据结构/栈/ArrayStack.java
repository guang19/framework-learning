package com.github.anhTom2000.线性数据结构.栈;

/**
 * Created on 22:18  22/03/2020
 * Description:
 * TODO 数组实现栈
 * @author Weleness
 */

public class ArrayStack {
    private int top;
    private int capacity;
    private int[] array;

    public ArrayStack() {
        this.top = -1;
        this.capacity = 1;
        this.array = new int[capacity];
    }

    /**
     * @Method
     * Description:
     *  判断栈是否为空
     * @Author weleness
     *
     * @Return
     */
    public  boolean isEmpty(){
        return top == -1;
    }

    /**
     * @Method
     * Description:
     *  判断栈是否满了
     * @Author weleness
     *
     * @Return
     */
    public boolean isFull(){
        return top == capacity-1;
    }

    public void  push(int data){
        if(isFull()) {
            doubleStack();
        }
        array[++top] = data;
    }
    /**
     * @Method
     * Description:
     *  扩容
     * @Author weleness
     *
     * @Return
     */
    public void  doubleStack(){
        int newArray[] = new int[capacity*2];
        System.arraycopy(array,0,newArray,0,capacity);
        capacity = capacity*2;
        array = newArray;
    }
    /**
     * @Method
     * Description:
     *  出栈
     * @Author weleness
     *
     * @Return
     */
    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("Stack isEmpty");
        }
        return array[top--];
    }

    public int peek(){
        if(isEmpty()) return -1;
        return array[top];
    }
    /**
     * @Method
     * Description:
     *  清空栈
     * @Author weleness
     *
     * @Return
     */
    public void clear(){
        top = -1;
        array = new int[1];
    }
}
