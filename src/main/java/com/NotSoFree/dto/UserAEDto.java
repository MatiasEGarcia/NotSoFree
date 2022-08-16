
package com.NotSoFree.dto;

import com.NotSoFree.domain.Rol;
import java.util.List;
import lombok.Data;

//for the admin to edit the roles and status of a user
@Data
public class UserAEDto {
    
    private Long idUser;
    private List<Rol> roles;
    private String state;
}
