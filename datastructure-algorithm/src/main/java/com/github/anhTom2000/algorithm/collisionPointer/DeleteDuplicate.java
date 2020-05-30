package com.github.anhTom2000.algorithm.collisionPointer;

import java.util.Arrays;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/05/02
 */
public class DeleteDuplicate {
    private static int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};

    public static void main(String[] args) {
        /*
            subject：
                    给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
                    不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
             示例:
                    给定 nums = [0,0,1,1,1,2,2,3,3,4],
                    函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
                    你不需要考虑数组中超出新长度后面的元素。
        */

        removeDuplicates();
        System.out.println(Arrays.toString(nums));
    }

    /*
        解题思路:
                如果l指针位置上的元素与r指针位置上的元素不相等，且l指针+1不等于r指针，那么就表示l+1位置上的元素是重复的，将r指针位置上的元素赋值过来
        第一遍:     0, 0, 1, 1, 1, 2, 2, 3, 3, 4   因为此时l指针位置上的元素和r指针位置上的元素相等
        第二遍：    0, 1, 1, 1, 1, 2, 2, 3, 3, 4    条件成立，l+1位置的元素被赋值
        第三遍:     0, 1, 1, 1, 1, 2, 2, 3, 3, 4   条件不成立
        第四遍：    0, 1, 1, 1, 1, 2, 2, 3, 3, 4   依然如此
        第五遍：    0, 1, 2, 1, 1, 2, 2, 3, 3, 4   条件成立
        第六编：    0, 1, 2, 1, 1, 2, 2, 3, 3, 4   条件不成立
        第七编：    0, 1, 2, 3, 1, 2, 2, 3, 3, 4   条件成立
        第八编：    0, 1, 2, 3, 1, 2, 2, 3, 3, 4   条件不成立
        第九编：    0, 1, 2, 3, 4, 2, 2, 3, 3, 4   条件成立，循环结束，返回长度5
    */
    static int l = 0;
    static int r = l+1;
    public static int removeDuplicates() {
        int len = nums.length;

        while (r <= len - 1) {
            if (nums[l] != nums[r]) {
                if (l + 1 != r) {
                    nums[l + 1] = nums[r];
                }
                l++;
            }
            r++;
        }
        return l + 1;
    }
}
