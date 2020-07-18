package com.github.guang19.entity;

import java.io.Serializable;

/**
 * @Description : TODO          测试实体类
 * @Author :    yangguang
 * @Date :      2019/12/22
 */
public class Person implements Serializable {
    private static final long serialVersionUID = -2098539486101139093L;

    //序列化ID

    private Long pId;

    private String name;

    private Integer age;


    public Long getpId()
    {
        return pId;
    }

    public void setpId(Long pId)
    {
        this.pId = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "pId=" + pId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
