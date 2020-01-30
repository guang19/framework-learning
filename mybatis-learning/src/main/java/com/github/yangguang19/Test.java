package com.github.yangguang19;

import com.github.yangguang19.mapper.PersonMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @Author yangguang
 * @Date 2020/1/9
 * @Description TODO
 */
public class Test
{
  public static void main(String[] args)
  {
    InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("mybatis-configuration.xml");
    /********************************/
    SqlSessionFactory sqlSessionFactory =
      new SqlSessionFactoryBuilder().build(inputStream);

    SqlSession sqlSession = sqlSessionFactory.openSession();

    PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

    System.out.println(personMapper.selectPersonById(1L));
    System.out.println(personMapper.getClass());
  }
}
