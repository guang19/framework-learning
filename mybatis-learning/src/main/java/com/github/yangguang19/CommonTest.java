package com.github.yangguang19;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.github.yangguang19.mapper.PersonMapper;

import java.io.InputStream;

/**
 * @Description : TODO      测试类
 * @Author :    yangguang
 * @Date :      2019/12/22
 */
public class CommonTest {

    public static void main(String[] args)
    {
        InputStream inputStream = CommonTest.class.getClassLoader().getResourceAsStream("mybatis-configuration.xml");
        /********************************/
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

        personMapper.selectPersonById(1L);
        System.out.println(personMapper.getClass());
    }
}
