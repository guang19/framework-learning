package com.github.guang19.designpattern.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 具体中介者
 * @since 1.0.0
 */
public class ConcreteMediator extends Mediator
{
    //已注册的同事
    private List<Colleague> registerColleagues = new ArrayList<>();

    @Override
    public void registerColleague(Colleague colleague)
    {
        if (colleague != null && !this.registerColleagues.contains(colleague))
        {
            this.registerColleagues.add(colleague);
            colleague.setMediator(this);
        }
    }

    @Override
    public void forwardMessage(Colleague colleague,String message)
    {
        for (Colleague c : registerColleagues)
        {
            if (!c.equals(colleague))
            {
                c.receive(message);
            }
        }
    }
}
