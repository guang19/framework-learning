package com.github.guang19.designpattern.interpreter;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 或运算符
 * @since 1.0.0
 */
public class OrExpression extends Expression
{
    private Expression expression1;
    private Expression expression2;

    public OrExpression(Expression expression1,Expression expression2)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public boolean interpret(String expression)
    {
        return expression1.interpret(expression) || expression2.interpret(expression);
    }
}
