
package com.NotSoFree.service;

import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import java.util.List;


public interface RolService {
    
    public void save(Rol rol) throws Exception;
    
    public void saveAll(List<Rol> roles) throws Exception;
    
    public void deleteByUserAndNameIn(UserD user,List<String> roles)throws Exception;
}
