package com.github.anhTom2000.algorithm.search;

/**
 * @author guang19
 * @date 2020/5/3
 * @description 插值查找算法
 * @since 1.0.0
 */
public class InterpolationSearch
{
    public static void main(String[] args)
    {
        int[] arr = {1,2,3,4,5,6,7,8,9,9,9,10,11,12,13,14,15,15};
        System.out.println(interpolationSearch(arr, 1,0,arr.length - 1));
    }

    public static int interpolationSearch(int[] array , int searchVal , int left , int right)
    {
        while (left <= right && searchVal >= array[0] && searchVal <= array[array.length - 1])
        {
            //插值算法的公式: low + (high - low)  * (value - array[low]) / (array[high] - array[low])
            int mid = left + (right - left) * (searchVal - array[left]) / (array[right] - array[left]);
            if(searchVal < array[mid])
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
        }
        return -1;
    }
}
