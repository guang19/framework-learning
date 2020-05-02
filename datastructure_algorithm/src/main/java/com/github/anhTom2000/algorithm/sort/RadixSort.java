package com.github.anhTom2000.algorithm.sort;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/2
 * @description     基数排序
 * @since 1.0.0
 */
public class RadixSort
{
    public static void main(String[] args)
    {
        int[] arr = {3,128,0,92,8,30,25,10,7,2333,2};

        /**
         * 基数排序是一种以空间换时间的算法
         * 首先创建一个10*数组长度的二维数组(不考虑负数，如果考虑负数则需要增加桶的长度)，然后取出原数组的每个元素的每一个位数，
         * 每一轮都根据当前位数将元素放入二维数组中，最终从二位数组中取出的元素自然形成了有序序列。
         *
         */
        radixSort(arr);
        System.out.println("基数排序后的结果为: ");
        System.out.println(Arrays.toString(arr));
    }

    //基数排序
    public static void radixSort(int[] array)
    {
        //不考虑负数，只考虑 0 - 9 这10个数
        final int mod = 10;

        //为了确保bucket可以装得下array，这里需要将每个bucket的长度设置大些
        int[][] bucket = new int[mod][array.length];

        //记录每个桶中有多少个元素
        int[] elementCount = new int[mod];

        //获取数组中最大值的位数，最大值的位数就是我们需要排序的次数
        short maxValLen = getNumLength(getMaxVal(array));

        for (short i = 0 , digits = 1 ; i < maxValLen ; ++i , digits *= 10)
        {
            //将数组中的每个元素都存入桶中
            for (int j = 0 ; j < array.length ; ++j)
            {
                //当前元素的当前位数的值
                int digit = array[j] / digits % 10;
                bucket[digit][elementCount[digit]] = array[j];
                ++elementCount[digit];
            }
            int index = 0;
            //将元素从桶中恢复到原数组
            for (short k = 0 ; k < mod; ++k)
            {
                //如果当前桶中有元素
                if(elementCount[k] > 0)
                {
                    for (int d = 0 ; d < elementCount[k]; ++d , ++index)
                    {
                        array[index] = bucket[k][d];
                        //取出一个就置0,这一步不做也没关系
                        bucket[k][d] = 0;
                    }
                    //这一步是将当前桶的元素的数量置0,非常关键
                    elementCount[k] = 0;
                }
            }
        }

    }


    //获取数组中最大的值
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

    //计算num长度
    private static short getNumLength(int num)
    {
        short len = 0;
        while (num > 0)
        {
            num /= 10;
            ++len;
        }
        return len;
    }


}
