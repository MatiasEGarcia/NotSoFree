package com.NotSoFree.dto;

import java.io.Serializable;
import lombok.Data;

//user and person attributes to save
@Data
public class UserSaveDto implements Serializable{
    
    private String username;
    private String userPassword;
    private String userState;
    private String personNames;
    private String personSurnames;
    private String personPhone;
    private String personEmail;
    private String personAddress;
    
    
}
