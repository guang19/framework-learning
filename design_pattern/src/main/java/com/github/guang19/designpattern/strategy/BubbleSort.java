package com.github.guang19.designpattern.strategy;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体策略：冒泡排序
 * @since 1.0.0
 */
public class BubbleSort implements Sorting
{
    @Override
    public void sort(int[] nums)
    {
        System.out.println("冒泡排序");
    }
}
