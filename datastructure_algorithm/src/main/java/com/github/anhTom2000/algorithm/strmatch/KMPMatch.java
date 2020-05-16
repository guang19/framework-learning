package com.github.anhTom2000.algorithm.strmatch;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/15
 * @description kmp匹配算法
 * @since 1.0.0
 */
public class KMPMatch
{
    public static void main(String[] args)
    {

        /**
         *
         * kmp算法我个人觉得是比较有难度的一种算法，
         * 我觉得倒是不难理解，但要想到相关步骤还是挺有难度的，
         * 所以还是那句话： "talk is cheap , show me the code."
         * 建议各位同学看下相关视频和博客，这样更加好理解
         *
         * 参考视频: https://www.bilibili.com/video/BV1hW411a7ys
         * 参考文章: https://ethsonliu.com/2018/04/kmp.html
         *
         */

        String source1 = "abcabcdeabacdabc";
        String pattern1 = "abacd";

        System.out.println(kmpMatch(source1,pattern1));

        String source2 = "abcdabceabcd";
        String pattern2 = "abcea";

        System.out.println(kmpMatch(source2,pattern2));

    }

    //根据当前字符串，生成前缀匹配表
    public static void prefixTable(String patternStr , int[] prefixTable)
    {
        int p = -1;
        int s = 0;
        //前缀表的第一位置为 -1 ， 记为一个标志位
        prefixTable[0] = -1;
        int len = patternStr.length();
        char[] patternArr = patternStr.toCharArray();
        while (s < len - 1)
        {
            //匹配
            if (p == -1 || patternArr[p] == patternArr[s])
            {
                ++p;
                ++s;
                prefixTable[s] = p;
            }
            //不匹配
            else
            {
                //p回溯，重新匹配
                p = prefixTable[p];
            }
        }
    }

    //如果匹配成功，返回patternStr在sourceStr中出现的位置，否则返回-1
    public static int kmpMatch(String sourceStr,String patternStr)
    {
        int[] prefixTable = new int[patternStr.length()];
        prefixTable(patternStr,prefixTable);
        int i = 0 ;
        int j = 0 ;
        int sLen = sourceStr.length();
        int pLen = patternStr.length();
        char[] sourceArr = sourceStr.toCharArray();
        char[] patternArr = patternStr.toCharArray();

        while (i < sLen && j < pLen)
        {
            //因为前缀表的第一个元素为-1,所以此处需要首先判断j是否为-1,如果为-1,证明上次判断失败了，那么就需要重新将j置为0(j+1 = -1 + 1 = 0) ，然后继续判断
            if (j == -1 || sourceArr[i] == patternArr[j])
            {
                ++i;
                ++j;
            }
            //匹配失败，j回溯，重新匹配
            else
            {
                j = prefixTable[j];
            }
        }
        if (j == pLen)
        {
            return i - j;
        }
        return -1;
    }
}
