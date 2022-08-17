package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.Person;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.UserAEDto;
import com.NotSoFree.dto.UserCDto;
import com.NotSoFree.dto.UserEDto;
import com.NotSoFree.dto.UserEPDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.util.BCPasswordEncoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("userDetailsService")
public class UserService implements UserDetailsService, UserDService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RolService rolService;

    @Autowired
    private BCPasswordEncoder bcPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserD usuario;
        try {
            usuario = userDao.findByUsername(username);
        } catch (DataAccessException e) {
            throw new UsernameNotFoundException("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("Unknown Error");
        }

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        for (Rol rol : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    @Override
    @Transactional
    public void userCreate(UserCDto user, MultipartFile image) throws Exception {

        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        UserD userD = new UserD();
        Person person = new Person();
        byte[] active = {1};

        userD.setUsername(user.getUsername());
        userD.setPassword(encoder.encode(user.getPassword()));
        userD.setState(active);
        person.setNames(user.getNames());
        person.setSurnames(user.getSurnames());
        person.setPhone(user.getPhone());
        person.setEmail(user.getEmail());
        person.setAddress(user.getAddress());
        userD.setPerson(person);

        try {
            if (!image.isEmpty()) {
                userD.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }else{
                userD.setImage(user.getImage());
            }
            userD = userDao.save(userD);
            rolService.save(new Rol("ROLE_USER", userD));
            /*If the user has to have the admin role, an admin should give it to them (modifying it)*/
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
    @Transactional
    public void userEdit(UserEDto user, MultipartFile image) throws Exception {
        UserD userD = new UserD();
        Person person = new Person();

        userD.setIdUser(user.getIdUser());
        userD.setUsername(user.getUsername());
        person.setIdPerson(user.getIdPerson());
        person.setNames(user.getNames());
        person.setSurnames(user.getSurnames());
        person.setPhone(user.getPhone());
        person.setEmail(user.getEmail());
        person.setAddress(user.getAddress());
        userD.setPerson(person);

        try {
            if (!image.isEmpty()) {
                userD.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            userDao.updateWithoutPassword(userD.getIdUser(), userD); /*TENGO QUE CREAR UN METODO PROPIIO PARA GUARDAR USUARIO SIN PASSWORD Y UNO SOLO PARA LA PASSWORD*/
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
    @Transactional
    public void userEditByAdmin(UserAEDto user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    @Transactional
    public void userEditPassword(UserEPDto user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    @Transactional
    public UserEDto findUserByUsername(String username) throws UserDNotFoundByUsername {
        UserD userD = null;
        UserEDto userEDto = new UserEDto();

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

        userEDto.setIdUser(userD.getIdUser());
        userEDto.setUsername(userD.getUsername());
        userEDto.setImage(userD.getImage());
        userEDto.setIdPerson(userD.getPerson().getIdPerson());
        userEDto.setNames(userD.getPerson().getNames());
        userEDto.setSurnames(userD.getPerson().getSurnames());
        userEDto.setPhone(userD.getPerson().getPhone());
        userEDto.setEmail(userD.getPerson().getEmail());
        userEDto.setAddress(userD.getPerson().getAddress());

        return userEDto;
    }

}
