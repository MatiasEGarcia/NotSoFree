package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.Product;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.PageDto;
import com.NotSoFree.dto.ProductDto;
import com.NotSoFree.dto.UserAEDto;
import com.NotSoFree.dto.UserCDto;
import com.NotSoFree.dto.UserEPUDto;
import com.NotSoFree.exception.UserDNotFoundByUsername;
import com.NotSoFree.util.BCPasswordEncoder;
import com.NotSoFree.util.CustomUserDetails;
import com.NotSoFree.util.RolEnum;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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

        UserD user;
        try {
            user = userDao.findByUsername(username);
        } catch (DataAccessException e) {
            throw new UsernameNotFoundException("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("Unknown Error");
        }

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }

    @Override
    @Transactional
    public void userCreate(UserCDto user, MultipartFile image) throws Exception {

        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        UserD userD = new UserD(user);
        byte active = 1;
        userD.setState(active);

        try {
            if (!image.isEmpty()) {
                userD.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            } else {
                userD.setImage(user.getImage());
            }
            userD = userDao.save(userD);
            rolService.save(new Rol(RolEnum.ROLE_USER.toString(), userD));
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
    public void userEditByAdmin(UserAEDto user, List<RolEnum> listRolEnum) throws Exception {
        UserD userD = new UserD();
        RolEnum[] allRolesEnum = RolEnum.values();
        List<String> rolesToDelete = new ArrayList<>();
        List<Rol> rolesToAdd = new ArrayList<>();
        userD.setIdUser(user.getIdUser());
        userD.setState(new Byte(user.getState()));

        if (listRolEnum != null) {
            for (RolEnum rol : allRolesEnum) {
                if (user.getRoles().contains(rol) && !listRolEnum.contains(rol)) { //If the role is in USER and not in LISTROLENUM: then I must delete that role from the bdd
                    rolesToDelete.add(rol.toString());
                } else if (!user.getRoles().contains(rol) && listRolEnum.contains(rol)) { //if the role is not in USER and in LISTROLENUM yes: then I should add the role in the database
                    rolesToAdd.add(new Rol(rol.toString(), userD));
                }
            }
        } else { //If listRolEnum is null the user will no longer have any role
            for (RolEnum rol : user.getRoles()) {
                rolesToDelete.add(rol.toString());
            }
        }

        try {
            userDao.updateState(userD);
            if (!rolesToDelete.isEmpty()) {
                rolService.deleteByUserAndNameIn(userD, rolesToDelete);
            }
            if (!rolesToAdd.isEmpty()) {
                rolService.saveAll(rolesToAdd);
            }
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }

    }

    @Override
    @Transactional
    public void userEditPassAndUName(UserEPUDto user) throws Exception {
        BCryptPasswordEncoder encoder = bcPasswordEncoder.passwordEncoder();
        user.setNewPassword(encoder.encode(user.getNewPassword()));
        try {
            userDao.updatePassUsername(user);
        } catch (DataAccessException e) {
            throw new Exception("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unknown Error");
        }
        //User must re-login
        SecurityContextHolder.clearContext();
    }

    @Override
    @Transactional
    public void userImageEdit(MultipartFile image, String user) throws Exception {
        String imageString;
        try {
            imageString = Base64.getEncoder().encodeToString(image.getBytes());
            userDao.updateUserImage(imageString, Long.parseLong(user));
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
    public UserD findUserByUsername(String username) throws UserDNotFoundByUsername {
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
    @Transactional(readOnly = true)
    public PageDto listUsers(int pageNo, int pageSize, String sortField, String sortDir) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort); // the pagination starts at 1 that's why I subtract 1
        Page<UserD> pageUserD;
        List<UserD> listUserD;
        List<UserAEDto> listAEDto = new ArrayList<>();

        try {
            pageUserD = userDao.findAll(pageable);
        } catch (DataAccessException e) {
            throw new UserDNotFoundByUsername("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserDNotFoundByUsername("Unknown Error");
        }
        if (!pageUserD.isEmpty()) {
            listUserD = pageUserD.getContent();
            for (int i = 0; i < listUserD.size(); i++) {
                listAEDto.add(new UserAEDto(listUserD.get(i)));
            }
            return new PageDto<>(listAEDto, pageUserD.getTotalPages(), pageUserD.getTotalElements());
        }

        return null;
    }

    @Override
    @Transactional
    public void deleteByuserName(String username) throws Exception {
        UserD user = this.findUserByUsername(username);

        try {
            userDao.delete(user);
        } catch (DataAccessException e) {
            throw new UserDNotFoundByUsername("Database Error");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserDNotFoundByUsername("Unknown Error");
        }
    }
}
