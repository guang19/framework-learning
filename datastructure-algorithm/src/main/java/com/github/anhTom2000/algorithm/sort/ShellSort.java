package com.github.anhTom2000.algorithm.sort;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/1
 * @description 希尔排序
 * @since 1.0.0
 */
public class ShellSort
{
    private static final int[] arr = {3,5,0,-9,8,30,25,8,10,6};

    public static void main(String[] args)
    {


        /**
         *
         * 希尔排序可以说是对插入排序的改进，插入排序效率较低，每次移动都只能移动一位。
         * 希尔排序则将集合按指定的增量分为若干组，再对这些组进行插入排序，
         * 这样做使得被排序的数组一开始就会变得基本有序，最后再对整个集合使用插入排序，使其有序。
         *
         * 第一次分组，定义增量为: arr.length / 2 = 10 / 2 = 5:
         *   3 -> 30 , 5 -> 25 , 0 -> 8 , -9 -> 10 , 8 -> 6
         *
         *   第一次分组排序后的结果为: 3 , 5, 0 ,-9 , 6 , 30 , 25 , 8 , 10 , 8
         *
         * 第二次分组，定义增量为: 5 / 2 = 2
         *   3 -> 0 , 5 -> -9 , 0 -> 6 , -9 -> 30 , 6 -> 25 , 30 -> 8 , 25 -> 10 , 8 -> 8
         *
         *    第二次分组排序后的结果为: 0 , -9 , 3 , 5 , 6 , 8 , 10 , 8 , 25 , 30
         *
         * 第三次分组时，增量为 2 / 2 = 1,是最后一组分组，此时每个相邻的元素就是一组，所以最后一组可以被看成是相邻元素的交换。
         *
         *  插入排序后的结果为: -9 , 0 , 3 , 5 , 6 , 8  , 8 , 10 , 25 , 30
         *
         *
         */

//        swapShellSort();
        moveShellSort();
    }

    //第一种希尔排序方案： 交换式
    public static void swapShellSort()
    {
        int temp;
        //增量每次除2
        for (int gap = arr.length >> 1 ; gap > 0; gap >>= 1)
        {
            for (int i = gap; i < arr.length ; ++i)
            {
                for (int j = i - gap ; j >= 0 ; j -= gap)
                {
                    //如果此分组前面的元素大于后面的元素，就交换
                    if(arr[j] > arr[j + gap])
                    {
                        temp = arr[j];
                        arr[j] = arr[j  + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }

        System.out.println("希尔排序后的数组为: ");
        System.out.println(Arrays.toString(arr));
    }

    //第二种希尔排序： 移位式 (此方案可以说是真正的希尔排序)
    public static void moveShellSort()
    {
        int j;
        int temp;
        for (int gap = arr.length >> 1 ; gap > 0 ; gap >>= 1)
        {
            for (int i = gap; i < arr.length ; ++i)
            {
                temp = arr[i];
                j = i - gap;
                while (j >= 0  && arr[j] > arr[j + gap])
                {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                //这里的j + gap 是因为while循环最后一次减了gap，导致j<0,所以需要加gap
                arr[j + gap] = temp;
            }
        }

        System.out.println("希尔排序后的数组为: ");
        System.out.println(Arrays.toString(arr));
    }
}
