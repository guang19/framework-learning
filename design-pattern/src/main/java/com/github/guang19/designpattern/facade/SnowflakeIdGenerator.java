package com.github.guang19.designpattern.facade;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 雪花ID生成器
 * @since 1.0.0
 */
public class SnowflakeIdGenerator implements IdGenerator
{
    @Override
    public String generateId()
    {
        return "雪花ID";
    }
}
