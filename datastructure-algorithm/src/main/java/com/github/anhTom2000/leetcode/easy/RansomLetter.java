package com.github.anhTom2000.leetcode.easy;

import java.util.Arrays;

/**
 * @Description : TODO   leetcode.383 赎金信
 * @Author : Weleness
 * @Date : 2020/05/01
 */
public class RansomLetter {
    /*
        subject:
            给定一个赎金信 (ransom) 字符串和一个杂志(magazine)字符串，判断第一个字符串 ransom 能不能由第二个字符串              magazines 里面的字符构成。如果可以构成，返回 true ；否则返回 false。

            (题目说明：为了不暴露赎金信字迹，要从杂志上搜索各个需要的字母，组成单词来表达意思。杂志字符串中的每个字符只能在赎金信字符串中使用一次。

            注意：
                你可以假设两个字符串均只含有小写字母。
                canConstruct("a", "b") -> false
                canConstruct("aa", "ab") -> false
                canConstruct("aa", "aab") -> true

     * */
    class Solution {
        public boolean canConstruct(String ransomNote, String magazine) {
            // 边界条件判断
            if(ransomNote.length() > magazine.length()) return false;
            // 这道题需要我们判断ransom中的字符是否能被magazine中的字符构成，所以转为字符数组
            char[] ran = ransomNote.toCharArray();
            char[] ma = magazine.toCharArray();
            // 对字符数组进行排序。
            Arrays.sort(ran);
            Arrays.sort(ma);
            // 定义双指针，两边同时进行遍历，提高效率。
            int left =  0;
            int right = 0;
            // 保证遍历的时候不会越界
            while(left < ran.length && right< ma.length){
                // 在ran[left]位置的字符比ma[right]位置的字符大，right指针就要向后移动，寻找满足条件字符
                if(ran[left] > ma[right]) right++;
                /*
                    形如：`              left = 0,right = 0
                        ran : [a,a,b]
                        ma  : [b]

                        ran[left] < ma[right]
                * */
                else if(ran[left] < ma[right] ) return false;
                // 如果符合就一起向后移动
                else {
                    left++;
                    right++;
                }
            }
            // 判断是否都符合条件，如果left指针等于ran数组的长度，那么ransom中的字符都能被magazine中的字符构成
            return left == ran.length;
        }
    }
}
