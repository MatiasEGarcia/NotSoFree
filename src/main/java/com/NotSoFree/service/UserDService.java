
package com.NotSoFree.service;


import com.NotSoFree.dto.UserAEDto;
import com.NotSoFree.dto.UserCDto;
import com.NotSoFree.dto.UserEDto;
import com.NotSoFree.dto.UserEPUDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import org.springframework.web.multipart.MultipartFile;


public interface UserDService {
    
    public void userCreate (UserCDto user, MultipartFile image) throws Exception;
    
    public void userEdit(UserEDto user ,MultipartFile image) throws Exception;
    
    public void userEditByAdmin(UserAEDto user) throws Exception;
    
    public void userEditPassAndUName(UserEPUDto user) throws Exception;
    
     public UserEDto findUserByUsername(String username) throws UserDNotFoundByUsername;
}
