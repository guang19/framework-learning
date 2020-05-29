package com.github.guang19.designpattern.templatemethod;

/**
 * @author guang19
 * @date 2020/5/29
 * @description 模板方法模式测试
 * @since 1.0.0
 */
public class TemplateMethodTest
{
    public static void main(String[] args)
    {
        AbstractTemplate template = new ConcreteTemplate();
        template.saveUser();
    }
}
