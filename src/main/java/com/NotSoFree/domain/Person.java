package com.NotSoFree.domain;

import com.NotSoFree.dto.PersonDto;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "people", schema = "notanlibre")
public class Person implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long idPerson;
        
    @Column(name="names")
    private String names;
        
    @Column(name="surnames")
    private String surnames;
    
    @Column(name="phone")
    private String phone;
  
    @Column(name="email")
    private String email;
  
    @Column(name="address")
    private String address; 
    
    public Person(){
        
    }
    
    public Person(PersonDto personDto){
        this.idPerson = personDto.getIdPerson();
        this.names = personDto.getNames();
        this.surnames = personDto.getSurnames();
        this.phone = personDto.getPhone();
        this.email = personDto.getEmail();
        this.address = personDto.getAddress();
    }
    
    
}
