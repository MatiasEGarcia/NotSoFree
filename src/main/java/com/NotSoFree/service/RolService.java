
package com.NotSoFree.service;

import com.NotSoFree.domain.Rol;
import java.util.List;


public interface RolService {
    
    public void save(Rol rol) throws Exception;
    
    public void saveAll(List<Rol> roles) throws Exception;
}
