
package com.NotSoFree.dto;

import com.NotSoFree.domain.Person;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonDto implements Serializable{
    
    private Long idPerson;
    
    @Size(min=5, max=15, message="name cannot be less than 5 and greater than 15")
    @NotBlank(message="Person name can't be blank")
    private String names;
    
    @Size(min=5, max=15, message="surname cannot be less than 5 and greater than 15")
    @NotBlank(message="Person surname can't be blank")
    private String surnames;
    
    @Size(min=8,max=15, message="Phone cannot be less than 8 and greater than 15")
    private String phone;
    
    @Email(message="please enter a valid email" ,regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")
    private String email;
     
    @NotBlank(message="Person address can't be blank")
    private String address;
    
    public PersonDto(){
        
    }
    
    public PersonDto(Person person){
        this.idPerson= person.getIdPerson();
        this.names= person.getNames();
        this.surnames= person.getSurnames();
        this.phone= person.getPhone();
        this.email= person.getEmail();
        this.address= person.getAddress();
    }       
}
