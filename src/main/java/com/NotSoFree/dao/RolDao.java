package com.NotSoFree.dao;

import com.NotSoFree.domain.Rol;
import com.NotSoFree.domain.UserD;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RolDao  extends JpaRepository<Rol, Long> {
        
    @Modifying 
    @Query("DELETE FROM Rol r WHERE r.user= :user AND r.name IN (:roles)")
    void deleteByUserAndNameIn(@Param(value = "user")UserD user,@Param(value = "roles")List<String> roles);
}