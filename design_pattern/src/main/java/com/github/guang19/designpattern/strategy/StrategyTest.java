package com.github.guang19.designpattern.strategy;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 策略模式测试
 * @since 1.0.0
 */
public class StrategyTest
{
    public static void main(String[] args)
    {

        int[] nums = {1,2,3,4,5,6};

        SortingContext sortingContext = new SortingContext();
        sortingContext.setSorting(new BubbleSort());

        sortingContext.sort(nums);

        sortingContext.setSorting(new MergeSort());
        sortingContext.sort(nums);
    }
}
