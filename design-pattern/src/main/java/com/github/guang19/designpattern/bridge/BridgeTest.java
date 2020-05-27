package com.github.guang19.designpattern.bridge;

/**
 * @author guang19
 * @date 2020/5/25
 * @description 桥接模式测试
 * @since 1.0.0
 */
public class BridgeTest
{
    public static void main(String[] args)
    {
        Font middleRedFont = new MiddleFont(new Red());
        Font smallBlackFont = new SmallerFont(new Black());

        middleRedFont.write("ubuntu mono");

        smallBlackFont.write("ubuntu mono");
    }
}
