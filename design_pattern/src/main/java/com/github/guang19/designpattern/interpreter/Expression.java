package com.github.guang19.designpattern.interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 表达式
 * @since 1.0.0
 */
public abstract class Expression
{
    protected List<String> context = new ArrayList<>();

    public abstract boolean interpret(String expression);

    public void add(String expression)
    {
        if (expression != null)
        {
            this.context.add(expression);
        }
    }
}
