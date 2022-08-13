package com.NotSoFree.dto;

import com.NotSoFree.validator.NotSameUsername;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

//user and person attributes to save
@Data
public class UserDto implements Serializable{
    
    @Size(min=5, max=15, message="Username cannot be less than 5 and greater than 15")
    @NotSameUsername
    @NotBlank(message="username can't be blank")
    private String username;
    
    @Size(min=6, max=15, message="The password cannot be less than 6 and greater than 15")
    @NotBlank(message="password can't be blank")
    private String userPassword;
    
    private String userState;
    
    @Size(min=5, max=15, message="name cannot be less than 5 and greater than 15")
    @NotBlank(message="Person name can't be blank")
    private String personNames;
    
    @Size(min=5, max=15, message="surname cannot be less than 5 and greater than 15")
    @NotBlank(message="Person surname can't be blank")
    private String personSurnames;
    
    @Size(min=8,max=15, message="Phone cannot be less than 8 and greater than 15")
    private String personPhone;
    
    @Email(message="please enter a valid email" ,regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")
    private String personEmail;
    
    private String personAddress;
    
    
}
