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
public class CommonTest1
{
  public static void main(String[] args)
  {
    InputStream inputStream = CommonTest1.class.getClassLoader().getResourceAsStream("mybatis-configuration.xml");
    /********************************/
    SqlSessionFactory sqlSessionFactory =
      new SqlSessionFactoryBuilder().build(inputStream);

    SqlSession sqlSession = sqlSessionFactory.openSession();

    PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

    System.out.println(personMapper.selectPersonById(1L));
    System.out.println(personMapper.getClass());
  }
}
