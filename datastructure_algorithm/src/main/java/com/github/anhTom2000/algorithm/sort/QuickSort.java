package com.github.anhTom2000.algorithm.sort;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/2
 * @description 快速排序
 * @since 1.0.0
 */
public class QuickSort
{

    public static void main(String[] args)
    {

        int[] arr = {3,5,0,-9,8,30,25,10,7,6};

        /**
         *
         * 快速排序体现的是一种分治的思想，它从集合中选取一个基准数，以这个基准数为标准，
         * 将集合分割成两部分，一部分比基准数小，一部分比基准数大，然后再分别对这两部分集合进行排序，不断递归，
         * 最后得到的集合就是这些分割出来的有序的集合组成的集合。
         *
         * 以 arr[(0 + arr.length) >> 1] = 8 为基准数:
         *
         * 第一次:
         *   3 , 5 , 0 , -9 , [8] , 30 , 25 , 10 , 7 , [6]
         *   从左边开始查找比基准数大的元素:30 , 从右边开始查找比基准数小的元素: 6, 并进行交换查找
         *   3 , 5 , 0 , -9 , 6 , [30] , 25 , 10 , 7 , [8]
         *   3 , 5 , 0 , -9 , 6 , [8] , 25 , 10 , [7] , 30
         *   3 , 5 , 0 , -9 , 6 , 7 , [25] , 10 , [8] , 30
         *   3 , 5 , 0 , -9 , 6 , 7 , [8] , 10 , 25 , 30
         *
         * 第二次将第一个集合分割成2部分:
         *     3 , 5 , 0 , -9 , 6 , 7   再计算次部分的基准数: 0 ,并重复第一次的步骤排序，不断递归
         *     10 , 25 , 30             再计算次部分的基准数:25，并重复第一次的步骤排序，不断递归
         *
         *
         */
        quickSort(arr,0,arr.length - 1);
        System.out.println("快速排序后的数组为: ");
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] array , int left , int right)
    {
        //如果left < right才分割排序
        if(left < right)
        {
            int l = left;
            int r = right;
            //计算基准数，这里计算基准数的方法可以随意取，不过基准数选取的好坏，是会直接影响排序效率的。
//            int radix = array[left];
//            int radix = array[right];
            int radix = array[(left + right) >> 1];
            //当 l < r的时候，不断的查找比基准数小的和比基准数大的值
            while (l < r)
            {
                while (l < r && array[l] < radix)
                {
                    ++l;
                }
                while (l < r && array[r] > radix)
                {
                    --r;
                }
                //先判断l和r是否相等，如果相等就代表基准数的左边都是比基准数小的元素，基准数的右边都是比基准数大的元素
                if(l == r)
                {
                    break;
                }
                //交换
                swap(array,l,r);
                //如果l或r对应的元素可能相等，且等于基准值，那么需要将l或r挪移一位，避免死循环
                if(array[l] == radix)
                {
                    --r;
                }
                else if (array[r] == radix)
                {
                    ++l;
                }

            }
            //继续分割左半部分
            quickSort(array,left,l - 1);
            //继续分割右半部分
            quickSort(array,l + 1,right);
        }
    }

    //交换函数
    private static void swap(int[] array , int index1 , int index2)
    {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}