package com.github.anhTom2000.algorithm.collisionPointer;

import java.util.Arrays;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/05/02
 */
public class MoveZero {
    private static int[] nums = {0, 1, 0, 3, 12};

    public static void main(String[] args) {
        /*
            subject:
                给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
                示例:

                    输入: [0,1,0,3,12]
                    输出: [1,3,12,0,0]
                    说明:

                    必须在原数组上操作，不能拷贝额外的数组。
                    尽量减少操作次数。
        * */
        moveZeroes();
        System.out.println(Arrays.toString(nums));
    }

    /*
        解题思路：
        把所有非0元素，按顺序给数组的前面元素赋值，剩下位置的元素赋0即可。

        第一遍：i指针后移一位，将j指针位置上非0元素赋值给i指针位置上的0，j指针后移
        第二遍：由于j指针位置上的元素是0，所以条件不成立，i指针不动，j指针后移
        第三遍：j指针位置上的元素是3，条件成立，i指针后移一位， 将j指针位置上非0元素赋值给i指针位置上的1，j指针后移
        第四遍：j指针位置上的元素是12，条件成立，i指针后移一位， 将j指针位置上非0元素赋值给i指针位置上的0，j指针后移
        第五遍: 循环条件不满足，退出循环，然后从i+1位开始，依次赋值为0
            结束
    */
    public static void moveZeroes() {
        int len = nums.length;
        int i = -1;
        int j = 0;
        while (j <= len - 1) {
            if (nums[j] != 0) {
                i++;
                nums[i] = nums[j];
            }
            j++;
        }
        for (int k = i + 1; k < len; k++) {
            nums[k] = 0;
        }
    }
}
