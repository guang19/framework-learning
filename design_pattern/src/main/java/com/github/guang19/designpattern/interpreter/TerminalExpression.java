package com.github.guang19.designpattern.interpreter;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 终结符表达式
 * @since 1.0.0
 */
public class TerminalExpression extends Expression
{

    @Override
    public boolean interpret(String expression)
    {
        if (expression != null)
        {
            return this.context.contains(expression);
        }
        return false;
    }
}
