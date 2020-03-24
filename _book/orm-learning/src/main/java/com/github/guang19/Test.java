package com.github.guang19;

import com.github.guang19.entity.Person;
import com.github.guang19.mapper.PersonMapper;
import com.mysql.cj.jdbc.Driver;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @Author yangguang
 * @Date 2020/1/9
 * @Description TODO
 */
public class Test
{
  public static void main(String[] args) throws Throwable
  {
    InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("mybatis-configuration.xml");

//    /********************************/
    SqlSessionFactory sqlSessionFactory =
      new SqlSessionFactoryBuilder().build(inputStream);
//
    SqlSession sqlSession = sqlSessionFactory.openSession();
//
    PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
//
    Person person = personMapper.selectPersonById(1L);
    System.out.println(person);
  }
}
