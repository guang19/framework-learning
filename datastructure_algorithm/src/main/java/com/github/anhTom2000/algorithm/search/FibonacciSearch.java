package com.github.anhTom2000.algorithm.search;

import java.util.Arrays;

/**
 * @author guang19
 * @date 2020/5/3
 * @description 斐波那契查找
 * @since 1.0.0
 */
public class FibonacciSearch
{
    public static void main(String[] args)
    {
        int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        /**
         *
         * 关于斐波那契查找的原理可以参考这篇文章:
         * https://blog.csdn.net/luochoudan/article/details/51629338
         * 我觉得讲的挺透彻的
         *
         */

//        System.out.println(Arrays.toString(getFbSeq(16)));
        System.out.println(fbSearch(arr,15));
    }

    public static int fbSearch(int[] array, int searchVal)
    {
        assert array != null && array.length > 2;
        assert searchVal >= array[0] && searchVal <= array[array.length - 1];

        int len = array.length;

        int left = 0;
        int right = len - 1;

        //这里创建长度为len + 1的斐波那契数列
        int[] fbSeq = getFbSeq(len + 1);

        //计算: 数组的长度 在 斐波那契数列中的下标
        int cur = 0;
        while (len > fbSeq[cur] - 1)
        {
            ++cur;
        }
        //创建一个与 fbSeq[cur] - 1 大小相同的数组
        int[] temp = new int[fbSeq[cur] - 1];
        for (int i = 0 ; i < temp.length ;++i)
        {
            temp[i] = i >= len ? array[len - 1] : array[i];
        }
        System.out.println(Arrays.toString(temp));

        int mid;
        while (left <= right)
        {
            mid = left + fbSeq[cur - 1] - 1;

            //fb[k] - 1 = fb[k - 1] - 1 + fb[k - 2] -1

            //如果searchVal处于前半段，即： fb[k - 1] - 1 , 所以 cur -= 1
            if(searchVal < temp[mid])
            {
                right = mid - 1;
                cur -= 1;
            }
            //如果searchVal处于后半段，即： fb[k - 2] -1 ，所以 cur -= 2
            else if(searchVal > temp[mid])
            {
                left = mid + 1;
                cur -= 2;
            }
            else
            {
                //如果mid小于等于right，说明mid的位置就是要查找的值的位置
                if (mid <= right)
                {
                    return mid;
                }
                else
                {
                    //如果mid > right，说明mid进入了temp的填充位置，即最后一个元素就是要查找的值
                    return right;
                }
            }
        }
        return -1;
    }

    //获取指定长度的斐波那契数列
    private static int[] getFbSeq(int length)
    {
        assert length >= 2;
        int[] fbseq = new int[length];
        fbseq[0] = 1;
        fbseq[1] = 1;
        int i = 2;
        while (i < length)
        {
            fbseq[i] = fbseq[i - 1] + fbseq[i - 2];
            ++i;
        }
        return fbseq;
    }
}
