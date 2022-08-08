/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.NotSoFree.service;

import com.NotSoFree.domain.Person;
import java.util.List;

public interface PersonService {

    public List<Person> listPeople();

    public void guardar(Person person);

    public void eliminar(Long person);

    public Person findPersonById(Long person);
}
