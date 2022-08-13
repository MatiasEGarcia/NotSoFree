
package com.NotSoFree.service;

import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.UserDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface UserDService {
    
    public void save(UserDto user,MultipartFile image) throws Exception;
    
    public UserDto findUserByUsername(String username) throws UserDNotFoundByUsername;
    
    public List<Rol> setRolesforUser(UserD user,String rol);
}
