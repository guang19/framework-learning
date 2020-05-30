package com.github.anhTom2000.leetcode.easy;

import java.util.Arrays;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/05/01
 */
public class FindTheDifference {

    /*
       subject :
                给定两个字符串 s 和 t，它们只包含小写字母。
                字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
                请找出在 t 中被添加的字母。

                示例:
                        输入：
                        s = "abcd"
                        t = "abcde"

                        输出：
                        e
                        解释：
                        'e' 是那个被添加的字母。
    */

    /* PS : 这道题有两种解法，第一种就是对两个字符串进行排序，然后定义两个指针，同时遍历两个字符串，
            直到有一边越界了或者是找到了那个不同的字符，退出循环

            第二种就是用一个变量接收字符的ASCII码，先用一个循环加上s的所有字符的ASCII码
            (每一个cha类型的字符都对应一个ASCII码)，然后再一个循环减去t中所有字符的ASCII码，
            最终剩下的值，如果不为0，就是多出来的字符
    */

    // 解法一 : 双指针法

    public char findTheDifference1(String s, String t) {
        if (s.length() == t.length()) return ' ';
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        Arrays.sort(ss);
        Arrays.sort(tt);
        int i = 0;
        int j = 0;
        while (i < ss.length && j < tt.length) {
            if (ss[i] != tt[j]) return tt[j];
            i++;
            j++;
        }
        return tt[j];
    }


    // 解法二 ：ASCII码差值法
    public char findTheDifference(String s, String t) {
        int res = 0;
        char[] ss = s.toCharArray();
        char[] tt = t.toCharArray();
        for (char c : ss) res -= c;
        for (char c : tt) res += c;
        return (char) res;
    }
}
