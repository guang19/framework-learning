package com.github.yangguang19.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author yangguang
 * @Date 2020/1/31
 * @Description TODO      测试 entity
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Person implements Serializable
{
    private static final long serialVersionUID = -1046991842379162205L;

    private Long personId;

    private String name;

    private Integer age;
}
