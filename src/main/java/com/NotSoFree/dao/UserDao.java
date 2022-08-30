package com.NotSoFree.dao;

import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.UserEPUDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<UserD, Long> {

    UserD findByUsername(String username);

    @Modifying
    @Query(value="UPDATE UserD u SET u.person= :#{#userD.person}"
            + ",u.image = :#{#userD.image}"
            + " WHERE u.idUser= :id")
    void updateWithoutPasswordUsername(@Param(value = "id") long id, @Param("userD")UserD userD);
    
    @Modifying
    @Query(value="UPDATE UserD u SET u.password= :#{#user.newPassword}"
            + ", u.username= :#{#user.username}"
            + "  WHERE u.idUser= :#{#user.idUser}")
    void updatePassUsername(@Param("user")UserEPUDto user);
    
    @Modifying
    @Query(value="UPDATE UserD u SET u.state= :#{#user.state}"
            + "  WHERE u.idUser= :#{#user.idUser}")
    void updateState(@Param("user")UserD user);
    
}
