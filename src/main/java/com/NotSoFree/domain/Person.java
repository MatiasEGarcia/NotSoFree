package com.NotSoFree.domain;

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
    
}
