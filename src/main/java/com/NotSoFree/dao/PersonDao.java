package com.NotSoFree.dao;

import com.NotSoFree.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDao extends JpaRepository<Person, Long>{
    
}
