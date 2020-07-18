package com.github.guang19;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guang19
 * @date 2020/7/18
 * @description
 */
public class PgsqlTest
{

    private Configuration configuration;
    private SimpleExecutor simpleExecutor;
    private ReuseExecutor reuseExecutor;
    private BatchExecutor batchExecutor;

    @Before
    public void before()
    {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = factoryBuilder.build(this.getClass().getClassLoader().getResourceAsStream("pgsql-mybatis-configuration.xml"));
        this.configuration = factory.getConfiguration();
        Transaction transaction = new JdbcTransaction(this.configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED,true);
        this.simpleExecutor = new SimpleExecutor(factory.getConfiguration(),transaction);
        this.reuseExecutor = new ReuseExecutor(factory.getConfiguration(),transaction);
        this.batchExecutor = new BatchExecutor(factory.getConfiguration(),transaction);
    }

    @Test
    public void test01() throws Exception
    {
        MappedStatement ms = this.configuration.getMappedStatement("com.github.guang19.mapper.PersonMapper.selectPersonById");
        Object person = simpleExecutor.doQuery(ms,1L, RowBounds.DEFAULT,SimpleExecutor.NO_RESULT_HANDLER,ms.getBoundSql(1L));
        System.out.println(person);
    }

    @Test
    public void test02() throws Exception
    {
        MappedStatement ms = this.configuration.getMappedStatement("com.github.guang19.mapper.PersonMapper.selectPersonById");
        Object person1 = reuseExecutor.doQuery(ms,1L, RowBounds.DEFAULT,SimpleExecutor.NO_RESULT_HANDLER,ms.getBoundSql(1L));
        Object person2 = reuseExecutor.doQuery(ms,1L, RowBounds.DEFAULT,SimpleExecutor.NO_RESULT_HANDLER,ms.getBoundSql(1L));
    }

    @Test
    public void test03() throws Exception
    {
        MappedStatement ms = this.configuration.getMappedStatement("com.github.guang19.mapper.PersonMapper.updateNameById");
        Map<String,Object> args = new HashMap<>();
        args.put("name","guang21");
        args.put("pId",3L);
        batchExecutor.doUpdate(ms,args);
        batchExecutor.doUpdate(ms,args);
        List<BatchResult> batchResults = batchExecutor.doFlushStatements(false);
        for (BatchResult result : batchResults)
        {
            System.out.println(Arrays.toString(result.getUpdateCounts()));
        }
    }
}
