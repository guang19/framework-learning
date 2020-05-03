package com.github.anhTom2000.algorithm.collisionPointer;

import java.util.Arrays;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/05/03
 */
public class RemoveElement {


    public static void main(String[] args) {
        /*
            subject :
                    给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
                    不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
                    元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
            示例 ：
                    给定 nums = [0,1,2,2,3,0,4,2], val = 2,
                    函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
                    注意这五个元素可为任意顺序。
                    你不需要考虑数组中超出新长度后面的元素。
         */
        removeElement();
        System.out.println(Arrays.toString(nums));
    }

    /*
        解题思路：
               遍历这个数组，将值不等于val的元素往前移动，直到推出循环。
    * */
    private static int[] nums = {0,1,2,2,3,0,4,2};
    private static int val = 2;
    public static int removeElement() {
        int index = 0;
        int left = -1;
        int right= 0;

        while(right <= nums.length-1){
            if(nums[right]!=val){
                left++;
                nums[left] = nums[right];
                index++;
            }
            right++;
        }
        return index;
    }


}
