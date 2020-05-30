package com.github.guang19.designpattern.facade;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 唯一ID生成器服务
 * @since 1.0.0
 */
public class IdGeneratorService
{
    private IdGenerator snowflakeIdGenerator;
    private IdGenerator uuidIdGenerator;

    public IdGeneratorService()
    {
        this.snowflakeIdGenerator = new SnowflakeIdGenerator();
        this.uuidIdGenerator = new UUIDGenerator();
    }

    public String uuid()
    {
        return uuidIdGenerator.generateId();
    }

    public String snowflakeId()
    {
        return snowflakeIdGenerator.generateId();
    }
}
