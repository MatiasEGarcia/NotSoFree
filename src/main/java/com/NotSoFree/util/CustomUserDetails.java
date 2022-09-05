
package com.NotSoFree.util;
import com.NotSoFree.domain.Person;
import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{

    private static final long serialVersionUID = 1L;
    
    private final UserD user;
    
    
    
    public CustomUserDetails(UserD user) {  
            this.user = user;
    }
    
    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        
        final Set<SimpleGrantedAuthority> grntdAuths = new HashSet<>();  // esto es para los roles

     List<Rol> roles = null;   //esta debe ser una lista que guarde los roles

     if (user!=null) {
             roles = user.getRoles();
     }

     //agregamos los roles a la lista de GrantedAuthority
     if (roles!=null) {
             for (Rol role : roles) {
                     grntdAuths.add(new SimpleGrantedAuthority(role.getName()) );  
             }
     }

     return grntdAuths;
        
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Byte.toString(user.getState()).equalsIgnoreCase("1");  //if there is 1 then is active, 0 no
    }
    
    public Long getId(){
        return this.user.getIdUser();
    }
    
    public String getImage(){
        return this.user.getImage();
    }
    
    public Person getPerson(){
        return this.user.getPerson();
    }
}
