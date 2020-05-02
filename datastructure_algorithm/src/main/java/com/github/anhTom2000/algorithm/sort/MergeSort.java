package com.github.anhTom2000.algorithm.sort;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/2
 * @description 归并排序
 * @since 1.0.0
 */
public class MergeSort
{


    public static void main(String[] args)
    {
        int[] arr = {3,5,0,-9,8,30,25,10,7,6};
        /***
         *
         * 归并排序体现的也是一种分治的思想，它主要有两大核心思想: 分 , 并。
         * 先将集合分成若干小的集合，然后将这些集合按照顺序合并成一个大的有序集合，不断的合并。
         * 最终合并的集合就是一个有序的集合。
         *
         * 3 , 5 , 0 , -9 , 8 , 30 , 25 , 10 , 7 , 6
         * 将集合分成2组
         *    left                         right
         * [3 , 5 , 0 , -9 , 8] , [30 , 25 , 10 , 7 , 6]
         * 再将这2组继续分组
         *      left      right         left         right
         * [3 , 5 , 0]  [-9 , 8] , [30 , 25 , 10] , [7 , 6]
         *
         *  left  right   left   right      left       right    left  right
         * [3,5] , [0]    [-9] , [8]       [30, 25] , [10]      [7] , [6]
         *
         * left right                            left   right
         * [3], [5]                              [30] , [25]
         *
         *
         * 然后将分组后的集合进行排序并归并:
         *   [3,5]           [25,30]
         *
         *   left     right      left        right
         *  [0,3,5] , [-9,8]  , [10,25,30] , [6,7]
         *
         *   left                  right
         * [-9,0,3,5,8]  ,  [6,7,10,25,30]
         *
         * 继续合并:
         *  [-9,0,3,5,6,7,8,10,25,30]
         *
         * 归并排序的每次合并排序都需要借助一个临时数组，所以归并排序的过程是比较耗费空间的
         *
         */
        mergeSort(arr,0,arr.length - 1);

        System.out.println("归并排序后的数组为: ");
        System.out.println(Arrays.toString(arr));

    }

    //需要排序的数组，这个数组是整个递归过程中通用的
    //left数组和right数组是虚拟的，就像上面演示的那样，他们是逻辑上存在的，需要依靠left和right下标区分界限
    //left代表left数组的第一个元素的下标
    //right代表right数组的最后一个元素的下标
    public static void mergeSort(int[] array , int left , int right)
    {
        //这里设定最小分割条件，防止数组长度为1还分割
        if(left < right)
        {
            //计算分割点
            int mid = (left + right) >> 1;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            //合并 , mid是left数组的最后一个元素的下标
            merge(array, left,mid, right);
        }
    }

    private static void merge(int[] array , int left , int mid,int right)
    {
        //创建临时数组，临时数组的长度就是right+1,因为right是right数组的最后一个元素的下标
        int[] temp = new int[right + 1];
        int l = left;
        int m = mid + 1;
        int tl = 0;
        while (l <= mid && m <= right)
        {
            if(array[l] < array[m])
            {
                temp[tl] = array[l];
                ++tl;
                ++l;
            }
            else
            {
                temp[tl] = array[m];
                ++tl;
                ++m;
            }
        }
        //如果left数组和right数组还有元素未被放入temp就再次遍历
        while (l <= mid)
        {
            temp[tl] = array[l];
            ++tl;
            ++l;
        }

        while (m <= right)
        {
            temp[tl] = array[m];
            ++tl;
            ++m;
        }
        //将temp中的元素赋予array数组
        tl = 0;
        for (int i = left; i <= right ; ++i , ++tl)
        {
            array[i] = temp[tl];
        }
    }
}
