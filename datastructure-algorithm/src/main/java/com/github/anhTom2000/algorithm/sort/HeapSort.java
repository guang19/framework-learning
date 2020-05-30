package com.github.anhTom2000.algorithm.sort;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/5
 * @description
 * @since 1.0.0
 */
public class HeapSort
{
    public static void main(String[] args)
    {
        int[] arr = {3,5,0,-9,8,30,25,10,7,6};
        /***
         *
         * 堆排序我推荐一篇博客，各位同学可以看看: https://www.cnblogs.com/chengxiao/p/6129630.html
         * 如果还是比较困惑的话，可以进入算法可视化的那个网站学习: https://www.cs.usfca.edu/~galles/visualization/Algorithms.html
         */
        heapSort(arr);

        System.out.println("堆排序后的数组为: ");
        System.out.println(Arrays.toString(arr));
    }

    public static void heapSort(int[] array)
    {
        int len = array.length;
        //调整大顶堆
        for (int i = (len / 2) - 1; i >= 0; --i)
        {
            adjustHeap(array,i,len);
        }

        //将对顶元素与树的最后一个元素交换
        for (int i = len - 1; i > 0 ; --i)
        {
            swap(array,0,i);
            adjustHeap(array,0,i);
        }

    }

    //交换函数
    private static void swap(int[] array , int index1 , int index2)
    {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    //将数组调整成为大顶堆或小顶堆
    private static void adjustHeap(int[] array , int i , int length)
    {
        //最后一个非叶子节点
        int temp = array[i];
        //k为非叶子节点的子节点
        for (int k = i * 2 + 1; k < length ; k = k * 2 + 1)
        {
            //如果左子节点小于右子节点，则k指向右子节点
            if (k + 1 < length && array[k] < array[k + 1])
            {
                ++k;
            }
            //如果子节点大于父节点，则子节点与父节点交换
            if (array[k] > temp)
            {
                array[i] = array[k];
                i = k;
            }
            else
            {
                break;
            }
        }
        array[i] = temp;
    }
}
