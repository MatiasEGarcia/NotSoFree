
package com.NotSoFree.service;

import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import org.springframework.web.multipart.MultipartFile;


public interface UserDService {
    
    public void save(UserD user,MultipartFile image) throws Exception;
    
    public UserD findUserDByUsername(String username) throws UserDNotFoundByUsername;
}
