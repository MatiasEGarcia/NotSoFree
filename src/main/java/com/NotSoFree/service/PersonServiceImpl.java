package com.NotSoFree.service;

import com.NotSoFree.dao.PersonDao;
import com.NotSoFree.domain.Person;
import com.NotSoFree.exception.PersonNotFoundById;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonDao personDao;

    @Override
    @Transactional(readOnly = true)
    public List<Person> listPeople() throws Exception {
        try {
            return personDao.findAll();
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

    }

    @Override
    @Transactional
    public void save(Person person) throws Exception {
        try {
            personDao.save(person);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional
    public void delete(Long person) throws Exception {
        
         try {
            personDao.delete(this.findPersonById(person));
        } catch (PersonNotFoundById e) {
            throw new Exception(e.getMessage());
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Person findPersonById(Long person) throws Exception {
        return personDao.findById(person).orElseThrow(()-> new PersonNotFoundById(person));
    }

}
