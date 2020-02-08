package com.github.guang19.dubbolearningpersonserviceconsumer.controller;


import com.github.guang19.entity.Person;
import com.github.guang19.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author yangguang
 * @Date 2020/1/31
 * @Description TODO        consumer
 */
@Slf4j
@Controller
public class ConsumerController
{
//    @Reference(version = "2.0.0",stub = "com.github.yangguang19.dubbolearningpersonserviceconsumer.servicestub.ServiceStub",loadbalance = "roundrobin" )
    @Reference(version = "2.0.0",loadbalance = "roundrobin" )
    private PersonService personService;

//    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @ResponseBody

    @GetMapping("/get/person/{personId}")
    public Person consume(@PathVariable("personId")Long personId)
    {
        Person person = null;
        try
        {
            person = personService.getPersonByPersonId(personId);
        }
        catch (RpcException rpcException)
        {
            log.error(rpcException.getMessage());
        }
        return person;
    }

    //fallbackMethod
    public Person fallbackMethod(Long personId)
    {
        return new Person(personId,"fallbackMethod",19);
    }


}
