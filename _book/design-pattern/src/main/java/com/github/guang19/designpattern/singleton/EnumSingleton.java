package com.github.guang19.designpattern.singleton;

/**
 * @author yangguang
 * @date 2020/3/5
 * @description <p>单例模式 - 枚举</p>
 */
public enum  EnumSingleton
{
    INSTANCE;

    public EnumSingleton getInstance()
    {
        return INSTANCE;
    }
}
