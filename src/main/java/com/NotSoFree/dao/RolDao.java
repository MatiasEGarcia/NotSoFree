package com.NotSoFree.dao;

import com.NotSoFree.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolDao  extends JpaRepository<Rol, Long> {
    
}
