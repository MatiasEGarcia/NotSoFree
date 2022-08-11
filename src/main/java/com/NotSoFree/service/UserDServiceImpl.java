package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.util.BCPasswordEncoder;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserDServiceImpl implements UserDService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private BCPasswordEncoder bcPasswordEncoder;

    @Override
    public void save(UserD user, MultipartFile image) throws Exception {
        
        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        
        user.setPassword( encoder.encode(user.getPassword() ) );
        
        try {
            if (!image.isEmpty()) {
            user.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            
            userDao.save(user);
        }
        }catch (IOException e) {
            throw new Exception("There was an error with the image");
        }catch(DataAccessException e){
            throw new Exception("Database Error");
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
    }
    
    @Override
    @Transactional(readOnly = true)
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
