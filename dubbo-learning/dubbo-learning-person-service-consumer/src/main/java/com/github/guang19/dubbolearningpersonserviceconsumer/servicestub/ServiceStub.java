package com.github.guang19.dubbolearningpersonserviceconsumer.servicestub;

import com.github.guang19.entity.Person;
import com.github.guang19.service.PersonService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yangguang
 * @Date 2020/2/1
 * @Description TODO        本地存根,在我看来,其思想类似AOP
 */
@Slf4j
public class ServiceStub implements PersonService
{
    private final PersonService personService;

    public ServiceStub(PersonService personService)
    {
        this.personService = personService;
    }

    @Override
    public Person getPersonByPersonId(Long personId)
    {
        log.info("do things before service");
        Person person = personService.getPersonByPersonId(personId);
        log.info("do things after service");
        return person;
    }
}
