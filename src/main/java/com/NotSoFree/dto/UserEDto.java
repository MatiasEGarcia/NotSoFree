
package com.NotSoFree.dto;

import com.NotSoFree.validator.NotSameUsername;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

//To edit an user without the password and username
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEDto extends PersonDto{
    
    @NotNull
    private Long idUser;
    
    private String image;
}
