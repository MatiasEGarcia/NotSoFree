package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserDServiceImpl implements UserDService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserD findUserDByUsername(String username) throws UserDNotFoundByUsername {
        UserD userD = null;
        try {
            userD = userDao.findByUsername(username);
        } catch (DataAccessException e) {
            throw new UserDNotFoundByUsername("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserDNotFoundByUsername("Unknown Error");
        }
        
         if(userD==null) {
        	throw new UserDNotFoundByUsername("There are not user with username= " + username);
        }
        
        return userD;
    }
}
