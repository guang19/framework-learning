package com.github.yangguang19.service;

import com.github.yangguang19.entity.Person;

/**
 * @Author yangguang
 * @Date 2020/1/31
 * @Description TODO person service
 */
public interface PersonService
{
    public Person getPersonByPersonId(Long personId);
}
