package com.github.guang19.designpattern.bridge;

/**
 * @author guang19
 * @date 2020/5/25
 * @description 具体抽象化角色: 中等字体
 * @since 1.0.0
 */
public class MiddleFont extends Font
{

    public MiddleFont(Color color)
    {
        super(color);
    }

    @Override
    public void write(String type)
    {
        System.out.println("write middle word with color of " + color.getColor() + " and type of " + type);
    }
}
