
package com.NotSoFree.service;

import com.NotSoFree.domain.Rol;
import java.util.List;


public interface RolService {
    
    public void create(Rol rol) throws Exception;
    
    public void createAll(List<Rol> roles) throws Exception;
}
