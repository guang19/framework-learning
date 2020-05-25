package com.github.guang19.designpattern.bridge;

/**
 * @author guang19
 * @date 2020/5/25
 * @description 抽象化角色
 * @since 1.0.0
 */
public abstract class Font
{
    protected Color color;

    public Font(Color color)
    {
        this.color = color;
    }

    public abstract void write(String type);
}
