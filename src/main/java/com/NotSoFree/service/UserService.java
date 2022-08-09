
package com.NotSoFree.service;

import com.NotSoFree.dao.UserDao;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserService implements UserDetailsService{
    
     @Autowired
    private UserDao userDao;
    
    
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserD usuario;
        try{
            usuario= userDao.findByUsername(username);
        }catch(DataAccessException e){
            throw new UsernameNotFoundException("Database Error");
        }catch(Exception e){
            e.printStackTrace();
            throw new UsernameNotFoundException("Unknown Error");
        }
        
        
        if(usuario==null) {
        	throw new UsernameNotFoundException(username);
        }
        
        List<GrantedAuthority> roles= new ArrayList<>();
        
        for(Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getName()));
        }
        
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
