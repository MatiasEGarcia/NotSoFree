package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.Person;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.UserDto;
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
    public void save(UserDto user, MultipartFile image) throws Exception {

        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        UserD userD = new UserD();
        Person person = new Person();
        byte[] active = {1};
        
        userD.setUsername(user.getUsername());
        userD.setPassword(encoder.encode(user.getUserPassword()));
        userD.setState(active);
        person.setNames(user.getPersonNames());
        person.setSurnames(user.getPersonSurnames());
        person.setPhone(user.getPersonPhone());
        person.setEmail(user.getPersonEmail());
        person.setAddress(user.getPersonAddress());
        userD.setPerson(person);
        
        try {
            if (!image.isEmpty()) {
                userD.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            
            userD = userDao.save(userD);
            rolService.createAll(setRolesforUser(userD,"user"));
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
    public UserDto findUserByUsername(String username) throws UserDNotFoundByUsername {
        UserD userD = null;
        Person person;
        UserDto userDto = new UserDto();
        
        
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
        
        userDto.setUsername(userD.getUsername());
        userDto.setUserPassword(userD.getPassword());
        String stateString = new String(userD.getState());
        userDto.setUserState(stateString);
        person= userD.getPerson();
        userDto.setPersonNames(person.getNames());
        userDto.setPersonSurnames(person.getSurnames());
        userDto.setPersonPhone(person.getPhone());
        userDto.setPersonEmail(person.getEmail());
        userDto.setPersonEmail(person.getAddress());
        
        return userDto;
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
