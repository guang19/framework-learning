package com.github.anhTom2000.datastructure.tree;


import java.util.LinkedList;

/**
 * @author guang19
 * @date 2020/5/8
 * @description 顺序存储二叉树
 * @since 1.0.0
 */
public class ArrayBinaryTree<T>
{
    /**
     *
     *  顺序存储二叉树的特点:
     *
     *  第n个元素的左子节点为: 2 * n + 1
     *  第n个元素的右子节点为: 2 * n + 2
     *  第n个元素的父节点为: (n - 1) / 2
     *
     *  因为顺序存储二叉树是使用数组实现的，所以 n 需要从0开始计算。
     */


    //存储树节点的数组
    private T[] array;

    //数组构造
    public ArrayBinaryTree(T[] array)
    {
        this.array = array;
    }

    //递归前序遍历
    public void preOrderTraverse1(int index)
    {
        if (index < 0 || index >= array.length)
        {
            return;
        }
        System.out.println(array[index]);
        int next;
        //递归左子节点
        if((next = 2 * index + 1) < array.length)
        {
            preOrderTraverse1(next);
        }
        //递归右子节点
        if ((next = 2 * index + 2) < array.length)
        {
            preOrderTraverse1(next);
        }
    }

    //非递归前序遍历
    public void preOrderTraverse2(int index)
    {
        if (index < 0 || index >= array.length)
        {
            return;
        }
        LinkedList<Integer> stack = new LinkedList<>();
        while ((index >= 0 && index < array.length) || !stack.isEmpty())
        {
            if (index >= 0 && index < array.length)
            {
                System.out.println(array[index]);
                stack.push(index);
                index = index * 2 + 1;
            }
            else
            {
                index = stack.pop() * 2 + 2;
            }
        }
    }

    //递归中序遍历
    public void inOrderTraverse1(int index)
    {
        if (index < 0 || index >= array.length)
        {
            return;
        }
        int next;
        if((next = index * 2 + 1) < array.length)
        {
            inOrderTraverse1(next);
        }
        System.out.println(array[index]);
        if ((next = index * 2 + 2) < array.length)
        {
            inOrderTraverse1(next);
        }
    }

    //非递归中序遍历
    public void inOrderTraverse2(int index)
    {
        if (index < 0 || index >= array.length)
        {
            return;
        }
        LinkedList<Integer> stack = new LinkedList<>();
        while ((index >= 0 && index < array.length) || !stack.isEmpty())
        {
            if (index >= 0 && index < array.length)
            {
                stack.push(index);
                index = index * 2 + 1;
            }
            else
            {
                index = stack.pop();
                System.out.println(array[index]);
                index = index * 2 + 2;
            }
        }
    }

    //递归后序遍历
    public void sufOrderTraverse1(int index)
    {
        if (index < 0 || index >= array.length)
        {
            return;
        }
        int next;
        if((next = index * 2 + 1) < array.length)
        {
            sufOrderTraverse1(next);
        }
        if ((next = index * 2 + 2) < array.length)
        {
            sufOrderTraverse1(next);
        }
        System.out.println(array[index]);
    }

    //非递归后续遍历
    public void sufOrderTraverse2(int index)
    {
        if (index < 0 || index >= array.length)
        {
            return;
        }
        LinkedList<Integer> stack = new LinkedList<>();
        int next = index;

        //从根节点遍历到最左子节点
        while (next >= 0 && next < array.length)
        {
            stack.push(next);
            next = next * 2 + 1;
        }
        int tempIndex , visited = 0;
        while (!stack.isEmpty())
        {
            tempIndex = stack.peek();
            //如果当前节点没有右子节点 或者 右子节点已经是访问过的节点
            if ((next = tempIndex * 2 + 2) >= array.length || visited == next)
            {
                System.out.println(array[tempIndex]);
                visited = tempIndex;
                stack.pop();
            }
            else
            {
                while (next < array.length)
                {
                    stack.push(next);
                    next = next * 2 + 1;
                }
            }
        }
    }



    public static void main(String[] args)
    {
        Integer[] arr = {1,5,10,3,7,9,12};
        ArrayBinaryTree<Integer> binaryTree = new ArrayBinaryTree<>(arr);
//        binaryTree.preOrderTraverse1(0);
//        binaryTree.preOrderTraverse2(0);
//        binaryTree.inOrderTraverse1(0);
//        binaryTree.inOrderTraverse2(0);
//        binaryTree.sufOrderTraverse1(0);
        binaryTree.sufOrderTraverse2(0);
    }
}
