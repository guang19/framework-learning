package com.github.anhTom2000.datastructure.稀疏数组;


/**
 * Created on 21:09  13/03/2020
 * Description:
 * TODO 稀疏数组实现
 *
 * @author Weleness
 */


public class SparseArray {
    public static void main(String[] args) {
        //    创建一个原始的二维数组 11*11
        //    0: 表示没有棋子 ， 1 表示黑子 2 表示白子

        int[][] chessArray = new int[11][11];
        //    创建测试数据
        chessArray[1][1] = 1;
        chessArray[2][3] = 2;
        chessArray[5][6] = 2;

        //    原始的二维数组（棋盘）
        //    输出原始的二维数组，查看棋盘
        for (int[] row : chessArray) {
            for (int data : row) {
                System.out.print(data + " ");
            }
            System.out.println();
        }
        /*
    转换思路：
            1. 遍历原始的二维数组，得到有效数据的个数（非0）
    * */
        int sum = 0;
        for (int i = 0; i < chessArray.length; i++) {
            for (int j = 0; j < chessArray[0].length; j++) {
                sum += chessArray[i][j] != 0 ? 1 : 0;
            }
        }

        //  2.创建对应的稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //  给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //  遍历二维数组,将非0的值存放到稀疏数组中
        int count = 0; // count 用于记录是第几个非0数据
        for (int i = 0; i < chessArray.length; i++) {
            for (int j = 0; j < chessArray[0].length; j++) {
                if (chessArray[i][j] != 0) {
                    ++count;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArray[i][j];
                }
            }
        }

        //    输出稀疏数组
        System.out.println();
        System.out.println("得到的稀疏数组为 :");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        System.out.println();
        //    将稀疏数组 --> 恢复成棋盘
        /*
         *   1. 先读取稀疏数组的第一行,根据第一行的数据,创建原始的二维数组
         *   2. 再读取稀疏数组的后几行数据,并赋给原始的二维数组
         * */

        //    创建原始的二维数组
        int[][] chessRepair = new int[sparseArr[0][0]][sparseArr[0][1]];

        //    读取数据,还原
        for (int i = 1; i <= sparseArr[0][2]; i++) {
            chessRepair[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        //    还原后的数组为:
        for (int[] row : chessRepair) {
            for (int data : row) {
                System.out.print(data + " ");
            }
            System.out.println();
        }

    }

}
