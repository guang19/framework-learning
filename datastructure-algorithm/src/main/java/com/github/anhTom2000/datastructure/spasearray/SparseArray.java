package com.github.anhTom2000.datastructure.spasearray;


import java.util.Arrays;

/**
 * Created on 21:09  13/03/2020
 * Description:
 * TODO 稀疏数组实现
 *
 * @author Weleness
 */

public class SparseArray
{
    public static void main(String[] args)
    {
        //创建一个8行10列的数组
        int[][] sourceArr = new int[8][10];
        //给这个矩阵分配一些值
        sourceArr[0][3] = 3;
        sourceArr[3][5] = 10;
        sourceArr[3][6] = 11;
        sourceArr[5][2] = 13;
        sourceArr[6][3] = 15;

        System.out.println("原数组： ");
        for (int i = 0 ; i < sourceArr.length; ++i)
        {
            for (int j = 0 ; j < sourceArr[i].length; ++j)
            {
                System.out.printf("%d\t",sourceArr[i][j]);
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------");

        //计算二维数组的有效元素的数量
        int num = 0;
        for (int i = 0 ; i < sourceArr.length; ++i)
        {
            for (int j = 0 ; j < sourceArr[i].length; ++j)
            {
                if (sourceArr[i][j] != 0)
                {
                    ++num;
                }
            }
        }

        //创建稀疏数组, 稀疏数组的行数为原数组的元素的数量 + 1，因为第一行需要存放原数组的元数据。稀疏数组的列数固定为3
        int[][] sparseArr = new int[num + 1][3];

        //将原数组的元数据赋予稀疏数组
        sparseArr[0][0] = 8;  //第一行的第一个元素为原数组的行数
        sparseArr[0][1] = 9;  // 第一行的第二个元素为原数组的列数
        sparseArr[0][2] = num; // 第一行的第三个元素为原数组的有效元素的数量

        //count纪录当前赋值到第几行了
        int count = 1;
        //将原数组的有效元素赋予稀疏数组
        for (int i = 0 ; i < sourceArr.length; ++i)
        {
            for (int j = 0 ; j < sourceArr[i].length; ++j)
            {
                if(sourceArr[i][j] != 0)
                {
                    //将有效元素赋予稀疏数组
                    sparseArr[count][0] = i;  //当前元素处于原数组的第几行
                    sparseArr[count][1] = j;  //当前元素处于原数组的第几列
                    sparseArr[count][2] = sourceArr[i][j]; //当前元素的值
                    ++count;
                }
            }
        }

        //创建的稀疏数组为:
//        8	9	5       //原数组有: 8行 9列 5个元素
//        0	3	3       //原数组的[0][3]元素的值为为3 ， 下面的依次类推
//        3	5	10
//        3	6	11
//        5	2	13
//        6	3	15
        System.out.println("根据原数组创建的稀疏数组为： ");
        for (int i = 0 ; i < sparseArr.length; ++i)
        {
            for (int j = 0 ; j < sparseArr[i].length; ++j)
            {
                System.out.printf("%d\t",sparseArr[i][j]);
            }
            System.out.println();
        }

        System.out.println("-----------------------------------------");

        //将稀疏数组恢复为原数组

        //第一步: 创建与原数组大小相同数组
        int[][] newArr = new int[sparseArr[0][0]][sparseArr[0][1]];

        //第二步: 赋值
        for (int i = 0 ; i < sparseArr[0][2]; ++i)
        {
            newArr[sparseArr[i + 1][0]][sparseArr[i + 1][1]] = sparseArr[i + 1][2];
        }

        System.out.println("恢复后的数组为: ");
        for (int i = 0 ; i < newArr.length; ++i)
        {
            for (int j = 0 ; j < newArr[i].length; ++j)
            {
                System.out.printf("%d\t",newArr[i][j]);
            }
            System.out.println();
        }
    }

}
