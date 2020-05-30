package com.github.anhTom2000.algorithm.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/3
 * @description  二分查找
 * @since 1.0.0
 */
public class BinarySearch
{
    public static void main(String[] args)
    {
        int[] arr = {1,2,3,4,5,6,7,8,9,9,9,10,11,12,13,14,15,15};
        System.out.println(binarySearch(arr,3,0,arr.length - 1));
        System.out.println(Arrays.toString(binarySearchAll(arr,15,0,arr.length - 1)));
    }

    //二分查找，查找指定的值
    //要求array必须是有序的
    public static int binarySearch(int[] array, int searchVal , int left , int right)
    {

        do
        {
            int mid = (left + right) >> 1;
            if (searchVal < array[mid])
            {
                right = mid - 1;
            }
            else if(searchVal > array[mid])
            {
                left = mid + 1;
            }
            else
            {
                return mid;
            }
        }while (left <= right);
        return -1;
    }

    //二分查找，查找指定的值的所有的下标
    //要求array必须是有序的
    public static Integer[] binarySearchAll(int[] array , int searchVal , int left , int right)
    {
        ArrayList<Integer> indexList = new ArrayList<>(4);
        while (left <= right)
        {
            int mid = (left + right) >> 1;
            if (searchVal < array[mid])
            {
                right = mid - 1;
            }
            else if(searchVal > array[mid])
            {
                left = mid + 1;
            }
            else
            {
                //找到了，所以往mid的前后查找是否还有相等于searchVal的值的下标
                indexList.add(mid);
                int temp = mid;
                while (temp > 0 && searchVal == array[--temp])
                {
                    indexList.add(temp);

                }
                temp = mid;
                while (temp < array.length - 1 && searchVal == array[++temp])
                {
                    indexList.add(temp);
                }
                return indexList.toArray(new Integer[0]);
            }
        }
        return new Integer[0];
    }
}
