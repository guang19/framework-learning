package com.github.anhTom2000.algorithm.strmatch;

/**
 * @author guang19
 * @date 2020/5/15
 * @description 暴力匹配
 * @since 1.0.0
 */
public class ViolenceMatch
{
    public static void main(String[] args)
    {
        /**
         * 16
         * 5
         * source :  a b c d e f a b c d e a b c d a b c d e f g
         * target :  a b c d a
         * 正确结果: 11
         *
         * 暴力匹配过程:
         *  定义2个指针i , j 分别指向source和target，
         *  如果j指向的target位置的值与i指向source位置的值相等，那么i和j往后移动，
         *  否则重置i和j，重新比较。
         *
         *
         */
        String source = "abcdefabcdeabcdabcdefg";
        String target = "abcda";
        System.out.println(violenceMatch(source,target));
    }

    //暴力匹配
    //source: 源字符串
    //target: 要匹配的模式串
    //返回target在source中出现的位置
    public static int violenceMatch(String source,String target)
    {
        int sLen  = source.length();
        int tLen = target.length();
        char[] sCharArr = source.toCharArray();
        char[] tCharArr = target.toCharArray();
        int i = 0 , j = 0;
        while (i < sLen && j < tLen)
        {
            if (sCharArr[i] == tCharArr[j])
            {
                ++i;
                ++j;
            }
            else
            {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == tLen)
        {
            return i - j;
        }
        return -1;
    }
}
