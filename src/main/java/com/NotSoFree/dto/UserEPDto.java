package com.NotSoFree.dto;

import com.NotSoFree.validator.NotSameUsername;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

//To edit password user and username
@Data
public class UserEPDto {

    private Long idUser;
    @Size(min = 5, max = 15, message = "Username cannot be less than 5 and greater than 15")
    @NotSameUsername
    @NotBlank(message = "username can't be blank")
    private String username;
    private String newPassword;
    private String oldPassword;
}
