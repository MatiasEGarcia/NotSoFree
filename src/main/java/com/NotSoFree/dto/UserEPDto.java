
package com.NotSoFree.dto;

import lombok.Data;

//To edit password user
@Data
public class UserEPDto {
    
    private Long idUser;
    private String newPassword;
    private String oldPassword;
}
