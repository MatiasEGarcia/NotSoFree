
package com.NotSoFree.dto;

import com.NotSoFree.domain.UserD;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

//To edit an user without the password and username
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEDto extends PersonDto{
    
    @NotNull
    private Long idUser;
    
    private String image;
    
    public UserEDto (){
        
    }
    
    public UserEDto(UserD userD ){
        super(userD.getPerson());
        this.idUser= userD.getIdUser();
        this.image= userD.getImage();   
    }
    
}
