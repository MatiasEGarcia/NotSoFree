
package com.NotSoFree.dto;

import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.util.RolEnum;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

//for the admin to edit the roles and status of a user
@Data
public class UserAEDto {
    
    @NotNull(message="User ID needed")
    private Long idUser;
    
    @NotBlank(message="User must have userName")
    private String userName;
    
    //List enums
    private List<RolEnum> roles;
    
    private String state;
    
    public UserAEDto(){
        
    }
    
    public UserAEDto(UserD userD){
        this.idUser= userD.getIdUser();
        this.userName= userD.getUsername();
        this.state=Byte.toString(userD.getState()); 
        this.roles=this.convertToEnum(userD.getRoles());
    }
    
    
    public List<RolEnum> convertToEnum(List<Rol> roles){
        List<RolEnum> listRolesEnum= new ArrayList<>();
        
        for(int i=0; i< roles.size(); i++){
            RolEnum rol=  RolEnum.valueOf(roles.get(i).getName());
            listRolesEnum.add(rol);
        }
        
        return listRolesEnum;
    }
    
}
