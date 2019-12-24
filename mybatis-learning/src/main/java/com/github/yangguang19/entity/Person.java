package com.github.yangguang19.entity;

import java.io.Serializable;

/**
 * @Description : TODO          测试实体类
 * @Author :    yangguang
 * @Date :      2019/12/22
 */
public class Person implements Serializable {

    //序列化ID
    private static final long serialVersionUID = 336571784061779072L;

    private Long p_id;

    private String p_name;

    private Integer age;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "p_id=" + p_id +
                ", p_name='" + p_name + '\'' +
                ", age=" + age +
                '}';
    }
}
