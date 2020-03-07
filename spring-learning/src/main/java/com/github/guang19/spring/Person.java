package com.github.guang19.spring;

import lombok.*;

/**
 * @author yangguang
 * @date 2020/3/7
 * @description <p>测试bean</p>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person
{
    private Long personId;

    private String personName;

    private Integer age;

    public Person(Long personId, String personName, Integer age)
    {
        System.out.println("init bean type of person");
        this.personId = personId;
        this.personName = personName;
        this.age = age;
    }
}
