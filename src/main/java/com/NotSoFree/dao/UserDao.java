package com.NotSoFree.dao;

import com.NotSoFree.domain.Product;
import com.NotSoFree.domain.UserD;
import com.NotSoFree.dto.UserEPUDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<UserD, Long> {

    UserD findByUsername(String username);

    @Modifying
    @Query(value="UPDATE UserD u SET u.image = :image"
            + " WHERE u.idUser= :user")
    void updateUserImage(@Param("image")String image,@Param("user") Long user);
    
    @Modifying
    @Query(value="UPDATE UserD u SET u.password= :#{#user.newPassword}"
            + ", u.username= :#{#user.username}"
            + "  WHERE u.idUser= :#{#user.idUser}")
    void updatePassUsername(@Param("user")UserEPUDto user);
    
    @Modifying
    @Query(value="UPDATE UserD u SET u.state= :#{#user.state}"
            + "  WHERE u.idUser= :#{#user.idUser}")
    void updateState(@Param("user")UserD user);
    
    @Query(value = "SELECT p FROM Product p WHERE p.idProduct = ANY (SELECT f.product FROM Favorite f WHERE f.user =  :#{#user.idUser})")
    Page<Product> findFavoriteProducts(@Param("user")UserD user, Pageable pageable);
    
}
