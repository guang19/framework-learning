package com.github.guang19.designpattern.strategy;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 环境角色
 * @since 1.0.0
 */
public class SortingContext
{
    private Sorting sorting;

    public Sorting getSorting()
    {
        return sorting;
    }

    public void setSorting(Sorting sorting)
    {
        this.sorting = sorting;
    }

    public void sort(int[] nums)
    {
        sorting.sort(nums);
    }
}
