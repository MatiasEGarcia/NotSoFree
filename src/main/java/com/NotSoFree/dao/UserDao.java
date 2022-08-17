package com.NotSoFree.dao;

import com.NotSoFree.domain.UserD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<UserD, Long> {

    UserD findByUsername(String username);

    @Modifying
    @Query(value="UPDATE UserD u SET u.person= :#{#userD.person}"
            + ",u.username= :#{#userD.username}"
            + ",u.image = :#{#userD.image}"
            + " WHERE u.idUser= :id")
    void updateWithoutPassword(@Param(value = "id") long id, @Param("userD")UserD userD);
}
