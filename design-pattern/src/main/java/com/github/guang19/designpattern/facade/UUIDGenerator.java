package com.github.guang19.designpattern.facade;

import java.util.UUID;

/**
 * @author guang19
 * @date 2020/5/27
 * @description UUID生成器
 * @since 1.0.0
 */
public class UUIDGenerator implements IdGenerator
{
    @Override
    public String generateId()
    {
        return "UUID";
    }
}
