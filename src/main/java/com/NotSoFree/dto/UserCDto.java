package com.NotSoFree.dto;

import com.NotSoFree.validator.NotSameUsername;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

//To create an User
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCDto extends PersonDto implements Serializable{
    
    private Long idUser;
    
    @Size(min=5, max=15, message="Username cannot be less than 5 and greater than 15")
    @NotSameUsername
    @NotBlank(message="username can't be blank")
    private String username;
    
    @Size(min=6, max=15, message="The password cannot be less than 6 and greater than 15")
    @NotBlank(message="password can't be blank")
    private String password;
    
    private String image;
    
}
