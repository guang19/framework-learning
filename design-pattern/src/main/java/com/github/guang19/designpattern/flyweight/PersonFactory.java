package com.github.guang19.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guang19
 * @date 2020/5/27
 * @description 享元工厂
 * @since 1.0.0
 */
public class PersonFactory
{
    private Map<String,Person> personMap = new HashMap<>();

    public Person getPerson(String name)
    {
        Person person = personMap.get(name);
        if (person == null)
        {
            person = new Programmer();
            person.setName(name);
            personMap.put(name,person);
        }
        else
        {
            System.out.println("person : " + name + " 已存在，获取成功。");
        }
        return person;
    }
}
