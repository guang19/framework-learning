package com.github.guang19.designpattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guang19
 * @date 2020/5/30
 * @description 对象结构
 * @since 1.0.0
 */
public class ObjectStructure
{
    private List<Element> elementList = new ArrayList<>();

    //访问所有的Element
    public void accept(Visitor visitor)
    {
        for (Element element : elementList)
        {
            element.accept(visitor);
        }
    }

    public void add(Element element)
    {
        this.elementList.add(element);
    }

    public void remove(Element element)
    {
        this.elementList.remove(element);
    }
}
