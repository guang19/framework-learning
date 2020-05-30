package com.github.guang19.designpattern.facade;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 外观模式测试
 * @since 1.0.0
 */
public class FacadeTest
{
    public static void main(String[] args)
    {
        IdGeneratorService idGeneratorService = new IdGeneratorService();
        System.out.println(idGeneratorService.uuid());
        System.out.println(idGeneratorService.snowflakeId());
    }
}
