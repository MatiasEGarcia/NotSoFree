
package com.NotSoFree.dto;

import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

//for the admin to edit the roles and status of a user
@Data
public class UserAEDto {
    
    @NotNull(message="User ID needed")
    private Long idUser;
    
    private String userName;
    
    @NotNull(message="User must have a rol")
    private List<Rol> roles;
    
    @NotBlank(message="User must have a state")
    private String state;
    
    public UserAEDto(){
        
    }
    
    public UserAEDto(UserD userD){
        this.idUser= userD.getIdUser();
        this.userName= userD.getUsername();
        this.state=Byte.toString(userD.getState()); 
        this.roles=userD.getRoles();
    }
}
