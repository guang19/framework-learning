package com.github.guang19.dubbolearningpersonserviceprovider1.service;

import com.github.guang19.entity.Person;
import com.github.guang19.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yangguang
 * @Date 2020/1/31
 * @Description TODO    person service implement 01
 */


@Slf4j
@Service(version = "1.0.0",timeout = 1000,retries = 3)
@Component
public class PersonServiceImpl01 implements PersonService
{
    private AtomicInteger retryCount = new AtomicInteger(0);

    @Override
    public Person getPersonByPersonId(Long personId)
    {
//        log.info("consumer retry : ------------> " + retryCount.incrementAndGet() );
        log.info("current version is : --- >  1.0.0");
        try
        {
            TimeUnit.SECONDS.sleep(2);
        }catch (Exception e)
        {
        }
        return new Person().setPersonId(1L).setName("yangxiaoguang1").setAge(19);
    }
}
