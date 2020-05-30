package com.github.guang19.designpattern.adapter;

/**
 * @author guang19
 * @date 2020/5/24
 * @description  FLV视频
 * @since 1.0.0
 */
public class FLVVideo implements Video
{
    @Override
    public void play()
    {
        System.out.println("flv video is playing.");
    }
}
