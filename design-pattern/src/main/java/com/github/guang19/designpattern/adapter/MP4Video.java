package com.github.guang19.designpattern.adapter;

/**
 * @author guang19
 * @date 2020/5/24  Mp4视频
 * @description
 * @since 1.0.0
 */
public class MP4Video implements Video
{
    @Override
    public void play()
    {
        System.out.println("mp4 video is playing.");
    }
}
