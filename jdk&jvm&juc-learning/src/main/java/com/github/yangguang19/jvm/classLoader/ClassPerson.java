package com.github.yangguang19.jvm.classLoader;

/**
 * @Description : TODO          测试用的,使用自定义的类加载器加载此类
 * @Author :    yangguang
 * @Date :      2019/11/19
 */
public class ClassPerson {
    private String name;
    private int age;

    public ClassPerson() {
    }

    public ClassPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
