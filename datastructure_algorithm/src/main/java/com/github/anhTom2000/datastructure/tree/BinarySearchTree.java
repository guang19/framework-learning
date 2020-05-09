package com.github.anhTom2000.datastructure.tree;

import java.util.LinkedList;

/**
 * @author guang19
 * @date 2020/5/8
 * @description 二叉查找树实现
 * @since 1.0.0
 */
public class BinarySearchTree<T extends Comparable>
{
    private Node<T> root;

    public BinarySearchTree(Node<T> root)
    {
        this.root = root;
    }

    static class Node<T extends Comparable>
    {
        T val;
        Node<T> left;
        Node<T> right;

        Node(T val)
        {
            this.val = val;
        }

        Node(T val , Node<T> left , Node<T> right)
        {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "val=" + val +
                    '}';
        }

        //添加节点
        void add(T val)
        {
            if (val != null)
            {
                Node<T> temp = this;
                while (temp != null)
                {
                    if (val.compareTo(temp.val) > 0)
                    {
                        if (temp.right != null)
                        {
                            temp = temp.right;
                        }
                        else
                        {
                            temp.right = new Node<>(val);
                            return;
                        }
                    }
                    else
                    {
                        if (temp.left != null)
                        {
                            temp = temp.left;
                        }
                        else
                        {
                            temp.left = new Node<>(val);
                            return;
                        }
                    }
                }
            }
        }

        //中序遍历
        void inOrderTraverse()
        {
            LinkedList<Node<T>> stack = new LinkedList<>();
            Node<T> temp = this;
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
                    System.out.println(temp);
                    temp = temp.right;
                }
            }
        }
    }

    //删除节点
    public void remove(T val)
    {
        if (val != null)
        {
            Node<T> temp = root;
            //要删除节点的父节点
            Node<T> parent = root;
            while (temp != null)
            {
                //向右遍历
                if (val.compareTo(temp.val) > 0)
                {
                    parent = temp;
                    temp = temp.right;
                }
                //向左遍历
                else if(val.compareTo(temp.val) < 0)
                {
                    parent = temp;
                    temp = temp.left;
                }
                //遍历到要删除的节点了
                else
                {
                    //如果节点是叶子节点，直接删除并改变其父节点的引用好了
                    if (temp.left == null && temp.right == null)
                    {
                        //如果parent等于 root ，就说明要删除的就是根节点，且根节点并没有左右子节点
                        //否则判断当前节点是其父子节点左引用还是右引用
                        if (temp == root)
                        {
                            root.val = null;
                            root = null;
                        }
                        else
                        {
                            if (parent.left == temp)
                            {
                                parent.left = null;
                            }
                            else if (parent.right == temp)
                            {
                                parent.right = null;
                            }
                            temp.val = null;
                        }
                    }
                    //如果要删除的节点只有左子节点，直接使左子节点取代当前的节点的位置就行了
                    else if (temp.left != null && temp.right == null)
                    {
                        //如果parent为空就说明要删除的就是根节点，且根节点只有左子节点
                        //否则判断当前节点是parent的左子节点还是右子节点
                        if (temp == root)
                        {
                            root.val = null;
                            root = root.left;
                        }
                        else
                        {
                            if (parent.left == temp)
                            {
                                parent.left = temp.left;
                            }
                            else if (parent.right == temp)
                            {
                                parent.right = temp.left;
                            }
                            temp.val = null;
                        }
                    }
                    //如果要删除的节点只有右子节点,直接使右子节点取代当前节点的位置就行了
                    else if(temp.right != null && temp.left == null)
                    {
                        //如果parent为空就说明要删除的就是根节点，且根节点只有右子节点
                        //否则判断当前节点是parent的左子节点还是右子节点
                        if (temp == root)
                        {
                            root.val = null;
                            root = root.right;
                        }
                        else
                        {
                            if (parent.left == temp)
                            {
                                parent.left = temp.right;
                            }
                            else if(parent.right == temp)
                            {
                                parent.right = temp.right;
                            }
                            temp.val = null;
                        }
                    }
                    //如果要删除的节点拥有左右子节点,那么选取其前驱或后继节点替换掉删除的节点
                    else
                    {
                        //前驱节点可以理解为要删除节点的最大左子节点，这里也可以获取后继节点，后继节点可以理解为要删除节点的最小右子节点
                        //前驱节点肯定在左子树的右子树上，所以向左子树的右子树遍历就行了
                        Node<T> leftMax = temp.left , leftMaxParent = temp;
                        while (leftMax.right != null)
                        {
                            leftMaxParent = leftMax;
                            leftMax = leftMax.right;
                        }
                        temp.val = leftMax.val;
                        if (leftMaxParent.left == leftMax)
                        {
                            leftMaxParent.left = leftMax.left;
                        }
                        else if(leftMaxParent.right == leftMax)
                        {
                            leftMaxParent.right = leftMax.left;
                        }
                        leftMax.val = null;
                    }
                    return;
                }
            }
        }
    }

    public void add(T val)
    {
        if (root != null)
        {
            this.root.add(val);
        }
    }

    public void inOrderTraverse()
    {
        if(root != null)
        {
            this.root.inOrderTraverse();
        }
    }

    public static void main(String[] args)
    {
        Node<Integer> root = new Node<>(5);
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>(root);
        binarySearchTree.add(2);
        binarySearchTree.add(3);
        binarySearchTree.add(1);
//        binarySearchTree.add(6);
//        binarySearchTree.add(10);
//        binarySearchTree.add(8);
//        binarySearchTree.add(9);
//        binarySearchTree.add(12);
//        binarySearchTree.add(13);
//        binarySearchTree.add(11);


//        binarySearchTree.remove(5);
//        binarySearchTree.remove(8);
//        binarySearchTree.remove(8);
//        binarySearchTree.remove(8);
//        binarySearchTree.remove(8);

        binarySearchTree.inOrderTraverse();
    }
}
