
package com.NotSoFree.dto;

import com.NotSoFree.validator.NotSameUsername;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

//To edit an user without the password
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEDto extends PersonDto{
    
    @NotNull
    private Long idUser;
    
    @Size(min=5, max=15, message="Username cannot be less than 5 and greater than 15")
    @NotSameUsername
    @NotBlank(message="username can't be blank")
    private String username;
    
    private String image;
}
