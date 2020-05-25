package com.github.guang19.designpattern.adapter;

/**
 * @author guang19
 * @date 2020/5/24
 * @description 适配器模式测试
 * @since 1.0.0
 */
public class AdapterTest
{
    public static void main(String[] args)
    {
        FLVVideo flvVideo = new FLVVideo();

        //使用flv视频适配mp4视频
        MP4Video mp4Video = new VideoAdapter(flvVideo);

        MP4Player mp4Player = new MP4Player();

        mp4Player.play(mp4Video);
    }
}
