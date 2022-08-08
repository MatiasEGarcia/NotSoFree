package com.NotSoFree.service;

import com.NotSoFree.dao.PersonDao;
import com.NotSoFree.domain.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService{
    
    @Autowired
    PersonDao personDao;

    @Override
    @Transactional(readOnly = true)
    public List<Person> listPeople() {
        return personDao.findAll();
    }

    @Override
    public void guardar(Person person) {
        personDao.save(person);
    }

    @Override
    public void eliminar(Long person) {
        personDao.delete(this.findPersonById(person));
    }


    @Override
    @Transactional(readOnly = true)
    public Person findPersonById(Long person) {
        return personDao.findById(person).orElseThrow(null);
    }
    
}
