package com.github.anhTom2000.线性数据结构.稀疏数组;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21:59  14/03/2020
 * Description:
 * TODO  实现本地棋盘保存
 * @author Weleness
 */

public class SparseArrayTest {
    public static void main(String[] args) {
        //创建一个原始的二维数组11*11
        //0:表示没有棋子，1表示黑子 2 表示蓝子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[2][4] = 1;
        chessArr1[5][6] = 2;
        chessArr1[10][10] = 1;
        System.out.println("原始的二维数组：");
        for (int[] ints : chessArr1) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println();
        }

        int sum = 0;
        //1.先遍历二维数组 得到非0数据的个数
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1.length; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
        //创建对应的稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        //遍历二维数组，将非0的值存放到稀疏数组中
        int count = 0;//count 用于记录是第几个非0数据
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1.length; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }
        System.out.println();
        System.out.println("得到的稀疏数组为：");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n ", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        fileInput("map.data", sparseArr);
        int[][] ints = sparesArr();
        System.out.println("还原后的二维数组为:");
        for (int[] i : chessArr1) {
            for (int anInt : i) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println();
        }
    }

    public static void fileInput(String filename, int[][] arr) {
        File f = new File(filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
               throw new RuntimeException("本地棋盘保存失败!");
            }
        } else {
            int n = 0;
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(f));
                for (int i = 0; i < arr.length; i++) {
                    bos.write((arr[i][0]+"\r\n" + arr[i][1]+"\r\n" + arr[i][2]+"\r\n").getBytes());
                    bos.flush();
                }
                bos.close();
            } catch (Exception e) {
               throw new RuntimeException("棋盘保存异常!");
            }
        }
    }

    public static int[][] sparesArr() {
        BufferedReader bfr = null;
        int[][] sparesArr = null;
        int[][] chessArr = null;
        List<String> list = new ArrayList<String>();
        try {
            bfr= new BufferedReader(new FileReader("map.data"));
            String s = null;
            while ((s = bfr.readLine())!=null){
                list.add(s.trim());
            }
            bfr.close();
            int n = 0;
            sparesArr = new int[Integer.parseInt(list.get(2))+1][3];
            for (int i = 0; i < sparesArr.length; i++) {
                for (int j = 0; j < 3; j++) {
                    sparesArr[i][j] =Integer.parseInt(list.get(n));
                    n++;
                }
            }
            chessArr = new int[sparesArr[0][0]][sparesArr[0][1]];
            for (int i = 1; i < sparesArr.length; i++) {
                chessArr[sparesArr[i][0]][sparesArr[i][1]] = sparesArr[i][2];
            }
        }catch (Exception e){
            throw new RuntimeException("棋盘读取异常!");
        }


        return chessArr;
    }
}
