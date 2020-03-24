package com.github.guang19.entity;

import lombok.*;
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
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable
{
    private static final long serialVersionUID = -1046991842379162205L;

    private Long personId;

    private String name;

    private Integer age;
}
