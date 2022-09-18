
package com.NotSoFree.service;


import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.UserAEDto;
import com.NotSoFree.dto.UserCDto;
import com.NotSoFree.dto.UserEPUDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.util.RolEnum;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface UserDService {
    
    public void userCreate (UserCDto user, MultipartFile image) throws Exception;
    
    public void userEditByAdmin(UserAEDto user,List<RolEnum> listRolEnum) throws Exception;
    
    public void userEditPassAndUName(UserEPUDto user) throws Exception;
    
    public void userImageEdit(MultipartFile image , String user) throws Exception;
    
     public UserD findUserByUsername(String username) throws UserDNotFoundByUsername;
     
     public PageDto listUsers(int pageNo, int pageSize, String sortField, String sortDir) throws Exception;
     
     public void deleteByuserName(String username) throws Exception;
     
     public void deleteMyUserByUsername(String username) throws Exception;
}
