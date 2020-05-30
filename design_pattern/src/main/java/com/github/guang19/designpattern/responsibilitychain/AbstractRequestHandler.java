package com.github.guang19.designpattern.responsibilitychain;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 抽象处理者
 * @since 1.0.0
 */
public abstract class AbstractRequestHandler
{
    private AbstractRequestHandler requestHandler;

    public void setRequestHandler(AbstractRequestHandler requestHandler)
    {
        this.requestHandler = requestHandler;
    }

    public AbstractRequestHandler getRequestHandler()
    {
        return this.requestHandler;
    }

    public abstract void handle(String request);
}
