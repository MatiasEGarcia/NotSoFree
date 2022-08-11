package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.util.BCPasswordEncoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class UserDServiceImpl implements UserDService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RolService rolService;

    @Autowired
    private BCPasswordEncoder bcPasswordEncoder;

    @Override
    @Transactional
    public void save(UserD user, MultipartFile image) throws Exception {

        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        byte active = 1;

        try {
            if (!image.isEmpty()) {
                user.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            user.setState(active);
            user = userDao.save(user);
            rolService.createAll(setRolesforUser(user,"user"));
        } catch (IOException e) {
            throw new Exception("There was an error with the image");
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
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

        if (userD == null) {
            throw new UserDNotFoundByUsername("There are not user with username= " + username);
        }

        return userD;
    }

    @Override
    public List<Rol> setRolesforUser(UserD user,String rol) {
        List<Rol> roles = new ArrayList<>();

        if (rol.contains("user")) {
            Rol userRol = new Rol("ROLE_USER",user);

            roles.add(userRol);
        } else {
            Rol userRol = new Rol("ROLE_USER",user);
            Rol adminRol = new Rol("ROLE_ADMIN",user);

            roles.add(userRol);
            roles.add(adminRol);
        }

        return roles;
    }
}
