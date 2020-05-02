package com.github.anhTom2000.algorithm.sort;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/2
 * @description 计数排序
 * @since 1.0.0
 */
public class CountingSort
{
    public static void main(String[] args)
    {
        int[] arr = {3,128,0,92,8,30,25,10,7,2333};

        /**
         * 计数排序和基数排序相似，都是以空间换时间的一种思想
         * 首先从要排序的数组中找出最大值，然后根据最大值创建一个长度为最大值的数组，
         * 然后遍历原数组，然后以原数组的元素的值为下标，大数组对应的值就加1，
         * 最终遍历大数组，当大数组中有元素大于0时，就说明这个元素对应的下标值就在原数组中，于是按顺序赋给原数组。
         *
         *
         */

        countingSort(arr);
        System.out.println("计数排序后的结果为: ");
        System.out.println(Arrays.toString(arr));
    }

    //计数排序
    public static void countingSort(int[] array)
    {
        int maxVal = getMaxVal(array);
        //根据最大值创建大数组，加1是因为0 - maxVal都可能是array中出现的元素
        int[] bucket = new int[maxVal  + 1];

        //遍历原数组，根据原数组的元素值，将大数组对应的值 ++
        for (int i = 0 ; i < array.length ; ++i)
        {
            ++bucket[array[i]];
        }

        //遍历大数组，当大数组中的元素值大于0时，就说明这个元素值的下标就是原数组中的元素
        //index是原数组的下标
        for (int i = 0 ,index = 0 ; i < bucket.length; ++i)
        {
            while (bucket[i] > 0)
            {
                array[index] = i;
                ++index;
                --bucket[i];
            }
        }
    }

    //获取数组中最大值
    private static int getMaxVal(int[] array)
    {
        int maxVal = array[0];
        for (int i = 1 ; i < array.length; ++i)
        {
            if(array[i] > maxVal)
            {
                maxVal = array[i];
            }
        }
        return maxVal;
    }
}
