package com.github.yangguang19.mapper;


import com.github.yangguang19.entity.Person;
import org.apache.ibatis.annotations.Param;

/**
 * @Description : TODO          测试mapper
 * @Author :    yangguang
 * @Date :      2019/12/22
 */
public interface PersonMapper {

    public Person selectPersonById(@Param("p_id") Long p_id);
}
