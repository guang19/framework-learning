package com.github.guang19.designpattern.adapter;

/**
 * @author guang19
 * @date 2020/5/24
 * @description 视频适配器
 * @since 1.0.0
 */
public class VideoAdapter extends MP4Video implements Video
{
    private FLVVideo flvVideo;

    public VideoAdapter(FLVVideo flvVideo)
    {
        this.flvVideo = flvVideo;
    }

    @Override
    public void play()
    {
        flvVideo.play();
    }
}
