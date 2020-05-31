package com.github.guang19.designpattern.interpreter;

/**
 * @author guang19
 * @date 2020/5/31
 * @description 解释器模式测试
 * @since 1.0.0
 */
public class InterpreterTest
{
    public static void main(String[] args)
    {
        TerminalExpression terminalExpression1 = new TerminalExpression();
        terminalExpression1.add("1");
        terminalExpression1.add("2");

        TerminalExpression terminalExpression2 = new TerminalExpression();
        terminalExpression2.add("3");
        terminalExpression2.add("4");

        OrExpression orExpression = new OrExpression(terminalExpression1,terminalExpression2);

        System.out.println(orExpression.interpret("1"));
    }
}
