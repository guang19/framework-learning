package com.github.guang19.designpattern.templatemethod;

/**
 * @author guang19
 * @date 2020/5/29
 * @description 抽象模板
 * @since 1.0.0
 */
public abstract class AbstractTemplate
{

    //模板方法：保存用户的信息
    public void saveUser()
    {
        check();
        save();
        doSomething();
    }

    //抽象方法：校验用户的信息
    protected abstract void check();


    //具体方法：保存用户的信息
    protected void save()
    {
        System.out.println("保存用户的信息");
    }


    //钩子方法
    protected void doSomething()
    {

    }
}
