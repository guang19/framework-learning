package com.github.guang19.designpattern.bridge;

/**
 * @author guang19
 * @date 2020/5/25
 * @description 具体抽象化角色: 小字
 * @since 1.0.0
 */
public class SmallerFont extends Font
{
    public SmallerFont(Color color)
    {
        super(color);
    }

    @Override
    public void write(String type)
    {
        System.out.println("write small word with color of " + color.getColor() + " and type of " + type);
    }
}
