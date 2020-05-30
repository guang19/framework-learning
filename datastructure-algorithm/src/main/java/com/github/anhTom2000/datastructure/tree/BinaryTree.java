package com.github.anhTom2000.datastructure.tree;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @author guang19
 * @date 2020/5/5
 * @description 二叉树实现
 * @since 1.0.0
 */
public class BinaryTree<T>
{
    static class Node<T>
    {
        T data;
        //左子节点
        Node<T> left;
        //右子节点
        Node<T> right;

        Node(){}

        Node(T data)
        {
            this.data = data;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return Objects.equals(data,node.data);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(data);
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "data=" + data +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

    }

    //递归前序遍历
    void preOrderTraverse1(Node<T> node)
    {
        if (node != null)
        {
            System.out.println(node.data);
            preOrderTraverse1(node.left);
            preOrderTraverse1(node.right);
        }
    }

    //非递归前序遍历
    void preOrderTraverse2(Node<T> node)
    {
        LinkedList<Node<T>> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty())
        {
            if(node != null)
            {
                System.out.println(node.data);
                stack.push(node);
                node = node.left;
            }
            else
            {
                node = stack.pop().right;
            }
        }
    }

    //递归中序遍历
    void inOrderTraverse1(Node<T> node)
    {
        if (node != null)
        {
            inOrderTraverse1(node.left);
            System.out.println(node.data);
            inOrderTraverse1(node.right);
        }
    }

    //非递归中序
    void inOrderTraverse2(Node<T> node)
    {
        LinkedList<Node<T>> stack = new LinkedList<>();
        while (node != null || !stack.isEmpty())
        {
            if (node != null)
            {
                stack.push(node);
                node = node.left;
            }
            else
            {
                node = stack.pop();
                if (node != null)
                {
                    System.out.println(node.data);
                    node = node.right;
                }
            }
        }
    }

    //递归后序遍历
    void sufOrderTraverse1(Node<T> node)
    {
        if(node != null)
        {
            sufOrderTraverse1(node.left);
            sufOrderTraverse1(node.right);
            System.out.println(node.data);
        }
    }

    //非递归后序遍历 (难点)
    void sufOrderTraverse2(Node<T> node)
    {
        LinkedList<Node<T>> stack = new LinkedList<>();
        //把节点的所有左子树全部入栈
        while (node != null)
        {
            stack.push(node);
            node = node.left;
        }
        //visited为已经访问过的节点
        Node<T> visited  = null, temp;
        while (!stack.isEmpty())
        {
            temp = stack.peek();
            if (temp.right == null || temp.right == visited)
            {
                stack.pop();
                System.out.println(temp.data);
                visited = temp;
            }
            else
            {
                temp = temp.right;
                while (temp != null)
                {
                    stack.push(temp);
                    temp = temp.left;
                }
            }
        }
    }

    //前序查找 : 指定值的节点
    Node<T> preOrderSearch(T val)
    {
        Node<T> temp = root;
        if (temp != null)
        {
            LinkedList<Node<T>> stack = new LinkedList<>();
            while (temp != null || !stack.isEmpty())
            {
                if (temp != null)
                {
                    if (val.equals(temp.data))
                    {
                        return temp;
                    }
                    stack.push(temp);
                    temp = temp.left;
                }
                else
                {
                    temp = stack.pop().right;
                }
            }
        }
        return null;
    }

    //中序查找
    Node<T> inOrderSearch(T val)
    {
        Node<T> temp = root;
        if (temp != null)
        {
            LinkedList<Node<T>> stack = new LinkedList<>();
            while (temp != null || !stack.isEmpty())
            {
                if (temp != null)
                {
                    stack.push(temp);
                    temp = temp.left;
                }
                else
                {
                    temp = stack.pop();
                    if (val.equals(temp.data))
                    {
                        return temp;
                    }
                    temp = temp.right;
                }
            }
        }
        return null;
    }

    //后序查找
    Node<T> sufOrderSearch(T val)
    {
        Node<T> temp = root;
        if (temp != null)
        {
            LinkedList<Node<T>> stack = new LinkedList<>();
            while (temp != null)
            {
                stack.push(temp);
                temp = temp.left;
            }
            Node<T> visited = null;
            while (!stack.isEmpty())
            {
                temp = stack.peek();
                if (temp.right == null || temp.right == visited)
                {
                    if (val.equals(temp.data))
                    {
                        return temp;
                    }
                    stack.pop();
                }
                else
                {
                    temp = temp.right;
                    while (temp != null)
                    {
                        stack.push(temp);
                        temp = temp.left;
                    }
                }
            }
        }
        return null;
    }


    ///////////////////////////////////////////////////////////////////////////
    public void preOrderTraver()
    {
        preOrderTraverse2(root);
    }

    public void inOrderTraver()
    {
        inOrderTraverse2(root);
    }

    public void sufOrderTraver()
    {
        sufOrderTraverse2(root);
    }
    ///////////////////////////////////////////////////////////////////////////



    //根节点
    private Node<T> root;

    public BinaryTree()
    {}

    public void setRoot(Node<T> root)
    {
        this.root = root;
    }

    public static void main(String[] args)
    {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        Node<Integer> root = new Node<>(1);
        Node<Integer> left = new Node<>(2);
        left.left = new Node<>(4);
        left.right = new Node<>(5);

        Node<Integer> right = new Node<>(3);
        right.left = new Node<>(6);
        right.right = new Node<>(7);

        root.left = left;
        root.right = right;

        binaryTree.setRoot(root);

//        binaryTree.preOrderTraver();
//        binaryTree.inOrderTraver();
//        binaryTree.sufOrderTraver();

//        System.out.println(binaryTree.preOrderSearch(1));
//        System.out.println(binaryTree.preOrderSearch(2));
//        System.out.println(binaryTree.preOrderSearch(3));


    }


}
