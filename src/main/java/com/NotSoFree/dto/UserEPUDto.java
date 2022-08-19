package com.NotSoFree.dto;

import com.NotSoFree.validator.NotOldPassword;
import com.NotSoFree.validator.NotSameUsername;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

//To edit password user and username
@Data
public class UserEPUDto {

    private Long idUser;
    @Size(min = 5, max = 15, message = "Username cannot be less than 5 and greater than 15")
    @NotSameUsername
    @NotBlank(message = "username can't be blank")
    private String username;
    
    @Size(min=6, max=15, message="The password cannot be less than 6 and greater than 15")
    @NotBlank(message="password can't be blank")
    private String newPassword;
    
    @NotOldPassword
    @Size(min=6, max=15, message="The password cannot be less than 6 and greater than 15")
    @NotBlank(message="password can't be blank")
    private String oldPassword;
    
    private String image;
}
