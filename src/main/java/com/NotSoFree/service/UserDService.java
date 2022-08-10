
package com.NotSoFree.service;

import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;


public interface UserDService {
    
    public UserD findUserDByUsername(String username) throws UserDNotFoundByUsername;
}
