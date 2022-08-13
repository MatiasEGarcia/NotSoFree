package com.NotSoFree.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    
    @Size(min=5, max=15, message="name cannot be less than 5 and greater than 15")
    @NotBlank(message="Person name can't be blank")
    @Column(name="names")
    private String names;
    
    @Size(min=5, max=15, message="surname cannot be less than 5 and greater than 15")
    @NotBlank(message="Person surname can't be blank")
    @Column(name="surnames")
    private String surnames;
    
    @Max(value=10, message="The phone number cannot be greater than 10")
    @Column(name="phone")
    private String phone;
  
    @Email(message="please enter a valid email" ,regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")
    @Column(name="email")
    private String email;
    
    @Column(name="address")
    private String address; 
    
}
