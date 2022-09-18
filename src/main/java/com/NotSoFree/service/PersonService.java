package com.NotSoFree.service;

import com.NotSoFree.domain.Person;
import java.util.List;

public interface PersonService {

    public List<Person> listPeople()  throws Exception;

    public void save(Person person)  throws Exception;

    public void delete(Long person)  throws Exception;

    public Person findPersonById(Long person)  throws Exception;
}
