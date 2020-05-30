package com.github.guang19.designpattern.facade;

/**
 * @author guang19
 * @date 2020/5/27
 * @description     Id生成接口
 * @since 1.0.0
 */
public interface IdGenerator
{
    //生成唯一ID
    public abstract String generateId();
}
